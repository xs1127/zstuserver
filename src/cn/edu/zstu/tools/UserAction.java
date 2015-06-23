package cn.edu.zstu.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

import cn.edu.zstu.db.DB;
import cn.edu.zstu.db.DBQuerySql;
import cn.edu.zstu.db.DbHelp;

import net.sf.json.JSONObject;

/**
 * @author sjtu
 * 
 */
public class UserAction extends HttpServlet {

	private String message, newmessage;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private int postid;
	private ResultSet resultSet;
	private DBQuerySql querySql;
	private JSONObject responseJsonObject;
	private String responseString;
	private DB db;

	/**
	 * Constructor of the object.
	 */
	public UserAction() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		/**
		 * 注意要设置为utf-8 不然会出现乱码
		 * 
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(request
				.getInputStream(), "UTF-8"));
		message = br.readLine();
		System.out.println(message);
		JSONObject object = JSONObject.fromObject(message);
		String type = object.getString("type");
		if (type.equals("login")) {
			String userName = object.getString("username");
			String passwd = object.getString("passwd");
			String sql = "select * from users where username = '" + userName
					+ "' and passwd = '" + passwd + "'";
			querySql = new DBQuerySql();
			try {
				resultSet = querySql.executeSQL(sql);
				responseJsonObject = new JSONObject();
				if (resultSet.next()) {
					responseJsonObject.put("responseCode", "200");
					responseJsonObject.put("userName", resultSet
							.getString("username"));
					responseJsonObject.put("userId", resultSet
							.getString("userid"));
					String responseString = String.valueOf(responseJsonObject);
					out.write(responseString);
				} else {
					responseJsonObject.put("responseCode", "500");
				}
				responseString = String.valueOf(responseJsonObject);
				out.write(responseString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (type.equals("register")) {

		} else if (type.equals("detail")) {
			String userid = object.getString("userid");
			String postid = object.getString("postid");
			String sql = "select username,gender,institute,phone,posttime,state from users a,postitem b where a.userid = '"
					+ userid + "' and b.postid = '" + postid + "'";
			querySql = new DBQuerySql();
			try {
				resultSet = querySql.executeSQL(sql);
				responseJsonObject = new JSONObject();
				if (resultSet.next()) {
					responseJsonObject.put("responseCode", "200");
					responseJsonObject.put("type", "detail");
					responseJsonObject.put("username", resultSet
							.getString("username"));
					responseJsonObject.put("gender", resultSet
							.getString("gender"));
					responseJsonObject.put("institute", resultSet
							.getString("institute"));
					responseJsonObject.put("phone", resultSet
							.getString("phone"));
					String temp = resultSet.getString("posttime");
					String posttime = stringFormate(temp);
					responseJsonObject.put("posttime", posttime);
					responseJsonObject.put("state", resultSet.getInt("state"));

					String responseString = String.valueOf(responseJsonObject);
					out.write(responseString);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (type.equals("buy")) {
			String purchasetime = dateFormat.format(System.currentTimeMillis());
			String userid = object.getString("userid");
			String postid = object.getString("postid");
			String customerid = object.getString("customerid");
			String sql = "select username,gender,institute,phone,posttime,state from users a,postitem b where a.userid = '"
					+ userid
					+ "' and b.postid = '"
					+ postid
					+ "' and b.state = '0'";
			querySql = new DBQuerySql();
			try {
				resultSet = querySql.executeSQL(sql);
				responseJsonObject = new JSONObject();
				if (resultSet.next()) {
					responseJsonObject.put("responseCode", "200");
					responseJsonObject.put("type", "buy");
					String updateSql = "update postitem set state = '1',customerid = '"
							+ customerid
							+ "',purchasetime = '"
							+ purchasetime
							+ "' where userid ='"
							+ userid
							+ "' and postid ='"
							+ postid + "'";
					DBQuerySql dbQuerySql = new DBQuerySql();
					dbQuerySql.executeSaveOfUpdateSQL(updateSql);

				} else {
					responseJsonObject.put("responseCode", "500");
					responseJsonObject.put("type", "detail");
				}
				String responseString = String.valueOf(responseJsonObject);
				out.write(responseString);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (type.equals("delete")) {
			int postid = object.getInt("currentIndex");
			String sql = "delete postitem where postid = '" + postid + "'";
			db = new DB();
			responseJsonObject = new JSONObject();
			try {
				int count = db.runUpdateSql(sql);
				responseJsonObject.put("responseCode", 200);
				responseJsonObject.put("type", "delete");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				responseJsonObject.put("responseCode", 400);
			}
			String responseString = String.valueOf(responseJsonObject);
			out.write(responseString);
		} else if (type.equals("modify")) {
			postid = object.getInt("postid");
			newmessage = object.getString("newmessage");
			String sql = "update postitem set message = '" + newmessage
					+ "' where postid ='" + postid + "'";
			db = new DB();
			responseJsonObject = new JSONObject();
			try {
				int count = db.runUpdateSql(sql);
				if (count > 0) {
					responseJsonObject.put("responseCode", 200);
					responseJsonObject.put("type", "delete");
				} else {

				}
				out.write(String.valueOf(responseJsonObject));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				responseJsonObject.put("responseCode", 400);
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	public String stringFormate(String string) {
		StringBuffer sb = new StringBuffer();
		sb.append(string.substring(0, 4));
		sb.append("/");
		sb.append(string.substring(4, 6));
		sb.append("/");
		sb.append(string.substring(6, 8));
		return sb.toString();
	}
}
