package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import lombok.Data;

/**
 * 录像片段信息
 */
@Data
public class PlaybackSegment {
    /** 锁定类型 */
    private Integer lockType;
    /** 起始时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX */
    private String beginTime;
    /** 结束时间，格式：yyyy-MM-dd'T'HH:mm:ss.SSSXXX */
    private String endTime;
    /** 片段大小（字节） */
    private Long size;
}