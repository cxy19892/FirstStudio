package com.cxy.firststudio.interfaces;

/**
 * Created by hasee on 2016/12/26.
 */
public interface ButtonController {
    int getPressedColor(int color);

    int getLighterColor(int color);

    int getDarkerColor(int color);

    boolean enablePress();

    boolean enableGradient();
}
