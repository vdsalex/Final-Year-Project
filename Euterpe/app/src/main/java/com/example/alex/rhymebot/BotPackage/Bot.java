package com.example.alex.rhymebot.BotPackage;

import com.example.alex.rhymebot.Adapter.ChatMessage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

public class Bot
{
    private final String[] CONJS = {"iar", "dar", "și"};
    private final char[] SEMIVOWELS = {'i', 'u', 'o', 'e'};
    private final char[] VOWELS = {'a', 'e', 'i', 'o', 'u', 'ă', 'â', 'î'};
    private final String DEFAULT_VERSES = "Să-nțeleg ce spui aș vrea\nCa poet să-ți pot părea.";
    private Flavor flavor;
    private ArrayList<VocabWord> vocabulary;
    private ArrayList<Noun> nouns;
    private ArrayList<Verb> verbs;
    private ArrayList<Adjective> adjectives;
    private ArrayList<String> adverbs;
    private final boolean ART = true;
    private final boolean NEART = false;
    private final int SAPC = 0;
    private final int ASPC = 1;
    private final int SACP = 2;
    private final int ASCP = 3;
    private final int CPSA = 4;
    private final int CPAS = 5;
    private final int PCAS = 6;
    private final int PCSA = 7;
    private final int MALE = 0;
    private final int FEMALE = 1;
    private final int NEUTRAL = 2;
    private final int VERB = 2;
    private final int NOUN = 0;
    private final int ADJECTIVE = 1;
    private final int ADVERB = 3;

