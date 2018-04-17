package com.hongdee.atlas.metadata.mysql;

import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.exception.MetadataException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Reflector {

    private JdbcTemplate sqlTemplate;
    public Reflector(JdbcTemplate sqlTemplate){
        this.sqlTemplate=sqlTemplate;
    }

    /**
     * 字段映射
     * @param metadataGroup
     * @return
     */
    public List<Metadata> getMetadatas(MetadataGroup metadataGroup){
        List<Metadata> metadatas = new ArrayList<>();
        Date now = new Date();
        sqlTemplate.query(String.format("show full fields from `%s`", metadataGroup.getName()), new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Metadata metadata = new Metadata();
                metadata.setDescription(resultSet.getString("comment"));
                metadata.setName(resultSet.getString("field"));
                metadata.setType(resultSet.getString("type"));
                String kt = resultSet.getString("key");
                if (StringUtils.isNotBlank(kt)) {
                    metadata.setKeyType(KeyType.valueOf(kt));
                }
                metadata.setMetadataGroup(metadataGroup);
                metadata.setNullable(resultSet.getBoolean("null"));
                metadata.setDefaultVal(resultSet.getString("default"));
                metadata.setCreateDatetime(now);
                metadatas.add(metadata);
            }});
        return metadatas;
    }

    /**
     * 删除字段
     * @param metadata
     */
    public void deleteMetadata(Metadata metadata){
        String table=metadata.getMetadataGroup().getName();
        String column=metadata.getName();
        sqlTemplate.execute(String.format("alter table %s drop column %s",table,column));
    }

    /**
     * 添加字段
     * @param metadata
     */
    public void addMetadata(Metadata metadata){
        String table=metadata.getMetadataGroup().getName();
        String column=metadata.getName();
        String type="varchar(255)";
        if(StringUtils.isNotBlank(metadata.getType())){
            type=metadata.getType();
        }
        String comment="";
        if(StringUtils.isNotBlank(metadata.getDescription())){
            comment="comment '"+metadata.getDescription()+"'";
        }
        String nul="NOT NULL";
        if(metadata.getNullable()!=null&&metadata.getNullable()){
            nul="NULL";
        }
        String def="";
        if(StringUtils.isNotBlank(metadata.getDefaultVal())){
            def="default '"+metadata.getDefaultVal()+"'";
        }
        String sql=String.format(" alter table `%s` add column `%s` %s %s %s %s",table,column,type,nul,def,comment);
        sqlTemplate.execute(sql);
    }

    /**
     * 修改字段
     * @param metadata
     */
    public void mdyMetadata(Metadata metadata){
        String table=metadata.getMetadataGroup().getName();
        String column=metadata.getName();
        String type="varchar(255)";
        if(StringUtils.isNotBlank(metadata.getType())){
            type=metadata.getType();
        }
        String comment="";
        if(StringUtils.isNotBlank(metadata.getDescription())){
            comment="comment '"+metadata.getDescription()+"'";
        }
        String nul="NOT NULL";
        if(metadata.getNullable()!=null&&metadata.getNullable()){
            nul="NULL";
        }
        String def="";
        if(StringUtils.isNotBlank(metadata.getDefaultVal())){
            def="default '"+metadata.getDefaultVal()+"'";
        }
        String sql=String.format(" alter table %s modify column `%s` %s %s %s %s",table,column,type,nul,def,comment);
        sqlTemplate.execute(sql);
    }

    /**
     * 修改表
     */
    public void mdyMetadataGroup(MetadataGroup metadataGroup,MetadataGroup newMetadataGroup){
        String oldTable=metadataGroup.getName();
        String newTable=newMetadataGroup.getName();
        String comment="";

        if(StringUtils.isNotBlank(newMetadataGroup.getDescription())&&(!newMetadataGroup.getDescription().equals(metadataGroup.getDescription()))){
            comment=newMetadataGroup.getDescription();
            sqlTemplate.execute(String.format(" alter table `%s` comment '%s'",oldTable,comment));
        }
        if(!metadataGroup.getName().equals(newMetadataGroup.getName())){
            if(!metadataGroup.isStandard()){
                sqlTemplate.execute(String.format(" alter table `%s` rename `%s`",oldTable,newTable));
            }
        }
    }

    /**
     * 新增表
     */
    public void addMetadataGroup(MetadataGroup metadataGroup){
        String table=metadataGroup.getName();
        String comment="";
        if(StringUtils.isNotBlank(metadataGroup.getDescription())){
            comment=metadataGroup.getDescription();
        }
        String sql=String.format("create table `%s` (",table);
        String primary=",";
        for(Metadata metadata:metadataGroup.getMetadatas()){
            String nul="NOT NULL";
            String def=null;
            if(KeyType.PRI.equals(metadata.getKeyType())){
                primary="primary key ("+metadata.getName()+"),";
            }
            if(StringUtils.isNotBlank(metadata.getDefaultVal())){
                def=" default `"+metadata.getDefaultVal()+"`";
            }
            if(metadata.getNullable()!=null&&metadata.getNullable()){
                nul="NULL";
            }
            sql+="`"+metadata.getName()+"` "
                    +metadata.getType()
                    +" "+
                    nul;
            if(StringUtils.isNotBlank(def)){
                sql+=def;
            }
            if(StringUtils.isNotBlank(metadata.getDescription())){
                sql+=" COMMENT "+ "'"+metadata.getDescription()+"'";
            }
            sql+=",";
        }
        sql+=primary;
        if(metadataGroup.getMetadatas().size()>0){
            sql=sql.substring(0,sql.length()-1);
        }
        sql+=")ENGINE=InnoDB DEFAULT CHARSET=utf8";
        if(StringUtils.isNotBlank(comment)){
            sql+=" comment='"+comment+"'";
        }
        sqlTemplate.execute(sql);
    }

    /**
     * 删除表
     * @param metadataGroup
     */
    public void deleteMetadataGroup(MetadataGroup metadataGroup){
        String table=metadataGroup.getName();
        sqlTemplate.execute(String.format("drop table `%s`",table));
    }



    /**
     * 获取表
     * @param module
     * @return
     */
    public List<MetadataGroup> getMetadataGroups(String module){
        List<MetadataGroup> metadataGroups=new ArrayList<>();
        Date now=new Date();
        sqlTemplate.query("show table status", (RowCallbackHandler) resultSet -> {
            MetadataGroup metadataGroup=new MetadataGroup();
            metadataGroup.setDescription(resultSet.getString("comment"));
            metadataGroup.setName(resultSet.getString("name"));
            metadataGroup.setModule(module);
            metadataGroup.setSyncDateTime(now);
            metadataGroups.add(metadataGroup);
        });
        return metadataGroups;

    }

}
