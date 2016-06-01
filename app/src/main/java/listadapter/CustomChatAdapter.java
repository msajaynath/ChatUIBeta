package listadapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
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

import java.util.List;

import Entities.ChatListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import su.levenetc.android.badgeview.BadgeView;

public class CustomChatAdapter extends BaseAdapter{
    List<ChatListItem> chatListItem;
    Context context;
 int [] imageId;
      private static LayoutInflater inflater=null;
    public CustomChatAdapter(Context context, List<ChatListItem> chatListItem) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.chatListItem=chatListItem;
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chatListItem.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {

        TextView headerText,contentUnder,headerRight;
        CircleImageView prfPic;
        BadgeView unreadChatBadge;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;       
             rowView = inflater.inflate(R.layout.chat_list_item, null);
        holder.headerRight=(TextView) rowView.findViewById(R.id.headerright);
        holder.contentUnder=(TextView) rowView.findViewById(R.id.contentunder);
        holder.headerText=(TextView) rowView.findViewById(R.id.header);
        holder.prfPic=(CircleImageView) rowView.findViewById(R.id.profile_image);
        holder.unreadChatBadge=(BadgeView) rowView.findViewById(R.id.unreadChatBadge);
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

        return rowView;
    }

} 