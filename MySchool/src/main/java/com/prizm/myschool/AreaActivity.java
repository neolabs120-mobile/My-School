package com.prizm.myschool;

import android.os.Bundle;
import android.app.Activity;
import java.util.ArrayList;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;


public class AreaActivity extends Activity {
    private ListView listview;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.area);

        list = new ArrayList<String>();
        list.add("서울");
        list.add("경기도");
        list.add("인천광역시");
        list.add("강원도");
        list.add("충청북도");
        list.add("충청남도");
        list.add("세종특별자치도");
        list.add("대전광역시");
        list.add("경상북도");
        list.add("경상남도");
        list.add("대구광역시");
        list.add("울산광역시");
        list.add("부산광역시");
        list.add("전라북도");
        list.add("전라남도");
        list.add("광주광역시");
        list.add("제주도");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);



        listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listview.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                String str = (String)adapter.getItem(position);
                Intent intent = new Intent(AreaActivity.this,FindSchool.class);
                intent.putExtra("where", str);
                startActivity(intent);
            }
        });


    }

}
