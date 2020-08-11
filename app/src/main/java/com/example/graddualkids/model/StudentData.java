package com.example.graddualkids.model;

import java.util.ArrayList;
import java.util.Date;

public class StudentData {
    private String idStudent;
    private String userName;

    private String name;
    private String lastName;
    private int dni;
    private Date birthDate;
    private String distric;
    private String addres;
    private String imgProfile;
    private int homeworkSent;
    private int homeworkChecked;
    public  ArrayList<StudentData> studentDataArrayList;


    public StudentData (String idStudent, String name, String lastName, String imgProfile, int homeworkSent, int homeworkChecked) {
        this.idStudent = idStudent;
        this.name = name;
        this.lastName = lastName;
        this.imgProfile = imgProfile;
        this.homeworkSent = homeworkSent;
        this.homeworkChecked = homeworkChecked;
    }

    public StudentData(String idStudent, String userName){
        this.idStudent = idStudent;
        this.userName = userName;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDistric() {
        return distric;
    }

    public void setDistric(String distric) {
        this.distric = distric;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }

    public int getHomeworkSent() {
        return homeworkSent;
    }

    public void setHomeworkSent(int homeworkSent) {
        this.homeworkSent = homeworkSent;
    }

    public int getHomeworkChecked() {
        return homeworkChecked;
    }

    public void setHomeworkChecked(int homeworkChecked) {
        this.homeworkChecked = homeworkChecked;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
