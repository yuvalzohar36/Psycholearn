package com.example.psycholearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SortingWordsActivity extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference reff_two;
    private FirebaseAuth firebaseAuth;
    ListView sortwordlistview;
    NewSortWord CreateNewWord;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> StateList = new ArrayList<>();
    ArrayList<String> EnglishList = new ArrayList<>();
    ArrayList<String> MainList = new ArrayList<String>();
    char Letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorting_words);
        firebaseAuth = FirebaseAuth.getInstance();
        String User_ID = firebaseAuth.getCurrentUser().getUid();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("Words");
        reff_two = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID);
        CreateNewWord = new NewSortWord();
        sortwordlistview = (ListView) findViewById(R.id.SortingWords);
        insert_All_Words_By_Sorting();
        show_all();
    }


    public void insert_All_Words_By_Sorting() {

        reff_two.child("Inserted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String IsInserted = dataSnapshot.getValue().toString().trim();
                System.out.println(IsInserted);
                reff.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (!snapshot.getKey().equals("Currently") && IsInserted.equals("false")) {
                                String English_Word = snapshot.child("english_Word").getValue().toString();
                                String Hebrew_Word = snapshot.child("hebrew_Word").getValue().toString();
                                CreateNewWord.setEnglish_Word(English_Word);
                                CreateNewWord.setHebrew_Word(Hebrew_Word);
                                CreateNewWord.setState("Empty");
                                Letter = English_Word.charAt(0);
                                if (Letter == 'a' || Letter == 'b' || Letter == 'c') {
                                    reff_two.child("Sorting Words").child(String.valueOf("A-C")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'd' || Letter == 'e') {
                                    reff_two.child("Sorting Words").child(String.valueOf("D-E")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'f' || Letter == 'g' || Letter == 'h' || Letter == 'i') {
                                    reff_two.child("Sorting Words").child(String.valueOf("F-I")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'j' || Letter == 'k' || Letter == 'l' || Letter == 'm') {
                                    reff_two.child("Sorting Words").child(String.valueOf("J-M")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'n' || Letter == 'o' || Letter == 'p' || Letter == 'q') {
                                    reff_two.child("Sorting Words").child(String.valueOf("N-Q")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'r' || Letter == 's' || Letter == 't') {
                                    reff_two.child("Sorting Words").child(String.valueOf("R-T")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'u' || Letter == 'v') {
                                    reff_two.child("Sorting Words").child(String.valueOf("U-V")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'x' || Letter == 'w') {
                                    reff_two.child("Sorting Words").child(String.valueOf("X-W")).child(English_Word).setValue(CreateNewWord);
                                }
                                if (Letter == 'y' || Letter == 'z') {
                                    reff_two.child("Sorting Words").child(String.valueOf("Y-Z")).child(English_Word).setValue(CreateNewWord);
                                }
                            }
                        }
                        reff_two.child("Inserted").setValue("true");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void show_all() {
        reff_two.child("WhichNow").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String WhichNow = dataSnapshot.getValue().toString().trim();
                System.out.println(WhichNow);
                reff_two.child("Sorting Words").child(WhichNow).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot data) {
                        for (final DataSnapshot snapshot : data.getChildren()) {
                            final String English = snapshot.child("english_Word").getValue().toString();
                            String Hebrew = snapshot.child("hebrew_Word").getValue().toString();
                            final String Current_State = snapshot.child("state").getValue().toString();
                            // TODO need create a text view every time, add it to ListViewList, and then run the arrayAdapter from the ListViewList.
                            // TODO after that cntorol all the backgroud row color.
                            // TODO Do not clear anything, Copy,Paste , all ! ! ! !!
                            TextView t = new TextView(SortingWordsActivity.this);
                            t.setText(English + "      " + Hebrew);
                            StateList.add(Current_State);
                            EnglishList.add(English);
                            MainList.add(t.getText().toString());
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                                    (SortingWordsActivity.this, android.R.layout.simple_list_item_1, MainList) {
                                @Override
                                public View getView(final int position, View convertView, ViewGroup parent) {
                                    final View view = super.getView(position, convertView, parent);
                                    if (StateList.get(position).toString().trim().equals("Empty")) {
                                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    }
                                    if (StateList.get(position).toString().trim().equals("Red")) {
                                        view.setBackgroundColor(Color.parseColor("#FF0000"));
                                    }
                                    if (StateList.get(position).toString().trim().equals("Green")) {
                                        view.setBackgroundColor(Color.parseColor("#7FFF00"));
                                    }
                                    if (StateList.get(position).toString().trim().equals("Green")) {
                                        view.setBackgroundColor(Color.parseColor("#7FFF00"));
                                    }
                                    return view;
                                }
                            };
                            sortwordlistview.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                            sortwordlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> arg0, View arg1,
                                                        int pos, long id) {
                                    // TODO Auto-generated method stub
                                    if (!StateList.get(pos).toString().trim().equals("Green")) {
                                        reff_two.child("Sorting Words").child(WhichNow).child(EnglishList.get(pos)).child("state").setValue("Green");
                                        StateList.set(pos, "Green");
                                    } else {
                                        reff_two.child("Sorting Words").child(WhichNow).child(EnglishList.get(pos)).child("state").setValue("Red");
                                        StateList.set(pos, "Red");
                                    }
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}

