package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private ArrayList <Mountain> item;
    private ArrayAdapter <Mountain> adapter;

    /*
    @SuppressWarnings("SameParameterValue")
    private String readFile(String fileName) {
        try {
            //noinspection CharsetObjectCanBeUsed
            return new Scanner(getApplicationContext().getAssets().open(fileName), Charset.forName("UTF-8").name()).useDelimiter("\\A").next();
        } catch (IOException e) {
            Log.e("==>", "Could not read file: " + fileName);
            return null;
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item = new ArrayList<>();
        adapter = new ArrayAdapter<Mountain>(this, R.layout.list_item_textview,R.id.list_item_textview,item);

        ListView myListView = findViewById(R.id.list_view);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name= item.get(position).getName("name");
                int height = item.get(position).getHeight("height");
                String location = item.get(position).getLocation("location");
                String msg = name + "is about " + height + "MASL and located in" + location + ".";
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });

        /*
        String s = readFile("mountains.json");
        Log.d("==>","The following text was found in textfile:\n\n"+s);
        */

        new JsonTask().execute("https://wwwlab.iit.his.se/brom/kurser/mobilprog/dbservice/admin/getdataasjson.php?type=brom");
    }

    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {

        private HttpURLConnection connection = null;
        private BufferedReader reader = null;

        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null && !isCancelled()) {
                    builder.append(line).append("\n");
                }
                return builder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("MainActivity ==>", s);
            try {
                JSONArray jsonarray = new JSONArray(s);
                for(int i = 0; i < jsonarray.length(); i++){
                    JSONObject object = jsonarray.getJSONObject(i);
                    String name = object.getString("name");
                    int height = object.getInt("size");
                    String location = object.getString("location");
                    item.add(new Mountain(name, height, location));
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                Log.e("==>","E:"+e.getMessage());
            }

            /*
            Gson gson=new Gson();
            mountains=gson.fromJson(s,Mountain[].class);
            for(int i=0; i<mountains.length; i++){
                /*Log.d("MainActivity ==>","Hittade ett berg:"+i);
                Log.d("MainActivity ==>","Hittade ett berg:"+mountains[i].getName()+" "+mountains[i].getAuxdata().getWiki());
            }*/

        }
    }

}
