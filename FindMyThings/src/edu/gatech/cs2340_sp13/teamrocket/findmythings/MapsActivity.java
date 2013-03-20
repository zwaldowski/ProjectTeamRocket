package edu.gatech.cs2340_sp13.teamrocket.findmythings;

import android.os.Bundle;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MapController;
import com.google.android.maps.GeoPoint;

public class MapsActivity extends MapActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

         //fetch the map view from the layout
        MapView mapView = (MapView) findViewById(R.id.mapview);

         //make available zoom controls
        mapView.setBuiltInZoomControls(true);

        //latitude and longitude of Rome
        double lat = 41.889882;
        double lon = 12.479267;

        GeoPoint point = new GeoPoint((int)(lat * 1E6), (int)(lon *1E6));

        //get the MapController object
        MapController controller = mapView.getController();

        //animate to the desired point
        controller.animateTo(point);

        //set the map zoom to 13
        // zoom 1 is top world view
        controller.setZoom(13);

        //invalidate the map in order to show changes
        mapView.invalidate();
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
       
}