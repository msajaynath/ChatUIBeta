package listadapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatui.ms.chatui.MainActivity;
import com.chatui.ms.chatui.R;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Entities.ChatListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import su.levenetc.android.badgeview.BadgeView;
import utilities.OnItemClickListener;

public class CustomChatAdapter  extends RecyclerView.Adapter<CustomChatAdapter.MyViewHolder> {
    private List<ChatListItem> chatListItem;
    Context context;
      private static LayoutInflater inflater=null;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView headerText,contentUnder,headerRight;
        CircleImageView prfPic;
        BadgeView unreadChatBadge;

        public MyViewHolder(View view) {
            super(view);
            headerRight=(TextView) view.findViewById(R.id.headerright);
            contentUnder=(TextView) view.findViewById(R.id.contentunder);
            headerText=(TextView) view.findViewById(R.id.header);
            prfPic=(CircleImageView) view.findViewById(R.id.profile_image);
            unreadChatBadge=(BadgeView) view.findViewById(R.id.unreadChatBadge);
        }
    }
    public CustomChatAdapter(Context context, List<ChatListItem> chatListItems,OnItemClickListener listener) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        chatListItem = new ArrayList<>(chatListItems);
        this.listener = listener;

    }

    @Override
    public CustomChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item, parent, false);

        return new MyViewHolder(itemView);    }

    public static String filter="";
    @Override
    public void onBindViewHolder(CustomChatAdapter.MyViewHolder holder, int position) {
      //  String fullText=;

        // highlight search text

            final int pos=position;
            holder.headerText.setText(chatListItem.get(position).chatName);


        holder.headerRight.setText(chatListItem.get(position).lastsendTimeText);
        holder.contentUnder.setText(chatListItem.get(position).lastChatMessage);
        holder.prfPic.setImageResource(chatListItem.get(position).profilePic);
        if(chatListItem.get(position).unReadChatCount>0)
        {

            holder.unreadChatBadge.setValue(chatListItem.get(position).unReadChatCount);
            holder.unreadChatBadge.setVisibility(View.VISIBLE);
            holder.headerRight.setTextColor(ContextCompat.getColor(context, R.color.greenUNREAD));
        }
        holder.prfPic.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onProfileItemClick(chatListItem.get(pos));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onListItemClick(chatListItem.get(pos));
            }
        });

       // AlertDialog alertDialog = new AlertDialog.Builder(context.getApplicationContext());


    }

    @Override
    public int getItemCount() {
       return  chatListItem.size();
    }

    
    ///filterss
    public void animateTo(List<ChatListItem> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<ChatListItem> newModels) {
        for (int i = chatListItem.size() - 1; i >= 0; i--) {
            final ChatListItem model = chatListItem.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<ChatListItem> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final ChatListItem model = newModels.get(i);
            if (!chatListItem.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<ChatListItem> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final ChatListItem model = newModels.get(toPosition);
            final int fromPosition = chatListItem.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public ChatListItem removeItem(int position) {
        final ChatListItem model = chatListItem.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, ChatListItem model) {
        chatListItem.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final ChatListItem model = chatListItem.remove(fromPosition);
        chatListItem.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
    
    

    }

