package com.app.gopalayurvediccenter.Fragments.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.app.gopalayurvediccenter.Activitiestwo.AddressBookActivity;
import com.app.gopalayurvediccenter.Activitiestwo.AppointmentActivity;
import com.app.gopalayurvediccenter.Activitiestwo.FaqActivity;
import com.app.gopalayurvediccenter.Activitiestwo.HelpActivity;
import com.app.gopalayurvediccenter.Activitiestwo.LoginOrSignupActivity;
import com.app.gopalayurvediccenter.Activitiestwo.OrdersActivity;
import com.app.gopalayurvediccenter.Dataclass.UserDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.app.gopalayurvediccenter.Utils.MyLifecycleObserver;
import com.app.gopalayurvediccenter.databinding.FragmentProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private DatabaseReference dbRefPersonalDetails;
    private static int PICK_IMAGE_REQUEST = 71;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private ImageView ivDisplayPicture;
    private Uri filePath;
    private FirebaseStorage storage;
    private UserDto userDtoDialogDto;
    private LinearLayoutCompat llAddresses, llOrders, llLogout, llFaq, llHelp, llBookAnAppointment, llEdit;
    private String phNumber, fullName, phNumberConcat, userKey;
    private AlertDialog loadingDialog, profileChangeDetailsDialog, logoutDialog;
    private TextView tvName, tvEmailAddress, tvPhNumber, tvBio, tvChangeDetails;
    private ActivityResultLauncher activityResultLauncher;
//    private MyLifecycleObserver mObserver;;
//    private final ActivityResultRegistry mRegistry;
//    private final MutableLiveData<Bitmap> mThumbnailLiveData = new MutableLiveData();
//    private ActivityResultLauncher<Void> mTakePicture;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        tvName = binding.tvName;
        tvEmailAddress = binding.tvEmailAddress;
        tvPhNumber = binding.tvPhNumber;
        tvBio = binding.tvBio;
        llAddresses = binding.llAddresses;
        tvChangeDetails = binding.tvChangeDetails;
        ivDisplayPicture = binding.ivDisplayPicture;
        llOrders = binding.llOrders;
        llLogout = binding.llLogout;
        llFaq = binding.llFaq;
        llHelp = binding.llHelp;
        llBookAnAppointment = binding.llBookAnAppointment;
        llEdit = binding.llEdit;

        phNumber = getActivity().getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "nophNumberfound");
        dbRefPersonalDetails = FirebaseDatabase.getInstance().getReference("users");
        getPersonalDetails();
        llAddresses.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AddressBookActivity.class);
            startActivity(i);
        });

        tvChangeDetails.setOnClickListener(view -> getPersonalDetailsDialog());
        llOrders.setOnClickListener(view -> goToOrdersActivity());
        llLogout.setOnClickListener(view -> {
//            logout();
            logoutDialog();
        });
        llFaq.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), FaqActivity.class);
            startActivity(i);

        });

        llHelp.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), HelpActivity.class);
            startActivity(i);
        });
        llBookAnAppointment.setOnClickListener(view -> {
            Intent i = new Intent(getActivity(), AppointmentActivity.class);

            startActivity(i);
        });

        llEdit.setOnClickListener(view -> showToast("Edit button for profile picture clicked"));

//        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            Log.e(TAG, "onActivityResult: works");
//            if (result.getResultCode() == PICK_IMAGE_REQUEST && result.getResultCode() == RESULT_OK
//                    && result.getData() != null && result.getData().getData() != null) {
//                filePath = result.getData().getData();
//                try {
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
//                    ivDisplayPicture.setImageBitmap(bitmap);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        mObserver = new MyLifecycleObserver(getActivity().getActivityResultRegistry());
//        getLifecycle().addObserver(mObserver);
//
//
//        mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), mRegistry, thumbnail -> {
//            mThumbnailLiveData.setValue(thumbnail);
//            Log.e(TAG, "onActivityResult: this works too");
//        });

        return root;
    }

