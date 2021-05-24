package Models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.sql.Array;

public class Room {

    private String idRoom;
    private String name;
    private int capacity;
    private List<User> guests = new ArrayList<User>();
    private Date checkIn;
    private Date checkOut;
    private double price;
    private RoomType type;

    public void setIdRoom(String idRoom)
    {
        this.idRoom = idRoom;
    }
    public String getIdRoom()
    {
        return idRoom;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public void setCheckIn(Date checkIn)
    {
        this.checkIn = checkIn;
    }
    public Date getCheckIn()
    {
        return checkIn;
    }

    public void setCheckOut(Date checkOut)
    {
        this.checkOut = checkOut;
    }
    public Date getCheckOut()
    {
        return checkOut;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
    public double getPrice()
    {
        return price;
    }

    public void setType(String strType)
    {
        type = RoomType.valueOf(strType);
    }

    public String getType()
    {
        return type.toString();
    }

}
