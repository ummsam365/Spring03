package util;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vo.BoardVO;
import vo.PageVO;

// ** Board CRUD 구현
@Repository
public class BoardDAO {
	
	@Autowired
	private SqlSession sqlSession; 
	// SqlSession (Interface) -> SqlSessionTemplate (servl...xml 에 Bean 등록)
	private static final String NS ="com.ncs.BoardMapper.";
	
// ** Page BoardList
	public int totalRowCount() {
		return sqlSession.selectOne(NS+"totalRowCount");
	}  
	public List<BoardVO> pageList(PageVO<BoardVO> pvo) {
		return sqlSession.selectList(NS+"pageList",pvo);
	}
	
// ** Check BoardList
	public List<BoardVO> checkselectList(BoardVO vo) {
		return sqlSession.selectList(NS+"checkselectList",vo);
	}	

// ** Ajax id BoardList
	public List<BoardVO> idbList(BoardVO vo) {
		return sqlSession.selectList(NS+"idbList",vo);
	} //idbList
	
// ** reply insert
// => 답글 등록과 step증가	
	public int rinsert(BoardVO vo) {
		// step증가 후 입력
		// 조건 => root 동일하고 step 이 vo 의 step 과 같거나 큰경우
		System.out.println("** stepUpdate 결과 => "+
					sqlSession.update(NS+"stepUpdate",vo));
		return sqlSession.insert(NS+"rinsert",vo);
	} //rinsert
	
// ** selectList
	public List<BoardVO> selectList() {
		return sqlSession.selectList(NS+"selectList");
	} //selectList
	
// ** selectOne
	public BoardVO selectOne(BoardVO vo) {
		return sqlSession.selectOne(NS+"selectOne",vo);
	} //selctOne

// ** insert (원글) : root(seq) step(0) indent(0)
	public int insert(BoardVO vo) {
		return sqlSession.insert(NS+"insert",vo);
	} //insert

// ** update
	public int update(BoardVO vo) {
		return sqlSession.update(NS+"update",vo);
	} //update
	
// ** delete	
	public int delete(BoardVO vo) {
		// 답글등록 후 
		// => 원글 삭제 : 모든 후손들 같이 삭제
		// => 답글 삭제 : 현재글만 삭제
		// => 원글 or 답글 구분 : seq==root (원글)
		return sqlSession.delete(NS+"delete",vo);
	} //delete

// ** countUp(vo)	
	public int countUp(BoardVO vo) {
		return sqlSession.update(NS+"countUp",vo);
	} //countUp

} //class
