package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;

/**
 * @author jerryge
 */
@Data
public class ConfigRateSearchResponseData {
    /**
     * 标签ID
     */
    private String tagId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 进度百分比
     */
    private Integer percent;

    /**
     * 剩余时间
     */
    private Integer leftTime;

    /**
     * 是否完成
     */
    private Boolean isFinished;

    /**
     * 是否配置完成
     */
    private Boolean isConfigFinished;

    /**
     * 总数
     */
    private Integer totalNum;

    /**
     * 成功数
     */
    private Integer successedNum;

    /**
     * 失败数
     */
    private Integer failedNum;

    /**
     * 重复数
     */
    private Integer repeatedNum;
}