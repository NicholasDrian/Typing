package com.company;

public interface Typable {
    String nums = "1234567890";
    String lowerCase = "qwertyuiopasdfghjklzxcvbnm";
    String upperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
    String symbols = "~!@#$%^&*()_+-=[]\\{}|;':\",./<>?" ;
    static String getTypable(boolean Nums, boolean Lower, boolean Upper, boolean Symbols){
        String result = "";
        if (Nums) result = result + nums;
        if (Lower) result = result + lowerCase;
        if (Upper) result = result + upperCase;
        if (Symbols) result = result + symbols;
        return result;
    }
}
