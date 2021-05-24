package Models;

public class User {
    private String idUser;
    private String userName;
    private String name;
    private String surName;
    private String number;
    private double money;
    private String password;
    private int age;
    private String idRoom;

    public void setIdUser(String id)
    {
        idUser = id;
    }
    public String getIdUser()
    {
        return idUser;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public String getUserName()
    {
        return userName;
    }


    public void setSurName(String surName)
    {
        this.surName = surName;
    }
    public String getSurName()
    {
        return surName;
    }


    public void setAge(int age)
    {
        if (age > 0 && age < 100) {
            this.age = age;
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }
    public int getAge()
    {
        return age;
    }


    public void setMoney(double money)
    {
        this.money = money;
    }
    public double getMoney()
    {
        return money;
    }


    public void setNumber(String number)
    {
        this.number = number;
    }
    public String getNumber()
    {
        return number;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }


    public void setIdRoom(String idRoom)
    {
        this.idRoom = idRoom;
    }
    public String getIdRoom()
    {
        return idRoom;
    }



}
