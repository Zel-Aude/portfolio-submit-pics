package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserInfoDto;


public class SelectPicture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SelectPicture() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションからユーザーデータを取得
		HttpSession session            = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("USER_INFO");

		String forwardDestination = "";

		if (userInfoOnSession != null) {
			//ログイン済
			forwardDestination = "/WEB-INF/view/select_picture.jsp";

		} else {
			//未ログイン
			forwardDestination = "/WEB-INF/view/user_login.jsp";

		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
