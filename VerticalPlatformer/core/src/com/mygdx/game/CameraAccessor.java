package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class CameraAccessor implements TweenAccessor<OrthographicCamera> {
    public static final int X = 0;
    public static final int Y = 1;
    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case Y:
                returnValues[0] = target.position.y;
                return 1;
                default:
                    assert false;
                    return 0;
        }
    }

    @Override
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch(tweenType){

            case Y:
                target.position.y = newValues[0];
                default:
                    assert false;
        }
    }
}
