<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.UserInfoDto"      %>

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
  <style>
    img {
      padding: 10px;
      height: 250px;
    }

    .information {
      text-align: left;
      margin-left: 30%;
    }
  </style>
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
    <form action="<%=request.getContextPath()%>/ExecuteSelectPicture" enctype="multipart/form-data" method="post">
      <div class="item1">
        <p>＜ユーザー画面で表示する画像を3枚まで登録できます。＞</p>
        <div class="information">
          <p>・対応している拡張子は .jpeg、.jpg、.png、.tiff、.bmp、.gif です。<br>
          ・登録できる合計容量は 3MB までです。<br>
          ・4枚以上選択しても、プレビューされるのは 3枚 までです。</p>
        </div>
        <input type="file" id="example" name="SELECT_PICTURES" accept=".jpeg, .jpg, .png, .tiff, .bmp, .gif" multiple>
        <div id="preview"></div>
        <input type="submit" value="画像登録" id="PICTURE_SUBMIT" >
      </div>
    </form>
  </section><%-- /#menu1.menus --%>
  <script type="text/javascript" src="js/preview-file.js"></script>
</body>
</html>
