package info.zhihui.idevice.core.module.concrete.camera.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 视频参数加密信息
 */
@Data
@Accessors(chain = true)
public class CameraEncryptionInfoBo {

    private String appKey;

    private String secret;

    private String ip;

    private String port;

}
