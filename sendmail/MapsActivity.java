package com.example.sendmail;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Get the SupportMapFragment and request notification when the map is ready
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Set a marker at a specific location (e.g., Pune, India)
        LatLng pune = new LatLng(18.5204, 73.8567);
        MarkerOptions markerOptions=new MarkerOptions().position(pune).title("Marker in Pune");
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pune));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                Toast.makeText(MapsActivity.this,
                        "Touched Location: \nLat: " + latitude + ", Lng: " + longitude,
                        Toast.LENGTH_SHORT).show();
                mMap.clear(); // Removes previous markers
                mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
            }
        });


    }
}
