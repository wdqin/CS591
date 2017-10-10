package com.example.wendaqin.randomaddition;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private TextView txvNum1;
    private TextView txvNum2;
    private TextView txvNum3;
    private TextView txvAdd;
    private TextView txvEql;
    private RelativeLayout rloBkg;
    private GestureDetector randomizeGestureDetector;
    private GestureDetector resultGestureDetector;
    private addition pairModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Context context = getApplicationContext();

        pairModel = new addition();
        txvNum1 = (TextView) findViewById(R.id.txvNum1);
        txvNum2 = (TextView) findViewById(R.id.txvNum2);
        txvNum3 = (TextView) findViewById(R.id.txvNum3);
        txvAdd = (TextView) findViewById(R.id.txvAdd);
        txvEql = (TextView) findViewById(R.id.txvEql);
        rloBkg = (RelativeLayout) findViewById(R.id.rloBkg);

        txvNum1.setText(Integer.toString(pairModel.number1));
        txvNum2.setText(Integer.toString(pairModel.number2));
        txvNum3.setText("?");

        randomizeGestureDetector = new GestureDetector(getApplicationContext(), new randomizeGestureListener()
        );
        resultGestureDetector = new GestureDetector(getApplicationContext(),new resultGestureListener());

        rloBkg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                boolean result = randomizeGestureDetector.onTouchEvent(event);
                return true;
            }
        });
        txvAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean result = resultGestureDetector.onTouchEvent(event);
                return true;
            }
        });



    }

    class randomizeGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            pairModel.getAnotherRandomNumbers();
            txvNum1.setText(Integer.toString(pairModel.number1));
            txvNum2.setText(Integer.toString(pairModel.number2));
            txvNum3.setText("?");
            Log.i("gesture","randomizeGestureListener");
            return super.onDoubleTap(e);
        }

    }

    class resultGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            txvNum3.setText(Integer.toString(pairModel.getSum()));
            Log.i("gesture","resultGestureListener");
            return super.onDoubleTap(e);
        }

    }
}


