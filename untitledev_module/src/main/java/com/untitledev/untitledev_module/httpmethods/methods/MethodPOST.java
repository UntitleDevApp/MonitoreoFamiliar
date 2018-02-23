package com.untitledev.untitledev_module.httpmethods.methods;

import android.os.AsyncTask;

import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.utilities.Conf;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by TesistaSDN on 22/02/2018.
 */

public class MethodPOST extends AsyncTask<String, String, Response> {
    private String host = Conf.http_host.getPropiedad();
    private String connectionTimeout = Conf.http_connectiontimeout.getPropiedad();
    private String readTimeout = Conf.http_readtimeout.getPropiedad();
    private MethodPOSTCallback mPOSTCallback;
    public interface MethodPOSTCallback
    {
        void onMethodPOSTCallback(Response response);
    }
    public MethodPOST(MethodPOSTCallback mPOSTCallback){
        this.mPOSTCallback = mPOSTCallback;
    }
    @Override
    protected Response doInBackground(String... vars) {
        String sURL = vars[0];
        String mBody = vars[1];
        HttpURLConnection hURLConnection = null;
        Response mResponse = new Response();
        try {
            URL mUrl = new URL(host + sURL);
            hURLConnection = (HttpURLConnection) mUrl.openConnection();
            hURLConnection.setReadTimeout(Integer.parseInt(readTimeout));
            hURLConnection.setConnectTimeout(Integer.parseInt(connectionTimeout));
            hURLConnection.setRequestMethod("POST");
            hURLConnection.setDoInput(true);
            hURLConnection.setDoOutput(true);
            hURLConnection.setUseCaches(false);

            //make some HTTP header nicety
            hURLConnection.setRequestProperty("Content-Type", "application/json");
            //open
            hURLConnection.connect();
            DataOutputStream dOutputStream = new DataOutputStream(hURLConnection.getOutputStream());
            dOutputStream.writeBytes(mBody);
            dOutputStream.flush();
            mResponse.setHttpCode(hURLConnection.getResponseCode());
            if (mResponse.getHttpCode()==201) {
                try {
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(hURLConnection.getInputStream()));
                    String sLine;
                    StringBuffer sBuffer = new StringBuffer();
                    while ((sLine = bReader.readLine()) != null) {
                        sBuffer.append(sLine);
                    }
                    mResponse.setBodyString(sBuffer.toString());
                    bReader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            dOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(hURLConnection!=null)
                hURLConnection.disconnect();
        }
        return mResponse;
    }

    @Override
    public void onPostExecute(Response response) {
        this.mPOSTCallback.onMethodPOSTCallback(response);
    }
}
