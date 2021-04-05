package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 This is the class for a Paddle, used for a Pong game. There are 2 paddles per game,
 and at least one is always an AI.
 */
public class Paddle {
    int x, y;//position on screen
    int originalX, originalY;//used to reset
    ShapeRenderer s;//used to draw
    int score;//keep track of score

    //the following values will only be used if a Paddle is an AI
    boolean isAI = false;
    int speed;//speed to move around screen at
    int dy;//used to move at speed
    int moveTo = 0;//position we're moving towards
    boolean moving = false;//are we moving?


    //paddle takes in position, if its an AI, and a speed (only used if its an AI)
    Paddle(int x, int y, boolean isAI, int speed){
        this.x = this.originalX = x;
        this.y = this.originalY = y;
        this.speed = speed;
        this.score = 0;
        this.isAI = isAI;
        s = new ShapeRenderer();
        s.setColor(1,1,1,1);//white
    }

    //return score as a String so I can print
    public String getScore(){
        return Integer.toString(this.score);
    }

    public void draw(){
        s.begin(ShapeRenderer.ShapeType.Filled);
        moveTowards();
        s.rect(x, y, 50, 250);
        s.end();
    }


    public void moveBy(float val){
        int newPosition = (int) ((y) + ((val/90) * 25));
        if(newPosition > 0 && newPosition < (Gdx.graphics.getHeight()) - 250) y = newPosition;
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

    //AI functions

    public void prepareForVolley(Ball ball, String side){
        if(moving) moving = false;
        int toY = ball.y;
        int toX = ball.x;
        int ballDXCopy = ball.dx;
        int ballDYCopy = ball.dy;

        if(side == "left") {
            while (toX < Gdx.graphics.getWidth() - 50) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;
            }
        }
        else{
            while (toX > 50) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                System.out.println(toX);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;
            }
        }

        this.moveTo = toY - 100;
        this.moving = true;
        if(this.moveTo > this.y) this.dy = this.speed * 1;
        if(this.moveTo < this.y) this.dy = this.speed * -1;

    }

    public void moveTowards(){
        if(moving){
            this.y = this.y + this.dy;
            if(this.y + 75 >= moveTo && this.y - 75 <= moveTo) moving = false;
        }
    }

}