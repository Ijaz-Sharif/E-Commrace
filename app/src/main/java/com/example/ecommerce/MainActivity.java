package com.example.ecommerce;

import static com.example.ecommerce.Utils.Constant.ShowMessageDialogwithOkBtn;
import static com.example.ecommerce.Utils.Constant.getUserLoginStatus;
import static com.example.ecommerce.Utils.Constant.setUserLoginStatus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ecommerce.Fragments.MainFragment;
import com.example.ecommerce.Fragments.UserProfileFragment;
import com.example.ecommerce.Screens.CartActivity;
import com.example.ecommerce.Screens.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView=findViewById(R.id.bottomNavigationView);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag,new MainFragment()).commit();
        navigationView.setOnNavigationItemSelectedListener(navlis);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlis=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.homeScreen:
                            getSupportFragmentManager().beginTransaction().replace(R.id.frag,new MainFragment()).commit();
                            break;
                        case R.id.profileScreen:
                               if(getUserLoginStatus(MainActivity.this)){
                                   getSupportFragmentManager().beginTransaction().replace(R.id.frag,new UserProfileFragment()).commit();
                               }
                               else {
                                   ShowMessageDialogwithOkBtn(MainActivity.this, "You must login first ", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                       }
                                   });

                               }

                            break;
                    }
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.logout);
        if(!getUserLoginStatus(MainActivity.this)){
            item.setVisible(false);//
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.cart:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            case R.id.logout:
                setUserLoginStatus(MainActivity.this,false);
                Toast.makeText(MainActivity.this,"you are logout",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}