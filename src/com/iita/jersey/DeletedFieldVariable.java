package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class DeletedFieldVariable
{ 
    private long fieldvarId;
    
    public DeletedFieldVariable() {}
 
    public DeletedFieldVariable(long fieldvarId) 
    { 
        this.fieldvarId = fieldvarId;
    }
    
    public long getFieldvarId() 
    {
        return fieldvarId;
    }
    public void setFieldvarId(long fieldvarId) 
    {
        this.fieldvarId = fieldvarId;
    }  
}