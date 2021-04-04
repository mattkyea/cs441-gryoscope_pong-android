package com.gryoscopepong;


import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    int x, y;
    int dy, dx;
    int originalX, originalY, originalDX, originalDY;
    ShapeRenderer s;

    Ball(int x, int y, int dx, int dy){
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        //this.dy = 0;
        originalDX = this.dx;
        originalDY = this.dy;
        //originalDY = 0;
        originalX = x;
        originalY = y;
        s = new ShapeRenderer();
        s.setColor(1,1,1,1);//white
    }

    public void draw(){
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.rect(x, y, 50, 50);
        s.end();
        x += dx;
        y += dy;

    }



    public void calculateNewDY(float midpointOfPaddle){
        int sign = 1;
        float distanceFromCenter = 0;//use abs
        if(this.y > midpointOfPaddle){
            System.out.println(this.y + " > " + midpointOfPaddle);
            sign = 1;
            distanceFromCenter = this.y - midpointOfPaddle;
        }
        else if (this.y < midpointOfPaddle){
            System.out.println(this.y + " < " + midpointOfPaddle);
            sign = -1;
            distanceFromCenter = midpointOfPaddle - this.y;
        }
        else if(this.y == midpointOfPaddle){
            System.out.println(this.y + " = " + midpointOfPaddle);
            dy= 0;
            return;
        }

        this.dy = (int) (sign * ((distanceFromCenter/125) * 25));
        System.out.println(((distanceFromCenter/125) * 10) + ", so set dy to " + this.dy);
    }






    public void moveUp(){
        y+=1;
    }

    public void moveDown(){
        y-=1;
    }

    public void move(int val){
        int newPositionY = y+ val;
        if(newPositionY > 0 && newPositionY < 800) y = newPositionY;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void reset(){
        x = originalX;
        y = originalY;
        dx = originalDX;
        dy = originalDY;
    }

}