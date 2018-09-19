package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class ItemType
{ 
    private long itemtypeId;
    private String name;
    private int version;
    private String shortName;
    
    public ItemType() {}
 
    public ItemType(long itemtypeId, String name, int version, String shortName) 
    { 
        this.itemtypeId = itemtypeId;
        this.name = name;
        this.version = version;
        this.shortName = shortName;
    }
    
    public long getItemtypeId() 
    {
        return itemtypeId;
    }
    public void setItemtypeId(long itemtypeId) 
    {
        this.itemtypeId = itemtypeId;
    }

    public String getName() 
    {
        return name;
    }
    public void setName(String name) 
    {
        this.name = name;
    } 

    public int getVersion() 
    {
        return version;
    }
    public void setVersion(int version) 
    {
        this.version = version;
    }  

    public String getShortName() 
    {
        return shortName;
    }
    public void setShortName(String shortName) 
    {
        this.shortName = shortName;
    }    
}