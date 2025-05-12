package info.zhihui.idevice.mcp.access;

import info.zhihui.idevice.common.utils.SysDateUtil;
import info.zhihui.idevice.web.access.biz.AccessBizService;
import info.zhihui.idevice.web.access.vo.*;
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
public class AccessMCPService {

    private final AccessBizService accessBizService;

    @Tool(name = "get-all-access-control-channels", description = """
            通过区域id，获取这个区域内全部门禁通道列表信息。
            包括：上级设备编码、第三方通道序号、设备唯一编码、设备名称、在线状态、设备能力、节点地址、节点地址名称等字段信息
            """)
    public List<AccessFetchInfoVo> findAllDeviceChannelList(@ToolParam(description = "区域id") Integer areaId) {
        AccessDeviceQueryVo accessDeviceQueryVo = new AccessDeviceQueryVo();
        accessDeviceQueryVo.setAreaId(areaId);

        return accessBizService.findAllDeviceChannelList(accessDeviceQueryVo);
    }

    @Tool(name = "grant-access", description = """
            对本地人员进行授权，指定允许通过的门禁和有效期起止时间。
            成功后返回"授权成功"
            """)
    public String grantAccess(
            @ToolParam(description = "本地人员Id") Integer localPersonId,
            @ToolParam(description = "本地人员所在模块，一般是user") String localModuleName,
            @ToolParam(description = "区域ID") Integer areaId,
            @ToolParam(description = "门禁通道编码列表") List<String> channelCodeList,
            @ToolParam(description = "开始时间，例如：2025-05-02T13:00:12") String startTime,
            @ToolParam(description = "结束时间，例如：2025-05-18T13:00:12") String endTime) {

        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setLocalPersonId(localPersonId);
        localPersonVo.setLocalModuleName(localModuleName);
        localPersonVo.setAreaId(areaId);

        AccessPersonAuthVo accessPersonAuthVo = new AccessPersonAuthVo();
        accessPersonAuthVo.setLocalPerson(localPersonVo);
        accessPersonAuthVo.setThirdPartyDeviceUniqueCodes(channelCodeList);
        accessPersonAuthVo.setStartTime(SysDateUtil.toDateTimeWithT(startTime));
        accessPersonAuthVo.setEndTime(SysDateUtil.toDateTimeWithT(endTime));

        accessBizService.grantAccess(accessPersonAuthVo);
        return "授权成功";
    }

    @Tool(name = "revoke-access", description = """
            对本地人员进行授权，指定允许通过的门禁和有效期起止时间。
            成功后返回"删除权限成功"
            """)
    public String revokeAccess(
            @ToolParam(description = "本地人员Id") Integer localPersonId,
            @ToolParam(description = "本地人员所在模块，一般是user") String localModuleName,
            @ToolParam(description = "区域ID") Integer areaId,
            @ToolParam(description = "门禁通道编码列表") List<String> channelCodeList
    ) {
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setLocalPersonId(localPersonId);
        localPersonVo.setLocalModuleName(localModuleName);
        localPersonVo.setAreaId(areaId);

        AccessPersonRevokeVo accessPersonRevokeVo = new AccessPersonRevokeVo();
        accessPersonRevokeVo.setLocalPerson(localPersonVo);
        accessPersonRevokeVo.setThirdPartyDeviceUniqueCodes(channelCodeList);

        accessBizService.revokeAccess(accessPersonRevokeVo);
        return "删除权限成功";
    }

    @Tool(name = "sync-permissions", description = """
            同步本地人员权限到指定的门禁。
            成功后返回"同步权限成功"
            """)
    public String syncPermissions(
            @ToolParam(description = "区域ID") Integer areaId,
            @ToolParam(description = "门禁通道编码列表") List<String> channelCodeList,
            @ToolParam(description = "同步类型: 1卡片；2指纹；3卡片+指纹（组合）;4人脸；5卡片+人脸（组合）;6人脸+指纹（组合）;7卡片+指纹+人脸（组合）。默认7") Integer taskType
    ) {
        AccessPermissionSyncVo vo = new AccessPermissionSyncVo();
        vo.setAreaId(areaId);
        vo.setThirdPartyDeviceUniqueCodes(channelCodeList);
        vo.setTaskType(taskType);

        accessBizService.syncPermissions(vo);
        return "同步权限成功";
    }

    @Tool(name = "card-binding", description = """
            对本地人员进行卡绑定，指定允许有效期起止时间。通行的门禁权限可以通过grant-access方法来设置
            成功后返回"卡片绑定成功"
            """)
    public String cardBinding(
            @ToolParam(description = "卡号") String cardNumber,
            @ToolParam(description = "本地人员Id") Integer localPersonId,
            @ToolParam(description = "本地人员所在模块，一般是user") String localModuleName,
            @ToolParam(description = "区域ID") Integer areaId,
            @ToolParam(description = "开始时间，例如：2025-05-02T13:00:12") String startTime,
            @ToolParam(description = "结束时间，例如：2025-05-18T13:00:12") String endTime,
            @ToolParam(required = false, description = "卡类型 0:普通卡;1:VIP卡;2:来宾卡;3:巡逻卡;5:胁迫卡;6:巡检卡;7:黑名单卡;11:管理员卡;13:辅助卡;-1:未知卡类型; --- 1001:IC卡;1002:CPU卡;1003:远距离卡;1004:M1卡") Integer cardType
    ) {
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setLocalPersonId(localPersonId);
        localPersonVo.setLocalModuleName(localModuleName);
        localPersonVo.setAreaId(areaId);

        AccessCardBindingVo cardBindingVo = new AccessCardBindingVo();
        cardBindingVo.setCardNumber(cardNumber);
        cardBindingVo.setLocalPerson(localPersonVo);
        cardBindingVo.setCardType(cardType);
        cardBindingVo.setStartTime(SysDateUtil.toDateTimeWithT(startTime));
        cardBindingVo.setEndTime(SysDateUtil.toDateTimeWithT(endTime));

        accessBizService.cardBinding(cardBindingVo);
        return "卡片绑定成功";
    }

    @Tool(name = "card-unbind", description = """
            对本地人员进行卡解绑。
            成功后返回"卡片解绑成功"
            """)
    public String cardUnbind(
            @ToolParam(description = "卡号") String cardNumber,
            @ToolParam(description = "本地人员Id") Integer localPersonId,
            @ToolParam(description = "本地人员所在模块，一般是user") String localModuleName,
            @ToolParam(description = "区域ID") Integer areaId
    ) {
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setLocalPersonId(localPersonId);
        localPersonVo.setLocalModuleName(localModuleName);
        localPersonVo.setAreaId(areaId);

        AccessCardUnBindingVo unBindingVo = new AccessCardUnBindingVo();
        unBindingVo.setLocalPerson(localPersonVo);
        unBindingVo.setCardNumber(cardNumber);

        accessBizService.cardUnbind(unBindingVo);
        return "卡片解绑成功";
    }

    @Tool(name = "open-door", description = """
            远程开门。开启区域下的指定门禁。
            成功后返回"远程开门成功"
            """)
    public String openDoor(
            @ToolParam(description = "区域ID") Integer areaId,
            @ToolParam(description = "门禁通道编码") String channelCode
    ) {
        AccessOpenVo accessOpenVo = new AccessOpenVo();
        accessOpenVo.setAreaId(areaId);
        accessOpenVo.setThirdPartyDeviceUniqueCode(channelCode);

        accessBizService.openDoor(accessOpenVo);
        return "远程开门成功";
    }

}
