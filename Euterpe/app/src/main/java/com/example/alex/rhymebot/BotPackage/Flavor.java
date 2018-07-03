package com.example.alex.rhymebot.BotPackage;

import java.util.ArrayList;
import java.util.List;

public class Flavor
{
    private List<Noun> nouns;
    private List<Verb> verbs;
    private List<Adjective> adjectives;
    private List<String> adverbes;

    Flavor()
    {
        this.nouns = new ArrayList<>();
        this.verbs = new ArrayList<>();
        this.adjectives = new ArrayList<>();
        this.adverbes = new ArrayList<>();
    }

    // getters
    public List<Noun> getNouns() { return this.nouns; }
    public List<Verb> getVerbs() { return this.verbs; }
    public List<Adjective> getAdjectives() { return this.adjectives; }
    public List<String> getAdverbs() { return this.adverbes; }

    // setters
    public void setNouns(List<Noun> value) { this.nouns = value; }
    public void setVerbs(List<Verb> value) { this.verbs = value; }
    public void setAdjectives(List<Adjective> value) { this.adjectives = value; }
    public void setAdverbes(List<String> value) { this.adverbes = value; }

    // adders
    public void addSubstantiv(Noun noun) { this.nouns.add(noun); }
    public void addAdjectiv(Adjective adjective) { this.adjectives.add(adjective); }
    public void addVerb(Verb verb) { this.verbs.add(verb); }
    public void addAdverb(String adverb) { this.adverbes.add(adverb); }
}
