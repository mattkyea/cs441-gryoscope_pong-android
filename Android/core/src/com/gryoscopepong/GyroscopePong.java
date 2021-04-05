package com.gryoscopepong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

import java.io.FileInputStream;

import javax.crypto.spec.PSource;

public class GyroscopePong implements Screen {
	BitmapFont playerScore, AIScore;
	SpriteBatch batch;
	Stage stage;
	private Game game;

	private MainClass mainScreen;

	public GyroscopePong(MainClass ms, Stage s){
		mainScreen = ms;
		stage = s;
		Sound ballHit = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
		Paddle AI = new Paddle((Gdx.graphics.getWidth()) - 50, (Gdx.graphics.getHeight()/2) - 125, true, 2);
		Paddle playerOne = new Paddle(0,(Gdx.graphics.getHeight()/2) - 125, false, 0);
		Ball ball = new Ball((Gdx.graphics.getWidth()/2)  - 25,(Gdx.graphics.getHeight()/2) - 25, -10, 0);
		game = new Game(AI, playerOne, ball, ballHit);

		batch = new SpriteBatch();
		playerScore = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
		playerScore.getData().setScale(-5, 5);//negative  to mirror/flip
		playerScore.setColor(Color.WHITE);
		AIScore = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
		AIScore.getData().setScale(-5, 5);//negative  to mirror/flip
		AIScore.setColor(Color.WHITE);
	}


	@Override
	public void render(float delta) {
		float pitch = Gdx.input.getPitch();
		//this gets Z-rotation
		//it starts at 0, tilting up goes to 90, tilting down goes to -90
		Gdx.gl.glClearColor(0, 0, 0, 1);//black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		playerScore.draw(batch, game.getRightSide().getScore(), (Gdx.graphics.getWidth()/2) - (Gdx.graphics.getWidth()/4), 175);
		AIScore.draw(batch, game.getLeftSide().getScore(), (Gdx.graphics.getWidth()/2) + (Gdx.graphics.getWidth()/4), 175);
		batch.end();
		game.play();
		game.getRightSide().moveBy(pitch);
		if(game.getLeftSide().score == 3){
			System.out.println("game over");
			stage.clear();
			mainScreen.setScoreEntry(game.getRightSide().score, stage);
		}
	}

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
	public void dispose () {
		batch.dispose();
		playerScore.dispose();
		AIScore.dispose();
	}
}
