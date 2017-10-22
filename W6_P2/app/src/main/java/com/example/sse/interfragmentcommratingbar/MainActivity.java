package com.example.sse.interfragmentcommratingbar;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RightFragment.RFButtonListener,DrawableFragment.DrawableListener{

    ArrayList<Drawable> drawables;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void getDrawables() {
        Field[] drawablesFields = com.example.sse.interfragmentcommratingbar.R.drawable.class.getFields();
        drawables = new ArrayList<>();

        String fieldName;
        for (Field field : drawablesFields) {
            try {
                fieldName = field.getName();
                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith("animals_"))  //only add drawable resources that have our prefix.
                    drawables.add(getResources().getDrawable(field.getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void changeImage(int index)
    {

        DrawableFragment bottomFrag = (DrawableFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        bottomFrag.changePicture(index);
        bottomFrag.starRateBar.setRating(bottomFrag.ratingArray[index]);
        bottomFrag.currDrawableIndex=index;
    }
    public void giveIndex(int index)
    {

        RightFragment rFrag = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.rightButtonFrag);
        rFrag.curr_index = index;
    }
}
