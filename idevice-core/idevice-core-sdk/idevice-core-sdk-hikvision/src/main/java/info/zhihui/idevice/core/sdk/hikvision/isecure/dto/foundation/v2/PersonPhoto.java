package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class PersonPhoto {
    private String personPhotoIndexCode;
    private String picUri;
    private String serverIndexCode;
    private String personId;
}
