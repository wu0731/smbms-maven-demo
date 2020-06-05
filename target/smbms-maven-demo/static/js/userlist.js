var userObj;

//用户管理页面上点击删除按钮弹出删除框(userlist.jsp)
function deleteUser(obj){
	$.ajax({
		type:"GET",
		url:path+"/userCon/userDelo",
		data:{method:"deluser",uid:obj.attr("userid")},
		dataType:"json",
		success:function(data){
			if(data.delResult == "true"){//删除成功：移除删除行
				cancleBtn();
				obj.parents("tr").remove();
			}else if(data.delResult == "false"){//删除失败
				//alert("对不起，删除用户【"+obj.attr("username")+"】失败");
				changeDLGContent("对不起，删除用户【"+obj.attr("username")+"】失败");
			}else if(data.delResult == "notexist"){
				//alert("对不起，用户【"+obj.attr("username")+"】不存在");
				changeDLGContent("对不起，用户【"+obj.attr("username")+"】不存在");
			}
		},
		error:function(data){
			//alert("对不起，删除失败");
			changeDLGContent("对不起，删除失败");
		}
	});
}

function openYesOrNoDLG(){
	$('.zhezhao').css('display', 'block');
	$('#removeUse').fadeIn();
}

function cancleBtn(){
	$('.zhezhao').css('display', 'none');
	$('#removeUse').fadeOut();
}
function changeDLGContent(contentStr){
	var p = $(".removeMain").find("p");
	p.html(contentStr);
}

$(function(){
	//通过jquery的class选择器（数组）
	//对每个class为viewUser的元素进行动作绑定（click）
	/**
	 * bind、live、delegate
	 * on
	 */
	$(".viewUser").on("click",function(){
		//将被绑定的元素（a）转换成jquery对象，可以使用jquery方法
		var obj = $(this);
		//调用ajax查询方法
		//AjaxUserSelect(obj);
		window.location.href=path+"/userCon/userById?url=userview&uid="+ obj.attr("userid");
	});
	
	$(".modifyUser").on("click",function(){
		var obj = $(this);
		window.location.href=path+"/userCon/userById?url=usermodify&uid="+ obj.attr("userid");
	});

	$('#no').click(function () {
		cancleBtn();
	});
	
	$('#yes').click(function () {
		deleteUser(userObj);
	});

	$(".deleteUser").on("click",function(){
		userObj = $(this);
		changeDLGContent("你确定要删除用户【"+userObj.attr("username")+"】吗？");
		openYesOrNoDLG();
	});
	
	/*$(".deleteUser").on("click",function(){
		var obj = $(this);
		if(confirm("你确定要删除用户【"+obj.attr("username")+"】吗？")){
			$.ajax({
				type:"GET",
				url:path+"/jsp/user.do",
				data:{method:"deluser",uid:obj.attr("userid")},
				dataType:"json",
				success:function(data){
					if(data.delResult == "true"){//删除成功：移除删除行
						alert("删除成功");
						obj.parents("tr").remove();
					}else if(data.delResult == "false"){//删除失败
						alert("对不起，删除用户【"+obj.attr("username")+"】失败");
					}else if(data.delResult == "notexist"){
						alert("对不起，用户【"+obj.attr("username")+"】不存在");
					}
				},
				error:function(data){
					alert("对不起，删除失败");
				}
			});
		}
	});*/
});

function AjaxUserSelect(AjaxId){
	$.ajax({
		type:"GET",
		url:path+"/userCon/AjaxSelectUser",
		data:{uid:AjaxId.attr("userid")},
		dataType:"json",
		success:function(data){
			if(data.user !=null){
				var userCode=data.user.userCode;//用户编号
				$("#userCode").text(userCode);
				var userName=data.user.userName;//用户姓名
				$("#userName").text(userName);
				var userGender=data.user.gender;//用户性别
				//判断性别
				if (userGender==1){
					$("#userGender").text("男")
				}else if (userGender==2){
					$("#userGender").text("女")
				}
				var userBirthday=data.user.birthday;//出生日期
				$("#userBirthday").text(userBirthday);
				var userPhone=data.user.phone;//用户电话
				$("#userPhone").text(userPhone);
				var userAddress=data.user.address;//用户地址
				$("#userAddress").text(userAddress);
				var userRoleName=data.user.userRoleName;//用户角色
				$("#userRoleName").text(userRoleName);
				$(".providerView").show();
			}else {
				$(".providerView").hide();
			}
		}
	})
}
