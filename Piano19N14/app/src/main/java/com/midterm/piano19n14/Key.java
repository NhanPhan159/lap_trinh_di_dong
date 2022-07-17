package com.midterm.piano19n14;

import android.graphics.RectF;

public class Key {
    public int sound;
    public RectF rect;
    public boolean isDown;

    public Key() {
    }

    public Key(RectF rectF, int sound) {
        this.sound = sound;
        this.rect = rectF;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public RectF getRectF() {
        return rect;
    }

    public void setRectF(RectF rectF) {
        this.rect = rectF;
    }

    public boolean isDown() {
        return isDown;
    }

    public void setDown(boolean down) {
        this.isDown = down;
    }
}
