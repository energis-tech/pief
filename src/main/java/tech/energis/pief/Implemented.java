package tech.energis.pief;

/**
 *
 * @author pokvalitov
 */
public abstract class Implemented<I>
{
    protected abstract I impl();
    protected Implemented<I> root()
    {
        return this;
    };
}
