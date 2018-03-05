package com.hongdee.atlas.metadata.mysql;

import com.hongdee.atlas.common.sql.QueryCallback;
import com.hongdee.atlas.common.sql.SqlTemplate;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import com.hongdee.atlas.metadata.exception.MetadataException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Reflector {

    private SqlTemplate sqlTemplate;
    public Reflector(SqlTemplate sqlTemplate){
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
        sqlTemplate.executeQuery(String.format("show full fields from %s", metadataGroup.getName()), new QueryCallback() {
            @Override
            public void exec(ResultSet resultSet) {
                try {
                    while (resultSet.next()) {
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
                    }

                }catch (Exception e){
                    throw new MetadataException(Reflector.class,"映射元数据失败");
                }
            }
        },null);
        return metadatas;
    }

    /**
     * 删除字段
     * @param metadata
     */
    public void deleteMetadata(Metadata metadata){
        String table=metadata.getMetadataGroup().getName();
        String column=metadata.getName();
        sqlTemplate.execute(String.format(" alter table %s drop column %s"),table,column);
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
            comment=metadata.getDescription();
        }
        sqlTemplate.execute(String.format(" alter table %s add column %s %s comment '%s'"),table,column,type,comment);
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
            comment=metadata.getDescription();
        }
        sqlTemplate.execute(String.format(" alter table %s modify column %s %s comment '%s'"),table,column,type,comment);
    }

    /**
     * 修改表
     */
    public void mdyMetadataGroup(MetadataGroup metadataGroup,MetadataGroup newMetadataGroup){
        String oldTable=metadataGroup.getName();
        String newTable=newMetadataGroup.getName();
        String comment="";
        if(StringUtils.isNotBlank(newMetadataGroup.getDescription())){
            comment=newMetadataGroup.getDescription();
        }
        sqlTemplate.execute(String.format(" alter table %s rename %s comment '%s'"),oldTable,newTable,comment);
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
        String sql=String.format("cretae table `%s` (",table);
        for(Metadata metadata:metadataGroup.getMetadatas()){
            String nul="NOT NULL";
            String def=null;
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
            if(StringUtils.isNotBlank(comment)){
                sql+=comment;
            }

        }
        sqlTemplate.execute(sql);
    }

    /**
     * 删除表
     * @param metadataGroup
     */
    public void deleteMetadataGroup(MetadataGroup metadataGroup){
        String table=metadataGroup.getName();
        sqlTemplate.execute(String.format("drop table %s"),table);
    }



    /**
     * 获取表
     * @param module
     * @return
     */
    public List<MetadataGroup> getMetadataGroups(String module){
        List<MetadataGroup> metadataGroups=new ArrayList<>();
        Date now=new Date();
        sqlTemplate.executeQuery("show table status", new QueryCallback() {
            @Override
            public void exec(ResultSet resultSet) {
                try{
                    while (resultSet.next()){
                        MetadataGroup metadataGroup=new MetadataGroup();
                        metadataGroup.setDescription(resultSet.getString("comment"));
                        metadataGroup.setName(resultSet.getString("name"));
                        metadataGroup.setModule(module);
                        metadataGroup.setSyncDateTime(now);
                        metadataGroups.add(metadataGroup);
                    }
                }catch (Exception e){
                    throw new MetadataException(Reflector.class,"获取元数据组失败");
                }
            }
        },null);
        return metadataGroups;

    }
}
