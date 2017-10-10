package com.example.wendaqin.randomaddition;

import java.util.Random;

/**
 * Created by wendaqin on 10/9/17.
 */

public class addition {
    public int number1;
    public int number2;

    public addition()
    {
        number1 = 1+(int)(Math.random()*9);
        number2 = 1+(int)(Math.random()*9);
    }

    public int getSum()
    {
        return number1+number2;
    }

    public void getAnotherRandomNumbers()
    {
        number1 = 1+(int)(Math.random()*9);
        number2 = 1+(int)(Math.random()*9);
    }
}
