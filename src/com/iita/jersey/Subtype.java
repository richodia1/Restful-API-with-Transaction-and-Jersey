package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Subtype
{ 
    private String name;
    
    public Subtype() {}
 
    public Subtype(String name) 
    { 
        this.name = name;
    }
    
    public String getName() 
    {
        return name;
    }
    public void setName(String name) 
    {
        this.name = name;
    }    
}