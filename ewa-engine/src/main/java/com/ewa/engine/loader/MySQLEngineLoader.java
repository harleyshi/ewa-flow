package com.ewa.engine.loader;

import com.ewa.engine.domain.EwaEngineDO;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;

/**
 * @author harley.shi
 * @date 2025/1/23
 */
public class MySQLEngineLoader implements EngineLoader {

    private final SqlSessionFactory sqlSessionFactory;

    public MySQLEngineLoader(DataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("default", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers("com.ewa.engine.loader.mapper");
        xmlMapperParser(configuration, "classpath*:mapper/*.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private void xmlMapperParser(Configuration configuration, String xmlPath) {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(xmlPath);
            for (Resource resource : resources) {
                try (InputStream inputStream = resource.getInputStream()) {
                    XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource.toString(), configuration.getSqlFragments());
                    mapperParser.parse();
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * load all engine list from database
     */
    @Override
    public List<EwaEngineDO> loadAllEngineList() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList("com.ewa.engine.loader.mapper.EwaEngineMapper.listAll");
        }
    }

    /**
     * load published engine list from database
     */
    @Override
    public List<EwaEngineDO> loadPublishedEngines() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectList("com.ewa.engine.loader.mapper.EwaEngineMapper.getPublishedEngines");
        }
    }

    /**
     * load engine by name from database
     * @param engineName engine name
     */
    @Override
    public EwaEngineDO getEngineByName(String engineName) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return sqlSession.selectOne("com.ewa.engine.loader.mapper.EwaEngineMapper.getEngineByName", engineName);
        }
    }
}
