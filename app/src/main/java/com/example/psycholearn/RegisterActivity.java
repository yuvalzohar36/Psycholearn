package com.example.psycholearn;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

// the registertion page
public class RegisterActivity extends AppCompatActivity {
    private EditText User, Passw, repass,Fullname;
    private Button regButton;
    private Button LoginBtn;
    private FirebaseAuth firebaseAuth;
    MainActivity mainactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();
        firebaseAuth = FirebaseAuth.getInstance();
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String user_email = User.getText().toString().trim();
                    String user_password = Passw.getText().toString().trim();
                    String Repassword = repass.getText().toString().trim();
                    final String fullname = Fullname.getText().toString().trim();
                    if (Repassword.equals(user_password)){
                        firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String User_ID = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_id = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID);
                                    current_user_id.child("Words").child("Currently").setValue("0");
                                    current_user_id.child("FullName").setValue(fullname);
                                    current_user_id.child("Inserted").setValue("false");
                                    Toast.makeText(RegisterActivity.this, "הרשמה הושלמה", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                } else {
                                    Toast.makeText(RegisterActivity.this, "הרשמה נכשלה", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "הסיסמה לא תואמת", Toast.LENGTH_SHORT).show();
                    }
                }}})
        ;
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });
    }
    private void setupUIViews() {
        User = (EditText) findViewById(R.id.UNtextedit);
        Passw = (EditText) findViewById(R.id.PWtextedit);
        regButton = (Button) findViewById(R.id.RegButton);
        LoginBtn = (Button) findViewById(R.id.movetologin);
        repass = (EditText) findViewById(R.id.againPStextedit);
        Fullname = (EditText)findViewById(R.id.fullnameplaintext);
    }
    private Boolean validate() {
        Boolean result = false;
        String username = User.getText().toString();
        String password = Passw.getText().toString();
        String repassword = repass.getText().toString();
        if (username.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "פרטים חסרים", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }
}

