/*
 * Ext JS Library 2.1
 * Copyright(c) 2006-2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */

/**
 * @class Ext.PagingToolbar
 * @extends Ext.Toolbar
 * A specialized toolbar that is bound to a {@link Ext.data.Store} and provides automatic paging controls.
 * @constructor
 * Create a new PagingToolbar
 * @param {Object} config The config object
 */
Ext.PagingToolbar = Ext.extend(Ext.Toolbar, {
    /**
     * @cfg {Ext.data.Store} store The {@link Ext.data.Store} the paging toolbar should use as its data source (required).
     */
    /**
     * @cfg {Boolean} displayInfo
     * True to display the displayMsg (defaults to false)
     */
	displayInfo: false,
    /**
     * @cfg {Number} pageSize
     * The number of records to display per page (defaults to 20)
     */
    pageSize: 10,
    /**
     * @cfg {String} displayMsg
     * The paging status message to display (defaults to "Displaying {0} - {1} of {2}").  Note that this string is
     * formatted using the braced numbers 0-2 as tokens that are replaced by the values for start, end and total
     * respectively. These tokens should be preserved when overriding this string if showing those values is desired.
     */
    displayMsg : '显示第 {0} 条 - {1} 条记录,共 {2} 条',
    /**
     * @cfg {String} emptyMsg
     * The message to display when no records are found (defaults to "No data to display")
     */
    emptyMsg : '没有数据',
    /**
     * Customizable piece of the default paging text (defaults to "Page")
     * @type String
     */
    beforePageText : "第",
    /**
     * Customizable piece of the default paging text (defaults to "of {0}"). Note that this string is
     * formatted using {0} as a token that is replaced by the number of total pages. This token should be 
     * preserved when overriding this string if showing the total page count is desired.
     * @type String
     */
    afterPageText : "页,共 {0} 页",
    /**
     * Customizable piece of the default paging text (defaults to "First Page")
     * @type String
     */
    firstText : "第一页",
    /**
     * Customizable piece of the default paging text (defaults to "Previous Page")
     * @type String
     */
    prevText : "上一页",
    /**
     * Customizable piece of the default paging text (defaults to "Next Page")
     * @type String
     */
    nextText : "下一页",
    /**
     * Customizable piece of the default paging text (defaults to "Last Page")
     * @type String
     */
    lastText : "最后一页",
    /**
     * Customizable piece of the default paging text (defaults to "Refresh")
     * @type String
     */
    refreshText : "刷新",

    /**
     * Object mapping of parameter names for load calls (defaults to {start: 'start', limit: 'limit'})
     */
     
    totalCount : 0,
    
    start : '0',
    
    limit : '10',
    
    dsLoaded : false ,
    
    filePath : 'filePath',
    
    paramNames : '0',

    initComponent : function(){
        Ext.PagingToolbar.superclass.initComponent.call(this);
        this.cursor = 0;
    },
    
    initProperties : function(totalCount,filePath,flag){
    	this.start = 0;
        this.limit = this.pageSize;
        this.totalCount = totalCount;
        this.filePath = filePath;
        this.dsLoaded = flag;
    },

    // private
    onRender : function(ct, position){
        Ext.PagingToolbar.superclass.onRender.call(this, ct, position);
        this.first = this.addButton({
            tooltip: this.firstText,
            iconCls: "x-tbar-page-first",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["first"])
        });
        this.prev = this.addButton({
            tooltip: this.prevText,
            iconCls: "x-tbar-page-prev",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["prev"])
        });
        this.addSeparator();
        this.addText(this.beforePageText);
        this.addField(this.inputItem = new Ext.form.NumberField({
			cls: 'x-tbar-page-number',
			allowDecimals: false,
			allowNegative: false,
			enableKeyEvents: true,
			selectOnFocus: true,
			submitValue: false,
			value: '1'
		}));
		this.inputItem.on("keydown", this.onPagingKeydown, this);
		this.inputItem.on("blur", this.onPagingBlur, this);
//		this.afterTextEl = this.addText(String.format(this.afterPageText, 1));
		this.inputItem.setHeight(18);
		this.afterTextItem = new Ext.Toolbar.TextItem({
			text: String.format(this.afterPageText, 1)
		});
		this.addItem(this.afterTextItem);
        this.addSeparator();
        this.next = this.addButton({
            tooltip: this.nextText,
            iconCls: "x-tbar-page-next",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["next"])
        });
        this.last = this.addButton({
            tooltip: this.lastText,
            iconCls: "x-tbar-page-last",
            disabled: true,
            handler: this.onClick.createDelegate(this, ["last"])
        });
        this.addSeparator();
        this.loading = this.addButton({
            tooltip: this.refreshText,
            iconCls: "x-tbar-loading",
            handler: this.onClick.createDelegate(this, ["refresh"])
        });

        if(this.displayInfo){
            this.displayEl = Ext.fly(this.el.dom).createChild({cls:'x-paging-info'});
        }

        if(this.dsLoaded){
            this.onLoad.apply(this);
        }
    },

    // private
    updateInfo : function(){
        if(this.displayEl){
            var count = this.totalCount;

            var msg = count == 0 ?
                this.emptyMsg :
                String.format(
                    this.displayMsg,
                    parseFloat(this.cursor)+1, 
                    parseFloat(this.cursor)+((parseFloat(this.totalCount) - parseFloat(this.cursor)) < parseFloat(this.limit)?(parseFloat(this.totalCount) - parseFloat(this.cursor)):parseFloat(this.limit))
                    , this.totalCount
                );
            this.displayEl.update(msg);
        }
    },

    // private
    onLoad : function(r){

		this.cursor = this.start ;
		var d = this.getPageData(), ap = d.activePage, ps = d.pages;
		
//		this.afterTextEl.el.innerHTML = String.format(this.afterPageText, d.pages);
		this.afterTextItem.setText(String.format(this.afterPageText, d.pages));
		this.inputItem.setValue(ap);
		this.first.setDisabled(ap == 1);
		this.prev.setDisabled(ap == 1);
		this.next.setDisabled(ap == ps);
		this.last.setDisabled(ap == ps);
		this.loading.enable();
		this.updateInfo();
    },

    // private
    getPageData : function(){
        var total = this.totalCount;
        return {
            total : total,
            //activePage : Math.ceil((this.cursor+this.pageSize)/this.pageSize),
            activePage : Math.ceil((parseFloat(this.cursor)+parseFloat(this.pageSize))/parseFloat(this.pageSize)),
            pages :  total < this.pageSize ? 1 : Math.ceil(total/this.pageSize)
        };
    },

    // private
    onLoadError : function(){
        if(!this.rendered){
            return;
        }
        this.loading.enable();
    },

    readPage : function(d){
        var v = this.inputItem.getValue(), pageNum;
        if (!v || isNaN(pageNum = parseInt(v, 10))) {
            this.inputItem.setValue(d.activePage);
            return false;
        }
        return pageNum;
    },

    // private
    onPagingKeydown : function(obj, e){
        var k = e.getKey(), d = this.getPageData(), pageNum;
        if (k == e.RETURN) {
            e.stopEvent();
            if(pageNum = this.readPage(d)){
                pageNum = Math.min(Math.max(1, pageNum), d.pages) - 1;
                this.doLoad(pageNum * this.pageSize);
            }
        }else if (k == e.HOME || k == e.END){
            e.stopEvent();
            pageNum = k == e.HOME ? 1 : d.pages;
            this.inputItem.setValue(pageNum);
        }else if (k == e.UP || k == e.PAGEUP || k == e.DOWN || k == e.PAGEDOWN){
            e.stopEvent();
            if(pageNum = this.readPage(d)){
                var increment = e.shiftKey ? 10 : 1;
                if(k == e.DOWN || k == e.PAGEDOWN){
                    increment *= -1;
                }
                pageNum += increment;
                if(pageNum >= 1 & pageNum <= d.pages){
                    this.inputItem.setValue(pageNum);
                }
            }
        }
    },
    
    // private
    onPagingBlur : function(obj){
    	this.inputItem.setValue(this.getPageData().activePage);
    },

    // private
    beforeLoad : function(){
        if(this.rendered && this.loading){
            this.loading.disable();
        }
    },

    doLoad : function(start){

		this.start = start;
		this.limit = this.pageSize;
		
		var index = this.filePath.indexOf("?start=");
		if(index != -1)
			this.filePath = this.filePath.substring(0,index)+ "?start="+this.start+"&limit="+this.limit;
		
		index = this.filePath.indexOf("&start=");
		if(index != -1)
			this.filePath = this.filePath.substring(0,index)+ "&start="+this.start+"&limit="+this.limit;
		
		load(this.filePath);
		this.onLoad(this);
    },

    // private
    onClick : function(which){
        //var store = this.store;
        switch(which){
            case "first":
                this.doLoad(0);
            break;
            case "prev":
                this.doLoad(Math.max(0, this.cursor-this.pageSize));
            break;
            case "next":
                //this.doLoad(this.cursor+this.pageSize);
                this.doLoad(parseFloat(this.cursor)+parseFloat(this.pageSize));
            break;
            case "last":
                var total = this.totalCount;
                var extra = total % this.pageSize;
                var lastStart = extra ? (total - extra) : total-this.pageSize;
                this.doLoad(lastStart);
            break;
            case "refresh":
                this.doLoad(this.cursor);
            break;
        }
    },

    /**
     * Unbinds the paging toolbar from the specified {@link Ext.data.Store}
     * @param {Ext.data.Store} store The data store to unbind
     */
    unbind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.un("beforeload", this.beforeLoad, this);
        store.un("load", this.onLoad, this);
        store.un("loadexception", this.onLoadError, this);
        this.store = undefined;
    },

    /**
     * Binds the paging toolbar to the specified {@link Ext.data.Store}
     * @param {Ext.data.Store} store The data store to bind
     */
    bind : function(store){
        store = Ext.StoreMgr.lookup(store);
        store.on("beforeload", this.beforeLoad, this);
        store.on("load", this.onLoad, this);
        store.on("loadexception", this.onLoadError, this);
        this.store = store;
    }
});
Ext.reg('paging', Ext.PagingToolbar);