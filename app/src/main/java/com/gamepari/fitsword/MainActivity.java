package com.gamepari.fitsword;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.gamepari.fitsword.game.GameDirector;
import com.gamepari.fitsword.game.Mode;
import com.gamepari.fitsword.provider.FitDataProvider;


public class MainActivity extends ActionBarActivity implements FitDataProvider.OnDataProvideListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //using Google Fit Api
        new FitDataProvider(this).execute();
    }


    /**
     * FitDataProvider.OnDataProvideListener Callbacks.<br/>
     * Adapting GoogleFit Data & Game File Data to GameDirector
     *
     * @param o return Object
     */
    @Override
    public void onDataLoaded(Object o) {

        //TODO game start
        GameDirector gameDirector = GameDirector.getInstance();
        gameDirector.setMainActivity(this);

        gameDirector.changeMode(Mode.REST);

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
