package com.gamepari.fitsword;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gamepari.fitsword.game.GameDirector;
import com.gamepari.fitsword.game.Mode;
import com.gamepari.fitsword.provider.FitData;
import com.gamepari.fitsword.provider.FitDataProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;

import java.util.List;


public class MainActivity extends ActionBarActivity implements FitDataProvider.OnDataProvideListener {

    private static final String TAG = "MainActivity";
    private static final int REQUEST_OAUTH = 1;
    private static final String AUTH_PENDING = "auth_state_pending";
    private GoogleApiClient apiClient;
    private boolean authInProgress = false;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }

        buildGoogleClient();

        progressDialog = ProgressDialog.show(this, null, "Access Google Fit...", true, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!apiClient.isConnecting() && !apiClient.isConnected()) {
                    apiClient.connect();
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    private void buildGoogleClient() {

        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                        Log.d(TAG, "onConnected");

                        //using Google Fit Api
                        new FitDataProvider(MainActivity.this, apiClient).execute();

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                        Log.d(TAG, "onConnectionSuspended");

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {

                        Log.i(TAG, "Connection failed. Cause: " + result.toString());
                        if (!result.hasResolution()) {
                            // Show the localized error dialog
                            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
                                    MainActivity.this, 0).show();
                            return;
                        }
                        // The failure has a resolution. Resolve it.
                        // Called typically when the app is not yet authorized, and an
                        // authorization dialog is displayed to the user.
                        if (!authInProgress) {
                            try {
                                Log.i(TAG, "Attempting to resolve failed connection");
                                authInProgress = true;
                                result.startResolutionForResult(MainActivity.this,
                                        REQUEST_OAUTH);
                            } catch (IntentSender.SendIntentException e) {
                                Log.e(TAG,
                                        "Exception while starting resolution activity", e);
                            }
                        }
                    }
                })
                .addApi(Fitness.API)
                .addScope(Fitness.SCOPE_ACTIVITY_READ)
                .build();
    }


    /**
     * FitDataProvider.OnDataProvideListener Callbacks.<br/>
     * Adapting GoogleFit Data & Game File Data to GameDirector
     *
     * @param o return Object
     */
    @Override
    public void onDataLoaded(Object o) {

        progressDialog.dismiss();

        //--game start--
        GameDirector gameDirector = GameDirector.getInstance();
        gameDirector.init(this, (List<FitData>) o);

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
