package com.prizm.myschool;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class EasterEgg extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.easteregg);
    }

    
}
