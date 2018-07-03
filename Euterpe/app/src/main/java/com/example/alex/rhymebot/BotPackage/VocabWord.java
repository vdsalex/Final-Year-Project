package com.example.alex.rhymebot.BotPackage;

import java.util.ArrayList;

public class VocabWord
{
    private String string;
    private int syllables;
    private int indexInPartOfSpeechFile;
    private int partOfSpeech;
    private ArrayList<String> similarWords;

    VocabWord(String fileline)
    {
        String[] filelineElems = fileline.split(" ");

        this.string = filelineElems[0];
        this.partOfSpeech = Integer.parseInt(filelineElems[1]);
        this.syllables = Integer.parseInt(filelineElems[2]);
        this.indexInPartOfSpeechFile = Integer.parseInt(filelineElems[3]);

        this.similarWords = new ArrayList<>();

        for(int i = 4; i < filelineElems.length; i++)
        {
            this.similarWords.add(filelineElems[i]);
        }
    }

    public String getString() { return this.string; }
    public int getSyllables() { return this.syllables; }
    public int getIndexInPartOfSpeechFile() { return this.indexInPartOfSpeechFile; }
    public int getPartOfSpeech()
    {
        return partOfSpeech;
    }
    public ArrayList<String> getSimilarWords()
    {
        return similarWords;
    }
    public void setString(String value) { this.string = value; }
}
