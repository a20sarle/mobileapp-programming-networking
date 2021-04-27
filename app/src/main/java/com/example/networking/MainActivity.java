package com.example.networking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Mountain[] mountains;

    @SuppressWarnings("SameParameterValue")
    private String readFile(String fileName) {
        try {
            //noinspection CharsetObjectCanBeUsed
            return new Scanner(getApplicationContext().getAssets().open(fileName), Charset.forName("UTF-8").name()).useDelimiter("\\A").next();
        } catch (IOException e) {
            Log.e("==>", "Could not read file: " + fileName);
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webbSettings=webView.getSettings();
        webbSettings.setJavaScriptEnabled(true);

        String s = readFile("mountains.json");
        Log.d("==>","The following text was found in textfile:\n\n"+s);

        Gson gson=new Gson();
        mountains=gson.fromJson(s,Mountain[].class);
        for(int i=0; i<mountains.length; i++){
            /*Log.d("MainActivity ==>","Hittade ett berg:"+i);*/
            Log.d("MainActivity ==>","Hittade ett berg:"+mountains[i].getName()+" "+mountains[i].getAuxdata().getWiki());
        }
    }
}
