package com.example.raghavkishan.wealthmanagement;

/**
 * Created by raghavkishan on 5/8/2017.
 */

public class GroupClass {

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

    public GroupClass(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public static String groupId;
    public static String groupName;

    public GroupClass() {

    }
}
