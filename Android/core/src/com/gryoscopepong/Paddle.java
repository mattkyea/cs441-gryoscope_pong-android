package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int x, y;
    int originalX, originalY;
    int speed;
    int dy;
    int moveTo = 0;
    boolean moving = false;
    boolean isAI = false;
    ShapeRenderer s;
    int score;

    Paddle(int x, int y, boolean isAI, int speed){
        this.x = x;
        this.y = y;
        this.originalX = x;
        this.originalY = y;
        this.speed = speed;
        this.score = 0;
        this.isAI = isAI;
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
        if(newPosition > 0 && newPosition < (Gdx.graphics.getHeight()) - 250) y = newPosition;
    }

    public int[] getLocation(){
        int[] ret = {this.x, this.y, this.x + 50, this.y + 250};
        return ret;
    }

    public void reset(){
        x = originalX;
        y = originalY;
    }

    public void prepareForVolley(Ball ball, String side){

        //this works for very small values of DY
        //i.e. values that don't send the ball off screen
        //so that's the real challenge - how to compute on a smaller basis (i.e. I can't do the whole *190 at once), so I can check if we go off screen
        //loop in worst case scenario -
        //start at 2, go to 190
        //at each "toY" and "toX" see if would trigger hit screen
        //if so, update values here as part of prediction

        if(moving) moving = false;

        int toY, toX;
        int ballDXCopy = ball.dx;
        int ballDYCopy = ball.dy;
        toY = ball.y;
        toX = ball.x;

        System.out.println(ball.dx);


        if(side == "left") {
            while (toX < Gdx.graphics.getWidth() - 50) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;
            }
        }
        else{
            System.out.println(toX);
            while (toX > 50) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                //System.out.println(toX);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;
            }
            System.out.println(toX);
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