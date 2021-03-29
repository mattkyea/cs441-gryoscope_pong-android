package com.gryoscopepong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.io.FileInputStream;

public class GryoscopePong extends ApplicationAdapter {
	Paddle playerOne, AI;
	Ball ball;


	//TODO
	//trajectory
	//move ball function
	//account for hitting paddles and side of screen
	//basic AI/something that looks smart
	//then all the screens, popups, buttons
	//and save/load data


	public class Paddle {
		int x, y;
		ShapeRenderer s;

		Paddle(int x, int y){
			this.x = x;
			this.y = y;
			s = new ShapeRenderer();
			s.setColor(1,1,1,1);//white
		}

		public void draw(){
			s.begin(ShapeRenderer.ShapeType.Filled);
			s.rect(x, y, 50, 250);
			s.end();
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

	}

	public class Ball {
		int x, y;
		int dy, dx;
		ShapeRenderer s;

		Ball(int x, int y){
			this.x = x;
			this.y = y;
			this.dx = this.dy = 0;
			s = new ShapeRenderer();
			s.setColor(1,1,1,1);//white
		}

		public void draw(){
			s.begin(ShapeRenderer.ShapeType.Filled);
			s.rect(x, y, 50, 50);
			s.end();

			if(collision()){
				System.out.println("\n\n\n\nCOLLIDE\n\n\n\n");
				setDx(this.dx * -1);
			}
				x += dx;
				y += dy;

		}

		public boolean collision(){
			int[] p1, p2;
			p1 = playerOne.getLocation();
			//System.out.println("ball - X: " + this.x + " Y: " + this.y);
			//System.out.println("paddle - X: " + p1[0] + " Y: " + p1[1] + " range: " + p1[3]);
			if(this.x <= p1[0] && this.y >= p1[1] && this.y <= p1[3]) return true;

			p2 = AI.getLocation();
			//System.out.println("ball - X: " + this.x + " Y: " + this.y);
			//System.out.println("paddle - X: " + p1[0] + " Y: " + p1[1] + " range: " + p1[3]);
			if(this.x >= p2[0] && this.y >= p2[1] && this.y <= p2[3]) return true;


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
	}
	
	@Override
	public void create () {
		AI = new Paddle(2000,400);
		playerOne = new Paddle(50,400);
		ball = new Ball(1000,500);
		ball.setDx(-10);

	}

	@Override
	public void render () {
		float pitch = Gdx.input.getPitch();
		//this gets Z-rotation, which is what I'm looking for
		//now I just need to
		//-find range
		//-use to create a move function
		//-smooth move, but faster if a more dramatic tilt?

		//it starts at 0, tilting up goes to -90, tilting down goes to 90

		Gdx.gl.glClearColor(0, 0, 0, 1);//black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerOne.draw();
		AI.draw();
		ball.draw();

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
	public void dispose () {
		playerOne.s.dispose();
		AI.s.dispose();
	}
}
