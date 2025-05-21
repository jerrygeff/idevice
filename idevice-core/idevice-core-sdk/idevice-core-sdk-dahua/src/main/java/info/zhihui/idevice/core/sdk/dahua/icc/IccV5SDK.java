package info.zhihui.idevice.core.sdk.dahua.icc;

import com.dahuatech.icc.assesscontrol.model.v202103.channelControl.ChannelControlOpenDoorRequest;
import com.dahuatech.icc.assesscontrol.model.v202103.channelControl.ChannelControlOpenDoorResponse;
import com.dahuatech.icc.brm.model.v202010.card.*;
import com.dahuatech.icc.brm.model.v202010.person.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.config.IccSdkConfig;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmCardQueryRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmCardQueryResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonAddResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonQueryRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonQueryResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonUpdateRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.BrmPersonUpdateResponse;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5.*;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5.ParkCarReservationAddRequest;
import info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5.ParkCarReservationAddResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.CAPABILITIES;

/**
 * @author jerryge
 */
@Slf4j
@Component
public class IccV5SDK extends BaseIcc {

    // VERSION = "5.0.17";

    // -------------------- 门禁相关接口开始 --------------------

    /**
     * 批量按人新增授权
     */
    public AuthPersonBatchAddResponse accessAuthPeople(IccSdkConfig config, AuthPersonBatchAddRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 批量按人删除授权
     */
    public AuthPersonBatchDeleteResponse accessDeletePeopleAllPrivilege(IccSdkConfig config, AuthPersonBatchDeleteRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 按人删除单个授权
     */
    public AuthPersonDeleteSingleResponse accessDeletePersonSinglePrivilege(IccSdkConfig config, AuthPersonDeleteSingleRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 远程开门
     */
    public ChannelControlOpenDoorResponse accessOpenDoor(IccSdkConfig config, ChannelControlOpenDoorRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }
    // -------------------- 门禁相关接口结束 --------------------


    // -------------------- 基础模块相关接口开始 --------------------

    /**
     * 人员详情
     */
    public BrmPersonQueryResponse brmPersonQuery(IccSdkConfig config, BrmPersonQueryRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员新增
     */
    public BrmPersonAddResponse brmPersonAdd(IccSdkConfig config, BrmPersonAddRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员更新
     */
    public BrmPersonUpdateResponse brmPersonUpdate(IccSdkConfig config, BrmPersonUpdateRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员删除
     */
    public BrmPersonDelResponse brmPersonDel(IccSdkConfig config, BrmPersonDelRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员图片上传
     */
    public BrmImageUploadResponse brmImageUpload(IccSdkConfig config, BrmImageUploadRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员头像批量更新
     */
    public BrmPersonBatchUpdateImgResponse brmPersonUpdateFace(IccSdkConfig config, BrmPersonBatchUpdateImgRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 人员全局id生成
     */
    public BrmPersonGenIdResponse brmPersonGenId(IccSdkConfig config, BrmPersonGenIdRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 设备分页
     */
    public BrmDevicePageResponse brmDevicePage(IccSdkConfig config, BrmDevicePageRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 通道分页
     */
    public BrmChannelPageResponse brmChannelPage(IccSdkConfig config, BrmChannelPageRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 设备树列表
     */
    public BrmDeviceTreeListResponse brmDeviceTreeList(IccSdkConfig config, BrmDeviceTreeListRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 卡片全局id生成
     */
    public BrmCardGenIdResponse brmCardGenId(IccSdkConfig config, BrmCardGenIdRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 卡片新增
     */
    public BrmCardAddResponse brmCardAdd(IccSdkConfig config, BrmCardAddRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 卡片详情查询
     */
    public BrmCardQueryResponse brmCardQuery(IccSdkConfig config, BrmCardQueryRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 卡片激活
     */
    public BrmCardActiveResponse brmCardActive(IccSdkConfig config, BrmCardActiveRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 退卡
     */
    public BrmCardReturnResponse brmCardReturn(IccSdkConfig config, BrmCardReturnRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 卡片删除
     */
    public BrmCardDelResponse brmCardDel(IccSdkConfig config, BrmCardDelRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 获取通道能力集
     * 设备的通道能力用32位二进制字符串表示。
     * 每一位代表一种能力，1表示有，0表示没有。
     * 能力说明，从右往左依次说明获取能力集
     *
     * @param capability 0和1表示的能力集字符串
     * @return 能力集具体内容列表
     */
    public List<String> getCapabilities(String capability) {
        if (StringUtils.isEmpty(capability)) {
            return List.of();
        }

        int targetLen = CAPABILITIES.size();

        List<String> result = new ArrayList<>();
        for (int i = 0; i < capability.length() && i < targetLen; i++) {
            if (capability.charAt(i) == '1') {
                String cap = CAPABILITIES.get(i);
                if (!"-".equals(cap)) {
                    result.add(cap);
                }
            }
        }
        return result;
    }

    // -------------------- 基础模块相关接口结束 --------------------

    // -------------------- 视频模块相关接口开始 --------------------

    /**
     * 获取实时流
     */
    public RealTimeLinkResponse cameraRealTimeLink(IccSdkConfig config, RealTimeLinkRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 获取回放流
     */
    public PlaybackLinkResponse cameraPlaybackLink(IccSdkConfig config, PlaybackLinkRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    /**
     * 摄像头控制
     */
    public CameraControlResponse cameraControl(IccSdkConfig config, CameraControlRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }

    // -------------------- 视频模块相关接口结束 --------------------

    // -------------------- 停车场相关接口开始 --------------------
    
    /**
     * 添加预约车辆
     * 根据车牌号，预约停车。停车场默认不支持预约，必须启用指定场区的预约功能
     * 
     * @param config ICC配置信息
     * @param request 预约车辆请求
     * @return 预约车辆响应
     */
    public ParkCarReservationAddResponse parkCarReservationAdd(IccSdkConfig config, ParkCarReservationAddRequest request) {
        return doActionWithConfig(config, request, request.getResponseClass());
    }
    
    // -------------------- 停车场相关接口结束 --------------------

}
