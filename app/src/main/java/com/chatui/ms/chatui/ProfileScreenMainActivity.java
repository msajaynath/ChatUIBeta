package com.chatui.ms.chatui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import Seeds.SeedClass;
import listadapter.CustomChatAdapter;
import listadapter.CustomParticipantAdapter;
import utilities.HeaderView;
import utilities.SimpleDividerItemDecoration;

public class ProfileScreenMainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    protected HeaderView toolbarHeaderView;

    protected HeaderView floatHeaderView;

    protected AppBarLayout appBarLayout;

    protected Toolbar toolbar;

    protected RecyclerView participantsList;

private boolean isHideToolbarView = false;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen_main);
        toolbarHeaderView=(HeaderView)findViewById(R.id.toolbar_header_view);
        floatHeaderView=(HeaderView)findViewById(R.id.float_header_view);
        appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        toolbar=(Toolbar)findViewById(R.id.toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    appBarLayout.addOnOffsetChangedListener(this);

    toolbarHeaderView.bindTo("Funlife Returns: \uD83D\uDE01\uD83D\uDE01", "Hoping the life of ...");
    floatHeaderView.bindTo("Funlife Returns: \uD83D\uDE01\uD83D\uDE01", "Hoping the life of ...");

    mLinearLayoutManager = new LinearLayoutManager(this);
    mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        participantsList=(RecyclerView) findViewById(R.id.profileRecycleView);
    //   chatListview.setHasFixedSize(true);
        participantsList.setLayoutManager(mLinearLayoutManager);
        participantsList.setAdapter(new CustomParticipantAdapter(getApplicationContext(), new SeedClass().seedParticipantListMain()));
        participantsList.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
//        participantsList.addOnItemTouchListener(new MainActivity.RecyclerTouchListener(getApplicationContext(), participantsList, new MainActivity.ClickListener() {
//        @Override
//        public void onClick(View view, int position) {
//           // getActivity().startActivity(new Intent(getActivity(),ChatListMainActivity.class));
//
//            // Toast.makeText(getActivity().getApplicationContext(), " is selected!", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onLongClick(View view, int position) {
//
//        }
//    }));
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;

        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
