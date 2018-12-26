package org.zechac.atlas.metadata.entity;

import lombok.Data;
import org.zechac.atlas.common.jpa.entity.AuditingEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
public class DictKey extends AuditingEntity {

    private String name;

    @OneToMany
    private List<DictValue> dictValues;
}
