package com.example.alex.rhymebot.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.alex.rhymebot.Adapter.ChatMessage;
import com.example.alex.rhymebot.Adapter.SavedVersesAdapter;
import com.example.alex.rhymebot.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SavedVersesActivity extends AppCompatActivity
{
    private final char END_MESSAGE_MARK = '#';
    private long currentClickTime;
    private long lastClickTime;
    private final long DOUBLE_CLICK_INTERVAL = 250;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_verses);

        char selectedTab = '0';
        lastClickTime = 0;

        try
        {
            selectedTab = getSelectedTab();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            ListView eminescuListView = (ListView) findViewById(R.id.eminescu_listview);
            ListView bacoviaListView = (ListView) findViewById(R.id.bacovia_listview);
            ListView stanescuListView = (ListView) findViewById(R.id.stanescu_listview);

            SavedVersesAdapter eminescuArrayAdapter;
            SavedVersesAdapter bacoviaArrayAdapter;
            SavedVersesAdapter stanescuArrayAdapter;

            eminescuArrayAdapter = getSavedVersesFromFile(getFilesDir().getPath() + "/eminescu.txt");
            bacoviaArrayAdapter = getSavedVersesFromFile(getFilesDir().getPath() + "/bacovia.txt");
            stanescuArrayAdapter = getSavedVersesFromFile(getFilesDir().getPath() + "/stanescu.txt");

            eminescuListView.setAdapter(eminescuArrayAdapter);
            bacoviaListView.setAdapter(bacoviaArrayAdapter);
            stanescuListView.setAdapter(stanescuArrayAdapter);

            setOnListViewItemClickListener("eminescu.txt", eminescuListView, eminescuArrayAdapter);
            setOnListViewItemClickListener("bacovia.txt", bacoviaListView, bacoviaArrayAdapter);
            setOnListViewItemClickListener("stanescu.txt", stanescuListView, stanescuArrayAdapter);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        TabHost tabHost = (TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Tab Eminescu");
        tab1.setContent(R.id.eminescu_tab_layout);
        tab1.setIndicator("Eminescu");

        TabHost.TabSpec tab2 = tabHost.newTabSpec("Tab Bacovia");
        tab2.setContent(R.id.bacovia_tab_layout);
        tab2.setIndicator("Bacovia");

        TabHost.TabSpec tab3 = tabHost.newTabSpec("Tab Stanescu");
        tab3.setContent(R.id.stanescu_tab_layout);
        tab3.setIndicator("Stănescu");

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

        tabHost.setCurrentTab(charToInt(selectedTab));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener()
        {
            @Override
            public void onTabChanged(String tabId)
            {
                if(tabId.equals("Tab Eminescu"))
                {
                    setCurrentTabInFile('0');
                }
                else if(tabId.equals("Tab Bacovia"))
                {
                    setCurrentTabInFile('1');
                }
                else
                {
                    setCurrentTabInFile('2');
                }
            }
        });
    }

    public SavedVersesAdapter getSavedVersesFromFile(String filepath) throws IOException
    {
        SavedVersesAdapter versesAdapter = new SavedVersesAdapter(getApplicationContext());

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

                // get the mode
                char mode = (char) c;

                // skip the underscore
                c = fis.read();


                // get the value of isSaved
                char isSaved = (char) fis.read();
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
                verses.setIsSaved(charToBoolean(isSaved));

                versesAdapter.add(new ChatMessage(id, versesString.toString(), mode));
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return versesAdapter;
    }

    private char getSelectedTab() throws IOException
    {
        FileInputStream fis = openFileInput("selected_tab.txt");

        return (char) fis.read();
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

    private int charToInt(char value)
    {
        switch(value)
        {
            case '0': return 0;
            case '1': return 1;
            case '2': return 2;
            default: return 0;
        }
    }

    private void setCurrentTabInFile(char currentTabInFile)
    {
        try
        {
            FileOutputStream fos = openFileOutput("selected_tab.txt", Context.MODE_PRIVATE);
            fos.write(currentTabInFile);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void setOnListViewItemClickListener(final String filename, final ListView listview, final SavedVersesAdapter itsAdapter)
    {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                currentClickTime = System.currentTimeMillis();

                if(currentClickTime - lastClickTime <= DOUBLE_CLICK_INTERVAL)
                {
                    //removeVersesFromAdapter(itsAdapter.getItem(position), itsAdapter);
                    updateConversationFile(itsAdapter.getItem(position).getId());
                    itsAdapter.remove(itsAdapter.getItem(position));

                    try
                    {
                        updateFileWithServedVerses(openFileOutput(filename, Context.MODE_PRIVATE), itsAdapter);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                    itsAdapter.notifyDataSetChanged();
                    listview.setAdapter(itsAdapter);
                    Toast.makeText(getApplicationContext(), "Versurile au fost șterse!", Toast.LENGTH_SHORT).show();
                }

                lastClickTime = currentClickTime;
            }
        });
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

    private void updateFileWithServedVerses(FileOutputStream fos, SavedVersesAdapter adapter)
    {
        int len = adapter.getCount();

        for(int i = 0; i < len; i++)
        {
            writeChatMessageToFile(adapter.getItem(i), fos);
        }
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

    private void removeVersesFromAdapter(ChatMessage message, SavedVersesAdapter arrayList)
    {
        int len = arrayList.getCount();

        for(int i = 0; i < len; i++)
        {
            if(message.getId() == arrayList.getItem(i).getId())
            {
                arrayList.remove(arrayList.getItem(i));
                break;
            }
        }
    }

    private char getTheOpposite(char value)
    {
        return (value == '0') ? '1' : '0';
    }
}
