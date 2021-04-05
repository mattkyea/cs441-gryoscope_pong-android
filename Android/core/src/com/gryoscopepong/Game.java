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

    //getters for the variables
    public Paddle getLeftSide() {
        return leftSide;
    }

    public Paddle getRightSide() {
        return rightSide;
    }

    public Ball getBall() {
        return ball;
    }

    public Sound getBallHit() {
        return ballHit;
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
        if(ball.x >= p1[0] && ball.x <= p1[2] && ball.y >= p1[1] && ball.y <= p1[3]){//right side hits, tell left side to get ready for response
            ball.calculateNewDY(p1[1] + 100);//calculate angle of ball
            ball.setDx(ball.dx * -1);//send ball back in opposite direction
            if(leftSide.isAI) leftSide.prepareForVolley(ball, "left");//move left paddle
            ballHit.play(1f);//play sound
            return true;
        }
        else if(ball.x+50 <= p2[2] && ball.x+50 >= p2[0] && ball.y >= p2[1] && ball.y <= p2[3]){//left side hits, tell right side to get ready for response
            ball.calculateNewDY(p2[1] + 100);//calculate angle of ball
            ball.setDx(ball.dx * -1);//send ball back in opposite direction
            if(rightSide.isAI) rightSide.prepareForVolley(ball, "right");//move left paddle
            ballHit.play(1f);//play sound
            return true;
        }
        return false;
    }

    //check if left scores, and increase score of left side if so
    private boolean leftScores() {
        int[] p1 = rightSide.getLocation();
        if(ball.x < p1[0]) {
            leftSide.score+=1;
            return true;
        }
        return false;
    }

    //check if right scores, and increase score of right side if so
    private boolean rightScores() {
        int[] p2 = leftSide.getLocation();
        if(ball.x+50 > p2[2]){
            rightSide.score+=1;
            return true;
        }
        return false;
    }

    //check if the ball hits the screen, and bounce it off if so
    private boolean hitScreen(){
        if(ball.y <= 0 || ball.y >= Gdx.graphics.getHeight()) {
            ballHit.play(1f);//play sound effect
            ball.setDy(- this.ball.dy);//send back off screen at opposite angle
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
        if(ball.dx < 0 && rightSide.isAI) rightSide.prepareForVolley(ball, "right");
        else if(ball.dy > 0 && leftSide.isAI) leftSide.prepareForVolley(ball, "left");
    }





}
