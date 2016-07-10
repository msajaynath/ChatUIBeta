package listadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.chatui.ms.chatui.R;
import java.util.List;
import Entities.ContactItem;
import utilities.OnItemContactClickListener;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private final List<ContactItem> contactItems;
    private Context context;
    private final OnItemContactClickListener listener;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView name,phone;
    public ImageView profileImage,cancel;

    public MyViewHolder(View view) {
        super(view);
        name = (TextView) view.findViewById(R.id.header);
        phone = (TextView) view.findViewById(R.id.contentunder);
        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        cancel = (ImageView) view.findViewById(R.id.cancel);

    }


}

    public ContactAdapter(Context context, List<ContactItem> contactItems, OnItemContactClickListener listener) {
        // TODO CustomChatAdapter-generated constructor stub
        this.context=context;
        this.contactItems=contactItems;
        this.listener = listener;


    }
    public int getItemCount() {
        return  contactItems.size();
    }

    @Override
    public ContactAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);

        return new ContactAdapter.MyViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ContactItem contact = contactItems.get(position);
        //to simulate whether it me or other sender


        holder.name.setText(contact.name);
        holder.phone.setText(contact.mobileNumber);
        holder.profileImage.setImageResource(contact.image);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onClick(contact);
            }
        });


    }




    public void add(ContactItem message) {
        if(!contactItems.contains(message))
        contactItems.add(message);
    }

    public void remove(ContactItem message) {

        contactItems.remove(message);
    }
    public void add(List<ContactItem> messages) {
        contactItems.addAll(messages);
    }



}