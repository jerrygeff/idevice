package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PersonData {

    /**
     * 人员数据类型
     */
    private String personDataType;

    /**
     * 人员数据索引编码列表
     */
    private List<String> indexCodes;
}