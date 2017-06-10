package com.example.raghavkishan.wealthmanagement;


import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalExpenses extends Fragment {

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    FirebaseDatabase database;

    View personaldashboardLayoutView;

    int totalretirementFund,totalChildrenEducationFund,totalRealEstateFund;
    int totalLuxuryMediumTermFund,totalComfortMediumTermFund,totalNecessaryMediumTermFund;
    int totalLuxuryShortTermFund,totalComfortShortTermFund,totalNecessaryShortTermFund;

    int totalRetirementExpenseFund,totalChildrenEducationExpenseFund,totalRealEstateExpenseFund;
    int totalLuxuryExpenseMediumTermFund,totalComfortExpenseMediumTermFund,totalNecessaryExpenseMediumTermFund;
    int totalLuxuryExpenseShortTermFund,totalComfortExpenseShortTermFund,totalNecessaryExpenseShortTermFund;

    private static final String TAG = "PeronalExpense fragment";

    TextView retirementIncomeFundValueView,childrenEducationIncomeFundValueView,realEstateIncomeFundValueView;
    TextView luxuryIncomeMediumTermFundValueView,comfortIncomeMediumTermFundValueView,necessaryIncomeMediumTermFundValueView;
    TextView luxuryIncomeShortTermFundValueView,comfortIncomeShortTermFundValueView,necessaryIncomeShortTermFundValueView;

    TextView retirementExpenseFundValueView,childrenEducationExpenseFundValueView,realEstateExpenseFundValueView;
    TextView luxuryExpenseMediumTermFundValueView,comfortExpenseMediumTermFundValueView,necessaryExpenseMediumTermFundValueView;
    TextView luxuryExpenseShortTermFundValueView,comfortExpenseShortTermFundValueView,necessaryExpenseShortTermFundValueView;

    TextView necessaryAvailableShortTermFundValueView,comfortAvailableShortTermFundValueView,luxuryAvailableShortTermFundValueView;
    TextView necessaryAvailableMediumTermFundValueView,comfortAvailableMediumTermFundValueView,luxuryAvailableMediumTermFundValueView;
    TextView retirementAvailableFundValueView,childrenEducationAvailableFundValueView,realEstateAvailableFundValueView;

    String firebaseUid;

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

    public PersonalExpenses() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        personaldashboardLayoutView =  inflater.inflate(R.layout.fragment_personal_expenses, container, false);

        return personaldashboardLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        retirementIncomeFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.retirementIncomeFundValueView);
        childrenEducationIncomeFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.childrenEducationIncomeFundValueView);
        realEstateIncomeFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.realEstateIncomeFundValueView);
        luxuryIncomeMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryIncomeMediumTermFundValueView);
        comfortIncomeMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortIncomeMediumTermFundValueView);
        necessaryIncomeMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.necessaryIncomeMediumTermFundValueView);
        luxuryIncomeShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryIncomeShortTermFundValueView);
        comfortIncomeShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortIncomeShortTermFundValueView);
        necessaryIncomeShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.necessaryIncomeShortTermFundValueView);

        retirementExpenseFundValueView = (TextView)  personaldashboardLayoutView.findViewById(R.id.retirementExpenseFundValueView);
        childrenEducationExpenseFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.childrenEducationExpenseFundValueView);
        realEstateExpenseFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.realEstateExpenseFundValueView);
        luxuryExpenseMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryExpenseMediumTermFundValueView);
        necessaryExpenseMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id. necessaryExpenseMediumTermFundValueView);
        comfortExpenseMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortExpenseMediumTermFundValueView);
        luxuryExpenseShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryExpenseShortTermFundValueView);
        comfortExpenseShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortExpenseShortTermFundValueView);
        necessaryExpenseShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.necessaryExpenseShortTermFundValueView);

        retirementAvailableFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.retirementAvailableFundValueView);
        childrenEducationAvailableFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.childrenEducationAvailableFundValueView);
        realEstateAvailableFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.realEstateAvailableFundValueView);
        necessaryAvailableMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.necessaryAvailableMediumTermFundValueView);
        comfortAvailableMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortAvailableMediumTermFundValueView);
        luxuryAvailableMediumTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryAvailableMediumTermFundValueView);
        necessaryAvailableShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.necessaryAvailableShortTermFundValueView);
        comfortAvailableShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.comfortAvailableShortTermFundValueView);
        luxuryAvailableShortTermFundValueView = (TextView) personaldashboardLayoutView.findViewById(R.id.luxuryAvailableShortTermFundValueView);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseUid = user.getUid();

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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                calculateAvailableFunds();
            }
        },400);

    }

    private void calculateAvailableFunds(){
        retirementAvailableFundValueView.setText(String.valueOf(totalretirementFund-totalRetirementExpenseFund));
        childrenEducationAvailableFundValueView.setText(String.valueOf(totalChildrenEducationFund-totalChildrenEducationExpenseFund));
        realEstateAvailableFundValueView.setText(String.valueOf(totalRealEstateFund-totalRealEstateExpenseFund));
        necessaryAvailableMediumTermFundValueView.setText(String.valueOf(totalNecessaryMediumTermFund-totalNecessaryExpenseMediumTermFund));
        comfortAvailableMediumTermFundValueView.setText(String.valueOf(totalComfortMediumTermFund-totalComfortExpenseMediumTermFund));
        luxuryAvailableMediumTermFundValueView.setText(String.valueOf(totalLuxuryMediumTermFund-totalLuxuryExpenseMediumTermFund));
        necessaryAvailableShortTermFundValueView.setText(String.valueOf(totalNecessaryShortTermFund-totalNecessaryExpenseShortTermFund));
        comfortAvailableShortTermFundValueView.setText(String.valueOf(totalComfortShortTermFund-totalComfortExpenseShortTermFund));
        luxuryAvailableShortTermFundValueView.setText(String.valueOf(totalLuxuryShortTermFund-totalLuxuryExpenseShortTermFund));


    }

    private void necessaryShortTermExpensesListener(){

        necessaryExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                necessaryExpenseShortTermFundValueView.setText(String.valueOf(totalNecessaryExpenseShortTermFund));
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
        necessaryExpensesShortTermFundQuery = database.getReference("necessaryexpenses").child("shorttermexpenses").orderByChild("uid").equalTo(firebaseUid);
        necessaryExpensesShortTermFundQuery.addChildEventListener(necessaryExpensesShortTermFundListener);

    }

    private void comfortShortTermExpensesListener(){

        comfortExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                comfortExpenseShortTermFundValueView.setText(String.valueOf(totalComfortExpenseShortTermFund));
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
        comfortExpensesShortTermFundQuery = database.getReference("comfortsexpenses").child("shorttermexpenses").orderByChild("uid").equalTo(firebaseUid);
        comfortExpensesShortTermFundQuery.addChildEventListener(comfortExpensesShortTermFundListener);

    }

    private void luxuryShortTermExpensesListener(){

        luxuryExpensesShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryExpenseShortTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                luxuryExpenseShortTermFundValueView.setText(String.valueOf(totalLuxuryExpenseShortTermFund));
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
        luxuryExpensesShortTermFundQuery = database.getReference("luxuryexpenses").child("shorttermexpenses").orderByChild("uid").equalTo(firebaseUid);
        luxuryExpensesShortTermFundQuery.addChildEventListener(luxuryExpensesShortTermFundListener);


    }
    private void comfortMediumTermExpensesListener()
    {
        comfortExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                comfortExpenseMediumTermFundValueView.setText(String.valueOf(totalComfortExpenseMediumTermFund));
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
        comfortExpensesMediumTermFundQuery = database.getReference("comfortsexpenses").child("mediumtermexpenses").orderByChild("uid").equalTo(firebaseUid);
        comfortExpensesMediumTermFundQuery.addChildEventListener(comfortExpensesMediumTermFundListener);

    }

    private void necessaryMediumTermExpensesListener(){
        necessaryExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                necessaryExpenseMediumTermFundValueView.setText(String.valueOf(totalNecessaryExpenseMediumTermFund));
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
        necessaryExpensesMediumTermFundQuery = database.getReference("necessaryexpenses").child("mediumtermexpenses").orderByChild("uid").equalTo(firebaseUid);
        necessaryExpensesMediumTermFundQuery.addChildEventListener(necessaryExpensesMediumTermFundListener);

    }

    private void luxuryMediumTermExpensesListener(){
        luxuryExpensesMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryExpenseMediumTermFund +=Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                luxuryExpenseMediumTermFundValueView.setText(String.valueOf(totalLuxuryExpenseMediumTermFund));
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
        luxuryExpensesMediumTermFundQuery = database.getReference("luxuryexpenses").child("mediumtermexpenses").orderByChild("uid").equalTo(firebaseUid);
        luxuryExpensesMediumTermFundQuery.addChildEventListener(luxuryExpensesMediumTermFundListener);
    }

    private void realEstateFundExpensesListenerMethod(){
        realEstateExpensesListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG","dataSnapshot realestate:" +dataSnapshot.toString());
                //Toast.makeText(getActivity(),"dataSnapshot realestate:" +dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                totalRealEstateExpenseFund += Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                realEstateExpenseFundValueView.setText(String.valueOf(totalRealEstateExpenseFund));
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
        realEstateExpensesFundQuery = database.getReference("realestateexpenses").orderByChild("uid").equalTo(firebaseUid);
        realEstateExpensesFundQuery.addChildEventListener(realEstateExpensesListener);

    }
    private void childrenEducationFundExpensesListenerMethod(){
        childrenEducationExpensesFundListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalChildrenEducationExpenseFund +=  Integer.parseInt(dataSnapshot.child("expenseValue").getValue().toString());
                childrenEducationExpenseFundValueView.setText(String.valueOf(totalChildrenEducationExpenseFund));
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
        childrenEducationExpensesFundQuery = database.getReference("childreneducationfundexpenses").orderByChild("uid").equalTo(firebaseUid);
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
                retirementExpenseFundValueView.setText(String.valueOf(totalRetirementExpenseFund));
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
        retirementExpensesFundQuery = database.getReference("retirementfundexpenses").orderByChild("uid").equalTo(firebaseUid);
        retirementExpensesFundQuery.addChildEventListener(retirementExpensesFundListener);
    }

    private void realEstateFundListenerMethod() {

        realestateListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("TAG","dataSnapshot realestate:" +dataSnapshot.toString());
                //Toast.makeText(getActivity(),"dataSnapshot realestate:" +dataSnapshot.toString(), Toast.LENGTH_SHORT).show();
                totalRealEstateFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                realEstateIncomeFundValueView.setText(String.valueOf(totalRealEstateFund));
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
        realestateQuery = database.getReference("realestate").orderByChild("uid").equalTo(firebaseUid);
        realestateQuery.addChildEventListener(realestateListener);
    }

    private void childrenEducationFundListenerMethod() {

        childrenEducationFundListener= new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalChildrenEducationFund +=  Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                childrenEducationIncomeFundValueView.setText(String.valueOf(totalChildrenEducationFund));
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
        childreneducationfundQuery = database.getReference("childreneducationfund").orderByChild("uid").equalTo(firebaseUid);
        childreneducationfundQuery.addChildEventListener(childrenEducationFundListener);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //totalRealEstateFund = 0;
        //totalretirementFund = 0;
        //totalChildrenEducationFund = 0;
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
                retirementIncomeFundValueView.setText(String.valueOf(totalretirementFund));
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
        retirementfundQuery = database.getReference("retirementfund").orderByChild("uid").equalTo(firebaseUid);
        retirementfundQuery.addChildEventListener(retirementFundListener);
    }

    private void luxuryMediumTermListener(){

        luxuryIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryMediumTermFund +=Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                luxuryIncomeMediumTermFundValueView.setText(String.valueOf(totalLuxuryMediumTermFund));
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
        luxuryIncomeMediumTermFundQuery = database.getReference("luxury").child("mediumterminvestment").orderByChild("uid").equalTo(firebaseUid);
        luxuryIncomeMediumTermFundQuery.addChildEventListener(luxuryIncomeMediumTermFundListener);

    }

    public void necessaryMediumTermListener(){

        necessaryIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryMediumTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                necessaryIncomeMediumTermFundValueView.setText(String.valueOf(totalNecessaryMediumTermFund));
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
        necessaryIncomeMediumTermFundQuery = database.getReference("necessary").child("mediumterminvestment").orderByChild("uid").equalTo(firebaseUid);
        necessaryIncomeMediumTermFundQuery.addChildEventListener(necessaryIncomeMediumTermFundListener);

    }

    public void comfortMediumTermListener(){

        comfortIncomeMediumTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortMediumTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                comfortIncomeMediumTermFundValueView.setText(String.valueOf(totalComfortMediumTermFund));
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
        comfortIncomeMediumTermFundQuery = database.getReference("comforts").child("mediumterminvestment").orderByChild("uid").equalTo(firebaseUid);
        comfortIncomeMediumTermFundQuery.addChildEventListener(comfortIncomeMediumTermFundListener);

    }

    private void luxuryShortTermListener(){

        luxuryIncomeShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalLuxuryShortTermFund +=Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                luxuryIncomeShortTermFundValueView.setText(String.valueOf(totalLuxuryShortTermFund));
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
        luxuryIncomeShortTermFundQuery = database.getReference("luxury").child("shortterminvestment").orderByChild("uid").equalTo(firebaseUid);
        luxuryIncomeShortTermFundQuery.addChildEventListener(luxuryIncomeShortTermFundListener);

    }

    public void necessaryShortTermListener(){

        necessaryIncomeShortTermFundistener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalNecessaryShortTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                necessaryIncomeShortTermFundValueView.setText(String.valueOf(totalNecessaryShortTermFund));
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
        necessaryIncomeShortTermFundQuery = database.getReference("necessary").child("shortterminvestment").orderByChild("uid").equalTo(firebaseUid);
        necessaryIncomeShortTermFundQuery.addChildEventListener(necessaryIncomeShortTermFundistener);

    }

    public void comfortShortTermListener(){

        comfortIncomeShortTermFundListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                totalComfortShortTermFund += Integer.parseInt(dataSnapshot.child("incomeValue").getValue().toString());
                comfortIncomeShortTermFundValueView.setText(String.valueOf(totalComfortShortTermFund));
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
        comfortIncomeShortTermFundQuery = database.getReference("comforts").child("shortterminvestment").orderByChild("uid").equalTo(firebaseUid);
        comfortIncomeShortTermFundQuery.addChildEventListener(comfortIncomeShortTermFundListener);
    }


}
