package com.zw.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zw.common.domain.Admin;

/**
* @author anking
* @description 针对表【backstage_admin】的数据库操作Mapper
* @createDate 2023-01-01 13:12:41
* @Entity generator.domain.Admin
*/
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    // 根据用户名查询用户
    Admin selectOneByUsername(@Param("username") String username);
}




