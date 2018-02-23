package com.untitledev.untitledev_module.db.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "monitoring.db";

    interface  Tables{
        String TBL_VALIDATE = "tbl_validate";
    }

    //Campos de la tabla tbl_validate:
    public static final String TBL_VALIDATE_ID = "id";
    public static final String TBL_VALIDATE_KEYWORD = "keyword";
    public static final String TBL_VALIDATE_STATUS = "status";

    //Sentencia SQL para crear la tabla validate.
    String sqlCreateTblValidate = "CREATE TABLE "+ Tables.TBL_VALIDATE +" ("+ TBL_VALIDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+ TBL_VALIDATE_KEYWORD +" TEXT NOT NULL, "+ TBL_VALIDATE_STATUS +" INTEGER)";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTblValidate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla;
        db.execSQL("DROP TABLE IF EXISTS "+Tables.TBL_VALIDATE);
        //Se crea la nueva versión de la tabla.
        db.execSQL(sqlCreateTblValidate);
    }

    /**
     * Elimina la base de datos.
     * @param context el contexto de donde se desea eliminar a DB.
     */
    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    //--------------------------------METODOS PARA LA TABLA TBL_VALIDATE------------------------------------------------//
    /**
     * @param keyword
     * @return verdadero si se inserto correctamente en caso contrario retorna false.
     */
    public boolean createValidate(String keyword){
        boolean band = false;
        if(keyword.length() == 0 || keyword.equals("")){
            band = false;
        }else {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(TBL_VALIDATE_KEYWORD, keyword);
            values.put(TBL_VALIDATE_STATUS, 0);

            long newRowId = db.insert(Tables.TBL_VALIDATE, null, values);
            Log.i("Result Insert: ", "" + newRowId);
            if (newRowId == -1) {
                band = false;
            } else {
                band = true;
            }

            db.close();
        }
        return band;
    }

    public boolean findValidateByKeyword(String keyword){
        boolean band;
        if (keyword.length() == 0 || keyword.equals("")) {
            band = false;
        }else{
            SQLiteDatabase db = this.getReadableDatabase();
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    TBL_VALIDATE_ID,
                    TBL_VALIDATE_KEYWORD,
                    TBL_VALIDATE_STATUS
            };

            // Filter results WHERE "title" = 'My Title'
            String selection = TBL_VALIDATE_KEYWORD + " = ?";
            String[] selectionArgs = {keyword};

            Cursor cursor = db.query(
                    Tables.TBL_VALIDATE,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                       // The sort order
            );

            if(cursor != null) {
                if(cursor.moveToFirst()){
                    keyword = cursor.getString(1);
                    band = true;
                    Log.i("Details of the query: ", " findValidateByKeyword:" +keyword);
                }else{
                    band = false;
                }
            }else{
                band = false;
            }
            db.close();
        }
        return band;
    }
    public boolean findValidateByKeywordAndStatus(String keywork, int status){
        boolean band;
        if (keywork.length() == 0 || keywork.equals("")) {
            band = false;
        }else{
            SQLiteDatabase db = this.getReadableDatabase();
            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
            String[] projection = {
                    TBL_VALIDATE_ID,
                    TBL_VALIDATE_KEYWORD,
                    TBL_VALIDATE_STATUS
            };

            // Filter results WHERE "title" = 'My Title'
            String selection = TBL_VALIDATE_KEYWORD + " = ? AND "+ TBL_VALIDATE_STATUS + "= ?";
            String[] selectionArgs = {keywork, String.valueOf(status)};

            Cursor cursor = db.query(
                    Tables.TBL_VALIDATE,                     // The table to query
                    projection,                               // The columns to return
                    selection,                                // The columns for the WHERE clause
                    selectionArgs,                            // The values for the WHERE clause
                    null,                                     // don't group the rows
                    null,                                     // don't filter by row groups
                    null                                       // The sort order
            );

            if(cursor != null) {
                if(cursor.moveToFirst()){
                    keywork = cursor.getString(1);
                    band = true;
                    Log.i("Details of the query: ", " findValidateByKeywordAndStatus:" +keywork);
                }else{
                    band = false;
                }
            }else{
                band = false;
            }
            db.close();
        }
        return band;
    }

    public boolean updateValidateStatusActive(String keyword){
        boolean band = false;
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        String status = "1";
        values.put(TBL_VALIDATE_STATUS, status);

        // Which row to update, based on the title
        String selection = TBL_VALIDATE_KEYWORD + " LIKE ?";
        String [] selectionArgs = {keyword};

        int count = db.update(
                Tables.TBL_VALIDATE,
                values,
                selection,
                selectionArgs);
        Log.i("Update: ", "updateValidateStatusActive: " + count);
        if(count >= 1){
            band = true;
        }else{
            band = false;
        }
        db.close();

        return band;
    }

    public boolean updateValidateStatusInactive(String keyword){
        boolean band = false;
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        String status = "0";
        values.put(TBL_VALIDATE_STATUS, status);

        // Which row to update, based on the title
        String selection = TBL_VALIDATE_KEYWORD + " LIKE ?";
        String [] selectionArgs = {keyword};

        int count = db.update(
                Tables.TBL_VALIDATE,
                values,
                selection,
                selectionArgs);
        Log.i("Update: ", "updateValidateStatusInactive: " + count);
        if(count >= 1){
            band = true;
        }else{
            band = false;
        }
        db.close();

        return band;
    }
}