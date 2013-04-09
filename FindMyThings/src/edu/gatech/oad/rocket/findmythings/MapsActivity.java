package edu.gatech.oad.rocket.findmythings;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import edu.gatech.oad.rocket.findmythings.util.*;

/**
 * CS 2340 - FindMyStuff Android App
 * Activity that deals with the maps feature for location of items
 * 
 * @author TeamRocket
 * */
public class MapsActivity extends FragmentActivity {
	
	/**
	 * Used to convert address into longitude and latitude
	 */
	private Geocoder findLoc;
	
	/**
	 * Reference to the map
	 */
	private GoogleMap map;
	
	/**
	 * Stores a list of possible addresses
	 */
	private List<Address> loc;

	private void setupMapIfNeeded() {

		// Do a null check to confirm that we have not already instantiated the map.
		if (map == null) {
			// Try to obtain the map from the SupportMapFragment.
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				setupMap();
			}
		}
	}

	private void setupMap() {
		//Geocoder object to convert a text address into an Address object
		findLoc = new Geocoder(this);

		LatLng currlocation = null;

		//Used to add a marker to the map
		MarkerOptions options = new MarkerOptions();

		try { //Converts the location string into a list of possible long/lag addresses
			for (int i = 0; i < 10 && (loc == null || loc.size() == 0); i++) {
				loc = findLoc.getFromLocationName(ItemDetailFragment.mItem.getLoc(), 1);
			}

			if (loc.size() >= 1) {
				//Object to store lat/long
				currlocation = new LatLng(loc.get(0).getLatitude(),loc.get(0).getLongitude());
			} else {
				new ErrorDialog("Unable to find the location using Google Maps at this time, please try again later.").getDialog(this).show();
			}
		} catch (IOException e) {
			new ErrorDialog("Unable to reach Google Maps at this time, please check your connection strength.").getDialog(this).show();
		}

		if (currlocation != null) {
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

	@Override
	protected void onResume() {
		super.onResume();
		setupMapIfNeeded();
	}

	/**
	 * create the window with correct layout
	 * 
	 * @param savedInstanceState
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

		setTitle(ItemDetailFragment.mItem.getLoc());
	}
       
}