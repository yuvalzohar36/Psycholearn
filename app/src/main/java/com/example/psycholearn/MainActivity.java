package com.example.psycholearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    DatabaseReference reff_2;
    DatabaseReference newreff;
    DatabaseReference reffHardList;
    DatabaseReference Sum_Of_HardList;
    DatabaseReference Currently_Pos;
    Newword word;
    TextView English_TV;
    TextView Hebrew_TV;
    Button Translation;
    Button NextBtn;
    ImageButton HardListButton;
    static ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    Integer i = 0 ;
    static Integer HardListCounter = 0;
    Button BackBtn;
    List<String> englishlist = new ArrayList<String>();
    List<String> hebrewlist = new ArrayList<String>();
    List<String> Word_List = new ArrayList<String>();
    HashMap<String, String> Words = new HashMap<String, String>();
    private String User_ID;
    Map<String, String> words = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        String User_ID = firebaseAuth.getCurrentUser().getUid();
        Currently_Pos = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("Words");
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("Words");
        reff_2 = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID);
        reffHardList = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("HardList");
        Get_First_i_Value();
        newreff = FirebaseDatabase.getInstance().getReference().child("Words").child(String.valueOf(i));
        English_TV = (TextView)findViewById(R.id.English_TV);
        Hebrew_TV = (TextView)findViewById(R.id.Hebrew_TV);
        Translation = (Button)findViewById(R.id.translate);
        word = new Newword();
        HardListButton = (ImageButton)findViewById(R.id.hardlistbtn);
        NextBtn = (Button)findViewById(R.id.next);
        BackBtn = (Button)findViewById(R.id.back);
        Translation_Func();
        ReadFileHebrew();
        ReadFileEnglish();
        Add_To_Hard_List();
        Load_All_Words();
        Get_Data();
        nextorbackword();
    }
    public void Load_All_Words() {

                    for (int i = 0; i < englishlist.size(); i++) {
                       word.setEnglish_Word(englishlist.get(i));
                      word.setHebrew_Word(hebrewlist.get(i));
                     reff.child(String.valueOf(i)).setValue(word);
            }}
    public void ReadFileEnglish() {
        String text = "";
        try {
            InputStream is = getAssets().open("english.txt");
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                englishlist.add(line);
            }
            in.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Exception", Toast.LENGTH_LONG).show();
        }
    }
    public void ReadFileHebrew() {
        String text = "";
        try {
            InputStream ip = getAssets().open("hebrew.txt");
            DataInputStream in = new DataInputStream(ip);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                hebrewlist.add(line);
            }
            ip.close();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Exception", Toast.LENGTH_LONG).show();
        }
    }
    public void Get_Data() {
        Currently_Pos.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String English_Word = dataSnapshot.child("english_Word").getValue().toString();
                String Hebrew_Word = dataSnapshot.child("hebrew_Word").getValue().toString();
                English_TV.setText(English_Word);
                Hebrew_TV.setVisibility(View.INVISIBLE);
                Hebrew_TV.setText(Hebrew_Word);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }}); }
    public void Translation_Func(){
        Translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hebrew_TV.setVisibility(View.VISIBLE);
            }
        });
    }
    public void nextorbackword(){
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i+=1;
                if (englishlist.size() > i && i >= 0) {
                    Get_Data();
                    reff.child("Currently").setValue(i);
                }else {(Toast.makeText(MainActivity.this,"End of list",Toast.LENGTH_SHORT)).show();
                    i-=1;}}

        });
        BackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i-=1;
                if (englishlist.size() > i && i >= 0) {
                    Get_Data();
                    reff.child("Currently").setValue(i);

                }
                else{(Toast.makeText(MainActivity.this,"End of list",Toast.LENGTH_SHORT)).show();
                    i+=1;}
            }});}
    public void Get_First_i_Value() {
        Currently_Pos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Value_I = dataSnapshot.child("Currently").getValue().toString();
                System.out.println(Value_I);
                i = Integer.parseInt(Value_I);
                Get_Data();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    public void Add_To_Hard_List(){
        HardListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Currently_Pos.child(String.valueOf(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String English_Word = dataSnapshot.child("english_Word").getValue().toString();
                        String Hebrew_Word = dataSnapshot.child("hebrew_Word").getValue().toString();
                        word.setEnglish_Word(English_Word);
                        word.setHebrew_Word(Hebrew_Word);
                        reffHardList.child(English_Word).setValue(word);
                        Toast.makeText(MainActivity.this,"מילה התווספה",Toast.LENGTH_SHORT).show();
                        arrayList.add(English_Word + "              " + Hebrew_Word);
                        }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }}); }










        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.hardlist: {
                startActivity(new Intent(MainActivity.this, HardListActivity.class));
                return true;
            }
            case R.id.homepagemove: {
                startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                return true;
            }
            case R.id.logout: {
                firebaseAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}




