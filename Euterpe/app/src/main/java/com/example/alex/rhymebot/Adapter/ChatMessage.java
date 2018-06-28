package com.example.alex.rhymebot.Adapter;

import java.io.Serializable;

public class ChatMessage implements Serializable
{
    private int id;
    private String message;
    private char activeMod;
    private String mode;
    private int numberOfEndMessageMarks;
    private boolean isSaved;

    public ChatMessage(int id, String message, char activeMod)
    {
        this.id = id;
        this.message = message;
        this.activeMod = activeMod;
        this.numberOfEndMessageMarks = 0;
        this.isSaved = false;

        for(int i = 0; i < message.length(); i++)
        {
            if(message.charAt(i) == '#')
            {
                this.numberOfEndMessageMarks++;
            }
        }

        switch (activeMod)
        {
            case '0':
            {
                mode = "Eminescu";
                break;
            }

            case '1':
            {
                mode = "Bacovia";
                break;
            }

            case '2':
            {
                mode = "Stănescu";
                break;
            }

            default:
            {
                mode = "Eminescu";
                break;
            }
        }
    }

    public String getMessage() { return this.message; }
    public char getActiveMod() { return this.activeMod; }
    public int getNumberOfEndMessageMarks() { return this.numberOfEndMessageMarks; }
    public int getId() { return this.id; }
    public boolean getIsSaved() { return this.isSaved; }
    public String getModeString() { return this.mode; }

    public void setIsSaved(boolean value) { this.isSaved = value; }
}
