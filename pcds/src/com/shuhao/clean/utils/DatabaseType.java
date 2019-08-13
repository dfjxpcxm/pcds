package com.shuhao.clean.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.shuhao.clean.apps.meta.entity.DbColumn;
/**
 * 
 * 类描述: 数据库类型枚举类
 * @author chenxiangdong
 * 创建时间：2015-1-9上午11:29:55
 */
public enum DatabaseType {

	Oracle("00", "oracle.jdbc.driver.OracleDriver") {
		public String queryTableColumnSql() throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer(1000);
			sql.append("select a.column_name as column_name,");
			sql.append("m.comments as column_desc,");
			sql.append("case ");
			sql.append("	when a.data_type = 'VARCHAR2' or a.data_type = 'CHAR' then '01' ");
			sql.append("	when a.data_type = 'NUMBER' then '02' ");
			sql.append("	when a.data_type = 'DATE' then '03' ");
			sql.append("	else '' ");
			sql.append("end as data_type_cd, ");
			sql.append("a.data_length as data_length, ");
			sql.append("a.data_scale as data_scale, ");
			sql.append("case when c.column_name is not null then 'Y' else 'N' end as is_pk, ");
			sql.append("a.nullable as is_nullable ");
			sql.append("from user_tab_columns a ");
			sql.append("left join user_constraints s ");
			sql.append("on a.table_name = s.table_name ");
			sql.append("and s.constraint_type = 'P' ");
			sql.append("left join user_cons_columns c ");
			sql.append("on a.table_name = c.table_name ");
			sql.append("and a.column_name = c.column_name ");
			sql.append("and s.constraint_name = c.constraint_name ");
			sql.append("left join user_col_comments m ");
			sql.append("on a.table_name = m.table_name ");
			sql.append("and a.column_name = m.column_name ");
			sql.append("where a.table_name = ? ");
			sql.append("order by a.column_id");
			return sql.toString();
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer(300);
			sql.append("select count(*) as col_count ");
			sql.append("from all_tab_columns a ");
			sql.append("where a.owner = ? and a.table_name = ? ");
			sql.append("and column_name = ?");
			return sql.toString();
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			String[] sqls = new String[2];
			StringBuffer sql = new StringBuffer(300);
			sql.append("alter table ").append(dbColumn.getTable_name()).append(" add (");
			sql.append(dbColumn.getColumn_name()).append(" ").append(parseDataType(dbColumn.getData_type_cd(), dbColumn.getData_length()));
			if(!"Y".equals(dbColumn.getIs_nullable())) {
				sql.append(" not null");
			}
			sql.append(")");
			sqls[0] = sql.toString();
			sqls[1] = "comment on column upp_datatype .data_type_name is '" + dbColumn.getColumn_desc() + "'";
			return sqls; 
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return new String[]{"comment on column "+dbColumn.getTable_name()+"."+dbColumn.getColumn_name()+" is '" + dbColumn.getColumn_desc() + "'"};
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("a.table_name,");
			sql.append("b.comments as table_desc ");
			sql.append("from user_tables a, user_tab_comments b ");
			sql.append("where a.table_name = b.table_name ");
			if(searchKey != null) {
				sql.append("where a.table_name like '%").append(searchKey.toUpperCase()).append("%' ");
			}
			sql.append("order by a.table_name ");
			return sql.toString();
		}
	}, 
	DB2("01", "com.ibm.db2.jcc.DB2Driver") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			return null;
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			return null;
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			return null;
		}
	}, 
	SQLServer("02", "") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			return null;
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			return null;
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			return null;
		}
	}, 
	Infomix("03", "") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			return null;
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			return null;
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			return null;
		}
	}, 
	MySQL("04", "com.mysql.jdbc.Driver") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer(1000);
			sql.append("select a.column_name as column_name,");
			sql.append("a.column_comment as column_desc,");
			sql.append("case ");
			sql.append("	when a.data_type = 'VARCHAR' or a.data_type = 'CHAR' then '01' ");
			sql.append("	when a.data_type = 'DECIMAL' then '02' ");
			sql.append("	when a.data_type = 'DATETIME' then '03' ");
			sql.append("	else '' ");
			sql.append("end as data_type_cd, ");
			sql.append("a.character_octet_length as data_length, ");
			sql.append("a.numeric_scale as data_scale, ");
			sql.append("case when a.column_name is not null then 'Y' else 'N' end as is_pk,");
			//sql.append("a.is_nullable ");
			sql.append("case ");
			sql.append("	when a.is_nullable = 'yes'  then 'Y' ");
			sql.append("	when a.is_nullable = 'NO' then 'N' ");
			
			sql.append("	else '' ");
			sql.append("end as is_nullable ");
			
			sql.append("from INFORMATION_SCHEMA.COLUMNS  a ");
			sql.append("where a.table_name = ? ");
			sql.append("order by a.ordinal_position");
			return sql.toString();
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer("");
			sql.append("select count(*) as col_count from information_schema.COLUMNS ");
			sql.append("where table_name = ? ");
			sql.append("and column_name = ?");
			return sql.toString();
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			String[] sqls = new String[1];
			StringBuffer sql = new StringBuffer(300);
			sql.append("alter table ").append(dbColumn.getTable_name()).append(" add ");
			sql.append(dbColumn.getColumn_name()).append(" ").append(parseDataType(dbColumn.getData_type_cd(), dbColumn.getData_length()));
			if(!"Y".equals(dbColumn.getIs_nullable())) {
				sql.append(" not null");
			}
			sql.append(" comment '"+ dbColumn.getColumn_desc()+"' ");
			sqls[0] = sql.toString();
			return sqls;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {

			return new String[]{"ALTER TABLE  "+dbColumn.getTable_name()+" MODIFY COLUMN "+dbColumn.getColumn_name()+" "+parseDataType(dbColumn.getData_type_cd(), dbColumn.getData_length())+" COMMENT '" + dbColumn.getColumn_desc() + "'"};
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			StringBuffer sql = new StringBuffer();
			sql.append("select ");
			sql.append("a.table_name,");
			sql.append("a.table_comment as table_desc ");
			sql.append("from information_schema.tables a ");
			//sql.append("where a.table_name = b.table_name ");
			if(searchKey != null) {
				sql.append("where a.table_name like '%").append(searchKey.toUpperCase()).append("%' ");
			}
			sql.append("order by a.table_name ");
			return sql.toString();
		}


	}, 
	Sybase("05", "") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			return null;
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			return null;
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			return null;
		}
	}, 
	Teradata("06", "") {

		public String queryTableColumnSql() throws IllegalArgumentException {
			return null;
		}
		
		public String queryColumnExistsSql() throws IllegalArgumentException {
			return null;
		}
		
		public String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException {
			return null;
		}
		
		public String listTablesSql(String searchKey) throws IllegalArgumentException {
			return null;
		}
	};

	private String type;
	private String dirverClass;

	private DatabaseType(String db_type, String driverClass) {
		this.type = db_type;
		this.dirverClass = driverClass;
	}

	private String getTypeValue() {
		return this.type;
	}
	
	/**
	 * 获取数据库连接
	 * @param url 连接字符串
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	public Connection getDBConnection(String url, String username, String password) throws Exception {
		Connection conn = null;
		try {
			Class.forName(this.dirverClass);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return conn;
	}

	/**
	 * 测试连接
	 * 
	 * @param url 数据库连接url
	 * @param testUserName 测试用户名
	 * @param testPassword 测试密码
	 * @return
	 * @throws IllegalArgumentException
	 */
	public boolean testConnect(String url, String testUserName, String testPassword) throws IllegalArgumentException {
		boolean flag = false;
		try {
			Class.forName(this.dirverClass);
			DriverManager.getConnection(url, testUserName, testPassword);
			flag = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		return flag;
	}
	
	/**
	 * 根据数据库类型字符串获取对应的数据库类型对象
	 * @param db_type
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static DatabaseType getValue(String db_type) throws IllegalArgumentException {
		DatabaseType[] typeArray = DatabaseType.values();
		for (DatabaseType dbType : typeArray) {
			if (dbType.getTypeValue().equals(db_type)) {
				return dbType;
			}
		}
		throw new IllegalArgumentException("错误的数据库类型:[" + db_type + "]");
	}
	
	protected String parseDataType(String dataTypeCd, String length) {
		if("01".equals(dataTypeCd))
			return "varchar("+length+")";
		else if("02".equals(dataTypeCd))
			return "number";
		else
			return "date";
	}
	
	/**
	 * 对应数据库类型查询表信息sql
	 * @return
	 * @throws IllegalArgumentException
	 */
	public abstract String queryTableColumnSql() throws IllegalArgumentException;
	
	/**
	 * 查询表的列字段是否存在Sql
	 * @return
	 * @throws IllegalArgumentException
	 */
	public abstract String queryColumnExistsSql() throws IllegalArgumentException;
	
	/**
	 * 添加列的sql
	 * @return
	 * @throws IllegalArgumentException
	 */
	public abstract String[] addColumnSqls(DbColumn dbColumn) throws IllegalArgumentException;
	
	/**
	 * 修改列的sql
	 * @return
	 * @throws IllegalArgumentException
	 */
	public abstract String[] updateColumnSqls(DbColumn dbColumn) throws IllegalArgumentException;
	
	/**
	 * 查询表列表Sql
	 * @return
	 * @throws IllegalArgumentException
	 */
	public abstract String listTablesSql(String searchKey) throws IllegalArgumentException;


	public static void main(String[] args) {
		System.out.println(DatabaseType.Oracle.queryTableColumnSql());
	}
}
