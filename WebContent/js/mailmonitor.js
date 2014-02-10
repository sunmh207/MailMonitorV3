function fPopUpUserDlg(nameObj, idObj, single) {
    var nameComp = document.getElementById(nameObj)||document.all(nameObj);
    var idComp = document.getElementById(idObj)||document.all(idObj);
    var time =new Date();
    // var url = "/JTMobile/console/popUserQuery!list.do?time="+time; 
    var url = "/console/popUserQuery!list.do?time="+time;
    //  var url = "/JTMobile/console/popUserQuery!list.do?time="+time; 
    var url = "/console/popUserQuery.do?time="+time;
    if(single){
    	url = url+"&single=true";
    }
    retval = window.showModalDialog(url, "", "dialogWidth:800px; dialogHeight:520px;status:1;resizable:yes;center:true");
  //window.open("/JTMobile/console/popUserQuery!list.do");
    
    if (retval != null) {          //name1,name2,name3..||id1,id2,id3..
        rs = retval.split("||");
        if (idObj != null) {
            nameComp.value = rs[0];
            idComp.value = rs[1];            
        } 
        return true;
    } else {
        return false;
    }
}

function fPopUpUserDlg2(nameObj, idObj, single) {
    var nameComp = document.getElementById(nameObj)||document.all(nameObj);
    var idComp = document.getElementById(idObj)||document.all(idObj);
    var time =new Date();
    // var url = "/JTMobile/console/popUserQuery!list.do?time="+time;
    var url = "/console/popUserQuery!list.do?time="+time;
    if(single){
    	url = url+"&single=true";
    }
    retval = window.showModalDialog(url, "", "dialogWidth:800px; dialogHeight:520px;status:1;resizable:yes;center:true");
  //window.open("/JTMobile/console/popUserQuery!list.do");
    retval = retval?retval:selectedStr;
    if (retval != null) {          //name1,name2,name3..||id1,id2,id3..
        rs = retval.split("||");
        if (idObj != null) {    
            addUsers( rs[1],rs[0],rs[2]);
        } 
        return true;
    } else {
        return false;
    }
}
/**
 * Check if this is any checkbox checked.
 * @param chk
 * @return false:no checked.  ture:checked some or all.
 */
function checkMutilSelect(chk) {
    if (chk == null) {
        return false;
    }
    var size = chk.length;
    if (size == null) {
        if (chk.checked) {
            return true;
        } else {
            return false;
        }
    } else {
        for (var i = 0; i < size; i++) {
            if (chk[i].checked) {
                return true;
            }
        }
    }
    return false;
}

function selectAll(all, chk) {
    if (chk == null) {
        return false;
    }
    var size = chk.length;
    
    if (size == null) {
        if (!chk.disabled)
            chk.checked = all.checked;
        ;
    } else {
        for (var i = 0; i < size; i++) {
            if (!chk[i].disabled)
                chk[i].checked = all.checked;
        }
    }
    return true;
}
function fDelCheck(){
	return window.confirm("Confirm to delete?"); 
} 
function check(){
	var r = checkNull();
	if(r) r = checkLenght();
	return r;
}
function checkLenght(){
	if((typeof lengthLimits=='undefined' )||lengthLimits==null){
		return true;
	}
	alert(lengthLimits.length);
	for(var i=0;i<lengthLimits.length;i++){
		var name = lengthLimits[i][0];
		var p = lengthLimits[i][1];
		var len = lengthLimits[i][2];
		alert(name+p+len);
		var v = $("*[name='"+p+"']").val();
		if(v){
			var l = 0;
			for(var j=0;j<v.length;j++){
				if(v.charAt(j)<='~'){
					l++;
				}else{
					l+=2;
				}
			}
			if(l>len*2){
				alert(name+"长度不能超过"+len+"个字。");
				return false;
			}
		}
	}
	return true;
}
function checkNull(){
	if((typeof notNulls=='undefined' )||notNulls==null){
		return true;
	}
	for(p in notNulls){
		var v = $("*[name='"+p+"']").val();
		if(!v){
			alert(notNulls[p]);
			return false;
		}
	}
	return true;
}
