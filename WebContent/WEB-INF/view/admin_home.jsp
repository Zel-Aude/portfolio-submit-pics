<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.ShowUserBusinessLogic" %>
<%@ page import="model.UserInfoDto"           %>
<%@ page import="model.HandlePageSessionBean" %>
<%@ page import="model.HandlePageBean"        %>
<%@ page import="java.util.List"              %>

<%--
管理者画面
--%>

<%
//セッションでのページング処理
HandlePageSessionBean pageBean = (HandlePageSessionBean)session.getAttribute("PAGE_BEAN");

List<UserInfoDto> userRecordList = pageBean.getUserRecordList();
List<String>      paginationList = pageBean.getPaginationList();
int               maxPageRecords = pageBean.getMaxPageRecords();
%>

<html>
<head>
  <title>管理者画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
  <link rel="stylesheet" type="text/css" href="css/admin-style.css">
</head>
<body>
  <header>
    <h2 class="title">ADMINISTRATOR</h2>
    <div class="submit-btn">
      <form action="<%=request.getContextPath()%>/ExecuteAdminLogout" method="get">
        <input type="submit" value="ログアウト" id="LOGOUT_SUBMIT" >
      </form>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <section id="menu1" class="menus menu1">
<%
	if(!userRecordList.isEmpty()) {
//ユーザーデータがある場合

	ShowUserBusinessLogic logic = new ShowUserBusinessLogic();
	StringBuilder         sb    = new StringBuilder();
%>
    <form name="DELETE_CHECK" action="<%=request.getContextPath()%>/ExecuteUserDelete" method="post">
      <table class="user-list">
        <tr>
          <th>ID</th>
          <th id="user-name">名前</th>
          <th>登録日時</th>
          <th>削除</th>
        </tr>
<%
	//任意のユーザーデータ範囲を表示するhtml文を順次出力
	for(int i = 0 ; i < maxPageRecords ; i++) {

		if(i > userRecordList.size() - 1) {
			break;
		}

		UserInfoDto dto = userRecordList.get(i);

		String userData = logic.createAllUserDisplay(dto,sb);
%>
        <tr>
          <%= userData %>
          <td><label>
            <input type="checkbox" name="DELETE_TARGETS" value="<%= dto.getUserId() %>" id="<%= dto.getUserId() %>">
            選択する</label>
          </td>
        </tr>
<%
		sb.delete(0, sb.length());
	}
%>
      </table>
      <div class="delete-btn">
        <input type="submit" value="選択削除" id="DELETE_SUBMIT">
        <input type="submit" name="ALL_CLEAR" value="全件削除" id="ALL_CLEAR_SUBMIT">
      </div>
    </form>
    <script type="text/javascript" src="js/checkbox-check.js"></script>
    <script type="text/javascript" src="js/allclear-check.js"></script>
<%
	if(!paginationList.isEmpty()) {
%>
    <form name="PAGE_TRANSITION" action="<%=request.getContextPath()%>/ExecuteAdminLogin" method="post">
      <input type="hidden" id="SELECT_PAGE" name="SELECT_PAGE">
      <ul class="page-list">
<%
		for(String pr : paginationList) {
%>
	    <%= pr %>
<%
		}
%>
      </ul>
    </form>
    <script type="text/javascript" src="js/paging-process.js"></script>
<%
	}

} else {
//ユーザーデータが無い場合
%>
    <div class="no-data">
      <p>ユーザーデータがありません</p>
    </div>
<%
}
%>
  </section><%-- /#menu1.menus --%>
  <script type="text/javascript" src="js/smooth-scroll.js"></script>
</body>
</html>
