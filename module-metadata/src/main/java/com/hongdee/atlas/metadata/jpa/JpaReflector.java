package com.hongdee.atlas.metadata.jpa;

import com.hongdee.atlas.common.utils.StringUtil;
import com.hongdee.atlas.entity.Metadata;
import com.hongdee.atlas.entity.MetadataGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.EntityManagerFactory;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.metamodel.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Slf4j
public class JpaReflector {

    private EntityManagerFactory entityManagerFactory;

    public JpaReflector(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory=entityManagerFactory;
    }

    public Metamodel getMetamodel(){
        Metamodel metamodel=entityManagerFactory.getMetamodel();
        return metamodel;
    }

    protected EntityType getEntityType(String tableName){
        Metamodel metamodel=getMetamodel();
        Set<EntityType<?>> entityTypes= metamodel.getEntities();
        for(EntityType entityType1:entityTypes){
            if(((Table)entityType1.getJavaType().getAnnotation(Table.class)).name().equals(tableName)){
                return entityType1;
            }
        }
        return null;
    }

    private Attribute getAttribute(IdentifiableType entityType,String clumnName){
        Class clazz=entityType.getJavaType();
        for(Object object : entityType.getSingularAttributes()){
            SingularAttribute attribute= ((SingularAttribute)object);
            String name=attribute.getName();
            Field field= null;
            try {
                field = clazz.getDeclaredField(name);
                if(field!=null){
                    Column column = field.getAnnotation(Column.class);
                    if(column!=null&&StringUtils.isNotBlank(column.name())){
                        name=column.name();
                    }
                    JoinColumn joinColumn= field.getAnnotation(JoinColumn.class);
                    if(joinColumn!=null&&StringUtils.isNotBlank(joinColumn.name())){
                        name=joinColumn.name();
                    }
                }
                name=StringUtil.underscoreName(name);
                if (name.equals(clumnName)) {
                    return attribute;
                }
            } catch (NoSuchFieldException e) {
                //do nothing
            }
        }
        if(entityType.getSupertype()!=null){
            return getAttribute(entityType.getSupertype(),clumnName);
        }
        return null;
    }

    public List<Metadata> getPluralAttribute(MetadataGroup metadataGroup){
        EntityType entityType=getEntityType(metadataGroup.getName());
        List<Metadata> metadatas=new ArrayList<>();
        if(entityType!=null) {
            for (Object object : entityType.getPluralAttributes()) {
                PluralAttribute attribute = ((PluralAttribute) object);
                Metadata metadata = new Metadata();
                metadata.setJpaType(attribute.getPersistentAttributeType().name());
                metadata.setJpaName(attribute.getName());
                metadata.setStandard(true);
                metadata.setJavaType(attribute.getElementType().getJavaType().getName());
                metadata.setMetadataGroup(metadataGroup);
                metadatas.add(metadata);
            }
        }
        return metadatas;
    }

    public void fillJpaMetadataGroup(MetadataGroup metadataGroup){
        EntityType entityType=getEntityType(metadataGroup.getName());
        if(entityType!=null){
            metadataGroup.setJpaName(entityType.getName());
            metadataGroup.setJavaType(entityType.getJavaType().getName());
            metadataGroup.setStandard(true);
        }
    }

    public void fillJpaMetadata(Metadata metadata){
        EntityType entityType=getEntityType(metadata.getMetadataGroup().getName());
        if(entityType!=null){
            Attribute attribute=getAttribute(entityType,metadata.getName());
            if(attribute!=null){
                metadata.setJavaType(attribute.getJavaType().getName());
                metadata.setJpaName(attribute.getName());
                metadata.setStandard(true);
                metadata.setJpaType(attribute.getPersistentAttributeType().name());
            }
        }
    }
}
