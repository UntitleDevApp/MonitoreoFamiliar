package com.untitledev.monitoreofamiliar.fragments;


import android.Manifest;
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

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MonitoringFragment extends Fragment implements OnMapReadyCallback, LocationListener {
    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;

    private LocationManager locationManager;
    private Location currentLocation;
    private Marker marker;
    private CameraPosition camera;

    public MonitoringFragment() {
        // Required empty public constructor
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
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gMap.setMyLocationEnabled(true);
        //Ocultar el boton
        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        /*gMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {
                Toast.makeText(getContext(),"Click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        currentLocation = location;
        if(currentLocation != null){
            createOrUpdateMarkerByLocation(location);
            zoomToLocation(location);
        }
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

    private void createOrUpdateMarkerByLocation(Location location){
        if(marker == null){
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        }else{
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    private void zoomToLocation(Location location){
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(15)       //limit -> 21
                .bearing(0)    //orientación de la camara hacia el este 0°-365°
                .tilt(30)       //efecto 3D 0-90
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));
    }


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getContext(),"Changed...! "+location.getProvider(), Toast.LENGTH_SHORT).show();
        //Con este metodo no se puede mover el marcador...
        //gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(true));
        zoomToLocation(location);
        createOrUpdateMarkerByLocation(location);
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
}