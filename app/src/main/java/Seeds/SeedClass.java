package Seeds;

import com.chatui.ms.chatui.R;

import java.util.ArrayList;
import java.util.List;

import Entities.ChatListItem;
import Entities.ParticipantListItem;

/**
 * Created by MS on 31/05/16.
 */
public  class SeedClass {

    public  List<ChatListItem> chatListItemSeed=new ArrayList<ChatListItem>();
    public  List<ParticipantListItem> participantListItemSeed=new ArrayList<ParticipantListItem>();

    public List<ChatListItem> seedChatListMain()
    {
        chatListItemSeed.add(new ChatListItem(R.drawable.pic1,"Funlife Returns: \uD83D\uDE01\uD83D\uDE01","Hoping the life of ...","11:06 PM",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic2,"AmalRaj kr","Hi \uD83D\uDE4F how are u.","Yesterday",5));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic3,"Bibin C \uD83D\uDE2B\uD83D\uDE2B","Please forward this message ","Yesterday",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic4,"Nijin Jain","Please stop messaging me","Yesterday",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic5,"Devika \uD83D\uDE4E\uD83D\uDE4E","Hope is like light...","5/30/16",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic6,"Parvathy","Contact has been ","5/30/16",10));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic11,"Renjith \uD83D\uDE34\uD83D\uDE34","This is not a warkkjkjlk.","Yesterday",90));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic7,"Dhanya","Please pick up phone","5/29/16",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic8,"Chinchu Mary","Hi \uD83D\uDE4F how are u.","5/29/16",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic9,"Exinmates of MTSS !!!!","Hi \uD83D\uDE4F how are u.","5/29/16",0));
        chatListItemSeed.add(new ChatListItem(R.drawable.pic10,"I am back for another day \uD83D\uDE46","Hi \uD83D\uDE4F how are u.","5/29/16",0));
        return chatListItemSeed;
    }

    public List<ParticipantListItem> seedParticipantListMain()
    {
        participantListItemSeed.add(new ParticipantListItem(R.drawable.pic1,"Funlife Returns: \uD83D\uDE01\uD83D\uDE01","Hoping the life of ..."));
        participantListItemSeed.add(new ParticipantListItem(R.drawable.pic2,"AmalRaj kr","Hi \uD83D\uDE4F how are u."));
        participantListItemSeed.add(new ParticipantListItem(R.drawable.pic3,"Bibin C \uD83D\uDE2B\uD83D\uDE2B","Please forward this message "));
        participantListItemSeed.add(new ParticipantListItem(R.drawable.pic4,"Nijin Jain","Please stop messaging me"));
        participantListItemSeed.add(new ParticipantListItem(R.drawable.pic5,"Devika \uD83D\uDE4E\uD83D\uDE4E","Hope is like light..."));
           return participantListItemSeed;
    }
}
