package listadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatui.ms.chatui.R;

import java.util.List;

import Entities.ChatListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import su.levenetc.android.badgeview.BadgeView;

public class CustomContentChatAdapter extends RecyclerView.Adapter<CustomContentChatAdapter.MyViewHolder> {
    List<ChatListItem> chatListItem;
    Context context;
      private static LayoutInflater inflater=null;

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
    public CustomContentChatAdapter(Context context, List<ChatListItem> chatListItem) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.chatListItem=chatListItem;

    }

    @Override
    public CustomContentChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chat_message, parent, false);

        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(CustomContentChatAdapter.MyViewHolder holder, int position) {
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
    }

    @Override
    public int getItemCount() {
       return  chatListItem.size();
    }


    }

