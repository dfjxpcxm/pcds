

MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},
	getModules : function(){
		//在开始菜单里添加窗口信息
		var obj = eval("["+extObject+"]");
		return obj;
	},

    // 添加菜单
    getStartConfig : function(){
        return {
            title: '快捷菜单',
            iconCls: 'user',
            toolItems: [{
                text:'当前用户',
                iconCls:'user',
                handler : function(){
                	 $("#div3").fadeIn(1000);
                }
            },'-',{
                text:'修改密码',
                iconCls:'setpwd',
                handler : function(){
                	modifyPassword();
                }
            },'-',{
                text:'锁定',
                iconCls:'lock',
                handler : function(){
                	logout(false);
                }
            },'-',{
                text:'退出',
                iconCls:'logout',
                handler : function(){
                	logout(true);
                }
            }]
        };
    }
});



function createWindow(obj) {
	var desktop = obj.app.getDesktop();
	var win = desktop.getWindow(obj.id);
	if (!win) {
		win = desktop.createWindow({
					id : obj.id,
					title : obj.text,
					width : 740,
					height : 480,
					//iconCls : obj.iconCls,
					sytle : 'background-image:url(../images/icons/add.png) !important',
					shim : false,
					animCollapse : false,
					constrainHeader : true,
					layout : 'fit',
					items : [new Ext.ux.IFrameComponent({
								region : 'center',
								url : basePath +'menus/'+obj.id
							})]
				});
	}
	win.maximize();//默认最大化
	win.show();
}
