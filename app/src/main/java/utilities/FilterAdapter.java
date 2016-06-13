package utilities;

import java.util.ArrayList;
import java.util.List;

import Entities.ChatListItem;

/**
 * Created by MS on 12/06/16.
 */

public  class FilterAdapter {
    public  List<ChatListItem> filter(List<ChatListItem> models, String query) {
        query = query.toLowerCase();

        final List<ChatListItem> filteredModelList = new ArrayList<>();
        for (ChatListItem model : models) {
            final String text = model.chatName.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
