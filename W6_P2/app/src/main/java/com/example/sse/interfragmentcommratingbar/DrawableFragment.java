package com.example.sse.interfragmentcommratingbar;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawableFragment extends Fragment {

    ArrayList<Drawable> drawables;  //keeping track of our images
    public int currDrawableIndex;
    public float[] ratingArray;
    private ImageView imgRateMe;
    public RatingBar starRateBar;
    private Button btnLeft;
    private Button btnRight;

    public DrawableFragment() {
        // Required empty public constructor
    }

    public interface DrawableListener
    {
        public void giveIndex(int index);

    }


    DrawableListener DL;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        DL = (DrawableListener) context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_drawable, container, false);

        View v = inflater.inflate(R.layout.fragment_drawable, container, false);   //MUST HAPPEN FIRST, otherwise components don't exist.

//getting all drawable resources, handy third party see method below.
        currDrawableIndex = 0;
        getDrawables();
        ratingArray = new float[drawables.size()];
        for(int i=0;i<ratingArray.length;i++)
        {
            ratingArray[i] =0;
        }
        imgRateMe = (ImageView) v.findViewById(R.id.imgRateMe);
        btnRight = (Button) v.findViewById(R.id.btnRight);
        btnLeft = (Button) v.findViewById(R.id.btnLeft);
        starRateBar = (RatingBar) v.findViewById(R.id.ratingBar);

        starRateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if(b)
                {
                    ratingArray[currDrawableIndex] = v;
                }


            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currDrawableIndex == 0)
                    currDrawableIndex = drawables.size() - 1;
                else
                    currDrawableIndex--;

                starRateBar.setRating(ratingArray[currDrawableIndex]);
                DL.giveIndex(currDrawableIndex);
                changePicture(currDrawableIndex);
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currDrawableIndex == drawables.size() - 1)
                    currDrawableIndex = 0;
                else
                    currDrawableIndex++;

                DL.giveIndex(currDrawableIndex);
                starRateBar.setRating(ratingArray[currDrawableIndex]);
                changePicture(currDrawableIndex);
            }
        });


        return v;   //must happen last, it is a return statement after all, it can't happen sooner!
    }


    public void changePicture(int index) {
        imgRateMe.setImageDrawable(drawables.get(index));
    }


    //REF: http://stackoverflow.com/questions/31921927/how-to-get-all-drawable-resources

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
}