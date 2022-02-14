package com.app.gopalayurvediccenter.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gopalayurvediccenter.Activitiestwo.ProductDetailActivity;
import com.app.gopalayurvediccenter.Dataclass.CartDto;
import com.app.gopalayurvediccenter.Dataclass.ProductDto;
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


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private static String TAG = "ProductAdapter";
    private Context context;
    private List<ProductDto> cardinfoList;
    private int quantityCountint = 0;
    private int quantityCap = 0;
    private int quantityDb;
    private int totalprice;
    private int productPrice;


    public ProductAdapter(Context mContext, List<ProductDto> cardinfoList) {
        this.context = mContext;
        this.cardinfoList = cardinfoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.extra_layout_product_view_withoutpic, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final ProductDto cardData = cardinfoList.get(position);
        holder.tvProductName.setText(cardData.getProductName());
        String price = "Rs. " + cardData.getProductPrice();
        holder.tvProductPrice.setText(price);
        holder.btMinus.setOnClickListener(v -> setMinusQuantity(holder, position));
        holder.btPlus.setOnClickListener(v -> setPlusQuantity(holder, position, false));

        final String phNumber = context.getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "No phone number detected");
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
        db.child("cart_table").child(phNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        CartDto cx = dataSnapshot2.getValue(CartDto.class);

                        if (cardData.getProductID().equalsIgnoreCase(cx.getProductKey())) {
                            quantityCountint = Integer.parseInt(cx.getProductQuantity());
                            cardData.setCartHasItem(true);
                        }

                    }
                    if (cardData.isCartHasItem()) {
                        Toast.makeText(context, "Items cannot go below 0", Toast.LENGTH_LONG).show();
                        holder.quantityCount.setText(String.valueOf(quantityCountint));
                    } else {
                        Toast.makeText(context, "Items cannot go below 0", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(context, "Items cannot go below 0", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        holder.btAddToCart.setOnClickListener(view -> {
////            addProductToCart(holder, true, position);
//            setPlusQuantity(holder, position, true);
//        });

//        getData(holder, position);
    }

    private void setMinusQuantity(final MyViewHolder holder, int pos) {
        quantityCountint = quantityCountint - 1;
        addProductToCart(holder, false, pos);
    }

    public void setPlusQuantity(final MyViewHolder holder, int pos, boolean first) {
        if (first) {
//            holder.btAddToCart.setVisibility(View.GONE);
//            holder.llPlusMinus.setVisibility(View.VISIBLE);
        }
        quantityCountint = quantityCountint + 1;
        addProductToCart(holder, true, pos);
    }

    private void addProductToCart(final MyViewHolder holder, final boolean minusOrPlus, final int pos) {

        final String phNumber = context.getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "No phone number detected");
        final DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Adding products to your cart.......");
        progressDialog.show();

        final ProductDto productsClass = cardinfoList.get(pos);
//        quantityCheck(productsClass.getProductType());
        final String productDbKey = dbRef.child("cart_table").child(phNumber).push().getKey();
        dbRef.child("cart_table").child(phNumber).orderByChild("productKey").equalTo(productsClass.getProductID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {//cart has already been made
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        CartDto c3 = dataSnapshot1.getValue(CartDto.class);
                        quantityDb = Integer.parseInt(c3.getProductQuantity());
                        totalprice = 0;
                        productPrice = Integer.parseInt(productsClass.getProductPrice());
                        if (quantityDb != 0) {
                            if (minusOrPlus) {
                                DatabaseReference dbRef2;
                                dbRef2 = FirebaseDatabase.getInstance().getReference().getRef();
                                Query getDeliveryCharges = dbRef2.child(Constants.CONSTANTS_FOR_ANDROID_APP_FIREBASE);
                                getDeliveryCharges.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                        String getQuantityForType = "quantity_" + productsClass.getProductType().toLowerCase();
                                        String quantityForCategory = String.valueOf(snapshot.child(getQuantityForType).getValue());
                                        quantityCap = Integer.parseInt(quantityForCategory);
                                        if (quantityDb == quantityCap) {
                                            Toast.makeText(context, "Quantity is set to only " + quantityCap + " for " + productsClass.getProductType(), Toast.LENGTH_LONG).show();
                                            progressDialog.dismiss();

                                        } else {
                                            quantityDb = quantityDb + 1;
                                            totalprice = productPrice * quantityDb;
                                            c3.setProductTotalPrice(String.valueOf(totalprice));
                                            c3.setProductQuantity(String.valueOf(quantityDb));
                                            dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productTotalPrice").setValue(String.valueOf(totalprice));
                                            dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productQuantity").setValue(String.valueOf(quantityDb));
                                            progressDialog.dismiss();
//                                            quantityDb = quantityDb + 1;
//                                            totalprice = productPrice * quantityDb;
//                                            cardinfoList.remove(pos);
//                                            cardinfoList.clear();
//                                            c3.setProductTotalPrice(String.valueOf(totalprice));
//                                            c3.setProductQuantity(String.valueOf(quantityDb));
//                                dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productTotalPrice").setValue(String.valueOf(totalprice));
//                                dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productQuantity").setValue(String.valueOf(quantityDb));
//                                            dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).setValue(c3);
//                                            progressDialog.dismiss();
                                            holder.quantityCount.setText(String.valueOf(quantityDb));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                    }

                                });

//                                if (quantityDb == quantityCap) {
//                                    Toast.makeText(context, "Quantity for test purposes is set to only " + quantityCap + " for " + productsClass.getProductType(), Toast.LENGTH_LONG).show();
//                                    progressDialog.dismiss();
//                                } else {
//                                    quantityDb = quantityDb + 1;
//                                    totalprice = productPrice * quantityDb;
//                                    c3.setProductTotalPrice(String.valueOf(totalprice));
//                                    c3.setProductQuantity(String.valueOf(quantityDb));
//                                    dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productTotalPrice").setValue(String.valueOf(totalprice));
//                                    dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productQuantity").setValue(String.valueOf(quantityDb));
//                                    progressDialog.dismiss();
//                                }

                            }
                            if (minusOrPlus == false) {
                                if (quantityDb == 1) {
//                                    holder.llPlusMinus.setVisibility(View.GONE);
//                                    holder.btAddToCart.setVisibility(View.VISIBLE);
                                    dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).removeValue();
                                    quantityDb = 0;
                                    Toast.makeText(context, "Items cannot go below 0", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    quantityDb = quantityDb - 1;
                                    totalprice = productPrice * quantityDb;
                                    c3.setProductTotalPrice(String.valueOf(totalprice));
                                    c3.setProductQuantity(String.valueOf(quantityDb));
                                    dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productTotalPrice").setValue(String.valueOf(totalprice));
                                    dbRef.child("cart_table").child(phNumber).child(c3.getNodeKey()).child("productQuantity").setValue(String.valueOf(quantityDb));
                                    progressDialog.dismiss();

                                }

                            }
                            holder.quantityCount.setText(String.valueOf(quantityDb));
                        }

                    }

                } else//cart has not been made yet
                {
                    if (minusOrPlus) {
                        quantityCountint = 1;
                        holder.quantityCount.setText(String.valueOf(quantityCountint));
                        CartDto c4 = new CartDto();
                        c4.setPd(productsClass);
                        c4.setProductQuantity(String.valueOf(quantityCountint));
                        c4.setProductTotalPrice(productsClass.getProductPrice());
                        c4.setNodeKey(productDbKey);
                        c4.setProductKey(productsClass.getProductID());
                        progressDialog.dismiss();
                        dbRef.child("cart_table").child(phNumber).child(productDbKey).setValue(c4);
                    } else {
                        Toast.makeText(context, "Items cannot go below 0", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void productDetailClick(int pos) {
        final ProductDto cardData = cardinfoList.get(pos);

        Intent i = new Intent(context, ProductDetailActivity.class);
        i.putExtra(Constants.PRODUCT_ID, cardData.getProductID());
        i.putExtra(Constants.PRODUCT_IMG, cardData.getProductImg());
        i.putExtra(Constants.PRODUCT_TYPE, cardData.getProductType());
        i.putExtra(Constants.PRODUCT_NAME, cardData.getProductName());
        i.putExtra(Constants.PRODUCT_MEASUREMENT, cardData.getProductMeasurement());
        i.putExtra(Constants.PRODUCT_PRICE, cardData.getProductPrice());
        i.putExtra(Constants.PRODUCT_INFO, cardData.getProductInfo());
        context.startActivity(i);
    }


//    private void getData(final MyViewHolder holder, int pos) {
//        final String pKey;//current position product key
//        ProductDto productsClass = cardinfoList.get(pos);
//        pKey = productsClass.getProductID();
//
//
//        final String phNumber = context.getSharedPreferences(Constants.ACCESS_PREFS, Context.MODE_PRIVATE).getString(Constants.PH_NUMBER, "No phone number detected");
//        DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//        db.child("cart_table").child(phNumber).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChildren()) {
//                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                        CartDto cx = dataSnapshot2.getValue(CartDto.class);
//                        quantityCountint = Integer.parseInt(cx.getProductQuantity());
//                        if (pKey.equalsIgnoreCase(cx.getProductKey())) {
//                            holder.llPlusMinus.setVisibility(View.VISIBLE);
//                            holder.btAddToCart.setVisibility(View.GONE);
//                            holder.quantityCount.setText(String.valueOf(quantityCountint));
//                        }
//
//                    }
//                } else {
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

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

        private TextView tvProductName, tvProductPrice, quantityCount;
        private Button  btMinus, btPlus;

        public MyViewHolder(View view) {
            super(view);
            tvProductName = view.findViewById(R.id.tvProductName);
            tvProductPrice = view.findViewById(R.id.tvProductPrice);
            btMinus = view.findViewById(R.id.minusQuantity);
            btPlus = view.findViewById(R.id.plusQuantity);
            quantityCount = view.findViewById(R.id.quantityCount);

        }
    }
}
