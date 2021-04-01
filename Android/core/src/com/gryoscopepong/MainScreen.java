package com.gryoscopepong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.naming.Context;

public class MainScreen extends Game implements ApplicationListener {

    private GyroscopePong game;
    private LeaderboardScreen leaderboardScreen;
    private Context c;



    void setGyroscopePong(){
        game = new GyroscopePong(this);
        setScreen(game);
    }

    void setLeaderboardScreen(int score){
        leaderboardScreen = new LeaderboardScreen(this, score);
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
