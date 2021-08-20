package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Connutil;

public class BoardDao {
	private static BoardDao instance = null;

	private BoardDao() {
	}

	public static BoardDao getInstance() {
		if (instance == null) {
			synchronized (BoardDao.class) {
				instance = new BoardDao();
			}
		}
		return instance;
	}

	// 전체 게시글 수 가져오기
	public int getPostCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int listCount = 0;

		try {
			conn = Connutil.getConnection();
			String query = "select count(\"NUM\") as \"COUNT\" from \"BOARD_PROJECT\"";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				listCount = rs.getInt("COUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return listCount;
	}

	// 게시글 목록(list)가져오기
	public List<BoardVo> getPostList(int startNum, int endNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVo> result = null;
		
		try {
			conn = Connutil.getConnection();
			StringBuffer query = new StringBuffer();
			query.append("select * from");
			query.append("(select rownum \"RNUM\", \"NUM\", \"WRITER\", \"SUBJECT\", \"READCOUNT\", ");
			query.append("\"REF\", \"STEP\", \"DEPTH\", \"REGDATE\", \"CONTENT\" from ");
			query.append("(select * from \"BOARD_PROJECT\" order by \"REF\" desc, \"STEP\" asc))");
			query.append("where \"RNUM\" >= ? and \"RNUM\" <= ?");

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, startNum);
			pstmt.setInt(2, endNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = new ArrayList<BoardVo>(10);
				do {
					BoardVo vo = new BoardVo();
					vo.setNum(rs.getInt("NUM"));
					vo.setWriter(rs.getString("WRITER"));
					vo.setSubject(rs.getString("SUBJECT"));
					vo.setReadcount(rs.getInt("READCOUNT"));
					vo.setRef(rs.getInt("REF"));
					vo.setStep(rs.getInt("STEP"));
					vo.setDepth(rs.getInt("DEPTH"));
					vo.setRegdate(rs.getTimestamp("REGDATE"));
					vo.setContent(rs.getString("CONTENT"));
					result.add(vo);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}
	
	// 게시글 저장
	public int insertPost(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		int num = vo.getNum();
		int ref = vo.getRef();
		int step = vo.getStep();
		int depth = vo.getDepth();
		
		int number = 0;
		String query = "";
		
		try {
			conn = Connutil.getConnection();
			query = "select max(\"NUM\") from \"BOARD_PROJECT\"";
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				number = rs.getInt(1) + 1;
			} else {
				number = 1;
			}
			if(num != 0) { //답 글일 경우
				query = "update \"BOARD_PROJECT\" set \"STEP\"=\"STEP\"+1 where \"REF\" = ? and \"STEP\" > ?";
				pstmt.close();
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeQuery();
				step++;
				depth++;
			} else {
				ref = number;
				step = 0;
				depth = 0;
			}
			query = "insert into \"BOARD_PROJECT\" "
					+ "(\"NUM\", \"WRITER\",  \"SUBJECT\", \"REGDATE\", \"REF\", \"STEP\", \"DEPTH\", \"CONTENT\") "
					+ "values (\"BOARD_PROJECT_SEQ\".nextval, ?, ?, ?, ?, ?, ?, ?)"; 
			pstmt.close();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, vo.getWriter());
			pstmt.setString(2, vo.getSubject());
			pstmt.setTimestamp(3, vo.getRegdate());
			pstmt.setInt(4, ref);
			pstmt.setInt(5, step);
			pstmt.setInt(6, depth);
			pstmt.setString(7, vo.getContent());
			
			result = pstmt.executeUpdate();
			System.out.println(result + "개 게시글 저장완료 Dao");
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
		return result;
	}
	
	// 업로드 정보 DB에 저장
	public int insertUploadInfo(BoardUploadVo upVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			conn = Connutil.getConnection();
			String query = "insert into \"UPLOAD_PROJECT\" "
					+ "(\"NUM\", \"FILENAME\", \"CONTENTTYPE\", \"FILESIZE\") "
					+ "values (\"BOARD_PROJECT_SEQ\".currval, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, upVo.getFileName());
			pstmt.setString(2, upVo.getContentType());
			pstmt.setLong(3, upVo.getFileSize());
			result = pstmt.executeUpdate();
			System.out.println(result + "개의 upload정보가 입력됨");
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
	
	// 게시글 가져오기
	public BoardVo getPost(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVo vo = new BoardVo();
		
		try {
			conn = Connutil.getConnection();
			String updateQuery = "update \"BOARD_PROJECT\" set \"READCOUNT\"=\"READCOUNT\"+1 where \"NUM\"=?";
			pstmt = conn.prepareStatement(updateQuery);
			pstmt.setInt(1, num);
			pstmt.executeQuery();
			
			String selectQuery = "select * from \"BOARD_PROJECT\" where \"NUM\"=?";
			pstmt.close();
 			pstmt = conn.prepareStatement(selectQuery);
 			pstmt.setInt(1, num);
 			rs = pstmt.executeQuery();
 			if(rs.next()) {
 				vo.setNum(rs.getInt("NUM"));
 				vo.setWriter(rs.getString("WRITER"));
 				vo.setSubject(rs.getString("SUBJECT"));
 				vo.setReadcount(rs.getInt("READCOUNT"));
 				vo.setRef(rs.getInt("REF"));
 				vo.setStep(rs.getInt("STEP"));
 				vo.setDepth(rs.getInt("DEPTH"));
 				vo.setRegdate(rs.getTimestamp("REGDATE"));
 				vo.setContent(rs.getString("CONTENT"));
 			}
		}catch (Exception e) {
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
	
	// 업로드 정보 가져오기
	public BoardUploadVo getUpload(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardUploadVo vo = new BoardUploadVo();
		
		try {
			conn = Connutil.getConnection();
			String query = "select * from \"UPLOAD_PROJECT\" where \"NUM\"=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo.setNum(rs.getInt("NUM"));
				vo.setFileName(rs.getString("FILENAME"));
				vo.setContentType(rs.getString("CONTENTTYPE"));
				vo.setFileSize(rs.getLong("FILESIZE"));
			}
		}catch (Exception e) {
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
	
	// 수정할 게시글 가져오기
		public BoardVo getUpdatePost(int num) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			BoardVo postVo = new BoardVo();
			
			try {
				conn = Connutil.getConnection();
				String query = "select * from \"BOARD_PROJECT\" where \"NUM\" = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					postVo.setNum(rs.getInt("NUM"));
					postVo.setWriter(rs.getString("WRITER"));
					postVo.setSubject(rs.getString("SUBJECT"));
					postVo.setReadcount(rs.getInt("READCOUNT"));
					postVo.setRef(rs.getInt("REF"));
					postVo.setStep(rs.getInt("STEP"));
					postVo.setDepth(rs.getInt("DEPTH"));
					postVo.setRegdate(rs.getTimestamp("REGDATE"));
					postVo.setContent(rs.getString("CONTENT"));
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
			return postVo;
		}
		
		// 게시글 수정 처리
		public int updatePosting(BoardVo postVo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			try {
				conn = Connutil.getConnection();
				String query = "update \"BOARD_PROJECT\" set \"SUBJECT\" = ?, \"CONTENT\" = ? where \"NUM\" = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, postVo.getSubject());
				pstmt.setString(2, postVo.getContent());
				pstmt.setInt(3, postVo.getNum());
				result = pstmt.executeUpdate();
				System.out.println(result + "개의 게시글이 수정되었습니다");
			}catch (Exception e) {
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
		
		// 업로드파일 수정
		public int updateUploading(BoardUploadVo upVo) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			try {
				conn = Connutil.getConnection();
				String query = "update  \"UPLOAD_PROJECT\" set \"FILENAME\" = ?, \"CONTENTTYPE\" = ?, \"FILESIZE\" = ? where \"NUM\"=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, upVo.getFileName());
				pstmt.setString(2, upVo.getContentType());
				pstmt.setLong(3, upVo.getFileSize());
				pstmt.setInt(4, upVo.getNum());
				result = pstmt.executeUpdate();
				System.out.println(result + "개의 업로드 파일이 수정되었습니다");
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
		
		// 게시글 삭제
		public int deletePost(int num) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			try {
				conn = Connutil.getConnection();
				String query = "delete from \"BOARD_PROJECT\" where \"NUM\" = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
				System.out.println(result + "개의 게시글이 삭제되었습니다");
			}catch (Exception e) {
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
		// 업로드 파일 삭제
		public int deleteUpload(int num) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			int result = 0;
			
			try {
				conn = Connutil.getConnection();
				String query = "delete from \"UPLOAD_PROJECT\" where \"NUM\" = ?";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
				System.out.println(result + "개의 업로드파일이 삭제되었습니다");
			}catch (Exception e) {
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
