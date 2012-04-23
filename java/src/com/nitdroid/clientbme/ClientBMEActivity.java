package com.nitdroid.clientbme;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientBMEActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateBMEInfo();
    }

	// This method is called at button click because we assigned the name to the
	// "On Click property" of the button
	public void buttonHandler(View view) {
		updateBMEInfo();
	}

    protected void updateBMEInfo() {
        BMEReader reader = new BMEReader();
        BMEInfo info = reader.getInfo();
        TextView view = (TextView) findViewById(R.id.info);
        if (info != null)
        	view.setText(info.toString());
        else
        	view.setText("Can't connect to BME. Reboot your phone.");
    }
}
