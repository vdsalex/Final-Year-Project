package com.example.alex.rhymebot.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alex.rhymebot.R;

import java.util.ArrayList;

public class MessagesAdapter extends ArrayAdapter<ChatMessage>
{
    private ArrayList<ChatMessage> messagesList;

    public MessagesAdapter(Context context, ArrayList<ChatMessage> messages)
    {
        super(context, 0, messages);
        this.messagesList = messages;
    }

    public ArrayList<ChatMessage> getMessagesList()
    {
        return messagesList;
    }

    @Override
    public void remove(ChatMessage message)
    {
        messagesList.remove(message);
    }

    @Override
    public void add(ChatMessage chatMessage)
    {
        messagesList.add(chatMessage);
    }

    @Override
    public int getCount()
    {
        return messagesList.size();
    }

    @Override
    public ChatMessage getItem(int index)
    {
        return messagesList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        ChatMessage message = this.getItem(position);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.conversation_item_layout2, null);

            TextView textViewMessageBody = (TextView) view.findViewById(R.id.textview_message_body);
            TextView textViewMode = (TextView) view.findViewById(R.id.textview_mode);
            ImageView savedIcon = (ImageView) view.findViewById(R.id.imageView_saved);
            RelativeLayout messageContainer = (RelativeLayout) (view.findViewById(R.id.message_container));

            if(position % 2 == 0)
            {
                messageContainer.setBackgroundResource(R.drawable.dark_grey_rectangle);
                textViewMessageBody.setTextColor(Color.parseColor("#fce87d"));

                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.setMargins(25, 10, 60, 10);

                textViewMode.setVisibility(View.GONE);
                savedIcon.setVisibility(View.GONE);
            }
            else
            {
                messageContainer.setBackgroundResource(R.drawable.gold_rectangle);
                textViewMessageBody.setTextColor(Color.parseColor("#4e4e50"));

                lp.addRule(RelativeLayout.ALIGN_PARENT_END);
                lp.setMargins(60, 10, 25, 10);

                if(message.getIsSaved())
                {
                    savedIcon.setVisibility(View.VISIBLE);
                }
                else
                {
                    savedIcon.setVisibility(View.INVISIBLE);
                }
            }

            messageContainer.setLayoutParams(lp);
            textViewMessageBody.setText(message.getMessage());
            textViewMode.setText(message.getModeString());
            savedIcon.setImageResource(R.drawable.saved_verses_icon);
        }
        else
        {
            if(message != null)
            {
                TextView textViewMessageBody = (TextView) view.findViewById(R.id.textview_message_body);
                TextView textViewMode = (TextView) view.findViewById(R.id.textview_mode);
                ImageView savedIcon = (ImageView) view.findViewById(R.id.imageView_saved);
                RelativeLayout messageContainer = (RelativeLayout) (view.findViewById(R.id.message_container));

                if(position % 2 == 0)
                {
                    messageContainer.setBackgroundResource(R.drawable.dark_grey_rectangle);
                    textViewMessageBody.setTextColor(Color.parseColor("#fce87d"));

                    lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                    lp.setMargins(25, 10, 60, 10);

                    textViewMode.setVisibility(View.GONE);
                    savedIcon.setVisibility(View.GONE);
                }
                else
                {
                    messageContainer.setBackgroundResource(R.drawable.gold_rectangle);
                    textViewMessageBody.setTextColor(Color.parseColor("#4e4e50"));

                    lp.addRule(RelativeLayout.ALIGN_PARENT_END);
                    lp.setMargins(60, 10, 25, 10);

                    if(message.getIsSaved())
                    {
                        savedIcon.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        savedIcon.setVisibility(View.INVISIBLE);
                    }
                }

                messageContainer.setLayoutParams(lp);
                textViewMessageBody.setText(message.getMessage());
                textViewMode.setText(message.getModeString());
                savedIcon.setImageResource(R.drawable.saved_verses_icon);
            }
        }

        return view;
    }
}
