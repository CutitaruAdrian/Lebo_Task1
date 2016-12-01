package com.example.cutit_000.lebogitsearch;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.StringTokenizer;

public class Main2Activity extends AppCompatActivity {

    private String TAG = Main2Activity.class.getSimpleName();
    private ListView lv;
    ArrayList<HashMap<String, String>> RepositoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RepositoryList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetRepositories().execute();

    }

    private class GetRepositories extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Main2Activity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        public Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String url = ("https://api.github.com/users/"+ MainActivity.MyClass.user+"/repos");
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {

                try {
                    JSONArray repositories = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < repositories.length(); i++) {
                        JSONObject r = repositories.getJSONObject(i);
                        String name = r.getString("name");
                        //afisare in consola (test pentru nume)
                        System.out.println(name);

                        // tmp hash map for single repository
                        HashMap<String, String> repository = new HashMap<>();

                        // adding each child node to HashMap key => value
                        repository.put("name", name);

                        // adding repository to repository list
                        RepositoryList.add(repository);



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(Main2Activity.this, RepositoryList, R.layout.list_item, new String[]{"name"},
                    new int[]{R.id.name});
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    HashMap<String, String> s=RepositoryList.get(position);
                    MainActivity.MyClass.rep_name=s.toString();

                    System.out.println(MainActivity.MyClass.rep_name);

                    Intent newActivity = new Intent(Main2Activity.this, Rep_Details.class);
                    newActivity.putExtra("position", RepositoryList.get(position));
                    startActivity(newActivity);}
            });

        }


    }






}
