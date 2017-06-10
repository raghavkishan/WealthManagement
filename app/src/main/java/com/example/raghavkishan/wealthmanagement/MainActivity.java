package com.example.raghavkishan.wealthmanagement;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;

    private static final String TAG = "MainActivity Activity";

    private String personName,personGivenName,personFamilyName,personEmail,personId,personFirebaseUID,firebaseGroupId,firebaseGroupName;
    private String enterGroupNameText,createdGroupID,enteredGroupId,obtainedGroupNameForJoin;

    private Uri personPhoto;

    private FirebaseDatabase dataBase;

    public GoogleApiClient googleApiClient;

    private FirebaseUser user;

    Person person;

    AlertDialog.Builder alertDialogCreateGroup;

    ArrayList<String> existingEmailList = new ArrayList<String>();
    ArrayList<String> existingNameList = new ArrayList<String>();

    ProgressDialog pd;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_profile:
                    onClickProfileOption(personName,personGivenName,personFamilyName,personEmail,personId,personPhoto);
                    return true;
                case R.id.navigation_dashboard:
                    onClickGroupDashboard();
                    return true;
                case R.id.navigation_personal_dashboard:
                    onCLickPersonalExpense();
                    return true;
                case R.id.navigation_add_income_expenses:
                    onClickAddIncomeExpense();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        firebaseAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();

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

        alertDialogCreateGroup = new AlertDialog.Builder(MainActivity.this);

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading...");
        pd.show();

        dataBase =  FirebaseDatabase.getInstance();
        DatabaseReference userTable = dataBase.getReference("users");
        userTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot.getChildren());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        personFirebaseUID = user.getUid();
        dataBase =  FirebaseDatabase.getInstance();
        Query userExistsTable = dataBase.getReference("users/"+personFirebaseUID);
        userExistsTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if((dataSnapshot.child("groupId").getValue() != null)) {
                    firebaseGroupId = dataSnapshot.child("groupId").getValue().toString();
                    firebaseGroupName = dataSnapshot.child("groupName").getValue().toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        personName = getIntent().getStringExtra("personName");
        personGivenName = getIntent().getStringExtra("personGivenName");
        personFamilyName = getIntent().getStringExtra("personFamilyName");
        personEmail = getIntent().getStringExtra("personEmail");
        personId = getIntent().getStringExtra("personId");
        personPhoto = Uri.parse(getIntent().getStringExtra("personPhoto"));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        },2500);
    }

    private void checkBeforeProfileCall(){
        dataBase = FirebaseDatabase.getInstance();
        personFirebaseUID = user.getUid();
        Query userExistsInTable = dataBase.getReference("users/"+personFirebaseUID);
        userExistsInTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                checkBeforeProfileCallMethod(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkBeforeProfileCallMethod(DataSnapshot dataSnapshot){

        long count = dataSnapshot.getChildrenCount();
        if (count == 9){
            onClickProfileOption(personName,personGivenName,personFamilyName,personEmail,personId,personPhoto);
        }
    }


    private void fetchData(Iterable<DataSnapshot> snapshots){

        existingEmailList.clear();
        for(DataSnapshot snap:snapshots){
            existingEmailList.add(snap.child("personEmail").getValue().toString());
        }
        checkIfUserExistsInDatabase();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =  getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.signOut:
                SignOut();
                break;
            case R.id.add_join_group:
                checkUserGroupExists();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SignOut() {
        existingEmailList.clear();
        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override

            public void onConnected(@Nullable Bundle bundle) {

                FirebaseAuth.getInstance().signOut();
                if (googleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Log.d(TAG, "User Logged out");
                                Intent intent = new Intent(MainActivity.this, LogIn.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "you have logged out successfully", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });

    }

    private void checkUserGroupExists(){

        if (firebaseGroupId.equalsIgnoreCase("empty")){
            addJoinGroup();
        }
        else{
            Toast.makeText(this, "User already belongs to a group", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickProfileOption(String personName,String personGivenName,String personFamilyName,
                                     String personEmail,String personId,Uri personPhoto){

        Bundle args = new Bundle();
        args.putString("personName",personName);
        args.putString("personGivenName",personGivenName);
        args.putString("personFamilyName",personFamilyName);
        args.putString("personEmail",personEmail);
        args.putString("personId",personId);
        args.putString("personPhoto",personPhoto.toString());

        Log.d(TAG,"Inside onClickProfileOption");

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        Profile profile_fragment = new Profile();
        profile_fragment.setArguments(args);
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, profile_fragment);
        fragmentTransaction.commit();
    }

    public void onCLickPersonalExpense(){

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        PersonalExpenses PersonalExpenses_fragment = new PersonalExpenses();
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, PersonalExpenses_fragment);
        fragmentTransaction.commit();

    }

    private void onClickGroupDashboard(){

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        GroupDahsboard groupDashboard_fragment = new GroupDahsboard();
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, groupDashboard_fragment);
        fragmentTransaction.commit();
    }

    public void insertIntoFirbaseDatabase(){

        int phoneNumber = 0;
        String dateOfBirth = "";
        String city = "";
        String state = "";
        String country = "";
        String groupId = "empty";
        String groupName = "empty";

        personFirebaseUID = user.getUid();

        dataBase = FirebaseDatabase.getInstance();
        DatabaseReference userTable = dataBase.getReference("users");
        person = new Person(personName,personEmail,phoneNumber,dateOfBirth,city,state,country,groupId,groupName);
        userTable.child(personFirebaseUID).setValue(person);

        //onClickProfileOption(personName,personGivenName,personFamilyName,personEmail,personId,personPhoto);
    }


    public void checkIfUserExistsInDatabase(){

        String currentUserEmail = firebaseAuth.getCurrentUser().getEmail();
        if ((existingEmailList.contains(currentUserEmail))){
            Log.d(TAG,"User exists");
            checkBeforeProfileCall();
        }
        else{
            Log.d(TAG,"User does not exists");
            insertIntoFirbaseDatabase();
        }
    }

    public void onClickAddIncomeExpense(){

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        AddIncomeExpenses addIncomeExpenses_fragment = new AddIncomeExpenses();
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, addIncomeExpenses_fragment);
        fragmentTransaction.commit();

    }

    private void addJoinGroup(){

        FragmentManager fragments = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragments.beginTransaction();
        AddJoinGroup addJoinGroup_fragment = new AddJoinGroup();
        fragmentTransaction.replace(R.id.mainactivity_frame_layout, addJoinGroup_fragment);
        fragmentTransaction.commit();
    }

}