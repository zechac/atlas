package com.hongdee.atlas.common.sql;

import com.hongdee.atlas.common.exception.AtlasSqlException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 本地化sql执行器
 */
public class SqlTemplate {

    private DataSource dataSource;

    public SqlTemplate(DataSource dataSource){
        this.dataSource=dataSource;
    }

    private void fillStatement(PreparedStatement statement,List param){
        try {
            for (int i=0;i<param.size();i++) {
                Object o=param.get(i);
                if(o.getClass().equals(String.class)){
                    statement.setString(i,o.toString());
                }else if(o instanceof Integer){
                    statement.setInt(i,((Integer) o).intValue());
                }else if(o instanceof Double){
                    statement.setDouble(i,((Double) o).doubleValue());
                }else if(o instanceof Date){
                    Timestamp timestamp=new Timestamp(((Date)o).getTime());
                    statement.setTimestamp(i,timestamp);
                }
            }
        } catch (SQLException e) {
            throw new AtlasSqlException(SqlTemplate.class,"查询参数赋值出错");
        }
    }

    public int executeUpdate(String sql,List param){
        Connection connection= null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            if(param!=null) {
                fillStatement(statement,param);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new AtlasSqlException(SqlTemplate.class,"获取数据连接失败");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new AtlasSqlException(SqlTemplate.class,"关闭数据连接失败");
            }
        }
    }

    public boolean execute(String sql,List param){
        Connection connection= null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            if(param!=null) {
                fillStatement(statement,param);
            }
            return statement.execute();
        } catch (SQLException e) {
            throw new AtlasSqlException(SqlTemplate.class,"获取数据连接失败");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new AtlasSqlException(SqlTemplate.class,"关闭数据连接失败");
            }
        }
    }

    public boolean execute(String sql,Object... param){
        return execute(sql,Arrays.asList(param));
    }

    public void executeQuery(String sql,QueryCallback queryCallback,List param){
        Connection connection= null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement= connection.prepareStatement(sql);
            if(param!=null) {
                fillStatement(statement,param);
            }
            ResultSet resultSet= statement.executeQuery(sql);
            if(queryCallback!=null){
                queryCallback.exec(resultSet);
            }
        } catch (SQLException e) {
            throw new AtlasSqlException(SqlTemplate.class,"获取数据连接失败");
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new AtlasSqlException(SqlTemplate.class,"关闭数据连接失败");
            }
        }
    }

}
