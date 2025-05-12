package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.PersonAuthActionEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.PersonTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonInfo {

    /**
     * 内部人员可以通过接口获取，外部人员标识最大长度64位，设备仅支持32位，超过时将截取前32位
     * {@see /api/resource/v2/person/advance/personList}
     */
    private String personId;

    /**
     * @see PersonAuthActionEnum
     */
    private String personStatus;

    /**
     * 开始日期，配置人员权限的有效期，为空时默认长期有效，
     * ISO8601时间格式，最大长度32个字符，如2019-09-03T17:30:08.000+08:00
     */
    private String startTime;

    /**
     * 结束日期，格式同上
     */
    private String endTime;

    /**
     * 人员姓名
     */
    private String name;

    /**
     * @see PersonTypeEnum
     */
    private String personType;

    private CardInfo cardInfo;

    private FaceInfo faceInfo;

    private PersonExtendData personExtendData;
}