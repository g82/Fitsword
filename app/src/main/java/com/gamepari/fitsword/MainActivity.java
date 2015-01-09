package com.gamepari.fitsword;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.gamepari.fitsword.game.Mode;
import com.gamepari.fitsword.provider.FitDataProvider;


public class MainActivity extends ActionBarActivity implements FitDataProvider.OnDataProvideListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().beginTransaction().add(R.id.fragment_container, RestFragment.newInstance(null, null)).commit();

        FitDataProvider fitDataProvider = new FitDataProvider(this);
        fitDataProvider.execute();
    }

    @Override
    public void onDataLoaded(Object o) {

    }

    public void changeFragmentByMode(Mode mode) {

        Fragment fragment = null;

        switch (mode) {
            case FIGHT:
                fragment = FightFragment.newInstance(null, null);
                break;

            case REST:
                fragment = RestFragment.newInstance(null, null);
                break;

            case UPGRADE:
                fragment = UpgradeFragment.newInstance(null, null);
                break;
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
