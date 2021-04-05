package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 This is the class for a Paddle, used for a Pong game. There are 2 paddles per game,
 and at least one is always an AI.
 */
public abstract class Paddle {
    //fields
    protected int x, y;//position on screen
    protected int originalX, originalY;//used to reset
    protected ShapeRenderer s;//used to draw
    protected int score;//keep track of score
    protected boolean isAI;

    //enum for side of screen paddle is on
    enum SIDE{
        LEFT, RIGHT
    }

    protected SIDE side;

    //getters for things we need
    public int getScore(){
        return this.score;
    }

    //return score as a String so I can print
    public String getScoreAsString(){
        return Integer.toString(this.score);
    }

    public boolean getIsAI(){
        return isAI;
    }

    //paddle takes in position, if its an AI, and a speed (only used if its an AI)
    Paddle(int x, int y, SIDE side){
        this.x = this.originalX = x;
        this.y = this.originalY = y;
        this.score = 0;
        this.side = side;
        s = new ShapeRenderer();
        s.setColor(1,1,1,1);//white
    }

    //draw the paddle
    public void draw(){
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.rect(x, y, 50, 250);
        s.end();
    }

    public int[] getLocation(){
        int[] ret = {this.x, this.y, this.x + 50, this.y + 250};
        return ret;
    }

    //reset position back to original values
    public void reset(){
        x = originalX;
        y = originalY;
    }

    public void incrementScore(){
        this.score+=1;
    }

}