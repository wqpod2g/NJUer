package nju.iip.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nju.iip.dao.impl.UserDaoImpl;
import nju.iip.dto.NJUerUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用户注册
 * @author wangqiang
 *
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(Register.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		logger.info("username="+username+"  password="+password);
		PrintWriter out = response.getWriter();
		UserDaoImpl UDI = new UserDaoImpl();
		if(!UDI.checkUserName(username)) {
			out.write("用户名不存在");
		}
		else if(!UDI.checkPassword(username, password)) {
			out.write("密码错误");
		}
		else {
			out.write("ok");
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		logger.info("username="+username+"  password="+password+" email="+email);
		PrintWriter out = response.getWriter();
		UserDaoImpl UDI = new UserDaoImpl();
		if(UDI.checkUserName(username)) {
			out.write("用户名已经存在");
		}
		else {
			NJUerUser user = new NJUerUser();
			user.setPassword(password);
			user.setUsername(username);
			user.setEmail(email);
			if(UDI.addUser(user)) {
				out.write("ok");
			}
			else {
				out.write("failed");
			}
		}
		out.flush();
		out.close();
	}

}
