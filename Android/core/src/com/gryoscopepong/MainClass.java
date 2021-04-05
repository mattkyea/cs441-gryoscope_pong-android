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

/*
 This is a class I can use to display different screens from any other screen, using a singleton pattern.
 */
public class MainClass extends Game implements ApplicationListener {

    //variables for each of our screens
    private GyroscopePong game;
    private LeaderboardScreen leaderboardScreen;
    private RulesScreen rulesScreen;
    private MainScreen mainScreen;
    private ScoreEntry scoreEntry;


    //functions to set each of our screens as the current screen
    //some accept additional parameters where necessary
    void setGyroscopePong(Stage s){
        game = new GyroscopePong(this, s);
        setScreen(game);
    }

    void setLeaderboardScreen(){
        leaderboardScreen = new LeaderboardScreen(this);
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

    void setScoreEntry(int score, Stage stage){
        scoreEntry = new ScoreEntry(this, score, stage);
        setScreen(scoreEntry);
    }


    //execution starts here, instantly open main/start/splash screen
    @Override
    public void create() {
        setMainScreen();
    }


    //necessary functions for implementing Screen

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
