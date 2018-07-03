package com.example.alex.rhymebot.BotPackage;

import java.util.ArrayList;
import java.util.List;

class Noun
{
    private int gen;
    private List<String> allForms;

    private String nomAczSgArt;
    private String nomAczSgNeart;
    private String nomAczPlArt;
    private String nomAczPlNeart;
    private String genDatSgArt;
    private String genDatSgNeart;
    private String genDatPlArt;
    private String genDatPlNeart;

    Noun(String line)
    {
        this.allForms = new ArrayList<>();
        String[] lineElems = line.split(" ");

        switch (lineElems[0])
        {
            case "M":
            {
                this.gen = 0;
                break;
            }

            case "F":
            {
                this.gen = 1;
                break;
            }

            default:
            {
                this.gen = 2;
                break;
            }
        }

        this.nomAczSgNeart = lineElems[1];
        this.nomAczSgArt = lineElems[2];
        this.nomAczPlNeart = lineElems[3];
        this.nomAczPlArt = lineElems[4];

        this.allForms.add(lineElems[1]);
        this.allForms.add(lineElems[2]);
        this.allForms.add(lineElems[3]);
        this.allForms.add(lineElems[4]);

        this.genDatPlArt = "";
        this.genDatPlNeart = "";
        this.genDatSgArt = "";
        this.genDatSgNeart = "";
    }

    Noun(int gen, String nomAczSgNeart, String nomAczSgArt, String nomAczPlNeart, String nomAczPlArt)
    {
        this.gen = gen;
        this.allForms = new ArrayList<>();

        this.nomAczSgNeart = nomAczSgNeart;
        this.nomAczSgArt = nomAczSgArt;
        this.nomAczPlNeart = nomAczPlNeart;
        this.nomAczPlArt = nomAczPlArt;
        this.genDatPlArt = "";
        this.genDatPlNeart = "";
        this.genDatSgArt = "";
        this.genDatSgNeart = "";

        this.allForms.add(nomAczSgNeart);
        this.allForms.add(nomAczSgArt);
        this.allForms.add(nomAczPlNeart);
        this.allForms.add(nomAczPlArt);
    }

    // getters
    String getNomAczSgArt() { return this.nomAczSgArt; }
    String getNomAczSgNeart() { return this.nomAczSgNeart; }
    String getNomAczPlArt() { return this.nomAczPlArt; }
    String getNomAczPlNeart() { return this.nomAczPlNeart; }
    String getGenDatSgArt() { return this.genDatSgArt; }
    String getGenDatSgNeart() { return this.genDatSgNeart; }
    String getGenDatPlArt() { return this.genDatPlArt; }
    String getGenDatPlNeart() { return this.genDatPlNeart; }
    List<String> getAllForms() { return this.allForms; }
    int getGen() { return this.gen; }

    void setNomAczSgArt(String value) { this.nomAczSgArt  = value; }
    void setNomAczSgNeart(String value) { this.nomAczSgNeart = value; }
    void setNomAczPlArt(String value) { this.nomAczPlArt  = value; }
    void setNomAczPlNeart(String value) { this.nomAczPlNeart = value; }
    void setGenDatSgArt(String value) { this.genDatSgArt = value; }
    void setGenDatSgNeart(String value) { this.genDatSgNeart = value; }
    void setGenDatPlArt(String value) { this.genDatPlArt  = value; }
    void setGenDatPlNeart(String value) { this.genDatPlNeart = value; }
}
