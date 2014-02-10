<%@include file="/WEB-INF/jsp/include.jsp"%>
<script type="text/javascript">
function forDelete(){
	return window.confirm("Confirm to delete this Email account?"); 
}
</script>
<body>
<center>
<form id="searchForm"¡¡action="${root}/rule/schedule.do">
	<input type="button" onclick="location.href='${root}/rule/scheduleinput!input.do'" value="+ Schedule">
<html:pagination exportExcel="false"/>
</form>

<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td class="td_header">NO.</td>
		<td class="td_header">Name</td>
		<td class="td_header">Description</td>
		<td class="td_header">Day of Week</td>
		<td class="td_header">Time Range</td>
		<td class="td_header">Time Zone</td>
		<td class="td_header" width="60">Operation</td>
	</tr>
	<s:iterator value="objectList" status="status">
		<tr>
			<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
			<td align="left" class='td_body'><s:property value="name"/></td>
			<td align="left" class='td_body'><s:property value="desc"/></td>
			<td align="left" class='td_body'><s:property value="daysOfWeek"/></td>
			<td align="left" class='td_body'><s:property value="timeRange"/></td>
			<td align="left" class='td_body'><s:property value="timeZoneID"/></td>
			<td align="center"  class='td_body'>
				<a href="${root}/rule/scheduleinput!input.do?schedule.id=<s:property value="id"/>">Edit</a>&nbsp;
				<a href="${root}/rule/scheduleinput!delete.do?schedule.id=<s:property value="id"/>" onclick="return forDelete()">Delete</a>
			</td>
		</tr>
	</s:iterator>
</table>
</center>
</body>
</html>