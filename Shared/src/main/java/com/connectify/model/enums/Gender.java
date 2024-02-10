package com.connectify.model.enums;

public enum Gender {
    MALE,FEMALE;

    public String toString(){
        String gender = super.toString();
        return gender.charAt(0) + gender.substring(1).toLowerCase();
    }
}
