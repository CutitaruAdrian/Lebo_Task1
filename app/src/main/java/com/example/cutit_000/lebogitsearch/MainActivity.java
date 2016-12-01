package com.example.cutit_000.lebogitsearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.StrictMode;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    public static class MyClass{

        public static String  user;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button = (Button) findViewById(R.id.button);

        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setEnabled(false);



        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                HttpsURLConnection connection;
                TextView textView = (TextView) findViewById(R.id.textView);
                EditText editText = (EditText) findViewById(R.id.editText);
                MyClass.user = editText.getText().toString();


                try {
                    URL url = new URL("https://github.com/"+MyClass.user);
                    connection = (HttpsURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    int code = connection.getResponseCode();

                    if (code == 200) {
                        textView.setText("User existent");
                        button2.setEnabled(true);

                    }


                    else {
                        textView.setText("!!! User inexistent !!!");
                        button2.setEnabled(false);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });}


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivity(intent);
                break;
        }
    }


}
