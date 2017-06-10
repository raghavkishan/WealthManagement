package com.example.raghavkishan.wealthmanagement;

/**
 * Created by raghavkishan on 5/7/2017.
 */

public class incomeNodeLongTerm {

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getIncomeValue() {
        return incomeValue;
    }

    public void setIncomeValue(int incomeValue) {
        this.incomeValue = incomeValue;
    }



    public incomeNodeLongTerm(String uid, int incomeValue, String groupId, String description, String groupName) {
        this.uid = uid;
        this.incomeValue = incomeValue;
        this.groupId = groupId;
        this.description = description;
        this.groupName = groupName;
    }

    String uid;
    int incomeValue;
    String groupId;
    String description;
    String groupName;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public incomeNodeLongTerm(){

    }



}
