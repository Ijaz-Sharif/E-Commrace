package com.example.ecommerce.Screens;

import static com.example.ecommerce.Utils.Constant.getAppStatus;
import static com.example.ecommerce.Utils.Constant.saveProductData;
import static com.example.ecommerce.Utils.Constant.setAppStatus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.ecommerce.MainActivity;
import com.example.ecommerce.Model.Product;
import com.example.ecommerce.R;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread thread=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    if(getAppStatus(SplashActivity.this)){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                    else {
                         addProductData();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }



    public void addProductData(){

        ArrayList<Product> productArrayList=new ArrayList<Product>();
        productArrayList.add(new Product("Nike shoes","50","This is best for sports use",R.drawable.nike_shoe,0016));
        productArrayList.add(new Product("Kids shoes","10","This is best for children",R.drawable.kids_shoes,0011));
        productArrayList.add(new Product("Leather shoes for men","35","This is best for mens for office use",R.drawable.leather_shoes,0015));
        productArrayList.add(new Product("Heals shoes","20","This is best for girls for function use",R.drawable.leadies_shoes,0013));
        productArrayList.add(new Product("Leather shoes for women","10","This is best for girls for office use",R.drawable.leather_women_shoes,0014));
        productArrayList.add(new Product("Child shoes","15","This is best for children",R.drawable.babe_child_shoes,0012));

           saveProductData(SplashActivity.this,productArrayList);
              setAppStatus(SplashActivity.this,true);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();









    }
}