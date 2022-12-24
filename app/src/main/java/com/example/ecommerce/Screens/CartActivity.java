package com.example.ecommerce.Screens;

import static com.example.ecommerce.Utils.Constant.ShowMessageDialogwithOkBtn;
import static com.example.ecommerce.Utils.Constant.getCartData;
import static com.example.ecommerce.Utils.Constant.getUserLoginStatus;
import static com.example.ecommerce.Utils.Constant.saveCartData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayAdapter arrayAdapter;
    TextView total_price;
    ArrayList<Product> productArrayList =new ArrayList<Product>();


    private Dialog loadingDialog;
    int finalPrice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        recyclerView=findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        total_price=findViewById(R.id.total_price);
    }

    @Override
    protected void onStart() {
        finalPrice=0;
        getData();

        super.onStart();
    }
    public void getData(){

        loadingDialog.show();
         productArrayList=getCartData(this);

         for(int i=0;i<productArrayList.size();i++){
             int totalPrice=  Integer.parseInt(productArrayList.get(i).getPrice())*productArrayList.get(i).getQuantity();
             finalPrice=finalPrice+totalPrice;
         }
         arrayAdapter=new ArrayAdapter();
         recyclerView.setAdapter(arrayAdapter);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                total_price.setText("€"+finalPrice+"");
                loadingDialog.dismiss();
            }
        }, 3000);

    }

    public void moveToMainScreen(View view) {

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void moveToPurchaseScreen(View view) {

        if(getUserLoginStatus(this)){
            startActivity(new Intent(CartActivity.this, PurchaseActivity.class)
                    .putExtra("total",total_price.getText().toString()));
        }
        else {
            ShowMessageDialogwithOkBtn(CartActivity.this, "You must login first ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(new Intent(CartActivity.this, LoginActivity.class));
                    finish();
                }
            });
        }
    }

    public class ArrayAdapter extends RecyclerView.Adapter<ArrayAdapter.ImageViewHoler> {

        public ArrayAdapter(){
        }
        @NonNull
        @Override
        public ArrayAdapter.ImageViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(CartActivity.this).inflate(R.layout.item_cart,parent,false);
            return  new ArrayAdapter.ImageViewHoler(v);
        }

        @Override
        public void onBindViewHolder(@NonNull final ArrayAdapter.ImageViewHoler holder, @SuppressLint("RecyclerView") int position) {


            holder.product_des.setText("Product Description\n"+productArrayList.get(position).getDescription());

            holder.product_quantity.setText("Product Quantity "+productArrayList.get(position).getQuantity()+"");

          int totalPrice=  Integer.parseInt(productArrayList.get(position).getPrice())*productArrayList.get(position).getQuantity();
            holder.product_price.setText("€"+totalPrice);
            holder.product_name.setText(productArrayList.get(position).getName());;
            holder.product_img.setImageDrawable(getResources().getDrawable(productArrayList.get(position).getImage()));




            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finalPrice=0;
                        productArrayList.remove(position);
                        saveCartData(CartActivity.this,productArrayList);
                        getData();

                }
            });


        }

        @Override
        public int getItemCount() {
            return productArrayList.size();
        }

        public class ImageViewHoler extends RecyclerView.ViewHolder {
            ImageView product_img;
            TextView product_name,product_price,product_des,product_quantity;

            Button btnDelete;
            public ImageViewHoler(@NonNull View itemView) {
                super(itemView);
                product_img=itemView.findViewById(R.id.product_img);
                product_name=itemView.findViewById(R.id.product_name);
                product_des=itemView.findViewById(R.id.product_des);
                btnDelete=itemView.findViewById(R.id.btnDelete);
                product_price=itemView.findViewById(R.id.product_price);
                product_quantity=itemView.findViewById(R.id.product_quantity);
            }
        }
    }
}