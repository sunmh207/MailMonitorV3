<%@include file="/WEB-INF/jsp/include.jsp"%>
<body>
<center>
<form id="searchForm"¡¡action="${root}/rule/ruletriggerrecord.do">
<html:pagination exportExcel="false"/>
</form>

<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td class="td_header">NO.</td>
		<td class="td_header">Triggered Time</td>
		<td class="td_header">MailTo</td>
		<td class="td_header">MailCC</td>
		<td class="td_header">Subject</td>
		<td class="td_header">Body</td>
		<td class="td_header">Note</td>
	</tr>
	<s:iterator value="objectList" status="status">
	<tr>
		<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
		<td align="left" class='td_body'><s:property value="triggerTime"/></td>
		<td align="left" class='td_body'><s:property value="actionMailTo"/></td>
		<td align="left" class='td_body'><s:property value="actionMailCC"/></td>
		<td align="left" class='td_body'><s:property value="actionMailSubject"/></td>
		<td align="left" class='td_body'><s:property value="actionMailBody"/></td>
		<td align="left" class='td_body'><s:property value="note"/></td>
	</tr>
	</s:iterator>
</table>
</center>
</body>
</html>