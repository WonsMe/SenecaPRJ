package com.example.wonho.webapisample.Utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.wonho.webapisample.Models.Title;

import java.util.ArrayList;


public class TitleAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Title> items;

    public TitleAdapter(Context context) {
        this.context = context;
        items = new ArrayList<Title>();
    }

    public void addItem(Title item) {
        if (item != null) {
            items.add(item);
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TitleItemView titleItemView = new TitleItemView(context);

        Title title = items.get(i);
        titleItemView.setID(title.getId());
        titleItemView.setName(title.getName());

        return titleItemView;
    }
}
