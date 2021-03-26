package com.gryoscopepong;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GryoscopePong extends ApplicationAdapter {
	Paddle playerOne, AI;


	//TODO
	//trajectory
	//move ball function
	//account for hitting paddles and side of screen
	//hook up gryoscope
	//basic AI/something that looks smart


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
		Gdx.gl.glClearColor(0, 0, 0, 1);//black
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		playerOne.draw();
		AI.draw();
		//playerOne.moveUp();
		//AI.moveDown();
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
