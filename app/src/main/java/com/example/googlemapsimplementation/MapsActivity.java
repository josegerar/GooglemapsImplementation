package com.example.googlemapsimplementation;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        this.latLng = latLng;
        mMap.addMarker(new MarkerOptions().position(latLng).title("Ubicación seleccionada"));
        Projection projection = mMap.getProjection();
        Point point = projection.toScreenLocation(latLng);
        Toast.makeText(
                MapsActivity.this,
                "Ubicacion \n"
                        + "Lat: " + latLng.latitude + "\n"
                        + "Lng: " + latLng.longitude + "\n"
                        + "X: " + point.x + " - Y: " + point.y,
                Toast.LENGTH_SHORT
        ).show();
    }

    /**
     * @param view
     */
    public void changeViewMap(View view) {
        int mapType = mMap.getMapType();
        mapType++;
        if (mapType > 4) {
            mapType = 0;
        }
        mMap.setMapType(mapType);
    }

    /**
     * @param view
     */
    public void activateOptionsMap(View view) {
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    /**
     * @param view
     */
    public void moveCameraMap(View view) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-0.876061, -79.494887), 15);
        mMap.moveCamera(cameraUpdate);
    }

    /**
     * @param view
     */
    public void paintPolygonMap(View view) {
        PolylineOptions lines = new PolylineOptions()
                .add(new LatLng(-0.872103, -79.498406))
                .add(new LatLng(-0.872553, -79.484051))
                .add(new LatLng(-0.878797, -79.486003))
                .add(new LatLng(-0.879655, -79.500595))
                .add(new LatLng(-0.872103, -79.498406));
        lines.width(8);
        lines.color(Color.RED);
        mMap.addPolyline(lines);
    }

    /**
     * @param view
     */
    public void animateCamera(View view) {
        this.latLng = mMap.getCameraPosition().target;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(this.latLng)
                .zoom(mMap.getCameraPosition().zoom)
                .bearing(45)
                .tilt(70)
                .build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }
}