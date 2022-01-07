<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
ログアウト画面
--%>

<html>
<head>
  <title>ログアウト画面</title>
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
    <h2 class="title">LOGOUT</h2>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <div class="login-space">
    <section class="user">
      <p>ログアウトしました。</p>
      <br>
      <a href="<%=request.getContextPath()%>/Home">HOMEに戻る</a>
    </section>
  </div>
</body>
</html>
