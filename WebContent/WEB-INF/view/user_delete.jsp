<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="java.util.List" %>

<%--
ユーザー削除完了画面
--%>

<%
//削除ユーザーID格納リストをリクエストスコープから取得
@SuppressWarnings("unchecked")
List<String> targetList = (List<String>)request.getAttribute("TARGET_LIST");
%>

<html>
<head>
  <title>ユーザー削除完了画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
  <link rel="stylesheet" type="text/css" href="css/admin-style.css">
  <style>
    section {
      padding-top: 10px;
      margin: 0 auto;
      text-align: center;
    }
  </style>
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
  <div class="login-space">
    <section class="user">
<%
if(!targetList.isEmpty()) {
%>
      <p>選択したユーザーデータを削除しました。</p>
<%
} else {
//削除ユーザーIDが無い場合
%>
      <p>ユーザーデータを全件削除しました。</p>
<%
}
%>
      <br>
      <a href="<%=request.getContextPath()%>/ExecuteAdminLogin">管理者画面に戻る</a>
    </section>
  </div>
</body>
</html>
