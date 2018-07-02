/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.energis.pief;

/**
 *
 * @author pokvalitov
 * @param <E> Класс корневой сущности, хранящей данные
 * @param <I> Класс реализации в паттерне «мост»
 */
public class EntityDecorator<E extends Implemented<I>, I>
        extends Implemented<I>
{
    public EntityDecorator( E original )
    {
        this.original = original;
    }

    public EntityDecorator( EntityDecorator<? extends E, I> original )
    {
        this.original = original;
    }

    @Override
    protected I impl()
    {
        return decorated().impl();
    }
    
    @Override
    protected E root()
    {
        return (E) decorated().root();
    };

    protected E decorated()
    {
        return (E) original;
    }
    
    private final Implemented<I> original;
}
