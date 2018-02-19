package com.clearboxsoln.aviationwx;

import android.content.pm.PackageManager;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback, FragmentCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private static int MY_LOCATION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("ME","Hello World. onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        LatLng ssi = new LatLng(31.1519722,-81.3910556);
        //mMap.addMarker(new MarkerOptions().position(ssi).title("Marker at KSSI"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ssi, 7.7f));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        MapListener ml = new MapListener(googleMap);
        mMap.setOnCameraIdleListener(ml);
        mMap.setOnMapLongClickListener(ml);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            // Major fight trying to get this to work.  Using example from documentation simply doesn't
            // seem to work. Don't really know why.  When I step through in debugger, it looks like
            // we why get inside the if statement (one commented out), but we never do.


            // Log.v("DBG1",Integer.toString(permissions.length));
            // Log.v("DBG1",permissions[0]);
            // Log.v("DBG1",android.Manifest.permission.ACCESS_FINE_LOCATION);
            // Log.v("DBG1",Integer.toString(grantResults[0]));
            // Log.v("DBG1",Integer.toString(PackageManager.PERMISSION_GRANTED));
            // if ((permissions.length == 1) &&
            //         (permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION) &&
            //         (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            }
            //}
        }
    }

}
