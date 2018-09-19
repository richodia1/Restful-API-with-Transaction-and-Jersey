package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class ContainerType
{ 
    private long containertypeId;
    private String name;
    private int version;
    
    public ContainerType() {}
 
    public ContainerType(long containertypeId, String name, int version) 
    { 
        this.containertypeId = containertypeId;
        this.name = name;
        this.version = version;
    }
    
    public long getContainertypeId() 
    {
        return containertypeId;
    }
    public void setContainertypeId(long containertypeId) 
    {
        this.containertypeId = containertypeId;
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
}