<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<html:pagetitle title="Email Account"/>
<s:form action="scheduleinput!save.do" method="post">
<s:hidden name="schedule.id" value="%{schedule.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
    <tr>
        <td height="24" align="right" class="td_lable">Name </td>
        <td class="td_edit">		
			<s:textfield  name="schedule.name" value="%{schedule.name}" size="50"/>	
			<s:fielderror ><s:param>schedule.name</s:param></s:fielderror> 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Description </td>
        <td class="td_edit">		
			<s:textarea  name="schedule.desc" value="%{schedule.desc}" rows="4" cols="100"/>	
			<s:fielderror ><s:param>schedule.desc</s:param></s:fielderror> 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Days of Week</td>
        <td class="td_edit">		
			<s:checkbox name="sunday" fieldValue="1" />SUN 		
			<s:checkbox name="monday" fieldValue="2" />MON 		
			<s:checkbox name="tuesday" fieldValue="3" />TUE 		
			<s:checkbox name="wednesday" fieldValue="4" />WED 		
			<s:checkbox name="thursday" fieldValue="5" />THU 		
			<s:checkbox name="friday" fieldValue="6" />FRI		
			<s:checkbox name="saturday" fieldValue="7" />SAT   
			<s:fielderror ><s:param>schedule.dayOfWeek</s:param></s:fielderror> 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Time Range Expression: </td>
        <td class="td_edit">		
			<s:textfield  name="schedule.timeRange" value="%{schedule.timeRange}" size="50"/>	
			<s:fielderror ><s:param>schedule.timeRange</s:param></s:fielderror> 
			 e.g. 10:00:00-12:00:00|18:00:00-23:59:59|...	  
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Time Zone</td>
        <td class="td_edit">		
			<s:select name="schedule.timeZoneID" list="timeZoneIdArray"  headerKey="" headerValue=""/>
			<s:fielderror ><s:param>schedule.timeZoneID</s:param></s:fielderror>  
        </td>
    </tr>
    
        
    <tr>
      <td  height="24" align="right" class="td_lable"></td>
      <td class="td_edit">	
       <s:submit value="OK"/>
       <input type="button" onclick="location.href='${root}/rule/schedule.do'" value="Cancel">
      </td>
      <td>&nbsp;</td>
    </tr>	
</table>
    
</s:form>
</body>
</html>