/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tech.energis.pief;

import java.io.InputStream;
import javax.sql.DataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author v
 */
public class SessionFactoryConfigurer
{
    final static String BATIS_CONFIG_RESOURCE = "mybatis-config.xml";
    final static String BATIS_CONFIG_STATIC_RESOURCE = "tech/energis/datamodel/mybatis-config.xml";
    private static SqlSessionFactory defaultSqlSessionFactory;
    private SqlSessionFactory sqlSessionFactory;
    
    private static final Logger log = LoggerFactory.getLogger( SessionFactoryConfigurer.class );
    
    public SessionFactoryConfigurer( DataSource dataSource )
    {
        this( dataSource, null );
    }
    
    public SessionFactoryConfigurer( DataSource dataSource, TransactionFactory transactionFactory )
    {
        if ( transactionFactory == null )
        {
            transactionFactory = new JdbcTransactionFactory();
            log.info( "Use default transaction factory {}", transactionFactory.getClass().getName() );
        }
        else
        {
            log.info( "Use transaction factory {}", transactionFactory.getClass().getName() );
        }
                
        Environment environment = new Environment( "default",
                                                   transactionFactory,
                                                   dataSource );
        makeSqlSessionFactory( environment );
    }
    
    public SessionFactoryConfigurer( Environment environment )
    {
        makeSqlSessionFactory( environment );
    }

    private void makeSqlSessionFactory( Environment environment )
    {
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder()
                .build( getConfigStream() );
        sessionFactory.getConfiguration().setEnvironment( environment );
        setSqlSessionFactory( sessionFactory );
    }

    private void setSqlSessionFactory( SqlSessionFactory sqlSessionFactory )
    {
        this.sqlSessionFactory = sqlSessionFactory;
        if ( defaultSqlSessionFactory == null )
        {
            defaultSqlSessionFactory = sqlSessionFactory;
            log.info( "Default session factory created");
        }
    }

    public SqlSessionFactory getSqlSessionFactory()
    {
        return sqlSessionFactory;
    }

    public static SqlSessionFactory getDefaultSqlSessionFactory()
    {
        return defaultSqlSessionFactory;
    }

    public static InputStream getConfigStream()
    {
        return SessionFactoryConfigurer.class.getResourceAsStream( BATIS_CONFIG_RESOURCE );
    }
}
