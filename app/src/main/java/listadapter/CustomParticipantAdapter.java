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
import Entities.ParticipantListItem;
import de.hdodenhof.circleimageview.CircleImageView;
import su.levenetc.android.badgeview.BadgeView;

public class CustomParticipantAdapter extends RecyclerView.Adapter<CustomParticipantAdapter.MyViewHolder> {
    List<ParticipantListItem> chatListItem;
    Context context;
      private static LayoutInflater inflater=null;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView headerText,contentUnder;
        CircleImageView prfPic;
        public MyViewHolder(View view) {
            super(view);
            contentUnder=(TextView) view.findViewById(R.id.contentunder);
            headerText=(TextView) view.findViewById(R.id.header);
            prfPic=(CircleImageView) view.findViewById(R.id.profile_image);
        }
    }
    public CustomParticipantAdapter(Context context, List<ParticipantListItem> chatListItem) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.chatListItem=chatListItem;

    }

    @Override
    public CustomParticipantAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_list_item, parent, false);

        return new MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(CustomParticipantAdapter.MyViewHolder holder, int position) {
        holder.headerText.setText(chatListItem.get(position).name);
        holder.contentUnder.setText(chatListItem.get(position).status);
        holder.prfPic.setImageResource(chatListItem.get(position).pic);

    }

    @Override
    public int getItemCount() {
       return  chatListItem.size();
    }


    }

