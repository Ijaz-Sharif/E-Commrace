package com.example.ecommerce.Screens;

import static com.example.ecommerce.Utils.Constant.getUserId;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardActivity extends AppCompatActivity {
          EditText card_number,card_exp_date,card_cvv,first_name,last_name,zipcode;
    private Dialog loadingDialog;
    Button btn_submit,btn_otherCard,btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        zipcode=findViewById(R.id.zipcode);
        last_name=findViewById(R.id.last_name);
        first_name=findViewById(R.id.first_name);
        card_cvv=findViewById(R.id.card_cvv);
        card_exp_date=findViewById(R.id.card_exp_date);
        card_number=findViewById(R.id.card_number);
        btn_submit=findViewById(R.id.btn_submit);
        btn_otherCard=findViewById(R.id.btn_otherCard);
        btn_otherCard.setVisibility(View.GONE);
        btn_login=findViewById(R.id.btn_login);
        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void addCard(View view) {
        loadingDialog.show();
        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference().child("User").child(getUserId(AddCardActivity.this)).child("Card1");
             myRef.child("CardNumber").setValue(card_number.getText().toString());
        myRef.child("CVC").setValue(card_cvv.getText().toString());
        myRef.child("ExpiryDate").setValue(card_exp_date.getText().toString());
        myRef.child("ZipCode").setValue(zipcode.getText().toString());
        myRef.child("HonerName").setValue(first_name.getText().toString()+" "+last_name.getText().toString());
        loadingDialog.dismiss();
        Toast.makeText(this, "Card added sucessfully", Toast.LENGTH_SHORT).show();

        clearData();


    }
    public void addSecondCard(View view) {
        loadingDialog.show();
        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference().child("User").child(getUserId(AddCardActivity.this)).child("Card2");
        myRef.child("CardNumber").setValue(card_number.getText().toString());
        myRef.child("CVC").setValue(card_cvv.getText().toString());
        myRef.child("ExpiryDate").setValue(card_exp_date.getText().toString());
        myRef.child("ZipCode").setValue(zipcode.getText().toString());
        myRef.child("HonerName").setValue(first_name.getText().toString()+" "+last_name.getText().toString());
        loadingDialog.dismiss();
        Toast.makeText(this, "Card added sucessfully", Toast.LENGTH_SHORT).show();
         finish();
    }
    public void clearData(){
        btn_submit.setVisibility(View.GONE);
        btn_otherCard.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        card_cvv.setText("");
        card_exp_date.setText("");
        last_name.setText("");
        card_number.setText("");
        first_name.setText("");
        zipcode.setText("");
    }

    public void startLogin(View view) {
        finish();
    }
}