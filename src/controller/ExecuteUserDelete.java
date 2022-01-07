package controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AdminInfoDto;
import model.DeleteUserBusinessLogic;
import model.PathConfig;


public class ExecuteUserDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUserDelete() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//リクエスト（受信データ）の文字コードを設定
		request.setCharacterEncoding("UTF-8");

		//セッションから管理者データを取得
		HttpSession  session             = request.getSession();
		AdminInfoDto adminInfoOnSession = (AdminInfoDto)session.getAttribute("ADMIN_INFO");

		//フォワード先指定用変数
		String forwardDestination = "";

		if(adminInfoOnSession != null) {
			//ログイン済

			//リクエストパラメータを取得
			String[] deleteTargets         = request.getParameterValues("DELETE_TARGETS");
			String   allUserClearRequest  = request.getParameter("ALL_CLEAR");

			DeleteUserBusinessLogic logic      = new DeleteUserBusinessLogic();
			List<String>            targetList = new ArrayList<String>();

			boolean succesDeleteUser = false;
			boolean succesClearUser  = false;

			if(allUserClearRequest == null) {

				Collections.addAll(targetList,deleteTargets);
				succesDeleteUser = logic.executeDeleteUser(targetList);

			} else {
				succesClearUser = logic.executeClearUser();
			}

			if(succesDeleteUser || succesClearUser) {
				//DB操作に成功
				if(succesDeleteUser) {
					logic.deleteUserDirectory(targetList);

				} else if(succesClearUser) {

					Path clearTargetDir = PathConfig.PICTURES_DIR_PATH.getPath();
					logic.clearUserDirectory(clearTargetDir);
				}

				request.setAttribute("TARGET_LIST", targetList);

				forwardDestination = "/WEB-INF/view/user_delete.jsp";

			} else {
				//DB操作に失敗
				forwardDestination = "htmls/deleteError.html";

			}

		} else {
			//未ログイン
			forwardDestination = "/WEB-INF/view/user_login.jsp";

		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}
}
