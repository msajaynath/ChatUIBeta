package com.chatui.ms.chatui;


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpicker.ImagePickerActivity;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import Global.GlobalChats;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import listadapter.ChatAdapter;
import utilities.BitmapDecoder;
import utilities.ChatMessage;
import utilities.OnItemChatClickListener;

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
    private AlertDialog b;
    private ActionMode mActionMode;
    private boolean paste=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list_main);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        Intent intent=getIntent();
        if(intent.hasExtra("pasteChat"))
        paste=true;

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
//        mRevealView = (LinearLayout) findViewById(R.id.reveal_items);
//        mRevealView.setVisibility(View.GONE);
//        hidden = true;

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
        mLinearLayoutManager.setStackFromEnd(true);
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

        if(paste)
            appendCopiedChats();
    }

    private void appendCopiedChats() {
        List <ChatMessage> chatsSave=((GlobalChats)getApplication()).getSavedChats();
        for(ChatMessage chat:chatsSave)
        {
            chat.setMe(true);
            displayMessage(chat);
        }

    }


    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
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

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    private void getImages() {

        Intent intent  = new Intent(this, ImagePickerActivity.class);
        startActivityForResult(intent,INTENT_REQUEST_GET_IMAGES);

    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {

            ArrayList<Uri>  image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            for(Uri uri:image_uris)
            {
                try {
                    Uri newUri=Uri.fromFile(new File(uri.getPath()));

                   // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), newUri);

                   Bitmap bitmap = BitmapDecoder.getThumbnail(newUri, getApplicationContext());
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(122);//dummy
                    chatMessage.imageAttached = true;//dummy
                    chatMessage.setImage(bitmap);
                   // chatMessage.setMessage(uri.getPath());
                    chatMessage.setDate((String) DateFormat.format(delegate, Calendar.getInstance().getTime()));
                    chatMessage.setMe(true);
                    chatMessage.sameSender = true;
                    emojiconEditText.setText("");

                    displayMessage(chatMessage);
                }
                catch (Exception e)
                {
Log.d("devuu",e.toString());
                }

            }


           // Log.d("devuu",image_uris.get(0).getPath()) ;
            //do something
        }
        else if (requestCode == 1 && resuleCode == RESULT_OK) {
              String filePath = intent.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setId(122);//dummy
            chatMessage.fileAttached = true;//dummy
            chatMessage.setFilePath(filePath);
            // chatMessage.setMessage(uri.getPath());
            chatMessage.setDate((String) DateFormat.format(delegate, Calendar.getInstance().getTime()));
            chatMessage.setMe(true);
            chatMessage.sameSender = true;
            emojiconEditText.setText("");

            displayMessage(chatMessage);
            // Do anything with file
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.attach:

              //  getImages();

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.ripple_attach_menu, null);
                
                dialogBuilder.setView(dialogView);

                 b = dialogBuilder.create();

                b.show();
                dialogView.findViewById(R.id.attachImage).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getImages();
                        b.cancel();
                    }
                });

                dialogView.findViewById(R.id.attachDocs).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new MaterialFilePicker()
                                .withActivity(ChatListMainActivity.this)
                                .withRequestCode(1)
                                .withFilter(Pattern.compile(".*\\.txt$|.*\\.pdf|.*\\.docx")) // Filtering files and directories by file name using regexp
                                .withFilterDirectories(false) // Set directories filterable (false by default)
                                .withHiddenFiles(true) // Show hidden files and folders
                                .start();
                        b.cancel();
                    }
                });

                return true;

        }
        return true;

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


        adapter = new ChatAdapter(ChatListMainActivity.this, new ArrayList<ChatMessage>(), new OnItemChatClickListener() {
            @Override
            public void onLongClick(ChatMessage item,int position) {
                mActionMode = ChatListMainActivity.this.startSupportActionMode(new ActionBarCallBacks());
                myToggleSelection(position);
            }

            @Override
            public void onClick(ChatMessage item,int position) {
             if(mActionMode!=null)
             {
                 myToggleSelection(position);

             }
            }

            @Override
            public void onImageClick(ChatMessage item,int position) {
                if(mActionMode!=null)
                {
                    myToggleSelection(position);

                }
                else {
                    //Toast.makeText(getApplicationContext(),"devu i heree",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChatListMainActivity.this, ProfileImageDetailActivity.class);
                    Bitmap b = item.getImage(); // your bitmap
                    ByteArrayOutputStream bs = new ByteArrayOutputStream();
                    b.compress(Bitmap.CompressFormat.PNG, 50, bs);
                    intent.putExtra("Image", bs.toByteArray());
                    //  intent.putExtra("Image",item.getImage());
                    startActivity(intent);
                }
            }
        });
                messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    private void myToggleSelection(int idx) {
        adapter.toggleSelection(idx);
//        String title = getString(
//                R.string.selected_count,
//                adapter.getSelectedItemCount());
//        actionMode.setTitle(title);
    }

    class ActionBarCallBacks implements  android.support.v7.view.ActionMode.Callback {

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // TODO Auto-generated method stub
            switch (item.getItemId())
            {

                case R.id.item_delete:
                    List<Integer> selectedItemPositions =
                            adapter.getSelectedItems();
                    for (int i = selectedItemPositions.size()-1; i >= 0; i--) {
                    adapter.remove(selectedItemPositions.get(i));
                }

                    mode.finish();
                break;
                case R.id.item_copy:
                    List <ChatMessage> chats=adapter.getAllChats();
                    String copyText="";
                    for(ChatMessage chat:chats)
                    {
                        copyText+=chat.getMessage()+"\n";
                    }
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copy", copyText);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(ChatListMainActivity.this,"Copied to clipboard!",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.item_forward:
                    List <ChatMessage> chatsSave=adapter.getAllChats();
                    GlobalChats globalChats =  (GlobalChats) getApplication();
                    globalChats.setSavedChats(chatsSave);
                    Intent intent =new Intent(ChatListMainActivity.this, MainActivity.class);
                    intent.putExtra("pasteChat",true);
                    startActivity(intent);
                    Toast.makeText(ChatListMainActivity.this,"Please select  groups/chat for forwarding!",Toast.LENGTH_SHORT).show();
                    mActionMode.finish();
                    break;

                default:
                    return false;
            }
            return true;

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            mode.getMenuInflater().inflate(R.menu.context_chat_menu, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mActionMode = null;
            adapter.clearSelections();
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub

            return false;
        }


    }


}