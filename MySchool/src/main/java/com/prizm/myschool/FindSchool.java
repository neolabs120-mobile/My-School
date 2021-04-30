package com.prizm.myschool;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;
import android.os.AsyncTask;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.school.library.Schoollibrary;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import java.util.ArrayList;
import java.util.Calendar;

public class FindSchool extends Activity {

    String where;
    String school;
    String address;
    String datearea;
    String schoolname[] = new String[10001];
    String schoolcode[] = new String[10001];
    String schoolknd[] = new String[10001];
    String schoolcrse[] = new String[10001];
    String schoolfood[] = new String[10001];
    String page;
    String schulCode, schulCrseSeCode, sculKndScCode, area;
    String breakfast[] = new String[101];
    String lunch[] = new String[101];
    String dinner[] = new String[101];
    String move[] = new String[101];
    String dateaver[] = new String[9];
    int range;
    private ListView listview;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private ProgressDialog loagindDialog;
    MyFirstMethod myfirst;
    getsomefood somefood;
    StringBuilder builder;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.findschool);

        Button button;
        button = (Button) findViewById(R.id.button);
        final EditText eName = (EditText)findViewById(R.id.Edit1);

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //schulCode=D100000393&schulCrseScCode=3&schulKndScCode=03

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
  /*              AlertDialog alert = new AlertDialog.Builder( FindSchool.this )
                        .setIcon( R.drawable.ic_launcher )
                        .setTitle( "Data" )
                        .setMessage( "schulCode : " + schoolcode[position] + "\nschulCrseScCode : " + schoolcrse[position]+ "\nschulKndScCode : " + schoolknd[position])
                        .setPositiveButton( "OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                            }
                        })
                        .show(); */
                Log.d("informaiton", Integer.toString(position));
                getlink();
                fordate();
                somefood = new getsomefood();
                schulCode = schoolcode[position];
                schulCrseSeCode = schoolcrse[position];
                sculKndScCode = schoolknd[position];
                SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("schulcode", schoolcode[position]);
                editor.putString("schulcrsesccode", schulCrseSeCode);
                editor.putString("schulkndsccode", sculKndScCode);
                editor.putString("datearea", datearea);
                editor.putString("area", where);
                editor.putString("schoolname", schoolname[position]);
                editor.commit();
                somefood.execute("");
                saveit();
                Intent i = new Intent(FindSchool.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        button.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v) {
                school = eName.getText().toString();
                Log.d("dfd", "dfd");
                myfirst=new MyFirstMethod();
                if (where.equals("서울")) {
                    address = "http://hes." + "sen" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("경기도")) {
                    address = "http://hes." + "goe" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("인천광역시")) {
                    address = "http://hes." + "ice" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("강원도")) {
                    address = "http://hes." + "kwe" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("충청북도")) {
                    address = "http://hes." + "cbe" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("충청남도")) {
                    address = "http://hes." + "cne" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("세종특별자치도")) {
                    address = "http://hes." + "sje" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("대전광역시")) {
                    address = "http://hes." + "dje" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("경상북도")) {
                    address = "http://hes." + "gbe" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("경상남도")) {
                    address = "http://hes." + "gne" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("대구광역시")) {
                    address = "http://hes." + "dge" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("울산광역시")) {
                    address = "http://hes." + "use" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("부산광역시")) {
                    address = "http://hes." + "pen" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("전라북도")) {
                    address = "http://hes." + "jbe" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("전라남도")) {
                    address = "http://hes." + "jne" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("광주광역시")){
                    address = "http://hes." + "gen" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                } else if (where.equals("제주도")){
                    address = "http://hes." + "jje" + ".go.kr/spr_ccm_cm01_100.do?kraOrgNm=" + school;
                    myfirst.execute(address);
                }
            }
        });
        Intent intent = getIntent();
        where = intent.getExtras().getString("where");
    }


    public class MyFirstMethod extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FindSchool.this);
            dialog.setMessage("Loading....");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(url);
                ResponseHandler<String> resHandler = new  BasicResponseHandler();
                page = httpClient.execute(httpGet, resHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject jsonObj = new JSONObject(page);
                JSONObject channel = jsonObj.getJSONObject("resultSVO");
                JSONObject data = channel.getJSONObject("data");
                JSONArray items = data.getJSONArray("orgDVOList");
                range = items.length();
                for(int i=0 ; i<items.length() ; i++){
                    JSONObject temp = items.getJSONObject(i);
                    String orgcode = temp.getString("orgCode");
                    String kraorgnm = temp.getString("kraOrgNm");
                    String zipAdres = temp.getString("zipAdres");
                    String schulkndsccode = temp.getString("schulKndScCode");
                    String schulcrsesccode = temp.getString("schulCrseScCode");
                    schoolname[i] = kraorgnm;
                    schoolcode[i] = orgcode;
                    schoolknd[i] = schulkndsccode;
                    schoolcrse[i] = schulcrsesccode;
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            list.clear();
            dialog.dismiss();
            dialog.cancel();
            for(int i = 0; i < range; i++)
            {
                list.add(schoolname[i]);
                adapter.notifyDataSetChanged();
            }
        }


    }

    public class getsomefood extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet("http://meal.lap.so/api/get_data.php?code=" + schulCode + "&area=" + area + "&type=json");
                ResponseHandler<String> resHandler = new  BasicResponseHandler();
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
                    String get = schoolfood[i].replaceAll("\\[중식\\]", ":::");
                    String get1 = get.replaceAll("\\[석식\\]",":::");
                    Log.d("food", get1);
                    String change = "\\:::";
                    if(get1.equals("no_food"))
                    {
                        breakfast[i] = "급식이 없습니다.";
                        lunch[i] = "급식이 없습니다.";
                        dinner[i] = "급식이 없습니다.";
                    }
                    else
                    {
                        move = get1.split(change);
                        Log.d("playwith", move[0]);
                        int go  = move.length;
                        Log.d("good",Integer.toString(go));
                        breakfast[i] = "급식이 없습니다.";
                        lunch[i] = "급식이 없습니다.";
                        dinner[i] = "급식이 없습니다.";
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
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            saveit();
        }


    }

    public String getlink()
    {
        if (where.equals("서울")) {
            area = "sen";
        } else if (where.equals("경기도")) {
            area = "goe";
        } else if (where.equals("인천광역시")) {
            area = "ice";
        } else if (where.equals("강원도")) {
            area = "kwe";
        } else if (where.equals("충청북도")) {
            area = "cbe";
        } else if (where.equals("충청남도")) {
            area = "cne";
        } else if (where.equals("세종특별자치도")) {
            area = "sje";
        } else if (where.equals("대전광역시")) {
            area = "dje";
        } else if (where.equals("경상북도")) {
            area = "gbe";
        } else if (where.equals("경상남도")) {
            area = "gne";
        } else if (where.equals("대구광역시")) {
            area = "dge";
        } else if (where.equals("울산광역시")) {
            area = "use";
        } else if (where.equals("부산광역시")) {
            area = "pen";
        } else if (where.equals("전라북도")) {
            area = "jbe";
        } else if (where.equals("전라남도")) {
            area = "jne";
        } else if (where.equals("광주광역시")){
            area = "gen";
        } else if (where.equals("제주도")){
            area = "jje";
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
