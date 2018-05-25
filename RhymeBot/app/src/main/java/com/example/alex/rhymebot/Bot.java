package com.example.alex.rhymebot;

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

    public String answer(String userMessage)
    {
        if(userMessage.equals("daa")) return "nuu";
        else if(userMessage.equals("nuu")) return "daa";
        else if(userMessage.equals("ceva")) return "alceva";
        else return "saluuut";
    }
}
