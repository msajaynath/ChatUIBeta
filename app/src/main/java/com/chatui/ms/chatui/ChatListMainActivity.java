package com.chatui.ms.chatui;


import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorListenerAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import listadapter.ChatAdapter;
import utilities.ChatMessage;

public class ChatListMainActivity extends AppCompatActivity {
    String delegate = "hh:mm aaa";

    private TextView title;
    private TextView sub_title;
    EmojiconEditText emojiconEditText;
    ImageView emojiButton;
    ImageView submitButton;
    View rootView;
    EmojIconActions emojIcon;
    private ChatAdapter adapter;
    private RecyclerView messagesContainer;
    private ArrayList<ChatMessage> chatHistory;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayout mRevealView;
    private boolean hidden;

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
        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
        mRevealView.setVisibility(View.GONE);
        hidden = true;

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
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //   chatListview.setHasFixedSize(true);
        messagesContainer = (RecyclerView) findViewById(R.id.messagesContainer);
        messagesContainer.setLayoutManager(mLinearLayoutManager);

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

//        emojiconEditText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emojIcon.ShowEmojIcon();
//
//            }
//        });
//        emojiconEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                emojIcon.ShowEmojIcon();
//
//            }
//        });


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
                chatMessage.setDate((String) DateFormat.format(delegate, Calendar.getInstance().getTime()));
                chatMessage.setMe(true);
                chatMessage.sameSender=true;
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


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        new MenuInflater(this).inflate(R.menu.menu_chat_main, menu);
//        super.onCreateOptionsMenu(menu);
//    }
private void hideRevealView() {
    if (mRevealView.getVisibility() == View.VISIBLE) {
        mRevealView.setVisibility(View.GONE);
        hidden = true;
    }
}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_chat_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        mRevealView.setVisibility(View.GONE);
        hidden = true;    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.attach:

                int cx = (mRevealView.getLeft() + mRevealView.getRight());
                int cy = mRevealView.getTop();
                int radius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

                //Below Android LOLIPOP Version
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    SupportAnimator animator =
                            ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, 0, radius);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.setDuration(700);

                    SupportAnimator animator_reverse = animator.reverse();

                    if (hidden) {
                        mRevealView.setVisibility(View.VISIBLE);
                        animator.start();
                        hidden = false;
                    } else {
                        animator_reverse.addListener(new SupportAnimator.AnimatorListener() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {
                                mRevealView.setVisibility(View.INVISIBLE);
                                hidden = true;

                            }

                            @Override
                            public void onAnimationCancel() {

                            }

                            @Override
                            public void onAnimationRepeat() {

                            }
                        });
                        animator_reverse.start();
                    }
                }
                // Android LOLIPOP And ABOVE Version
                else {
                    if (hidden) {
                        Animator anim = android.view.ViewAnimationUtils.
                                createCircularReveal(mRevealView, cx, cy, 0, radius);
                        mRevealView.setVisibility(View.VISIBLE);
                        anim.start();
                        hidden = false;
                    } else {
                        Animator anim = android.view.ViewAnimationUtils.
                                createCircularReveal(mRevealView, cx, cy, radius, 0);
                        anim.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                              //  super.onAnimationEnd(animation);

                                mRevealView.setVisibility(View.INVISIBLE);
                                hidden = true;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
//                        anim.addListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                mRevealView.setVisibility(View.INVISIBLE);
//                                hidden = true;
//                            }
//                        });
                        anim.start();
                    }
                }
                return true;

        }
        return true;

    }
    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        mRevealView.setVisibility(View.GONE);
        hidden = true;
    }

            private void scroll() {
        messagesContainer.scrollToPosition(adapter.getItemCount()-1);

//        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("How r u doing n long tme nah???");
        msg.setDate((String) DateFormat.format(delegate, Calendar.getInstance().getTime()));
        chatHistory.add(msg);


        adapter = new ChatAdapter(ChatListMainActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}