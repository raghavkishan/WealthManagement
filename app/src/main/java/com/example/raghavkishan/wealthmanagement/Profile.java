package com.example.raghavkishan.wealthmanagement;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private String personName,personGivenName,personFamilyName,personEmail,personId,personFirebaseId,personFirebaseName;
    String firebaseGroupId,firebaseGroupName,personFirebaseUID;

    private Uri personPhoto;

    private ImageView profileImageView;

    TextView nameTextView,emailTextView,groupNameTextView,groupIdTextView;

    private static final String TAG = "Profile Fragment";

    View profileLayoutView;

    private UserInfo userInfo;

    private FirebaseUser user;

    FirebaseDatabase database;

    FirebaseAuth firebaseAuth;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileLayoutView = inflater.inflate(R.layout.fragment_profile, container, false);
        return profileLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getArguments()!=null){
            personName = getArguments().getString("personName");
            personGivenName = getArguments().getString("personGivenName");
            personFamilyName = getArguments().getString("personFamilyName");
            personEmail = getArguments().getString("personEmail");
            personId = getArguments().getString("personId");
            personPhoto = Uri.parse(getArguments().getString("personPhoto"));
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG,"Fire Base user email id:"+FirebaseAuth.getInstance().getCurrentUser().getEmail());

        getGroupIdName();

    }

    private void getGroupIdName(){
        database = FirebaseDatabase.getInstance();
        personFirebaseUID = user.getUid();
        Query userExistsInTable = database.getReference("users/"+personFirebaseUID);
        userExistsInTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                displayProfileInfo(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void displayProfileInfo(DataSnapshot dataSnapshot){

        firebaseGroupId = dataSnapshot.child("groupId").getValue().toString();
        firebaseGroupName = dataSnapshot.child("groupName").getValue().toString();
        Log.d(TAG,"firebaseGroupId: "+firebaseGroupId+" firebaseGroupName: "+firebaseGroupName);
        System.out.println("Inside getGroupIdName values");

        profileImageView = (ImageView) profileLayoutView.findViewById(R.id.profileImageView);
        nameTextView = (TextView) profileLayoutView.findViewById(R.id.nameTextView);
        emailTextView = (TextView) profileLayoutView.findViewById(R.id.emailTextView);
        groupNameTextView = (TextView) profileLayoutView.findViewById(R.id.groupNameTextView);
        groupIdTextView = (TextView) profileLayoutView.findViewById(R.id.groupIdTextView);

        personFirebaseId = user.getUid();
        personFirebaseName = user.getDisplayName();

        Log.d(TAG,"The user id from firebase: "+personFirebaseId);
        Log.d(TAG,"The user id from google: "+personId);
        Log.d(TAG,"personName:"+personName);
        Log.d(TAG,"personGivenName:"+personGivenName);
        Log.d(TAG,"personFamilyName"+personFamilyName);


        Picasso.with(getContext()).load(personPhoto).into(profileImageView);
        nameTextView.setText(personFirebaseName);
        emailTextView.setText(personEmail);
        if (!(firebaseGroupId.equalsIgnoreCase("empty") || firebaseGroupName.equalsIgnoreCase("empty"))) {
            groupIdTextView.setText(firebaseGroupId);
            groupNameTextView.setText(firebaseGroupName);
        }
    }

}
