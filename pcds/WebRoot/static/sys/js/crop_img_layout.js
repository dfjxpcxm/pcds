
/**
 * 初始化页面

Ext.onReady(function(){
	  btn = new Ext.Button({
	    text: "Insert Image",
		handler: showChooseWin,
        renderTo: 'buttons'
    });
    
});
 */
var chooseWindow;
/**
 * 删除临时图片标识
 * 隐藏切图窗口后 会删除已上传的图片  但页面显示存在 无法继续保存 该标志用于区别此状态
 * 在上传完图片没有截图直接取消关闭窗口时 
 * 要主动请求删除临时图片 删除完图片设置该标识
 * 上传图片成功后  需要重置该标识
 *  
 */
var isDeleted = false;

/**
 * 弹出截图窗口
 */
function showChooseWin(){
	if(chooseWindow){
		chooseWindow.show();
		return;
	}
	
	var infoFormPanel = new Ext.form.FormPanel({  
				region:'north',
   				baseCls: 'x-plain',  
	   			labelWidth: 60,  
	   			height : 35,
   				fileUpload:true,  
   				bodyStyle : 'padding:5px 5px 0px 5px',
   				url: 'upload',//上传文件
   				items: [
/*   					{
   					xtype: 'textfield',  
   					fieldLabel: '图标文件',
   					name: 'upload',  
   					id:'upload',
   					inputType: 'file',  
   					allowBlank: false,  
   					blankText:'必须选择一个文件进行上传。',
   					anchor: '90%' ,
   					autoCreate : {  
			            tag : "input",  
			            type : "file",  
			            size : "20"
			            autocomplete : "off",  
			            onChange : "afterSelectImg(this);"  
			        }
     			},*/
     			new Ext.Button({
				    text: "上传文件",
					handler: openUpload
//			        renderTo: 'buttons'
			    })
     	
     			
     			]   
   			})
	
	var cropPanel = new Ext.Panel({
		id : 'cropPanel',
		region : 'center',
		split : false,
		title : '请选择裁剪区域',
		//autoScroll :true,
		layout : 'fit',
		border:false,
		html : '<img  id="cropImg" style="height:auto;"/>'
	});
	
	var resultPanel  = new Ext.form.FormPanel({
		id:'resultPanel',
		region:'east',
		width:'200',
		border:false,
		title : '最终显示结果',
		frame : true,
		layout : 'form',
		layout: 'absolute',
		items:[
			{
			    x: 70,
			    y: 120,
			    html:'<div id="resultDiv"  style="display:none;width:48px; height:48px; overflow:hidden;"><img  id="crop_preview" /> </div>'
//			    contentEl : 'resultDiv'
			},
			{
				xtype:'hidden',
				id: 'x',
				name: 'x'
			},
			{
				xtype:'hidden',
				id: 'y',
				name: 'y'
			},
			{
				xtype:'hidden',
				id: 'w',
				name: 'w'
			},
			{
				xtype:'hidden',
				id: 'h',
				name: 'h'
			}
		]
		
	});
	
	chooseWindow = new Ext.Window({
		width : 640,
		height : 500,
		layout : 'border',
		title : '选择功能图标',
		modal : true,
		split : false,
		buttonAlign : 'center',
		id:'chooseWindow',
		resizable:false,
		border:false,
		listeners : {
			beforeclose : function(){//点击关闭按钮
			 	chooseWindow.hide();
				return false;
			}
		},
		items : [infoFormPanel,cropPanel,resultPanel],
		buttons : [{
			text : '保存',
			handler : function() {
				if(isDeleted){
					Ext.Msg.alert('提示','图片已无效，请重新上传');
					return;
				}
				if(!imgName){
					Ext.Msg.alert('提示','请先上传图片');
					return;
				}
				var x = Ext.getCmp('x').getValue();
				var y = Ext.getCmp('y').getValue();
				var w = Ext.getCmp('w').getValue();
				var h = Ext.getCmp('h').getValue();
//				Ext.Msg.alert('提示','x:'+x+' y:'+y+' w:'+w+' h:'+h);
				
				Ext.Ajax.request({  
	                    url : pathUrl+'/crop/crop',  
	                    form :'upForm',//formUpload.form 
	                    params:{x:x,y:y,w:w,h:h,imgName:imgName,viewWidth:maxWidth,viewHeight:Math.round(maxHeight)},
	                    callback:function(options,success,response){
	                    	var json = Ext.util.JSON.decode(response.responseText);
	                    	if(json.success){
	                    		setIconValue(json.imgName);
	                    		
	                    		chooseWindow.hide();
	                    		isDeleted = true;
	                    	}
	                    	Ext.MessageBox.alert("提示信息", json.info);
	                    }
				}
				);
				
				
			}
		}, {
			text : '取消',
			handler : function() {
				chooseWindow.hide();
				deleteTempFile();
			}
		}]
	});
	//显示截图窗口
	chooseWindow.show();
//	chooseWindow.on('close',function(){
//		alert();
//		chooseWindow.hide();
//		deleteTempFile();
//	});
}
var imgName = "";
var srcWidth = 0;
var srcHeight = 0;

