package com.untitledev.untitledev_module.controllers;

import com.untitledev.untitledev_module.db.sqlite.SQLiteHelper;

/**
 * Created by Cipriano on 11/17/2017.
 */

public class ControllerQueries {

    public boolean insertarObject(Object object){
        //Si esta conetado
        return new SQLiteHelper().insert(object);
    }
}
