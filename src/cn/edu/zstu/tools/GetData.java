package cn.edu.zstu.tools;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import cn.edu.zstu.db.DB;

public class GetData {

	private String jsonString; // 用于返回字符串
	private int currentIndex;
	// private String category;
	private ResultSet rs;
	private Object[] params = new Object[1];
	// private Object[] filter = new Object[2];
	private Object[] filter = new Object[1];
	private JSONObject jsonObject;
	private String userId;
	private String userid;
	private DB db;

	public GetData(int currentIndex) {
		// TODO Auto-generated constructor stub
		this.currentIndex = currentIndex;
		this.params[0] = currentIndex;
	}

	public GetData(int currentIndex, String category) {
		this.currentIndex = currentIndex;
		filter[0] = category;
	}

	public GetData(String userid) {
		this.userid = userid;
	}

	public String getData() {
		jsonObject = new JSONObject();
		jsonObject.put("responseCode", 200);
		String sql = "select * from postitem where postid > ? order by postid desc";
		db = new DB();
		rs = db.runSelectSql(sql, params);
		try {
			if (rs.next()) {
				jsonObject.put("hasMore", true);
				jsonObject.put("currentIndex", rs.getObject(1));
				rs.beforeFirst();
				JSONArray list = new JSONArray();
				while (rs.next()) {
					userId = rs.getString("userid");
					String sqlString = "select * from users where userid = '"
							+ userId + "'";
					// System.out.println("sqlString" + sqlString);
					// ResultSet resultSet = DbHelp.runSelectSql(sqlString);
					ResultSet resultSet = db.runSelectSql(sqlString);
					resultSet.next();
					JSONObject item = new JSONObject();
					item.put("user_pho", userId + "/"
							+ resultSet.getString("userpicth"));
					item.put("user_name", resultSet.getString("username"));
					item.put("user_message", rs.getString("message"));

					/**
					 * 下面两个隐藏域 用于以后查找时使用
					 */
					item.put("user_id", userId);
					item.put("post_id", rs.getString("postid"));

					JSONArray user_post = new JSONArray();
					// JSONObject post_item = new JSONObject();
					String[] strings = splitPicth(rs.getString("picth"));
					for (int i = 0; i < strings.length; i++) {
						user_post.add(i, userId + "/" + strings[i]);
					}
					item.put("user_post", user_post);
					list.add(item);
				}
				jsonObject.put("list", list);
			} else {
				jsonObject.put("hasMore", false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		jsonString = jsonObject.toString();
		System.out.println("--jsonString--" + jsonString);
		return jsonString;
	}

	public String getManageData() {
		jsonObject = new JSONObject();
		jsonObject.put("responseCode", 200);
		String sql = "select * from postitem where userid = '" + userid + "'";
		db = new DB();
		rs = db.runSelectSql(sql);
		try {
			if (rs.next()) {
				jsonObject.put("flag", true);
				rs.beforeFirst();
				JSONArray list = new JSONArray();
				while (rs.next()) {
					JSONObject item = new JSONObject();
					item.put("postid", rs.getInt("postid"));
					item.put("date", stringFormate(rs.getString("posttime")));
					item.put("message", rs.getString("message"));
					item.put("state", rs.getInt("state"));
					JSONArray user_post = new JSONArray();
					// JSONObject post_item = new JSONObject();
					String[] strings = splitPicth(rs.getString("picth"));
					for (int i = 0; i < strings.length; i++) {
						user_post.add(i, userid + "/" + strings[i]);
					}
					item.put("pics", user_post);
					list.add(item);
				}
				jsonObject.put("list", list);
			} else {
				jsonObject.put("flag", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonString = jsonObject.toString();
		System.out.println("manage:" + jsonString);
		return jsonString;
	}

	public String getPurchaseData() {
		jsonObject = new JSONObject();
		String sql = "select a.username,a.phone,b.purchasetime,c.categoryname,b.message from users a,postitem b,category c "
				+ "where b.customerid ='"
				+ userid
				+ "' and b.state = '1' and a.userid = b.userid and b.category = c.CategoryID";
		db = new DB();
		rs = db.runSelectSql(sql);
		jsonObject.put("responseCode", 200);
		try {
			if(rs.next()){
				jsonObject.put("flag", true);
				rs.beforeFirst();
				JSONArray list = new JSONArray();
				while (rs.next()) {
					JSONObject item = new JSONObject();
					item.put("username", rs.getString("username"));
					item.put("phone", rs.getString("phone"));
					item.put("date", stringFormate(rs.getString("purchasetime")));
					item.put("category", rs.getString("categoryname"));
					item.put("message", rs.getString("message"));
					list.add(item);
				}
				jsonObject.put("list", list);
			}else {
				jsonObject.put("flag", false);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jsonString = jsonObject.toString();
		System.out.println("purchasehistory:" + jsonString);
		return jsonString;
	}

	public String getFilterData() {
		jsonObject = new JSONObject();
		jsonObject.put("responseCode", 200);
		String sql = "select * from postitem where category = ?";
		// String sql = "select * from postitem where message like %"+product;
		db = new DB();
		rs = db.runSelectSql(sql, filter);
		try {

			if (rs.next()) {
				jsonObject.put("hasMore", true);
				jsonObject.put("currentIndex", 0);
				rs.beforeFirst();
				JSONArray list = new JSONArray();
				while (rs.next()) {
					userId = rs.getString("userid");
					String sqlString = "select * from users where userid = '"
							+ userId + "'";
					// System.out.println("sqlString" + sqlString);
					// ResultSet resultSet = DbHelp.runSelectSql(sqlString);
					ResultSet resultSet = db.runSelectSql(sqlString);
					resultSet.next();
					JSONObject item = new JSONObject();
					item.put("user_pho", userId + "/"
							+ resultSet.getString("userpicth"));
					item.put("user_name", resultSet.getString("username"));
					item.put("user_message", rs.getString("message"));

					item.put("user_id", userId);
					item.put("post_id", rs.getString("postid"));

					JSONArray user_post = new JSONArray();
					// JSONObject post_item = new JSONObject();
					String[] strings = splitPicth(rs.getString("picth"));
					for (int i = 0; i < strings.length; i++) {
						user_post.add(i, userId + "/" + strings[i]);
					}
					item.put("user_post", user_post);
					list.add(item);
				}
				jsonObject.put("list", list);
			} else {
				jsonObject.put("hasMore", false);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		jsonString = jsonObject.toString();
		System.out.println("--filterString--" + jsonString);
		return jsonString;
	}

	public String[] splitPicth(String picth) {
		String[] list = picth.split("#");
		return list;
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
