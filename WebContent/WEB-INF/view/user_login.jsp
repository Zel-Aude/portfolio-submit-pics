<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
ログイン画面
--%>

<html>
<head>
  <title>ログイン画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
  <link rel="stylesheet" type="text/css" href="css/login-style.css">
</head>
<body>
  <header>
    <h2 class="title">LOGIN</h2>
    <div class="submit-btn">
      <a href="<%=request.getContextPath()%>/Home">HOMEに戻る</a>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <div class="login-space">
    <section class="user">
      <p>ユーザーの方</p>
      <br>
      <form action="<%=request.getContextPath()%>/ExecuteUserLogin" method="post">
        <p>ユーザーID：<br>
          <input type="text" name="USER_ID" maxlength="20" id="ID_USER_ID">
        </p>
        <p>パスワード：<br>
          <input type="password" name="USER_PASSWORD" maxlength="20" id="ID_USER_PASS">
        </p>
        <input type="submit" value="ログイン" id="USER_SUBMIT" >
      </form>
    </section>
    <section class="admin">
      <p>管理者の方</p>
      <br>
      <form action="<%=request.getContextPath()%>/ExecuteAdminLogin" method="post">
        <p>管理者ID：<br>
          <input type="text" name="ADMIN_ID" maxlength="20" id="ID_ADMIN_ID">
        </p>
        <p>パスワード：<br>
          <input type="password" name="ADMIN_PASSWORD" maxlength="20" id="ID_ADMIN_PASS">
        </p>
        <input type="submit" value="ログイン" id="ADMIN_SUBMIT" >
      </form>
    </section>
  </div>

  <script type="text/javascript" src="js/login-check.js"></script>

</body>
</html>
