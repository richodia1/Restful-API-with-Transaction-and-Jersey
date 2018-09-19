package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class Variable
{ 
    private long varId;
    private String createdBy;
    private String createdDate;
    private String lastUpdated;
    private String lastUpdatedBy;
    private String name;
    
    public Variable() {}
 
    public Variable(long varId, String createdBy, String createdDate, String lastUpdated, String lastUpdatedBy, String name) 
    { 
        this.varId = varId;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
        this.name = name;
    }
    
    public long getVarId() 
    {
        return varId;
    }
    public void setVarId(long varId) 
    {
        this.varId = varId;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() 
    {
        return createdDate;
    }
    public void setCreatedDate(String createdDate) 
    {
        this.createdDate = createdDate;
    }
    
    public String getLastUpdated() 
    {
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated) 
    {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdatedBy() 
    {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) 
    {
        this.lastUpdatedBy = lastUpdatedBy;
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