package com.example.ch.snip_it;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ch.snip_it.users.CreateUserActivity;
import com.example.ch.snip_it.users.UserHomePage;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * The main activity for Snip-it
 */
public class MainActivity extends AppCompatActivity {

    int RC_SIGN_IN = 0;
    int USER_PAGE_REQUEST_CODE = 4;
    String dbKeyString = "";
    GoogleSignInClient signedInUser;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Get Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();

        // Build a GoogleSignInClient with the options specified by gso.
        final GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signedInUser = mGoogleSignInClient;

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        setContentView(R.layout.activity_main);

        SignInButton signInListener = findViewById(R.id.sign_in_button);
        signInListener.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.sign_in_button:
                        signIn(mGoogleSignInClient);
                        break;
                }
            }
        }));
    }

    private void signIn(GoogleSignInClient mGoogleSignInCient) {
        Intent signInIntent = mGoogleSignInCient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if (requestCode == USER_PAGE_REQUEST_CODE){
            dbKeyString = data.getStringExtra("dbKey");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            firebaseAuthWithGoogle(account);

            // Sign in success? Have the user create a username and password if they weren't authenticated with Firebase onStart
            if (currentUser == null){
                Intent sendIntent = new Intent(MainActivity.this, CreateUserActivity.class);
                sendIntent.putExtra("USER_EMAIL", account.getEmail());
                sendIntent.putExtra("USER_ID", account.getId());
                MainActivity.this.startActivityForResult(sendIntent, USER_PAGE_REQUEST_CODE);
            }

            // ********************* TODO: FOR TEST. DELETE ****************** //

//            String id = "1234908";
//            final FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference ref = database.getReference("Snip-it-user-data");
//            DatabaseReference usersRef = ref.child("users");
//            String key = usersRef.push().getKey();
//            usersRef.child(key).setValue(new User("newKhurl", "pword", "khurl@khurl.com", id, key));
//            //usersRef.setValue(
//            //DatabaseReference userNameRef = usersRef.child(id);
//            dbKeyString = key;

            // ******************** TODO: TESTING END HERE ******************** //

            Intent userPageIntent = new Intent(MainActivity.this, UserHomePage.class);
            userPageIntent.putExtra("USER_ID", account.getId());
            MainActivity.this.startActivity(userPageIntent);
        } catch (ApiException e) {
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success. Set the user value
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly. This indicates they previously authenticated with firebase
        currentUser = mAuth.getCurrentUser();
    }
}
