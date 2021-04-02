package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;

import javax.swing.GroupLayout;

public class ScoreEntry implements Screen {

    MainClass mainClass;
    Stage stage;
    int score;
    String initials;
    private BitmapFont font;
    SpriteBatch batch;



    public ScoreEntry(MainClass mc, int sc, Stage stage) {
        mainClass = mc;
        this.stage = stage;
        score = sc;
        batch = new SpriteBatch();

        Skin mySkin = new Skin(Gdx.files.internal("skins/plain-james-ui.json"));
        TextField scoreTextField = new TextField("", mySkin);
        scoreTextField.getStyle().font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        scoreTextField.getStyle().font.getData().setScale(-2, 2);

        scoreTextField.getStyle().cursor = null;
        scoreTextField.setAlignment(Align.center);
        scoreTextField.setPosition((Gdx.graphics.getWidth()/2)-250,Gdx.graphics.getHeight()/2);
        scoreTextField.setSize(500, 150);


        stage.addActor(scoreTextField);

        scoreTextField.setMaxLength(3);

        scoreTextField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                initials = textField.getText();
            }
        });



        TextButton enterButton = new TextButton("Confirm", mySkin);
        enterButton.setTransform(true);
        enterButton.setColor(Color.WHITE);
        enterButton.setSize(500,100);
        enterButton.setRotation(180);
        enterButton.setPosition((Gdx.graphics.getWidth()/2) + 250,(Gdx.graphics.getHeight()/2) + 500);
        enterButton.getLabel().setFontScale(2f);

        enterButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("click");
                FileHandle input = Gdx.files.local("scores.txt");
                input.writeString(initials + " : " + score + ";", true);
                mainClass.setLeaderboardScreen();
                return true;
            }
        });

        stage.addActor(enterButton);


        font = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
        font.getData().setScale(-5, 5);//negative  to mirror/flip
        font.setColor(Color.WHITE);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);//black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        batch.begin();
        font.draw(batch, "Enter Initials", (Gdx.graphics.getWidth()/2) + 700,(Gdx.graphics.getHeight()/2) - 400);
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
