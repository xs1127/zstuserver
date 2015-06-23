package cn.edu.zstu.db;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class DBQuerySql {
	private Connection con = null;
	private PreparedStatement pst = null;
	private Component res = null;
	private ResultSet rst = null;
	private Result re;

	/**
	 * 执行查询的SQL语句
	 * 
	 * @param SQL语句
	 * @return Result
	 */
	public ResultSet executeSQL(String SQL) throws SQLException {
		con = DbHelp.getConnection();
		pst = con.prepareStatement(SQL);
		rst = pst.executeQuery();
//		re = ResultSupport.toResult(rst);// 转化为Result
		return rst;
	}

	/**
	 * 执行添加或修改的SQL语句
	 * 
	 * @param SQL语句
	 */
	public int executeSaveOfUpdateSQL(String SQL) throws SQLException {
		int states = DbHelp.runUpdateSql(SQL);
		return states;
	}
}
