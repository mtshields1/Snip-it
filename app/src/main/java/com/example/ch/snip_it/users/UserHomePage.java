package com.example.ch.snip_it.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ch.snip_it.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * The user home page. The user will be redirected here
 * after making a username or logging back in
 */
public class UserHomePage extends AppCompatActivity
{
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        User user = getUserData(getIntent().getStringExtra("USER_ID"));
        TextView usernameSetText = (TextView) findViewById(R.id.userHomePageText);
        usernameSetText.setText("Welcome back to Snip-it, " + user.username);
        setContentView(R.layout.user_homepage);

    }

    public User getUserData(final String userId) {
        DatabaseReference ref = database.getReference("Snip-it-user-data");
        Query q = FirebaseDatabase.getInstance().getReference().child("users").equalTo(userId);
        q.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // for example: if you're expecting your user's data as an object of the "User" class.
                        User user = dataSnapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // read query is cancelled.
                    }
                });
        return null;
    }
}
