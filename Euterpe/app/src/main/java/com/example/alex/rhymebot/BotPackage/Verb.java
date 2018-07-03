package com.example.alex.rhymebot.BotPackage;

import java.util.ArrayList;
import java.util.List;

class Verb
{
    private String partic;
    private List<String> pers1Sg;
    private List<String> pers2Sg;
    private List<String> pers3Sg;
    private List<String> pers1Pl;
    private List<String> pers2Pl;
    private List<String> pers3Pl;

    Verb(String line)
    {
        String[] lineElems = line.split(" ");

        try
        {
            this.partic = lineElems[0];
            this.pers1Sg = new ArrayList<>();
            this.pers2Sg = new ArrayList<>();
            this.pers3Sg = new ArrayList<>();
            this.pers1Pl = new ArrayList<>();
            this.pers2Pl = new ArrayList<>();
            this.pers3Pl = new ArrayList<>();

            this.pers1Sg.add("am " + partic);
            this.pers1Sg.add(lineElems[1]);
            this.pers1Sg.add(lineElems[2]);

            this.pers2Sg.add("ai " + partic);
            this.pers2Sg.add(lineElems[3]);
            this.pers2Sg.add(lineElems[4]);

            this.pers3Sg.add("a " + partic);
            this.pers3Sg.add(lineElems[5]);
            this.pers3Sg.add(lineElems[6]);

            this.pers1Pl.add("am " + partic);
            this.pers1Pl.add(lineElems[7]);
            this.pers1Pl.add(lineElems[8]);

            this.pers2Pl.add("ați " + partic);
            this.pers2Pl.add(lineElems[9]);
            this.pers2Pl.add(lineElems[10]);

            this.pers3Pl.add("au " + partic);
            this.pers3Pl.add(lineElems[11]);
            this.pers3Pl.add(lineElems[12]);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    Verb(String partic, String prez1Sg, String impf1Sg,
         String prez2Sg, String impf2Sg, String prez3Sg,
         String impf3Sg, String prez1Pl, String impf1Pl,
         String prez2Pl, String impf2Pl, String prez3Pl,
         String impf3Pl)
    {
        this.partic = partic;
        this.pers1Sg = new ArrayList<>();
        this.pers2Sg = new ArrayList<>();
        this.pers3Sg = new ArrayList<>();
        this.pers1Pl = new ArrayList<>();
        this.pers2Pl = new ArrayList<>();
        this.pers3Pl = new ArrayList<>();

        this.pers1Sg.add("am " + partic);
        this.pers1Sg.add(prez1Sg);
        this.pers1Sg.add(impf1Sg);

        this.pers2Sg.add("ai " + partic);
        this.pers2Sg.add(prez2Sg);
        this.pers2Sg.add(impf2Sg);

        this.pers3Sg.add("a " + partic);
        this.pers3Sg.add(prez3Sg);
        this.pers3Sg.add(impf3Sg);

        this.pers1Pl.add("am " + partic);
        this.pers1Pl.add(prez1Pl);
        this.pers1Pl.add(impf1Pl);

        this.pers2Pl.add("ați " + partic);
        this.pers2Pl.add(prez2Pl);
        this.pers2Pl.add(impf2Pl);

        this.pers3Pl.add("au " + partic);
        this.pers3Pl.add(prez3Pl);
        this.pers3Pl.add(impf3Pl);
    }

    // getters
    String getPartic() { return this.partic; }
    List<String> getpers1Sg() { return this.pers1Sg; }
    List<String> getpers2Sg() { return this.pers2Sg; }
    List<String> getpers3Sg() { return this.pers3Sg; }
    List<String> getpers1Pl() { return this.pers1Pl; }
    List<String> getpers2Pl() { return this.pers2Pl; }
    List<String> getpers3Pl() { return this.pers3Pl; }

    //setters
    void setPartic(String value) { this.partic = value; }
    void setpers1Sg(List<String> value) { this.pers1Sg = value; }
    void setpers2Sg(List<String> value) { this.pers2Sg = value; }
    void setpers3Sg(List<String> value) { this.pers3Sg = value; }
    void setpers1Pl(List<String> value) { this.pers1Pl = value; }
    void setpers2Pl(List<String> value) { this.pers2Pl = value; }
    void setpers3Pl(List<String> value) { this.pers3Pl = value; }
}
