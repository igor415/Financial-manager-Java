package com.varivoda.igor.financijskimanager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if(mapFragment!=null){
            mapFragment.getMapAsync(this);
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        int ovlastenje = 1;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ovlastenje);
        }

        // Add a marker in Sydney and move the camera
        LatLng ilica = new LatLng(45.813019, 15.966568);
        LatLng jankomir = new LatLng(45.803019, 15.860000);
        LatLng noviZagreb = new LatLng(45.777019, 15.966568);
        LatLng zitnjak = new LatLng(45.777019, 16.057568);
        LatLng rijeka = new LatLng(45.328979, 14.457664);
        LatLng osijek = new LatLng(45.5511111, 18.6938889);
        float zoomLevel = 9.0f;
        googleMap.addMarker(new MarkerOptions().position(ilica).title("Ilica")).showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(rijeka).title("Rijeka")).showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(osijek).title("Osijek")).showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(jankomir).title("Jankomir")).showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(noviZagreb).title("Novi Zagreb")).showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(zitnjak).title("Žitnjak")).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(noviZagreb, zoomLevel));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }
}
