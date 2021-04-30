package com.prizm.myschool;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.Preference;
import android.widget.Toast;
import android.renderscript.ProgramVertexFixedFunction;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;


public class Setting extends PreferenceActivity implements OnPreferenceClickListener {


    Boolean go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.setting);

        Preference wipeschool = (Preference)findPreference("wipe_school");
        Preference introduce = (Preference)findPreference("introduce");

        wipeschool.setOnPreferenceClickListener(this);
        introduce.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference)
    {
        if(preference.getKey().equals("wipe_school"))
        {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(Setting.this);
            alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(getResources().getString(R.string.app_name), "OK");
                    SharedPreferences prefs = getSharedPreferences("PrefName", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("schulcode", "");
                    editor.putString("schulcrsesccode", "");
                    editor.putString("schulkndsccode", "");
                    editor.putString("datearea", "");
                    editor.putString("area", "");
                    editor.putString("schoolname", "");
                    editor.putBoolean("isFirstRun", true);
                    editor.commit();

                    SharedPreferences settings = getSharedPreferences("first", 0);
                    SharedPreferences.Editor editor1 = settings.edit();
                    editor1.putBoolean("isFirstRun", true);
                    editor1.commit();
                }
            });
            alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i(getResources().getString(R.string.app_name), "CANCEL");
                }
            });
            alertDialog.setMessage("초기화 하시겠습니까?");
            alertDialog.show();
        }
        else if(preference.getKey().equals("introduce"))
        {
            Intent i = new Intent(this, Developer.class);
            startActivity(i);
        }
        return false;
    }



}
