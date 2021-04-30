package com.prizm.myschool;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Calendar;

import android.widget.ListView;
import android.os.AsyncTask;
import com.school.library.*;
import android.util.Log;

public class ScheduleActivity extends Activity {

    ArrayAdapter<Adapter> abAdapter;
    final ArrayList<Adapter> abList = new ArrayList<Adapter>();
    ListView listview;
    String schulCode, where, schulCrseSeCode, sculKndScCode, datearea, schoolname;
    String[] schedule;
    ProcessTask p;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);

        SharedPreferences preferences = getSharedPreferences("PrefName", 0);
        schulCode = preferences.getString("schulcode","");
        where = preferences.getString("area", "");
        schulCrseSeCode = preferences.getString("schulcrsesccode", "");
        sculKndScCode = preferences.getString("schulkndsccode", "");
        schoolname = preferences.getString("schoolname", "");
        fordate();

        listview = (ListView)findViewById(R.id.list);
        listview.setClickable(false);

        abAdapter = new ABArrayAdapter(this, R.layout.list_row, abList);
        listview.setAdapter(abAdapter);

        p = new ProcessTask();
        p.execute();

/*        Adapter ne1 = new Adapter("Mia","01034567890");
        abList.add(ne1); */
    }

    public class ProcessTask extends AsyncTask<String, Integer, Long> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ScheduleActivity.this);
            dialog.setMessage("학사일정을 가져오는 중입니다.");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected Long doInBackground(String... params) {
            schedule = Utils.getSchedule(datearea, schulCode, schulCrseSeCode, sculKndScCode, schoolname);
            return 0l;
        }

        protected void onPostExecute(Long result) {
            super.onPostExecute(result);
            int last = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.d("getcheck",Integer.toString(schedule.length));
            for(int i = 1; i <= last; i++)
            {
                String day = Integer.toString(i);
                Adapter thing = new Adapter(day + "일", schedule[i]);
                abList.add(thing);
            }
            abAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    public String fordate()
    {
        if (where.equals("서울")) {
            datearea = "sen.go.kr";
        } else if (where.equals("경기도")) {
            datearea = "goe.go.kr";
        } else if (where.equals("인천광역시")) {
            datearea = "ice.go.kr";
        } else if (where.equals("강원도")) {
            datearea = "kwe.go.kr";
        } else if (where.equals("충청북도")) {
            datearea = "cbe.go.kr";
        } else if (where.equals("충청남도")) {
            datearea = "cne.go.kr";
        } else if (where.equals("세종특별자치도")) {
            datearea = "sje.go.kr";
        } else if (where.equals("대전광역시")) {
            datearea = "dje.go.kr";
        } else if (where.equals("경상북도")) {
            datearea = "gbe.go.kr";
        } else if (where.equals("경상남도")) {
            datearea = "gne.go.kr";
        } else if (where.equals("대구광역시")) {
            datearea = "dge.go.kr";
        } else if (where.equals("울산광역시")) {
            datearea = "use.go.kr";
        } else if (where.equals("부산광역시")) {
            datearea = "pen.go.kr";
        } else if (where.equals("전라북도")) {
            datearea = "jbe.go.kr";
        } else if (where.equals("전라남도")) {
            datearea = "jne.go.kr";
        } else if (where.equals("광주광역시")){
            datearea = "gen.go.kr";
        } else if (where.equals("제주도")){
            datearea = "jje.go.kr";
        }
        return "";
    }


    
}
