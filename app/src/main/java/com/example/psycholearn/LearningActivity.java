package com.example.psycholearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LearningActivity extends AppCompatActivity {
RadioGroup radioGroup;
RadioButton radioButton;
DatabaseReference reff_two;
ListView LearningListview;
FirebaseAuth firebaseAuth;
ArrayList<String> ArrayListKnow = new ArrayList<String>();
ArrayList<String> ArrayListDontKnow = new ArrayList<String>();
ArrayList<String> ArrayListAll = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        LearningListview = (ListView)findViewById(R.id.LearningListView);
        String User_ID = firebaseAuth.getCurrentUser().getUid();
        reff_two = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID);
        handle_RadioGroup();
    }
    public void handle_RadioGroup(){

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                final int index = radioGroup.indexOfChild(radioButton);
                switch (index) {
                    case 0:
                                ArrayListDontKnow.clear();
                                ArrayListAll.clear();
                                reff_two.child("Sorting Words").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot data) {
                                        for (final DataSnapshot snapshot : data.getChildren()) {
                                            //System.out.println(snapshot.child("english_Word").getValue());
                                            reff_two.child("Sorting Words").child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot data) {
                                                    for (final DataSnapshot snapshot2 : data.getChildren()) {
                                                       String EnglishWord = snapshot2.child("english_Word").getValue().toString();
                                                       String HebrewWord = snapshot2.child("hebrew_Word").getValue().toString();
                                                       String State = snapshot2.child("state").getValue().toString();
                                                       if (State.equals("Green")){
                                                        ArrayListKnow.add(EnglishWord + "        " + HebrewWord);
                                                       }
                                                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                                                (LearningActivity.this, android.R.layout.simple_list_item_1, ArrayListKnow);
                                                        LearningListview.setAdapter(arrayAdapter);
                                                        arrayAdapter.notifyDataSetChanged();
                                                    }
                                        }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                                }
                                            });}}
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                        break;
                    case 1:
                        ArrayListKnow.clear();
                        ArrayListAll.clear();
                        reff_two.child("Sorting Words").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {
                                for (final DataSnapshot snapshot : data.getChildren()) {
                                    //System.out.println(snapshot.child("english_Word").getValue());
                                    reff_two.child("Sorting Words").child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot data) {
                                            for (final DataSnapshot snapshot2 : data.getChildren()) {
                                                String EnglishWord = snapshot2.child("english_Word").getValue().toString();
                                                String HebrewWord = snapshot2.child("hebrew_Word").getValue().toString();
                                                String State = snapshot2.child("state").getValue().toString();
                                                if (State.equals("Red")){
                                                    ArrayListDontKnow.add(EnglishWord + "        " + HebrewWord);
                                                }
                                                System.out.println(ArrayListDontKnow);
                                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                                        (LearningActivity.this, android.R.layout.simple_list_item_1, ArrayListDontKnow);
                                                LearningListview.setAdapter(arrayAdapter);

                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });}}
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break;
                    case 2:
                        ArrayListKnow.clear();
                        ArrayListDontKnow.clear();
                        reff_two.child("Sorting Words").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot data) {
                                for (final DataSnapshot snapshot : data.getChildren()) {
                                    reff_two.child("Sorting Words").child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot data) {
                                            for (final DataSnapshot snapshot2 : data.getChildren()) {
                                                String EnglishWord = snapshot2.child("english_Word").getValue().toString();
                                                String HebrewWord = snapshot2.child("hebrew_Word").getValue().toString();
                                                String State = snapshot2.child("state").getValue().toString();
                                                ArrayListAll.add(EnglishWord + "       " + HebrewWord);
                                                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                                        (LearningActivity.this, android.R.layout.simple_list_item_1, ArrayListAll);
                                                LearningListview.setAdapter(arrayAdapter);

                                                arrayAdapter.notifyDataSetChanged();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });}}
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        break; }
            }
        }); }
}
