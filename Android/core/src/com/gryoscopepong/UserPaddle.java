package com.gryoscopepong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

//a user controlled paddle is a specific case of a paddle
public class UserPaddle extends Paddle{

    UserPaddle(int x, int y, SIDE side) {
        super(x, y, side);
        this.isAI = false;
    }

    //draw the paddle
    //override so we accept user tilt to move paddle
    public void draw(){
        s.begin(ShapeRenderer.ShapeType.Filled);
        s.rect(x, y, 50, 250);
        s.end();
        float pitch = Gdx.input.getPitch();
        //this gets Z-rotation
        //it starts at 0, tilting up goes to 90, tilting down goes to -90
        moveBy(pitch);
    }

    //move paddle by a value
    public void moveBy(float val){
        int newPosition = (int) ((y) + ((val/90) * 25));
        if(newPosition > 0 && newPosition < (Gdx.graphics.getHeight()) - 250) y = newPosition;
    }
}
