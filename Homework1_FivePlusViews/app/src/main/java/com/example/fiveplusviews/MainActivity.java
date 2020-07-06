package com.example.fiveplusviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // User Name Box
        final EditText username_box = findViewById(R.id.user_name);

        // Password Box
        final EditText password_box = findViewById(R.id.password);

        // Show/Hide Password Checkbox
        CheckBox cb = findViewById(R.id.cb_password);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean isChecked) {
                if(isChecked) {
                    Log.i("CheckBox", "Change Status to 'show password'");
                    ((CheckBox) view).setText(R.string.cb_hide_password);
                    password_box.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else {
                    Log.i("CheckBox", "Change Status to 'hide password'");
                    ((CheckBox) view).setText(R.string.cb_show_password);
                    password_box.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        // Summit Button
        Button btn = findViewById(R.id.button_send);
        final TextView tv = findViewById(R.id.info);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username_box.getText().toString().trim().isEmpty()) {
                    Log.i("Username EditText", "Block Empty Username");
                    tv.setText(R.string.empty_username_info);
                }
                else if(password_box.getText().toString().trim().isEmpty()) {
                    Log.i("Username EditText", "Block Empty Password");
                    tv.setText(R.string.empty_password_info);
                }
                else {
                    Log.i("Username EditText", "Succesfully Summit");
                    tv.setText(R.string.login_successful_info);
                }
            }
        });
    }
}