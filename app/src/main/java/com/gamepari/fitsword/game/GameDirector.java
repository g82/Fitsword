package com.gamepari.fitsword.game;

import com.gamepari.fitsword.MainActivity;

/**
 * Created by gamepari on 1/8/15.
 */
public class GameDirector {

    private static GameDirector singletoninstance;

    private MainActivity mainActivity;

    public static GameDirector getInstance() {

        if (singletoninstance == null) {
            singletoninstance = new GameDirector();
        }

        return singletoninstance;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void changeMode(Mode mode) {
        mainActivity.changeFragmentByMode(mode);
    }

}
