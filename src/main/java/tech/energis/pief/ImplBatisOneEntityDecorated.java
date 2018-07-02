package tech.energis.pief;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.ibatis.session.SqlSession;

/**
 *
 * @author pokvalitov
 */
public abstract class ImplBatisOneEntityDecorated<M, E extends Implemented<?>> extends ImplBatisOneEntity<M, E>
{
    public ImplBatisOneEntityDecorated( Class<M> mapperInterface )
    {
        super( mapperInterface );
    }

    public ImplBatisOneEntityDecorated( Class<M> mapperInterface, SqlSession sqlSession )
    {
        super( mapperInterface, sqlSession );
    }
    
    public <D extends EntityDecorator<E, ?>> List<D> selectList( Supplier<List<?>> dtoSupplier, Function<E,D> decorator )
    {
        final Stream<E> entityStream = dtoSupplier.get()
                .stream()
                .map( this::makeEntityFromDto );
        final Stream<D> decoratedEntityStream = entityStream
                .map( decorator );
        final List<D> decoratedEntityList = decoratedEntityStream
                .collect( Collectors.toList() );
        return decoratedEntityList;
    }

    public <D extends EntityDecorator<E, ?>> Optional<D> selectOptional( Supplier<?> dtoSupplier, Function<E,D> decorator )
    {
        return Optional.ofNullable( dtoSupplier.get() )
                .map( this::makeEntityFromDto )
                .map( decorator );
    }
    
    public <D extends EntityDecorator<E, ?>> D selectEntity( Supplier<?> dtoSupplier, Function<E,D> decorator )
    {
        Object found = dtoSupplier.get();
        return found != null ? decorator.apply( makeEntityFromDto( found )) : null;
    }
    
    @Override
    protected void doRefreshEntityFromDto( E entity, Object dto )
    {
        E rootEntity = (E) entity.root();
        refreshEntityFromDto( rootEntity, dto );
    }
}
