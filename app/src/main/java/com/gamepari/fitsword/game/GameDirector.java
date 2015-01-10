package com.gamepari.fitsword.game;

import com.gamepari.fitsword.MainActivity;
import com.gamepari.fitsword.provider.FitData;

import java.util.List;

/**
 * Created by gamepari on 1/8/15.
 */
public class GameDirector {

    private static GameDirector singleton;

    private MainActivity mainActivity;

    private Player mPlayer;
    private List<FitData> listFitData;


    private GameDirector() {
        mPlayer = new Player();
    }

    public static GameDirector getInstance() {

        if (singleton == null) {
            singleton = new GameDirector();
        }

        return singleton;
    }

    public void init(MainActivity mainActivity, List<FitData> fitDatas) {
        this.mainActivity = mainActivity;
        this.listFitData = fitDatas;

        int sumOfsteps = 0;
        for (FitData fit : listFitData) {
            sumOfsteps += fit.getValue();
        }

        getPlayer().setAvailableSteps(sumOfsteps);
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void changeMode(Mode mode) {
        mainActivity.changeFragmentByMode(mode);
    }

}
