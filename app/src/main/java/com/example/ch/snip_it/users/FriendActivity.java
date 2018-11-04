package com.example.ch.snip_it.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.ch.snip_it.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for performing friend list operations
 */
public class FriendActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ListView friendView;

    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Intent friendIntent = getIntent();
        List<String> friendsList = friendIntent.getStringArrayListExtra("FRIENDS_LIST");
        queryFriends(friendsList);
    }

    public void queryFriends(final List<String> friendsList) {
        final List<String> friendsUserNames = new ArrayList<>();
        DatabaseReference databaseReference = database.getReference("Snip-it-user-data/users");
        databaseReference.orderByChild("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userDataSnapshot: dataSnapshot.getChildren()){
                    if (friendsList.contains(userDataSnapshot.child("id").getValue().toString())){
                        String userString = userDataSnapshot.child("username").getValue().toString();
                        friendsUserNames.add(userString);
                    }
                }
                // After querying to find current user's friends, output the friends to the user. If no friends found, output that none were found
                setContentView(R.layout.friends_page);
                if (friendsUserNames.size() > 0) {
                    friendView = (ListView) findViewById(R.id.friendList);
                    ArrayAdapter<String> friendAdapter = new ArrayAdapter<String>(
                            getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            friendsUserNames);
                    friendView.setAdapter(friendAdapter);
                }
                else {
                    // handle this
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
