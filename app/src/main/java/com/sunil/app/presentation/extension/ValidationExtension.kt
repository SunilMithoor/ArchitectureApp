package com.sunil.app.presentation.extension

import java.util.regex.Pattern


fun isValidMail(email: String?): Boolean {
    val emailPattern = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    return email?.trim()?.let { Pattern.compile(emailPattern).matcher(it).matches() } == true
}

fun isValidMobile(phone: String?): Boolean {
    return if (!Pattern.matches("[a-zA-Z]+", phone)) {
        phone?.length == 10
    } else false
}

fun checkPassword(password: String): Boolean {
    val passwordREGEX = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{7,}" +               //at least 7 characters
                "$"
    );
    return passwordREGEX.matcher(password).matches()
}

fun checkDecimal(data: String) : Boolean{
    val regex = "\\d*\\.?\\d+"
    println(data.trim().matches(regex.toRegex()).toString())
    return data.trim().matches(regex.toRegex())
}

fun checkPhone(data: String) : Boolean {
    val regex = "[0-9]+"
    return data.trim().matches(regex.toRegex())
}
