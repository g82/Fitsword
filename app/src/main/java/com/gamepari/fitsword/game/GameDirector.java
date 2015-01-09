package com.gamepari.fitsword.game;

import com.gamepari.fitsword.MainActivity;

/**
 * Created by gamepari on 1/8/15.
 */
public class GameDirector {

    private static GameDirector singleton;

    private MainActivity mainActivity;

    private Player player;


    private GameDirector() {
    }

    public static GameDirector getInstance() {

        if (singleton == null) {
            singleton = new GameDirector();
        }

        return singleton;
    }

    public Player getPlayer() {
        return player;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void changeMode(Mode mode) {
        mainActivity.changeFragmentByMode(mode);
    }

}
