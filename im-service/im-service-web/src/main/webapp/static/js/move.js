	function getStyle(obj,attr){
		if (obj.currentStyle) {
			return obj.currentStyle[attr];
		}else{
			return getComputedStyle(obj,false)[attr];
		}
	}



	function startMove(obj,json,fn){
		var flag=true;
		clearInterval(obj.timer);
		obj.timer=setInterval(function(){
			for (var attr in json){
			//1.取当前的值
			var cur=0;
			if (attr=='opacity') {
				//cur=parseFloat(getStyle(obj,attr))*100;
				cur=Math.round(parseFloat(getStyle(obj,attr))*100);
			}
			else{
				var cur=parseInt(getStyle(obj,attr));
			}
			//2.算速度
			var speed=(json[attr]-cur)/8;
			speed=speed>0?Math.ceil(speed):Math.floor(speed);
			//3.检测停止
			if (cur!=json[attr]) {
				flag=false;
			}
			
			if (attr=='opacity') {
				obj.style.filter='alpha(opacity:'+(cur+speed)+')';
				obj.style.opacity=(cur+speed)/100;
			}
			else{
			//使用[],不使用.
			obj.style[attr]=cur+speed+'px';
			}
			if (flag) {
				clearInterval(timer);
				if (fn) {
					fn();
				}
			}
			
		}
		},30)
	}