//    public ProfileFragment(@NonNull ActivityResultRegistry registry) {
//        super();
//        mRegistry = registry;
//    }
//
//    @VisibleForTesting
//    @NonNull
//    ActivityResultLauncher<Void> getTakePicture() {
//        return mTakePicture;
//    }
//
//    @VisibleForTesting
//    @NonNull
//    LiveData<Bitmap> getThumbnailLiveData() {
//        return mThumbnailLiveData;
//    }

    public void setselectProductimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void logout()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.ACCESS_PREFS,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        showToast("Logged out successfully");
        Intent i = new Intent(getActivity(), LoginOrSignupActivity.class);
        startActivity(i);
        logoutDialog.dismiss();
        getActivity().finish();
    }

    private void goToOrdersActivity()
    {
        Intent i = new Intent(getActivity(), OrdersActivity.class);
        startActivity(i);
    }
    private void loadingScreen() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogLoading = factory.inflate(R.layout.loading, null);
        loadingDialog = new AlertDialog.Builder(getActivity()).create();
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setCancelable(false);
        loadingDialog.setView(dialogLoading);
        loadingDialog.show();
        TextView tvLoading = dialogLoading.findViewById(R.id.tvLoading);
        LottieAnimationView animation_view = dialogLoading.findViewById(R.id.animation_view);
        tvLoading.setText("Loading your information....");
    }
    private void getPersonalDetails()
    {
        loadingScreen();
        Query getPersonalDetails = dbRefPersonalDetails.orderByChild("phNumber").equalTo(phNumber);
        getPersonalDetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.hasChildren())
                {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        userKey = dataSnapshot1.getKey();
                        UserDto userDto = dataSnapshot1.getValue(UserDto.class);
                        userDtoDialogDto = userDto;
                        fullName = userDto.getfName()+" "+userDto.getlName();
                        phNumberConcat = "+91 "+userDto.getPhNumber();
                        tvName.setText(fullName);
                        tvEmailAddress.setText(userDto.getEmailAddress());
                        tvPhNumber.setText(phNumberConcat);
                        tvBio.setText(userDto.getBio());
                        if(userDto.getGender().equalsIgnoreCase("male"))
                        {
                            ivDisplayPicture.setImageResource(R.drawable.ic_avatarmale);
                        }
                        if(userDto.getGender().equalsIgnoreCase("female"))
                        {
                            ivDisplayPicture.setImageResource(R.drawable.ic_avatarfemale);
                        }
                        if(userDto.getGender().equalsIgnoreCase("notsay"))
                        {
                            ivDisplayPicture.setImageResource(R.drawable.ic_profileplaceholder);

                        }

                    }
                    loadingDialog.dismiss();

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }





    private void getPersonalDetailsDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogLoading = factory.inflate(R.layout.personal_detail_change_layout, null);
        profileChangeDetailsDialog = new AlertDialog.Builder(getActivity()).create();
        Window window = profileChangeDetailsDialog.getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        profileChangeDetailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        profileChangeDetailsDialog.setCancelable(true);
        profileChangeDetailsDialog.setView(dialogLoading);
        profileChangeDetailsDialog.show();
        profileChangeDetailsDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        TextView etFirstName, etLastName, etEmailAddress, etPhoneNumber, etBio;
        Button btUpdateDetails;

        ImageView tvCloseDialog = profileChangeDetailsDialog.findViewById(R.id.tvCloseDialog);
        etFirstName = profileChangeDetailsDialog.findViewById(R.id.etFirstName);
        etLastName = profileChangeDetailsDialog.findViewById(R.id.etLastName);
        etEmailAddress = profileChangeDetailsDialog.findViewById(R.id.etEmailAddress);
        etPhoneNumber = profileChangeDetailsDialog.findViewById(R.id.etPhoneNumber);
        etBio = profileChangeDetailsDialog.findViewById(R.id.etBio);
        btUpdateDetails = profileChangeDetailsDialog.findViewById(R.id.btUpdateDetails);

        etFirstName.setText(userDtoDialogDto.getfName());
        etLastName.setText(userDtoDialogDto.getlName());
        etEmailAddress.setText(userDtoDialogDto.getEmailAddress());
        etPhoneNumber.setText(userDtoDialogDto.getPhNumber());
        etBio.setText(userDtoDialogDto.getBio());
        tvCloseDialog.setOnClickListener(view -> profileChangeDetailsDialog.dismiss());
        etPhoneNumber.setEnabled(false);
        btUpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etFirstName.getText().toString().trim())) {
                    showToast("Please enter your house number");
                } else if (TextUtils.isEmpty(etLastName.getText().toString().trim())) {
                    showToast("Please enter your floor");
                } else if (TextUtils.isEmpty(etEmailAddress.getText().toString().trim())) {
                    showToast("Please enter your tower/block number");

                } else if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
                    showToast("Please set a tag for your address");
                } else {
//                    String addressID = phNumber + System.currentTimeMillis();
                    String strFirstName = etFirstName.getText().toString().trim();
                    String strLastName = etLastName.getText().toString().trim();
                    String strEmailAddress = etEmailAddress.getText().toString().trim();
                    String strPhNumber = etPhoneNumber.getText().toString().trim();
                    String strBio = etBio.getText().toString().trim();
                    if (!TextUtils.isEmpty(etBio.getText().toString().trim())) {
                        strBio = etBio.getText().toString().trim();
                    } else {
                        strBio = "empty";
                    }

                    userDtoDialogDto.setfName(strFirstName);
                    userDtoDialogDto.setlName(strLastName);
                    userDtoDialogDto.setEmailAddress(strEmailAddress);
                    userDtoDialogDto.setPhNumber(strPhNumber);
                    userDtoDialogDto.setBio(strBio);

                    dbRefPersonalDetails.child(userKey).setValue(userDtoDialogDto);
                    profileChangeDetailsDialog.dismiss();
                    getPersonalDetails();
                    showToast("Information saved");
                }
            }
        });

    }




    private void showToast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }






    private void logoutDialog() {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View dialogLoading = factory.inflate(R.layout.logout_dialog, null);
        logoutDialog = new AlertDialog.Builder(getActivity()).create();
        Window window = logoutDialog.getWindow();

        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        logoutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logoutDialog.setCancelable(true);
        logoutDialog.setView(dialogLoading);
        logoutDialog.show();
        logoutDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        Button noButton, yesButton;
        noButton = logoutDialog.findViewById(R.id.noButton);
        yesButton = logoutDialog.findViewById(R.id.yesButton);

        noButton.setOnClickListener(view -> {
            showToast("Nice choice");
            logoutDialog.dismiss();
        });

        yesButton.setOnClickListener(view -> logout());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}