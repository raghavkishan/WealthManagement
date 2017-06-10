package com.example.raghavkishan.wealthmanagement;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDahsboard extends Fragment {

    FirebaseUser user;

    FirebaseDatabase database;

    View groupDashboardLayoutView;

    int totalretirementFund,totalChildrenEducationFund,totalRealEstateFund;
    int totalLuxuryMediumTermFund,totalComfortMediumTermFund,totalNecessaryMediumTermFund;
    int totalLuxuryShortTermFund,totalComfortShortTermFund,totalNecessaryShortTermFund;

    int totalRetirementExpenseFund,totalChildrenEducationExpenseFund,totalRealEstateExpenseFund;
    int totalLuxuryExpenseMediumTermFund,totalComfortExpenseMediumTermFund,totalNecessaryExpenseMediumTermFund;
    int totalLuxuryExpenseShortTermFund,totalComfortExpenseShortTermFund,totalNecessaryExpenseShortTermFund;

    private static final String TAG = "GroupDashboard fragment";

    TextView retirementIncomeFundValueViewGroup,childrenEducationIncomeFundValueViewGroup,realEstateIncomeFundValueViewGroup;
    TextView luxuryIncomeMediumTermFundValueViewGroup,comfortIncomeMediumTermFundValueViewGroup,necessaryIncomeMediumTermFundValueViewGroup;
    TextView luxuryIncomeShortTermFundValueViewGroup,comfortIncomeShortTermFundValueViewGroup,necessaryIncomeShortTermFundValueViewGroup;

    TextView retirementExpenseFundValueViewGroup,childrenEducationExpenseFundValueViewGroup,realEstateExpenseFundValueViewGroup;
    TextView luxuryExpenseMediumTermFundValueViewGroup,comfortExpenseMediumTermFundValueViewGroup,necessaryExpenseMediumTermFundValueViewGroup;
    TextView luxuryExpenseShortTermFundValueViewGroup,comfortExpenseShortTermFundValueViewGroup,necessaryExpenseShortTermFundValueViewGroup;

    TextView necessaryAvailableShortTermFundValueViewGroup,comfortAvailableShortTermFundValueViewGroup,luxuryAvailableShortTermFundValueViewGroup;
    TextView necessaryAvailableMediumTermFundValueViewGroup,comfortAvailableMediumTermFundValueViewGroup,luxuryAvailableMediumTermFundValueViewGroup;
    TextView retirementAvailableFundValueViewGroup,childrenEducationAvailableFundValueViewGroup,realEstateAvailableFundValueViewGroup;

    String firebaseUid,firebaseGroupId,firebaseGroupName;

    Query retirementfundQuery,childreneducationfundQuery,realestateQuery;
    Query luxuryIncomeMediumTermFundQuery,comfortIncomeMediumTermFundQuery,necessaryIncomeMediumTermFundQuery;
    Query luxuryIncomeShortTermFundQuery,comfortIncomeShortTermFundQuery,necessaryIncomeShortTermFundQuery;

    Query retirementExpensesFundQuery,childrenEducationExpensesFundQuery,realEstateExpensesFundQuery;
    Query luxuryExpensesMediumTermFundQuery,comfortExpensesMediumTermFundQuery,necessaryExpensesMediumTermFundQuery;
    Query luxuryExpensesShortTermFundQuery,comfortExpensesShortTermFundQuery,necessaryExpensesShortTermFundQuery;

    ChildEventListener retirementFundListener,childrenEducationFundListener,realestateListener;
    ChildEventListener luxuryIncomeMediumTermFundListener,comfortIncomeMediumTermFundListener,necessaryIncomeMediumTermFundListener;
    ChildEventListener luxuryIncomeShortTermFundListener,comfortIncomeShortTermFundListener,necessaryIncomeShortTermFundistener;

    ChildEventListener retirementExpensesFundListener,childrenEducationExpensesFundListener,realEstateExpensesListener;
    ChildEventListener luxuryExpensesMediumTermFundListener,comfortExpensesMediumTermFundListener,necessaryExpensesMediumTermFundListener;
    ChildEventListener luxuryExpensesShortTermFundListener,comfortExpensesShortTermFundListener,necessaryExpensesShortTermFundListener;

    public GroupDahsboard() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupDashboardLayoutView =  inflater.inflate(R.layout.fragment_group_dahsboard, container, false);
        return groupDashboardLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        retirementIncomeFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.retirementIncomeFundValueViewGroup);
        childrenEducationIncomeFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.childrenEducationIncomeFundValueViewGroup);
        realEstateIncomeFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.realEstateIncomeFundValueViewGroup);
        luxuryIncomeMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryIncomeMediumTermFundValueViewGroup);
        comfortIncomeMediumTermFundValueViewGroup= (TextView) groupDashboardLayoutView.findViewById(R.id.comfortIncomeMediumTermFundValueViewGroup);
        necessaryIncomeMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.necessaryIncomeMediumTermFundValueViewGroup);
        luxuryIncomeShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryIncomeShortTermFundValueViewGroup);
        comfortIncomeShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.comfortIncomeShortTermFundValueViewGroup);
        necessaryIncomeShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.necessaryIncomeShortTermFundValueViewGroup);

        retirementExpenseFundValueViewGroup = (TextView)  groupDashboardLayoutView.findViewById(R.id.retirementExpenseFundValueViewGroup);
        childrenEducationExpenseFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.childrenEducationExpenseFundValueViewGroup);
        realEstateExpenseFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.realEstateExpenseFundValueViewGroup);
        luxuryExpenseMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryExpenseMediumTermFundValueViewGroup);
        necessaryExpenseMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id. necessaryExpenseMediumTermFundValueViewGroup);
        comfortExpenseMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.comfortExpenseMediumTermFundValueViewGroup);
        luxuryExpenseShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryExpenseShortTermFundValueViewGroup);
        comfortExpenseShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.comfortExpenseShortTermFundValueViewGroup);
        necessaryExpenseShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.necessaryExpenseShortTermFundValueViewGroup);

        retirementAvailableFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.retirementAvailableFundValueViewGroup);
        childrenEducationAvailableFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.childrenEducationAvailableFundValueViewGroup);
        realEstateAvailableFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.realEstateAvailableFundValueViewGroup);
        necessaryAvailableMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.necessaryAvailableMediumTermFundValueViewGroup);
        comfortAvailableMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.comfortAvailableMediumTermFundValueViewGroup);
        luxuryAvailableMediumTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryAvailableMediumTermFundValueViewGroup);
        necessaryAvailableShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.necessaryAvailableShortTermFundValueViewGroup);
        comfortAvailableShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.comfortAvailableShortTermFundValueViewGroup);
        luxuryAvailableShortTermFundValueViewGroup = (TextView) groupDashboardLayoutView.findViewById(R.id.luxuryAvailableShortTermFundValueViewGroup);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUid = user.getUid();

        getGroupIdName();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callListenrs();
            }
        },100);

    }

    private void callListenrs(){
        retirementFundListenerMethod();
        childrenEducationFundListenerMethod();
        realEstateFundListenerMethod();
        luxuryMediumTermListener();
        necessaryMediumTermListener();
        comfortMediumTermListener();
        luxuryShortTermListener();
        comfortShortTermListener();
        necessaryShortTermListener();

        retirementFundExpensesListenerMethod();
        childrenEducationFundExpensesListenerMethod();
        realEstateFundExpensesListenerMethod();
        luxuryMediumTermExpensesListener();
        necessaryMediumTermExpensesListener();
        comfortMediumTermExpensesListener();
        luxuryShortTermExpensesListener();
        comfortShortTermExpensesListener();
        necessaryShortTermExpensesListener();
    }

    private void getGroupIdName() {
        database = FirebaseDatabase.getInstance();
        firebaseUid = user.getUid();
        Query userExistsInTable = database.getReference("users/" + firebaseUid);
        userExistsInTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseGroupId = dataSnapshot.child("groupId").getValue().toString();
                firebaseGroupName = dataSnapshot.child("groupName").getValue().toString();
                Log.d(TAG, "firebaseGroupId: " + firebaseGroupId + " firebaseGroupName: " + firebaseGroupName);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void necessaryShortTermExpensesListener(){

        necessaryExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                necessaryExpenseShortTermFundValueViewGroup.setText(String.valueOf(totalNecessaryExpenseShortTermFund));
                necessaryAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalNecessaryShortTermFund-totalNecessaryExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        necessaryExpensesShortTermFundQuery = database.getReference("necessaryexpenses").child("shorttermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        necessaryExpensesShortTermFundQuery.addChildEventListener(necessaryExpensesShortTermFundListener);

    }

    private void comfortShortTermExpensesListener(){

        comfortExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                comfortExpenseShortTermFundValueViewGroup.setText(String.valueOf(totalComfortExpenseShortTermFund));
                comfortAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalComfortShortTermFund-totalComfortExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        comfortExpensesShortTermFundQuery = database.getReference("comfortsexpenses").child("shorttermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        comfortExpensesShortTermFundQuery.addChildEventListener(comfortExpensesShortTermFundListener);

    }

    private void luxuryShortTermExpensesListener(){

        luxuryExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                luxuryExpenseShortTermFundValueViewGroup.setText(String.valueOf(totalLuxuryExpenseShortTermFund));
                luxuryAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalLuxuryShortTermFund-totalLuxuryExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        database =  FirebaseDatabase.getInstance();
        luxuryExpensesShortTermFundQuery = database.getReference("luxuryexpenses").child("shorttermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        luxuryExpensesShortTermFundQuery.addChildEventListener(luxuryExpensesShortTermFundListener);
    }

    private void comfortMediumTermExpensesListener()
    {
        comfortExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                comfortExpenseMediumTermFundValueViewGroup.setText(String.valueOf(totalComfortExpenseMediumTermFund));
                comfortAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalComfortMediumTermFund-totalComfortExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        comfortExpensesMediumTermFundQuery = database.getReference("comfortsexpenses").child("mediumtermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        comfortExpensesMediumTermFundQuery.addChildEventListener(comfortExpensesMediumTermFundListener);

    }

    private void necessaryMediumTermExpensesListener(){
        necessaryExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                necessaryExpenseMediumTermFundValueViewGroup.setText(String.valueOf(totalNecessaryExpenseMediumTermFund));
                necessaryAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalNecessaryMediumTermFund-totalNecessaryExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        necessaryExpensesMediumTermFundQuery = database.getReference("necessaryexpenses").child("mediumtermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        necessaryExpensesMediumTermFundQuery.addChildEventListener(necessaryExpensesMediumTermFundListener);

    }

    private void luxuryMediumTermExpensesListener(){
        luxuryExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                luxuryExpenseMediumTermFundValueViewGroup.setText(String.valueOf(totalLuxuryExpenseMediumTermFund));
                luxuryAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalLuxuryMediumTermFund-totalLuxuryExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        luxuryExpensesMediumTermFundQuery = database.getReference("luxuryexpenses").child("mediumtermexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        luxuryExpensesMediumTermFundQuery.addChildEventListener(luxuryExpensesMediumTermFundListener);
    }

    private void realEstateFundExpensesListenerMethod(){
        realEstateExpensesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG","dataSnapshot realestate:" +dataSnapshot.toString());
                //Toast.makeText(getActivity(),"dataSnapshot realestate:" +dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                totalRealEstateExpenseFund += Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                realEstateExpenseFundValueViewGroup.setText(String.valueOf(totalRealEstateExpenseFund));
                realEstateAvailableFundValueViewGroup.setText(String.valueOf(totalRealEstateFund-totalRealEstateExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        realEstateExpensesFundQuery = database.getReference("realestateexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        realEstateExpensesFundQuery.addChildEventListener(realEstateExpensesListener);

    }
    private void childrenEducationFundExpensesListenerMethod(){
        childrenEducationExpensesFundListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalChildrenEducationExpenseFund +=  Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                childrenEducationExpenseFundValueViewGroup.setText(String.valueOf(totalChildrenEducationExpenseFund));
                childrenEducationAvailableFundValueViewGroup.setText(String.valueOf(totalChildrenEducationFund-totalChildrenEducationExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        childrenEducationExpensesFundQuery = database.getReference("childreneducationfundexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        childrenEducationExpensesFundQuery.addChildEventListener(childrenEducationExpensesFundListener);
    }


    private void retirementFundExpensesListenerMethod(){
        retirementExpensesFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d(TAG,"totalretirementFund:"+dataSnapshot.getValue());
                //System.out.println(dataSnapshot.child("expenseValue").getValue());
                //System.out.println(dataSnapshot.child("description").getValue());
                totalRetirementExpenseFund += Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                retirementExpenseFundValueViewGroup.setText(String.valueOf(totalRetirementExpenseFund));
                retirementAvailableFundValueViewGroup.setText(String.valueOf(totalretirementFund-totalRetirementExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        retirementExpensesFundQuery = database.getReference("retirementfundexpenses").orderByChild("groupId").equalTo(firebaseGroupId);
        retirementExpensesFundQuery.addChildEventListener(retirementExpensesFundListener);
    }

    private void realEstateFundListenerMethod() {

        realestateListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG","dataSnapshot realestate:" +dataSnapshot.toString());
                //Toast.makeText(getActivity(),"dataSnapshot realestate:" +dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                totalRealEstateFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                realEstateIncomeFundValueViewGroup.setText(String.valueOf(totalRealEstateFund));
                realEstateAvailableFundValueViewGroup.setText(String.valueOf(totalRealEstateFund-totalRealEstateExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        realestateQuery = database.getReference("realestate").orderByChild("groupId").equalTo(firebaseGroupId);
        realestateQuery.addChildEventListener(realestateListener);
    }

    private void childrenEducationFundListenerMethod() {

        childrenEducationFundListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalChildrenEducationFund +=  Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                childrenEducationIncomeFundValueViewGroup.setText(String.valueOf(totalChildrenEducationFund));
                childrenEducationAvailableFundValueViewGroup.setText(String.valueOf(totalChildrenEducationFund-totalChildrenEducationExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        childreneducationfundQuery = database.getReference("childreneducationfund").orderByChild("groupId").equalTo(firebaseGroupId);
        childreneducationfundQuery.addChildEventListener(childrenEducationFundListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        retirementfundQuery.removeEventListener(retirementFundListener);
        childreneducationfundQuery.removeEventListener(childrenEducationFundListener);
        realestateQuery.removeEventListener(realestateListener);
        comfortIncomeShortTermFundQuery.removeEventListener(comfortIncomeShortTermFundListener);
        luxuryIncomeMediumTermFundQuery.removeEventListener(luxuryIncomeMediumTermFundListener);
        necessaryIncomeMediumTermFundQuery.removeEventListener(necessaryIncomeMediumTermFundListener);
        comfortIncomeMediumTermFundQuery.removeEventListener(comfortIncomeMediumTermFundListener);
        necessaryIncomeShortTermFundQuery.removeEventListener(necessaryIncomeShortTermFundistener);
        luxuryIncomeShortTermFundQuery.removeEventListener(luxuryIncomeShortTermFundListener);
    }

    private void retirementFundListenerMethod(){

        retirementFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.d(TAG,"totalretirementFund:"+dataSnapshot.getValue());
                System.out.println(dataSnapshot.child("incomeValue").getValue());
                //System.out.println(dataSnapshot.child("description").getValue());
                totalretirementFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                retirementIncomeFundValueViewGroup.setText(String.valueOf(totalretirementFund));
                retirementAvailableFundValueViewGroup.setText(String.valueOf(totalretirementFund-totalRetirementExpenseFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        retirementfundQuery = database.getReference("retirementfund").orderByChild("groupId").equalTo(firebaseGroupId);
        retirementfundQuery.addChildEventListener(retirementFundListener);
    }


    private void luxuryMediumTermListener(){

        luxuryIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryMediumTermFund +=Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                luxuryIncomeMediumTermFundValueViewGroup.setText(String.valueOf(totalLuxuryMediumTermFund));
                luxuryAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalLuxuryMediumTermFund-totalLuxuryExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        luxuryIncomeMediumTermFundQuery = database.getReference("luxury").child("mediumterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        luxuryIncomeMediumTermFundQuery.addChildEventListener(luxuryIncomeMediumTermFundListener);

    }


    public void necessaryMediumTermListener(){

        necessaryIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryMediumTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                necessaryIncomeMediumTermFundValueViewGroup.setText(String.valueOf(totalNecessaryMediumTermFund));
                necessaryAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalNecessaryMediumTermFund-totalNecessaryExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        necessaryIncomeMediumTermFundQuery = database.getReference("necessary").child("mediumterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        necessaryIncomeMediumTermFundQuery.addChildEventListener(necessaryIncomeMediumTermFundListener);

    }

    public void comfortMediumTermListener(){

        comfortIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortMediumTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                comfortIncomeMediumTermFundValueViewGroup.setText(String.valueOf(totalComfortMediumTermFund));
                comfortAvailableMediumTermFundValueViewGroup.setText(String.valueOf(totalComfortMediumTermFund-totalComfortExpenseMediumTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        comfortIncomeMediumTermFundQuery = database.getReference("comforts").child("mediumterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        comfortIncomeMediumTermFundQuery.addChildEventListener(comfortIncomeMediumTermFundListener);

    }

    private void luxuryShortTermListener(){

        luxuryIncomeShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryShortTermFund +=Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                luxuryIncomeShortTermFundValueViewGroup.setText(String.valueOf(totalLuxuryShortTermFund));
                luxuryAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalLuxuryShortTermFund-totalLuxuryExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        database =  FirebaseDatabase.getInstance();
        luxuryIncomeShortTermFundQuery = database.getReference("luxury").child("shortterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        luxuryIncomeShortTermFundQuery.addChildEventListener(luxuryIncomeShortTermFundListener);

    }


    public void necessaryShortTermListener(){

        necessaryIncomeShortTermFundistener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryShortTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                necessaryIncomeShortTermFundValueViewGroup.setText(String.valueOf(totalNecessaryShortTermFund));
                necessaryAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalNecessaryShortTermFund-totalNecessaryExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        necessaryIncomeShortTermFundQuery = database.getReference("necessary").child("shortterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        necessaryIncomeShortTermFundQuery.addChildEventListener(necessaryIncomeShortTermFundistener);

    }

    public void comfortShortTermListener(){

        comfortIncomeShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortShortTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                comfortIncomeShortTermFundValueViewGroup.setText(String.valueOf(totalComfortShortTermFund));
                comfortAvailableShortTermFundValueViewGroup.setText(String.valueOf(totalComfortShortTermFund-totalComfortExpenseShortTermFund));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        database =  FirebaseDatabase.getInstance();
        comfortIncomeShortTermFundQuery = database.getReference("comforts").child("shortterminvestment").orderByChild("groupId").equalTo(firebaseGroupId);
        comfortIncomeShortTermFundQuery.addChildEventListener(comfortIncomeShortTermFundListener);
    }
}
