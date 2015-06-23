package cn.edu.zstu.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.org.apache.xml.internal.serializer.ElemDesc;

import cn.edu.zstu.tools.RestorePost;

import net.sf.json.JSONObject;

public class ChangeAvator extends HttpServlet {

	private JSONObject object;
	private String userid;
	private boolean flag = false;
	
	/**
	 * Constructor of the object.
	 */
	public ChangeAvator() {
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
		System.out.println("连接服务器成功，准备上传头像");
		object = new JSONObject();
		response.setContentType("text/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		InputStream inputStream = null;
		String fileName = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(30 * 1024 * 1024);
			upload.setFileSizeMax(10 * 1024 * 1024);
			List<FileItem> fileItems = upload.parseRequest(request);
			String upload_path = this.getServletContext().getRealPath("/")
					+ "uploadfile\\";
			for (FileItem item : fileItems) {
				if (item.isFormField()) {
					System.out.println("--表单文件--" + item.getFieldName());
					System.out.println(item.getString("UTF-8"));
					userid = (item.getString("UTF-8"));
					/**
					 * getFieldName 得到字段name属性的值 getName
					 * 得到file字段的文件名全路径名，如果不是file字段，为null getString("utf-8")
					 * 得到字段的值
					 */
				} else if (item.getName() != null && !item.getName().equals("")) {
					System.out.println("--图片文件--" + item.getName());
					inputStream = item.getInputStream();
					/**
					 * 根据用户id生成一个文件夹 如果不存在就先创建
					 */
					String temp = upload_path + userid + "\\";
					File file = new File(temp);
					if (!file.exists()) {
						file.mkdirs();
					}
					
					fileName = upload_path + userid + "\\" + "pho.jpg";
					System.out.println("--图片的完整路径--" + fileName);
					item.write(new File(fileName));
					flag = true;
				}
			}
			if(flag){
				object.put("responseCode", 200);
			}else {
				object.put("responseCode", 500);
			}
			out.write((String.valueOf(object)));
			System.out.println("头像上传成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

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

}
