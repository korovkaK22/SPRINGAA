package com.example.springaa.util;

public class UserValidation {

    public static boolean isValid (String username, String password) throws IllegalStateException{
        if (username == null || username.trim().length()==0){
            throw new IllegalArgumentException("Ім'я не може бути пустим");
        }
        if (password == null || password.trim().length()==0){
            throw new IllegalArgumentException("Пароль не може бути пустим");
        }
        if (!username.matches("^[a-zA-Z0-9]{3,22}$")){
            throw new IllegalArgumentException("Некоректний логін користувача");
        }
        if (password.length() <3 ){
            throw new IllegalArgumentException("Пароль не може бути менше 3 символів");
        }
        return true;
    }


}
