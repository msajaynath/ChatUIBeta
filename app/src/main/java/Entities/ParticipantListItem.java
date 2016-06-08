package Entities;

/**
 * Created by MS on 31/05/16.
 */
public class ParticipantListItem {
    public String name,status;
    public int pic;
    public ParticipantListItem(int pic,String name, String status)
    {
        this.name=name;
        this.pic=pic;
        this.status=status;

    }
}
