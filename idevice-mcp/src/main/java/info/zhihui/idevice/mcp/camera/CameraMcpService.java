package info.zhihui.idevice.mcp.camera;

import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.web.camera.biz.CameraBizService;
import info.zhihui.idevice.web.camera.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jerryge
 */
@Service
@RequiredArgsConstructor
public class CameraMcpService {
    private final CameraBizService cameraBizService;

    @Tool(name = "get-all-camera-channels", description = """
            通过区域id，获取这个区域内全部摄像头通道列表信息。
            包括：设备编码、设备名称、设备能力、通道序号、节点地址、节点地址名称、所属录像机编号、摄像头类型、是否支持云台能力、设备所属组织、在线状态（1在线；0离线）等字段信息
            """)
    public List<CameraFetchInfoVo> findAllCameraChannelList(@ToolParam(description = "区域id") Integer areaId) {
        CameraQueryVo requestBaseVo = new CameraQueryVo();
        requestBaseVo.setAreaId(areaId);

        return cameraBizService.findAllCameraChannelList(requestBaseVo);
    }

    @Tool(name = "get-camera-real-time-link", description = """
            获取摄像头实时流的播放地址
            """)
    public String getCameraRealTimeLink(@ToolParam(description = "区域ID") Integer areaId,
                                        @ToolParam(description = "通道编码") String channelCode,
                                        @ToolParam(description = "取流的协议: rtsp, rtmp, hls") String protocol,
                                        @ToolParam(description = "码流类型: 1-主码流, 2-子码流, 3-第三码流") Integer streamType
    ) {
        CameraRealtimeQueryVo requestVo = new CameraRealtimeQueryVo()
                .setProtocol(protocol)
                .setStreamType(streamType)
                .setChannelCode(channelCode)
                .setAreaId(areaId);

        return cameraBizService.getCameraRealTimeLink(requestVo).getUrl();
    }

    @Tool(name = "get-camera-playback-link", description = """
            获取摄像头回放流的播放地址
            """)
    public String getCameraPlaybackLink(@ToolParam(description = "区域ID") Integer areaId,
                                        @ToolParam(description = "通道编码") String channelCode,
                                        @ToolParam(description = "取流的协议: rtsp, rtmp, hls") String protocol,
                                        @ToolParam(description = "码流类型: 1-主码流, 2-子码流, 3-第三码流") Integer streamType,
                                        @ToolParam(description = "开始时间, 例如：2025-04-27T12:00:00") String startTime,
                                        @ToolParam(description = "结束时间,例如：2025-04-27T12:15:00") String endTime) {
        CameraPlaybackQueryVo requestVo = new CameraPlaybackQueryVo();
        requestVo.setProtocol(protocol)
                .setStreamType(streamType)
                .setChannelCode(channelCode)
                .setAreaId(areaId);
        requestVo.setStartTime(SysDateUtil.toDateTimeWithT(startTime));
        requestVo.setEndTime(SysDateUtil.toDateTimeWithT(endTime));

        return cameraBizService.getCameraPlaybackLink(requestVo).getUrl();
    }

    @Tool(name = "control-direction", description = """
            控制摄像头方向，操作完成会返回"操作成功"。
            如果需要停在某个位置，需要再次发送请求，并设置action=false
            """)
    public String controlDirection(@ToolParam(description = "区域ID") Integer areaId,
                                   @ToolParam(description = "通道编码") String channelCode,
                                   @ToolParam(description = "动作状态: true-开启, false-停止") Boolean action,
                                   @ToolParam(description = "操作方向: UP-上, DOWN-下, LEFT-左, RIGHT-右, LEFT_UP-左上, LEFT_DOWN-左下, RIGHT_UP-右上, RIGHT_DOWN-右下") String operation,
                                   @ToolParam(description = "速度：1-100。1最慢，100最快") Integer speed) {
        CameraOperateVo cameraOperateVo = new CameraOperateVo()
                .setAreaId(areaId)
                .setChannelCode(channelCode)
                .setOperation(operation)
                .setAction(action)
                .setSpeed(speed);

        cameraBizService.controlDirection(cameraOperateVo);

        return "操作成功";
    }
}
