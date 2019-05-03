package com.evpmqr.handlers;

import com.evpmqr.objects.User;
import com.evpmqr.objects.Users;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private Users users;

    public boolean loadData() {
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

    public User fetchUser(String name) {
        return users.getUserList().stream().filter(user1 -> user1.isName(name)).findFirst().orElse(null);
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void resetPoints() {
        for (User user : users.getUserList()) {
            user.setVotesLeft(2);
        }
    }

    public void init() {
        List<User> userList = new ArrayList<>();
        User eric = new User("Eric", "146737615922528256", true);
        //eric.setSjwPoints(3);
        userList.add(eric);

        User michael = new User("Michael", "264629103477522443", false);
        // michael.setSjwPoints(6);
        userList.add(michael);

        User dan = new User("Dan", "320718791862517762", false);
        // dan.setSjwPoints(1);
        userList.add(dan);

        User khue = new User("Khue", "368695065591414784", false);
        // khue.setSjwPoints(0);
        userList.add(khue);

        User riley = new User("Riley", "272895425399226368", false);
        // riley.setSjwPoints(1);
        userList.add(riley);

        User sam = new User("Sam", "296426486993518614", false);
        // sam.setSjwPoints(0);
        userList.add(sam);

        User karma = new User("Karma", "153558841169149953", false);
        //karma.setSjwPoints(3);
        userList.add(karma);
        users = new Users(userList);
    }

}
