package com.example.ecommerce.Screens;

import static com.example.ecommerce.Utils.Constant.setUserId;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.ecommerce.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog datePicker;
    EditText et_dateOfBirth;
    private FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
       RadioButton btnRadioMale,btnRadioFemale,btnRadioOther;
    private Dialog loadingDialog;
    private EditText etRegisterEmail,et_user_name, etRegisterPassword, etRegisterConfirmPassword,et_name,et_user_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRadioOther=findViewById(R.id.btnRadioOther);
        btnRadioFemale=findViewById(R.id.btnRadioFemale);
        btnRadioMale=findViewById(R.id.btnRadioMale);

        et_dateOfBirth=findViewById(R.id.et_dateOfBirth);
        firebaseAuth = FirebaseAuth.getInstance();

        et_user_number=findViewById(R.id.et_user_number);
        et_name=findViewById(R.id.et_name);
        /////loading dialog
        loadingDialog=new Dialog(this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        firebaseAuth = FirebaseAuth.getInstance();
        etRegisterEmail = findViewById(R.id.et_register_email);
        etRegisterPassword = findViewById(R.id.et_register_password);
        etRegisterConfirmPassword = findViewById(R.id.et_register_confirm_password);
        et_user_name = findViewById(R.id.et_user_name);
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
                datePicker =  new DatePickerDialog(RegisterActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
                datePicker.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePicker.show();
            }
        });
    }

    public void register(View view) {
        String email = etRegisterEmail.getText().toString();
        String user_name = et_user_name.getText().toString();
        String password = etRegisterPassword.getText().toString();
        String confirm_password = etRegisterConfirmPassword.getText().toString();
        String user_number =et_user_number.getText().toString();
        String _name =et_name.getText().toString();
        String _dateOfBirth =et_dateOfBirth.getText().toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (validate(email,user_name, password, confirm_password,user_number,_name,_dateOfBirth)) requestRegister(email, password);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean validate(String email, String name, String password, String confirm_password, String user_number, String _name,String _dateOfBirth) {
        if (email.isEmpty()) etRegisterEmail.setError("Enter email!");
        else if (_dateOfBirth.isEmpty()) et_dateOfBirth.setError("Enter dateofbirth!");
        else if (_name.isEmpty()) et_name.setError("Required!");
        else if (user_number.isEmpty()) et_user_number.setError("Enter phone number!");
        else if (name.isEmpty()) et_user_name.setError("Enter user name!");
        else if (!email.contains("@")||!email.contains(".")) etRegisterEmail.setError("Enter valid email!");
        else if (password.isEmpty()) etRegisterPassword.setError("Enter password!");
        else if (password.length()<6) etRegisterPassword.setError("Password must be at least 6 characters!");
        else if (confirm_password.isEmpty()) etRegisterConfirmPassword.setError("Enter password!");
        else if (!password.equals(confirm_password)) etRegisterConfirmPassword.setError("Password not matched!");
        else return true;
        return false;
    }

    private void requestRegister(String email, String password) {
        loadingDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getCreateUserWithEmailOnClickListener(email));
    }
    private OnCompleteListener<AuthResult> getCreateUserWithEmailOnClickListener(String email) {
        return task -> {
            if (task.isSuccessful()) {
                add();
            } else {
                loadingDialog.dismiss();
                Toast.makeText(RegisterActivity.this,"Registration failed!",Toast.LENGTH_LONG).show();

            }
        };
    }
    public void add(){
        String id = firebaseAuth.getCurrentUser().getUid();
        myRef=  FirebaseDatabase.getInstance().getReference("User").child(id);
        myRef.child("UserName").setValue(et_user_name.getText().toString());
        myRef.child("UserId").setValue(id);
        myRef.child("Mail").setValue(etRegisterEmail.getText().toString());
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
        setUserId(this,id);
        startActivity(new Intent(this,AddCardActivity.class));
        finish();
    }

}