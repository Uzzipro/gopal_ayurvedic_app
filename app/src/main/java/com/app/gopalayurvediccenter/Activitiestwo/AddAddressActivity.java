package com.app.gopalayurvediccenter.Activitiestwo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gopalayurvediccenter.Dataclass.AddressDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAddressActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = "AddAddressActivity";
    private TextView etHouseNumber, etFloor, etTowerBlock, etHowToReach, etTag;
    private ImageView tvCloseDialog;
    private Button btSaveAddress;
    private DatabaseReference addressDbRef;
    private String phNumber, addLong, addLat;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLatLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        phNumber = getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "nophNumberfound");
        addressDbRef = FirebaseDatabase.getInstance().getReference();
        tvCloseDialog = findViewById(R.id.tvCloseDialog);
        btSaveAddress = findViewById(R.id.btSaveAddress);
        etHouseNumber = findViewById(R.id.etHouseNumber);
        etFloor = findViewById(R.id.etFloor);
        etTowerBlock = findViewById(R.id.etTowerBlock);
        etHowToReach = findViewById(R.id.etHowToReach);
        etTag = findViewById(R.id.etTag);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvCloseDialog.setOnClickListener(view -> onBackPressed());
        btSaveAddress.setOnClickListener(view -> {
            if (TextUtils.isEmpty(etHouseNumber.getText().toString().trim())) {
                showToast("Please enter your house number");
            } else if (TextUtils.isEmpty(etFloor.getText().toString().trim())) {
                showToast("Please enter your floor");
            } else if (TextUtils.isEmpty(etTowerBlock.getText().toString().trim())) {
                showToast("Please enter your tower/block number");
            } else if (TextUtils.isEmpty(etTag.getText().toString().trim())) {
                showToast("Please set a tag for your address");
            } else {
                String addressID = phNumber + System.currentTimeMillis();
                String strHouseNumber = etHouseNumber.getText().toString().trim();
                String strFloor = etFloor.getText().toString().trim();
                String strTowerBlock = etTowerBlock.getText().toString().trim();
                String strHowToReach;
                if (!TextUtils.isEmpty(etHowToReach.getText().toString().trim())) {
                    strHowToReach = etHowToReach.getText().toString().trim();
                } else {
                    strHowToReach = "empty";
                }
                String strTag = etTag.getText().toString().trim();
                AddressDto addressDto = new AddressDto(addressID, strHouseNumber, strFloor, strTowerBlock, strHowToReach, strTag, " ", " ");
                addressDbRef.child("address_book").child(phNumber).child(addressID).setValue(addressDto);
                showToast("Address saved");
                onBackPressed();
            }
        });

    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.e(TAG, "onInfoWindowClick: ");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
             currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
            float zoomLevel = 16.0f; //This goes up to 21
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, zoomLevel));
            mMap.addMarker(new MarkerOptions().position(currentLatLong).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)).draggable(true).title("Set location on map")).showInfoWindow();
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                Log.e(TAG, "onMarkerDrag: ");
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {
                currentLatLong = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
                Log.e(TAG, "onMarkerDragEnd: "+currentLatLong.latitude + " " +currentLatLong.longitude);
            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {
                Log.e(TAG, "onMarkerDragStart: ");

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}