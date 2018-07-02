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
public class ImplementedEntity<I> extends Implemented<I>
{
    public ImplementedEntity( I impl )
    {
        implementation = impl;
    }
    
    protected I impl()
    {
        return implementation;
    }
    
    protected final I implementation;
}
