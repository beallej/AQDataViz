package com.josiebealle.asst2try3;

/**
 * Created by josiebealle on 05/04/15.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListDemoAdapter extends ArrayAdapter<String> {
    public ListDemoAdapter(Context context, int resource, ArrayList<String> listItems) {
        super(context, resource, listItems);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_cell, null);
        }

        if (v != null) {
            TextView textView = (TextView) v.findViewById(R.id.listCellTextView);
            textView.setText(this.getItem(position));
        }

        return v;
    }
}

