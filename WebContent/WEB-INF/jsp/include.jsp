<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="html" tagdir="/WEB-INF/tags/html" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setAttribute("root", request.getContextPath());%>
<%request.setCharacterEncoding("utf-8");%> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<title>SupportNet Mail Monitor</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" content="no-cache"/>
<link href="${root}/css/style.css" rel="stylesheet" type="text/css"></link>
<link href="${root}/css/jquery-ui.css" rel="stylesheet" type="text/css"></link>

<script type="text/javascript" src="${root}/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${root}/js/jquery-ui.js"></script>
<script type="text/javascript" src="${root}/js/mailmonitor.js"></script>
</head>