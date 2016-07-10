package Entities;

/**
 * Created by MS on 31/05/16.
 */
public class ContactItem {
    public String name,mobileNumber;
    public  int  ID,image;
    public ContactItem(String name,String mobileNumber ,int  ID,int image)
    {
        this.name=name;
        this.mobileNumber=mobileNumber;
        this.ID=ID;
        this.image=image;

    }
}
