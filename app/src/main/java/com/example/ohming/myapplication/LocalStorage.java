package com.example.ohming.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class LocalStorage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_storage);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.i("test","1");
        String api_call = "http://www.fbcredibility.com/cloudobject/user1/find/student";
        TextView Test = (TextView)findViewById(R.id.t);
        Log.i("test","2");
        try {
            URL url = new URL(api_call);
            Log.i("test","3");
            Scanner scan = new Scan ner(url.openStream());
            Log.i("test","4");
            StringBuffer buf = new StringBuffer();
            Log.i("test","5");
            while(scan.hasNext()){
                buf.append(scan.next());
        }

            JSONObject jsonObject = new JSONObject(buf.toString());
            JSONArray sysArr = jsonObject.getJSONArray("data");
            int tl = sysArr.length();
            int i;
            String tt;
            ArrayList<String> StorageA = new ArrayList<String>();
            for(i = 0 ; i < tl ; i++) {
                tt = sysArr.getString(i);
                JSONObject ti = new JSONObject(tt);
                StorageA.add(ti.getString("name"));
            }
            try {
                FileOutputStream fOut = this.openFileOutput("data.obj", Context.MODE_PRIVATE);
                ObjectOutputStream objOut = new ObjectOutputStream(fOut);
                objOut.writeObject(StorageA);
                objOut.close();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                FileInputStream fIn = this.openFileInput("data.obj");
                ObjectInputStream objIn = new ObjectInputStream(fIn);
                List<String> lstArray = (List<String>)objIn.readObject();
                for(String str: lstArray){
                    Log.v("test", str);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_local_storage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
