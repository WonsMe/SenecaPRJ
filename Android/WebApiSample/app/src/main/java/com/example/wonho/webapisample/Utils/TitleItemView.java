package com.example.wonho.webapisample.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wonho.webapisample.R;


public class TitleItemView extends LinearLayout {

    private TextView textID;
    private TextView textName;

    public TitleItemView(Context context) {
        super(context);
        init(context);
    }

    public TitleItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_item, this, true);

        textID = (TextView)findViewById(R.id.textID);
        textName = (TextView)findViewById(R.id.textName);
    }

    public void setID(int id) {
        textID.setText(Integer.toString(id));
    }

    public void setName(String name) {
        textName.setText(name);
    }
}
