package com.prizm.myschool;

import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

public class ABArrayAdapter extends ArrayAdapter<Adapter> {
        private ArrayList<Adapter> items;
        private int rsrc;
        
        public ABArrayAdapter(Context ctx, int rsrcId, ArrayList<Adapter> data) {
            super(ctx, rsrcId, data);
            this.items = data;
            this.rsrc = rsrcId;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = li.inflate(rsrc, null);
            } 
            Adapter e = items.get(position);
            if (e != null) {
                ((TextView)v.findViewById(R.id.date)).setText(e.getdate());
                ((TextView)v.findViewById(R.id.schedule)).setText(e.getschedule());
            }
            return v;
        }

        public boolean isEnabled(int position)
        {
            return false;
        }


}
