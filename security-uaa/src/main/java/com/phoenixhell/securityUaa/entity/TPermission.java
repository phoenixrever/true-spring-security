package com.phoenixhell.securityUaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author phoniexhell
 * @since 2021-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 描述
     */
    private String description;

    /**
     * 请求地址
     */
    private String url;


}
