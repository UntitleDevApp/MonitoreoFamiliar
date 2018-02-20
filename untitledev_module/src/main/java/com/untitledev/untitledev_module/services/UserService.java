package com.untitledev.untitledev_module.services;

import android.content.Context;

import com.untitledev.untitledev_module.entities.User;

import java.util.List;

/**
 * Created by Cipriano on 2/19/2018.
 */

public interface UserService {
    public abstract Response addUser(Context context, User user);
    public abstract Response updateUser(User User);
    public abstract Response findUserById(int id);
    public abstract List<User> listAllUsers();
    public abstract Response removeUser(int id);
}
