package info.zhihui.idevice.web.config.controller;

import info.zhihui.idevice.common.utils.ResultUtil;
import info.zhihui.idevice.common.vo.RestResult;
import info.zhihui.idevice.web.config.biz.ConfigBizService;
import info.zhihui.idevice.web.config.vo.DeviceModuleConfigSetVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 设备模块配置控制器
 */
@Tag(name = "设备模块配置管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/config/device")
public class ConfigController {

    private final ConfigBizService configBizService;

    @PostMapping("/set")
    @Operation(summary = "设置区域设备模块配置")
    public RestResult<Void> setDeviceConfigByArea(@Validated @RequestBody DeviceModuleConfigSetVo configSetVo) {
        configBizService.setDeviceConfigByArea(configSetVo);
        return ResultUtil.success();
    }

    @GetMapping("/getAll")
    @Operation(summary = "获取全部设备模块配置")
    public RestResult<String> getDeviceConfigByModule() {
        String configValue = configBizService.getDeviceConfigValue();
        return ResultUtil.success(configValue);
    }
}
