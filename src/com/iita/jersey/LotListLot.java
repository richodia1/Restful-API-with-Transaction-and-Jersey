package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class LotListLot
{ 
    private long lotlistId;
    private long lotId;
    
    public LotListLot() {}
 
    public LotListLot(long lotlistId, long lotId) 
    { 
        this.lotlistId = lotlistId;
        this.lotId = lotId;
    }
    
    public long getLotlistId() 
    {
        return lotlistId;
    }
    public void setLotlistId(long lotlistId) 
    {
        this.lotlistId = lotlistId;
    }

    public long getLotId() 
    {
        return lotId;
    }
    public void setLotId(long lotId) 
    {
        this.lotId = lotId;
    }     
}