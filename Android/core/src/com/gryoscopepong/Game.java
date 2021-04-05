package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/*
 This class handles a game of Pong. It needs 2 paddles, a ball, and a sound effect to be implemented.
 */
public class Game {

    //class variables
    private Paddle leftSide, rightSide;
    private Ball ball;
    private Sound ballHit;

    //getters for the variables we need
    public Paddle getLeftSide() {
        return leftSide;
    }

    public Paddle getRightSide() {
        return rightSide;
    }


    //constructor for paddles, ball, sound effect
    public Game(Paddle leftSide, Paddle rightSide, Ball ball, Sound soundEffect){
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.ball = ball;
        this.ballHit = soundEffect;
        newServe();
    }

    //check for hitting a paddle
    private boolean collision(){
        int[] p1, p2;
        p1 = rightSide.getLocation();
        p2 = leftSide.getLocation();
        //right side of ball will hit left side of paddle
        if(ball.getX() >= p1[0] && ball.getX() <= p1[2] && ball.getY() >= p1[1] && ball.getY() <= p1[3]){//right side hits, tell left side to get ready for response
            ball.calculateNewDY(p1[1] + 100);//calculate angle of ball
            ball.setDx(ball.getDx() * -1);//send ball back in opposite direction
            if(leftSide.getIsAI()) ((AIPaddle) leftSide).prepareForVolley(ball);
            ballHit.play(1f);//play sound
            return true;
        }
        //left side of ball (thus the +50) will hit right side of paddle
        else if(ball.getX()+50 >= p2[0] && ball.getX()+50 <= p2[2] &&  ball.getY() >= p2[1] && ball.getY() <= p2[3]){//left side hits, tell right side to get ready for response
            ball.calculateNewDY(p2[1] + 100);//calculate angle of ball
            ball.setDx(ball.getDx() * -1);//send ball back in opposite direction
            if(rightSide.getIsAI()) ((AIPaddle) rightSide).prepareForVolley(ball);//move left paddle
            ballHit.play(1f);//play sound
            return true;
        }
        return false;
    }

    //check if left scores, and increase score of left side if so
    private boolean leftScores() {
        int[] p1 = rightSide.getLocation();
        if(ball.getX() < p1[0]) {
            leftSide.incrementScore();//leftSide.getScore()+=1;
            return true;
        }
        return false;
    }

    //check if right scores, and increase score of right side if so
    private boolean rightScores() {
        int[] p2 = leftSide.getLocation();
        if(ball.getX()+50 > p2[2]){
            rightSide.incrementScore();
            return true;
        }
        return false;
    }

    //check if the ball hits the screen, and bounce it off if so
    private boolean hitScreen(){
        if(ball.getY() <= 0 || ball.getY() >= Gdx.graphics.getHeight()) {
            ballHit.play(1f);//play sound effect
            ball.setDy(- this.ball.getDy());//send back off screen at opposite angle
            return true;
        }
        return false;
    }

    //this is called for each step of the game
    public void play(){
        //draw the shapes
        leftSide.draw();
        rightSide.draw();
        ball.draw();
        collision();//check for paddle collision
        hitScreen();//check if hit screen
        if(leftScores() || rightScores()){//if either side scores
            //reset shapes back to original position
            ball.reset();
            rightSide.reset();
            leftSide.reset();
            newServe();
        }
    }

    //if the side that will receive the ball is an AI, tell them to prepare for volley
    //this is used at start of game and after scoring
    private void newServe(){
        if(ball.getDx() < 0 && rightSide.getIsAI()) ((AIPaddle)rightSide).prepareForVolley(ball);
        else if(ball.getDx() > 0 &&  leftSide.getIsAI()) ((AIPaddle)leftSide).prepareForVolley(ball);
    }





}
