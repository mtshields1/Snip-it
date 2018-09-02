package com.example.ch.snip_it.users;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ch.snip_it.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Activity to create a new user for snip it
 */
public class CreateUserActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.new_user);

        Button userButtonListener = findViewById(R.id.makeUserButton);
        userButtonListener.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameText = (EditText)findViewById(R.id.userNameInput);
                EditText passwordText = (EditText)findViewById(R.id.passwordText);
                // Get Firebase Authentication instance
                String dbKey = createSnipItUser(usernameText.getText().toString(), passwordText.getText().toString(), getIntent().getStringExtra("USER_EMAIL"),
                        getIntent().getStringExtra("USER_ID"));
                Intent output = new Intent();
                output.putExtra("dbKey", dbKey);
                setResult(RESULT_OK, output);
                finish();
            }
        }));
    }

    public String createSnipItUser(String username, String password, String email, String id)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Snip-it-user-data");
        DatabaseReference usersRef = ref.child("users");
        String dbKey = usersRef.push().getKey();

        usersRef.setValue(new User(username, password, email, id, dbKey, new ArrayList<String>()));
        return dbKey;
    }
}
