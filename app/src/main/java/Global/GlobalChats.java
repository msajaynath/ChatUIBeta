package Global;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import utilities.ChatMessage;

public class GlobalChats extends Application {
        private List<ChatMessage> chats;
        public List <ChatMessage>  getSavedChats() {
                return chats;
        }

        public void setSavedChats( List<ChatMessage> chats) {
                this.chats = chats;
        }
        @Override
        public void onCreate() {
                //reinitialize variable
                chats=new ArrayList<ChatMessage>();
        }
}