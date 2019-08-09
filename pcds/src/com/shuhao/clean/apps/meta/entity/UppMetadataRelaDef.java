package com.shuhao.clean.apps.meta.entity;

/**
 * 
 * 类描述: 元数据上下级关系定义
 * 
 * @author chenxiangdong 创建时间：2014-12-30下午04:57:29
 */
public class UppMetadataRelaDef {

	private String prt_md_cate_cd;
	private String md_cate_cd;
	private String allow_child_cnt;

	public String getPrt_md_cate_cd() {
		return prt_md_cate_cd;
	}

	public void setPrt_md_cate_cd(String prtMdCateCd) {
		prt_md_cate_cd = prtMdCateCd;
	}

	public String getMd_cate_cd() {
		return md_cate_cd;
	}

	public void setMd_cate_cd(String mdCateCd) {
		md_cate_cd = mdCateCd;
	}

	public String getAllow_child_cnt() {
		return allow_child_cnt;
	}

	public void setAllow_child_cnt(String allowChildCnt) {
		allow_child_cnt = allowChildCnt;
	}

}
