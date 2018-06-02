package com.example.alex.rhymebot;

import android.util.Log;

import com.example.alex.rhymebot.Adapter.ChatMessage;

import java.util.ArrayList;

public class Bot
{
    ArrayList<ChatMessage> messages;

    public Bot()
    {
        this.messages = new ArrayList<>();
    }

    public Bot(ArrayList<ChatMessage> messages)
    {
        this.messages = messages;
    }

    public ArrayList<ChatMessage> getMessages() { return this.messages; }

    public void addMessage(ChatMessage message) { messages.add(message); }

    public ChatMessage answer(ChatMessage userMessage)
    {
        if(userMessage.getActiveMod() == '0')
        {
            return new ChatMessage(userMessage.getId() + 1, "Ce te legeni, codrule,\n" +
                    "Fără ploaie, fără vânt,\n" +
                    "Cu crengile la pământ?", '0');
        }
        else if(userMessage.getActiveMod() == '1')
        {
            return new ChatMessage(userMessage.getId() + 1, "Dormeau adânc sicriele de plumb,\n" +
                    "Si flori de plumb si funerar vestmint", '1');
        }
        else
        {
            return new ChatMessage(userMessage.getId() + 1, "Cu mâna stângă ţi-am întors spre mine chipul,\n" +
                    "sub cortul adormiţilor gutui\n" +
                    "şi de-aş putea să-mi rup din ochii tăi privirea, ", '2');
        }
    }
}
