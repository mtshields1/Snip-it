package com.example.ch.snip_it.users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ch.snip_it.R;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owner on 5/20/2018.
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
                createSnipItUser(usernameText.getText().toString(), passwordText.getText().toString());
                finish();
            }
        }));
    }

    public void createSnipItUser(String username, String password)
    {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Snip-it-user-data");
        DatabaseReference usersRef = ref.child("users");
        DatabaseReference userNameRef = usersRef.child(username);

        userNameRef.setValue(new User(username, password));
    }
}
