package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.AdminInfoBusinessLogic;
import model.AdminInfoDto;
import model.HandlePageSessionBean;
import model.ShowUserBusinessLogic;


public class ExecuteAdminLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteAdminLogin() {
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
		AdminInfoDto AdminInfoOnSession = (AdminInfoDto)session.getAttribute("ADMIN_INFO");

		//初期値：ログイン失敗
		String forwardDestination = "/WEB-INF/view/user_login.jsp";

		if(AdminInfoOnSession == null) {
			//未ログイン

			//リクエストパラメータからユーザー入力値を取得
			String adminId  = request.getParameter("ADMIN_ID");
			String passWord = request.getParameter("ADMIN_PASSWORD");

			//「admin_info」テーブルからユーザー入力値と合致する管理者データを抽出
			// ※合致するデータがなかった場合、各フィールドがnullのDTOを得る
			AdminInfoBusinessLogic logic = new AdminInfoBusinessLogic();
			AdminInfoOnSession = logic.executeSelectAdminInfo(adminId, passWord);

			if(AdminInfoOnSession.getAdminId() != null) {
				//管理者データの抽出に成功

				//DBから抽出した管理者データをセッションにセット
				session.setAttribute("ADMIN_INFO", AdminInfoOnSession);
			}
		}

		if(AdminInfoOnSession.getAdminId() != null) {
			//ログイン済またはログイン成功

			//セッションでのページング処理
			HandlePageSessionBean pageBean =
					(HandlePageSessionBean)session.getAttribute("PAGE_BEAN");

			if(pageBean == null) {

				pageBean = new HandlePageSessionBean();
				session.setAttribute("PAGE_BEAN", pageBean);
			}

			//シングルトンでのページング処理
//			HandlePageBean pageBean = HandlePageBean.getInstance();
			pageBean.setRequestLastPage(false);

			String pageNum = request.getParameter("SELECT_PAGE");

//			System.out.println("pageNum：" + pageNum); //pageNum値テスト
//			System.out.println("設定前：" + pageBean); //pageBean値テスト

			if(pageNum == null) {
				pageBean.setCurrentPage(1);

			} else {
				int pageNumInt = Integer.parseInt(pageNum);

				if(pageNumInt == 0) {
					pageBean.setRequestLastPage(true);

				} else if(pageNumInt > 0) {
					pageBean.setCurrentPage(pageNumInt);
				}
			}

			ShowUserBusinessLogic showLogic = new ShowUserBusinessLogic(pageBean);

			if(pageBean.isRequestLastPage()) {
				showLogic.showUserLastRange();

			} else {
				showLogic.showUserAnyRange();
			}

//			System.out.println("設定後：" + pageBean); //pageBean値テスト

			if(pageBean.getUserRecordList() != null) {

				showLogic.createPageLinkDisplay();

				forwardDestination = "/WEB-INF/view/admin_home.jsp";

			} else {
				forwardDestination = "htmls/showAllUserError.html";
			}
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}
}
