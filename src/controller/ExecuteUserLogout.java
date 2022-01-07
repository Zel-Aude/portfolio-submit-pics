package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserInfoDto;


public class ExecuteUserLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUserLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションからユーザーデータを取得
		HttpSession session            = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("USER_INFO");

		//フォワード先指定用変数
		String forwardDestination = "";

		if (userInfoOnSession != null) {
			//ログイン済

			//ログアウトに伴いセッション情報を破棄
			session.invalidate();

			forwardDestination = "/WEB-INF/view/user_logout.jsp";

		} else {
			//未ログイン
			forwardDestination = "/WEB-INF/view/home.jsp";
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
