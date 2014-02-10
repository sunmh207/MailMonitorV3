<%@include file="/WEB-INF/jsp/include.jsp"%>
<header>
<SCRIPT language=javascript type=text/javascript> 
<!--
function showOnMain(url){
		parent.mainFrame.location.href=url; 
}

$(function() {
    $( "#radio" ).buttonset();
  });
//-->
</script>
</header>

<body bgcolor="#000000">
<form>
  <div id="radio">
    <input type="radio" id="radio1" name="radio" checked="checked" /><label for="radio1"><a href="#" onclick="showOnMain('${root}/rule/rule.do')">Rules</a></label>
    <input type="radio" id="radio2" name="radio" /><label for="radio2"><a href="#" onclick="showOnMain('${root}/mail/account.do')">Email Account</a></label>
    <input type="radio" id="radio3" name="radio" /><label for="radio3"><a href="#" onclick="showOnMain('${root}/rule/schedule.do')">Schedule</a></label>
    <input type="radio" id="radio4" name="radio" /><label for="radio4">Setting</label>
    <input type="radio" id="radio5" name="radio" /><label for="radio5">Report</label>
  </div>
</form>
</body>

