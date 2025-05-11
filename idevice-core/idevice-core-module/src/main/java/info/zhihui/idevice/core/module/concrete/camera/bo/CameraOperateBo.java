package info.zhihui.idevice.core.module.concrete.camera.bo;

import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CameraOperateBo {
    /**
     * 操作方向
     */
    private CameraDirectionEnum operation;

    /**
     * true 开启    false  停止
     */
    private Boolean action;

    /**
     * 速度
     * 1-100。1最慢，100最快
     */
    private Integer speed;


    /**
     * 通道编码
     */
    private String channelCode;

    /**
     * 区域id
     */
    private Integer areaId;
}
