package com.prizm.myschool;

import android.util.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;

public class Utils
{
    static Source source;
    URL nURL;


    public static String[] getSchedule(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schoolname)
    {
        String[] date = new String[35];
        String url = "http://hes." + CountryCode + "/sts_sci_sf01_001.do?schulCode=" + schulCode + "&insttNm=" + schoolname +  "&schulCrseScCode=" + schulCrseScCode + "&schulKndScCode=" + schulKndScCode;
        Log.d("find", url);
        try {
            source = new Source(new URL(url));
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.d("something","some");
        source.fullSequentialParse();
        List table = source.getAllElements("table");
        for (int i = 0; i < table.size(); i++)
        {
            Log.d("something1","some");
            if (((Element)table.get(i)).getAttributeValue("class").equals("tableType2")) {
                Log.d("something2","some");
                List tbody = ((Element)table.get(i)).getAllElements("tbody");
                List tr = ((Element)tbody.get(0)).getAllElements("tr");
                for (int j = 0; j < tr.size(); j++)
                {
                    Log.d("something3","some");
                    List td = ((Element)tr.get(j)).getAllElements("td");
                    for (int k = 0; k < td.size(); k++)
                    {
                        Log.d("something4","some");
                        List span = ((Element)td.get(k)).getAllElements("span");
                        String check = ((Element)span.get(0)).getContent().toString();
                        if (!check.equals(""))
                        {
                            Log.d("something5","some");
                            for (int e = 1; e < span.size(); e++)
                            {
                                String parsedate = ((Element)span.get(e)).getContent().toString();
                                int datenumber = Integer.parseInt(check);
                                if (e == 1)
                                {
                                    date[datenumber] = "";
                                    date[datenumber] = (date[datenumber] + ((Element)span.get(e)).getContent().toString());
                                }
                                else
                                {
                                    date[datenumber] = (date[datenumber] + ((Element)span.get(e)).getContent().toString() + " ");
                                }
                                date[datenumber].replace("null", "");
                                Log.d("wow", date[datenumber]);
                                Log.d("wow1", Integer.toString(datenumber));
                            }
                        }
                    }
                }
            }
        }

        return date;
    }



}