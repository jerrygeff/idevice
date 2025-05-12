package info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5;

import com.dahuatech.icc.assesscontrol.enums.PrivilegeTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Accessors(chain = true)
@Data
public class PrivilegeDetail {
    /**
     * @see PrivilegeTypeEnum
     */
    private Integer privilegeType;
    private String resourceCode;
    private Long timeQuantumId;
    private String startDate;
    private String endDate;

}
