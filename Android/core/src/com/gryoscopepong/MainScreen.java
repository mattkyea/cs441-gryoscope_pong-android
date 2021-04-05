package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

public class MainScreen implements Screen {

    private MainClass mainClass;//singleton

    private Stage stage;//what we interact with on screen
    private BitmapFont font;//to display text
    private SpriteBatch batch;//needed to display text
    private Game game;//to show AI vs AI on main screen

    public MainScreen(MainClass mc) {
        mainClass = mc;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //set up buttons
        //starts with a skin - a style/look for the buttons
        //found this one along with others at https://github.com/czyzby/gdx-skins
        Skin mySkin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));

        //this is the play button
        TextButton playButton = new TextButton("Play Pong", mySkin);
        playButton.setTransform(true);
        playButton.setColor(Color.WHITE);
        playButton.setSize(500,100);
        playButton.setRotation(180);
        playButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 50);
        playButton.getLabel().setFontScale(2f);

        //event handler for play button
        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();//clear stage
                mainClass.setGyroscopePong(stage);//open game screen
                return true;
            }
        });

        //set up rules button
        TextButton instructionsButton = new TextButton("Rules", mySkin);
        instructionsButton.setTransform(true);
        instructionsButton.setSize(500,100);
        instructionsButton.setColor(Color.WHITE);
        instructionsButton.setRotation(180);
        instructionsButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 250);
        instructionsButton.getLabel().setFontScale(2f);

        //event handler for rules button
        instructionsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                mainClass.setRulesScreen(stage);//open rules screen
                return true;
            }
        });

        //set up font for name of game
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);

        //add buttons to stage
        //buttons are drawn on screen via the stage
        stage.addActor(playButton);
        stage.addActor(instructionsButton);

        //set up AI vs AI game
        Sound ballHit = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));//sound effect
        //2 paddles
        Paddle leftAI = new Paddle((Gdx.graphics.getWidth()) - 50, (Gdx.graphics.getHeight()/2) - 125, true, 3);
        Paddle rightAI = new Paddle(0,(Gdx.graphics.getHeight()/2) - 125, true, 3);
        //the ball, which is moving up and to the right (up because it creates an interesting game, head on would have no effect)
        Ball ball = new Ball((Gdx.graphics.getWidth()/2)  - 25,(Gdx.graphics.getHeight()/2) - 25, -10, -1);
        game = new Game(leftAI, rightAI, ball, ballHit);//set up the game

    }




    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //stage will do the buttons
        stage.act();
        stage.draw();
        //batch and font will do text
        batch.begin();
        font.draw(batch, "Gyroscope Pong", (Gdx.graphics.getWidth()/2) + 700, (Gdx.graphics.getHeight()/2) -350);
        batch.end();
        //game will run AI vs AI game
        game.play();
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
