package contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ContactDAO {
	
	private final String DRIVER			= "oracle.jdbc.driver.OracleDriver";
	private final String URL			= "jdbc:oracle:thin:@localhost:1521:xe";
	private final String USERID			= "ora_user";
	private final String USERPW			= "1234";
	private String sessionID;
	private String userName;
	
	public Connection open() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USERID, USERPW);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
//	1. 회원 추가 메소드
	public void insert(ContactDTO contactDTO) throws Exception{
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO contact(id, profile_id, name, phone_num, address, gubun_id, memo, user_id)	");
		sql.append("	 VALUES (TO_CHAR(sysdate, 'YYYY')||LPAD(id_seq.nextval,4,'0')				");
		sql.append("			,(SELECT PROFILE_ID													");
		sql.append("				FROM PROFILE													");
		sql.append("			   WHERE PROFILE_val = ?)											");
		sql.append("			,?,?,?																");
		sql.append("			,(SELECT GUBUN_ID													");
		sql.append("  				FROM gubun 														");
		sql.append("			   WHERE GUBUN_NAME = ?)											");
		sql.append("			,?,?)																	");			
		
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		contactDTO.setUser_id(sessionID);
		
		try(con;pstmt){
			pstmt.setString(1, contactDTO.getProfile_val());
			pstmt.setString(2, contactDTO.getName());
			pstmt.setString(3, contactDTO.getPhone_num());
			pstmt.setString(4, contactDTO.getAddress());
			pstmt.setString(5, contactDTO.getGubun_name());
			pstmt.setString(6, contactDTO.getMemo());
			pstmt.setString(7, contactDTO.getUser_id());
			
			pstmt.executeUpdate();
		}
		
	}
	
//	2. 목록+상세보기 출력 메소드
	public ArrayList<ContactDTO> findAll() throws Exception{
		ArrayList<ContactDTO> contacts 	= new ArrayList<ContactDTO>();
		StringBuilder sql 				= new StringBuilder();
		sql.append("SELECT a.id							");
		sql.append("	 , b.profile_val				");
		sql.append("	 , a.name						");
		sql.append("	 , a.phone_num					");
		sql.append("	 , a.address					");
		sql.append("	 , c.gubun_name					");
		sql.append("	 , a.memo						");
		sql.append("  FROM contact a					");
		sql.append("  	 , profile b					");
		sql.append("  	 , gubun c						");
		sql.append(" WHERE a.profile_id = b.profile_id	");
		sql.append("   AND a.gubun_id = c.gubun_id		");
		sql.append("   AND a.user_id = ?				");
		
		Connection con 			= open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, sessionID);
		ResultSet rs 			= pstmt.executeQuery();
			
		try(con;pstmt;rs){
			while(rs.next()) {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setId(rs.getString("id"));
				contactDTO.setProfile_val(rs.getString("profile_val"));
				contactDTO.setName(rs.getString("name"));
				contactDTO.setPhone_num(rs.getString("phone_num"));
				contactDTO.setAddress(rs.getString("address"));
				contactDTO.setGubun_name(rs.getString("gubun_name"));
				contactDTO.setMemo(rs.getString("memo"));
				contacts.add(contactDTO);
			}
		}
		return contacts;
	}
	
