package com.example.alex.rhymebot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Alex on 5/24/2018.
 */

public class MessagesAdapter extends ArrayAdapter<String>
{
    public MessagesAdapter(Context context, ArrayList<String> messages)
    {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String message = getItem(position);

        //if(convertView == null)
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.)

        return convertView;
    }
}
