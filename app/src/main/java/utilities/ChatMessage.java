package utilities;

import android.graphics.Bitmap;

public class ChatMessage {
    private long id;
    private boolean isMe;
    private String message;
    private Long userId;
    private String dateTime,localfilePath;
    private Bitmap image;

    public boolean sameSender=false,imageAttached=false,fileAttached=false;

    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image=image;
    }
    public String getFilePath() {
        return localfilePath;
    }
    public String getFileName() {
        return localfilePath.substring(localfilePath.lastIndexOf("/")+1);
    }
    public void setFilePath(String filePath) {
        this.localfilePath=filePath;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean getIsme() {
        return isMe;
    }
    public void setMe(boolean isMe) {
        this.isMe = isMe;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return dateTime;
    }

    public void setDate(String dateTime) {
        this.dateTime = dateTime;
    }
}
