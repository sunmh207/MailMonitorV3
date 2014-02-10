<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<center>
<html:pagetitle title="Rule"/>

<table width="100%" class="table" cellpadding="0" cellspacing="0" align="center">
    <tr>
        <td height="24" align="right" class="td_lable">Type</td>
        <td class="td_edit">		
			<s:property  value="rule.class.simpleName" />	 
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Name</td>
        <td class="td_edit">		
			<s:property  value="rule.name" />	 
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Description</td>
        <td class="td_edit">		
			<s:property value="rule.desc"  />     		
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Project</td>
        <td class="td_edit">		
			<s:property value="rule.project" />
        </td>
    </tr>
    <tr>
        <td height="24" align="right" class="td_lable">Monitor Email Account</td>
        <td class="td_edit">		
			<s:property value="rule.emailAccount.username" />
        </td>
    </tr> 
    <tr>
        <td height="24" align="right" class="td_lable">Schedule</td>
        <td class="td_edit">		
			<s:property value="rule.schedule.name"/>
        </td>
    </tr> 
    
	<tr>
        <td height="24" align="right" class="td_lable">Order</td>
        <td class="td_edit">		
			<s:property  value="rule.order"/>	 
        </td>
    </tr>
	<tr>
        <td height="24" align="right" class="td_lable">Creator</td>
        <td class="td_edit">		
			<s:property  value="rule.creator"/>	 
        </td>
    </tr>
    
    <tr>
        <td  height="24" align="right" class="td_lable">Active</td>
        <td class="td_edit">		
			<s:property value="rule.active" />   		
        </td>
    </tr>
    <tr>
        <td  height="24" align="right" class="td_lable">Stop Processing More Rules</td>
        <td class="td_edit">		
			<s:property value="rule.stopProcessingMoreRules" />   		
        </td>
    </tr>
</table>
<input type="button" onclick="location.href='${root}/rule/rule.do'" value="Back">

<html:pagetitle title="Conditions"/>
<form id="searchForm"¡¡action="${root}/rule/condition.do">
	<input type="button" onclick="location.href='${root}/rule/condition_form!input.do?rule.id=<s:property value="rule.id"/>'" value="+ Condition">
<s:hidden name="rule.id" value="%{rule.id}" />	
</form>

<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td class="td_header">NO.</td>
		<td class="td_header">subjectExp</td>
		<td class="td_header">bodyExp</td>
		<td class="td_header">fromExp</td>
		<td class="td_header">order</td>
		<td class="td_header" width="60">Operation</td>
	</tr>
	<s:iterator value="rule.conditions" status="status">
		<s:if test="id != null">
		<tr>
			<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
			<td align="left" class='td_body'><s:property value="subjectExp"/></td>
			<td align="left" class='td_body'><s:property value="bodyExp"/></td>
			<td align="left" class='td_body'><s:property value="fromExp"/></td>
			<td align="left" class='td_body'><s:property value="order"/></td>
			<td align="center"  class='td_body'>
				<a href="${root}/rule/condition_form!input.do?rule.id=<s:property value="rule.id"/>&condition.id=<s:property value="id"/>">Edit</a>&nbsp;
				<a href="${root}/rule/condition!delete.do?rule.id=<s:property value="rule.id"/>&condition.id=<s:property value="id"/>" onclick="return fDelCheck()">Delete</a>
			</td>
		</tr>
		</s:if>
	</s:iterator>
</table>
<br>
<br>
<s:if test="rule.class.simpleName == \"NoPairMailRule\"">
	<html:pagetitle title="Conditions2"/>
	<form id="searchForm"¡¡action="${root}/rule/condition.do">
		<input type="button" onclick="location.href='${root}/rule/condition2_form!input.do?rule.id=<s:property value="rule.id"/>'" value="+ Condition2">
	<s:hidden name="rule.id" value="%{rule.id}" />	
	</form>
	
	<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
		<tr>
			<td class="td_header">NO.</td>
			<td class="td_header">subjectExp</td>
			<td class="td_header">bodyExp</td>
			<td class="td_header">fromExp</td>
			<td class="td_header">order</td>
			<td class="td_header" width="60">Operation</td>
		</tr>
		<s:iterator value="rule.conditions2" status="status">
			<s:if test="id != null">
			<tr>
				<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
				<td align="left" class='td_body'><s:property value="subjectExp"/></td>
				<td align="left" class='td_body'><s:property value="bodyExp"/></td>
				<td align="left" class='td_body'><s:property value="fromExp"/></td>
				<td align="left" class='td_body'><s:property value="order"/></td>
				<td align="center"  class='td_body'>
					<a href="${root}/rule/condition2_form!input.do?rule.id=<s:property value="rule.id"/>&condition.id=<s:property value="id"/>">Edit</a>&nbsp;
					<a href="${root}/rule/condition!delete.do?rule.id=<s:property value="rule.id"/>&condition.id=<s:property value="id"/>" onclick="return fDelCheck()">Delete</a>
				</td>
			</tr>
			</s:if>
		</s:iterator>
	</table>
</s:if>
</center>
</body>
</html>