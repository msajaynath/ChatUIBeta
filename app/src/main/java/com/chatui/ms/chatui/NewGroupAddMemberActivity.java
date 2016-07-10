package com.chatui.ms.chatui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.ChatListItem;
import Entities.ContactItem;
import Seeds.SeedClass;
import listadapter.ContactAdapter;
import listadapter.CustomChatAdapter;
import utilities.CustomAutoCompleteTextView;
import utilities.OnItemClickListener;
import utilities.OnItemContactClickListener;

public class NewGroupAddMemberActivity extends AppCompatActivity {
    private LinearLayout upHome,createGroup;
    public List<ContactItem> contacts = new ArrayList<ContactItem>();
    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
    String[] from = { "image","txt"};
    private RecyclerView contactsAdded;
    // Ids of views in listview_layout
    int[] to = { R.id.image,R.id.name};
    private List<ContactItem> contactItems;
    private ContactAdapter contactAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private CustomAutoCompleteTextView autoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group_add_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        upHome = (LinearLayout) findViewById(R.id.upHome);
        createGroup = (LinearLayout) findViewById(R.id.createButton);
        contactsAdded = (RecyclerView) findViewById(R.id.contactAdded);
        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        contactsAdded.setLayoutManager(mLinearLayoutManager);

        contacts= new SeedClass().seedContacts();
        contactItems=new ArrayList<ContactItem>();
        upHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        for(int i=0;i<contacts.size();i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", contacts.get(i).name);
            hm.put("image", Integer.toString(contacts.get(i).image) );
            hm.put("phone", contacts.get(i).mobileNumber);
            hm.put("ID", Integer.toString(contacts.get(i).ID));
            aList.add(hm);
        }
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.autocomplete_list_item, from, to);
        autoComplete = ( CustomAutoCompleteTextView) findViewById(R.id.autocomplete);
        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Codee to create group",Toast.LENGTH_LONG).show();       }
        });

        contactAdapter=new ContactAdapter(getApplicationContext(), contactItems, new OnItemContactClickListener() {
            @Override
            public void onLongClick(ContactItem item) {

            }

            @Override
            public void onClick(ContactItem item) {
                contactAdapter.remove(item);
                contactAdapter.notifyDataSetChanged();

            }
        });

       contactsAdded.setAdapter(contactAdapter);
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hm = (HashMap<String, String>) parent.getAdapter().getItem(position);
                for(int j=0;j< contacts.size();j++){
                    ContactItem d=contacts.get(j);
                    if(d.ID ==Integer.parseInt(hm.get("ID")))
                    {

                        contactAdapter.add(d);
                        contactAdapter.notifyDataSetChanged();
                        autoComplete.setText("");
                    }
                    //something here
                }


                /** Getting a reference to the TextView of the layout file activity_main to set Currency */
                //TextView tvCurrency = (TextView) findViewById(R.id.tv_currency) ;
                Toast.makeText(getApplicationContext(),hm.get("ID")+"",Toast.LENGTH_SHORT).show();
                /** Getting currency from the HashMap and setting it to the textview */
                //tvCurrency.setText("Currency : " + hm.get("cur"));

            }
        });
        autoComplete.setAdapter(adapter);

    }

}
