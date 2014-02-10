<%@page import="java.util.Iterator"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<s:actionerror />
<s:actionmessage />
<s:form action="continuesmailruleinput!save.do" method="post">
<s:hidden name="rule.id" value="%{rule.id}" />
	
<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
 	<tr>
        <td height="24" align="right" class="td_lable">Type</td>
        <td class="td_edit" colspan="2">		
			No Mail Rule: If the specific mail hasn't come in a period of time, then trigger the rule.
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Name</td>
        <td class="td_edit">		
			<s:textfield  name="rule.name" value="%{rule.name}" size="50"/>	 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Project</td>
        <td class="td_edit">		
			<s:select name="rule.project" list="#{'CAT-APEX':'CAT-APEX','CAT-Enterprise-Portal':'CAT-Enterprise-Portal','CAT-eCommerce-Portal':'CAT-eCommerce-Portal','BCBSMA':'BCBSMA','Greyhound':'Greyhound','Bunge':'Bunge'}"  label="abc" listKey="key" listValue="value"  headerKey="" headerValue=""/>
		<s:fielderror ><s:param>rule.project</s:param></s:fielderror>    
			
        </td>
    </tr>
     <tr>
        <td height="24" align="right" class="td_lable">Period/Mail Threshold</td>
        <td class="td_edit">		
			Trigger this rule if receive this mails <s:textfield  name="rule.mailNumberThreshold" value="%{rule.mailNumberThreshold}" size="10"/> times in <s:textfield  name="rule.period" value="%{rule.period}" size="10"/>	minutes. 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable"><b>Subject Rule Regular Expression</b></td>
        <td class="td_edit">		
			<s:textarea name="rule.subjectKeywordsExp" value="%{rule.subjectKeywordsExp}"  rows="4" cols="100"/> 
			<a href="http://gskinner.com/RegExr/" target="_blank">Regular Expression Test</a>     		
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable"><b>AND/OR</b></td>
        <td class="td_edit">		
        <s:select name="rule.subjectBodyKeywordsR" list="#{'AND':'AND','OR':'OR'}"  label="abc" listKey="key" listValue="value"/>
		<s:fielderror ><s:param>rule.subjectBodyKeywordsR</s:param></s:fielderror>      		 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable"><b>Body Rule Regular Expression</b></td>
        <td class="td_edit">		
			<s:textarea name="rule.bodyKeywordsExp" value="%{rule.bodyKeywordsExp}"  rows="4" cols="100"/>     		
        </td>
    </tr>
    
    
    
    <tr>
        <td  height="24" align="right" class="td_lable">Subject Exception Regular Expression</td>
        <td class="td_edit">		
			<s:textarea name="rule.subjectExceptionExp" value="%{rule.subjectExceptionExp}"  rows="4" cols="100"/>     		
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">AND/OR</td>
        <td class="td_edit">		
        <s:select name="rule.subjectBodyExceptionR" list="#{'AND':'AND','OR':'OR'}"  label="abc" listKey="key" listValue="value" />
		<s:fielderror ><s:param>rule.subjectBodyExceptionR</s:param></s:fielderror>      		 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Body Exception Regular Expression</td>
        <td class="td_edit">		
			<s:textarea name="rule.bodyExceptionExp" value="%{rule.bodyExceptionExp}"  rows="4" cols="100"/>     		
        </td>
    </tr>
	<tr>
        <td height="24" align="right" class="td_lable">Creator</td>
        <td class="td_edit">		
			<s:textfield  name="rule.creator" value="%{rule.creator}"/>	 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Description</td>
        <td class="td_edit">		
			<s:textarea name="rule.desc" value="%{rule.desc}"  rows="4" cols="100"/>     		
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Available Time</td>
        <td class="td_edit">	
        	Days of Week: 	 
			<s:checkbox name="sunday" fieldValue="1" />SUN 		
			<s:checkbox name="monday" fieldValue="2" />MON 		
			<s:checkbox name="tuesday" fieldValue="3" />TUE 		
			<s:checkbox name="wednesday" fieldValue="4" />WED 		
			<s:checkbox name="thursday" fieldValue="5" />THU 		
			<s:checkbox name="friday" fieldValue="6" />FRI		
			<s:checkbox name="saturday" fieldValue="7" />SAT 		
			<br>
			Time Expression: <s:textfield  name="rule.availableTimeRange" value="%{rule.availableTimeRange}" size="50"/> e.g. 10:00:00-12:00:00|18:00:00-23:59:59|...	
        </td>
    </tr>
	
    <tr>
        <td height="24" align="right" class="td_lable">Alert Message Subject(send out)</td>
        <td class="td_edit">		
			<s:textfield  name="rule.alertMsgSubject" value="%{rule.alertMsgSubject}" size="80"/>	
			
        </td>
    </tr>
     <tr>
        <td  height="24" align="right" class="td_lable">Alert Message Body(send out)</td>
        <td class="td_edit">		
			<s:textarea name="rule.alertMsgBody" value="%{rule.alertMsgBody}"  rows="4" cols="100"/>     		
        </td>
    </tr>
     <tr>
        <td height="24" align="right" class="td_lable">Send TO</td>
        <td class="td_edit">		
			<s:textfield  name="rule.alertTo" value="%{rule.alertTo}" size="100"/>	 
        </td>
    </tr>
     <tr>
        <td height="24" align="right" class="td_lable">CC TO</td>
        <td class="td_edit">		
			<s:textfield  name="rule.alertCC" value="%{rule.alertCC}" size="100"/>	 
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