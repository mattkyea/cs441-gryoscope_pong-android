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

    private MainClass mainClass;

    private Stage stage;//what we interact with on screen
    private BitmapFont font;
    private SpriteBatch batch;
    private Game game;

    public MainScreen(MainClass mc) {
        System.out.println("in main screen");
        mainClass = mc;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //set up buttons
        //starts with a skin - a style/look for the buttons
        //found this one along with others at https://github.com/czyzby/gdx-skins
        Skin mySkin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));

        TextButton playButton = new TextButton("Play Pong", mySkin);
        playButton.setTransform(true);
        playButton.setColor(Color.WHITE);
        playButton.setSize(500,100);
        playButton.setRotation(180);
        playButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 50);
        playButton.getLabel().setFontScale(2f);

        playButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                mainClass.setGyroscopePong(stage);
                return true;
            }
        });

        TextButton instructionsButton = new TextButton("Rules", mySkin);
        instructionsButton.setTransform(true);
        instructionsButton.setSize(500,100);
        instructionsButton.setColor(Color.WHITE);
        instructionsButton.setRotation(180);
        instructionsButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 250);
        instructionsButton.getLabel().setFontScale(2f);

        instructionsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                stage.clear();
                mainClass.setRulesScreen(stage);
                return true;
            }
        });

        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);


        stage.addActor(playButton);
        stage.addActor(instructionsButton);
        //render(1);


        Sound ballHit = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
        //AI = new Paddle(2000,400);
        Paddle AI = new Paddle((Gdx.graphics.getWidth()) - 50, (Gdx.graphics.getHeight()/2) - 125, true, 3);
        Paddle playerOne = new Paddle(0,(Gdx.graphics.getHeight()/2) - 125, true, 3);
        Ball ball = new Ball((Gdx.graphics.getWidth()/2)  - 25,(Gdx.graphics.getHeight()/2) - 25, -10, -1);

        game = new Game(AI, playerOne, ball, ballHit);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //System.out.println("render");
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();
        font.draw(batch, "Gyroscope Pong", (Gdx.graphics.getWidth()/2) + 700, (Gdx.graphics.getHeight()/2) -350);
        batch.end();
        game.play();
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
