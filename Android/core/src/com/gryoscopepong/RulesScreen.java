package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/*
 Basic screen to display rules.
 */
public class RulesScreen implements Screen {

    //variables
    private MainClass mainScreen;//singleton
    //2 fonts and the batch needed to draw them
    private BitmapFont rulesFont;
    private BitmapFont headerFont;
    private SpriteBatch batch;
    private Stage stage;//what we interact with on screen

    public RulesScreen(MainClass ms, Stage s){
        mainScreen = ms;
        stage = s;

        //set up buttons
        //starts with a skin - a style/look for the buttons
        //found this one along with others at https://github.com/czyzby/gdx-skins
        Skin mySkin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));

        //set up exit button
        TextButton exitButton = new TextButton("Exit Rules", mySkin);
        exitButton.setTransform(true);
        exitButton.setColor(Color.WHITE);
        exitButton.setSize(500,100);
        exitButton.setRotation(180);
        exitButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 500);
        exitButton.getLabel().setFontScale(2f);

        //on clicking exit button
        exitButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                mainScreen.setMainScreen();//send user back to the main screen
                return true;
            }
        });

        //set up the 2 fonts
        batch = new SpriteBatch();
        //this one is larger, says "rules"
        headerFont = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        headerFont.getData().setScale(-5, 5);//negative  to mirror/flip
        headerFont.setColor(Color.WHITE);

        //this one is smaller, lists the rules
        rulesFont = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        rulesFont.getData().setScale(-1, 1);//negative  to mirror/flip
        rulesFont.setColor(Color.WHITE);


        stage.addActor(exitButton);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //draw fonts
        headerFont.draw(batch, "Rules:", (Gdx.graphics.getWidth()/2) + 250 , (Gdx.graphics.getHeight()/2) -400);
        rulesFont.draw(batch, "This is a twist on the classic game Pong. \n\n\n" +
                "\tYour paddle is on the right side, and you are playing against the computer. \n\n\n" +
                "You move your paddle by tilting your phone left and right to move it up and down respectively.\n\n\n" +
                "A steeper tilt moves the paddle faster.\n\n\n" +
                "The game ends once the AI has scored 3 points.\n\n\n", (Gdx.graphics.getWidth()/2) + 1000, (Gdx.graphics.getHeight()/2) -150);

        batch.end();
        //add stage so button drawn as well
        stage.act();
        stage.draw();
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
