package com.prizm.myschool;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.school.library.Schoollibrary;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class LoadFood extends Activity implements OnGestureListener {

    ImageButton b1, b2, b3;
    TextView t1, t2;
    String now;
    String breakfast[] = new String[33];
    String lunch[] = new String[33];
    String dinner[] = new String[33];
    String date[] = new String[9];
    String move[];
    String dateaver[] = new String[9];
    String checkmonth;
    String schoolfood[] = new String[33];
    String schulCode, where, schulCrseSeCode, sculKndScCode, area;
    String getbreak, getlunch, getdinner;
    String datearea;
    String page;
    String m_week, month, year, day;
    int datenow, daynow;
    private AsyncTask<String, Void, String> task;
    private GestureDetector gestureScanner;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    Calendar calendar = Calendar.getInstance();

    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gestureScanner.onTouchEvent(me);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first);

        t1 = (TextView)findViewById(R.id.datetext);
        t2 = (TextView)findViewById(R.id.foodtext);

        b1 = (ImageButton)findViewById(R.id.imageButton5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               now = "??????";
               t2.setText(breakfast[datenow-1]);
               b1.setImageResource(R.drawable.button_p);
               b2.setImageResource(R.drawable.button_n);
               b3.setImageResource(R.drawable.button_n);
            }
        });


        b2 = (ImageButton)findViewById(R.id.imageButton6);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               now = "??????";
               t2.setText(lunch[datenow-1]);
               b1.setImageResource(R.drawable.button_n);
               b2.setImageResource(R.drawable.button_p);
               b3.setImageResource(R.drawable.button_n);
            }
        });


        b3 = (ImageButton)findViewById(R.id.imageButton7);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              now = "??????";
              t2.setText(dinner[datenow-1]);
               b1.setImageResource(R.drawable.button_n);
               b2.setImageResource(R.drawable.button_n);
               b3.setImageResource(R.drawable.button_p);
            }
        });

        task = new MyFirstMethod();
        gestureScanner = new GestureDetector(LoadFood.this);

        int abc = calendar.get(Calendar.MONTH)+1;
        int abc1 = calendar.get(Calendar.YEAR);
        int abc2 = calendar.get(Calendar.DAY_OF_MONTH);
        int day_of_week = calendar.get ( Calendar.DAY_OF_WEEK );
        datenow = abc2;
        daynow = day_of_week;
        month = Integer.toString(abc);
        year = Integer.toString(abc1);
        day = Integer.toString(abc2);
        if ( day_of_week == 1 )
            m_week="(???)";
        else if ( day_of_week == 2 )
            m_week="(???)";
        else if ( day_of_week == 3 )
            m_week="(???)";
        else if ( day_of_week == 4 )
            m_week="(???)";
        else if ( day_of_week == 5 )
            m_week="(???)";
        else if ( day_of_week == 6 )
            m_week="(???)";
        else if ( day_of_week == 7 )
            m_week="(???)";

        SharedPreferences preferences = getSharedPreferences("PrefName", 0);
        schulCode = preferences.getString("schulcode","");
        where = preferences.getString("area", "");
        schulCrseSeCode = preferences.getString("schulcrsesccode", "");
        sculKndScCode = preferences.getString("schulkndsccode", "");
        checkmonth = preferences.getString("month", "");
        getbreak = preferences.getString("breakfast", "");
        getlunch = preferences.getString("lunch", "");
        getdinner = preferences.getString("dinner", "");

        breakfast = getbreak.split(":::");
        lunch = getlunch.split(":::");
        dinner = getdinner.split(":::");

        getlink();
        fordate();

        if(!month.equals(checkmonth))
        {
            task.execute();
        }
        SharedPreferences settings = getSharedPreferences("first", 0);
        SharedPreferences.Editor editor = settings.edit();
        Boolean needcheck = settings.getBoolean("isFirstRun", false);
        if (settings.getBoolean("isFirstRun", true) || needcheck) {
            editor.putBoolean("isFirstRun", false);
            editor.commit();
            task.execute();
        }
        t1.setText(year + "." + month + "." + day + "." + m_week);
        Log.d("next", breakfast[datenow]);
        b1.setImageResource(R.drawable.button_p);
        b2.setImageResource(R.drawable.button_n);
        b3.setImageResource(R.drawable.button_n);
        t2.setText(breakfast[datenow-1]);
        now = "??????";


    }

    public class MyFirstMethod extends AsyncTask<String, Void, String> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoadFood.this);
            dialog.setMessage("??????????????? ???????????? ????????????.");
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
//            dateaver = Schoollibrary.getDate(datearea, schulCode, schulCrseSeCode, sculKndScCode, "2");
            saveit();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }


    }

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


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
            return false;

        // right to left swipe
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

            int last = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            Log.d("datecheck", Integer.toString(last));
            Log.d("datecheck2", Integer.toString(datenow));

            if(datenow == last)
            {

                AlertDialog.Builder alertDlg = new AlertDialog.Builder(LoadFood.this) ;
                alertDlg.setTitle("??????") ;
                alertDlg.setMessage("???????????? ????????? ????????????.") ;
                alertDlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }) ;
                alertDlg.show() ;
            }
            else
            {
                datenow++;
                daynow++;
                if(daynow == 8) daynow = 1;
                String textweek = getnow(daynow);
                t1.setText(year + "." + month + "." + Integer.toString(datenow) + "." + textweek);
                if(now.equals("??????"))
                {
                    t2.setText(breakfast[datenow-1]);
                }
                else if(now.equals("??????"))
                {
                    t2.setText(lunch[datenow-1]);
                }
                else if(now.equals("??????"))
                {
                    t2.setText(dinner[datenow-1]);
                }
            }

        }
        // left to right swipe
        else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
            Log.d("found", Integer.toString(datenow));
            if(datenow == 1)
            {
                AlertDialog.Builder alertDlg = new AlertDialog.Builder(LoadFood.this) ;
                alertDlg.setTitle("??????") ;
                alertDlg.setMessage("???????????? ???????????????.") ;
                alertDlg.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }) ;
                alertDlg.show();
            }
            else
            {
                datenow--;
                daynow--;
                if(daynow == 0) daynow = 7;
                String textweek = getnow(daynow);
                t1.setText(year + "." + month + "." + Integer.toString(datenow) + "." + textweek);
                if(now.equals("??????"))
                {
                    t2.setText(breakfast[datenow-1]);
                }
                else if(now.equals("??????"))
                {
                    t2.setText(lunch[datenow-1]);
                }
                else if(now.equals("??????"))
                {
                    t2.setText(dinner[datenow-1]);
                }
            }


        }
        return false;
    }
    public String getnow(int dayweek)
    {
        String imsi = null;
        if ( dayweek == 1 )
            imsi = "(???)";
        else if ( dayweek == 2 )
            imsi = "(???)";
        else if ( dayweek == 3 )
            imsi = "(???)";
        else if ( dayweek == 4 )
            imsi ="(???)";
        else if ( dayweek == 5 )
            imsi = "(???)";
        else if ( dayweek == 6 )
            imsi = "(???)";
        else if ( dayweek == 7 )
            imsi = "(???)";
        return imsi;
    }



}
