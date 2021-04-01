package com.gryoscopepong;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import javax.naming.Context;

public class MainClass extends Game implements ApplicationListener {

    private GyroscopePong game;
    private LeaderboardScreen leaderboardScreen;
    private RulesScreen rulesScreen;
    private MainScreen mainScreen;


    void setGyroscopePong(){
        game = new GyroscopePong(this);
        setScreen(game);
    }

    void setLeaderboardScreen(int score){
        leaderboardScreen = new LeaderboardScreen(this, score);
        setScreen(leaderboardScreen);
    }

    void setRulesScreen(Stage s){
        rulesScreen = new RulesScreen(this, s);
        setScreen(rulesScreen);
    }

    void setMainScreen(){
        mainScreen = new MainScreen(this);
        setScreen(mainScreen);
    }



    @Override
    public void create() {
        setMainScreen();
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
