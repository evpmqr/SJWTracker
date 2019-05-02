package com.evpmqr.data;

import com.evpmqr.objects.User;
import com.evpmqr.objects.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private Users users;

    public boolean loadData(){
        try {
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Object.dat");

            if (!file.exists()) {
                return false;
            }

            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            users = (Users) objectIn.readObject();

            System.out.println("The Object has been read from the file");
            objectIn.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }



    public void saveData() {
        try {
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\Object.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(users);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User fetchUser(String name){
        User user = users.getUserList().stream().filter(user1 -> user1.isName(name)).findFirst().orElse(null);
        return user;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void init() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Eric","146737615922528256", true));
        userList.add(new User("Michael", "264629103477522443", false));
        userList.add(new User("Dan", "320718791862517762", false));
        userList.add(new User("Khue", "368695065591414784", false));
        userList.add(new User("Riley", "272895425399226368", false));
        userList.add(new User("Sam", "296426486993518614", false));
        userList.add(new User("Karma", "153558841169149953", false));
        users = new Users(userList);
    }

}
