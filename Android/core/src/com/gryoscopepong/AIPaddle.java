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

    public void prepareForVolley(Ball ball){
        if(moving) moving = false;
        int toY = ball.getY();
        int toX = ball.getX();
        int ballDXCopy = ball.getDx();
        int ballDYCopy = ball.getDy();

        if(side == SIDE.LEFT) {
            while (toX < faceOfPaddle) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
                if (toY <= 0 || toY >= Gdx.graphics.getHeight()) ballDYCopy *= -1;
            }
        }

        else if (side == SIDE.RIGHT){
            while (toX > 50) {
                toY = toY + (ballDYCopy * 1);
                toX = toX + (ballDXCopy * 1);
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

    @Override
    public void draw(){
        super.draw();
        moveTowards();
    }

    @Override
    public void reset(){
        super.reset();
        if(moving) moving = false;
    }


}
