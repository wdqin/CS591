package com.example.sse.interfragmentcommratingbar;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RightFragment extends Fragment {

    ArrayList<Drawable> drawables;
    private Button rBtn;
    public int curr_index=0;
    public interface RFButtonListener
    {
        public void changeImage(int index);

    }


    RFButtonListener RFBL;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        RFBL = (RFButtonListener) context;
    }
    public RightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right,container,false);
        getDrawables();
        rBtn = (Button) view.findViewById(R.id.rBtn);
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (curr_index == drawables.size() - 1)
                    curr_index = 0;
                else
                    curr_index++;


                RFBL.changeImage(curr_index);
            }
        });
        return view;
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

}
