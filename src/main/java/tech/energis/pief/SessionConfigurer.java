package tech.energis.pief;

import org.apache.ibatis.session.SqlSession;

public class SessionConfigurer
{
    private SqlSession sqlSession;
    private static SqlSession defaultSqlSession;
    
    public SessionConfigurer( SqlSession sqlSession )
    {
        setSqlSession( sqlSession );
    }

    public void setSqlSession( SqlSession sqlSession )
    {
        this.sqlSession = sqlSession;
        if ( defaultSqlSession == null )
        {
            defaultSqlSession = sqlSession;
        }            
    }

    public SqlSession getSqlSession()
    {
        return sqlSession;
    }

    public static SqlSession getDefaultSqlSession()
    {
        return defaultSqlSession;
    }
}
