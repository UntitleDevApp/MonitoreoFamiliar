package com.untitledev.monitoreofamiliar.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.untitledev.monitoreofamiliar.R;
import com.untitledev.monitoreofamiliar.activities.HomeActivity;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringFragment extends Fragment implements OnMapReadyCallback{
    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;
    private LocationManager locationManager;
    private double longitudeGPS, latitudeGPS;
    private double longitudeNetwork, latitudeNetwork;
    private Location currentLocation;
    private Marker marker;
    private CameraPosition camera;
    private static final String STATUS = "Status";
    Context context;

    public MonitoringFragment() {
        // Required empty public constructor
        context = HomeActivity.CONTEXT_MAIN;
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGPS);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_monitoring, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) rootView.findViewById(R.id.map);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        //locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);
        //Ocultar el boton
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
    }

    private boolean isGPSEnabled(){
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(gpsSignal == 0){
                return false;
            }else{
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void showGPSDisabledAlert(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("GPS is disabled on the device. Do you want to enable?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    private void createOrUpdateMarkerByLocation(double latitude, double longitude){
        if(marker == null){
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).draggable(true));
        }else{
            marker.setPosition(new LatLng(latitude, longitude));
        }
    }

    private void zoomToLocation(double latitude, double longitude){
        camera = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(15)       //limit -> 21
                .bearing(0)    //orientación de la camara hacia el este 0°-365°
                .tilt(30)       //efecto 3D 0-90
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }


    private final LocationListener locationListenerGPS = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            longitudeGPS = (double)location.getLongitude();
            latitudeGPS = (double)location.getLatitude();

            if (location != null) {
                Log.i(STATUS, "Test Status GPS Latitude: "+latitudeGPS+" - Longitude: "+longitudeGPS);
                //gMap.addMarker(new MarkerOptions().position(new LatLng(latitudeGPS, longitudeGPS)).draggable(true));
                createOrUpdateMarkerByLocation(latitudeGPS, longitudeGPS);
                zoomToLocation(latitudeGPS, longitudeGPS);
            } else {
                Log.i(STATUS, "Error GPS...!");
                //Toast.makeText(getBaseContext(), "Error GPS...!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private final LocationListener locationListenerNetwork = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            longitudeNetwork = (double)location.getLongitude();
            latitudeNetwork = (double)location.getLatitude();

            if (location != null) {
                Log.i(STATUS, "Test Status Network Latitude: "+latitudeNetwork+" - Longitude"+longitudeNetwork);
                //gMap.addMarker(new MarkerOptions().position(new LatLng(latitudeGPS, longitudeGPS)).draggable(true));
                createOrUpdateMarkerByLocation(latitudeNetwork, longitudeNetwork);
                zoomToLocation(latitudeNetwork, longitudeNetwork);
            } else {
                Log.i(STATUS, "Error GPS...!");
                //Toast.makeText(getBaseContext(), "Error GPS...!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
