package com.app.common;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages="com.app.mapper")
@EnableTransactionManagement
public class DatabaseConfig{

//	@Autowired
//	DataSource dataSource;
//	
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        try(Connection connection = dataSource.getConnection()){
//            System.out.println(connection);
//            String URL = connection.getMetaData().getURL();
//            System.out.println(URL);
//            String User = connection.getMetaData().getUserName();
//            System.out.println(User);
//
//            Statement statement = connection.createStatement();
//            String sql = "SELECT PERSONID, PASSWORD, EMAIL FROM PERSON";
//            
//            ResultSet executeQuery = statement.executeQuery(sql);
//            System.out.println(executeQuery.getRow());
//        }
//
//    }
	

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/**/*.xml"));
        return sessionFactory.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
      final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
      return sqlSessionTemplate;
    }
	
}
