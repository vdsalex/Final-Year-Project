package com.example.alex.rhymebot;

class Word
{
    private String string;
    private int relevance;

    Word()
    {
        this.string = "";
        this.relevance = 0;
    }

    Word(String string)
    {
        this.string = string;
        this.relevance = 0;
    }

    Word(String string, int relevance)
    {
        this.string = string;
        this.relevance = relevance;
    }

    //getters
    String getString()
    {
        return string;
    }

    int getRelevance()
    {
        return relevance;
    }

    //setters
    void setString(String string)
    {
        this.string = string;
    }

    void setRelevance(int relevance)
    {
        this.relevance = relevance;
    }
}
