package com.app.gopalayurvediccenter.Fragments.ui.social;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.app.gopalayurvediccenter.Adapters.ProductAdapter;
import com.app.gopalayurvediccenter.Adapters.SocialMediaAdapter;
import com.app.gopalayurvediccenter.Dataclass.ProductDto;
import com.app.gopalayurvediccenter.Dataclass.SocialMediaDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.app.gopalayurvediccenter.databinding.FragmentSocialBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SocialFragment extends Fragment {
    private static final String TAG = "FavouritesFragment";
    private List<SocialMediaDto> socialMediaList;
    private RecyclerView rvSocial;
    private FragmentSocialBinding binding;
    private SocialMediaAdapter adapter;
    private String phNumber;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSocialBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        rvSocial = binding.rvSocial;
        phNumber = getActivity().getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "No phone number detected");

        socialMediaList = new ArrayList<>();
        adapter = new SocialMediaAdapter(getActivity(), socialMediaList);
        int numberOfColumns = 2;
        rvSocial.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        rvSocial.setAdapter(adapter);

        getSocialMedia();
        return root;
    }

    private void getSocialMedia() {
        String[] socialMediaNames = {"Facebook", "Youtube", "Blog", "LinkedIn", "Twitter", "Website"};
        int[] logos = {R.drawable.ic_facebook, R.drawable.ic_yt, R.drawable.ic_blogspot, R.drawable.ic_linkedin, R.drawable.ic_twitter, R.drawable.ic_website};
//        String[] mediaLinks = {"https://www.facebook.com/gopalayurveda45". };
        socialMediaList.clear();
        for(int z = 0; z < socialMediaNames.length; z++)
        {
            SocialMediaDto socialMediaDto = new SocialMediaDto();
            socialMediaDto.setSocialMediaName(socialMediaNames[z]);
            socialMediaDto.setSocialMediaLogo(logos[z]);
            socialMediaList.add(socialMediaDto);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getSocialMedia();
    }
}