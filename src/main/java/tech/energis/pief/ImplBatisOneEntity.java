package tech.energis.pief;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author pokvalitov
 * @param <M> Интерфейс mapper'а для MyBatis
 * @param <E> Класс сущности
 */
public abstract class ImplBatisOneEntity<M, E extends Implemented<?>> extends ImplBatis
{

    private final Class<M> mapperInterface;

    public ImplBatisOneEntity( Class<M> mapperInterface )
    {
        super();
        this.mapperInterface = mapperInterface;
    }

    public ImplBatisOneEntity( Class<M> mapperInterface, SqlSession sqlSession )
    {
        super( sqlSession );
        this.mapperInterface = mapperInterface;
    }
    
    public List<E> selectList( Supplier<List<?>> dtoSupplier )
    {
        return dtoSupplier.get().stream()
                .map( this::makeEntityFromDto )
                .collect( Collectors.toList() );
    }
    
    public Optional<E> selectOptional( Supplier<?> dtoSupplier )
    {
        return Optional.ofNullable( dtoSupplier.get() )
                .map( this::makeEntityFromDto );
    }
    
    public E selectEntity( Supplier<?> dtoSupplier )
    {
        Object found = dtoSupplier.get();
        return found != null ? makeEntityFromDto( found ) : null;
    }
    
    protected M getMapper()
    {
        return getSession().getMapper( mapperInterface );
    }
    
    protected E makeEntityFromDto( Object dto )
    {
        E newEntity = newEntityInstance();
        doRefreshEntityFromDto( newEntity, dto );
        return newEntity;
    }
    
    protected void doRefreshEntityFromDto( E entity, Object dto )
    {
        refreshEntityFromDto( entity, dto );
    }
    
    protected abstract void refreshEntityFromDto( E entity, Object dto );
    protected abstract E newEntityInstance();
}
