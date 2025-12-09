package com.dnaf.batch.core.facir.mapper;

import com.dnaf.batch.core.facir.entity.FACIR;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Lenovo
* @description 针对表【FACIR(Late payment interests)】的数据库操作Mapper
* @createDate 2025-12-08 13:19:02
* @Entity com.dnaf.batch.core.facir.entity.FACIR
*/
public interface FACIRMapper extends BaseMapper<FACIR> {

}
