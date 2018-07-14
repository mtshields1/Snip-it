package com.example.ch.snip_it.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ch.snip_it.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The user home page. The user will be redirected here
 * after making a username or logging back in
 */
public class UserHomePage extends AppCompatActivity
{
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userString = "user";
    int sent = 1;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        queryUserData(getIntent().getStringExtra("USER_ID"), getIntent().getStringExtra("DB_KEY"));
    }

    public void queryUserData(final String userId, final String dbKey) {
        DatabaseReference databaseReference = database.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    userString = dataSnapshot1.child("users").child(dbKey).child("username").getValue().toString();
                    setContentView(R.layout.user_homepage);
                    TextView usernameSetText = (TextView) findViewById(R.id.userHomePageText);
                    usernameSetText.setText("Welcome back to Snip-it, " + userString);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
