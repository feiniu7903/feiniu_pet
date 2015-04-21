<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
  <head>
    <title>提现列表</title>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script language="JavaScript" src="../js/frame/MzPageControlTop.js"></script>
  </head>
  <body class=frame>
    <SCRIPT LANGUAGE="JavaScript">
    var pct = new MzPageControlTop("pct");
    pct.add("待审核","/bonus/drawMoneyInfoList.do?status=NEW");
    pct.add("审核通过","/bonus/drawMoneyInfoList.do?status=PASS");
    pct.add("已打款","/bonus/drawMoneyInfoList.do?status=PAYOUT");
    pct.add("已取消","/bonus/drawMoneyInfoList.do?status=CANCEL");
    document.write(pct.toString());
    </SCRIPT>
  </body>
</html>