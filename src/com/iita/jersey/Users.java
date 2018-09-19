package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Users
{
    private String username;
    private String password;
    private int loginStatus;
    private int id;

    public Users() {}

    public Users(String username, String password, int loginStatus, int id)
    {
        this.username = username;
        this.password = password;
        this.loginStatus = loginStatus;
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getLoginStatus()
    {
        return loginStatus;
    }
    public void setLoginStatus(int loginStatus)
    {
        this.loginStatus = loginStatus;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
}