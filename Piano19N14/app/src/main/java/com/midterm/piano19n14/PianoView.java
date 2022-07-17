package com.midterm.piano19n14;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
public class PianoView extends View {

    public static final int NUMBER_KEY = 14;
    private Paint black, yellow, white;
    private ArrayList<Key> whites = new ArrayList<>();
    private ArrayList<Key> blacks = new ArrayList<>();
    private int keyWidth, keyHeight;
    //private AudioSoundPlayer soundPlayer;
    private SoundManager soundManager;
    private int widthImage, heightImage;
    int positionClick=0;
    boolean isBlack = false;

    public PianoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        black = new Paint();
        black.setColor(Color.BLACK);
        white = new Paint();
        white.setColor(Color.WHITE);
        white.setStyle(Paint.Style.FILL);
        yellow = new Paint();
        yellow.setColor(Color.YELLOW);
        yellow.setStyle(Paint.Style.FILL);
        //soundPlayer = new AudioSoundPlayer(context);
        soundManager = SoundManager.getInstance();
        soundManager.init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        keyWidth = w / NUMBER_KEY;
        keyHeight = h;
        int count = 15;

        for (int i = 0; i < NUMBER_KEY; i++) {
            int left = i * keyWidth;
            int right = left + keyWidth;

            if (i == NUMBER_KEY - 1) {
                right = w;
            }

            RectF rect = new RectF(left, 0, right, h);
            whites.add(new Key(rect, i + 1));

            if (i != 0  &&   i != 3  &&  i != 7  &&  i != 10) {
                rect = new RectF((float) (i - 1) * keyWidth + 0.5f * keyWidth + 0.25f * keyWidth, 0,
                        (float) i * keyWidth + 0.25f * keyWidth, 0.67f * keyHeight);
                blacks.add(new Key(rect, count));
                count++;
            }
        }

        widthImage = w;
        heightImage = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rect = new RectF(0, 0, widthImage, heightImage);
        canvas.drawRect(rect, white);
        for (Key k : whites) {
            canvas.drawRect(k.rect, k.isDown ? yellow : white);
        }

        for (int i = 1; i < NUMBER_KEY; i++) {
            RectF rect1 = new RectF( i* keyWidth-2, 0, i * keyWidth+2, keyHeight);
            canvas.drawRect(rect1, black);
            //canvas.drawLine(i * keyWidth-5, 0, i * keyWidth+5, keyHeight, black);
        }

        for (Key k : blacks) {
            canvas.drawRect(k.rect, k.isDown ? yellow : black);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        boolean isDownAction = action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE;
        for (int touchIndex = 0; touchIndex < event.getPointerCount(); touchIndex++) {
            System.out.println(event.getPointerCount());
            float x = event.getX(touchIndex);
            float y = event.getY(touchIndex);



            /*for (Key k:whites) {
                if(k.rect.contains(x, y)) {
                    k.isDown = isDownAction;
                    soundManager.playSound(R.raw.c3);
                    break;
                }
            }*/

            Key k = keyForCoords(x,y);

            if (k != null) {
                k.isDown = isDownAction;
                int numberPress = numberPress(x, y);
                if(isBlack) {
                    isBlack = false;
                    switch (numberPress) {
                        case 0: soundManager.playSound(R.raw.c3);
                            break;
                        case 1: soundManager.playSound(R.raw.c4);
                            break;
                        case 2: soundManager.playSound(R.raw.d3);
                            break;
                        case 3: soundManager.playSound(R.raw.d4);
                            break;
                        case 4: soundManager.playSound(R.raw.e3);
                            break;
                        case 5: soundManager.playSound(R.raw.e4);
                            break;
                        case 6: soundManager.playSound(R.raw.f3);
                            break;
                        case 7: soundManager.playSound(R.raw.f4);
                            break;
                        case 8: soundManager.playSound(R.raw.a3);
                            break;
                        case 9: soundManager.playSound(R.raw.a4);
                            break;
                        case 10: soundManager.playSound(R.raw.b3);
                            break;
                        default: break;
                    }
                } else {
                    switch (numberPress) {
                        case 0:
                            soundManager.playSound(R.raw.db3);
                            break;
                        case 1:
                            soundManager.playSound(R.raw.db4);
                            break;
                        case 2:
                            soundManager.playSound(R.raw.eb3);
                            break;
                        case 3:
                            soundManager.playSound(R.raw.eb4);
                            break;
                        case 4:
                            soundManager.playSound(R.raw.gb3);
                            break;
                        case 5:
                            soundManager.playSound(R.raw.gb4);
                            break;
                        case 6:
                            soundManager.playSound(R.raw.ab3);
                            break;
                        case 7:
                            soundManager.playSound(R.raw.ab4);
                            break;
                        case 8:
                            soundManager.playSound(R.raw.bb3);
                            break;
                        case 9:
                            soundManager.playSound(R.raw.bb4);
                            break;
                        default:
                            break;
                    }
                }
                invalidate();
            }

            releaseKey(k);
        }

        /*ArrayList<Key> tmp = new ArrayList<>(whites);
        tmp.addAll(blacks);*/

        /*for (Key k : tmp) {
            if (k.isDown) {
                if (!soundPlayer.isNotePlaying(k.sound)) {
                    soundPlayer.playNote(k.sound);
                    invalidate();
                } else {
                    releaseKey(k);
                }
            } else {
                soundPlayer.stopNote(k.sound);
                releaseKey(k);
            }
        }*/

        return true;
    }

    private int numberPress(float x, float y) {
        int count = 0;
        for (Key k : blacks) {
            count++;
            if (k.rect.contains(x,y)) {
                isBlack = true;
                return count;
            }
        }
        count=0;
        for (Key k : whites) {
            count++;

            if (k.rect.contains(x,y)) {
                return count;
            }
        }

        return -1;
    }

    private Key keyForCoords(float x, float y) {

        for (Key k : blacks) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        for (Key k : whites) {
            if (k.rect.contains(x,y)) {
                return k;
            }
        }

        return null;
    }


    private void releaseKey(final Key k) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                k.isDown = false;
                handler.sendEmptyMessage(0);
            }
        }, 50);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            invalidate();
        }
    };
}