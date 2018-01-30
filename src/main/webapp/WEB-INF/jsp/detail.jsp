<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
   <head>
      <title>秒杀详情页</title>
   <%@include file="common/head.jsp"%>
   <body>
   		<div class="container">
   			<div class="panel panel-default text-center">
   				<div class="panel-heading">
   				<h1>${seckill.name }</h1>
   				</div>
   			<div class="panel-body">
   				<h2 class="text-danger">
   					<!-- 显示time图标 -->
   					<span class="glyphicon glyphicon-time"></span>
   					<!-- 显示倒计时 -->
   					<span class="glyphicon" id="seckill-box"></span>
   				</h2>
   			</div>
   			</div>
   		</div>
   	<!-- 登录弹出层，输入电话 -->
<div id="killPhoneModel" class="modal fade">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h3 class="modal-title text-center">
					<span class="glyphicon glyphicon-phone"></span>秒杀电话:
				</h3>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-8 col-xs-offset-2">
						<input type="text" name="killPhone" id="killPhoneKey" placeholder="填手机号^o^" class="form-control">
					</div>
				</div>
			</div>
			<div class="modal-footer">
			<!-- 验证信息 -->
				<span id="killPhoneMessage" class="glyphicon"></span>
				<button type="button" id="killPhoneBtn" class="btn btn-success">
					<span class="glyphicon glyphicon-phone"></span>
					Submit
				</button>
			</div>
		</div>
	</div>
</div>
   </body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- 使用cdn获取公共js http://www.bootcdn.cn -->
<!-- jquery cookie操作插件 -->
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jquery countDown倒计时插件 -->
<script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<!-- 开始编写交互逻辑 -->
<script type="text/javascript">
	$(function(){
		var seckill = {
				//封装秒杀相关ajax的url
				URL:{
					now:function(){
						return "${pageContext.request.contextPath }/seckill/time/now";
					},
					exposer:function(seckillId){
						return "${pageContext.request.contextPath }/seckill/"+seckillId+"/exposer";
					},
					execution:function(seckillId,md5){
						return "${pageContext.request.contextPath }/seckill/"+seckillId+"/"+md5+"/execution";
					}
				},
				validatePhone:function(phone){
					if(phone && phone.length==11&&!isNaN(phone)){
						return true;
					}else{
						return false;
					}
				},
				//处理秒杀逻辑
				handleSeckillkill:function(seckillId,node){
					//获取秒杀地址，控制实现逻辑，执行秒杀
					node.hide().html('<button class="btn btn-primary btn-lg" id=killBtn>开始秒杀</button>');
					$.post(seckill.URL.exposer(seckillId),{},function(result){
						if(result && result.success){
							var exposer = result['data'];
							//判断是否开启秒杀
							if(exposer['exposed']){
								//开启秒杀
								//获取秒杀地址
								var md5 = exposer['md5']
								var killUrl = seckill.URL.execution(seckillId,md5);
								console.log("killUrl:"+killUrl);
								$("#killBtn").one('click',function(){
									//执行秒杀请求
									//1.先禁用按钮
									$(this).addClass('disabled');
									//2.发送请求执行秒杀
									$.post(killUrl,{},function(result){
										if(result && result.success){
											var killResult = result['data'];
											var state = killResult['state'];
											var stateInfo = killResult['stateInfo'];
											//3.显示秒杀结果
											node.html('<span class="label label-success">'+stateInfo+'</span>');
										}
									});
								})
							}else{
								//未开启秒杀
								var now = exposer['now'];
								var start = exposer['start'];
								var end = exposer['end'];
								//重新计算计时逻辑
								seckill.countdown(seckillId,now,start,end);
							}
						}
					});
					node.show();
				},
				countdown:function(seckillId,nowTime,startTime,endTime){
					var seckillBox=$('#seckill-box');
					if(nowTime>endTime){
						//秒杀结束
						seckillBox.html('秒杀结束');
					}else if(nowTime<startTime){
						//秒杀未开始，计时事件绑定
						var killTime = new Date(startTime+1000);
						seckillBox.countdown(killTime,function(event){
							//时间格式
							var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒');
							seckillBox.html(format);
							//时间完成后回调事件
						}).on('finish.countdown',function(){
							//获取秒杀地址，控制实现逻辑，执行秒杀
							seckill.handleSeckillkill(seckillId,seckillBox);
						});
					}else{
						//秒杀开始
						seckill.handleSeckillkill(seckillId,seckillBox);
					};
				},
				//详情页秒杀逻辑
				detail:{
					//详情页初始化
					init : function(params){
						//手机验证和登录，计时交互
						var killPhone = $.cookie('killPhone');
						if(!seckill.validatePhone(killPhone)){
							//绑定phone
							var killPhoneModel = $('#killPhoneModel');
							killPhoneModel.modal({
								//显示弹出层
								show:true,
								//禁止位置关闭
								backdrop:'static',
								//关闭键盘事件
								keyboard:false
							});
							$('#killPhoneBtn').click(function(){
								var inputPhone = $('#killPhoneKey').val();
								if(seckill.validatePhone(inputPhone)){
									//电话写入cookie
									$.cookie('killPhone',inputPhone,{expires:7,path:'${pageContext.request.contextPath }'});
									//刷新页面
									window.location.reload();
								}else{
									$('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
								}
							});
						}
						var startTime = params['startTime'];
						var endTime = params['endTime'];
						var seckillId = params['seckillId'];
						//ajax获取后台当前系统时间
						 $.get(seckill.URL.now(),{},function(result){
							if(result && result.success){
								var nowTime = result.data;
								//时间判断
								seckill.countdown(seckillId,nowTime,startTime,endTime);
							}else{
								console.log('result'+result);
							}
						}); 
					
					}
				},
		}
	
		seckill.detail.init({
			seckillId : ${seckill.seckillId},
			startTime : ${seckill.startTime.time},//.time转成毫秒
			endTime : ${seckill.endTime.time}
		});
		
	});
</script>
</html>