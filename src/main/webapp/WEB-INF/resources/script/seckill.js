//存放主要交互逻辑js代码
//javascript 模块化
var seckill = {
		//封装秒杀相关ajax的url
		URL:{
			
		},
		//详情页秒杀逻辑
		detail:{
			//详情页初始化
			init : function(params){
				alert("11");
				//手机验证和登录，计时交互
				var kileePhone = $.cookie('killPhone');
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				var seckillId = params['seckillId'];
			}
		},
		i:function(){
			alert("123");
		}
}