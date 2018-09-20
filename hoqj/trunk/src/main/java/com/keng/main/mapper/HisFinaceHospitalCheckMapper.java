package com.keng.main.mapper;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface HisFinaceHospitalCheckMapper {

    List<Map<String, Object>> getMz(Map map);
}
