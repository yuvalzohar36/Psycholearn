package com.example.psycholearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LearnWordsActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;
    private Button ACBtn;
    private Button DEBtn;
    private Button FIBtn;
    private Button JMBtn;
    private Button NQBtn;
    private Button RTBtn;
    private Button UVBtn;
    private Button WXBtn;
    private Button YZBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_words);
         firebaseAuth = FirebaseAuth.getInstance();
         String User_ID = firebaseAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID);
        //def all Buttons --->
        ACBtn = (Button) findViewById(R.id.AuntilCBtn);
        DEBtn = (Button) findViewById(R.id.DuntilEBtn);
        FIBtn = (Button) findViewById(R.id.FuntilIBtn);
        JMBtn = (Button) findViewById(R.id.JuntilMBtn);
        NQBtn = (Button) findViewById(R.id.NuntilQBtn);
        RTBtn = (Button) findViewById(R.id.RuntilTBtn);
        UVBtn = (Button) findViewById(R.id.UuntilVBtn);
        WXBtn = (Button) findViewById(R.id.WuntilXBtn);
        YZBtn = (Button) findViewById(R.id.YuntilZBtn);
        ACBtn.setOnClickListener(this);
        DEBtn.setOnClickListener(this);
        FIBtn.setOnClickListener(this);
        JMBtn.setOnClickListener(this);
        NQBtn.setOnClickListener(this);
        RTBtn.setOnClickListener(this);
        UVBtn.setOnClickListener(this);
        WXBtn.setOnClickListener(this);
        YZBtn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.AuntilCBtn:
                reff.child("WhichNow").setValue("A-C");
                startActivity(new Intent(LearnWordsActivity.this,SortingWordsActivity.class));
                break;
            case R.id.DuntilEBtn:
                reff.child("WhichNow").setValue("D-E");
                break;
            case R.id.FuntilIBtn:
                reff.child("WhichNow").setValue("F-I");
                break;
            case R.id.JuntilMBtn:
                reff.child("WhichNow").setValue("J-M");
                break;
            case R.id.NuntilQBtn:
                reff.child("WhichNow").setValue("N-Q");
                break;
            case R.id.RuntilTBtn:
                reff.child("WhichNow").setValue("R-T");
                break;
            case R.id.UuntilVBtn:
                reff.child("WhichNow").setValue("U-V");
                break;
            case R.id.WuntilXBtn:
                reff.child("WhichNow").setValue("W-X");
                break;
            case R.id.YuntilZBtn:
                reff.child("WhichNow").setValue("Y-Z");
                break;

        }
    }
}