// 3. 검색 메소드
	public ArrayList<ContactDTO> findById(String name) throws Exception{
		ArrayList<ContactDTO> contacts 	= new ArrayList<ContactDTO>();
		StringBuilder sql 				= new StringBuilder();
		sql.append("SELECT a.id							");
		sql.append("	 , b.profile_val				");
		sql.append("	 , a.name						");
		sql.append("	 , a.phone_num					");
		sql.append("	 , a.address					");
		sql.append("	 , c.gubun_name					");
		sql.append("	 , a.memo						");
		sql.append("  FROM contact a					");
		sql.append("  	 , profile b					");
		sql.append("  	 , gubun c						");
		sql.append(" WHERE a.profile_id = b.profile_id	");
		sql.append("   AND a.gubun_id = c.gubun_id		");
		sql.append("   AND a.user_id = ?				");
		sql.append("   AND a.name like ?				");
		
		Connection con 			= open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, sessionID);
		pstmt.setString(2, "%" + name + "%");
		ResultSet rs 			= pstmt.executeQuery();
			
		try(con;pstmt;rs){
			while(rs.next()) {
				ContactDTO contactDTO = new ContactDTO();
				contactDTO.setId(rs.getString("id"));
				contactDTO.setProfile_val(rs.getString("profile_val"));
				contactDTO.setName(rs.getString("name"));
				contactDTO.setPhone_num(rs.getString("phone_num"));
				contactDTO.setAddress(rs.getString("address"));
				contactDTO.setGubun_name(rs.getString("gubun_name"));
				contactDTO.setMemo(rs.getString("memo"));
				contacts.add(contactDTO);
			}
		}
		return contacts;
	}
	
//	4. 회원 수정 메소드
	public void Update(ContactDTO contactDTO) throws Exception {
		StringBuilder sql		= new StringBuilder();
		sql.append("UPDATE contact								");
		sql.append("   SET profile_id = (SELECT profile_id		");
		sql.append("					   FROM profile			");
		sql.append("					  WHERE profile_val = ?)");
		sql.append("     , name = ?								");
		sql.append("   	 , phone_num = ?						");
		sql.append("   	 , address = ?							");
		sql.append("   	 , GUBUN_ID = (SELECT GUBUN_ID			");
		sql.append("  					 FROM gubun				");
		sql.append("			   		WHERE GUBUN_NAME = ?)	");
		sql.append("   	 , memo = ?								");
		sql.append(" WHERE id = ?								");
		
		Connection con			= open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		
		try(con;pstmt){
			pstmt.setString(1, contactDTO.getProfile_val());
			pstmt.setString(2, contactDTO.getName());
			pstmt.setString(3, contactDTO.getPhone_num());
			pstmt.setString(4, contactDTO.getAddress());
			pstmt.setString(5, contactDTO.getGubun_name());
			pstmt.setString(6, contactDTO.getMemo());
			pstmt.setString(7, contactDTO.getId());
			pstmt.executeUpdate();
		}
	}
	
//	5. 회원 삭제
	public void delete(String id) throws Exception {
		String sql = "delete from contact where id = ?";
		
		Connection con 			= open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, id);
		
		try(con;pstmt){
			if(pstmt.executeUpdate()==0) {	// 삭제 안됨
				throw new SQLException("삭제 안됨");
			}
		}
	}
	
//	6. 회원가입
	public int register(UserInfoDTO userInfoDTO) throws Exception{
		String sql = "INSERT INTO userInfo(name, user_id, pw) VALUES (?, ?, ?)";
		Connection con = open();
		PreparedStatement pstmt = con.prepareStatement(sql);
		int register = 0;
		
		try(con;pstmt){
			pstmt.setString(1, userInfoDTO.getName());
			pstmt.setString(2, userInfoDTO.getUser_id());
			pstmt.setString(3, userInfoDTO.getPw());
			
			register = pstmt.executeUpdate();
				
			}
		
		return register;
	}
	
//	7. 로그인
	public String login(UserInfoDTO userInfoDTO) throws Exception{
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT name				");
		sql.append("  FROM userinfo			");
		sql.append(" WHERE user_id = ?		");
		sql.append("   AND pw = ?			");
		
		Connection con 			= open();
		PreparedStatement pstmt = con.prepareStatement(sql.toString());
		pstmt.setString(1, userInfoDTO.getUser_id());
		pstmt.setString(2, userInfoDTO.getPw());
		ResultSet rs 			= pstmt.executeQuery();
		
		try(con;pstmt){
			if (rs.next()) {
				userInfoDTO.setName(rs.getString("name"));
			}
		}
		String name = userInfoDTO.getName();
		return name;
	}
	
	public String getSessionID(HttpSession httpSession) {
		sessionID = (String)httpSession.getAttribute("sessionID");
		userName = (String) httpSession.getAttribute("userName");
		
		return sessionID;
	}
	
}
