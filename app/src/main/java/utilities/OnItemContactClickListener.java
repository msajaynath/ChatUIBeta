package utilities;

import Entities.ContactItem;

public interface OnItemContactClickListener {
    void onLongClick(ContactItem item);
    void onClick(ContactItem item);
}