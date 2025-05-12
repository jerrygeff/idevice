package info.zhihui.idevice.web.camera.controller;

import info.zhihui.idevice.common.utils.ResultUtil;
import info.zhihui.idevice.common.vo.RestResult;
import info.zhihui.idevice.web.camera.biz.CameraBizService;
import info.zhihui.idevice.web.camera.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 摄像头控制器
 */
@Tag(name = "摄像头管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/camera")
public class CameraController {

    private final CameraBizService cameraBizService;

    @GetMapping("/realtime-link")
    @Operation(summary = "获取实时取流地址")
    public RestResult<CameraLinkVo> getCameraRealTimeLink(@Validated CameraRealtimeQueryVo realtimeQueryVo) {
        return ResultUtil.success(cameraBizService.getCameraRealTimeLink(realtimeQueryVo));
    }

    @GetMapping("/playback-link")
    @Operation(summary = "获取回放取流地址")
    public RestResult<CameraLinkVo> getCameraPlaybackLink(@Validated CameraPlaybackQueryVo playbackQueryVo) {
        return ResultUtil.success(cameraBizService.getCameraPlaybackLink(playbackQueryVo));
    }

    @GetMapping("/channel-list")
    @Operation(summary = "获取全部摄像头通道列表")
    public RestResult<List<CameraFetchInfoVo>> findAllCameraChannelList(@Validated CameraQueryVo requestBaseVo) {
        return ResultUtil.success(cameraBizService.findAllCameraChannelList(requestBaseVo));
    }

    @PostMapping("/control")
    @Operation(summary = "控制摄像头方向")
    public RestResult<Void> controlDirection(@Validated @RequestBody CameraOperateVo cameraOperateVo) {
        cameraBizService.controlDirection(cameraOperateVo);
        return ResultUtil.success();
    }
}