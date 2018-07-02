package tech.energis.pief;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImplBatis
{
    protected SqlSession sqlSession;
    private boolean assignedDefaultSession = false;
    protected final Logger log = LoggerFactory.getLogger( getClass() );
    
    public ImplBatis()
    {
        assignDefaultSession();
    }

    public ImplBatis( SqlSession sqlSession )
    {
        this.sqlSession = sqlSession;
    }
    
    private void assignDefaultSession()
    {
        assignedDefaultSession = true;
        sqlSession = SessionConfigurer.getDefaultSqlSession();
        log.info( "Assigned default {}SqlSession", sqlSession == null ? "null " : "" );
    }
    
    protected SqlSession getSession()
    {
        /* Проверка, полезная для работы библиотеки с привязкой Spring.
           Если, при конфигурировании бинов через Spring,
             ImplBatis инициализируется раньше, чем SessionConfigurer,
             то при создании ImplBatis по умолчанию получит объект сессии
             sqlSession==null.
           Возможны два пути решения проблемы:
             1) требовать строгого порядка инициализации бинов: сначала SessionConfigurer,
                а только потом все DAO -- наследники ImplBatis;
             2) проверить sqlSession на предмет преждевременной инициализации по умолчанию
                и при необходимости запросить сессию заново.
           Для обеспечения гибкости конфигов выбран второй способ.
        */
        if ( sqlSession == null && assignedDefaultSession )
            assignDefaultSession();
        return sqlSession;
    }
}
