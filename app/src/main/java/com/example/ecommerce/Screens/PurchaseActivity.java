package com.example.ecommerce.Screens;

import static com.example.ecommerce.Utils.Constant.getUserId;
import static com.example.ecommerce.Utils.Constant.saveCartData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PurchaseActivity extends AppCompatActivity {
    TextView total_price;
    RadioButton btnPayment2,btnPayment1;
    private Dialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        total_price=findViewById(R.id.total_price);
        btnPayment2=findViewById(R.id.btnPayment2);
        btnPayment1=findViewById(R.id.btnPayment1);
        total_price.setText(getIntent().getStringExtra("total"));
        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                  btnPayment1.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                      }
                  });

        btnPayment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        getPaymentMethod();
    }


 public void getPaymentMethod(){
        loadingDialog.show();

     DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference().child("User").child(getUserId(this)).child("Card1");
     myRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
     DatabaseReference databaseReference=  FirebaseDatabase.getInstance().getReference().child("User").child(getUserId(this)).child("Card2");
     databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
     loadingDialog.dismiss();



 }

    public void moveToCartScreen(View view) {
        finish();
    }

    public void showAlert(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to purchase?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ArrayList<Product> productArrayList = new ArrayList<Product>();
                        productArrayList.clear();
                        saveCartData(PurchaseActivity.this,productArrayList);
                      startActivity(new Intent(PurchaseActivity.this, MainActivity.class).
                              setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}