package org.zechac.atlas.metadata.entity;

import lombok.Data;
import org.zechac.atlas.common.jpa.entity.AuditingEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class DictValue extends AuditingEntity {
    @ManyToOne
    @JoinColumn
    private DictKey dictKey;

    private String data;
}
