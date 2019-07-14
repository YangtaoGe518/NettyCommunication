package org.yangtao.ge.NettyLogin.Model;

import java.util.ArrayList;

public abstract class UserFactory {

    private static ArrayList<User> users = new ArrayList<>();

    public static void addUsers(User newUser){
        users.add(newUser);
    }

    public static void removeUsers(User oldUser){
        users.remove(oldUser);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

}
