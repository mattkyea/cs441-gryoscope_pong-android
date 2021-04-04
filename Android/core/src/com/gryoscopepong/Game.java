package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Game {

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

    private Paddle leftSide, rightSide;
    private Ball ball;
    private Sound ballHit;

    public Game(Paddle leftSide, Paddle rightSide, Ball ball, Sound soundEffect){
        this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.ball = ball;
        this.ballHit = soundEffect;
    }

    public boolean collision(){
        int[] p1, p2;
        p1 = rightSide.getLocation();
        p2 = leftSide.getLocation();
        //int topRight = this.x - 10 ;
        //int topLeft = this.x + 10;
        //System.out.println("ball - X: " + this.x + " Y: " + this.y);
        //System.out.println("paddle - X: " + p1[0] + " range: "+ p1[2]+ " Y: " + p1[1] + " range: " + p1[3]);
        if(ball.x >= p1[0] && ball.x <= p1[2] && ball.y >= p1[1] && ball.y <= p1[3]){//player hits, tell AI to get ready for response
            System.out.println("hit");
            ball.calculateNewDY(p1[1] + 100);
            ball.setDx(ball.dx * -1);
            if(leftSide.isAI) leftSide.prepareForVolley(ball, "left");
            ballHit.play(1f);
            return true;
        }


        //System.out.println("ball - X: " + this.x + " Y: " + this.y);
        //System.out.println("paddle - X: " + p2[0] + " range: "+ p2[2]+ " Y: " + p2[1] + " range: " + p2[3]);
        else if(ball.x+50 <= p2[2] && ball.x+50 >= p2[0] && ball.y >= p2[1] && ball.y <= p2[3]){
            System.out.println("hit2");
            ball.calculateNewDY(p2[1] + 100);
            ball.setDx(ball.dx * -1);
            if(rightSide.isAI) rightSide.prepareForVolley(ball, "right");
            ballHit.play(1f);
            return true;
        }


        return false;

    }

    //TODO move this to Game class
    private boolean leftScores() {
        int[] p1 = rightSide.getLocation();
        //System.out.println(this.x + " VS " + p1[0]);
        if(ball.x < p1[0]) {
            leftSide.score+=1;
            return true;
        }
        return false;
    }

    //TODO move this to Game class
    private boolean rightScores() {
        int[] p2 = leftSide.getLocation();
        if(ball.x+50 > p2[2]){
            rightSide.score+=1;
            return true;
        }
        return false;
    }

    public void play(){
        //TODO move this to Game class

        leftSide.draw();
        rightSide.draw();
        ball.draw();

        collision();
        hitScreen();
        if(leftScores() || rightScores()){
            //System.out.println("GOAL");
            ball.reset();
            rightSide.reset();
            leftSide.reset();
        }
    }

    public boolean hitScreen(){
        if(ball.y <= 0 || ball.y >= Gdx.graphics.getHeight()) {
            ballHit.play(1f);
            ball.setDy(- this.ball.dy);
            return true;
        }
        return false;
    }



}
