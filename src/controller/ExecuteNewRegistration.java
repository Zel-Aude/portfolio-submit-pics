package controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CreateIdBusinessLogic;
import model.RegistrationUserBusinessLogic;
import model.UserInfoDto;
import model.XssControl;


public class ExecuteNewRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExecuteNewRegistration() {
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

		HttpSession session            = request.getSession();
		UserInfoDto userInfoOnSession = (UserInfoDto)session.getAttribute("USER_INFO");

		String forwardDestination = "";

		if(userInfoOnSession != null) {
			//重複登録防止
			forwardDestination = "/WEB-INF/view/completed_registration_user.jsp";

		} else {
			//「user_member」テーブルから最新のIDレコードを取得して、新規IDを作成する
			CreateIdBusinessLogic createIdLogic = new CreateIdBusinessLogic();
			String                userId         = createIdLogic.createId();

			if(userId.isEmpty()) {
				//ID数が上限値になった場合
				forwardDestination = "htmls/createIdError.html";

			} else {
				//新規IDを作成できた場合

				//リクエストパラメータからユーザー入力値を取得
				String userName = request.getParameter("USER_NAME");
				String passWord = request.getParameter("USER_PASSWORD");

				userInfoOnSession = new UserInfoDto();
				userInfoOnSession.setUserId(   userId   );
				userInfoOnSession.setUserName( userName );
				userInfoOnSession.setPassWord( passWord );
				userInfoOnSession.setTime( new Timestamp(System.currentTimeMillis()) );

				//「user_member」テーブルにユーザーデータを登録する
				RegistrationUserBusinessLogic registrationLogic =
						new RegistrationUserBusinessLogic();

				boolean succesRegistration =
						registrationLogic.executeRegistrationUser(userInfoOnSession);

				if(succesRegistration) {
					//ユーザーデータの登録に成功

					registrationLogic.createUserDirectory(userId);

					UserInfoDto sessionDto      = new UserInfoDto(userInfoOnSession);
					String      escapedUserName = XssControl.replaceEscapeChar(userName);
					sessionDto.setUserName(escapedUserName);

					//ユーザデータをセッションにセット
					session.setAttribute("USER_INFO", sessionDto);

					forwardDestination = "/WEB-INF/view/completed_registration_user.jsp";

				} else {
					//ユーザーデータの登録に失敗
					forwardDestination = "htmls/registrationError.html";//変更の余地有り

				}
			}
		}

		RequestDispatcher dispatch = request.getRequestDispatcher(forwardDestination);
		dispatch.forward(request, response);
	}
}
