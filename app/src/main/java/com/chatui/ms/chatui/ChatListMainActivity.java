package com.chatui.ms.chatui;


import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import listadapter.ChatAdapter;
import utilities.ChatMessage;

public class ChatListMainActivity extends AppCompatActivity {

    private TextView title;
    private TextView sub_title;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton;
    ImageView submitButton;
    View rootView;
    EmojIconActions emojIcon;
    private ChatAdapter adapter;
    private ListView messagesContainer;
    private ArrayList<ChatMessage> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_main);
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
//        ImageView view = (ImageView)findViewById(android.R.id.home);
//        view.setPadding(10, 0, 0, 0);
        title =(TextView)tb.findViewById(R.id.toolbar_title);
        sub_title =(TextView)tb.findViewById(R.id.toolbar_subtitle);
        title.setText("Funlife Returns new group \uD83D\uDE01 \uD83D\uDE01");
        sub_title.setText("Hoping the life of ...");
        setSupportActionBar(tb);
        tb.setTitle("DevuDevuDevuD evuDevuDevuDe vuDevu Devu De vu  ");
        tb.inflateMenu(R.menu.menu_main);
        LinearLayout rv=(LinearLayout)tb.findViewById(R.id.up_navigation_custom);
        LinearLayout profile_nav=(LinearLayout)tb.findViewById(R.id.profile_nav);

        // Get the ActionBar here to configure the way it behaves.
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button

        rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        profile_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChatListMainActivity.this,ProfileScreenMainActivity.class));
            }
        });

        messagesContainer = (ListView) findViewById(R.id.messagesContainer);

        rootView = findViewById(R.id.root_view);
        emojiButton = (ImageView) findViewById(R.id.kayboardswitch);
        submitButton = (ImageView) findViewById(R.id.sendButton);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojiEditText);
        emojIcon=new EmojIconActions(this,rootView,emojiconEditText,emojiButton);
        emojIcon.ShowEmojIcon();
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard","open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard","close");
            }
        });



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newText = emojiconEditText.getText().toString();
                String messageText = newText;
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                emojiconEditText.setText("");

                displayMessage(chatMessage);
            }
        });
        loadDummyHistory();
    }


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ChatListMainActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}