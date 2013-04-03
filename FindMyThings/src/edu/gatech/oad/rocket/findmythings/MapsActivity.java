package edu.gatech.oad.rocket.findmythings;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


import edu.gatech.oad.rocket.findmythings.Helpers.*;
import edu.gatech.oad.rocket.findmythings.NonActivity.*;

public class MapsActivity extends FragmentActivity   {
	
	/**
	 * Used to convert address into longitude and latitude
	 */
	private Geocoder findLoc;
	
	/**
	 * Reference to the map
	 */
	private GoogleMap map;
	
	/**
	 * Reference to the fragment holding the map
	 */
	private SupportMapFragment mFrag;
	
	/**
	 * Stores a list of possible addresses
	 */
	private List<Address> loc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Reference to the map fragment
        mFrag = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map));
        //Gets the actual map
        map = mFrag.getMap();
        //Geocoder object to convert a text address into an Address object
        findLoc = new Geocoder(getApplicationContext());
        
        //Used to add a marker to the map
        MarkerOptions options = new MarkerOptions();
        
        
        try { //Converts the location string into a list of possible long/lag addresses
        	loc = findLoc.getFromLocationName(ItemDetailFragment.mItem.getLoc(), 1);
		} catch (IOException e) {
			new ErrorDialog("Unable to reach Google Maps at this time, please check your connection strength.").getDialog(this).show();
		}
        //Object to store lat/long
        LatLng currlocation = new LatLng(loc.get(0).getLatitude(),loc.get(0).getLongitude());
        //Sets the markers position
        options.position(currlocation);
        //Tells the camera where to go
        CameraUpdate updatePosition = CameraUpdateFactory.newLatLng(currlocation);
        //Adds the marker
        map.addMarker(options);
        //Moves the camera to the CameraUpdate location
        map.moveCamera(updatePosition);
        
    }


       
}