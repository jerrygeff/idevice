package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera;

import lombok.Getter;

/**
 * 协议类型枚举：
 * hik  HIK私有协议，使用视频SDK进行播放时，传入此类型
 * rtsp RTSP协议
 * rtmp RTMP协议
 * hls  HLS协议（只支持海康SDK协议、EHOME协议、ONVIF协议接入的设备；只支持H264视频编码和AAC音频编码）
 * ws   Websocket协议（一般用于H5视频播放器取流播放）
 */
@Getter
public enum ProtocolEnum {
    HIK("hik"),
    RTSP("rtsp"),
    RTMP("rtmp"),
    HLS("hls"),
    WS("ws");

    private final String value;

    ProtocolEnum(String value) {
        this.value = value;
    }
}