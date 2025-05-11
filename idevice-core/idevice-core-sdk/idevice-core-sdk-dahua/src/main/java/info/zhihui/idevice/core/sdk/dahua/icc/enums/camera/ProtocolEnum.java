package info.zhihui.idevice.core.sdk.dahua.icc.enums.camera;

import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
public enum ProtocolEnum {

    HLS("hls"),
    HLSS("hlss"),
    FLV("flv"),
    FLVS("flvs"),
    WS_FLV("ws_flv"),
    WSS_FLV("wss_flv"),
    RTMP("rtmp");

    private final String value;

    ProtocolEnum(String value) {
        this.value = value;
    }
}