package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ShowPictureBusinessLogic;
import model.UserInfoBusinessLogic;
import model.UserInfoDto;
import model.XssControl;


public class ExecuteUserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteUserLogin() {
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

		//セッションからユーザーデータを取得
		HttpSession session            = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("USER_INFO");

		//初期値：ログイン失敗
		String forwardDestination = "/WEB-INF/view/user_login.jsp";

		String userId = "";

		if(userInfoOnSession == null) {
			//未ログイン

			//リクエストパラメータからユーザー入力値を取得
			userId = request.getParameter("USER_ID");
			String passWord = request.getParameter("USER_PASSWORD");

			//「user_member」テーブルからユーザー入力値と合致するユーザーデータ（UserInfoDto型）を抽出
			// ※合致するデータがなかった場合、各フィールドがnullのDTOを得る
			UserInfoBusinessLogic userLogic = new UserInfoBusinessLogic();
			userInfoOnSession = userLogic.executeSelectUserInfo(userId, passWord);

			if(userInfoOnSession.getUserId() != null) {
				//ユーザーデータの抽出に成功
				String extractUserName = userInfoOnSession.getUserName();
				userInfoOnSession.setUserName(XssControl.replaceEscapeChar(extractUserName));

				//DBから抽出したユーザデータをセッションにセット
				session.setAttribute("USER_INFO", userInfoOnSession);
			}
		}

		if(userInfoOnSession.getUserId() != null) {
			//ログイン済またはログイン成功

			if(userId.isEmpty()) {
				userId = userInfoOnSession.getUserId();
			}

			ShowPictureBusinessLogic showPictureLogic = new ShowPictureBusinessLogic();
			List<String>             picturesList      = showPictureLogic.executeShowPicture(userId);

			//登録画像データをリクエストスコープに保存
			request.setAttribute("PICTURES_LIST", picturesList);

			forwardDestination = "/WEB-INF/view/user_home.jsp";
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}
}
