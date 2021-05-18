package com.example.aclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity{
    private static final String TAG ="MainActivity";
    EditText input;
    TextView result;
    float dollarRate = 0.15f;
    float euroRate = 0.12f;
    float wonRate = 172.0f;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input = (EditText) findViewById(R.id.input);
        result = (TextView) findViewById(R.id.result);

        SharedPreferences sharedPreferences =
                getSharedPreferences("rate", Activity.MODE_PRIVATE);

        PreferenceManager.getDefaultSharedPreferences(this);

        dollarRate = sharedPreferences.getFloat("dollar_rate", 0.0f);
        euroRate = sharedPreferences.getFloat("euro_rate", 0.0f);
        wonRate = sharedPreferences.getFloat("won_rate", 0.0f);

        Thread t = new Thread(String.valueOf(this));
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    String str = (String) msg.obj;
                    Log.i(TAG, "handlerMessage:get str=" + str);
                    result.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void onClick(View btn) {
        String str = input.getText().toString();
        float r = 0;
        if (str.length() > 0) {
            r = Float.parseFloat(str);
        }
    }
    public void click(View btn){
        Log.i(TAG,"click:");

           float r = 0.0f;
        switch (btn.getId()){
            case R.id.btn_dollar:
                r = dollarRate;
                break;
            case R.id.btn_euro:
                r = euroRate;
                break;
            case R.id.btn_won:
                r = wonRate;
                break;
        }


        String str = input.getText().toString();
        Log.i(TAG,"click:str="+str);
        if (str == null || str.length() == 0) {
            Toast.makeText(this, "Please input RMB", Toast.LENGTH_SHORT).show();
        } else {
            if (str == null) {
                Toast.makeText(this, "Please input RMB", Toast.LENGTH_SHORT).show();
            } else {

                result.setText("123.444");
            }
        }
    }

    public void openCongigActivity(View btn){

        openConfig();
    }

    private void openConfig() {
        Intent config = new Intent( this, MainActivity2.class);
        config.putExtra("dollar_rate_key", dollarRate);
        config.putExtra("dollar_rate_key", euroRate);
        config.putExtra("dollar_rate_key", wonRate);

        Log.i(TAG,"openConfigActivity:dollarRate="+dollarRate);
        Log.i(TAG,"openConfigActivity:euroRate="+euroRate);
        Log.i(TAG,"openConfigActivity:wonRate="+wonRate);

        startActivityForResult(config,1);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.menu_setting){
            openConfig();
        }
        return false;
    }


    public void run() {
        Log.i(TAG, "run:..........");
        URL url = null;
        try {
 //           url = new URL("http://www.usd-cny.com/bankofchina.htm");
 //           HttpURLConnection http = (HttpURLConnection)url.openConnection();
  //          InputStream in = http.getInputStream();
   //         String html = inputStream2String(in);
   //         Log.i(TAG, "run:html=" + html);

            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG,"run:title="+doc.title());

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i(TAG,"run: time="+publicTime.html());

            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    Log.i(TAG,"run:td="+tds.first().text());
                    Log.i(TAG,"run:rate="+tds.get(5).text());
                }
            }
        } catch (MalformedURLException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
       Message msg = handler.obtainMessage(7);
       msg.obj="from message";
       handler.sendMessage(msg);

    }
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "gb2312");
        for (; ;) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
        return out.toString();
    }

    public void setHandler(Handler handler) {

    }
}























