package cn.edu.zstu.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class GetdataAction extends HttpServlet {

	private GetData getData;
	private int currentIndex = 0;
	private int groupPos;
	private int childPos;
	private String category;
	private String userid;
	private String responseString;
	private String message;
	private String type;

	/**
	 * Constructor of the object.
	 */
	public GetdataAction() {
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
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),
				"UTF-8"));
		message = br.readLine();
		System.out.println("requestMessage:" + message);
		JSONObject object = JSONObject.fromObject(message);
		type = object.getString("type");
		currentIndex = object.getInt("currentIndex");
		if (type.equals("new")) {
			getData = new GetData(currentIndex);
			responseString = getData.getData();
		} else if (type.equals("filter")) {
//			groupPos = object.getInt("groupPos");
//			childPos = object.getInt("childPos");
			category = object.getString("category");
			getData = new GetData(currentIndex,category);
			responseString = getData.getFilterData();
		} else if (type.equals("manage")){
			userid = object.getString("category");
			getData = new GetData(userid);
			responseString = getData.getManageData();
		} else if (type.equals("purchase")){   //购买记录
			userid = object.getString("category");
			getData = new GetData(userid);
			responseString = getData.getPurchaseData();
		}
		out.write(responseString);
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
	}
}
