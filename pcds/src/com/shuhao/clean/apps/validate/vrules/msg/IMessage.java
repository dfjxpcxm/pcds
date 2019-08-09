package com.shuhao.clean.apps.validate.vrules.msg;
/**
 * 校验返回消息，字段可以按需要扩充<br>
 * 1。[元数据ID]{0}执行[精度>4]校验错误，当前值[]
 * 2。[]执行[a+b+c>0]()校验不通过,结果[]
 * <br>
 * -MyMessage
 * <br>
 * -InvalidMessage
 * <br>
 * -SimpleMessage
 * <br>
 * --RegexMessage<br>
 * --SqlMessage<br>
 * --TableMessage<br>
 * @author gongzhiyang
 *
 */
public interface IMessage {
	//返回metaId
	public String getId();
	//是否使用相同的返回结果
	public String getMsg();
	//简单的消息
	public String getShortMsg(); 
	//
	public String getName();
}
