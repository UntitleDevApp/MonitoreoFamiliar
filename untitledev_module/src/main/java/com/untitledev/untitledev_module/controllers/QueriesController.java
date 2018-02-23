package com.untitledev.untitledev_module.controllers;

import android.content.Context;

import com.untitledev.untitledev_module.db.sqlite.SQLiteHelper;

/**
 * Created by Cipriano on 11/17/2017.
 */

public class QueriesController {
    private Context context;
    private SQLiteHelper sqLiteHelper;

    public QueriesController(Context context){
        sqLiteHelper = new SQLiteHelper(context);
    }

    public boolean createValidate(String keywork){
        return sqLiteHelper.createValidate(keywork);
    }

    public boolean findValidateByKeyword(String keyword){
        return sqLiteHelper.findValidateByKeyword(keyword);
    }

    public boolean findValidateByKeywordAndStatusActive(String keywork){
        int status = 1;
        return  sqLiteHelper.findValidateByKeywordAndStatus(keywork, status);
    }

    public boolean findValidateByKeywordAndStatusInactive(String keywork){
        int status = 0;
        return  sqLiteHelper.findValidateByKeywordAndStatus(keywork, status);
    }

    public boolean updateValidateStatusActive(String keyword){
        return sqLiteHelper.updateValidateStatusActive(keyword);
    }

    public boolean updateValidateStatusInactive(String keyword){
        return sqLiteHelper.updateValidateStatusInactive(keyword);
    }
}
