package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AdminInfoDto;


public class ExecuteAdminLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteAdminLogout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//セッションから管理者データを取得
		HttpSession  session             = request.getSession();
		AdminInfoDto AdminInfoOnSession = (AdminInfoDto)session.getAttribute("ADMIN_INFO");

		//フォワード先指定用変数
		String forwardDestination = "";

		//ログイン状態によって表示画面を振り分ける
		if (AdminInfoOnSession != null) {
			//ログイン済

			//ログアウトに伴いセッション情報を破棄
			session.invalidate();
			//beanのフィールドを初期化
//			HandlePageBean.getInstance().resetBeanField();

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
