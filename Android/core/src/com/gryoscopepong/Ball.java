package com.gryoscopepong;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/*
 This is the class for the Ball object used for a Game.
 */
public class Ball {

    //fields
    int x, y;//position
    int dy, dx;//change in x, change in y, per render
    int originalX, originalY, originalDX, originalDY; //original values, used to reset
    ShapeRenderer s;

    //need a position and "speed"/movement to create
    Ball(int x, int y, int dx, int dy){
        this.x = this.originalX = x;
        this.y = this.originalY = y;
        this.dx = this.originalDX = dx;
        this.dy = this.originalDY = dy;
        s = new ShapeRenderer();
        s.setColor(1,1,1,1);//white
    }

    public void draw(){
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.rect(x, y, 50, 50);
        s.end();
        //every time we draw, we increase x by dx and y by dy
        //this gives the illusion of movement by these values
        x += dx;
        y += dy;
    }

    //this is used on a paddle collision
    //sets a new DY based on where the ball hit
    //the farther it is from the center, the bigger DY will be
    public void calculateNewDY(float midpointOfPaddle){
        int sign = 1;
        float distanceFromCenter = 0;
        if(this.y > midpointOfPaddle){//below the center (y increases from top to down)
            sign = 1;
            distanceFromCenter = this.y - midpointOfPaddle;
        }
        else if (this.y < midpointOfPaddle){//above the center
            sign = -1;
            distanceFromCenter = midpointOfPaddle - this.y;
        }
        else if(this.y == midpointOfPaddle){//right in the middle, no angle
            dy= 0;
            return;
        }
        //sign used for up vs down
        //paddle is 250 long, 125 is half of that
        //25 is a constant, keeps dy interesting but low enough so that its not impossible to play
        this.dy = (int) (sign * ((distanceFromCenter/125) * 25));
    }

    //setters, used in game class on collisions
    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    //reset x, y, dx, dy to starting values
    public void reset(){
        x = originalX;
        y = originalY;
        dx = originalDX;
        dy = originalDY;
    }

}