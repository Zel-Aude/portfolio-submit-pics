<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%--
ホーム画面
--%>

<html>
<head>
  <title>ホーム画面</title>
  <link rel="stylesheet" type="text/css" href="css/basic-style.css">
</head>
<body>
  <header>
    <h2 class="title">HOME</h2>
    <nav class="nav">
      <ul class="nav-list">
        <li><a href="#menu1">Item1</a></li>
        <li><a href="#menu2">Item2</a></li>
        <li><a href="#menu3">Item3</a></li>
      </ul><%-- /.nav-list --%>
    </nav><%-- /.nav --%>
    <div class="submit-btn">
      <form action="<%=request.getContextPath()%>/NewRegistration" method="get">
        <input type="submit" value="新規登録" id="NEW_SUBMIT" >
      </form>
      <form action="<%=request.getContextPath()%>/UserLogin" method="get">
        <input type="submit" value="ログイン" id="LOGIN_SUBMIT" >
      </form>
    </div>
  </header>
  <div class="brank-space"></div><%--ヘッダー分の余白--%>
  <section id="menu1" class="menus menu1">
    <div class="item1">
      <img src="pictures/ナスカの地上絵.jpg">
    </div>
  </section><%-- /#menu1.menus --%>
  <section id="menu2" class="menus menu2">
    <div class="item2">
      <img src="pictures/銀河.jpg">
    </div>
  </section><%-- /#menu2.menus --%>
  <section id="menu3" class="menus menu3">
    <div class="item3">
      <img src="pictures/メテオラの景色.jpg">
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
