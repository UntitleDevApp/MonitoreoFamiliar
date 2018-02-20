package com.untitledev.untitledev_module.controllers;

import android.content.Context;

import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.services.impl.UserServiceImpl;

/**
 * Created by Cipriano on 2/19/2018.
 */

public class UserController {
    public UserController(){

    }
    public Response addUser(Context context, User user) {
        user.setStatus(1);
        Response response = new UserServiceImpl().addUser(context, user);

        return response;
    }
}
