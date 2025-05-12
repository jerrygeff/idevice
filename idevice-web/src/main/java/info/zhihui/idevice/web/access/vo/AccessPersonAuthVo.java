package info.zhihui.idevice.web.access.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "门禁人员授权信息")
public class AccessPersonAuthVo {

    @Schema(description = "第三方设备唯一码列表")
    @NotEmpty(message = "设备唯一码列表不能为空")
    private List<String> thirdPartyDeviceUniqueCodes;

    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;

    @NotNull(message = "开始时间不能为空")
    @Schema(description = "开始时间，例如：2025-05-02 13:00:12")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    @Schema(description = "结束时间，，例如：2025-05-07 13:00:12")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}