package com.phoenixhell.securityUaa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenixhell.securityUaa.entity.TPermission;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author phoniexhell
 * @since 2021-04-09
 */
public interface TPermissionMapper extends BaseMapper<TPermission> {
    List<TPermission> getPermissionsListByUserId(String id);
}
