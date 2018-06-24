package com.example.ch.snip_it.users;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ch.snip_it.R;
import com.example.ch.snip_it.core.SimpleCallback;
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
    Boolean callCheck = false;
    String userString = "user";

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        queryUserData(new SimpleCallback<Boolean>() {
            @Override
            public void callback(Boolean data) {
                if(data) {
                    callCheck = true;
                }
                else {

                }
            }
        }, getIntent().getStringExtra("USER_ID"));
        if (callCheck){
            TextView usernameSetText = (TextView) findViewById(R.id.userHomePageText);
            usernameSetText.setText("Welcome back to Snip-it, " + userString);
            setContentView(R.layout.user_homepage);
            callCheck = false;
        }
    }

    public void queryUserData(@NonNull final SimpleCallback<Boolean> callback, final String userId) {
        DatabaseReference databaseReference = database.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    //String stuff = dataSnapshot1.child("id").getValue().toString();
                    userString = dataSnapshot1.toString();
                    System.out.println();
                }
                //User user = dataSnapshot.getValue(User.class);
                callback.callback(dataSnapshot.hasChild("id"));
                System.out.println(callCheck);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //DatabaseReference ref = database.getReference("Snip-it-user-data").child("users").child("thenewkhurl");
//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        Query query = rootRef.orderByChild("id").equalTo(userId);
//        //Query q = ref.child("users").orderByChild("id").equalTo(userId);
//        query.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        // for example: if you're expecting your user's data as an object of the "User" class.
////                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
////                            String stuff = dataSnapshot1.child("id").getValue().toString();
////                        }
//                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                            String stuff = dataSnapshot1.child("id").getValue().toString();
//                            System.out.println();
//                        }
//                        User user = dataSnapshot.getValue(User.class);
//                        userString = dataSnapshot.toString();
//                        callback.callback(dataSnapshot.hasChild("id"));
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        // read query is cancelled.
//                    }
//                });
    }
}
