package info.zhihui.idevice.core.module.config.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@TableName("sys_config")
public class ConfigEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 配置所属模块name
     */
    private String configModuleName;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * 配置name
     */
    private String configName;

    /**
     * 配置value
     */
    private String configValue;

    /**
     * 是否内置，内置配置项可在系统配置列表修改
     */
    private Boolean isSystem;

    /**
     * 是否被删除：0未删除；1已删除
     */
    private Boolean isDeleted;

}
