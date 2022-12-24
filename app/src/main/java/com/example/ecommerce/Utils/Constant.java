package com.example.ecommerce.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ecommerce.Model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Constant {
    public static boolean getAppStatus(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("install", false);
    }

    public static void setAppStatus(Context context , boolean s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("install", s).commit();
    }
    public static boolean getUserLoginStatus(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean("locked", false);
    }

    public static void setUserLoginStatus(Context context , boolean s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean("locked", s).commit();
    }
    public static String getUsername(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("name","");
    }

    public static void setUsername(Context context , String s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("name", s).commit();
    }
    public static String getUserId(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("id","");
    }

    public static void setUserId(Context context , String s){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString("id", s).commit();
    }



    public static void saveCartData(Context context, ArrayList<Product> bookingArrayList) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // creating a new variable for gson.
        Gson gson = new Gson();
        // getting data from gson and storing it in a string.
        String json = gson.toJson(bookingArrayList);
        prefs.edit().putString("cartData", json).commit();
    }
    public static ArrayList<Product> getCartData(Context context) {
        ArrayList<Product> bookingArrayList=null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json =  prefs.getString("cartData",null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        bookingArrayList = gson.fromJson(json, type);
        if(bookingArrayList==null){
            bookingArrayList=new ArrayList<Product>();
        }
        return bookingArrayList;
    }



    public static void ShowMessageDialogwithOkBtn(Context context, String message,
                                                  DialogInterface.OnClickListener positiveBtnListner){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",positiveBtnListner);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    public static void saveProductData(Context context, ArrayList<Product> bookingArrayList) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        // creating a new variable for gson.
        Gson gson = new Gson();
        // getting data from gson and storing it in a string.
        String json = gson.toJson(bookingArrayList);
        prefs.edit().putString("product", json).commit();
    }
    public static ArrayList<Product> getProductData(Context context) {
        ArrayList<Product> bookingArrayList=null;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json =  prefs.getString("product",null);
        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
        bookingArrayList = gson.fromJson(json, type);
        if(bookingArrayList==null){
            bookingArrayList=new ArrayList<Product>();
        }
        return bookingArrayList;
    }
}
