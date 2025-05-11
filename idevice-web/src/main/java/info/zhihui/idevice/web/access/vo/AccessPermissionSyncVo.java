package info.zhihui.idevice.web.access.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "门禁同步信息")
public class AccessPermissionSyncVo {

    @Schema(description = "区域ID")
    @NotNull(message = "区域ID不能为空")
    private Integer areaId;

    @Schema(description = "任务类型: 1卡片；2指纹；3卡片+指纹（组合）;4人脸；5卡片+人脸（组合）;6人脸+指纹（组合）;7卡片+指纹+人脸（组合）。默认7")
    private Integer taskType;

    @Schema(description = "第三方设备唯一码列表")
    @NotEmpty(message = "设备唯一码列表不能为空")
    private List<String> thirdPartyDeviceUniqueCodes;

}