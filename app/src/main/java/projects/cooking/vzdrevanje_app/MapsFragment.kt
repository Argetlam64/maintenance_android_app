package projects.cooking.vzdrevanje_app


import android.animation.ValueAnimator
import android.annotation.SuppressLint
import projects.cooking.vzdrevanje_app.MapInfoWindow
import org.osmdroid.config.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import projects.cooking.vzdrevanje_app.databinding.FragmentMapsBinding
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import si.feri.maintenance.lib.domain.Building
import org.osmdroid.views.overlay.infowindow.InfoWindow

class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var app: MyApplication
    private lateinit var mapView: MapView
    private lateinit var fused: FusedLocationProviderClient
    private val MARIBOR = GeoPoint(46.5547, 15.6459)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        app = requireContext().applicationContext as MyApplication
        return binding.root
    }

    @SuppressLint("MissingPermission")
    private fun centerMap() {
        fused.lastLocation.addOnSuccessListener { loc ->
            val target = if (loc != null) {
                val point = GeoPoint(loc.latitude, loc.longitude)

                val marker = Marker(mapView).apply {
                    position = point
                    icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.map_person_marker
                    )
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                }

                mapView.overlays.add(marker)
                point
            } else {
                MARIBOR
            }

            mapView.controller.apply {
                setZoom(16.0)
                setCenter(target)
            }

            mapView.invalidate()
        }
    }

    fun addMapMarker(building: Building){
        val marker = Marker(mapView).apply{
            position = GeoPoint(building.address.latitude, building.address.longitude)
            title = building.name
            setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        }
        marker.icon = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.location_marker
        )

        marker.snippet = "${building.getNumOfTasks()} ${getString(R.string.maps_open_tasks)}"
        marker.relatedObject = building
        marker.infoWindow = MapInfoWindow(R.layout.map_building_info, mapView)

        marker.setOnMarkerClickListener { m, _ ->

            if (m.isInfoWindowShown) {
                // Go to the info screen if it's already open
                val building = m.relatedObject as Building
                findNavController().navigate(
                    MyNavDirections.actionGlobalBuildingInfoFragment(building.id.toString())
                )
                m.closeInfoWindow()
                true
            } else {
                mapView.controller.animateTo(m.position)

                //slowly zoom into the point
                mapView.post {
                    val startZoom = mapView.zoomLevelDouble
                    val targetZoom = 17.5

                    ValueAnimator.ofFloat(startZoom.toFloat(), targetZoom.toFloat()).apply {
                        duration = 400
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener { animator ->
                            val zoom = (animator.animatedValue as Float).toDouble()
                            mapView.controller.setZoom(zoom)
                        }
                        start()
                    }
                }
                //show info if it's not open yet
                m.showInfoWindow()
                true
            }
        }

        mapView.overlays.add(marker)
        mapView.invalidate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().userAgentValue = requireContext().packageName
        fused = LocationServices.getFusedLocationProviderClient(requireContext())

        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        //close info if clicked somewhere
        mapView.overlays.add(object : org.osmdroid.views.overlay.Overlay() {
            override fun onSingleTapConfirmed(e: android.view.MotionEvent?, mapView: MapView?): Boolean {
                InfoWindow.closeAllInfoWindowsOn(mapView)
                return false
            }
        })

        centerMap()

        for(building in app.currentTechnician.buildings){
            addMapMarker(building)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

}