package com.example.alex.rhymebot.Adapter;

public class ChatMessage
{
    private String message;
    private int layoutId;
    private int textviewId;

    public ChatMessage(String message)
    {
        this.message = message;
    }

    public String getMessage() { return this.message; }
    public int getLayoutId() { return this.layoutId; }
    public int getTextviewId() { return this.textviewId; }
}
