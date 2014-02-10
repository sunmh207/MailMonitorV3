<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<html:pagetitle title="Action"/>
<s:form action="sendmailaction_form!save.do" method="post">
<s:hidden name="rule.id" value="%{rule.id}" />
<s:hidden name="action.id" value="%{action.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
    <tr>
        <td height="24" align="right" class="td_lable">To</td>
        <td class="td_edit">		
			<s:textfield  name="action.to" value="%{action.to}" size="60"/>	
			<s:fielderror ><s:param>action.to</s:param></s:fielderror>    
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">CC</td>
        <td class="td_edit">		
			<s:textfield  name="action.cc" value="%{action.cc}" size="60"/>	
			<s:fielderror ><s:param>action.cc</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Subject</td>
        <td class="td_edit">		
			<s:textfield  name="action.subject" value="%{action.subject}" size="100"/>	
			<s:fielderror ><s:param>action.subject</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Body</td>
        <td class="td_edit">		
			<s:textarea  name="action.body" value="%{action.body}" rows="6" cols="80"/>	
			<s:fielderror ><s:param>action.body</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Order</td>
        <td class="td_edit">		
			<s:textfield  name="action.order" value="%{action.order}" size="15"/>	
			<s:fielderror ><s:param>action.order</s:param></s:fielderror>   
        </td>
    </tr>
        
    <tr>
      <td  height="24" align="right" class="td_lable"></td>
      <td class="td_edit">	
       <s:submit value="OK"/>
      <input type="button" onclick="location.href='${root}/rule/action.do?rule.id=<s:property value="rule.id"/>'" value="Cancel">
      </td>
      <td>&nbsp;</td>
    </tr>	
</table>
    
</s:form>
</body>
</html>