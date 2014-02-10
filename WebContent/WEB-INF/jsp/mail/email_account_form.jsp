<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<html:pagetitle title="Email Account"/>
<s:form action="accountinput!save.do" method="post">
<s:hidden name="account.id" value="%{account.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
    <tr>
        <td height="24" align="right" class="td_lable">Email</td>
        <td class="td_edit">		
			<s:textfield  name="account.username" value="%{account.username}" size="50"/>	
			<s:fielderror ><s:param>account.username</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Password</td>
        <td class="td_edit">		
			<s:textfield  name="account.password" value="%{account.password}" size="50"/>	
			<s:fielderror ><s:param>account.password</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Monitoring Folder</td>
        <td class="td_edit">		
			<s:textfield  name="account.folder" value="%{account.folder}" size="50"/>	
			<s:fielderror ><s:param>account.folder</s:param></s:fielderror> Should be under INBOX  
        </td>
    </tr>
    
        
    <tr>
      <td  height="24" align="right" class="td_lable"></td>
      <td class="td_edit">	
       <s:submit value="OK"/>
       <input type="button" onclick="location.href='${root}/mail/account.do'" value="Cancel">
      </td>
      <td>&nbsp;</td>
    </tr>	
</table>
    
</s:form>
</body>
</html>