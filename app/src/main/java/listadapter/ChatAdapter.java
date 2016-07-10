package listadapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatui.ms.chatui.ProfileImageDetailActivity;
import com.chatui.ms.chatui.R;

import java.util.ArrayList;
import java.util.List;

import Entities.ChatListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import su.levenetc.android.badgeview.BadgeView;
import utilities.ChatMessage;
import utilities.OnItemChatClickListener;
import utilities.OnItemClickListener;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private final List<ChatMessage> chatMessages;
    private Context context;
    private final OnItemChatClickListener listener;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public EmojiconTextView txtMessage;
    public TextView txtInfo,attachedFileName;
    public ImageView attachedImage,attachedFile;
    public LinearLayout content;
    public LinearLayout contentWithBG;

    public MyViewHolder(View view) {
        super(view);
        txtMessage = (EmojiconTextView) view.findViewById(R.id.txtMessage);
        content = (LinearLayout) view.findViewById(R.id.content);
        contentWithBG = (LinearLayout) view.findViewById(R.id.contentWithBackground);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);
        attachedFileName = (TextView) view.findViewById(R.id.attachedFileName);
        attachedImage = (ImageView) view.findViewById(R.id.attachedImage);
        attachedFile = (ImageView) view.findViewById(R.id.attachedFile);

    }


}

    public ChatAdapter(Context context, List<ChatMessage> chatMessages, OnItemChatClickListener listener) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.chatMessages=chatMessages;
        this.listener = listener;

        selectedItems=new SparseBooleanArray();
    }
    public int getItemCount() {
        return  chatMessages.size();
    }

    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_chat_message, parent, false);

        return new ChatAdapter.MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ChatMessage chatMessage = chatMessages.get(position);
        boolean myMsg = chatMessage.getIsme() ;//Just a dummy check
        //to simulate whether it me or other sender
        setAlignment(holder, myMsg,chatMessage.sameSender);
        if(chatMessage.imageAttached)
        {
            holder.txtMessage.setVisibility(View.GONE);
            holder.attachedFile.setVisibility(View.GONE);
            holder.attachedFileName.setVisibility(View.GONE);
            holder.attachedImage.setImageBitmap(chatMessage.getImage());
            holder.attachedImage.setVisibility(View.VISIBLE);
        }
        else if(chatMessage.fileAttached) {

            holder.txtMessage.setVisibility(View.GONE);
            holder.attachedImage.setVisibility(View.GONE);
            holder.attachedFile.setImageResource(R.drawable.file_attached);
            holder.attachedFileName.setText(chatMessage.getFileName());
            holder.attachedFile.setVisibility(View.VISIBLE);
            holder.attachedFileName.setVisibility(View.VISIBLE);
        }

        else
        {
            holder.attachedImage.setVisibility(View.GONE);
            holder.txtMessage.setVisibility(View.VISIBLE);
            holder.attachedFile.setVisibility(View.GONE);
            holder.attachedFileName.setVisibility(View.GONE);

            holder.txtMessage.setText(chatMessage.getMessage());

        }

        holder.txtInfo.setText(chatMessage.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onClick(chatMessage,position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(chatMessage,position);
                return  true;
            }
        });

        holder.attachedImage.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                listener.onImageClick(chatMessage,position);


            }
        });

        holder.attachedImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(chatMessage,position);
                return  true;            }
        });
        holder.itemView.setActivated(selectedItems.get(position, false));



    }




    public void add(ChatMessage message) {

        chatMessages.add(message);
    }

    public List<ChatMessage>  getAllChats() {

        List<ChatMessage> items =
                new ArrayList<ChatMessage>();
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(chatMessages.get(selectedItems.keyAt(i)));
        }
        return items;
    }

    public void remove(ChatMessage message) {

        chatMessages.remove(message);
        notifyDataSetChanged();
    }


    public void remove(int position) {

        chatMessages.remove(position);
        notifyDataSetChanged();

    }
    public void add(List<ChatMessage> messages) {
        chatMessages.addAll(messages);
    }

    private void setAlignment(MyViewHolder holder, boolean isMe,boolean sameSender) {
        if (!isMe) {

                holder.contentWithBG.setBackgroundResource(R.drawable.blue_chat);

            LinearLayout.LayoutParams layoutParams =
            	(LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
            	(RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);
        } else {
            holder.contentWithBG.setBackgroundResource(R.drawable.grey_chat);

            LinearLayout.LayoutParams layoutParams =
            	(LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
            	(RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }


    }


    private SparseBooleanArray selectedItems;

    // …

    public void toggleSelection(int pos) {
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    // …


}