package com.example.aclass;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class List3Activity extends ListActivity implements AdapterView.OnItemClickListener{
    Handler handler;
    private static final String TAG = "List3Activity";
    private ArrayList<HashMap<String,String>> listItems;
    private SimpleAdapter listItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_list3);

        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0;i<10;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail:"+i);
            listItems.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,
                listItems,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
                );
        MyAdapter adapter = new MyAdapter(this,R.layout.list_item,listItems);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }
    @Override
    public  void  onItemClick(AdapterView<?> parent, View view, int position, long id){
        Log.i(TAG,"onItemClick:position="+position);

    }

}