package listadapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatui.ms.chatui.R;

import java.util.List;

import Entities.ChatListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import su.levenetc.android.badgeview.BadgeView;
import utilities.ChatMessage;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private final List<ChatMessage> chatMessages;
    private Context context;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public EmojiconTextView txtMessage;
    public TextView txtInfo;
    public LinearLayout content;
    public LinearLayout contentWithBG;
    public MyViewHolder(View view) {
        super(view);
        txtMessage = (EmojiconTextView) view.findViewById(R.id.txtMessage);
        content = (LinearLayout) view.findViewById(R.id.content);
        contentWithBG = (LinearLayout) view.findViewById(R.id.contentWithBackground);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);

    }


}

    public ChatAdapter(Context context, List<ChatMessage> chatMessages) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.chatMessages=chatMessages;

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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        boolean myMsg = chatMessage.getIsme() ;//Just a dummy check
        //to simulate whether it me or other sender
        setAlignment(holder, myMsg,chatMessage.sameSender);
        holder.txtMessage.setText(chatMessage.getMessage());
        holder.txtInfo.setText(chatMessage.getDate());
    }




    public void add(ChatMessage message) {

        chatMessages.add(message);
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


}