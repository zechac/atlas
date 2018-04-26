package org.zechac.atlas.common.sql;

import java.sql.ResultSet;

public interface QueryCallback {
    void exec(ResultSet resultSet);
}
