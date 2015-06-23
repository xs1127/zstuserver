package cn.edu.zstu.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.zstu.tools.RestorePost;

public class UploadAction extends HttpServlet {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private JSONObject object;

	/**
	 * Constructor of the object.
	 */
	public UploadAction() {
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

	/**
	 * list中数据顺序 0: picName 时间的字符串形式 1: userId id 2: message 发布的信息文字  3:Category
	 * 产品类别 4-> 图片名称
	 * 
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int count = 0;
		object = new JSONObject();
		ArrayList<String> list = new ArrayList<String>();
		String picName = dateFormat.format(System.currentTimeMillis());
		list.add(picName);
		System.out.println("开始上传文件...");
		System.out.println("服务器路径：" + this.getServletContext().getRealPath("/"));
		response.setContentType("text/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String fileName = null;
		InputStream inputStream = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(30 * 1024 * 1024);
			upload.setFileSizeMax(10 * 1024 * 1024);
			List<FileItem> fileItems = upload.parseRequest(request);
			String upload_path = this.getServletContext().getRealPath("/") + "uploadfile\\";
			for (FileItem item : fileItems) {
				if (item.isFormField()) {
					System.out.println("--表单文件--" + item.getFieldName());
					System.out.println(item.getString("UTF-8"));
					list.add(item.getString("UTF-8"));
					/**
					 * getFieldName 得到字段name属性的值 getName
					 * 得到file字段的文件名全路径名，如果不是file字段，为null getString("utf-8")
					 * 得到字段的值
					 */
				} else if (item.getName() != null && !item.getName().equals("")) {
					count++;
					System.out.println("--图片文件--" + item.getName());
					fileName = item.getName().substring(item.getName().lastIndexOf("\\") + 1);
					inputStream = item.getInputStream();
					/**
					 * 根据用户id生成一个文件夹 如果不存在就先创建
					 */
					String temp = upload_path + list.get(1) + "\\";
					File file = new File(temp);
					if (!file.exists()) {
						file.mkdirs();
					}
					String restore = picName + "_" + count + ".jpg";
					fileName = upload_path + list.get(1) + "\\" + restore;
					System.out.println("--图片的完整路径--" + fileName);
					list.add(restore);
					item.write(new File(fileName));
				}
			}
			boolean flag = new RestorePost(list).restorePost();
			if (flag) {
				object.put("responseCode", "200");
			} else {
				object.put("responseCode", "500");
			}
			out.write((String.valueOf(object)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void init() throws ServletException {
		// Put your code here
	}

}
