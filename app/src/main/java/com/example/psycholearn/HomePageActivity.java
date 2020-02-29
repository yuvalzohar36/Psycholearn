package com.example.psycholearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class HomePageActivity extends AppCompatActivity {
    TextView WelcomeUser;
    TextView CurrentlyTime;
    Button HardListBtn, FlashCardsBtn, SortingWordsBtn, LearningWordsBtn;
    DatabaseReference reff;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        WelcomeUser = (TextView) findViewById(R.id.welcometv);
        CurrentlyTime = (TextView) findViewById(R.id.datetimetv);
        firebaseAuth = FirebaseAuth.getInstance();
        String User_ID = firebaseAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("FullName");
        HardListBtn = (Button) findViewById(R.id.hardListBtn);
        FlashCardsBtn = (Button) findViewById(R.id.flashcardsBtn);
        SortingWordsBtn = (Button) findViewById(R.id.sortingwordsBtn);
        LearningWordsBtn = (Button)findViewById(R.id.LearningActivityMoveBtn);
        show_time();
        welcome_User_Func();
        handle_Buttons();
    }

    public void show_time() {
        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        CurrentlyTime.setText(currentDateTimeString);
    }

    public void welcome_User_Func() {
        reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String FullNameString = dataSnapshot.getValue().toString();
                WelcomeUser.setText("Welcome " + FullNameString);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void handle_Buttons() {
        HardListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, HardListActivity.class));
            }
        });
        FlashCardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, MainActivity.class));
            }
        });
        SortingWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, LearnWordsActivity.class));
            }
        });
        LearningWordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomePageActivity.this, LearningActivity.class));
            }
        });
    }
}


