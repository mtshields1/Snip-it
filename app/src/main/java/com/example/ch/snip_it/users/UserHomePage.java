package com.example.ch.snip_it.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ch.snip_it.MainActivity;
import com.example.ch.snip_it.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The user home page. The user will be redirected here
 * after making a username or logging back in
 */
public class UserHomePage extends AppCompatActivity
{
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userString = "user";
    List<String> friendsList = new ArrayList<>();
    int sent = 1;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        queryUserData(getIntent().getStringExtra("USER_ID"));
    }

    public void queryUserData(final String userId) {
        DatabaseReference databaseReference = database.getReference("Snip-it-user-data/users");
        databaseReference.orderByChild("id").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot: dataSnapshot.getChildren()){
                    userString = userDataSnapshot.child("username").getValue().toString();
                    friendsList = (List)userDataSnapshot.child("friendsList").getValue();
                    setContentView(R.layout.user_homepage);
                    TextView usernameSetText = (TextView) findViewById(R.id.userHomePageText);
                    usernameSetText.setText("Welcome back to Snip-it, " + userString);
                }
                setHomePageButtonListeners(friendsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setHomePageButtonListeners(final List<String> friendsList) {
        ImageButton friendButton = findViewById(R.id.friendsButton);
        friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent friendIntent = new Intent(UserHomePage.this, FriendActivity.class);
                friendIntent.putStringArrayListExtra("FRIENDS_LIST", (ArrayList)friendsList);
                UserHomePage.this.startActivity(friendIntent);
            }
        });
    }
}
