package com.example.alex.rhymebot.BotPackage;

import java.io.InputStream;

public class VocabData
{
    private String data;

    public VocabData()
    {
        this.data = "";
    }

    public byte[] getBytes()
    {
        return this.data.getBytes();
    }
}
