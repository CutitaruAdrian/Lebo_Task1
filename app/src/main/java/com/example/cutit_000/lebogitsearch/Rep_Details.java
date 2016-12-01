package com.example.cutit_000.lebogitsearch;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Rep_Details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep__details);
        TextView t1=(TextView) findViewById(R.id.textView0);
        TextView t2=(TextView) findViewById(R.id.textView1);
        TextView t3=(TextView) findViewById(R.id.textView5);
        TextView t4=(TextView) findViewById(R.id.textView6);
        TextView t5= (TextView) findViewById(R.id.textView7);
        TextView t6= (TextView) findViewById(R.id.textView8);
        TextView t7= (TextView) findViewById(R.id.textView9);
        TextView t8= (TextView) findViewById(R.id.textView10);
        TextView t9= (TextView) findViewById(R.id.textView11);
        TextView t10= (TextView) findViewById(R.id.textView12);

        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String url = ("https://api.github.com/repos/" + MainActivity.MyClass.user +""+ MainActivity.MyClass.rep_name);
        System.out.println(url); //test url
        String url1=url.replace("{name=","/");
        String url2=url1.replace("}","");
        System.out.println(url2);   //test url2
        String jsonStr = sh.makeServiceCall(url2);

        try {
            JSONObject r=new JSONObject(jsonStr);
            // looping through All object
            for (int i = 0; i < r.length(); i++)
            {
                String name = r.getString("name");
                t1.setText("1. Nume Rep.: "+name);
                String id=r.getString("id");
                t2.setText("2. id: " + id);


                String create=r.getString("created_at");
                create=create.replace("T"," / ");
                create=create.replace("Z","");
                t3.setText("3. Creat la: "+create);

                String update=r.getString("updated_at");
                update=update.replace("T"," / ");
                update=update.replace("Z","");
                t4.setText("4. Update la: "+update);

                String push=r.getString("pushed_at");
                push=push.replace("T"," / ");
                push=push.replace("Z","");
                t5.setText("5. Push la: "+push);


                JSONObject own = r.getJSONObject("owner");{
                String login=own.getString("login");
                String type=own.getString("type");
                t6.setText("6. Owner: "+login);
                t7.setText("7. Owner Type: "+type);}

                String br=r.getString("default_branch");
                t8.setText("8. Branch: "+br);

                String size=r.getString("size");
                t9.setText("9. Size: "+size+"kb");

                String link=r.getString("html_url");
                t10.setText("10. Link: "+link);
            }
        }

        catch (final JSONException e) {}
    }
}