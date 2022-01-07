<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.UserInfoDto" %>

<%--
ユーザー画面
--%>

<%
//セッションからdtoを取得
UserInfoDto dto = (UserInfoDto)session.getAttribute("USER_INFO");
%>

<html>
<head>
  <title>ユーザー画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
  <link rel="stylesheet" type="text/css" href="css/user-style.css">
</head>
<body>
  <header>
    <h2 class="title"><%= dto.getUserName() %></h2>
    <div class="submit-btn">
      <a href="<%=request.getContextPath()%>/ExecuteUserLogin">ユーザー画面へ</a>
      <form action="<%=request.getContextPath()%>/ExecuteUserLogout" method="get">
        <input type="submit" value="ログアウト" id="LOGOUT_SUBMIT" >
      </form>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <section id="menu1" class="menus menu1">
    <div class="item1">
      <p>選択した画像を登録しました。</p>
    </div>
  </section><%-- /#menu1.menus --%>
</body>
</html>
