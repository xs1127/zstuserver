package cn.edu.zstu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

/**
 * 封装数据库操作
 * @author sjtu
 *
 */
public class DbHelp {
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String URL = "jdbc:sqlserver://10.10.50.68:1433;DataBaseName=inzstu";
//	private static final String URL = "jdbc:sqlserver://192.168.126.1:1433;DataBaseName=inzstu";
	private static Statement statement;

	/**
	 * 注册驱动 获得connection对象
	 * @return
	 */
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, "sa", "sql2008");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// 运行有结果.无参数的SQL语句
	public static ResultSet runSelectSql(String sql) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res = null;
//		Result result = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			res = ps.executeQuery();
//			result = ResultSupport.toResult(res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				res.close();
//				ps.close();
//				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	
	
	// 运行有结果.有参数的SQL语句
	public static ResultSet runSelectSql(String sql, Object[] params) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			res = ps.executeQuery();
//			result = ResultSupport.toResult(res);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
//				ps.close();
//				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	/**
	 * 运行无结果.无参数的SQL语句
	 * 
	 * @return SQL语句执行状态 -1执行失败 >0执行成功
	 * */
	public static int runUpdateSql(String sql) throws SQLException {
		Connection con = null;
		// PreparedStatement ps=null;
		int i;
		try {
			con = getConnection();
			// ps=con.prepareStatement(sql);
			// ps.executeQuery();
			statement = con.createStatement();
			i = statement.executeUpdate(sql);
			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
//				statement.close();
//				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 运行无结果.有参数的SQL语句
	public static boolean updateSql(String sql, ArrayList<Object> params) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setObject(i + 1, params.get(i));
			}
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
//				ps.close();
//				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// 运行无结果.有参数的SQL语句
	public static boolean runUpdateSql(String sql, Object[] params) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
//				ps.close();
//				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
