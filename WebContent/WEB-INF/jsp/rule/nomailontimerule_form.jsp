<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<html:pagetitle title="No Email Rule"/>
<s:form action="nomailontimeruleinput!save.do" method="post">
<s:hidden name="rule.id" value="%{rule.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
	<tr>
        <td height="24" align="right" class="td_lable">Type</td>
        <td class="td_edit" colspan="2">		
			No Mail On Time Rule: If the specific mail doesn't come on specific time, then trigger the rule.
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Name</td>
        <td class="td_edit">		
			<s:textfield  name="rule.name" value="%{rule.name}" size="50"/>	 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Description</td>
        <td class="td_edit">		
			<s:textarea name="rule.desc" value="%{rule.desc}"  rows="4" cols="100"/>     		
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Project</td>
        <td class="td_edit">		
			<s:select name="rule.project" list="projectList"  headerKey="" headerValue=""/>
		<s:fielderror ><s:param>rule.project</s:param></s:fielderror>    
			
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Monitor Email Account</td>
        <td class="td_edit">		
			<s:select name="emailAccount.id" list="emailAccountMap"  headerKey="" headerValue=""/>
			<s:fielderror ><s:param>emailAccount.id</s:param></s:fielderror>    
        </td>
    </tr> 
    <tr>
        <td height="24" align="right" class="td_lable">Schedule</td>
        <td class="td_edit">		
			<s:select name="schedule.id" list="scheduleMap"  headerKey="" headerValue=""/>
			<s:fielderror ><s:param>schedule.id</s:param></s:fielderror>    
        </td>
    </tr> 
    <tr>
        <td height="24" align="right" class="td_lable">Expected Time</td>
        <td class="td_edit">		
			<s:textfield  name="rule.expectedTime" value="%{rule.expectedTime}" size="5"/> i.e. 10:00:00	 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Skew</td>
        <td class="td_edit">		
			<s:textfield  name="rule.skew" value="%{rule.skew}" size="5"/> Minutes	 
        </td>
    </tr>
	<tr>
        <td height="24" align="right" class="td_lable">Order</td>
        <td class="td_edit">		
			<s:textfield  name="rule.order" value="%{rule.order}"/>	 
        </td>
    </tr>
	<tr>
        <td height="24" align="right" class="td_lable">Creator</td>
        <td class="td_edit">		
			<s:textfield  name="rule.creator" value="%{rule.creator}"/>	 
        </td>
    </tr>
    
    <tr>
        <td  height="24" align="right" class="td_lable">Active</td>
        <td class="td_edit">		
			<s:checkbox name="rule.active" value="%{rule.active}"/>   		
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Stop Processing More Rules</td>
        <td class="td_edit">		
			<s:checkbox name="rule.stopProcessingMoreRules" value="%{rule.stopProcessingMoreRules}"/>   		
        </td>
    </tr>
        
    <tr>
      <td  height="24" align="right" class="td_lable"></td>
      <td class="td_edit">	
       <s:submit value="OK"/>
       <input type="button" onclick="location.href='${root}/rule/rule.do'" value="Cancel">
      </td>
      <td>&nbsp;</td>
    </tr>	
</table>
    
</s:form>
</body>
</html>