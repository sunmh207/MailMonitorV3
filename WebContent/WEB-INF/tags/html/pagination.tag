<%@ tag pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="large" type="java.lang.String" required="false"%>
<%@ attribute name="exportExcel" type="java.lang.String" required="false"%>
<script type="text/javascript">
	function goPage(id){
		var ele = document.getElementById("_PageID");
		ele.value = id;
		var fun = document.getElementById("searchForm").onsubmit;
		if(typeof fun=='function')fun();
		document.getElementById("searchForm").submit();
	}
	function exportExcel(){
		var frm = document.getElementById("searchForm");
		var actionURL = window.location.href;
		var url;
		try{
			url=exportExcelURL();
		}catch(err){
			url = actionURL.replace(".do", "!exportExcel.do");	
		}
			frm.action=url;
			frm.target="_blank";
			frm.submit();
			frm.action=actionURL;
			frm.target="_self";
		
	}
</script>
<div id='Pagination' style='float:right;'>Total:<strong>${pager.totalRecords}</strong>&nbsp;
	<select name='pageSize' onchange='document.getElementById("searchForm").submit();'>
		<option value='10' <c:if test="${pager.pageSize == 10}">selected='selected'</c:if>>10</option> 
		<option value='20' <c:if test="${pager.pageSize == 20}">selected='selected'</c:if>>20</option> 
		<option value='35' <c:if test="${pager.pageSize == 35}">selected='selected'</c:if>>35</option> 
		<option value='50' <c:if test="${pager.pageSize == 50}">selected='selected'</c:if>>50</option> 
		<option value='100'<c:if test="${pager.pageSize == 100}">selected='selected'</c:if>>100</option> 
		<%if("true".equals(large)){%>
			<option value='300'<c:if test="${pager.pageSize == 300}">selected='selected'</c:if>>300</option>
			<option value='500'<c:if test="${pager.pageSize == 500}">selected='selected'</c:if>>500</option>
			<option value='1000'<c:if test="${pager.pageSize == 1000}">selected='selected'</c:if>>1000</option>
			<option value='5000'<c:if test="${pager.pageSize == 5000}">selected='selected'</c:if>>5000</option>
		<%} %>
	</select> 
	Records/Page&nbsp;Page:<strong>${pager.currentPage}</strong>/<strong>${pager.totalPages}</strong>&nbsp;
	<c:choose>
		<c:when test="${pager.currentPage eq 1}">First&nbsp;Prev</c:when>
		<c:otherwise>
			<a href="javascript:goPage(1)">First</a>&nbsp;
			<a href="javascript:goPage(${pager.currentPage-1})">Prev</a>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${pager.currentPage eq pager.totalPages}">Next&nbsp;Last</c:when>
		<c:otherwise>
			<a href="javascript:goPage(${pager.currentPage+1})">Next</a>&nbsp;
			<a href="javascript:goPage(${pager.totalPages})">Last</a>
		</c:otherwise>
	</c:choose>
	<input type='text' id='_PageID' value='${pager.currentPage}' name="currentPage"
		style='text-align:center;width:30px;font-size:12px;'>
	<input type='button' id='goto' value='Go' onclick='document.getElementById("searchForm").submit();' class="button"/>
	<%if("true".equals(exportExcel)){ %>
	<input name="excel" type="button" onClick="exportExcel()" class="button" value="xls">
	<%}%>
</div>
