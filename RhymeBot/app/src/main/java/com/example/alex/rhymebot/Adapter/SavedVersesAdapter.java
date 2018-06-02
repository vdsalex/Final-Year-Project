package com.example.alex.rhymebot.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.rhymebot.R;

import java.util.ArrayList;

public class SavedVersesAdapter extends ArrayAdapter<ChatMessage>
{
    private ArrayList<ChatMessage> versesList;

    public SavedVersesAdapter(Context context)
    {
        super(context, R.layout.list_item_layout, R.id.textview_list_item);
        this.versesList = new ArrayList<>();
    }

    @Override
    public void add(ChatMessage verse)
    {
        versesList.add(verse);
    }

    @Override
    public int getCount()
    {
        return versesList.size();
    }

    @Override
    public void remove(ChatMessage message)
    {
        versesList.remove(message);
    }

    @Override
    public ChatMessage getItem(int index)
    {
        return versesList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        ChatMessage verse = this.getItem(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_layout, null);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView textView = (TextView) view.findViewById(R.id.textview_list_item);

            textView.setBackgroundResource(R.drawable.gold_rectangle);
            textView.setTextColor(Color.parseColor("#4e4e50"));

            lp.setMargins(50, 15, 50, 15);

            textView.setLayoutParams(lp);

            textView.setText(verse.getMessage());
        }
        else
        {
            if(verse != null)
            {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                TextView textView = (TextView) view.findViewById(R.id.textview_list_item);

                textView.setBackgroundResource(R.drawable.gold_rectangle);
                textView.setTextColor(Color.parseColor("#4e4e50"));

                lp.setMargins(50, 15, 50, 15);

                textView.setLayoutParams(lp);

                textView.setText(verse.getMessage());
            }
        }

        return view;
    }
}
