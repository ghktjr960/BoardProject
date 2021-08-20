package member.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.Connutil;

public class MemberDao {
	private static MemberDao instance = null;

	private MemberDao() {
	}

	public static MemberDao getInstance() {
		if (instance == null) {
			synchronized (MemberDao.class) {
				instance = new MemberDao();
			}
		}
		return instance;
	}
	
	// 아이디 중복확인
	public boolean memberIdCheck(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		boolean idCheck = false;
		ResultSet rs = null;
		
		try {
			conn = Connutil.getConnection();
			String query = "select count(\"ID\") as \"COUNT\" from \"MEMBER_PROJECT\" where \"ID\"=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
				if(result > 0) {
					idCheck = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return idCheck;
	}
	
	// 회원가입
	public boolean memberRegist(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int registCheck = 0;
		boolean result = false;
		
		try {
			conn = Connutil.getConnection();
			String query = "insert into \"MEMBER_PROJECT\" "
					+ "(\"NUM\", \"ID\", \"PASSWORD\", \"NAME\", \"YEAR\", \"MONTH\", \"DAY\", \"GENDER\", \"EMAIL\")"
					+ " values (\"MEMBER_PROJECT_SEQ\".nextval, ?, ?, ?, ?, ?, ?, ?, ?) ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getName());
			pstmt.setInt(4, vo.getYear());
			pstmt.setInt(5, vo.getMonth());
			pstmt.setInt(6, vo.getDay());
			pstmt.setString(7, vo.getGender());
			pstmt.setString(8, vo.getEmail());
			
			registCheck = pstmt.executeUpdate();
			if(registCheck > 0) {
				System.out.println(registCheck + "행 삽입됨(회원가입)");
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	// login
	public boolean memberLogin(String id, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String pwCheck = "";
		boolean result = false;
		try {
			conn = Connutil.getConnection();
			String query = "select \"PASSWORD\" from \"MEMBER_PROJECT\" where \"ID\"=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				pwCheck = rs.getString(1);
			}
			if(pwCheck.equals(password)){
				System.out.println("dao : 로그인 성공!" );
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	// 회원정보 가져오기
	public MemberVo getMemberInfo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVo vo = new MemberVo();
		
		try {
			conn = Connutil.getConnection();
			String query = "select * from \"MEMBER_PROJECT\" where \"ID\" = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setNum(rs.getInt("NUM"));
				vo.setId(rs.getString("ID"));
				vo.setPassword(rs.getString("PASSWORD"));
				vo.setName(rs.getString("NAME"));
				vo.setYear(rs.getInt("YEAR"));
				vo.setMonth(rs.getInt("MONTH"));
				vo.setDay(rs.getInt("DAY"));
				vo.setGender(rs.getString("GENDER"));
				vo.setEmail(rs.getString("EMAIL"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return vo;
	}
	
	// 회원정보 수정
	public boolean editMemberInfo(MemberVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int checkEdit = 0;
		boolean result = false;
		
		try {
			conn = Connutil.getConnection();
			String query = "update \"MEMBER_PROJECT\" set "
					+ "\"PASSWORD\" = ?, \"NAME\" = ?, \"YEAR\" =?, \"MONTH\" = ?, \"DAY\" = ?, "
					+ "\"GENDER\" = ?, \"EMAIL\" = ? where \"ID\" = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getPassword());
			pstmt.setString(2, vo.getName());
			pstmt.setInt(3, vo.getYear());
			pstmt.setInt(4, vo.getMonth());
			pstmt.setInt(5, vo.getDay());
			pstmt.setString(6, vo.getGender());
			pstmt.setString(7, vo.getEmail());
			pstmt.setString(8, vo.getId());
			checkEdit = pstmt.executeUpdate();
			if(checkEdit > 0 ) {
				result = true;
				System.out.println("회원 정보 수정 완료");
			} else {
				System.out.println("회원 정보 수정 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	// 회원 탈퇴
	public boolean removeMember(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int checkRemove = 0;
		boolean result = false;
		
		try {
			conn = Connutil.getConnection();
			String query = "delete from \"MEMBER_PROJECT\" where \"ID\" = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			checkRemove = pstmt.executeUpdate();
			if(checkRemove > 0) {
				result = true;
				System.out.println("삭제 완료");
			} else {
				System.out.println("삭제 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
