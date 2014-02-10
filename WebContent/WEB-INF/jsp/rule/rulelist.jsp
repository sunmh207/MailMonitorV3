<%@include file="/WEB-INF/jsp/include.jsp"%>
<script type="text/javascript">
function deactivate(){
	return window.confirm('Are you sure to deactivate this rule?'); 
} 
function activate(){
	return window.confirm("Are you sure to activate this rule?"); 
}
function forDelete(){
	return window.confirm("Are you sure to delete this rule?"); 
}
</script>
<body>
<center>
<form id="searchForm"¡¡action="${root}/rule/rule.do">
<div style="float: left; padding-bottom: 0px;">
Project
<s:select  name="searchForm.project"  list="projectList"   headerKey="" headerValue=""/>
Name <input name="searchForm.name" value='${searchForm.name}' size="5">
	<input type="button" value="Search" onclick="javascript:goPage(1)"> 
	<input type="button" onclick="location.href='${root}/rule/singlemailruleinput!input.do'" value="+SingleMailRule">
	<input type="button" onclick="location.href='${root}/rule/nopairmailruleinput!input.do'" value="+NoPairMailRule">
	<input type="button" onclick="location.href='${root}/rule/nomailruleinput!input.do'" value="+NoMailRule">
	<input type="button" onclick="location.href='${root}/rule/continuesmailruleinput!input.do'" value="+ContinuesMailRule">
	<input type="button" onclick="location.href='${root}/rule/nomailontimeruleinput!input.do'" value="+NoMailOnTimeRule">
</div>
<html:pagination exportExcel="false"/>
</form>

