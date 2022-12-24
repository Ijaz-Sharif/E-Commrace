package com.example.ecommerce.Fragments;

import static com.example.ecommerce.Utils.Constant.getUserId;
import static com.example.ecommerce.Utils.Constant.setUserId;
import static com.example.ecommerce.Utils.Constant.setUserLoginStatus;
import static com.example.ecommerce.Utils.Constant.setUsername;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.ecommerce.R;
import com.example.ecommerce.Screens.LoginActivity;
import com.example.ecommerce.Screens.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class UserProfileFragment extends Fragment {


    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog datePicker;
    EditText et_dateOfBirth;
    RadioButton btnRadioMale,btnRadioFemale,btnRadioOther;
    private Dialog loadingDialog;
    private EditText et_user_name,et_name,et_user_number;
    Button btn_update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        btnRadioOther=view.findViewById(R.id.btnRadioOther);
        btnRadioFemale=view.findViewById(R.id.btnRadioFemale);
        btnRadioMale=view.findViewById(R.id.btnRadioMale);

        et_dateOfBirth=view.findViewById(R.id.et_dateOfBirth);

        et_user_number=view.findViewById(R.id.et_user_number);
        et_name=view.findViewById(R.id.et_name);
        et_user_name = view.findViewById(R.id.et_user_name);
        /////loading dialog
        loadingDialog=new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);



        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                String myFormat="MM/dd/yyyy";
                SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                et_dateOfBirth.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        et_dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View view) {
                datePicker =  new DatePickerDialog(getContext(),date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePicker.show();
            }
        });
        btn_update=view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference("User").child(getUserId(getContext()));
                myRef.child("UserName").setValue(et_user_name.getText().toString());
                myRef.child("DateOfBirth").setValue(et_dateOfBirth.getText().toString());
                myRef.child("PhoneNumber").setValue(et_user_number.getText().toString());
                myRef.child("Name").setValue(et_name.getText().toString());
                if(btnRadioFemale.isChecked()){
                    myRef.child("Gender").setValue("Female");
                }
                if(btnRadioMale.isChecked()){
                    myRef.child("Gender").setValue("Male");
                }
                if(btnRadioOther.isChecked()){
                    myRef.child("Gender").setValue("Other");
                }
                loadingDialog.dismiss();
            }
        });
        loadProfile();
        return view;
    }

    public void loadProfile(){
        loadingDialog.show();
        DatabaseReference myRef=  FirebaseDatabase.getInstance().getReference().child("User").child(getUserId(getContext()));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                et_user_name.setText(dataSnapshot.child("UserName").getValue(String.class));
                et_name.setText(dataSnapshot.child("Name").getValue(String.class));
                et_dateOfBirth.setText(dataSnapshot.child("DateOfBirth").getValue(String.class));
                et_user_number.setText(dataSnapshot.child("PhoneNumber").getValue(String.class));
                loadingDialog.dismiss();
                if(dataSnapshot.child("Gender").getValue(String.class).equals("Female")){
                    btnRadioFemale.setChecked(true);
                }
                else if(dataSnapshot.child("Gender").getValue(String.class).equals("Male")){
                    btnRadioMale.setChecked(true);
                }
                else {
                    btnRadioOther.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}