package com.gryoscopepong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import javax.naming.Context;

public class MainScreen extends Game implements ApplicationListener {

    private GyroscopePong game;
    private LeaderboardScreen leaderboardScreen;
    private Context c;

    public MainScreen(Context context) {
        c = context;
    }

    void setGyroscopePong(){
        game = new GyroscopePong(this);
        setScreen(game);
    }

    void setLeaderboardScreen(){
        leaderboardScreen = new LeaderboardScreen(this);
        setScreen(leaderboardScreen);
    }

    @Override
    public void create() {
        System.out.println("hi");
        setGyroscopePong();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
