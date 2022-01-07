<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
新規登録画面
--%>

<html>
<head>
  <title>新規登録画面</title>
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
    <h2 class="title">REGISTRATION</h2>
    <div class="submit-btn">
      <a href="<%=request.getContextPath()%>/Home">HOMEに戻る</a>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <div class="login-space">
    <section class="user">
      <p>新規登録</p>
      <br>
      <form action="<%=request.getContextPath()%>/ExecuteNewRegistration" method="post">
        <p>ユーザー名：
        <br>
          <input type="text" name="USER_NAME" maxlength="20" id="ID_USER_ID">
        </p>
        <p>パスワード：<br>
          <input type="password" name="USER_PASSWORD" maxlength="20" id="ID_USER_PASS">
        </p>
        <br>
        <p>パスワード確認用：<br>
          <input type="password" name="CHECK_PASSWORD" maxlength="20" id="ID_CHECK_PASS">
        </p>
        <input type="submit" value="登録" id="USER_SUBMIT" >
      </form>
    </section>
  </div>

  <script type="text/javascript" src="js/registration-check.js"></script>

</body>
</html>
