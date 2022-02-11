package com.example.lab_1_2_vergeldelacruz_835208_android;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lab_1_2_vergeldelacruz_835208_android.databinding.ActivityProductMapsBinding;

public class ProductMapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityProductMapsBinding binding;
    private String name;
    private String latitude;
    private String longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    /*
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
*/
        Bundle b = getIntent().getExtras();
        name = b.getString("name");
        latitude = b.getString("latitude");
        longitude = b.getString("longitude");

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
        LatLng sydney = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title(name));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}