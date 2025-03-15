package com.example.project_ui_implementation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_ui_implementation.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    private TextView googleLogin;

    /** NEW **/
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    /** **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database= FirebaseDatabase.getInstance("https://seng-3210-project-4dd9d-default-rtdb.firebaseio.com/");

        /** NEW **/
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Request ID token from Google
                .requestEmail() // Request user's email
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        /** **/

        /**
         * Setting specific text, Google Account to be clickable.
         * Also to make the Create Account here to be clickable.
         */
        TextView googleLogin = findViewById(R.id.loginGoogle);
        TextView crtAccount = findViewById(R.id.crtAccountTXT);
        String txtGL= googleLogin.getText().toString();
        String txtcrtA=crtAccount.getText().toString();
        SpannableString spannabletxtGL= new SpannableString(txtGL);
        SpannableString spannabletxtcrtA= new SpannableString(txtcrtA);
        //Changing the settings of the Strings inside the textViews
        spannabletxtcrtA.setSpan(new ForegroundColorSpan(Color.RED), 33, 45, 0);
        //Clickable to create a new user.
        ClickableSpan ClicktxtcrtA= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //Toast.makeText(MainActivity.this, "Creating User", Toast.LENGTH_LONG).show();
                Intent goCreateAccunt= new Intent(MainActivity.this, CreateAccount.class);
                startActivity(goCreateAccunt);
            }
        };
        spannabletxtcrtA.setSpan(ClicktxtcrtA, 33, 45, 0);
        crtAccount.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        crtAccount.setText(spannabletxtcrtA);

        //Clickable logging for google
        spannabletxtGL.setSpan(new ForegroundColorSpan(Color.RED), 11, 25, 0);
        ClickableSpan ClicktxtGL= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(MainActivity.this, "Redirecting", Toast.LENGTH_LONG).show();
            }
            };
        spannabletxtGL.setSpan(ClicktxtGL, 11, 25, 0);
        googleLogin.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        googleLogin.setText(spannabletxtGL);
        //----------------------------------------------------------------------//
    }

    /** NEW **/
    private static final int RC_SIGN_IN = 9001;

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    /** **/


    /** NEW **/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken()); // Authenticate with Firebase
            } catch (ApiException e) {
                Log.w("SignInActivity", "Google sign in failed", e);
            }
        }
    }

    /** **/

    /** NEW **/
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("SignInActivity", "Sign in successful: " + user.getDisplayName());
                    } else {
                        Log.w("SignInActivity", "Sign in failed", task.getException());
                    }
                });
    }
    /** **/

    //Method to add Usernames inside the database.
    public void addUsername(View view){
        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_LONG).show();
        @SuppressLint("WrongViewCast")
        TextInputLayout fieldUsername = findViewById(R.id.layoutUsernameInput);
        TextInputLayout fieldPassword = findViewById(R.id.layoutPasswordInput);
        EditText txtUsername =fieldUsername.getEditText();
        EditText txtPassword= fieldPassword.getEditText();
        String nUsername=txtUsername.getText().toString();
        String nPassword=txtPassword.getText().toString();
        Users newUser = new Users(nUsername, nPassword);
        newUser.addGenre("Romance");
        newUser.addGenre("Action");
        databaseReference=database.getReference("Users");
        databaseReference.child(newUser.toString()).setValue(newUser);
        Toast.makeText(MainActivity.this, "User added", Toast.LENGTH_LONG).show();
        txtUsername.setText("");
        txtPassword.setText("");
    }

}

