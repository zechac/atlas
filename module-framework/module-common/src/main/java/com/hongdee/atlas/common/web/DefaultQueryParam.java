package com.hongdee.atlas.common.web;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;

@Data
public class DefaultQueryParam {

    private HashMap<String,String> select;

    private HashMap<String,String> query;

    private Pageable pageable;
}
