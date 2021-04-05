package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardScreen implements Screen {
    //variables

    private MainClass mainScreen;//singleton
    //2 fonts and the batch needed to draw them
    private BitmapFont font;
    private BitmapFont scoreFont;
    private SpriteBatch batch;
    //variables used to store scores
    private String[] scores;
    private ArrayList<Score> scoresToPrint = new ArrayList<>();
    private int entries;
    private Stage stage;


    //nested class representing a score
    public class Score{
        //made of a int value and string initials
        int value;
        String initials;

        public Score(int value, String initials){
            this.value = value;
            this.initials = initials;
        }

        @Override
        public String toString() {
            return initials + " " + value;
        }
    }


    public LeaderboardScreen(MainClass ms){
        mainScreen = ms;
        batch = new SpriteBatch();
        //set up fonts
        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        scoreFont.getData().setScale(-2, 2);//negative  to mirror/flip
        scoreFont.setColor(Color.WHITE);

        //open the file
        FileHandle input = Gdx.files.local("scores.txt");
        String contents = input.readString();
        //split the contents by ";" (i.e. put each entry as its own element)
        scores = contents.split(";");
        entries = 0;//keep track of how many we'll print
        for(String s : scores){
            String[] parsingScore;
            parsingScore = s.split(" : "); //split score by initials : value
            Score currScore = new Score(Integer.parseInt(parsingScore[1]), parsingScore[0] );//parse to make a new Score object
            scoresToPrint.add(currScore);//and add it to an arraylist
        }

        //then use a custom comparator to sort the values in the Scores
        scoresToPrint.sort(new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                return Integer.valueOf(s2.value).compareTo(Integer.valueOf(s1.value));
            }
        });

        //and print either the top 6 or all entries if there's less than 6
        if(scoresToPrint.size() >= 6) entries = 6;
        else entries = scoresToPrint.size();
    }



    //draw everything on screen
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "game over", (Gdx.graphics.getWidth()/2) + 500,(Gdx.graphics.getHeight()/2) - 400);
        int currY = (Gdx.graphics.getHeight()/2) - 150;
        for(int i=0; i < entries; i++){//loop though entries to print each one below the last
            scoreFont.draw(batch, scoresToPrint.get(i).toString(), (Gdx.graphics.getWidth()/2)+150, currY);
            currY+=100;
        }
        batch.end();

    }

    //necessary functions for implementing Screen

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
