<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="model.UserInfoDto" %>
<%@ page import="java.util.List"    %>

<%--
ユーザー画面
--%>

<%
//セッションからdtoを取得
UserInfoDto dto = (UserInfoDto)session.getAttribute("USER_INFO");

//登録画像データ格納リストをリクエストスコープから取得
@SuppressWarnings("unchecked")
List<String> extractedPicturesList = (List<String>)request.getAttribute("PICTURES_LIST");
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
    <nav class="nav">
      <ul class="nav-list">
        <li><a href="#menu1">Item1</a></li>
        <li><a href="#menu2">Item2</a></li>
        <li><a href="#menu3">Item3</a></li>
      </ul><%-- /.nav-list --%>
    </nav><%-- /.nav --%>
    <div class="submit-btn">
      <a href="<%=request.getContextPath()%>/SelectPicture">画像選択画面へ</a>
      <form action="<%=request.getContextPath()%>/ExecuteUserLogout" method="get">
        <input type="submit" value="ログアウト" id="LOGOUT_SUBMIT" >
      </form>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <section id="menu1" class="menus menu1">
    <div class="item1">
      <img src="<%= extractedPicturesList.get(0) %>" alt="登録画像1">
    </div>
  </section><%-- /#menu1.menus --%>
  <section id="menu2" class="menus menu2">
    <div class="item2">
      <img src="<%= extractedPicturesList.get(1) %>" alt="登録画像2">
    </div>
  </section><%-- /#menu2.menus --%>
  <section id="menu3" class="menus menu3">
    <div class="item3">
      <img src="<%= extractedPicturesList.get(2) %>" alt="登録画像3">
    </div>
  </section><%-- /#menu3.menus --%>
  <footer>
    <p>There are no facts, only interpretations.</p>
  </footer>
  <div id="js-scroll-top" class="scroll-top">TOP</div>
  <script type="text/javascript" src="js/smooth-scroll.js"></script>
  <script type="text/javascript" src="js/scroll-top.js"></script>
</body>
</html>
