<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<html:pagetitle title="Condition2"/>
<s:form action="condition2_form!save.do" method="post">
<s:hidden name="rule.id" value="%{rule.id}" />
<s:hidden name="condition.id" value="%{condition.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
    <tr>
        <td height="24" align="right" class="td_lable">Subject Regular Expression</td>
        <td class="td_edit">		
			<s:textfield  name="condition.subjectExp" value="%{condition.subjectExp}" size="60"/>	
			<s:fielderror ><s:param>condition.subjectExp</s:param></s:fielderror>   <a href="http://gskinner.com/RegExr/" target="_blank">Regular Expression Test</a>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Body Regular Expression</td>
        <td class="td_edit">		
			<s:textarea  name="condition.bodyExp" value="%{condition.bodyExp}" rows="4" cols="80"/>	
			<s:fielderror ><s:param>condition.bodyExp</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">From Regular Expression</td>
        <td class="td_edit">		
			<s:textfield  name="condition.fromExp" value="%{condition.fromExp}" size="100"/>	
			<s:fielderror ><s:param>condition.fromExp</s:param></s:fielderror>   
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Order</td>
        <td class="td_edit">		
			<s:textfield  name="condition.order" value="%{condition.order}" size="15"/>	
			<s:fielderror ><s:param>condition.order</s:param></s:fielderror>   
        </td>
    </tr>
        
    <tr>
      <td  height="24" align="right" class="td_lable"></td>
      <td class="td_edit">	
       <s:submit value="OK"/>
      <input type="button" onclick="location.href='${root}/rule/condition.do?rule.id=<s:property value="rule.id"/>'" value="Cancel">
      </td>
      <td>&nbsp;</td>
    </tr>	
</table>
    
</s:form>
</body>
</html>