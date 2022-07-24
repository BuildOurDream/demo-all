package demo.h2;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author jingxin
 * @date 2022-07-23
 */
@DisplayName("Junit5使用")
@SpringBootTest(classes = {DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
class Junit5TestTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        System.out.println("before");
        Assertions.assertNotNull(jdbcTemplate);
    }

    @Test
    @DisplayName("测试h2")
    void testH2() {
        System.out.println(jdbcTemplate.queryForList("select * from user"));
    }
}