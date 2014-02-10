<%@include file="/WEB-INF/jsp/include.jsp" %>
<html">
<head>
<title>SupportNet Mail Monitor</title>
</head>

<frameset rows="30,*" cols="*" framespacing="0" frameborder="no" border="0">
  <frame src="${root}/jsp/top.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
  <frame src="${root}/rule/rule.do" name="mainFrame" id="mainFrame" />	
</frameset>
<noframes>
<body>
</body>
</noframes>
</html>
