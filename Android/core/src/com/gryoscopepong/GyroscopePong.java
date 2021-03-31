package com.gryoscopepong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Align;

import java.io.FileInputStream;

import javax.crypto.spec.PSource;

public class GyroscopePong implements Screen {
	Paddle playerOne, AI;
	Ball ball;
	BitmapFont playerScore, AIScore;
	SpriteBatch batch;

	private MainScreen mainScreen;

	public GyroscopePong(MainScreen ms){
		mainScreen = ms;
		System.out.println("oh hello");

		create();
/*
		while(true){
			render(0);
			show();
		}

		 */
	}

	//TODO
	//scores on top of screens
	//line divider down the middle?
	//sound effect?
	//then all the screens, popups, buttons
	//should have AI vs AI in background on home screen
	//and save/load data

	public class T extends SpriteBatch{

	}


	public class Paddle {
		int x, y;
		int originalX, originalY;
		int speed;
		int dy;
		int moveTo = 0;
		boolean moving = false;
		ShapeRenderer s;
		int score;

		Paddle(int x, int y){
			this.x = x;
			this.y = y;
			this.originalX = x;
			this.originalY = y;
			this.speed = 1;
			this.score = 0;
			s = new ShapeRenderer();
			s.setColor(1,1,1,1);//white
		}

		public String getScore(){
			return Integer.toString(this.score);
		}

		public void draw(){
			s.begin(ShapeRenderer.ShapeType.Filled);
			moveTowards();
			s.rect(x, y, 50, 250);
			s.end();
		}

		public void moveTowards(){
			if(moving){
				this.y = this.y + this.dy;
				//System.out.println((this.y + 125) + " >= " + moveTo + " >= " + (this.y - 125));
				if(this.y + 75 >= moveTo && this.y - 75 <= moveTo) moving = false;
			}
		}

		public void moveUp(){
			y+=1;
		}

		public void moveDown(){
			y-=1;
		}

		public void moveBy(float val){
			//System.out.println(y);
			int newPosition = (int) ((y) + ((val/90) * 25));
			if(newPosition > 0 && newPosition < 800) y = newPosition;
		}

		public int[] getLocation(){
			int[] ret = {this.x, this.y, this.x + 50, this.y + 250};
			return ret;
		}

		public void reset(){
			x = originalX;
			y = originalY;
		}

		public void prepareForVolley(){

			//this works for very small values of DY
			//i.e. values that don't send the ball off screen
			//so that's the real challenge - how to compute on a smaller basis (i.e. I can't do the whole *190 at once), so I can check if we go off screen
			//loop in worst case scenario -
			//start at 2, go to 190
			//at each "toY" and "toX" see if would trigger hit screen
			//if so, update values here as part of prediction

			if(moving) moving = false;

			int toY, toX;
			int ballDXCopy = -ball.dx;
			int ballDYCopy = ball.dy;
			toY = ball.y;
			toX = ball.x;


			for(int i =1; i<191; i++){
				toY = toY + (ballDYCopy * 1);
				toX = toX + (ballDXCopy * 1);
				if(toY <= 0 || toY >= 1050) ballDYCopy*=-1;
			}


			//toY = ball.y + (ball.dy * 190);
			//toX = ball.x + (-ball.dx * 190);
			//System.out.println("predicted X: " + toX + " predicted Y: " + toY);

			//this jumps the paddle there
			//will add code to gradually move toward points instead
			//this.x = toX;//we shouldn't change this - can only move up/down
			//this.y = toY - 100;//offset paddle size, try to hit in middle



			this.moveTo = toY - 100;
			this.moving = true;
			if(this.moveTo > this.y) this.dy = this.speed * 1;
			if(this.moveTo < this.y) this.dy = this.speed * -1;
			//this.speed++;


			//how to deal with hitting floor and ceiling?
		}

	}

	public class Ball {
		int x, y;
		int dy, dx;
		int originalX, originalY, originalDX, originalDY;
		ShapeRenderer s;

		Ball(int x, int y){
			this.x = x;
			this.y = y;
			this.dx = -10;
			this.dy = 0;
			//this.dy = 0;
			originalDX = -10;
			originalDY = 0;
			//originalDY = 0;
			originalX = x;
			originalY = y;
			s = new ShapeRenderer();
			s.setColor(1,1,1,1);//white
		}

		public void draw(){
			s.begin(ShapeRenderer.ShapeType.Filled);
			s.rect(x, y, 50, 50);
			s.end();

			if(collision()){
				//System.out.println("\n\n\n\nCOLLIDE\n\n\n\n");
				setDx(this.dx * -1);
			}

			if(hitScreen()){
				setDy(-dy);
			}

			if(aiScores()){
				//System.out.println("GOAL");
				reset();
				playerOne.reset();
				AI.reset();
			}

			if(playerScores()){
				//System.out.println("GOAL");
				reset();
				playerOne.reset();
				AI.reset();
			}

			x += dx;
			y += dy;

		}

