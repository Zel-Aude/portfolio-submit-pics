<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.UserInfoDto" %>

<%--
登録完了画面
--%>

<%
//セッションからdtoを取得
UserInfoDto dto = (UserInfoDto)session.getAttribute("USER_INFO");
%>

<html>
<head>
  <title>登録完了画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
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
    <h2 class="title">COMPLETED_REGISTRATION</h2>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <div class="login-space">
    <section class="user">
      <p>登録完了しました。</p>
      <p><%= dto.getUserName() %> さんのIDは <%= dto.getUserId() %> です。<br>
      次回以降のログイン時にご使用ください。</p>
      <a href="<%=request.getContextPath()%>/ExecuteUserLogin">ユーザー画面へ</a>
    </section>
  </div>

</body>
</html>
