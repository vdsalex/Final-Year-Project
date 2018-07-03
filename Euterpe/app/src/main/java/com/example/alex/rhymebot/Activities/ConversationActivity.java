package com.example.alex.rhymebot.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alex.rhymebot.Adapter.ChatMessage;
import com.example.alex.rhymebot.Adapter.MessagesAdapter;

import com.example.alex.rhymebot.BotPackage.Bot;
import com.example.alex.rhymebot.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ConversationActivity extends AppCompatActivity
{
    private EditText editText;
    private int messageId;
    private Bot bot;
    private ArrayList<ChatMessage> savedVersesEminescu;
    private ArrayList<ChatMessage> savedVersesBacovia;
    private ArrayList<ChatMessage> savedVersesStanescu;
    private final long DOUBLE_CLICK_INTERVAL = 250;
    private long currentClickTime;
    private long lastClickTime;
    private char activeMod;
    private MessagesAdapter messagesAdapter;
    private final char END_MESSAGE_MARK = '#';

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        messageId = -1;

        try
        {
            savedVersesEminescu = getSavedVersesFromFile(getFilesDir().getPath() + "/eminescu.txt");
            savedVersesBacovia = getSavedVersesFromFile(getFilesDir().getPath() + "/bacovia.txt");
            savedVersesStanescu = getSavedVersesFromFile(getFilesDir().getPath() + "/stanescu.txt");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        ArrayList<ChatMessage> conversation = getConversationFromInternalStorage();

        final ListView messagesList = (ListView) this.findViewById(R.id.listView);
        messagesAdapter = new MessagesAdapter(this, conversation);
        messagesList.setAdapter(messagesAdapter);

        ImageButton sendButton = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton deleteButton = (ImageButton) findViewById(R.id.imageButton_delete);
        editText = (EditText) findViewById(R.id.editText2);
        lastClickTime = 0;

        try
        {
            activeMod = getActiveMod();

            if(activeMod == '0')
            {
                Toast.makeText(getApplicationContext(), "Modul Eminescu este activ!", Toast.LENGTH_SHORT).show();
            }
            else if(activeMod == '1')
            {
                Toast.makeText(getApplicationContext(), "Modul Bacovia este activ!", Toast.LENGTH_SHORT).show();
            }
            else if(activeMod == '2')
            {
                Toast.makeText(getApplicationContext(), "Modul Stănescu este activ!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Niciun mod nu este activ! Vă rugăm activați mai întâi un mod!", Toast.LENGTH_SHORT).show();
                return;
            }

            String vocabPath = getFilesDir().getPath() + "/vocabulary_i.txt";
            String adjPath = getFilesDir().getPath() + "/adjectives_i.txt";
            String advPath = getFilesDir().getPath() + "/adverbs_i.txt";
            String vbsPath = getFilesDir().getPath() + "/verbs_i.txt";
            String nnsPath = getFilesDir().getPath() + "/nouns_i.txt";
            bot = new Bot(activeMod, vocabPath, adjPath, advPath, vbsPath, nnsPath);

            sendButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    final String editTextMessage = editText.getText().toString().trim();

                    if(!editTextMessage.equals(""))
                    {
                        ChatMessage message = new ChatMessage(messageId, editTextMessage, activeMod);

                        try
                        {
                            writeChatMessageToFile(message, openFileOutput("conversation_file.txt", MODE_APPEND));
                            messageId++;

                            messagesAdapter.add(message);

                            ChatMessage botAnswer = bot.answer(message);

                            writeChatMessageToFile(botAnswer, openFileOutput("conversation_file.txt", MODE_APPEND));
                            messageId++;

                            messagesAdapter.add(botAnswer);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                        messagesList.setSelection(messagesAdapter.getCount() - 1);

                        editText.setText("");
                    }
                }
            });

            messagesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if(position % 2 == 1)
                    {
                        currentClickTime = System.currentTimeMillis();

                        if(currentClickTime - lastClickTime <= DOUBLE_CLICK_INTERVAL)
                        {
                            ChatMessage verses = messagesAdapter.getItem(position);

                            try
                            {
                                updateConversationFile(verses.getId());

                                if (!verses.getIsSaved())
                                {
                                    verses.setIsSaved(true);
                                    messagesAdapter.notifyDataSetChanged();

                                    if (verses.getActiveMod() == '0')
                                    {
                                        writeChatMessageToFile(verses, openFileOutput("eminescu.txt", MODE_APPEND));
                                        savedVersesEminescu.add(verses);
                                    }
                                    else if (verses.getActiveMod() == '1')
                                    {
                                        writeChatMessageToFile(verses, openFileOutput("bacovia.txt", MODE_APPEND));
                                        savedVersesBacovia.add(verses);
                                    }
                                    else
                                    {
                                        writeChatMessageToFile(verses, openFileOutput("stanescu.txt", MODE_APPEND));
                                        savedVersesStanescu.add(verses);
                                    }

                                    Toast.makeText(getApplicationContext(), "Versurile au fost salvate!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    verses.setIsSaved(false);
                                    messagesAdapter.notifyDataSetChanged();

                                    if (verses.getActiveMod() == '0')
                                    {
                                        removeVersesFromArray(verses, savedVersesEminescu);
                                        updateFileWithSavedVerses(openFileOutput("eminescu.txt", Context.MODE_PRIVATE), savedVersesEminescu);
                                    }
                                    else if (verses.getActiveMod() == '1')
                                    {
                                        removeVersesFromArray(verses, savedVersesBacovia);
                                        updateFileWithSavedVerses(openFileOutput("bacovia.txt", Context.MODE_PRIVATE), savedVersesBacovia);
                                    }
                                    else
                                    {
                                        removeVersesFromArray(verses, savedVersesStanescu);
                                        updateFileWithSavedVerses(openFileOutput("stanescu.txt", Context.MODE_PRIVATE), savedVersesStanescu);
                                    }

                                    Toast.makeText(getApplicationContext(), "Versurile au fost șterse!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch(IOException e)
                            {
                                e.printStackTrace();
                            }
                        }

                        lastClickTime = currentClickTime;
                    }
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConversationActivity.this, R.style.MyDialogTheme);

                    builder.setCancelable(false);

                    builder.setMessage("Doriți să ștergeți toată conversația?")
                            .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id)
                                {
                                    int adapterLength = messagesAdapter.getCount();

                                    for(int i = adapterLength - 1; i >= 0; i--)
                                    {
                                        ChatMessage item = messagesAdapter.getItem(i);
                                        messagesAdapter.remove(item);
                                    }

                                    messagesAdapter.notifyDataSetChanged();
                                    messagesList.setAdapter(messagesAdapter);

                                    try
                                    {
                                        File convFile = new File(getFilesDir().getPath() + "/conversation_file.txt");
                                        PrintWriter pw = new PrintWriter(convFile);
                                        pw.print("");
                                    }
                                    catch(FileNotFoundException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

                    builder.show();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        messagesList.setSelection(messagesAdapter.getCount() - 1);
    }

    private ArrayList<ChatMessage> getConversationFromInternalStorage()
    {
        ArrayList<ChatMessage> conversation = new ArrayList<>();

        try
        {
            String convPath = getFilesDir().getPath() + "/conversation_file.txt";
            BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(convPath), "UTF-8"));

            int c = fis.read();

            while(c != -1)
            {
                // get the message id

                StringBuilder idString = new StringBuilder("");

                while(((char) c) != '_')
                {
                    idString.append((char) c);
                    c = fis.read();
                }

                int id = Integer.parseInt(idString.toString());

                // skip the underscore
                c = fis.read();


                // get the mode of the message
                char mode = (char) c;

                // skip the underscore
                c = fis.read();
                c = fis.read();

                // get the value of isSaved
                char isSaved = (char) c;

                // skip the underscore
                c = fis.read();
                c = fis.read();

                // get the number of end message marks

                StringBuilder numberOfEndMessageMarksString = new StringBuilder("");

                while(((char) c) != '_')
                {
                    numberOfEndMessageMarksString.append((char) c);
                    c = fis.read();
                }

                int numberOfEndMessageMarks = Integer.parseInt(numberOfEndMessageMarksString.toString());

                // skip the underscore
                c = fis.read();


                // get the text of message

                StringBuilder messageString = new StringBuilder("");

                while(numberOfEndMessageMarks > -1)
                {
                    if((char) c == END_MESSAGE_MARK)
                    {
                        numberOfEndMessageMarks--;
                    }

                    messageString.append((char) c);

                    c = fis.read();
                }

                messageString.deleteCharAt(messageString.length() - 1);

                ChatMessage message = new ChatMessage(id, messageString.toString(), mode);
                message.setIsSaved(charToBoolean(isSaved));

                conversation.add(message);

                messageId = message.getId();
            }

            messageId++;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return conversation;
        }

        return conversation;
    }

    private char getActiveMod() throws IOException
    {
        File modeFile = new File(getFilesDir().getPath() + "/mode_file.txt");

        if(modeFile.length() != 0)
        {
            FileInputStream fileInputStream = openFileInput("mode_file.txt");
            char c = (char) fileInputStream.read();

            return c;
        }

        return '3';
    }

    private void writeChatMessageToFile(ChatMessage message, FileOutputStream fileOutputStream)
    {
        try
        {
            StringBuilder fileItem = new StringBuilder("");

            fileItem.append(String.valueOf(message.getId()));
            fileItem.append('_');
            fileItem.append(message.getActiveMod());
            fileItem.append('_');
            fileItem.append(booleanToChar(message.getIsSaved()));
            fileItem.append('_');
            fileItem.append(String.valueOf(message.getNumberOfEndMessageMarks()));
            fileItem.append('_');
            fileItem.append(message.getMessage());
            fileItem.append(END_MESSAGE_MARK);

            fileOutputStream.write(fileItem.toString().getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<ChatMessage> getSavedVersesFromFile(String filepath) throws IOException
    {
        ArrayList<ChatMessage> versesArray = new ArrayList<>();

        try
        {
            BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));

            int c = fis.read();

            while(c != -1)
            {
                // get the message id

                StringBuilder messageIdString = new StringBuilder("");

                while(((char) c) != '_')
                {
                    messageIdString.append((char) c);
                    c = fis.read();
                }

                int id = Integer.parseInt(messageIdString.toString());

                // skip the underscore
                c = fis.read();

                // get the mode of the message
                char mode = (char) c;

                // skip the underscore
                c = fis.read();
                c = fis.read();


                // skip the value of isSaved
                c = fis.read();
                c = fis.read();


                // get the number of end message marks

                StringBuilder numberOfEndMessageMarksString = new StringBuilder("");

                while(((char) c) != '_')
                {
                    numberOfEndMessageMarksString.append((char) c);
                    c = fis.read();
                }

                int numberOfEndMessageMarks = Integer.parseInt(numberOfEndMessageMarksString.toString());

                // skip the underscore
                c = fis.read();


                // get the text of the verses

                StringBuilder versesString = new StringBuilder("");

                while(numberOfEndMessageMarks > -1)
                {
                    if((char) c == END_MESSAGE_MARK)
                    {
                        numberOfEndMessageMarks--;
                    }

                    versesString.append((char) c);

                    c = fis.read();
                }

                versesString.deleteCharAt(versesString.length() - 1);

                ChatMessage verses = new ChatMessage(id, versesString.toString(), mode);
                verses.setIsSaved(true);

                versesArray.add(verses);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return versesArray;
    }

    private void updateFileWithSavedVerses(FileOutputStream fos, ArrayList<ChatMessage> savedVersesArray)
    {
        int arrayLen = savedVersesArray.size();

        for(int i = 0; i < arrayLen; i++)
        {
            writeChatMessageToFile(savedVersesArray.get(i), fos);
        }
    }

    private void updateConversationFile(int id)
    {
        try
        {
            String filepath = getFilesDir().getPath() + "/conversation_file.txt";
            BufferedReader fis = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "utf-8"));

            int c = fis.read();
            StringBuilder fileContent = new StringBuilder("");

            while(c != -1)
            {
                StringBuilder idString = new StringBuilder("");

                while((char) c != '_')
                {
                    idString.append((char) c);
                    c = fis.read();
                }

                fileContent.append(idString.toString());
                fileContent.append((char) c);

                int idFromString = Integer.parseInt(idString.toString());

                fileContent.append((char)fis.read());
                fileContent.append((char)fis.read());

                c = fis.read();

                if(id == idFromString)
                {
                    char oppositeChar = getTheOpposite((char) c);
                    fileContent.append(oppositeChar);
                }
                else
                {
                    fileContent.append((char) c);
                }

                fileContent.append((char)fis.read());
                c = fis.read();

                StringBuilder numberOfEndMessageMarksString = new StringBuilder("");

                while((char) c != '_')
                {
                    numberOfEndMessageMarksString.append((char) c);
                    c = fis.read();
                }

                fileContent.append(numberOfEndMessageMarksString);
                fileContent.append((char) c);

                int numberOfEndMessageMarksInt = Integer.parseInt(numberOfEndMessageMarksString.toString());

                // skip the underscore
                c = fis.read();

                while(numberOfEndMessageMarksInt > -1)
                {
                    if((char) c == END_MESSAGE_MARK)
                    {
                        numberOfEndMessageMarksInt--;
                    }

                    fileContent.append((char) c);
                    c = fis.read();
                }

                Log.i("tag", "ceva");
            }

            fis.close();

            FileOutputStream fos = openFileOutput("conversation_file.txt", Context.MODE_PRIVATE);
            fos.write(fileContent.toString().getBytes());
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void removeVersesFromArray(ChatMessage message, ArrayList<ChatMessage> arrayList)
    {
        int len = arrayList.size();

        for(int i = 0; i < len; i++)
        {
            if(message.getId() == arrayList.get(i).getId())
            {
                arrayList.remove(arrayList.get(i));
                break;
            }
        }
    }

    private char booleanToChar(boolean value)
    {
        if(value)return '1';
        else return '0';
    }

    private boolean charToBoolean(char value)
    {
        if(value == '1') return true;
        else return false;
    }

    private char getTheOpposite(char value)
    {
        return (value == '0') ? '1' : '0';
    }
}
