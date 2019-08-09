/**
 * FileName:     RexgxMessage.java
 * @Description: TODO 

 * Copyright:   Copyright(C) 2014-2015 
 * Company      数浩科技.
 * @author:     gongzhiyang
 * @version     V1.0  
 * Createdate:         2014-11-30 下午3:08:06 
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2014-11-30       author          1.0             1.0
 * Why & What is modified: 
 */

package com.shuhao.clean.apps.validate.vrules.msg;


/**
 * @Description:   TODO 无效的校验
 * 
 * @author:         gongzhiyang
 */
public class InvalidMessage extends Message {
	

	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.entity.Message#getMsg()
	 */
	public String getMsg() {
		return "["+getId()+"]配置了无效的校验器["+this.getMessages()+"].";
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.msg.IMessage#getShortMsg()
	 */
	public String getShortMsg() {
		return "无效的校验器["+getMessages()+"].";
	}
	
	public InvalidMessage(String  ...  args) {
		super(args);
	}
}
