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

public class MessagesAdapter extends ArrayAdapter<String>
{
    private ArrayList<String> messagesList;

    public MessagesAdapter(Context context)
    {
        super(context, R.layout.list_item_layout, R.id.textview_list_item);
        this.messagesList = new ArrayList<>();
    }

    @Override
    public void add(String chatMessage)
    {
        messagesList.add(chatMessage);
    }

    @Override
    public int getCount()
    {
        return messagesList.size();
    }

    @Override
    public String getItem(int index)
    {
        return messagesList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        String message = this.getItem(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.textview_list_item);

            if(position % 2 == 0)
            {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                textView.setBackgroundResource(R.drawable.dark_grey_rectangle);
                textView.setTextColor(Color.parseColor("#fce87d"));

                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.setMargins(25, 10, 60, 10);

                textView.setLayoutParams(lp);
            }
            else
            {
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                textView.setBackgroundResource(R.drawable.gold_rectangle);
                textView.setTextColor(Color.parseColor("#4e4e50"));

                lp.addRule(RelativeLayout.ALIGN_PARENT_END);
                lp.setMargins(60, 10, 25, 10);

                textView.setLayoutParams(lp);
            }

            textView.setText(message);
        }
        else
        {
            if(message != null)
            {
                TextView textView = (TextView) view.findViewById(R.id.textview_list_item);

                if(position % 2 == 0)
                {
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    textView.setBackgroundResource(R.drawable.dark_grey_rectangle);
                    textView.setTextColor(Color.parseColor("#fce87d"));

                    lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                    lp.setMargins(25, 10, 60, 10);

                    textView.setLayoutParams(lp);
                }
                else
                {
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    textView.setBackgroundResource(R.drawable.gold_rectangle);
                    textView.setTextColor(Color.parseColor("#4e4e50"));

                    lp.addRule(RelativeLayout.ALIGN_PARENT_END);
                    lp.setMargins(60, 10, 25, 10);

                    textView.setLayoutParams(lp);
                }

                textView.setText(message);
            }
        }

        return view;
    }
}
