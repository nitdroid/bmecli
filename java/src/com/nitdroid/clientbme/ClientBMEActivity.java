package com.nitdroid.clientbme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ClientBMEActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        BMEReader reader = new BMEReader();
        BMEInfo info = reader.getInfo();
        TextView view = (TextView) findViewById(R.id.info);
        if (info != null)
        	view.setText(info.toString());
        else
        	view.setText("Can't connect to BME. Reboot your phone.");
    }
}
