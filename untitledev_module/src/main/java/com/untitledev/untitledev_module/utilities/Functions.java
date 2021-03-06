package com.untitledev.untitledev_module.utilities;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Cipriano on 28/02/2017.
 * Clase donde se agregan funciones comunes que se utilizan en una aplicación.
 */
public class Functions {
    public static boolean isEmptyString(String str){
        if (str==null || str.length()==0 || str.trim().length()==0)
            return true;
        return false;
    }
    public static String getCurrentDate(){
        Date date = new Date();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        String strUTCDate = isoFormat.format(date);
        return strUTCDate;
    }

    /*public String getDataTime(){

        Date date = new Date();
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("CST"));
        String strUTCDate = isoFormat.format(date);

        return  strUTCDate;
    }*/

    /**
     * @return retorna la hora y fecha del sistema en un String.
     */
    public static String getDataTime(){
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        return  currentDateTimeString;
    }

    /**
     * @return la hora del sistema en un String.
     */
    public static String getHora(){
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formatteHour = df.format(dt.getTime());
        return formatteHour;
    }

    /**
     * @return la fecha del sistema en un String.
     */
    public static String getFecha(){
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formatteFecha = df.format(dt.getTime());
        return formatteFecha;
    }

    /**
     * @param string se ingresa la cadena con paratentesis a remplazar por espacios en blanco.
     * @return una cadena sin parentesis.
     */
    public static String getReplaceParent(String string){
        String data = string.replaceAll("\\(|\\)"," ");
        return data.replace(" ", "");
    }
    //FUNCIONES PARA LA GESTIÓN DE ARCHIVOS...

    /**
     * Checks if external storage is available for read and write
     * @return un valor verdadero o falso.
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /**
     * Checks if external storage is available to at least read
     * @return un valor verdadero o falso.
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean createWriteFile(String fileName, String value, Context context){
        try {
            FileOutputStream fOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            // Write the string to the file
            osw.write(value);
            // save and close
            osw.flush();
            osw.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * @return verdadero si el directorio existe.
     */
    public static boolean directoryExists(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware/";
        File dir = new File(path);
        if(dir.exists()){
            return true;
        }else{
           return false;
        }
    }

    /**
     * @param fileName nombre del archivo.
     * @param data dato a escribir en el archivo.
     * @return verdadero si el directorio y el archivo fueron generados con su respectiva información.
     */
    public boolean saveToFile(String fileName, String data){
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware/";
            new File(path).mkdir();
            File file = new File(path+fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }  catch(FileNotFoundException ex) {
            return false;
            //ex.printStackTrace();
        }  catch(IOException ex) {
            return false;
           // ex.printStackTrace();
           // Log.d(TAG, ex.getMessage());
        }
        return  true;
    }

    public String readFileConfiguration(String fileName){
        String line = null;
        String path = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                Log.i("JSON_gson:", line);
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        catch(IOException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        return line;
    }

    public String readFile(String fileName){
        String line = null;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware/";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                //Log.i("JSON_gson:", line);
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        catch(IOException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        return line;
    }

    /**
     * @param fileName nombre del archivo.
     * @return un arrayList con los datos leidos desde el archivo.
     */
    public ArrayList<String> readFileArrayList(String fileName){
        ArrayList<String> listFile = new ArrayList<String>();
        String line = null;
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware/";
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                //Log.i("JSON_gson:", line);
                listFile.add(line);
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            //line = stringBuilder.toString();
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        catch(IOException ex) {
            Log.d("Error...!", ex.getMessage());
        }
        return listFile;
    }

    public String readFile (String fileName, Context context){

        try {
            StringBuffer outStringBuf = new StringBuffer();
            String inputLine = "";
            FileInputStream fIn = context.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fIn);
            BufferedReader inBuff = new BufferedReader(isr);
            while ((inputLine = inBuff.readLine()) != null) {
                outStringBuf.append(inputLine);
                outStringBuf.append("\n");
            }
            inBuff.close();
            return outStringBuf.toString();
        } catch (IOException e) {
            return null;
        }
    }

    public boolean deleteFile(String fileName, Context context) {
        context.deleteFile(fileName);
        return true;
    }

    /**
     * @return verdadero si el directorio fue eliminado exitosamente.
     */
    public boolean deleteDirectory() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/fiware";
        File dir = new File(path);
        File[] files = dir.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteDirectory();
                } else {
                    f.delete();
                }
            }
        }
        dir.delete();
        return true;
    }

}
