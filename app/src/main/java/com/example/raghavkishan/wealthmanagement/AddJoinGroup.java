package com.example.raghavkishan.wealthmanagement;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddJoinGroup extends Fragment {


    private FirebaseDatabase dataBase;

    private FirebaseUser user;

    String currentFireBaseGroupCountId,firebaseGroupId,firebaseGroupName;

    View addJoinGroupView;

    EditText enterGroupNameCreateEditTextView,enterGroupNameJoinEditTextView,enterGroupIdJoinEditTextView;

    Button createGroupButton,JoinGroupButton;

    private String enterGroupNameText,createdGroupID,enteredGroupId,obtainedGroupNameForJoin,personFirebaseUID;

    public AddJoinGroup() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addJoinGroupView =  inflater.inflate(R.layout.fragment_add_join_group, container, false);

        return addJoinGroupView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        createGroupButton = (Button) addJoinGroupView.findViewById(R.id.create_group_button);
        JoinGroupButton = (Button) addJoinGroupView.findViewById(R.id.joinGroupButton);
        enterGroupNameCreateEditTextView = (EditText) addJoinGroupView.findViewById(R.id.enterGroupNameCreatEditTextView);
        enterGroupNameJoinEditTextView = (EditText) addJoinGroupView.findViewById(R.id.enterGroupNameJoinEditTextView);
        enterGroupIdJoinEditTextView = (EditText) addJoinGroupView.findViewById(R.id.enterGroupIdJoinEditTextView);

        dataBase =  FirebaseDatabase.getInstance();
        DatabaseReference currentUser = dataBase.getReference("currentgroupid");
        currentUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFireBaseGroupCountId = (dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCreateGroup();
            }
        });

        JoinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickJoinGroup();
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

    }

    private void onClickCreateGroup(){

        if (!(firebaseGroupId.equalsIgnoreCase("empty"))){
            Toast.makeText(getActivity(), "User already belongs to a group", Toast.LENGTH_SHORT).show();
        }else {

            dataBase = FirebaseDatabase.getInstance();
            enterGroupNameText = enterGroupNameCreateEditTextView.getText().toString();
            personFirebaseUID = user.getUid();
            int id = Integer.parseInt(currentFireBaseGroupCountId);
            id = id + 1;
            createdGroupID = String.valueOf(id);
            DatabaseReference myRef = dataBase.getReference("users/" + personFirebaseUID);
            myRef.child("groupId").setValue(createdGroupID);
            myRef.child("groupName").setValue(enterGroupNameText);
            DatabaseReference currentUser = dataBase.getReference("currentgroupid");
            currentUser.setValue(createdGroupID);
            Toast.makeText(getActivity(), "The Group has been created and set", Toast.LENGTH_SHORT).show();
        }
    }


    private void onClickJoinGroup(){


        if (!(firebaseGroupId.equalsIgnoreCase("empty"))){
            Toast.makeText(getActivity(), "User already belongs to a group", Toast.LENGTH_SHORT).show();
        }else
            {
            user = FirebaseAuth.getInstance().getCurrentUser();
            personFirebaseUID = user.getUid();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            enteredGroupId = enterGroupIdJoinEditTextView.getText().toString();
            enterGroupNameText = enterGroupNameJoinEditTextView.getText().toString();
            DatabaseReference myRef = dataBase.getReference("users/" + personFirebaseUID);
            myRef.child("groupId").setValue(enteredGroupId);
            myRef.child("groupName").setValue(enterGroupNameText);
            Toast.makeText(getActivity(), "You have joined the group Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
