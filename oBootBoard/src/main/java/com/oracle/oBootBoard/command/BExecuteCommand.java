package com.oracle.oBootBoard.command;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.oracle.oBootBoard.dao.BDao;
import com.oracle.oBootBoard.dto.BDto;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BExecuteCommand {
	private final BDao jdbcDao;
	public BExecuteCommand(BDao jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public void bListCmd(Model model) {
		// Dao 연결
		ArrayList<BDto> boardDtoList = jdbcDao.boardList();
		System.out.println("BListCommand boardDtoList.size()-->"+boardDtoList.size());
		model.addAttribute("boardList", boardDtoList);
	}

	public void bWriteCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bName  ,bTitle  , bContent  추출
//		  3) dao  instance 선언
//		  4) write method 이용하여 저장(bName, bTitle, bContent)
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		jdbcDao.write(bName, bTitle, bContent);
		
	}

	public void bContentCmd(Model model) {
		// 1. model1를 map으로 전환
		// 2. request -> bId get
		// 3. HW3
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		System.out.println("bContentCmd bid->"+bId);
		BDto board = jdbcDao.contentView(bId,true);
		System.out.println("bContentCmd board.getbName"+board.getbName());
		model.addAttribute("mvc_board", board);
	}

	public void bModifyCmd(Model model) {
		// 1. model Map선언
		// 2. parameter ->  bId, bName , bTitle , bContent
		Map<String,Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		System.out.println("bModifyCmd bid->"+bId);
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		jdbcDao.modify(bId,bName,bTitle,bContent);
	}

	public void bReplyViewCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 ->  bid  추출
//		  3) reply_view method 이용하여 (bid)
//		    - BDto dto = dao.reply_view(bId);
		System.out.println("bReplyViewCmd start...");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		BDto dto = jdbcDao.reply_view(bId);
		model.addAttribute("reply_view", dto);
	}

	public void bReplyCmd(Model model) {
//		  1)  model이용 , map 선언
//		  2) request 이용 -> bid,         bName ,  bTitle,
//		                    bContent ,  bGroup , bStep ,
//		                    bIndent 추출
//		  3) reply method 이용하여 댓글저장 
//		    - dao.reply(bId, bName, bTitle, bContent, bGroup, bStep, bIndent);
//		    [1] bId SEQUENCE = bGroup 
//		    [2] bName, bTitle, bContent -> request Value
//		    [3] 홍해 기적
//		    [4] bStep / bIndent   + 1
		System.out.println("bReplyCmd");
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
		System.out.println("bReplyCmd bTitle->"+bTitle);
		jdbcDao.reply(bId,bName,bTitle,bContent,bGroup,bStep,bIndent);
	}

	public void bDeleteCmd(Model model) {
		// 	 1)  model이용 , map 선언
	    //	 2) request 이용 ->  bId 추출
	    //	 3)  delete method 이용하여 삭제
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("request");
		int bId = Integer.parseInt(request.getParameter("bId"));
		jdbcDao.delete(bId);
	}
}
