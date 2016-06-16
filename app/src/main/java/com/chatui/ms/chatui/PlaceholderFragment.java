package com.chatui.ms.chatui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import Entities.ChatListItem;
import Seeds.SeedClass;
import listadapter.CustomChatAdapter;
import utilities.FilterAdapter;
import utilities.OnItemClickListener;
import utilities.SimpleDividerItemDecoration;

public  class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private RecyclerView chatListview;
        private LinearLayoutManager mLinearLayoutManager;
        private int scrollD;
        private Toolbar toolbar;
        private List<ChatListItem> chatMainList;
        private CustomChatAdapter chatAdapter;
        private TextView emptychatListview;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

            mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            chatListview = (RecyclerView) rootView.findViewById(R.id.chatlistView);
            emptychatListview = (TextView) rootView.findViewById(R.id.emptyListView);
            //   chatListview.setHasFixedSize(true);

            //     TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //   textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chatListview.setLayoutManager(mLinearLayoutManager);
            chatMainList=new SeedClass().seedChatListMain();
            chatAdapter=new CustomChatAdapter(getActivity().getApplicationContext(), chatMainList, new OnItemClickListener() {
                @Override
                public void onProfileItemClick(ChatListItem item) {
                    Toast.makeText(getContext(),item.chatName,Toast.LENGTH_LONG).show();
                    LoadProfileImageDialog(item);
                }

                @Override
                public void onListItemClick(ChatListItem item) {
                   // Toast.makeText(getContext(),"devuu   herer",Toast.LENGTH_LONG).show();
                   getActivity().startActivity(new Intent(getActivity(), ChatListMainActivity.class));

                }
            });
            chatListview.setAdapter(chatAdapter);
            chatListview.addItemDecoration(new SimpleDividerItemDecoration(getActivity().getApplicationContext()));
//            chatListview.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), chatListview, new ClickListener() {
//                @Override
//                public void onClick(View view, int position) {
//                    getActivity().startActivity(new Intent(getActivity(), ChatListMainActivity.class));
//                    Log.d("View ID:",view.toString()+"");
//
//                    // Toast.makeText(getActivity().getApplicationContext(), " is selected!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onLongClick(View view, int position) {
//
//                }
//            }));
            chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkAdapterIsEmpty();
                }
            });
            checkAdapterIsEmpty();
        }

    private void LoadProfileImageDialog(ChatListItem item) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        ImageView profilePic= (ImageView)dialogView.findViewById(R.id.profileImage);
        TextView upperTextView= (TextView)dialogView.findViewById(R.id.uppertext);
        upperTextView.setText(item.chatName);
      //  profilePic.setSystemUiVisibility( View.SYSTEM_UI_FLAG_HIDE_NAVIGATION );
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), ProfileImageDetailActivity.class));

            }
        });
        profilePic.setImageResource(item.profilePic);
        dialogBuilder.setView(dialogView);

        AlertDialog b = dialogBuilder.create();
        b.show();

    }

    private void checkAdapterIsEmpty () {
            if (chatAdapter.getItemCount() == 0) {
                emptychatListview.setVisibility(View.VISIBLE);
            } else {
                emptychatListview.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
            inflater.inflate(R.menu.menu_main, menu);

            // Associate searchable configuration with the SearchView
//            SearchManager searchManager =
//                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.search).getActionView();
           // searchView.setSearchableInfo(
             //       searchManager.getSearchableInfo(getComponentName()));


            SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextChange(String newText)
                {
                    // this is your adapter that will be filtered
                    // myAdapter.getFilter().filter(newText);
                    System.out.println("on query ajay: "+newText+" size="+chatMainList.size());

                    //if(newText.length()>0)
                    {

                        final List<ChatListItem> filteredModelList = new FilterAdapter().filter(chatMainList, newText);
                        chatAdapter.filter=newText;
                        chatAdapter.animateTo(filteredModelList);
                        chatListview.scrollToPosition(0);
                        checkAdapterIsEmpty();

                    }

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    // this is your adapter that will be filtered
                    //myAdapter.getFilter().filter(query);
                    System.out.println("on query submit: "+query);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(textChangeListener);


           // return true;
        }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
    }