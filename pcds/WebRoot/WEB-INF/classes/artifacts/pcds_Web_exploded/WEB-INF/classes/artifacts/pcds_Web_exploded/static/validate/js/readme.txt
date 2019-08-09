MyComponent = Ext.extend(Ext.some.component, { 
	//缺省构造参数，可被自定义设置覆盖  
	propA: 1, 
	initComponent: function(){  
		//在组件初始化期间调用的代码 
		//因为配置对象应用到了“this”，所以属性可以在这里被覆盖，或者添加新的属性 
		//（如items,tools,buttons） 
		Ext.apply(this, {
			propA: 3 
		});
		//调用父类代码之前
		//调用父类构造函数（必须）
		MyComponent.superclass.initComponent.apply(this, arguments);
		//调用父类代码之后 
		//如：设置事件处理和渲染组件 
	},  
	//覆盖其他父类方法
	onRender: 
		function(){ 
		//调用父类代码之前
		//调用父类相应方法（必须）
		MyScope.superclass.onRender.apply(this, arguments); 
		//调用父类代码之后  
	}}); 
//注册成xtype以便能够延迟加载
Ext.reg('mycomponentxtype', MyComponent);

 上面是一个启发例子，如果你用以下任意例子创建以上代码的实例 ：
var myComponent = new MyComponent(  propA: 2});
//或者延迟加载:{..  items: {xtype: 'mycomponentxtype', propA: 2}..}