package com.example.raghavkishan.wealthmanagement;
/* reference for the entore application:
1)from: https://developer.android.com
2)Class slides
3)firebase.com
*/

/*
The application begins with this activity.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class LogIn extends AppCompatActivity {

    private SignInButton googleSignInUpButton;

    private static final int RC_SIGN_IN = 1;

    public GoogleApiClient googleApiClient;

    private FirebaseAuth firebaseAuth;

    private static final String TAG = "LogIn Activity";

    private String personName,personGivenName,personFamilyName,personEmail,personId;
    private Uri personPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();

        googleSignInUpButton = (SignInButton) findViewById(R.id.log_in_google_login_signup_button);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(getApplicationContext(),"googleApiClient connection failed",Toast.LENGTH_LONG).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        googleSignInUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                Log.d(TAG,"data inside onActivityResult"+result);

                GoogleSignInAccount account = result.getSignInAccount();

                personName = account.getDisplayName();
                personGivenName = account.getGivenName();
                personFamilyName = account.getFamilyName();
                personEmail = account.getEmail();
                personId = account.getId();
                personPhoto = account.getPhotoUrl();

                Log.d(TAG,"The user id from google: "+personId);
                Log.d(TAG,"personName:"+personName);
                Log.d(TAG,"personGivenName:"+personGivenName);
                Log.d(TAG,"personFamilyName"+personFamilyName);
                Log.d(TAG,"Email id"+personEmail);


                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                //
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.d(TAG, "inside signInWithCredential Success user"+user.getEmail());
                            //updateUI(user);
                            if (user != null){

                                Intent go = new Intent(LogIn.this,MainActivity.class);
                                go.putExtra("personName",personName);
                                go.putExtra("personGivenName",personGivenName);
                                go.putExtra("personFamilyName",personFamilyName);
                                go.putExtra("personEmail",personEmail);
                                go.putExtra("personId",personId);
                                go.putExtra("personPhoto",personPhoto.toString());
                                startActivity(go);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LogIn.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
