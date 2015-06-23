package cn.edu.zstu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB {

	private final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	private final String URL = "jdbc:sqlserver://10.10.50.68:1433;DataBaseName=inzstu";
	private final String URL = "jdbc:sqlserver://192.168.126.1:1433;DataBaseName=inzstu";
	private Statement statement;
	private PreparedStatement ps;
	private Connection con;
	private ResultSet res;

	public Connection getConnection() {
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, "sa", "sql2008");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	// 运行有结果.无参数的SQL语句
	public ResultSet runSelectSql(String sql) {
		try {
			con = getConnection();
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// 运行有结果.有参数的SQL语句 可滚动
	public ResultSet runSelectSql(String sql, Object[] params) {
		try {
			con = getConnection();
			// ps = con.prepareStatement(sql);
			ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 运行无结果.无参数的SQL语句
	 * 
	 * @return SQL语句执行状态 -1执行失败 >0执行成功
	 * */
	public int runUpdateSql(String sql) throws SQLException {
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
		}

	}

	// 运行无结果.有参数的SQL语句
	public boolean updateSql(String sql, ArrayList<Object> params) {
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
		}
	}

	// 运行无结果.有参数的SQL语句
	public boolean runUpdateSql(String sql, Object[] params) {
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
		}
	}
}
