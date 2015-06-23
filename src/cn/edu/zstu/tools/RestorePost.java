package cn.edu.zstu.tools;

import java.util.ArrayList;

import cn.edu.zstu.db.DbHelp;

public class RestorePost {

	private ArrayList<String> list;
	private ArrayList<Object> params;

	public RestorePost(ArrayList<String> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
		Init();
	}

	private void Init() {
		// TODO Auto-generated method stub
		params = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if (i < 4) {
				params.add((Object) list.get(i));
			} else {
				sb.append(list.get(i));
				sb.append('#');
			}

		}
		sb.deleteCharAt(sb.length() - 1);
		params.add(sb.toString());

	}

	public boolean restorePost() {
		boolean flag = false;
		String sql = "insert into postitem(posttime,userid,message,category,picth)values(?,?,?,?,?)";
		flag = DbHelp.updateSql(sql, params);
		return flag;
	}
}
