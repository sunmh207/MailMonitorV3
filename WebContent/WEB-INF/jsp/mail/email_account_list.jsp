<%@include file="/WEB-INF/jsp/include.jsp"%>

<body>
<center>
<form id="searchForm"¡¡action="${root}/mail/account.do">
	<input type="button" onclick="location.href='${root}/mail/accountinput!input.do'" value="+ Email Account">
<html:pagination exportExcel="true"/>
</form>

<table width="100%" class="table" align="center" cellpadding="1" cellspacing="0" bgcolor="#FFFFFF">
	<tr>
		<td class="td_header">NO.</td>
		<td class="td_header">Email</td>
		<td class="td_header">Password</td>
		<td class="td_header">folder</td>
		<td class="td_header" width="60">Operation</td>
	</tr>
	<s:iterator value="objectList" status="status">
		<tr>
			<td align="left" class='td_header' >${status.index+pager.startRecord}</td>
			<td align="left" class='td_body'><s:property value="username"/></td>
			<td align="left" class='td_body'><s:property value="password"/></td>
			<td align="left" class='td_body'><s:property value="folder"/></td>
			<td align="center"  class='td_body'>
				<a href="${root}/mail/accountinput!input.do?account.id=<s:property value="id"/>">Edit</a>&nbsp;
				<a href="${root}/mail/accountinput!delete.do?account.id=<s:property value="id"/>" onclick="return fDelCheck()">Delete</a>
			</td>
		</tr>
	</s:iterator>
</table>
</center>
</body>
</html>