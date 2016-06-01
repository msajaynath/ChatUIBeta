package Entities;

/**
 * Created by MS on 31/05/16.
 */
public class ChatListItem {
    public String chatName,lastChatMessage,lastsendTimeText;
    public  int profilePic,unReadChatCount;
    public ChatListItem(int profilePic,String chatName,String lastChatMessage,String lastsendTimeText,int unReadChatCount)
    {
        this.profilePic=profilePic;
        this.chatName=chatName;
        this.lastChatMessage=lastChatMessage;
        this.lastsendTimeText=lastsendTimeText;
        this.unReadChatCount=unReadChatCount;

    }
}
