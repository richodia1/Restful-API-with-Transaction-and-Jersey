package com.iita.jersey;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement		// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
public class wsSQLResult
{
    public String WasSuccessful;
    public String Exception;

    public wsSQLResult() {}

    public String getWasSuccessful()
    {
        return WasSuccessful;
    }
    public void setWasSuccessful(String wasSuccessful)
    {
        this.WasSuccessful = wasSuccessful;
    }

    public String getException()
    {
        return Exception;
    }
    public void setException(String exception)
    {
        this.Exception = exception;
    }
}
