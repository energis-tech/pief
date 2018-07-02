/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.energis.pief;

/**
 *
 * @author pokvalitov
 */
public class PiefReflectiveOperationException extends RuntimeException
{
    public PiefReflectiveOperationException()
    {
        super();
    }

    public PiefReflectiveOperationException( String message )
    {
        super( message );
    }

    public PiefReflectiveOperationException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public PiefReflectiveOperationException( Throwable cause )
    {
        super( cause );
    }

    public PiefReflectiveOperationException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
