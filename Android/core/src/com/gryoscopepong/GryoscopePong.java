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
			System.out.println(y);
			int newPosition = (int) ((y) + ((val/90) * 25));
			if(newPosition > 0 && newPosition < 800) y = newPosition;
		}

	}

	public class Ball {
		int x, y;
		ShapeRenderer s;

		Ball(int x, int y){
			this.x = x;
			this.y = y;
			s = new ShapeRenderer();
			s.setColor(1,1,1,1);//white
		}

		public void draw(){
			s.begin(ShapeRenderer.ShapeType.Filled);
			s.circle(x, y, 50);
			s.end();
		}

		public void moveUp(){
			y+=1;
		}

		public void moveDown(){
			y-=1;
		}
	}
	
	@Override
	public void create () {
		AI = new Paddle(2000,400);
		playerOne = new Paddle(50,400);

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
