package com.example.raghavkishan.wealthmanagement;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddIncomeExpenses extends Fragment {

    ArrayList<String> incomeMainCategorySpinnerList = new ArrayList<String>();
    ArrayList<String> expensesMainCategorySpinnerList = new ArrayList<String>();
    ArrayList<String> incomeCategoryTagsSpinnerList = new ArrayList<String>();
    ArrayList<String> expensesCategoryTagsSpinnerList =  new ArrayList<String>();

    Spinner incomeMainCategorySpinner,expensesMainCategorySpinner,incomeCategoryTagsSpinner,expensesCategoryTagsSpinner;

    View addIncomeExpensesLayoutView;

    String selectedIncomeMainCategory,selectedIncomeCategoryTag,selectedExpensesMainCategory,selectedExpensesCategoryTag;
    String firebaseUserUid,enteredIncomeDescriptionText,enteredExpensesDescriptionText,firebaseGroupId,firebaseGroupName;

    int enteredIncomeValue,enteredExpensesValue;

    EditText enteredIncomeEditText,enteredIncomeDescriptionEditText,expensesEditText,expensesDescriptionEditText;

    Button addIncomeButton,addExpensesButton;

    FirebaseAuth firebaseAuth;

    FirebaseUser user;

    FirebaseDatabase database;

    private static final String TAG = "AddIncomeExpense Frag";

    boolean canPostIncome=true,canPostExpense=true,groupExist=true;

    TextView addExpenseLabelTextView,addIncomeLabelTextView;

    public AddIncomeExpenses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        addIncomeExpensesLayoutView =  inflater.inflate(R.layout.fragment_add_income_expenses, container, false);
        return addIncomeExpensesLayoutView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addExpenseLabelTextView = (TextView)  addIncomeExpensesLayoutView.findViewById(R.id.expensesLabelTextView);
        addIncomeLabelTextView = (TextView) addIncomeExpensesLayoutView.findViewById(R.id.incomeLabelTextView);
        incomeMainCategorySpinner = (Spinner) addIncomeExpensesLayoutView.findViewById(R.id.incomeMainCategorySpinner);
        incomeCategoryTagsSpinner = (Spinner) addIncomeExpensesLayoutView.findViewById(R.id.incomeCategoryTagsSpinner);
        expensesMainCategorySpinner = (Spinner) addIncomeExpensesLayoutView.findViewById(R.id.expensesMainCategorySpinner);
        expensesCategoryTagsSpinner = (Spinner) addIncomeExpensesLayoutView.findViewById(R.id.expensesCategoryTagsSpinner);
        enteredIncomeEditText = (EditText) addIncomeExpensesLayoutView.findViewById(R.id.incomeEditText);
        addIncomeButton = (Button) addIncomeExpensesLayoutView.findViewById(R.id.addIncomeButton);
        addExpensesButton = (Button)  addIncomeExpensesLayoutView.findViewById(R.id.addExpensesButton);
        enteredIncomeDescriptionEditText = (EditText) addIncomeExpensesLayoutView.findViewById(R.id.incomeDescriptionEditText);
        expensesEditText = (EditText) addIncomeExpensesLayoutView.findViewById(R.id.expensesEditText);
        expensesDescriptionEditText = (EditText) addIncomeExpensesLayoutView.findViewById(R.id.expensesDescriptionEditText);

        incomeMainCategorySpinnerMethod();
        expensesMainCategorySpinnerMethod();

        firebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        firebaseUserUid = user.getUid();

        addIncomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIncome();
            }
        });
        addExpensesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addExpense();
            }
        });

        database =  FirebaseDatabase.getInstance();
        Query userTable = database.getReference("users/"+firebaseUserUid);
        userTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseGroupId = dataSnapshot.child("groupId").getValue().toString();
                firebaseGroupName = dataSnapshot.child("groupName").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void incomeCategoryTagsSpinnerMethod(String selectIncomeMainCategory){

        if(selectIncomeMainCategory.equalsIgnoreCase("Select Main Category")){
            incomeCategoryTagsSpinnerList.clear();
        }

        if (selectIncomeMainCategory.equalsIgnoreCase("Long Term Investment")){
            incomeCategoryTagsSpinnerList.clear();
            incomeCategoryTagsSpinnerList.add("Retirement Fund");
            incomeCategoryTagsSpinnerList.add("Children Education Fund");
            incomeCategoryTagsSpinnerList.add("Real Estate");
        }

        if (selectIncomeMainCategory.equalsIgnoreCase("Medium Term Investment")){
            incomeCategoryTagsSpinnerList.clear();
            incomeCategoryTagsSpinnerList.add("Necessary");
            incomeCategoryTagsSpinnerList.add("Comforts");
            incomeCategoryTagsSpinnerList.add("Luxury");
        }

        if (selectIncomeMainCategory.equalsIgnoreCase("Short Term Investment")){
            incomeCategoryTagsSpinnerList.clear();
            incomeCategoryTagsSpinnerList.add("Necessary");
            incomeCategoryTagsSpinnerList.add("Comforts");
            incomeCategoryTagsSpinnerList.add("Luxury");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,incomeCategoryTagsSpinnerList);
        incomeCategoryTagsSpinner.setAdapter(adapter);
        incomeCategoryTagsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIncomeCategoryTag = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void expensesCategoryTagsSpinnerMethod(String selectedExpensesMainCategory){

        if(selectedExpensesMainCategory.equalsIgnoreCase("Select Main Category")){
            expensesCategoryTagsSpinnerList.clear();
        }

        if(selectedExpensesMainCategory.equalsIgnoreCase("Long Term Expenses")){
            expensesCategoryTagsSpinnerList.clear();
            expensesCategoryTagsSpinnerList.add("Retirement Fund");
            expensesCategoryTagsSpinnerList.add("Children Education Fund");
            expensesCategoryTagsSpinnerList.add("Real Estate");
        }

        if(selectedExpensesMainCategory.equalsIgnoreCase("Medium Term Expenses")){
            expensesCategoryTagsSpinnerList.clear();
            expensesCategoryTagsSpinnerList.add("Necessary");
            expensesCategoryTagsSpinnerList.add("Comforts");
            expensesCategoryTagsSpinnerList.add("Luxury");
        }

        if(selectedExpensesMainCategory.equalsIgnoreCase("Short Term Expenses")){
            expensesCategoryTagsSpinnerList.clear();
            expensesCategoryTagsSpinnerList.add("Necessary");
            expensesCategoryTagsSpinnerList.add("Comforts");
            expensesCategoryTagsSpinnerList.add("Luxury");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,expensesCategoryTagsSpinnerList);
        expensesCategoryTagsSpinner.setAdapter(adapter);
        expensesCategoryTagsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExpensesCategoryTag = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void expensesMainCategorySpinnerMethod(){

        expensesMainCategorySpinnerList.add("Select Main Category");
        expensesMainCategorySpinnerList.add("Long Term Expenses");
        expensesMainCategorySpinnerList.add("Medium Term Expenses");
        expensesMainCategorySpinnerList.add("Short Term Expenses");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,expensesMainCategorySpinnerList);
        expensesMainCategorySpinner.setAdapter(adapter);
        expensesMainCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedExpensesMainCategory = parent.getItemAtPosition(position).toString();
                expensesCategoryTagsSpinnerMethod(selectedExpensesMainCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void incomeMainCategorySpinnerMethod(){

        incomeMainCategorySpinnerList.add("Select Main Category");
        incomeMainCategorySpinnerList.add("Long Term Investment");
        incomeMainCategorySpinnerList.add("Medium Term Investment");
        incomeMainCategorySpinnerList.add("Short Term Investment");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,incomeMainCategorySpinnerList);
        incomeMainCategorySpinner.setAdapter(adapter);
        incomeMainCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIncomeMainCategory = parent.getItemAtPosition(position).toString();
                incomeCategoryTagsSpinnerMethod(selectedIncomeMainCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addIncome(){

        if (enteredIncomeEditText.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
        }else {

            enteredIncomeValue = Integer.parseInt(enteredIncomeEditText.getText().toString());
            enteredIncomeDescriptionText = enteredIncomeDescriptionEditText.getText().toString();

            if (enteredIncomeDescriptionText.equalsIgnoreCase("") || selectedIncomeMainCategory.equalsIgnoreCase("Select Main Category") || selectedIncomeCategoryTag.matches("")) {
                canPostIncome = false;
            } else {
                canPostIncome = true;
            }

            if (firebaseGroupId.equalsIgnoreCase("empty")) {
                groupExist = false;
            } else {
                groupExist = true;
            }

            if ((canPostIncome) && (groupExist)) {
                switch (selectedIncomeMainCategory) {
                    case "Long Term Investment":
                        Log.d(TAG, "" + selectedIncomeCategoryTag);
                        switch (selectedIncomeCategoryTag) {
                            case "Retirement Fund":
                                longTermIncomePushMethod("retirementfund", firebaseUserUid, enteredIncomeDescriptionText);
                                break;
                            case "Children Education Fund":
                                longTermIncomePushMethod("childreneducationfund", firebaseUserUid, enteredIncomeDescriptionText);
                                break;
                            case "Real Estate":
                                longTermIncomePushMethod("realestate", firebaseUserUid, enteredIncomeDescriptionText);
                                break;
                        }
                        break;
                    case "Medium Term Investment":
                        switch (selectedIncomeCategoryTag) {
                            case "Necessary":
                                ShortMediumTermIncomePushMethod("necessary", "mediumterminvestment", firebaseUserUid);
                                break;
                            case "Comforts":
                                ShortMediumTermIncomePushMethod("comforts", "mediumterminvestment", firebaseUserUid);
                                break;
                            case "Luxury":
                                ShortMediumTermIncomePushMethod("luxury", "mediumterminvestment", firebaseUserUid);
                                break;
                        }
                        break;
                    case "Short Term Investment":
                        switch (selectedIncomeCategoryTag) {
                            case "Necessary":
                                ShortMediumTermIncomePushMethod("necessary", "shortterminvestment", firebaseUserUid);
                                break;
                            case "Comforts":
                                ShortMediumTermIncomePushMethod("comforts", "shortterminvestment", firebaseUserUid);
                                break;
                            case "Luxury":
                                ShortMediumTermIncomePushMethod("luxury", "shortterminvestment", firebaseUserUid);
                                break;
                        }
                }
            } else {
                if (!(canPostIncome)) {
                    Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_SHORT).show();
                }
                if (!(groupExist)) {
                    Toast.makeText(getContext(), "Please create or join a group before you add income/expense", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void ShortMediumTermIncomePushMethod(String selectedIncomeCategoryTag,String selectedIncomeMainCategory,String firebaseUserUid){

        String path = selectedIncomeCategoryTag+"/"+selectedIncomeMainCategory;
        DatabaseReference node = database.getReference(path);
        String pushKey = node.push().getKey();
        incomeNodeLongTerm incNode = new incomeNodeLongTerm(firebaseUserUid,enteredIncomeValue,firebaseGroupId,enteredIncomeDescriptionText,firebaseGroupName);
        node.child(pushKey).setValue(incNode);
        Toast.makeText(getContext(), "Income Saved", Toast.LENGTH_SHORT).show();
        enteredIncomeEditText.setText("");
        enteredIncomeDescriptionEditText.setText("");
    }

    private void longTermIncomePushMethod(String path,String firebaseUserUid,String enteredIncomeDescriptionText){

        DatabaseReference node = database.getReference(path);
        String pushKey = node.push().getKey();
        incomeNodeLongTerm incNode = new incomeNodeLongTerm(firebaseUserUid,enteredIncomeValue,firebaseGroupId,enteredIncomeDescriptionText,firebaseGroupName);
        node.child(pushKey).setValue(incNode);
        Toast.makeText(getContext(), "Income Saved", Toast.LENGTH_SHORT).show();
        enteredIncomeEditText.setText("");
        enteredIncomeDescriptionEditText.setText("");
    }

    private void addExpense(){

        if(expensesEditText.getText().toString().matches("")){
            Toast.makeText(getActivity(), "Please complete all fields", Toast.LENGTH_SHORT).show();
        }else {
            firebaseUserUid = user.getUid();
            enteredExpensesValue = Integer.parseInt(expensesEditText.getText().toString());
            enteredExpensesDescriptionText = expensesDescriptionEditText.getText().toString();

            if (enteredExpensesDescriptionText.equalsIgnoreCase("") || selectedExpensesMainCategory.equalsIgnoreCase("Select Main Category") || selectedExpensesCategoryTag.matches("")) {
                canPostExpense = false;
            } else {
                canPostExpense = true;
            }


            if (firebaseGroupId.equalsIgnoreCase("empty")) {
                groupExist = false;
            } else {
                groupExist = true;
            }


            if ((canPostExpense) && (groupExist)) {
                switch (selectedExpensesMainCategory) {
                    case "Long Term Expenses":
                        switch (selectedExpensesCategoryTag) {
                            case "Retirement Fund":
                                longTermExpensesPushMethod("retirementfundexpenses", enteredExpensesValue, enteredExpensesDescriptionText);
                                break;
                            case "Children Education Fund":
                                longTermExpensesPushMethod("childreneducationfundexpenses", enteredExpensesValue, enteredExpensesDescriptionText);
                                break;
                            case "Real Estate":
                                longTermExpensesPushMethod("realestateexpenses", enteredExpensesValue, enteredExpensesDescriptionText);
                                break;
                        }
                        break;
                    case "Medium Term Expenses":
                        switch (selectedExpensesCategoryTag) {
                            case "Necessary":
                                ShortMediumTermExpensesPushMethod("necessaryexpenses","mediumtermexpenses");
                                break;
                            case "Comforts":
                                ShortMediumTermExpensesPushMethod("comfortsexpenses", "mediumtermexpenses");
                                break;
                            case "Luxury":
                                ShortMediumTermExpensesPushMethod( "luxuryexpenses", "mediumtermexpenses");
                                break;
                        }
                        break;
                    case "Short Term Expenses":
                        switch (selectedExpensesCategoryTag) {
                            case "Necessary":
                                ShortMediumTermExpensesPushMethod( "necessaryexpenses","shorttermexpenses");
                                break;
                            case "Comforts":
                                ShortMediumTermExpensesPushMethod( "comfortsexpenses", "shorttermexpenses");
                                break;
                            case "Luxury":
                                ShortMediumTermExpensesPushMethod( "luxuryexpenses", "shorttermexpenses");
                                break;
                        }
                }
            } else {
                if (!canPostExpense) {
                    Toast.makeText(getContext(), "Complete all fields please", Toast.LENGTH_SHORT).show();
                }
                if (!(groupExist)) {
                    Toast.makeText(getContext(), "Please create or join a group before you add income/expense", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void longTermExpensesPushMethod(String path,int enteredExpensesValue,String enteredExpensesDescriptionText){

        DatabaseReference node = database.getReference(path);
        String pushKey = node.push().getKey();
        expenseNode expNode = new expenseNode(enteredExpensesValue,enteredExpensesDescriptionText,firebaseUserUid,firebaseGroupId,firebaseGroupName);
        node.child(pushKey).setValue(expNode);
        Toast.makeText(getActivity(), "Expense Saved", Toast.LENGTH_SHORT).show();
        expensesEditText.setText("");
        expensesDescriptionEditText.setText("");
    }

    private void ShortMediumTermExpensesPushMethod(String selectedExpensesCategoryTag,String selectedExpensesMainCategory){

        String path = selectedExpensesCategoryTag+"/"+selectedExpensesMainCategory;
        DatabaseReference node = database.getReference(path);
        String pushKey = node.push().getKey();
        expenseNode expNode = new expenseNode(enteredExpensesValue,enteredExpensesDescriptionText,firebaseUserUid,firebaseGroupId,firebaseGroupName);
        node.child(pushKey).setValue(expNode);
        Toast.makeText(getContext(), "Expense Saved", Toast.LENGTH_SHORT).show();
        expensesEditText.setText("");
        expensesDescriptionEditText.setText("");

    }
}