//选中新的图片后
function loadImg(respText){
	if(!respText){
		return;
	}
	//获取文件名称 
	imgName = respText.split('@')[0];
	srcWidth = respText.split('@')[1];
	srcHeight = respText.split('@')[2];
	
	//结果区域显示
	Ext.getDom('resultDiv').style.display='';
//	Ext.getDom('cropDiv').style.display='';
	
	//重置高度 宽度 
	Ext.getDom('cropImg').style.width=getViewPara().width+'px';
	Ext.getDom('cropImg').style.height=getViewPara().height+'px';
	
	//裁剪区域图片设置路径
	var imgPath = '../../upload/temp/'+imgName;
	Ext.getDom('cropImg').src=imgPath;
	//结果区域图片设置路径默认值
	Ext.getDom('crop_preview').src=imgPath;
	
	//截图初始化
	startCrop();
}
	
//选择上传文件,进行上传
function openUpload(domId){   
	var formUpload ;
	var uploadWin = new Ext.Window({  
   		title: '资源上传',  
   		width: 400,  
   		height:150,  
   		layout: 'fit',  
   		plain:true,
   		modal:true,
   		bodyStyle:'padding:5px;',  
   		buttonAlign:'center',  
   		items : [
   			formUpload = new Ext.form.FormPanel({  
   				baseCls: 'x-plain',  
	   			labelWidth: 40,  
	   			id:'uploadWinForm',
   				fileUpload:true,  
   				url: pathUrl+'/crop/upload'//上传文件
//   				html:'<form id="upForm"> 请选择文件:<input type="file" name="file1"></form>'
//   			contentEl : 'upForm'
   				 
   			})
   		],
   		
   		buttons: [{  
   			text: '上传',  
   			handler: function() {  
   				
//   				var uploadFileVal = Ext.getCmp('upload').getValue();
//   				var uploadFileExt = uploadFileVal.substring(uploadFileVal.lastIndexOf('.')+1); 
//   				if(uploadFileExt != 'jpg' ){
//   					Ext.Msg.alert('提示','文件格式错误，系统目前只支持jpg格式的文件。');
//   					return;
//   				}
   				
   				Ext.Ajax.request({  
	                    url : pathUrl+'/crop/upload',  
	                    isUpload : true,  
	                    form :'upForm',//formUpload.form 
	                    success:function(response){
	                    //	Ext.MessageBox.alert("提示信息", response.responseText);
	                    	
	                    	var respText = response.responseText;
	                    	uploadWin.destroy(); 
	                    	loadImg(respText);
	                    	
	                    	isDeleted = false;
	                    }
	                });  
   				
   				/*if(formUpload.form.isValid()){  
   			 		Ext.MessageBox.show({  
   						title: '请稍后',  
   						msg: '资源上传中...',  
   						progressText: '',  
   						width:300,  
   						progress:true,  
   						closable:false,  
   						animEl: 'loding'  
   					});   
   					formUpload.getForm().submit(
   						{      
             			success: function(form, action){   
                			Ext.Msg.alert('成功','上传成功！');    
                			var info = action.result.info;
	                    	alert(info);
	                    	loadImg(info);
	                    	uploadWin.destroy(); 
                			
             			},         
              			failure: function(form, action){
              				Ext.Msg.alert('失败', '上传失败！');    
              			}
           			}
           			)   
         		} */
   				
   				
        	}  
     	},{     
       		text: '取消',  
       		handler:function(){uploadWin.destroy();}   
        }]   
    });  
	uploadWin.show() ;
	//由于html属性失效 所以采取如下方式初始化
	var uploadWinForm = Ext.getDom('uploadWinForm');
	uploadWinForm.innerHTML='<form id="upForm"> 请选择文件:<input type="file" name="file1"></form>';
	
}

/**
 * 计算适合显示的长宽
 */
function getViewPara(){
	var paraObj = new Object();
	if(srcWidth == 0){
		return;
	}
	paraObj.height = Math.round(getMaxHeight());
	paraObj.width = maxWidth;
	return paraObj;
}

//弹出窗口允许的最大宽高
var maxWidth = 420;
var maxHeight = 400;

/**
 * 递归方法根据比例获取最大高度 
 * 用于原比例显示 
 * 锁定宽度求得高度  对于不合适的高度在适当缩小比例
 */
function getMaxHeight (){
	var tempHeight = maxWidth*srcHeight/srcWidth;
	if(tempHeight >400){//对于不合适的高度 重新缩小宽度继续求得高度
		maxWidth = maxWidth - 25;
		 maxHeight = getMaxHeight ();
	}else{
		maxHeight = tempHeight;
	}
	return tempHeight;
	
}

/**
 * 设置图表文本框的值
 * @param imgName
 */
function setIconValue(imgName){
	Ext.getCmp('icon_name').setValue(imgName);
}


/**
 * 提交请求 删除临时文件夹下的图片
 */
function deleteTempFile(){
	if(!imgName){
		return;
	}
	Ext.Ajax.request({
		url:pathUrl+'/crop/deleteTempFile',
		params:{imgName:imgName},
		callback:function(options,success,response){
			var json = Ext.util.JSON.decode(response.responseText);
			if(json.success){
				isDeleted = true;
				imgName = '';
			}else{
				Ext.Msg.alert('提示',json.info);
			}
		}
	});
}

