package info.zhihui.idevice.core.module.concrete.camera.bo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CameraPlaybackLinkBo {

    /**
     * 回放url
     */
    private String playbackUrl;

}
