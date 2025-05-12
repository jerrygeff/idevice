package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;

import java.util.List;

/**
 * @author jerryge
 */
@Data
public class PersonUniqueKeySearchResponseData {
    private Integer total;
    private List<PersonDetail> list;
}
