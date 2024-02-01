package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;

public class JdbcDao implements BDao {

	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	@Override
	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("JdbcDao boardList start...");
		String sql = "select * from MVC_BOARD order by bGroup desc, bStep asc";
		try {
			connection = getConnection();
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bList.add(
						new BDto(
								rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)
								)
						);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(connection,pstmt,rs);
		}
		return bList;
	}

	private void close(Connection connection, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs!=null) rs.close();
			if(pstmt!=null) pstmt.close();
			if(connection!=null) close(connection);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private void close(Connection connection) {
		DataSourceUtils.releaseConnection(connection, dataSource);
	}

	@Override
	public void write(String bName, String bTitle, String bContent) {
		// 1. Insert Into mvc_board
		// 2. PrepareStatement
		// 3. mvc_board_seq
		// 4. bId , bGroup 같게
		// 5.  bStep, bIndent, bDate --> 0, 0 , sysdate
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into mvc_board values (mvc_board_seq.nextval,?,?,?,sysdate,0,mvc_board_seq.currval,0,0)";
//		String sql = "Insert into mvc_board (bid, bname, bTitle, bcontent, bhit, bgroup,bstep,bindent,bdate)"
//				+ "values (mvc_board_seq.nextval,?,?,?,0,mvc_board_seq.currval,0,0,sysdate";
				
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,pstmt,null);
		}
	}

	@Override
	public BDto contentView(int bId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from mvc_board where bId=?";
		BDto bDto = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bDto = new BDto(
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getTimestamp(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)
						);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,pstmt,rs);
		}
		
		return bDto;
	}

	@Override
	public void modify(int bId, String bName, String bTitle, String bContent) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update mvc_board set bName = ?, bTitle = ?, bContent = ? where bId = ?";
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bId);
			int result = pstmt.executeUpdate();
			System.out.println("JdbcDao modify result->"+ (result>0?"수정 성공":"수정 실패"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn,pstmt,null);
		}
	}
	
}