    public Bot(char mode, String vocabPath, String adjPath, String advPath, String vbsPath, String nnsPath)
    {
        this.flavor = new Flavor();
        this.vocabulary = new ArrayList<>();
        this.adjectives = new ArrayList<>();
        this.adverbs = new ArrayList<>();
        this.verbs = new ArrayList<>();
        this.nouns = new ArrayList<>();

        switch(mode)
        {
            case '0':
            {
                flavor.setAdjectives(createAdjectivesFlavor('0'));
                flavor.setNouns(createNounsFlavor('0'));
                flavor.setVerbs(createVerbsFlavor('0'));
                flavor.setAdverbes(createAdverbsFlavor('0'));
                break;
            }

            case '1':
            {
                flavor.setAdjectives(createAdjectivesFlavor('1'));
                flavor.setNouns(createNounsFlavor('1'));
                flavor.setVerbs(createVerbsFlavor('1'));
                flavor.setAdverbes(createAdverbsFlavor('1'));
                break;
            }

            case '2':
            {
                flavor.setAdjectives(createAdjectivesFlavor('2'));
                flavor.setNouns(createNounsFlavor('2'));
                flavor.setVerbs(createVerbsFlavor('2'));
                flavor.setAdverbes(createAdverbsFlavor('2'));
                break;
            }
        }

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(vocabPath), "UTF-8"));
            BufferedReader brAdj = new BufferedReader(new InputStreamReader(new FileInputStream(adjPath), "UTF-8"));
            BufferedReader brVbs = new BufferedReader(new InputStreamReader(new FileInputStream(vbsPath), "UTF-8"));
            BufferedReader brNns = new BufferedReader(new InputStreamReader(new FileInputStream(nnsPath), "UTF-8"));
            BufferedReader brAdv = new BufferedReader(new InputStreamReader(new FileInputStream(advPath), "UTF-8"));

            String line;

            while((line = br.readLine()) != null)
            {
                vocabulary.add(new VocabWord(line));
            }

            br.close();

            while((line = brAdj.readLine()) != null)
            {
                adjectives.add(new Adjective(line));
            }

            brAdj.close();

            while((line = brVbs.readLine()) != null)
            {
                verbs.add(new Verb(line));
            }

            brVbs.close();

            while((line = brNns.readLine()) != null)
            {
                nouns.add(new Noun(line));
            }

            brNns.close();

            while((line = brAdv.readLine()) != null)
            {
                adverbs.add(line.trim());
            }

            brAdv.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    public ChatMessage answer(ChatMessage userMessage)
    {
        List<String> words = tokenizeMessage(userMessage.getMessage());

        for(String word: words)
        {
            int index = binarySearch(vocabulary, word);

            if(index != -1)
            {
                VocabWord vocabWord = vocabulary.get(index);

                String verses = generateVerses(vocabWord);

                if(verses.length() == 0)
                {
                    return new ChatMessage(userMessage.getId() + 1, DEFAULT_VERSES, userMessage.getActiveMod());
                }

                return new ChatMessage(userMessage.getId() + 1, verses, userMessage.getActiveMod());
            }
        }

        return new ChatMessage(userMessage.getId() + 1, DEFAULT_VERSES, userMessage.getActiveMod());
    }

    private String generateVerses(VocabWord vocabWord)
    {
        String verse2;
        String ending;

        List<Integer> orders = new ArrayList<>();

        orders.add(SAPC);
        orders.add(ASPC);
        orders.add(PCAS);
        orders.add(PCSA);
        orders.add(CPAS);
        orders.add(CPSA);
        orders.add(SACP);
        orders.add(ASCP);

        int order = orders.get(ThreadLocalRandom.current().nextInt(0, 8));

        while(orders.size() > 0)
        {
            List<String> verse1Params = generateVerse1(vocabWord, order);

            ending = getVerseEnding(verse1Params.get(0));

            String wordsThatRhyme = wordsThatRhyme(ending);
            String lastWordOfVerse1 = getVerseLastWord(verse1Params.get(0));
            wordsThatRhyme = removeLastWordFromWordsThatRhyme(wordsThatRhyme, lastWordOfVerse1);

            if(!wordsThatRhyme.equals(""))
            {
                verse1Params.add(wordsThatRhyme);
                verse2 = CONJS[ThreadLocalRandom.current().nextInt(0, 3)] + " " + generateVerse2(verse1Params);

                if(verse2.length() > 4)
                {
                    return verse1Params.get(0).substring(0, 1).toUpperCase() +
                            verse1Params.get(0).substring(1) + ",\n" +
                            verse2.substring(0, 1).toUpperCase() +
                            verse2.substring(1) + '.';
                }
            }

            removeElement(orders, order);
            order = orders.get(ThreadLocalRandom.current().nextInt(0, orders.size()));
        }

        return "";
    }

    private void removeElement(List<Integer> l, Integer el)
    {
        for(Integer elem: l)
        {
            if(elem.equals(el))
            {
                l.remove(elem);
                break;
            }
        }
    }

    private List<String> generateVerse1(VocabWord vocabWord, int order)
    {
        StringBuilder verse1 = new StringBuilder("");
        List<String> params = new ArrayList<>();

        switch(order)
        {
            case SAPC:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                String art = getNArt(vocabWord);

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case PCSA:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                String art = getNArt(vocabWord);

                verse1.append(art);
                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case PCAS:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case ASPC:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case ASCP:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case CPAS:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            case CPSA:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                String art = getNArt(vocabWord);

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }

            // SACP
            default:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                String art = getNArt(vocabWord);

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                String verseString = verse1.toString();

                params.add(verseString);
                params.add(vb.get(0));
                params.add(String.valueOf(getNumberOfSyllablesInVerse(verseString)));

                return params;
            }
        }
    }

    private String generateVerse2(List<String> verse1Params)
    {
        //int timeOfVbInVerse1 = Integer.parseInt(verse1Params.get(1));
        String[] wordsThatRhyme = verse1Params.get(3).split(" ");

        for(String wordThatRhymes: wordsThatRhyme)
        {
            int indexInVocab = binarySearch(vocabulary, wordThatRhymes);

            if(indexInVocab == -1)
            {
                continue;
            }

            VocabWord vocabWord = vocabulary.get(indexInVocab);

            if(vocabWord.getPartOfSpeech() == NOUN)
            {
                if(isNounArticulated(vocabWord))
                {
                    continue;
                }

                int[] orders = {CPAS, PCAS};

                String verse2 = generateVerse21(vocabWord, orders[ThreadLocalRandom.current().nextInt(0, 2)]);
                int nr = Math.abs(getNumberOfSyllablesInVerse(verse2) + 1 - Integer.parseInt(verse1Params.get(2)));

                if(nr <= 2)
                {
                    return verse2;
                }
                else
                {
                    continue;
                }
            }

            if(vocabWord.getPartOfSpeech() == ADJECTIVE)
            {
                if(isAdjectiveArticulated(vocabWord))
                {
                    continue;
                }

                int[] orders = {CPSA, PCSA};

                String verse2 = generateVerse21(vocabWord, orders[ThreadLocalRandom.current().nextInt(0, 2)]);
                int nr = Math.abs(getNumberOfSyllablesInVerse(verse2) + 1 - Integer.parseInt(verse1Params.get(2)));

                if(nr <= 2)
                {
                    return verse2;
                }
                else
                {
                    continue;
                }
            }

            if(vocabWord.getPartOfSpeech() == VERB)
            {
                int[] orders = {SACP, ASCP};

                String verse2 = generateVerse21(vocabWord, orders[ThreadLocalRandom.current().nextInt(0, 2)]);
                int nr = Math.abs(getNumberOfSyllablesInVerse(verse2) + 1 - Integer.parseInt(verse1Params.get(2)));

                if(nr <= 2)
                {
                    return verse2;
                }
                else
                {
                    continue;
                }
            }

            if(vocabWord.getPartOfSpeech() == ADVERB)
            {
                int[] orders = {SAPC, ASPC};

                String verse2 = generateVerse21(vocabWord, orders[ThreadLocalRandom.current().nextInt(0, 2)]);
                int nr = Math.abs(getNumberOfSyllablesInVerse(verse2) + 1 - Integer.parseInt(verse1Params.get(2)));

                if(nr <= 2)
                {
                    return verse2;
                }
            }
        }

        return "";
    }

    private String generateVerse21(VocabWord targetWord, int order)
    {
        StringBuilder verse1 = new StringBuilder("");
        VocabWord vocabWord;
        int rand1;

        if(targetWord.getPartOfSpeech() == ADJECTIVE)
        {
            int adjGen = getAdjectiveGender(targetWord);

            do
            {
                rand1 = ThreadLocalRandom.current().nextInt(0, flavor.getNouns().size());
            }
            while(flavor.getNouns().get(rand1).getGen() != adjGen);

            vocabWord = vocabulary.get(binarySearch(vocabulary, flavor.getNouns().get(rand1).getNomAczSgArt()));
        }
        else if(targetWord.getPartOfSpeech() == NOUN)
        {
            vocabWord = targetWord;
        }
        else
        {
            rand1 = ThreadLocalRandom.current().nextInt(0, flavor.getNouns().size());

            vocabWord = vocabulary.get(binarySearch(vocabulary, flavor.getNouns().get(rand1).getNomAczSgArt()));
        }

        switch(order)
        {
            case SAPC:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                String art = getNArt(vocabWord);

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                return verse1.toString();
            }

            case PCSA:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                String art = getNArt(vocabWord);

                verse1.append(art);
                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));

                return verse1.toString();
            }

            case PCAS:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());

                return verse1.toString();
            }

            case ASPC:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                return verse1.toString();
            }

            case ASCP:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));

                return verse1.toString();
            }

            case CPAS:
            {
                unart(vocabWord);
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, ART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vocabWord.getString());

                return verse1.toString();
            }

            case CPSA:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                String art = getNArt(vocabWord);

                verse1.append(adv);
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));

                return verse1.toString();
            }

            // SACP
            default:
            {
                List<String> adj = getARandomAdjectiveFromFlavor(vocabWord, NEART);
                List<String> vb = getARandomVerbFromFlavor(vocabWord);
                String adv = getARandomAdverbFromFlavor();

                if(targetWord.getPartOfSpeech() == ADJECTIVE)
                {
                    adj.set(0, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == VERB)
                {
                    vb.set(1, targetWord.getString());
                }

                if(targetWord.getPartOfSpeech() == ADVERB)
                {
                    adv = targetWord.getString();
                }

                String art = getNArt(vocabWord);

                verse1.append(art);

                if(art.length() > 0)
                {
                    verse1.append(' ');
                }

                verse1.append(vocabWord.getString());
                verse1.append(' ');

                verse1.append(adj.get(0));
                verse1.append(' ');

                verse1.append(vb.get(1));
                verse1.append(' ');

                verse1.append(adv);

                return verse1.toString();
            }
        }
    }

    private String wordsThatRhyme(String verse1Ending)
    {
        StringBuilder words = new StringBuilder("");

        for(Noun noun: flavor.getNouns())
        {
            if(noun.getNomAczPlNeart().endsWith(verse1Ending))
            {
                words.append(noun.getNomAczPlNeart());
                words.append(' ');
            }

            if(noun.getNomAczPlArt().endsWith(verse1Ending))
            {
                words.append(noun.getNomAczPlArt());
                words.append(' ');
            }

            if(noun.getNomAczSgArt().endsWith(verse1Ending))
            {
                words.append(noun.getNomAczSgArt());
                words.append(' ');
            }

            if(noun.getNomAczSgNeart().endsWith(verse1Ending))
            {
                words.append(noun.getNomAczSgNeart());
                words.append(' ');
            }
        }

        for(Verb verb: flavor.getVerbs())
        {
            for(int i = 1; i < verb.getpers3Sg().size(); i++)
            {
                if(verb.getpers3Sg().get(i).endsWith(verse1Ending))
                {
                    words.append(verb.getpers3Sg().get(i));
                    words.append(' ');
                }
            }

            for(int i = 1; i < verb.getpers3Pl().size(); i++)
            {
                if(verb.getpers3Pl().get(i).endsWith(verse1Ending))
                {
                    words.append(verb.getpers3Pl().get(i));
                    words.append(' ');
                }
            }
        }

        for(Adjective adj: flavor.getAdjectives())
        {
            for(String form: adj.getAllForms())
            {
                if(form.endsWith(verse1Ending))
                {
                    words.append(form);
                    words.append(' ');
                }
            }
        }

        for(String adv: flavor.getAdverbs())
        {
            if(adv.endsWith(verse1Ending))
            {
                words.append(adv);
                words.append(' ');
            }
        }

        for(Noun flavorNoun: flavor.getNouns())
        {
            int nounIndex = binarySearch(vocabulary, flavorNoun.getNomAczSgNeart());

            if(nounIndex != -1)
            {
                List<String> similarWords = vocabulary.get(nounIndex).getSimilarWords();

                for(String similarWord: similarWords)
                {
                    int similarWordIndex = binarySearch(vocabulary, similarWord);

                    if(similarWordIndex != -1)
                    {
                        if(vocabulary.get(similarWordIndex).getString().endsWith(verse1Ending))
                        {
                            words.append(similarWord);
                            words.append(' ');
                        }
                    }
                }
            }
        }

        for(Adjective flavorAdj: flavor.getAdjectives())
        {
            int adjIndex = binarySearch(vocabulary, flavorAdj.getNomAczPlNeartM());

            if(adjIndex != -1)
            {
                List<String> similarWords = vocabulary.get(adjIndex).getSimilarWords();

                for(String similarWord: similarWords)
                {
                    int similarWordIndex = binarySearch(vocabulary, similarWord);

                    if(similarWordIndex != -1)
                    {
                        if(vocabulary.get(similarWordIndex).getString().endsWith(verse1Ending))
                        {
                            words.append(similarWord);
                            words.append(' ');
                        }
                    }
                }
            }
        }

        for(Verb flavorVb: flavor.getVerbs())
        {
            int vbIndex = binarySearch(vocabulary, flavorVb.getpers3Sg().get(1));

            if(vbIndex != -1)
            {
                List<String> similarWords = vocabulary.get(vbIndex).getSimilarWords();

                for(String similarWord: similarWords)
                {
                    int similarWordIndex = binarySearch(vocabulary, similarWord);

                    if(similarWordIndex != -1)
                    {
                        if(vocabulary.get(similarWordIndex).getString().endsWith(verse1Ending))
                        {
                            words.append(similarWord);
                            words.append(' ');
                        }
                    }
                }
            }
        }

        for(String flavorAdv: flavor.getAdverbs())
        {
            int advIndex = binarySearch(vocabulary, flavorAdv);

            if(advIndex != -1)
            {
                List<String> similarWords = vocabulary.get(advIndex).getSimilarWords();

                for(String similarWord: similarWords)
                {
                    int similarWordIndex = binarySearch(vocabulary, similarWord);

                    if(similarWordIndex != -1)
                    {
                        if(vocabulary.get(similarWordIndex).getString().endsWith(verse1Ending))
                        {
                            words.append(similarWord);
                            words.append(' ');
                        }
                    }
                }
            }
        }

        return words.toString();
    }

    private String getVerseLastWord(String verse)
    {
        StringBuilder word = new StringBuilder("");

        int i = verse.length() - 1;

        while(isLetter(verse.charAt(i)))
        {
            word.append(verse.charAt(i));
            i--;
        }

        return word.reverse().toString();
    }

    private String getVerseEnding(String verse)
    {
        StringBuilder end = new StringBuilder("");
        int i = verse.length() - 1;

        end.append(verse.charAt(i));
        end.append(verse.charAt(i-1));

        if(!isVowel(verse.charAt(i)) && !isVowel(verse.charAt(i-1)))
        {
            end.append(verse.charAt(i-2));
        }

//        if(!isVowel(verse.charAt(i)))
//        {
//            end.append(verse.charAt(i));
//            i--;
//            if(i >= 0 && isVowel(verse.charAt(i)))
//            {
//                int countVowels = 0;
//
//                while(i >= 0 && isVowel(verse.charAt(i)))
//                {
//                    countVowels++;
//                    end.append(verse.charAt(i));
//                    i--;
//                }
//
//                if(countVowels >= 3)
//                {
//                    end.deleteCharAt(end.length() - 1);
//                }
//            }
//        }
//        else
//        {
//            end.append(verse.charAt(i));
//
//            i--;
//
//            if(i >= 0 && !isVowel(verse.charAt(i)))
//            {
//                while(i > 0 && !isVowel(verse.charAt(i)))
//                {
//                    end.append(verse.charAt(i));
//
//                    if(isVowel(verse.charAt(i - 1)))
//                    {
//                        end.deleteCharAt(end.length() - 1);
//                        break;
//                    }
//
//                    i--;
//                }
//            }
//        }

        return end.reverse().toString();
    }

    private String removeLastWordFromWordsThatRhyme(String words, String lastWord)
    {
        String[] wordsArray = words.split(" ");
        StringBuilder result = new StringBuilder("");

        for(String wordArray: wordsArray)
        {
            if(!wordArray.equals(lastWord))
            {
                result.append(wordArray);
                result.append(' ');
            }
        }

        return result.toString();
    }

    private boolean isNounArticulated(VocabWord vocabWord)
    {
        Noun noun = nouns.get(vocabWord.getIndexInPartOfSpeechFile());

        return noun.getNomAczSgArt().equals(vocabWord.getString()) || noun.getNomAczPlArt().equals(vocabWord.getString());

    }

    private boolean isAdjectiveArticulated(VocabWord vocabWord)
    {
        Adjective adj = adjectives.get(vocabWord.getIndexInPartOfSpeechFile());

        return adj.getNomAczSgArtM().equals(vocabWord.getString()) || adj.getNomAczPlArtM().equals(vocabWord.getString()) ||
                adj.getNomAczPlArtF().equals(vocabWord.getString()) || adj.getNomAczSgArtF().equals(vocabWord.getString());

    }

    private int getNumberOfSyllablesInVerse(String verse)
    {
        List<String> words = tokenizeMessage(verse);

        int syllables = 0;

        for(String word: words)
        {
            syllables += getNumberOfSyllabesOfWord(word);
        }

        return syllables;
    }

    private int getNumberOfSyllabesOfWord(String word)
    {
        int wordLen = word.length();
        int i = 0;
        int syllables = 0;

        while(i < wordLen)
        {
            if(isSemivowel(word.charAt(i)))
            {
                if(triftong(word, wordLen, i))
                {
                    syllables++;
                    i += 3;
                    continue;
                }

                if(isVowel(word.charAt(i)))
                {
                    if(diftong(word, wordLen, i))
                    {
                        i += 2;
                    }

                    syllables++;
                    i++;
                    continue;
                }
            }

            i++;
        }

        return syllables;
    }

    private boolean triftong(String word, int wordLen, int i)
    {
        if(i < wordLen - 2 && isVowel(word.charAt(i + 1)) && isSemivowel(word.charAt(i + 2)))
        {
            return true;
        }

        return false;
    }

    private boolean diftong(String word, int wordLen, int i)
    {
        if(i < wordLen - 1)
        {
            if(isSemivowel(word.charAt(i + 1)))
            {
                return true;
            }

            if(isSemivowel(word.charAt(i)) && isVowel(word.charAt(i + 1)))
            {
                return true;
            }
        }

        return false;
    }

    private boolean isSemivowel(char c)
    {
        for(char semiv: SEMIVOWELS)
        {
            if(c == semiv)
            {
                return true;
            }
        }

        return false;
    }

    private boolean isVowel(char c)
    {
        for(char v: VOWELS)
        {
            if(c == v)
            {
                return true;
            }
        }

        return false;
    }

    private void unart(VocabWord vocabWord)
    {
        Noun noun = nouns.get(vocabWord.getIndexInPartOfSpeechFile());

        if(noun.getNomAczSgArt().equals(vocabWord.getString()))
        {
            vocabWord.setString(noun.getNomAczSgNeart());
        }

        if(noun.getNomAczPlArt().equals(vocabWord.getString()))
        {
            vocabWord.setString(noun.getNomAczPlNeart());
        }
    }

    private List<String> getARandomAdjectiveFromFlavor(VocabWord vocabWord, boolean isArt)
    {
        Noun noun = nouns.get(vocabWord.getIndexInPartOfSpeechFile());
        int gender = noun.getGen();
        List<String> params = new ArrayList<>();

        Adjective adj = flavor.getAdjectives().get(ThreadLocalRandom.current().nextInt(0, flavor.getAdjectives().size()));

        if(noun.getNomAczSgNeart().equals(vocabWord.getString()) || noun.getNomAczSgArt().equals(vocabWord.getString()))
        {
            if(gender == 0)
            {
                if(isArt)
                {
                    params.add(adj.getNomAczSgArtM());
                    params.add("1");

                    return params;
                }

                params.add(adj.getNomAczSgNeartM());
                params.add("0");

                return params;
            }
            else if(gender == 1)
            {
                if(isArt)
                {
                    params.add(adj.getNomAczSgArtF());
                    params.add("3");

                    return params;
                }

                params.add(adj.getNomAczSgNeartF());
                params.add("2");

                return params;
            }
            else
            {
                if(isArt)
                {
                    params.add(adj.getNomAczSgArtM());
                    params.add("1");

                    return params;
                }

                params.add(adj.getNomAczSgNeartM());
                params.add("0");

                return params;
            }
        }

        if(noun.getNomAczPlArt().equals(vocabWord.getString()) || noun.getNomAczPlNeart().equals(vocabWord.getString()))
        {
            if(gender == 0)
            {
                if(isArt)
                {
                    params.add(adj.getNomAczPlArtM());
                    params.add("5");

                    return params;
                }

                params.add(adj.getNomAczPlNeartM());
                params.add("4");

                return params;
            }
            else if(gender == 1)
            {
                if(isArt)
                {
                    params.add(adj.getNomAczPlArtF());
                    params.add("7");

                    return params;
                }

                params.add(adj.getNomAczPlNeartF());
                params.add("6");

                return  params;
            }
            else
            {
                if(isArt)
                {
                    params.add(adj.getNomAczPlArtF());
                    params.add("7");

                    return params;
                }

                params.add(adj.getNomAczPlNeartF());
                params.add("6");

                return  params;
            }
        }

        return params;
    }

    private List<String> getARandomVerbFromFlavor(VocabWord vocabWord)
    {
        Noun noun = nouns.get(vocabWord.getIndexInPartOfSpeechFile());

        List<String> params = new ArrayList<>();
        params.add("#");
        params.add("#");

        Verb vb = flavor.getVerbs().get(ThreadLocalRandom.current().nextInt(0, flavor.getVerbs().size()));

        if(noun.getNomAczSgNeart().equals(vocabWord.getString()) || noun.getNomAczSgArt().equals(vocabWord.getString()))
        {
            int vbTime = ThreadLocalRandom.current().nextInt(1, 3);

            params.set(0, String.valueOf(vbTime));
            params.set(1, vb.getpers3Sg().get(vbTime));
        }

        if(noun.getNomAczPlNeart().equals(vocabWord.getString()) || noun.getNomAczPlArt().equals(vocabWord.getString()))
        {
            int vbTime = ThreadLocalRandom.current().nextInt(1, 3);

            params.set(0, String.valueOf(vbTime));
            params.set(1, vb.getpers3Pl().get(vbTime));
        }

        return params;
    }

    private String getARandomAdverbFromFlavor()
    {
        return flavor.getAdverbs().get(ThreadLocalRandom.current().nextInt(0, flavor.getAdverbs().size()));
    }

    private String getNArt(VocabWord vocabWord)
    {
        Noun noun = nouns.get(vocabWord.getIndexInPartOfSpeechFile());

        if(noun.getNomAczSgNeart().equals(vocabWord.getString()))
        {
            if(noun.getGen() == 0 || noun.getGen() == 2)
            {
                return "un";
            }
            else
            {
                return "o";
            }
        }

        if(noun.getNomAczPlNeart().equals(vocabWord.getString()))
        {
            return "niște";
        }

        return "";
    }

    private int getAdjectiveGender(VocabWord vocabWord)
    {
        Adjective adj = adjectives.get(vocabWord.getIndexInPartOfSpeechFile());

        if(vocabWord.getString().equals(adj.getNomAczPlArtM()) ||
            vocabWord.getString().equals(adj.getNomAczPlNeartM()) ||
            vocabWord.getString().equals(adj.getNomAczSgArtM()) ||
            vocabWord.getString().equals(adj.getNomAczSgNeartM())
            )
        {
            return MALE;
        }
        else
        {
            return FEMALE;
        }
    }

    private List<String> tokenizeMessage(String message)
    {
        List<String> messageWords = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(message);

        for(int i = 0; tokenizer.hasMoreTokens(); i++)
        {
            messageWords.add(removeSeparators(tokenizer.nextToken().toLowerCase()));
        }

        return messageWords;
    }

    private String removeSeparators(String string)
    {
        StringBuilder stringBuilder = new StringBuilder("");

        for(int i = 0; i < string.length(); i++)
        {
            if(isLetter(string.charAt(i)))
            {
                stringBuilder.append(string.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    private boolean isLetter(char c)
    {
        return (c >= 'a' && c <= 'z') || c == 'ț' || c == 'ș' || c == 'ă' || c == 'î' || c == 'â';
    }

    private List<Noun> createNounsFlavor(char mode)
    {
        switch(mode)
        {
            case '0':
            {
                List<Noun> nns = new ArrayList<>();
                nns.add(new Noun(1, "lumină", "lumina", "lumini", "luminile"));
                nns.add(new Noun(1, "rază", "raza", "raze", "razele"));
                nns.add(new Noun(1, "noapte", "noaptea", "nopți", "nopțile"));
                nns.add(new Noun(1, "stea", "steaua", "stele", "stelele"));
                nns.add(new Noun(1, "lună", "luna", "luni", "lunile"));
                nns.add(new Noun(0, "codru", "codrul", "codri", "codrii"));
                nns.add(new Noun(1, "seară", "seara", "seri", "serile"));
                nns.add(new Noun(2, "cer", "cerul", "ceruri", "cerurile"));
                nns.add(new Noun(0, "stejar", "stejarul", "stejari", "stejarii"));
                nns.add(new Noun(0, "tei", "teiul", "tei", "teii"));
                nns.add(new Noun(1, "fată", "fata", "fete", "fetele"));
                nns.add(new Noun(0, "ochi", "ochiul", "ochi", "ochii"));
                nns.add(new Noun(0, "păr", "părul", "peri", "perii"));
                nns.add(new Noun(2, "lac", "lacul", "lacuri", "lacurile"));
                nns.add(new Noun(1, "pădure", "pădurea", "păduri", "pădurile"));
                nns.add(new Noun(2, "vis", "visul", "vise", "visele"));
                nns.add(new Noun(0, "luceafăr", "luceafărul", "luceferi", "luceferii"));
                nns.add(new Noun(0, "băiat", "băiatul", "băieți", "băieții"));
                nns.add(new Noun(0, "voievod", "voievodul", "voievozi", "voievozii"));
                nns.add(new Noun(0, "student", "studentul", "studenți", "studenții"));

                return nns;
            }

            case '1':
            {
                List<Noun> nns = new ArrayList<>();

                nns.add(new Noun(1, "noapte", "noaptea", "nopți", "nopțile"));
                nns.add(new Noun(2, "vaiet", "vaietul", "vaiete", "vaietele"));
                nns.add(new Noun(1, "furtună", "furtuna", "furtuni", "furtunile"));
                nns.add(new Noun(2, "oraș", "orașul", "orașe", "orașele"));
                nns.add(new Noun(1, "stradă", "strada", "străzi", "străzile"));
                nns.add(new Noun(1, "prăpastie", "prăpastia", "prăpăstii", "prăpastiile"));
                nns.add(new Noun(0, "plumb", "plumbul", "plumbi", "plumbii"));
                nns.add(new Noun(1, "gară", "gara", "gări", "gările"));
                nns.add(new Noun(0, "student", "studentul", "studenți", "studenții"));
                nns.add(new Noun(1, "ruină", "ruina", "ruinuri", "ruinurile"));
                nns.add(new Noun(2, "geamantan", "geamantanul", "geamantane", "geamantanele"));

                return nns;
            }

            default:
            {
                List<Noun> nns = new ArrayList<>();

                nns.add(new Noun(0, "inorog", "inorogul", "inorogi", "inorogii"));
                nns.add(new Noun(0, "motan", "motanul", "motani", "motanii"));
                nns.add(new Noun(1, "primăvară", "primăvara", "primăveri", "primăverile"));
                nns.add(new Noun(2, "tramvai", "tramvaiul", "tramvaie", "tramvaiele"));
                nns.add(new Noun(1, "zăpadă", "zăpada", "zăpezi", "zăpezile"));
                nns.add(new Noun(2, "prăpastie", "prăpastia", "prăpăstii", "prăpastiile"));
                nns.add(new Noun(2, "basm", "basmul", "basme", "basmele"));
                nns.add(new Noun(0, "meteorit", "meteoritul", "meteoriți", "meteoriții"));
                nns.add(new Noun(0, "student", "studentul", "studenți", "studenții"));
                nns.add(new Noun(0, "erou", "eroul", "eroi", "eroii"));
                nns.add(new Noun(0, "cireșar", "cireșarul", "cireșari", "cireșarii"));
                nns.add(new Noun(0, "cavaler", "cavalerul", "cavaleri", "cavalerii"));
                nns.add(new Noun(0, "cățel", "cățelul", "căței", "cățeii"));
                nns.add(new Noun(0, "morcov", "morcovul", "morcovi", "morcovii"));
                nns.add(new Noun(1, "cireașă", "cireașa", "cireșe", "cireșele"));
                nns.add(new Noun(0, "copil", "copilul", "copii", "copiii"));
                nns.add(new Noun(0, "băiețel", "băiețelul", "băieței", "băiețeii"));
                nns.add(new Noun(1, "fetiță", "fetița", "fetițe", "fetițele"));

                return nns;
            }
        }
    }

    private List<Adjective> createAdjectivesFlavor(char mode)
    {
        switch (mode)
        {
            case '0':
            {
                List<Adjective> adjs = new ArrayList<>();

                adjs.add(new Adjective("dulce", "dulcele", "dulce", "dulcea", "dulci", "dulcii", "dulci", "dulcile"));
                adjs.add(new Adjective("albastru", "albastrul", "albastră", "albastra", "albaștri", "albaștrii", "albastre", "albastrele"));
                adjs.add(new Adjective("îndrăgostit", "îndrăgostitul", "îndrăgostită", "îndrăgostita", "îndrăgostiți", "îndrăgostiții", "îndrăgostite", "îndrăgostitele"));
                adjs.add(new Adjective("senin", "seninul", "senină", "senina", "senini", "seninii", "senine", "seninele"));
                adjs.add(new Adjective("fermecat", "fermecatul", "fermecată", "fermecata", "fermecați", "fermecații", "fermecate", "fermecatele"));
                adjs.add(new Adjective("liniștit", "liniștitul", "liniștită", "liniștita", "liniștiți", "liniștiții", "liniștite", "liniștitele"));
                adjs.add(new Adjective("etern", "eternul", "eternă", "eterna", "eterni", "eternii", "eterne", "eternele"));
                adjs.add(new Adjective("nemuritor", "nemuritorul", "nemuritoare", "nemuritoarea", "nemuritori", "nemuritorii", "nemuritoare", "nemuritoarele"));
                adjs.add(new Adjective("bălai", "bălaiul", "bălaie", "bălaia", "bălai", "bălaii", "bălaie", "bălaiele"));
                adjs.add(new Adjective("duios", "duiosul", "duioasă", "duioasa", "duioși", "duioșii", "duioase", "duioasele"));
                adjs.add(new Adjective("măreț", "mărețul", "măreață", "măreața", "măreți", "măreții", "mărețe", "mărețele"));
                adjs.add(new Adjective("diafan", "diafanul", "diafană", "diafana", "diafani", "diafanii", "diafane", "diafanele"));
                adjs.add(new Adjective("suav", "suavul", "suavă", "suava", "suavi", "suavii", "suave", "suavele"));

                return adjs;
            }

            case '1':
            {
                List<Adjective> adjs = new ArrayList<>();

                adjs.add(new Adjective("gri", "griul", "gri", "griul", "griuri", "griurile", "griuri", "griurile"));
                adjs.add(new Adjective("cald", "caldul", "caldă", "calda", "calzi", "calzii", "calde", "caldele"));
                adjs.add(new Adjective("epuizat", "epuizatul", "epuizată", "epuizata", "epuizați", "epuizații", "epuizate", "epuizatele"));
                adjs.add(new Adjective("amar", "amarul", "amară", "amara", "amari", "amarii", "amare", "amarele"));
                adjs.add(new Adjective("plictisit", "plictisitul", "plictisită", "plictisita", "plictisiți", "plictisiții", "plictisite", "plictisitele"));
                adjs.add(new Adjective("întunecat", "întunecatul", "întunecată", "întunecata", "întunecați", "întunecații", "întunecate", "întunecatele"));
                adjs.add(new Adjective("negru", "negrul", "neagră", "neagra", "negri", "negrii", "negre", "negrele"));
                adjs.add(new Adjective("melancolic", "melancolicul", "melancolică", "melancolica", "melancolici", "melancolicii", "melancolice", "melancolicele"));
                adjs.add(new Adjective("nostalgic", "nostalgicul", "nostalgică", "nostalgica", "nostalgici", "nostalgicii", "nostalgice", "nostalgicele"));
                adjs.add(new Adjective("obosit", "obositul", "obosită", "obosita", "obosiți", "obosiții", "obosite", "obositele"));
                adjs.add(new Adjective("palid", "palidul", "palidă", "palida", "palizi", "palizii", "palide", "palidele"));
                adjs.add(new Adjective("pretențios", "pretențiosul", "pretențioasă", "pretențioasa", "pretențioși", "pretențioșii", "pretențioase", "pretențioasele"));
                adjs.add(new Adjective("pesimist", "pesimistul", "pesimistă", "pesimista", "pesimiști", "pesimiștii", "pesimiste", "pesimistele"));

                return adjs;
            }

            default:
            {
                List<Adjective> adjs = new ArrayList<>();

                adjs.add(new Adjective("tânăr", "tânărul", "tânără", "tânăra", "tineri", "tinerii", "tinere", "tinerele"));
                adjs.add(new Adjective("roz", "rozul", "roz", "rozul", "roz", "rozul", "roz", "rozul"));
                adjs.add(new Adjective("deschis", "deschisul", "deschisă", "deschisa", "deschiși", "deschișii", "deschise", "deschisele"));
                adjs.add(new Adjective("medieval", "medievalul", "medievală", "medievala", "medievali", "medievalii", "medievale", "medievalele"));
                adjs.add(new Adjective("mânjit", "mânjitul", "mânjită", "mânjita", "mânjiți", "mânjiții", "mânjite", "mânjitele"));
                adjs.add(new Adjective("scund", "scundul", "scundă", "scunda", "scunzi", "scunzii", "scunde", "scundele"));
                adjs.add(new Adjective("portocaliu", "portocaliul", "portocalie", "portocalia", "portocalii", "portocaliii", "portocalii", "portocaliile"));
                adjs.add(new Adjective("norocos", "norocosul", "norocoasă", "norocoasa", "norocoși", "norocoșii", "norocoase", "norocoasele"));
                adjs.add(new Adjective("obraznic", "obraznicul", "obraznică", "obraznica", "obraznici", "obraznicii", "obraznice", "obraznicele"));
                adjs.add(new Adjective("rotofei", "rotofeiul", "rotofeie", "rotofeia", "rotofei", "rotofeii", "rotofeie", "rotofeiele"));
                adjs.add(new Adjective("verzui", "verzuiul", "verzuie", "verzuia", "verzui", "verzuii", "verzui", "verzuile"));

                return adjs;
            }
        }
    }

    private List<Verb> createVerbsFlavor(char mode)
    {
        switch(mode)
        {
            case '0':
            {
                List<Verb> vbs = new ArrayList<>();

                vbs.add(new Verb("zâmbit", "zâmbesc", "zâmbeam", "zâmbești", "zâmbeai", "zâmbește", "zâmbea", "zâmbim", "zâmbeam", "zâmbiți", "zâmbeați", "zâmbesc", "zâmbeau"));
                vbs.add(new Verb("zărit","zăresc","zăream","zărești","zăreai","zărește","zărea","zărim","zăream","zăriți","zăreați","zăresc","zăreau"));
                vbs.add(new Verb("iubit","iubesc","iubeam","iubești","iubeai","iubește","iubea","iubim","iubeam","iubiți","iubeați","iubesc","iubeau"));
                vbs.add(new Verb("părut","par","păream","pari","păreai","pare","părea","părem","păream","păreți","păreați","par","păreau"));
                vbs.add(new Verb("zburat","zbor","zburam","zbori","zburai","zboară","zbura","zburăm","zburam","zburați","zburați","zboară","zburau"));
                vbs.add(new Verb("răsărit","răsar","răsăream","răsari","răsăreai","răsare","răsărea","răsărim","răsăream","răsăriți","răsăreați","răsar","răsăreau"));
                vbs.add(new Verb("înflorit","înfloresc","înfloream","înflorești","înfloreai","înflorește","înflorea","înflorim","înfloream","înfloriți","înfloreați","înfloresc","înfloreau"));
                vbs.add(new Verb("mângâiat","mângâi","mângâiam","mângâi","mângâiai","mângâie","mângâia","mângâiem","mângâiam","mângâiați","mângâiați","mângâie","mângâiau"));
                vbs.add(new Verb("alinat","alin","alinam","alini","alinai","alină","alina","alinăm","alinam","alinați","alinați","alină","alinau"));
                vbs.add(new Verb("îmbrățișat","îmbrățișez","îmbrățișam","îmbrățișezi","îmbrățișai","îmbrățișează","îmbrățișa","îmbrățișăm","îmbrățișam","îmbrățișați","îmbrățișați","îmbrățișează","îmbrățișau"));

                return vbs;
            }

            case '1':
            {
                List<Verb> vbs = new ArrayList<>();

                vbs.add(new Verb("plâns","plâng","plângeam","plângi","plângeai","plânge","plângea","plângem","plângeam","plângeți","plângeați","plâng","plângeau"));
                vbs.add(new Verb("strigat","strig","strigam","strigi","strigai","strigă","striga","strigăm","strigam","strigați","	strigați","strigă","strigau"));
                vbs.add(new Verb("oftat","oftez","oftam","oftezi","oftai","oftează","ofta","oftăm","oftam","oftați","oftați","oftează","oftau"));
                vbs.add(new Verb("pierdut","pierd","pierdeam","pierzi","pierdeai","pierde","pierdea","pierdem","pierdeam","pierdeți","pierdeați","pierd","pierdeau"));
                vbs.add(new Verb("căzut","cad","cădeam","cazi","cădeai","cade","cădea","cădem","cădeam","cădeți","cădeați","cad","cădeau"));
                vbs.add(new Verb("sunat","sun","sunam","suni","sunai","sună","suna","sunăm","sunam","sunați","sunați","sună","sunau"));
                vbs.add(new Verb("părut","par","păream","pari","păreai","pare","părea","părem","păream","păreți","păreați","par","păreau"));
                vbs.add(new Verb("întins","întind","întindeam","întinzi","întindeai","întinde","întindea","întindem","întindeam","întindeți","	întindeați","întind","întindeau"));
                vbs.add(new Verb("târâit","târâi","târâiam","târâi","târâiai","târâie","târâia","târâim","	târâiam","târâiți","târâiați","târâie","târâiau"));
                vbs.add(new Verb("studiat","studiez","studiam","studiezi","studiai","studiază","studia","studiem","studiam	","studiați","studiați","studiază","studiau"));
                vbs.add(new Verb("meditat","meditez","meditam","meditezi","meditai","meditează","medita","medităm","meditam","meditați","meditați","meditează","meditau"));
                vbs.add(new Verb("căscat","casc","căscam","caști","căscai","cască","căsca","căscăm","căscam","căscați","căscați","cască","căscau"));

                return vbs;
            }

            default:
            {
                List<Verb> vbs = new ArrayList<>();

                vbs.add(new Verb("înfășurat","înfășor","înfășuram","înfășori","înfășurai","înfășoară","înfășura","înfășurăm","înfășuram","înfășurați","înfășurați","înfășoară","înfășurau"));
                vbs.add(new Verb("râs","râd","râdeam","râzi","râdeai","râde","râdea","râdem","râdeam","râdeți","râdeați","râd","râdeau"));
                vbs.add(new Verb("jucat","joc","jucam","joci","jucai","joacă","jucă","jucăm","jucarăm","jucați","jucați","joacă","jucau"));
                vbs.add(new Verb("visat","visez","visam","visezi","visai","visează","visa","visăm","visam","visați","visați","visează","visau"));
                vbs.add(new Verb("bătut","bat","băteam","bați","băteai","bate","bătea","batem","băteam","bateți","băteați","bat","băteau"));
                vbs.add(new Verb("ros","rod","rodeam","rozi","rodeai","roade","rodea","roadem","rodeam","roadeți","rodeați","rod","rodeau"));
                vbs.add(new Verb("știut","știu","știam","știi","știai","știe","știa","știm","știam","știți","știați","știu","știau"));
                vbs.add(new Verb("zâmbit","zâmbesc","zâmbeam","zâmbești","zâmbeai","zâmbește","zâmbea","zâmbim","zâmbeam","zâmbiți","zâmbeați","zâmbesc","zâmbeau"));
                vbs.add(new Verb("tuns","tund","tundeam","tunzi","tundeai","tunde","tundea","tundem","tundeam","tundeți","tundeați","tund","tundeau"));
                vbs.add(new Verb("cotrobăit","cotrobăiesc","cotrobăiam","cotrobăiești","cotrobăiai","cotrobăiește","cotrobăia","cotrobăim","cotrobăiam","cotrobăiți","cotrobăiați","cotrobăiesc","cotrobăiau"));
                vbs.add(new Verb("sărit","sar","săream","sari","săreai","sare","sărea","sărim","săream","săriți","săreați","sar","săreau"));
                vbs.add(new Verb("mieunat","miaun","mieunam","miauni","mieunai","miaună","mieuna","mieunăm","mieunam","mieunați","mieunați","miaună","mieunau"));
                vbs.add(new Verb("lătrat","latru","lătram","latri","lătrai","latră","lătra","lătrăm","lătram","lătrați","lătrați","latră","lătrau"));
                vbs.add(new Verb("împachetat","împachetez","împachetam","împachetezi","împachetai","împachetează","mpacheta","împachetăm","împachetam","împachetați","împachetați","împachetează","împachetau"));
                vbs.add(new Verb("pieptănat","pieptăn","pieptănam","piepteni","pieptănai","piaptănă","pieptăna","pieptănăm","pieptănam","pieptănați","pieptănați","piaptănă","pieptănau"));

                return vbs;
            }
        }
    }

    private List<String> createAdverbsFlavor(char mode)
    {
        switch(mode)
        {
            case '0':
            {
                List<String> adv = new ArrayList<>();

                adv.add("deodată");
                adv.add("adesea");
                adv.add("câteodată");
                adv.add("cândva");
                adv.add("blând");
                adv.add("încet");
                adv.add("calm");
                adv.add("încetișor");
                adv.add("departe");
                adv.add("adeseori");

                return adv;
            }

            case '1':
            {
                List<String> adv = new ArrayList<>();

                adv.add("câteodată");
                adv.add("greoi");
                adv.add("rareori");
                adv.add("rar");
                adv.add("cândva");
                adv.add("tremurând");
                adv.add("strigând");
                adv.add("brusc");
                adv.add("scârțâind");

                return adv;
            }

            default:
            {
                List<String> adv = new ArrayList<>();

                adv.add("brusc");
                adv.add("imediat");
                adv.add("grăbit");
                adv.add("ieri");
                adv.add("alaltăieri");
                adv.add("fluierând");
                adv.add("râzând");
                adv.add("zâmbind");
                adv.add("mestecând");

                return adv;
            }
        }
    }

    private int binarySearch(List<VocabWord> vocabList, String x)
    {
        int l = 0, r = vocabList.size() - 1;

        while (l <= r)
        {
            int m = l + (r-l)/2;

            // Check if x is present at mid
            if (vocabList.get(m).getString().compareTo(x) == 0)
                return m;

            // If x greater, ignore left half
            if (vocabList.get(m).getString().compareTo(x) < 0)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        // if we reach here, then element was
        // not present
        return -1;
    }
}
