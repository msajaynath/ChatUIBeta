package utilities;

public interface OnItemChatClickListener {
    void onLongClick(ChatMessage item,int position);
    void onClick(ChatMessage item,int position);
    void onImageClick(ChatMessage item,int position);
}