package com.example.psycholearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class HardListActivity extends AppCompatActivity {
    ListView listViewHard;
    ArrayList<String> EnglishWordsList = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference HardList_Reff;
    public static Integer HardListCounter = 0;
    final Integer Counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_list);
        firebaseAuth = FirebaseAuth.getInstance();
        String User_ID = firebaseAuth.getCurrentUser().getUid();
        ArrayList<String> arrayList = new ArrayList<>();
        HardList_Reff = FirebaseDatabase.getInstance().getReference().child("Users").child(User_ID).child("HardList");
        listViewHard = (ListView) findViewById(R.id.HardListView);
        Load_All();
    }

    public void Load_All(){
        HardList_Reff.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String English_Word = snapshot.child("english_Word").getValue().toString();
                    String Hebrew_Word = snapshot.child("hebrew_Word").getValue().toString();
                    arrayList.add(English_Word + "       "+Hebrew_Word);
                    EnglishWordsList.add(English_Word);
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HardListActivity.this, android.R.layout.simple_list_item_1, arrayList);
                    listViewHard.setAdapter(arrayAdapter);
                    listViewHard.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                       int pos, long id) {
                            // TODO Auto-generated method stub
                            arrayList.remove(pos);
                            arrayAdapter.notifyDataSetChanged();
                            HardList_Reff.child(EnglishWordsList.get(pos)).removeValue();
                            EnglishWordsList.remove(pos);
                            Log.v("long clicked","pos: " + pos);
                            return true;
                        }
                    });
}}
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            };
    });}
}