		private boolean aiScores() {
			int[] p1 = playerOne.getLocation();
			//System.out.println(this.x + " VS " + p1[0]);
			if(this.x < p1[0]) {
				AI.score+=1;
				if(AI.score == 3){
					System.out.println("game over");
					dispose();
					mainScreen.setLeaderboardScreen();
				}
				return true;
			}
			return false;
		}

		private boolean playerScores() {
			int[] p2 = AI.getLocation();
			if(this.x+50 > p2[2]){
				playerOne.score+=1;
				return true;
			}
			return false;
		}

		public void calculateNewDY(float midpointOfPaddle){
			int sign = 1;
			float distanceFromCenter = 0;//use abs
			if(this.y > midpointOfPaddle){
				System.out.println(this.y + " > " + midpointOfPaddle);
				sign = 1;
				distanceFromCenter = this.y - midpointOfPaddle;
			}
			else if (this.y < midpointOfPaddle){
				System.out.println(this.y + " < " + midpointOfPaddle);
				sign = -1;
				distanceFromCenter = midpointOfPaddle - this.y;
			}
			else if(this.y == midpointOfPaddle){
				System.out.println(this.y + " = " + midpointOfPaddle);
				dy= 0;
				return;
			}

			this.dy = (int) (sign * ((distanceFromCenter/125) * 25));
			System.out.println(((distanceFromCenter/125) * 10) + ", so set dy to " + this.dy);
		}

		public boolean collision(){
			int[] p1, p2;
			p1 = playerOne.getLocation();
			//int topRight = this.x - 10 ;
			//int topLeft = this.x + 10;
			//System.out.println("ball - X: " + this.x + " Y: " + this.y);
			//System.out.println("paddle - X: " + p1[0] + " range: "+ p1[2]+ " Y: " + p1[1] + " range: " + p1[3]);
			if(this.x >= p1[0] && this.x <= p1[2] && this.y >= p1[1] && this.y <= p1[3]){//player hits, tell AI to get ready for response
				ball.calculateNewDY(p1[1] + 100);
				AI.prepareForVolley();
				return true;
			}

			p2 = AI.getLocation();
			//System.out.println("ball - X: " + this.x + " Y: " + this.y);
			//System.out.println("paddle - X: " + p2[0] + " range: "+ p2[2]+ " Y: " + p2[1] + " range: " + p2[3]);
			if(this.x+50 <= p2[2] && this.x+50 >= p2[0] && this.y >= p2[1] && this.y <= p2[3]){
				ball.calculateNewDY(p2[1] + 100);
				return true;
			}


			return false;

		}

		public boolean hitScreen(){
			if(this.y <= 0 || this.y >= 1050) return true;
			return false;
		}

		public void moveUp(){
			y+=1;
		}

		public void moveDown(){
			y-=1;
		}

		public void move(int val){
			int newPositionY = y+ val;
			if(newPositionY > 0 && newPositionY < 800) y = newPositionY;
		}

		public void setDy(int dy) {
			this.dy = dy;
		}

		public void setDx(int dx) {
			this.dx = dx;
		}

		public void reset(){
			x = originalX;
			y = originalY;
			dx = originalDX;
			dy = originalDY;
		}

	}
	
	//@Override
	public void create () {
		AI = new Paddle(2000,400);
		playerOne = new Paddle(50,400);
		ball = new Ball(1000,500);
		//ball.setDx(-10);
		batch = new SpriteBatch();
		playerScore = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
		playerScore.getData().setScale(-5, 5);//negative  to mirror/flip
		playerScore.setColor(Color.WHITE);
		AIScore = new BitmapFont(Gdx.files.internal("fonts/dot.fnt"), true);
		AIScore.getData().setScale(-5, 5);//negative  to mirror/flip
		AIScore.setColor(Color.WHITE);
	}

	//@Override
	public void render () {
		float pitch = Gdx.input.getPitch();
		//this gets Z-rotation

		//it starts at 0, tilting up goes to 90, tilting down goes to -90

		Gdx.gl.glClearColor(0, 0, 0, 1);//black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerOne.draw();
		AI.draw();
		ball.draw();
		batch.begin();
		playerScore.draw(batch, playerOne.getScore(), 600, 175);
		AIScore.draw(batch, AI.getScore(), 1600, 175);
		batch.end();
		playerOne.moveBy(pitch);


		//if(pitch > 0) playerOne.moveUp();
		//else if(pitch < 0) playerOne.moveDown();


		/*
		s.begin(ShapeRenderer.ShapeType.Filled);
		s.rect(2000, 400, 50, 250);
		s.rect(50, 400, 50, 250);
		s.setColor(1, 1 ,1, 1);
		s.end();

		 */
	}


	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		render();
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
		playerOne.s.dispose();
		AI.s.dispose();
		ball.s.dispose();
		batch.dispose();
		playerScore.dispose();
		AIScore.dispose();
	}
}
