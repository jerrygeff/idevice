package info.zhihui.idevice.web.access.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "门禁卡绑定信息")
public class AccessCardBindingVo {

    @Schema(description = "卡号")
    @NotBlank(message = "卡号不能为空")
    private String cardNumber;

    @Schema(description = "本地人员信息")
    @NotNull(message = "本地人员信息不能为空")
    private LocalPersonVo localPerson;

    @Schema(description = "卡类型 0:普通卡;1:VIP卡;2:来宾卡;3:巡逻卡;5:胁迫卡;6:巡检卡;7:黑名单卡;11:管理员卡;13:辅助卡;-1:未知卡类型; --- 1001:IC卡;1002:CPU卡;1003:远距离卡;1004:M1卡")
    private Integer cardType;

    @Schema(description = "开始时间，例如：2025-04-27 12:00:00")
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    @Schema(description = "结束时间，例如：2025-04-27 12:00:00")
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
}