package com.ISOUR.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ISOUR.Common.Common;
import com.ISOUR.VO.MemberVO;


public class MemberDAO {
	private Connection conn = null;
	private Statement stmt = null; //표준 SQL문을 수행하기 위한 Statement 객체 얻기
	private ResultSet rs = null; // Statement의 수행 결과를 여러행으로 받음
	// SQL문을 미리 컴파일해서 재 사용하므로 Statement 인터페이스보다 훨씬 빨르게 데이터베이스 작업을 수행
	private PreparedStatement pstmt = null; 
	
	public boolean logingCheck(String id, String pwd) {
		
		
		try {

			conn = Common.getConnection();
			stmt = conn.createStatement(); // Statement 객체 얻기
			String sql = "SELECT * FROM T_MEMBER WHERE ID = " + "'" + id + "'";
			rs = stmt.executeQuery(sql);

			while(rs.next()) { // 읽은 데이타가 있으면 true
				String sqlId = rs.getString("id"); // 쿼리문 수행 결과에서 ID값을 가져 옴
				String sqlPwd = rs.getString("pwd");
				
				System.out.println("ID : " + sqlId);
				System.out.println("PWD : " + sqlPwd);
				
				if(id.equals(sqlId) && pwd.equals(sqlPwd)) {
					Common.close(rs);
					Common.close(stmt);
					Common.close(conn);
					return true;
				}
			}
			Common.close(rs);
			Common.close(stmt);
			Common.close(conn);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<MemberVO> memberSelect() {
		List<MemberVO> list = new ArrayList<>();
		try {
			conn = Common.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM I_MEMBER";
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String id = rs.getString("ID");
				String pwd = rs.getString("PWD");
				String name = rs.getString("NAME");
				String email = rs.getString("EMAIL");
				Date join = rs.getDate("JOIN");
				
				MemberVO vo = new MemberVO();  // 각 정보를 저장할 수 있는 객체 생성.
				vo.setId(id);
				vo.setPwd(pwd);
				vo.setName(name);
				vo.setEmail(email);
				vo.setJoin(join);
				
				list.add(vo);  // 받은 정보를 list로 저장. 
			}
			Common.close(rs);
			Common.close(stmt);
			Common.close(conn);
								
		} catch(Exception e) {
			e.printStackTrace();	// 어디서 오류가 발생했는지 뿌려줌. 
		}
		return list;
	}
	
	// 회원가입여부 확인!!
	public boolean regIdCheck(String id) {

		boolean isNotReg = false;
		try {
			conn = Common.getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM I_MEMBER WHERE ID = " + "'" + id + "'";
			rs = stmt.executeQuery(sql);

			if(rs.next()) {
				isNotReg = false;
			} else {
				isNotReg =  true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		Common.close(rs);
		Common.close(stmt);
		Common.close(conn);
		return isNotReg;  // 가입되어 있으면 false, 가입 안되어 있으면 true.
	}
	// 회원가입
	public boolean memberRegister(String id, String pwd, String name, String mail) {
		int result = 0;
		String sql = "INSERT INTO T_MEMBER(ID, PWD, NAME, EMAIL, JOIN) VALUES(?, ?, ?, ?, SYSDATE)";
		try {
			conn = Common.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, mail);
			result = pstmt.executeUpdate();	
			System.out.println("여기까지 와라....2");
			System.out.println("회원 가입 DB 결과 확인 : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		Common.close(rs);
		Common.close(pstmt);
		Common.close(conn);
		
		if(result == 1) return true;
		else return false;
	}

}


















