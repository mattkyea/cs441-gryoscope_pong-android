package com.gryoscopepong;

import com.badlogic.gdx.Gdx;

//an AI controlled paddle is a specific case of a Paddle
public class AIPaddle extends Paddle{

    //the following values will only be used if a Paddle is an AI
    private int speed;//speed to move around screen at
    private int dy;//used to move at speed
    private int moveTo = 0;//position we're moving towards
    private boolean moving = false;//are we moving?
    private int faceOfPaddle;//X coordinate point we check for collisions on

    AIPaddle(int x, int y, int speed, SIDE side) {
        super(x, y, side);
        if(this.side == SIDE.LEFT) this.faceOfPaddle = this.x - 50;
        else if(this.side == SIDE.RIGHT) this.faceOfPaddle = this.x + 50;
        this.speed = speed;
        this.isAI = true;
    }

    //when the other paddle hits a ball, we must prepare to hit it back
    //this works by calculating where the ball will end up
    //the only thing that stops a perfect AI is its speed - if it can't get to a spot quick enough, it'll miss
    public void prepareForVolley(Ball ball){
        if(moving) moving = false;
        //get all of the ball's values
        int toY = ball.getY();
        int toX = ball.getX();
        int ballDXCopy = ball.getDx();
        int ballDYCopy = ball.getDy();

        //if this is the left paddle
        if(side == SIDE.LEFT) {
            while (toX < faceOfPaddle) {//increment X value of ball until its at the X value for the face of the paddle
                //use dy and dx to increment
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;//check for hitting walls and adjust
            }
        }
        //if this is the left paddle
        else if (side == SIDE.RIGHT){//decrement X value of ball until its at the X value for the face of the paddle
            while (toX > faceOfPaddle) {
                //use dy and dx to decrement
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;//check for hitting walls and adjust
            }
        }
        this.moveTo = toY - 100;//offset size of paddle
        this.moving = true;//allow paddle to start moving
        //check where to go relative to where we are, so we know to move up or down
        if(this.moveTo > this.y) this.dy = this.speed * 1;
        if(this.moveTo < this.y) this.dy = this.speed * -1;

    }

    //move the AI paddle
    public void moveTowards(){
        if(moving){//only if we've prepared for a volley
            this.y = this.y + this.dy;//increment y by dy
            if(this.y + 75 >= moveTo && this.y - 75 <= moveTo) moving = false;//once in range of predicted ball position, stop
        }
    }

    //override draw so we can call moveTowards
    @Override
    public void draw(){
        super.draw();
        moveTowards();
    }

    //override draw so we can stop moving
    @Override
    public void reset(){
        super.reset();
        if(moving) moving = false;
    }


}
