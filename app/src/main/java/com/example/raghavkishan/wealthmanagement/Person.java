package com.example.raghavkishan.wealthmanagement;

import java.util.Date;

/**
 * Created by raghavkishan on 5/3/2017.
 */

public class Person {

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    private String personName;
    private String personEmail;
    private int phoneNumber;
    private String dateOfBirth;
    private String city;
    private String state;
    private String country;
    private String groupId;
    private String groupName;

    public Person(String personName,String personEmail,int phoneNumber,String dateOfBirth,String city,String state,String country,String groupId,String groupName){
        this.personName = personName;
        this.personEmail = personEmail;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.state = state;
        this.country = country;
        this.groupId = groupId;
        this.groupName = groupName;

    }

    public Person(){

    }

}
