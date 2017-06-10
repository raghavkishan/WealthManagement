package com.example.raghavkishan.wealthmanagement;

/**
 * Created by raghavkishan on 5/9/2017.
 */

public class expenseNode {



    public expenseNode(int expenseValue, String description, String uid, String groupId, String groupName) {
        this.expenseValue = expenseValue;
        this.description = description;
        this.uid = uid;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    int expenseValue;
    String description;

    public int getExpenseValue() {
        return expenseValue;
    }

    public void setExpenseValue(int expenseValue) {
        this.expenseValue = expenseValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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


    String uid;
    String groupId;
    String groupName;

    public expenseNode() {
    }


}
