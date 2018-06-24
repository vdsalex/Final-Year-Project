package com.example.alex.rhymebot;

import com.example.alex.rhymebot.Adapter.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Bot
{
    private ArrayList<ChatMessage> messages;
    private final int VERB = 0;
    private final int SUBSTANTIV = 1;
    private final int ADJECTIV = 2;
    private final int ADVERB = 3;
    private final int INTERJECTIE = 4;

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

    public ChatMessage answer(ChatMessage userMessage)
    {
//        if(userMessage.getActiveMod() == '0')
//        {
//            return new ChatMessage(userMessage.getId() + 1, "Ce te legeni, codrule,\n" +
//                    "Fără ploaie, fără vânt,\n" +
//                    "Cu crengile la pământ?", '0');
//        }
//        else if(userMessage.getActiveMod() == '1')
//        {
//            return new ChatMessage(userMessage.getId() + 1, "Dormeau adânc sicriele de plumb,\n" +
//                    "Si flori de plumb si funerar vestmint", '1');
//        }
//        else
//        {
//            return new ChatMessage(userMessage.getId() + 1, "Cu mâna stângă ţi-am întors spre mine chipul,\n" +
//                    "sub cortul adormiţilor gutui\n" +
//                    "şi de-aş putea să-mi rup din ochii tăi privirea, ", '2');
//        }

        List<String> words = tokenizeMessage(userMessage.getMessage());
        StringBuilder answer = new StringBuilder("");

        for(String word: words)
        {
            answer.append(word);
        }

        return new ChatMessage(userMessage.getId() + 1, answer.toString(), userMessage.getActiveMod());
    }

    private List<String> tokenizeMessage(String message)
    {
        List<String> messageWords = new ArrayList<>();
        String separators = ".,?!;:[]{}\\/`~<>@#$%^&*()=+\"'";

        StringTokenizer tokenizer = new StringTokenizer(message, separators);

        for(int i = 0; tokenizer.hasMoreTokens(); i++)
        {
            messageWords.add(tokenizer.nextToken().toLowerCase());
        }

        return messageWords;
    }
}