<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td class="td_header">NO.</td>
		<td class="td_header">Project</td>
		<td class="td_header">Name</td>
		<td class="td_header">Type</td>
		<td class="td_header">Monitor Email Account</td>
		<td class="td_header">Schedule</td>
		<td class="td_header">Description</td>
		<td class="td_header" width="75">Created Time</td>
		<td class="td_header">Creator</td>
		<td class="td_header">Is Active?</td>
		<td class="td_header">StopProcessingMoreRules</td>
		<td class="td_header">Order</td>
		<td class="td_header">Triggered Times</td>
		<td class="td_header" width="60">Operation</td>
	</tr>
	<s:iterator value="objectList" status="status">
		<tr>
			<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
			<td align="left" class='td_body'><s:property value="project"/></td>
			<td align="left" class='td_body'><s:property value="name"/></td>
			<td align="left" class='td_body'><s:property value="class.simpleName"/></td>
			<td align="left" class='td_body'><s:property value="emailAccount.username"/>/<s:property value="emailAccount.folder"/></td>
			<td align="left" class='td_body'><s:property value="schedule.name"/></td>
			<td align="left" class='td_body'><s:property value="desc"/></td>
			<td align="left" class='td_body'><s:property value="createTime"/></td>
			<td align="left" class='td_body'><s:property value="creator"/></td>
			<td align="left" class='td_body'><s:property value="active"/></td>
			<td align="left" class='td_body'><s:property value="stopProcessingMoreRules"/></td>
			<td align="left" class='td_body'><s:property value="order"/></td>
			<td align="left" class='td_body'><s:property value="triggerTimes"/></td>
			<td align="center"  class='td_body'>
				<!-- SingleMailRule -->
				<s:if test="class.simpleName == \"SingleMailRule\"">
					<s:if test="active">
						<a href="${root}/rule/singlemailrule!deactivate.do?rule.id=<s:property value="id"/>" onclick="return deactivate()">Deactivate</a>
					</s:if>
					<s:else>
						<a href="${root}/rule/singlemailrule!activate.do?rule.id=<s:property value="id"/>" onclick="return activate()">Activate</a>&nbsp;
						<a href="${root}/rule/singlemailruleinput!input.do?rule.id=<s:property value="id"/>">Edit</a>&nbsp;
						<a href="${root}/rule/condition.do?rule.id=<s:property value="id"/>">Conditions</a>&nbsp;
						<a href="${root}/rule/action.do?rule.id=<s:property value="id"/>">Actions</a>&nbsp;
						<a href="${root}/rule/singlemailrule!delete.do?rule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
					</s:else>
				</s:if>
				
				<!-- PairMailRule -->
				<s:if test="class.simpleName == \"NoPairMailRule\"">
					<s:if test="active">
						<a href="${root}/rule/nopairmailrule!deactivate.do?rule.id=<s:property value="id"/>" onclick="return deactivate()">Deactivate</a>
					</s:if>
					<s:else>
						<a href="${root}/rule/nopairmailrule!activate.do?rule.id=<s:property value="id"/>" onclick="return activate()">Activate</a>&nbsp;
						<a href="${root}/rule/nopairmailruleinput!input.do?rule.id=<s:property value="id"/>">Edit</a>&nbsp;
						<a href="${root}/rule/condition.do?rule.id=<s:property value="id"/>">Conditions</a>&nbsp;
						<a href="${root}/rule/action.do?rule.id=<s:property value="id"/>">Actions</a>&nbsp;
						<a href="${root}/rule/nopairmailrule!delete.do?rule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
					</s:else>
				</s:if>
				
				<!-- NoMailRule -->
				<s:if test="class.simpleName == \"NoMailRule\"">
					<s:if test="active">
						<a href="${root}/rule/nomailrule!deactivate.do?rule.id=<s:property value="id"/>" onclick="return deactivate()">Deactivate</a>
					</s:if>
					<s:else>
						<a href="${root}/rule/nomailrule!activate.do?rule.id=<s:property value="id"/>" onclick="return activate()">Activate</a>&nbsp;
						<a href="${root}/rule/nomailruleinput!input.do?rule.id=<s:property value="id"/>">Edit</a>&nbsp;
						<a href="${root}/rule/condition.do?rule.id=<s:property value="id"/>">Conditions</a>&nbsp;
						<a href="${root}/rule/action.do?rule.id=<s:property value="id"/>">Actions</a>&nbsp;
						<a href="${root}/rule/nomailrule!delete.do?rule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
					</s:else>
				</s:if>
				
				<!-- ContinuesMailRule -->
				<s:if test="class.simpleName == \"ContinuesMailRule\"">
					<s:if test="active">
						<a href="${root}/rule/continuesmailrule!deactivate.do?rule.id=<s:property value="id"/>" onclick="return deactivate()">Deactivate</a>
					</s:if>
					<s:else>
						<a href="${root}/rule/continuesmailrule!activate.do?rule.id=<s:property value="id"/>" onclick="return activate()">Activate</a>&nbsp;
						<a href="${root}/rule/continuesmailruleinput!input.do?rule.id=<s:property value="id"/>">Edit</a>&nbsp;
						<a href="${root}/rule/condition.do?rule.id=<s:property value="id"/>">Conditions</a>&nbsp;
						<a href="${root}/rule/action.do?rule.id=<s:property value="id"/>">Actions</a>&nbsp;
						<a href="${root}/rule/continuesmailrule!delete.do?rule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
					</s:else>
				</s:if>
				<!-- ContinuesMailRule -->
				<s:if test="class.simpleName == \"NoMailOnTimeRule\"">
					<s:if test="active">
						<a href="${root}/rule/nomailontimerule!deactivate.do?rule.id=<s:property value="id"/>" onclick="return deactivate()">Deactivate</a>
					</s:if>
					<s:else>
						<a href="${root}/rule/nomailontimerule!activate.do?rule.id=<s:property value="id"/>" onclick="return activate()">Activate</a>&nbsp;
						<a href="${root}/rule/nomailontimeruleinput!input.do?rule.id=<s:property value="id"/>">Edit</a>&nbsp;
						<a href="${root}/rule/condition.do?rule.id=<s:property value="id"/>">Conditions</a>&nbsp;
						<a href="${root}/rule/action.do?rule.id=<s:property value="id"/>">Actions</a>&nbsp;
						<a href="${root}/rule/nomailontimerule!delete.do?rule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
					</s:else>
				</s:if>
				
				<a href="${root}/rule/ruletriggerrecord.do?ruleId=<s:property value="id"/>" >Logs</a>
			</td>
		</tr>
		
		</s:iterator>
</table>
</center>
</body>
</html>