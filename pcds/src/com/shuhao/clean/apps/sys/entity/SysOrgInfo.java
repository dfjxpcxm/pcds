package com.shuhao.clean.apps.sys.entity;

import com.shuhao.clean.utils.exttree.ExtTreeNode;
import com.shuhao.clean.utils.exttree.ToExtTreeNode;

public class SysOrgInfo implements ToExtTreeNode {
    private String bank_org_id;
    private String bank_org_name;
    private String parent_bank_org_id;


    private Integer bank_org_level;


    public String getBank_org_id() {
        return bank_org_id;
    }

    public void setBank_org_id(String bank_org_id) {
        this.bank_org_id = bank_org_id;
    }

    public String getBank_org_name() {
        return bank_org_name;
    }

    public void setBank_org_name(String bank_org_name) {
        this.bank_org_name = bank_org_name;
    }

    public String getParent_bank_org_id() {
        return parent_bank_org_id;
    }

    public void setParent_bank_org_id(String parent_bank_org_id) {
        this.parent_bank_org_id = parent_bank_org_id;
    }

    /* (non-Javadoc)
     * @see com.rx.util.tree.TreeNode#getNodeID()
     */
    public String getNodeID() {
        // TODO Auto-generated method stub
        return this.bank_org_id;
    }

    /* (non-Javadoc)
     * @see com.rx.util.tree.TreeNode#getNodeName()
     */
    public String getNodeName() {
        // TODO Auto-generated method stub
        return this.bank_org_name;
    }

    /* (non-Javadoc)
     * @see com.rx.util.tree.TreeNode#getParentNodeID()
     */
    public String getParentNodeID() {
        // TODO Auto-generated method stub
        return this.parent_bank_org_id;
    }
    /* (non-Javadoc)
     * @see com.shuhao.clean.utils.exttree.ToExtTreeNode#conver2ExtTreeNode()
     */
    public ExtTreeNode conver2ExtTreeNode() {
        ExtTreeNode node = new ExtTreeNode();
        node.setId(this.bank_org_id);
//        String hasPublic = this.status_cd.equals("02") ? "" : "*";
//        node.setText(hasPublic + this.template_name + (this.template_type_cd.equals("01")?"[目录]":(this.enter_type_cd.equals("01")?"[全部补录]":"[部分补录]")));
        node.setText(bank_org_name);
        node.setLeaf(false);
        node.setIconCls(getIcon(String.valueOf(this.bank_org_level)));
        node.setExpanded(true);
//        node.setAttr1(this.template_type_cd);
        node.setAttr1(this.bank_org_id);
        return node;
    }

    private String getIcon(String type){
        if(type.equals("1")){
            return "folder_table";
        }else if(type.equals("2")){
            return "table";
        }else{
            return "stack";
        }
    }


    public void setBank_org_level(Integer bank_org_level) {
        this.bank_org_level = bank_org_level;
    }


    public Integer getBank_org_level() {
        return bank_org_level;
    }
}
