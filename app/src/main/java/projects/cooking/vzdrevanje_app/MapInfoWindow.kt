package projects.cooking.vzdrevanje_app

import android.widget.TextView
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import si.feri.maintenance.lib.domain.Building

class MapInfoWindow(
    layoutResId: Int,
    mapView: MapView
) : InfoWindow(layoutResId, mapView) {


    override fun onOpen(item: Any?) {
        val marker = item as? Marker ?: return

        val titleView = mView.findViewById<TextView>(R.id.title)
        val subtitleView = mView.findViewById<TextView>(R.id.subtitle)

        titleView.text = marker.title
        subtitleView.text = marker.snippet
        val building = marker.relatedObject as? Building ?: return
        var stores = ""
        for(store in building.stores){
            stores += store.name + "\n"
        }


    }

    override fun onClose() {
    }
}