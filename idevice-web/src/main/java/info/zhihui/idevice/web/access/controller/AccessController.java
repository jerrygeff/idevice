package info.zhihui.idevice.web.access.controller;

import info.zhihui.idevice.common.utils.ResultUtil;
import info.zhihui.idevice.common.vo.RestResult;
import info.zhihui.idevice.web.access.biz.AccessBizService;
import info.zhihui.idevice.web.access.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "门禁管理")
@RequiredArgsConstructor
@RestController
@RequestMapping("/access")
public class AccessController {

    private final AccessBizService accessBizService;

    @PostMapping("/grant")
    @Operation(summary = "授权人员访问门禁设备")
    public RestResult<Void> grantAccess(@Validated @RequestBody AccessPersonAuthVo accessPersonAuthVo) {
        accessBizService.grantAccess(accessPersonAuthVo);
        return ResultUtil.success();
    }

    @PostMapping("/revoke")
    @Operation(summary = "撤销员工访问门禁设备权限")
    public RestResult<Void> revokeAccess(@Validated @RequestBody AccessPersonRevokeVo accessPersonRevokeVo) {
        accessBizService.revokeAccess(accessPersonRevokeVo);
        return ResultUtil.success();
    }

    @PostMapping("/sync")
    @Operation(summary = "同步权限")
    public RestResult<Void> syncPermissions(@Validated @RequestBody AccessPermissionSyncVo accessPermissionSyncVo) {
        accessBizService.syncPermissions(accessPermissionSyncVo);
        return ResultUtil.success();
    }

    @PostMapping("/face/update")
    @Operation(summary = "修改员工门禁的照片")
    public RestResult<Void> updateAccessImg(@Validated @RequestBody AccessPersonFaceUpdateVo accessPersonFaceUpdateVo) {
        accessBizService.updateAccessImg(accessPersonFaceUpdateVo);
        return ResultUtil.success();
    }

    @PostMapping("/card/bind")
    @Operation(summary = "绑定门禁卡")
    public RestResult<Void> cardBinding(@Validated @RequestBody AccessCardBindingVo cardVo) {
        accessBizService.cardBinding(cardVo);
        return ResultUtil.success();
    }

    @PostMapping("/card/unbind")
    @Operation(summary = "门禁卡解绑")
    public RestResult<Void> cardUnbind(@Validated @RequestBody AccessCardUnBindingVo cardVo) {
        accessBizService.cardUnbind(cardVo);
        return ResultUtil.success();
    }

    @GetMapping("/device/channels")
    @Operation(summary = "获取全部门禁通道列表")
    public RestResult<List<AccessFetchInfoVo>> findAllDeviceChannelList(@Validated AccessDeviceQueryVo accessDeviceQueryVo) {
        return ResultUtil.success(accessBizService.findAllDeviceChannelList(accessDeviceQueryVo));
    }

    @PostMapping("/door/open")
    @Operation(summary = "远程开门")
    public RestResult<Void> openDoor(@Validated @RequestBody AccessOpenVo accessOpenVo) {
        accessBizService.openDoor(accessOpenVo);
        return ResultUtil.success();
    }
}