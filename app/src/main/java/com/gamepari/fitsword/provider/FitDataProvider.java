package com.gamepari.fitsword.provider;

import android.os.AsyncTask;

/**
 * Created by gamepari on 1/8/15.
 */
public class FitDataProvider extends AsyncTask<Void, Void, Object> {

    OnDataProvideListener mOnDataProvideListener;

    public FitDataProvider(OnDataProvideListener mOnDataProvideListener) {
        this.mOnDataProvideListener = mOnDataProvideListener;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        mOnDataProvideListener.onDataLoaded(o);
    }

    public interface OnDataProvideListener {
        public void onDataLoaded(Object o);
    }


}
