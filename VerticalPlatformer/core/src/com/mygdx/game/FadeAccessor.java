package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;

public class FadeAccessor implements TweenAccessor<Fade> {
    public static final int ALPHA = 0;
    @Override
    public int getValues(Fade target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.alpha;
                return 1;
                default:
                    assert false;
                    return -1;
        }
    }

    @Override
    public void setValues(Fade target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALPHA:
                target.alpha = newValues[0];
                default:
                    assert false;
        }
    }
}
