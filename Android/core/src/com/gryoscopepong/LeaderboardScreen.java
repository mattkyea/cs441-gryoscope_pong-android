package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class LeaderboardScreen implements Screen {

    private MainScreen mainScreen;
    private BitmapFont font;
    private SpriteBatch batch;

    public LeaderboardScreen(MainScreen ms){
        mainScreen = ms;
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);

        //I'll put code to read scores here, and I'll print the top 5 or so
        //read file

        //need to figure out how to get context in libGDX
        try {
            FileInputStream input = getApplicationContext().openFileInput("save_data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(input, Charset.forName("UTF-8"));//byte by byte
            BufferedReader reader = new BufferedReader(inputStreamReader);//just for ease of use
            String line = reader.readLine();

            while(line != null){
                System.out.println(line);
                line = reader.readLine();
            }

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //save file
        try{
            FileOutputStream outputStream = getApplicationContext().openFileOutput("save_data.txt", Context.MODE_APPEND);
            outputStream.write("test data to write\n".getBytes(Charset.forName("UTF-8")));
            System.out.println("wrote data");
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

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
