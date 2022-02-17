package com.app.gopalayurvediccenter.Activitiestwo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gopalayurvediccenter.Dataclass.AddressDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAddressActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {
    private TextView etHouseNumber, etFloor, etTowerBlock, etHowToReach, etTag;
    private ImageView tvCloseDialog;
    private Button btSaveAddress;
    private DatabaseReference addressDbRef;
    private String phNumber;





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
                AddressDto addressDto = new AddressDto(addressID, strHouseNumber, strFloor, strTowerBlock, strHowToReach, strTag);
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

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}