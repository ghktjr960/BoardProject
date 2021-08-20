package board.model;

import java.util.List;

public class BoardService {
	private static BoardService instance = null;
	private BoardService() { }
	public static BoardService getInstance() {
		if (instance == null) {
			synchronized (BoardService.class) {
				instance = new BoardService();
			}
		}
		return instance;
	}
	private BoardDao dao = BoardDao.getInstance();
	
	// 전체 게시글 수 가져오기
	public int postCount() {
		return dao.getPostCount();
	}
	
	// 게시글 목록 가져오기
	public List<BoardVo> postList(int startNum, int endNum){
		return dao.getPostList(startNum, endNum);
	}
	
	// 게시글 등록
	public int posting(BoardVo vo) {
		return dao.insertPost(vo);
	}
	
	// 업로드 DB저장
	public int uploading(BoardUploadVo upVo) {
		return dao.insertUploadInfo(upVo);
	}

	// 게시글 가져오기
	public BoardVo postInfo(int num) {
		return dao.getPost(num);
	}
	
	// 업로드 가져오기
	public BoardUploadVo uploadInfo(int num) {
		return dao.getUpload(num);
	}
	
	// 수정할 게시글 가져오기
	public BoardVo getUpdatePostInfo(int num) {
		return dao.getUpdatePost(num);
	}
	
	// 게시글 수정
	public int updatePosting(BoardVo postVo) {
		return dao.updatePosting(postVo);
	}

	// 업로드 수정
	public int updateUploading(BoardUploadVo upVo) {
		return dao.updateUploading(upVo);
	}
	
	// 게시글 삭제
	public int deletePost(int num) {
		return dao.deletePost(num);
	}
	
	// 업로드 파일 삭제
	public int deleteUpload(int num) {
		return dao.deleteUpload(num);
	}
	
}
