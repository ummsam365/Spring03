package springMybatisTest;

import static org.junit.Assert.assertNotNull;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class ex01_SqlSession {
	@Autowired
	private SqlSessionFactory sqlFactory;
	// 계층도: SqlSessionFactory (interface)  
	// 		 -> FactoryBean<SqlSessionFactory> (interface) -> SqlSessionFactoryBean
	
	// 스프링 컨테이너가 setSqlSessionFactory 메서드를 자동으로 호출하여 
	// 스프링 설정파일(..-context.xml)에 <bean> 등록된 SqlSessionFactoryBean 객체를 인자로 받아	
	// 부모인 	SqlSessionDaoSupport 에 setSqlSessionFactory() 메서드로 설정해줌.
	// 이렇게 함으로 SqlSessionDaoSupport 로부터 상속된 getSqlSession() 메서드를 호출하여
	// SqlSession 객체를 return 받을 수 있게됨.
	
	@Before // import 주의 : org.junit.Before
	public void testFactory() {
		System.out.println("** SqlSessionFactory 자동주입 확인 => \n"+sqlFactory);
		//assertNotNull(sqlFactory);
	}
	
	// SqlSession -> 실제 DB 연결, Mapper의 Sql 구문을 이용해 DAO의 요청을 처리.
	// test1) 정상 의 경우 sqlSessionTest() 만 Test
	// test2) SqlSessionFactory 생성 안된 경우 Test
	// test2) @Before 적용  sqlFactoryTest() , sqlSessionTest() 모두 Test
	@Test
	public void testSqlSession() {
		try {
			SqlSession sqlSession = sqlFactory.openSession();
			System.out.println("** SqlSession 주입 확인 => \n"+sqlSession);
			// 계층도 : SqlSession (interface) -> SqlSessionTemplate
			assertNotNull(sqlSession);
		} catch (Exception e) {
			System.out.println("** testSqlSession Exception => "+e.toString());
		} //try
	} //testSqlSession

} //class
