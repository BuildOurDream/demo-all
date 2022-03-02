package demo.mybatis;

import demo.mybatis.entity.Employee;
import demo.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2022-02-11
 */
public class TestMybatis {

    private SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private Configuration configuration;

    @BeforeEach
    void setUp() throws IOException {
        try (Reader reader = Resources
                .getResourceAsReader("mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            sqlSession = sqlSessionFactory.openSession();
            configuration = sqlSession.getConfiguration();
        }
    }

    @Test
    void testMetaObject() {
        MetaObject metaObject = configuration.newMetaObject(Employee.random());
        System.out.println(metaObject.getValue("pet.name"));
        metaObject.setValue("pet.name", "小花");
        System.out.println(metaObject.getValue("pet.name"));
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        MappedStatement getById = configuration.getMappedStatement("getById");
        System.out.println(getById.getSqlCommandType());
        BoundSql boundSql = getById.getBoundSql(1L);
        System.out.println(boundSql.getSql());
        Employee byId = mapper.getById(1L);
        System.out.println(byId);
        System.out.println(sqlSession.selectOne("demo.mybatis.mapper.EmployeeMapper.getById", 1L).toString());
    }
}
