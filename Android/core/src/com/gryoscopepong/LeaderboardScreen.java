package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LeaderboardScreen implements Screen {

    private MainScreen mainScreen;
    private BitmapFont font;
    private BitmapFont scoreFont;
    private SpriteBatch batch;
    private String[] scores;
    private ArrayList<Score> scoresToPrint = new ArrayList<>();
    private int entries;


    public class Score{
        int value;
        String initials;

        public Score(int value, String initials){
            this.value = value;
            this.initials = initials;
        }

        @Override
        public String toString() {
            return initials + " ******************************** " + value;
        }
    }


    public LeaderboardScreen(MainScreen ms, int score){
        mainScreen = ms;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);

        scoreFont = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        scoreFont.getData().setScale(-2, 2);//negative  to mirror/flip
        scoreFont.setColor(Color.WHITE);

        //somehow allow user to enter initials
        String initials = "MAK";

        FileHandle input = Gdx.files.local("scores.txt");
        input.writeString(initials + " : " + score + ";", true);

        String contents = input.readString();
        scores = contents.split(";");
        int min = 0;
        entries = 0;
        for(String s : scores){
            String[] parsingScore;
            parsingScore = s.split(" : ");
            Score currScore = new Score(Integer.parseInt(parsingScore[1]), parsingScore[0] );
            scoresToPrint.add(currScore);
        }

        scoresToPrint.sort(new Comparator<Score>() {
            @Override
            public int compare(Score s1, Score s2) {
                return Integer.valueOf(s2.value).compareTo(Integer.valueOf(s1.value));
            }
        });

        //System.out.println(entries);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "game over", 1500, 250);
        int currY = 500;
        for(int i=0; i < 7; i++){
            scoreFont.draw(batch, scoresToPrint.get(i).toString(), 1250, currY);
            currY+=100;
        }
        batch.end();
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
