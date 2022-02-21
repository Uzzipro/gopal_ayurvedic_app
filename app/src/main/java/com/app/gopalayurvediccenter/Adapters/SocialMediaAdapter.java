package com.app.gopalayurvediccenter.Adapters;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gopalayurvediccenter.Activitiestwo.MapsActivity;
import com.app.gopalayurvediccenter.Activitiestwo.ProductDetailActivity;
import com.app.gopalayurvediccenter.Dataclass.CartDto;
import com.app.gopalayurvediccenter.Dataclass.ProductDto;
import com.app.gopalayurvediccenter.Dataclass.SocialMediaDto;
import com.app.gopalayurvediccenter.R;
import com.app.gopalayurvediccenter.Utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SocialMediaAdapter extends RecyclerView.Adapter<SocialMediaAdapter.MyViewHolder> {

    private static String TAG = "ProductAdapter";
    private Context context;
    private List<SocialMediaDto> cardinfoList;



    public SocialMediaAdapter(Context mContext, List<SocialMediaDto> cardinfoList) {
        this.context = mContext;
        this.cardinfoList = cardinfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.social_layout, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final SocialMediaDto cardData = cardinfoList.get(position);

        holder.socialMediaName.setText(cardData.getSocialMediaName());
//        Picasso.get().load(cardData.getSocialMediaLogo()).into(holder.socialMediaLogos);
        holder.socialMediaLogos.setImageResource(cardData.getSocialMediaLogo());
        holder.clMain.setOnClickListener(view -> {
//            String urlString = "https://gopalayurvediccenter.com";
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setPackage("com.android.chrome");
//            try {
//                context.startActivity(intent);
//            } catch (ActivityNotFoundException ex) {
//                // Chrome browser presumably not installed so allow user to choose instead
//                intent.setPackage(null);
//                context.startActivity(intent);
//            }

            Intent i = new Intent(context, MapsActivity.class);
            context.startActivity(i);
        });


    }

    @Override
    public int getItemCount() {
        return cardinfoList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView socialMediaName;
        private ImageView socialMediaLogos;
        private ConstraintLayout clMain;

        public MyViewHolder(View view) {
            super(view);
            socialMediaName = view.findViewById(R.id.tvSocialMediaName);
            socialMediaLogos = view.findViewById(R.id.ivImage);
            clMain = view.findViewById(R.id.clMain);

        }
    }
}
