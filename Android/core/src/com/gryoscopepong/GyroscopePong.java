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

/*
 The actual screen displayed during gameplay.
 */
public class GyroscopePong implements Screen {
	BitmapFont playerScore, AIScore;
	SpriteBatch batch;
	Stage stage;
	private Game game;

	private MainClass mainScreen;

	public GyroscopePong(MainClass ms, Stage s){
		mainScreen = ms;
		stage = s;
		//sound, paddles, ball, game
		Sound ballHit = Gdx.audio.newSound(Gdx.files.internal("blip.wav"));
		AIPaddle AI = new AIPaddle((Gdx.graphics.getWidth()) - 50, (Gdx.graphics.getHeight()/2) - 125, 1, Paddle.SIDE.LEFT);
		UserPaddle playerOne = new UserPaddle(0,(Gdx.graphics.getHeight()/2) - 125,  Paddle.SIDE.RIGHT);
		Ball ball = new Ball((Gdx.graphics.getWidth()/2)  - 25,(Gdx.graphics.getHeight()/2) - 25, -10, 0);
		game = new Game(AI, playerOne, ball, ballHit);

		//2 fonts, one for each score
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
		Gdx.gl.glClearColor(0, 0, 0, 1);//black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//draw scores
		batch.begin();
		playerScore.draw(batch, game.getRightSide().getScoreAsString(), (Gdx.graphics.getWidth()/2) - (Gdx.graphics.getWidth()/4), 175);
		AIScore.draw(batch, game.getLeftSide().getScoreAsString(), (Gdx.graphics.getWidth()/2) + (Gdx.graphics.getWidth()/4), 175);
		batch.end();
		//play game
		game.play();
		//check if AI has scored 3 times
		if(game.getLeftSide().getScore() == 3){
			stage.clear();
			mainScreen.setScoreEntry(game.getRightSide().getScore(), stage);
		}
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
	public void dispose () {
		batch.dispose();
		playerScore.dispose();
		AIScore.dispose();
	}
}
