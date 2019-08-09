package com.shuhao.clean.apps.validate.vrules.msg;
/**
 * 校验返回消息，字段可以按需要扩充<br>
 * 1。[元数据ID]{0}执行[精度>4]校验错误，当前值[]
 * 2。[]执行[a+b+c>0]()校验不通过,结果[]
 * @author gongzhiyang
 *
 */
public abstract class Message implements IMessage{
	
	private String metaId ;//1.元数据ID
	private String metaName;//2.元数据描述
	
	private String messages;//3.提示消息
	
	private String formula ;//5.公式,解析后
	
	public Message(String ... args){
		if(args.length > 0){
			this.metaId  = args[0];
		}
		if(args.length > 1){
			this.metaName  = args[1];
		}
		if(args.length > 2){
			this.messages  = args[2];
		}
		if(args.length > 3){
			this.formula  = args[3];
		}
	}
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.msg.IMessage#getId()
	 */
	public String getId() {
		return this.metaId;
	}
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.msg.IMessage#getName()
	 */
	public String getName() {
		return this.metaName;
	}
	
	/**
	 * @return the formula
	 */
	public String getFormula() {
		return this.formula;
	}
	
	public String getMessages() {
		return messages;
	}
	//是否使用相同的返回结果
	public abstract String getMsg();
	
	/* (non-Javadoc)
	 * @see com.shuhao.clean.apps.validate.vrules.msg.IMessage#getShortMsg()
	 */
	public abstract String getShortMsg();
}
