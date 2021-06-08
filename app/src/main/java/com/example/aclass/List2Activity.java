package com.example.aclass;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class List2Activity extends ListActivity implements AdapterView.OnItemClickListener{
    private String TAG = "List2Activity";
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems;
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initListView();

        this.setListAdapter(listItemAdapter);

        //setContentView(R.layout.activity_list2);
       //ListView listview = findViewById(R.id.mylist2);
       //ProgressBar progressBar = findViewById(R.id.progressBar);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 7) {
                    List<HashMap<String, String>> list2 = (List<HashMap<String, String>>) msg.obj;
                   listItemAdapter = new SimpleAdapter(List2Activity.this, list2,
                            R.layout.list_item,
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                            );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);

            }
        };
        getListView().setOnItemClickListener(this);
        MyList task = new MyList();
        task.setHandler(handler);
        Thread thread = new Thread(task);
        thread.start();
    }


    private void initListView() {
        listItems = new ArrayList<HashMap<String, String>>();
        for (int i = 0;i<50;i++){
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
        setListAdapter(listItemAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       //Log.i(TAG,"onItemClick:parent="+parent);
       //Log.i(TAG,"onItemClick:view="+view);
       Log.i(TAG,"onItemClick:position="+position);
       //Log.i(TAG,"onItemClick:id="+id);
       HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
    String titleStr = map.get("ItemTitle");
      String detailStr = map.get("ItemDetail");
      Log.i(TAG,"onItemClick:titleStr="+titleStr);
      Log.i(TAG,"onItemClick:detailStr="+detailStr);

        listItems.remove(position);
        listItemAdapter.notifyDataSetChanged();


        Intent rateCalc = new Intent(this,RateCalcActivity.class);
        rateCalc.putExtra("title",titleStr);
        rateCalc.putExtra("rate",Float.parseFloat(detailStr));
        startActivity(rateCalc);
    }
    public boolean onItemLongClick(AdapterView<?> parent,View view, int position, long id) {
        Log.i(TAG, "onItemLongClick:长按事件处理");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示")
                .setMessage("请确认是否删除当前数据")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "onClick:对话框事件处理");
                        listItems.remove(getListView().getItemAtPosition(position));

                    }
                }).setPositiveButton("否",null);
        builder.create().show();

        return  true;
    }

}


