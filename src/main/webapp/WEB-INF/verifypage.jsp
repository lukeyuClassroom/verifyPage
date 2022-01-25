<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="author" content="Weaver E-Mobile Dev Group">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="description" content="Weaver E-mobile">
	<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, minimum-scale=1.0">
	<meta name="keywords" content="weaver,e-mobile">
    <title>华为电子证明验真平台</title>
	<script type="text/javascript" src="./jquery.min_wev8.js"></script>
    <style type="text/css">
	.center {
		text-align: center;
		margin-left: auto;
		margin-right: auto;
	}
	.p-l-5 {
		padding-left: 5px;
	}
	table {
		display: table;
		border-collapse: separate;
		border-spacing: 2px;
		border-color: grey;
	}
	tbody {
		display: table-row-group;
		vertical-align: middle;
		border-color: inherit;
	}
	tr {
		display: table-row;
		vertical-align: inherit;
		border-color: inherit;
	}
	td {
		text-align: center;
		display: table-cell;
		vertical-align: inherit;
	}
	.overlabel {
		position: absolute;
		z-index: 1;
		font-size: 14px;
		left: 50px;
		font-size: 14px;
		color: #D5E7E4!important;
		line-height: 40px!important;
	}
	.input {
		left: 50px;
		top: 10px;
		height: 25px;
		width: 210px;
		background: transparent!important;
		border: 0px;
		position: absolute;
		font-size: 15px;
		outline: none;
	}
	.left {
		float: left!important;
	}
	.right {
		float: right;
	}
	.clear {
		clear: both;
	}
	.hand {
		cursor: pointer;
	}
	.h-10 {
    height: 10px;
}
    </style>
</head>
<body>

<iframe name="downloadFile" id="downloadFile" src="" style="display:none" ></iframe>
<div class="center" style="width: 100%">
		<div style="height:40px">&nbsp;</div>
		<div>
			<img class=" " style="border:0;margin:0" src="./hw_logo.png">
		</div>
		<div style="height:10px">&nbsp;</div>
		<div>
			<span style="font-weight: bold;font-size: 24pt!important;font-family: Microsoft YaHei!important;">华为电子证明验真平台</span>
		</div>
		<div style="height:80px">&nbsp;</div>
		<form id="form1" name="form1" action="/login/VerifyLogin.jsp" method="post">
		<div id="normalLogin" class="p-l-5">
			<table width="98%;">
				<tbody>
				<tr>
					<td>
					<span id='message' style="font-size: 9pt!important;height:38px; color:#FF4500;">&nbsp;</span>
					</td>
				</tr>
				<tr>
					<td>
					   <span style="font-size: 14pt!important;">验真码:</span>
					   <input type="text" name="verificode" id="verificode" style=";width:205px;height:38px;" value="">
					</td>
				</tr>
				<!-- 验证码 -->
				<tr>
					<td>
							<span style="font-size: 14pt!important;" >验证码:</span>
							<input type="text" id="validatecode" name="validatecode" class="input1" style=";width:100px;height:38px;">
						  	<a href="javascript:changeCode()"><img border="0" id="imgCode" align="absmiddle" style="width:90px;height:38px;margin-left:10px;" src="/verifycode"></a>
						  	<script>
						  	  var seriesnum_=0;
						  	 function changeCode(){
						  	 	seriesnum_++;
						  		setTimeout('$("#imgCode").attr("src", "/verifycode?"+seriesnum_)',50);
						  	 }
						  	</script>
					</td>									
				</tr>
				<tr>
					<td class="h-10">&nbsp;</td>
				</tr>
				<tr>
					<td>
					<div style="margin:0 auto;font-size: 10pt!important; width:300px;">
					<ul style="text-align: left;">
						<li>请在上方输入框中输入证明左上角的18位编码，C需要大写，点击提交按钮查阅对应的电子文档，推荐使用微信扫码。</li>
						<li>本文件已通过电子签名签署，与实物印章具有同等法律效力，不同打印设备造成的色差不影响使用效力。</li>
						<li>查询人对查询中涉及的个人隐私秘密负有保密义务，不得泄露给他人，也不得不正当使用。</li>
						<li>证明验真有效期为90天。</li>
					</ul>
					</div>
					</td>
				</tr>
				<tr>
					<td class="h-10">&nbsp;</td>
				</tr>
				<tr>
					<td>
						<input type="button" name="login" id="login" onclick="doVerifi(this)" value="提交验证" class="hand lgsm" tabindex="3" style="height: 45px; width: 180px; border: 0px; font-size: 14px;background-color:#1E90FF; color:#FFFFFF; letter-spacing: 2px; font-family: Arial, Helvetica, sans-serif, SimSun;">
					</td>
				</tr>
			</tbody>
			</table>
		</div>
	   </form>
	</div>
</body>
<script language="javascript">
function doVerifi(me){
	me.disable = true;
	var verificode = jQuery('#verificode').val();
	if(verificode === ''){
		jQuery('#message').text('验真码未填写');
		me.disable = false;
		return;
	}
	var validatecode = jQuery('#validatecode').val();
	if(validatecode === ''){
		jQuery('#message').text('验证码未填写');
		me.disable = false;
		return;
	}
	var url = 'api/huawei/browse/verifiCode';
	if(window.location.href.indexOf('staffservice.admin.huawei.com')>= 0){
		url = '/api/huawei/browse/verifiCode';
	}
    jQuery.ajax({
        url: url,
        data: {ecverificode:'ea461e8bc4aeb37410e52d2adc277f6aaa7bd7833dfeeed7',verificode:verificode,validatecode:validatecode,validateCodeKey:'huawei_verifi_1643093645181'},
        type: 'post',
        async: true,
		cache: false,
        dataType: 'json',
        success: function (data) {
			if(data.issuccess == 1){
				jQuery('#message').html('&nbsp;');
				if(window.location.href.indexOf('staffservice.admin.huawei.com')>= 0){
					window.location.href = data.url;
				}else{
					window.location.href = data.url.substr(1);
				}
			}else{
				jQuery('#message').text(data.msg);
			}
			changeCode();
			me.disable = false;
        },
        error: function () {
			jQuery('#message').text('接口调用异常');
			changeCode();
			me.disable = false;
        }
    });
}
</script>
</html>
