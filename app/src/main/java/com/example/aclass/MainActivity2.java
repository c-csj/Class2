package com.example.aclass;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.BreakIterator;
public class MainActivity2 extends AppCompatActivity {
    private static final String TAG ="MianActivity2";
    EditText dollarEditor,euroEditor,wonEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //接受数据
        Intent conf = getIntent();
        float dollar = conf.getFloatExtra( "dollar_rate_key",0.0f);
        float euro = conf.getFloatExtra( "euro_rate_key",0.0f);
        float won = conf.getFloatExtra( "won_rate_key",0.0f);

        Log.i(TAG, "onCreate: dollar=" + dollar);
        Log.i(TAG, "onCreate: euro=" + dollar);
        Log.i(TAG, "onCreate: won=" + dollar);

    }
    public void save(View btn){
        Log.i(TAG,"save: ");
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuroi = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());
        Log.i(TAG,"save: 获取到新的值");
        Log.i(TAG,"onCreate:newDollar=" + newDollar);
        Log.i(TAG,"onCreate:newEuro=" + newEuroi);
        Log.i(TAG,"onCreate:newWon=" + newWon);

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newDollar);
        bdl.putFloat("key_won",newDollar);
        intent.putExtras(bdl);
        setResult(2,intent);

        finish();
    }

}


