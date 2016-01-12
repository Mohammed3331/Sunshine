package com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG=MainActivity.class.getSimpleName();
    private ShareActionProvider mShareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
           startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }
        if(id==R.id.action_map){
            openPrefferedLocationInMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void openPrefferedLocationInMap(){
        SharedPreferences sharedPrefs= PreferenceManager.getDefaultSharedPreferences(this);
        String location =sharedPrefs.getString(getString(R.string.pref_location_key),getString(R.string.pref_location_default));

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation =Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q",location).build();

        Intent intent = new Intent();
        intent.setData(geoLocation);

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }else {
            Log.d(LOG_TAG, "Couldn't call" + location + ", no receiving apps installed!");
        }

    }
}
