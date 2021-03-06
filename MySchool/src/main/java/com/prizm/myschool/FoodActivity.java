package com.prizm.myschool;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.widget.ImageView;

import com.school.library.Schoollibrary;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FoodActivity extends FragmentActivity {
    private ViewPager _mViewPager;
    private ViewPagerAdapter _adapter;
    String[] breakfast;
    String[] lunch;
    String[] dinner;
    String[] move;
    String[] schoolfood = new String[101];
    String[] dateaver = new String[9];
    String schulCode, where, schulCrseSeCode, sculKndScCode, area;
    String page;
    String datearea;
    String getbreak, getlunch, getdinner, getdate, getmonth;
    private AsyncTask<String, Void, String> task;
    Calendar calendar = Calendar.getInstance();


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        SharedPreferences preferences = getSharedPreferences("PrefName", 0);
        schulCode = preferences.getString("schulcode","");
        where = preferences.getString("area", "");
        schulCrseSeCode = preferences.getString("schulcrsesccode", "");
        sculKndScCode = preferences.getString("schulkndsccode", "");

        getlink();
        fordate();
/*        SharedPreferences settings = getSharedPreferences("first", 0);
        if (settings.getBoolean("isFirstRun", true)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            task = new MyFirstMethod();
            task.execute();
        }
        else
        { */
            checkit();
//        }

        setContentView(R.layout.foodactivity);
        setUpView();
        setTab();

    }
    public class MyFirstMethod extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FoodActivity.this);
            dialog.setTitle("Indeterminate");
            dialog.setMessage("Please wait while loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://meal.lap.so/api/get_data.php?code=" + schulCode + "&area=" + area + "&type=json");
                ResponseHandler<String> resHandler = new BasicResponseHandler();
                page = httpClient.execute(httpGet, resHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONArray jsonObj = new JSONArray(page);
                for(int i = 0; i < jsonObj.length(); i++)
                {
                    JSONObject temp = jsonObj.getJSONObject(i);
                    schoolfood[i] = temp.getString("data");
                    Boolean check1 = schoolfood[i].contains("[??????]");
                    String get = schoolfood[i].replaceAll("\\[??????\\]", ":::");
                    String get1 = get.replaceAll("\\[??????\\]",":::");
                    Log.d("food", get1);
                    String change = "\\:::";
                    if(get1.equals("no_food"))
                    {
                        breakfast[i] = "????????? ????????????.";
                        lunch[i] = "????????? ????????????.";
                        dinner[i] = "????????? ????????????.";
                    }
                    else
                    {
                        move = get1.split(change);
                        Log.d("playwith", move[0]);
                        int go  = move.length;
                        Log.d("good",Integer.toString(go));
                        breakfast[i] = "????????? ????????????.";
                        lunch[i] = "????????? ????????????.";
                        dinner[i] = "????????? ????????????.";
                        for(int ii = 0; ii < go; ii++)
                        {
                            if(ii == 0)
                            {
                                breakfast[i] = move[0].replaceAll(",", "\n");
                            }
                            else if(ii == 1)
                            {
                                lunch[i] = move[1].replaceAll(",", "\n");
                            }
                            else if(ii == 2)
                            {

                                dinner[i] = move[2].replaceAll(",", "\n");
                            }
                        }
                        Log.d("yeah2", breakfast[i]);
                        Log.d("yeah", lunch[i]);
                        Log.d("yeah3", dinner[i]);
                    }

                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dateaver = Schoollibrary.getDate(datearea, schulCode, schulCrseSeCode, sculKndScCode, "2");
            saveit();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }


    }

    public void checkit()
    {
        SharedPreferences preferences2 = getSharedPreferences("PrefName", 0);
        String getdate2 = preferences2.getString("date","");
        String[] day3 = getdate2.split(":::");
        Log.d("checkit",day3[3]);

        Calendar calendar = new GregorianCalendar(Locale.KOREA);
        java.util.Date trialTime = new java.util.Date();
        calendar.setTime(trialTime);
        String date = String.valueOf(calendar.get(Calendar.DATE));
        int day_of_week = calendar.get ( Calendar.DAY_OF_WEEK );
        int length = date.length();
        Log.d("datecheck", Integer.toString(length));
        if(length == 1)
        {
            date = "0" + date;
        }
        if ( day_of_week == 1 )
        {
            if(!date.equals(day3[0].substring(8, 10)))
            {
                Log.d("getstring1",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
        else if ( day_of_week == 2 )
        {
            if(!date.equals(day3[1].substring(8, 10)))
            {
                Log.d("getstring2",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
        else if ( day_of_week == 3 )
        {
            if(!date.equals(day3[2].substring(8, 10)))
            {
                Log.d("getstring3",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
        else if ( day_of_week == 4 )
        {
            if(!date.equals(day3[3].substring(8, 10)))
            {
                Log.d("getstring4",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
        else if ( day_of_week == 5 )
        {
            if(!date.equals(day3[4].substring(8, 10)))
            {
                Log.d("getstring5",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
        else if ( day_of_week == 6 )
        {
            if(!date.equals(day3[5].substring(8, 10)))
            {
                Log.d("getstring5",day3[5].substring(8, 10));
                task = new MyFirstMethod();
                task.execute();
            }

        }
        else if ( day_of_week == 7 )
        {
            Log.d("checkitother", day3[6]);
            if(!date.equals(day3[6].substring(8, 10)))
            {
                Log.d("getstring7",date);
                task = new MyFirstMethod();
                task.execute();
            }
        }
    }

    /*
    ?????? - sen
    ?????? - pen
    ?????? - dge
    ?????? - ice
    ?????? - gen
    ?????? - dje
    ?????? - use
    ?????? - sje
    ????????? - goe
    ????????? - kwe
    ???????????? - cbe
    ???????????? - cne
    ???????????? - jbe
    ???????????? - jne
    ???????????? - gbe
    ???????????? - gne
    ????????? - jje
     */

    public String getlink()
    {
        if (where.equals("??????")) {
            area = "sen";
        } else if (where.equals("?????????")) {
            area = "goe";
        } else if (where.equals("???????????????")) {
            area = "ice";
        } else if (where.equals("?????????")) {
            area = "kwe";
        } else if (where.equals("????????????")) {
            area = "cbe";
        } else if (where.equals("????????????")) {
            area = "cne";
        } else if (where.equals("?????????????????????")) {
            area = "sje";
        } else if (where.equals("???????????????")) {
            area = "dje";
        } else if (where.equals("????????????")) {
            area = "gbe";
        } else if (where.equals("????????????")) {
            area = "gne";
        } else if (where.equals("???????????????")) {
            area = "dge";
        } else if (where.equals("???????????????")) {
            area = "use";
        } else if (where.equals("???????????????")) {
            area = "pen";
        } else if (where.equals("????????????")) {
            area = "jbe";
        } else if (where.equals("????????????")) {
            area = "jne";
        } else if (where.equals("???????????????")){
            area = "gen";
        } else if (where.equals("?????????")){
            area = "jje";
        }
        return "";
    }

    public String fordate()
    {
        if (where.equals("??????")) {
            datearea = "sen.go.kr";
        } else if (where.equals("?????????")) {
            datearea = "goe.go.kr";
        } else if (where.equals("???????????????")) {
            datearea = "ice.go.kr";
        } else if (where.equals("?????????")) {
            datearea = "kwe.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "cbe.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "cne.go.kr";
        } else if (where.equals("?????????????????????")) {
            datearea = "sje.go.kr";
        } else if (where.equals("???????????????")) {
            datearea = "dje.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "gbe.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "gne.go.kr";
        } else if (where.equals("???????????????")) {
            datearea = "dge.go.kr";
        } else if (where.equals("???????????????")) {
            datearea = "use.go.kr";
        } else if (where.equals("???????????????")) {
            datearea = "pen.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "jbe.go.kr";
        } else if (where.equals("????????????")) {
            datearea = "jne.go.kr";
        } else if (where.equals("???????????????")){
            datearea = "gen.go.kr";
        } else if (where.equals("?????????")){
            datearea = "jje.go.kr";
        }
        return "";
    }

    public void saveit()
    {
        SharedPreferences prefs = getSharedPreferences("PrefName", 0);
        SharedPreferences.Editor editor = prefs.edit();
        int abc = calendar.get(Calendar.MONTH)+1;
        String month = Integer.toString(abc);
        String morning = "";
        String evening = "";
        String dayoff = "";
        String savedate = "";
        morning += breakfast[0];
        dayoff += lunch[0];
        evening += dinner[0];
        savedate += dateaver[0];
        for(int a = 1; a < 32 ; a++)
        {
            morning += ":::" + breakfast[a];
            evening += ":::" + dinner[a];
            dayoff += ":::" + lunch[a];
        }
        for(int a = 1; a < 7; a++)
        {
            savedate += ":::" + dateaver[a];
        }
        editor.putString("breakfast", morning);
        editor.putString("lunch", dayoff);
        editor.putString("dinner", evening);
        editor.putString("date", savedate);
        editor.putString("month", month);
        editor.commit();
        Log.d("iwantit", morning);
        Log.d("iwantit1", dayoff);
        Log.d("iwantit2", savedate);
    }

    private void setUpView(){
        _mViewPager = (ViewPager) findViewById(R.id.view_pager);
        _adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        _mViewPager.setAdapter(_adapter);
        _mViewPager.setCurrentItem(0);
    }
    private void setTab(){
        _mViewPager.setOnPageChangeListener(new OnPageChangeListener(){

            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                switch(position){
                    case 0:
                        findViewById(R.id.imageView).setVisibility(View.VISIBLE);
                        findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView2).setVisibility(View.VISIBLE);
                        findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView3).setVisibility(View.VISIBLE);
                        findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
                        findViewById(R.id.imageView5).setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        findViewById(R.id.imageView).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView2).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView3).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView4).setVisibility(View.INVISIBLE);
                        findViewById(R.id.imageView5).setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });

    }
}