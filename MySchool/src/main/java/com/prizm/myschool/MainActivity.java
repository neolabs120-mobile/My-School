package com.prizm.myschool;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Window;
import android.util.DisplayMetrics;

public class MainActivity extends Activity {

    ImageButton food, schedule, egg, more;
    Boolean check;
    String schulCode, area, schulCrseSeCode, sculKndScCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("getlog",Integer.toString(metrics.densityDpi));
        if(metrics.densityDpi <= 240)
        {
            setContentView(R.layout.activity_main2);
        }
        else
        {
            setContentView(R.layout.activity_main);
        }


        SharedPreferences preferences = getSharedPreferences("PrefName", MODE_PRIVATE);
        schulCode = preferences.getString("schulcode","");
        area = preferences.getString("area", "");
        schulCrseSeCode = preferences.getString("schulcrsesccode", "");
        sculKndScCode = preferences.getString("schulkndsccode", "");
        Log.d("1234",schulCode);


        food = (ImageButton)findViewById(R.id.imageButton2);
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(schulCode.equals(""))
                {
                    Intent i = new Intent(MainActivity.this, AreaActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, LoadFood.class);
                    startActivity(i);
                }
            }
        });


        schedule = (ImageButton)findViewById(R.id.imageButton3);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(schulCode.equals(""))
                {
                    Intent i = new Intent(MainActivity.this, AreaActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(MainActivity.this, ScheduleActivity.class);
                    startActivity(i);
                }
            }
        });


        egg = (ImageButton)findViewById(R.id.imageButton);
        egg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, EasterEgg.class);
//                startActivity(i);
            }
        });


        more = (ImageButton)findViewById(R.id.imageButton4);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Setting.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onResume()
    {
        SharedPreferences preferences = getSharedPreferences("PrefName", MODE_PRIVATE);
        schulCode = preferences.getString("schulcode","");
        area = preferences.getString("area", "");
        schulCrseSeCode = preferences.getString("schulcrsesccode", "");
        sculKndScCode = preferences.getString("schulkndsccode", "");
        super.onResume();
    }




    
}
