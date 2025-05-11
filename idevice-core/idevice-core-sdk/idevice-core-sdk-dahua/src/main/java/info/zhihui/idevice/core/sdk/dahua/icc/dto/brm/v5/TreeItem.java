package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import lombok.Data;

/**
 * 设备树节点
 *
 * @author jerryge
 */
@Data
public class TreeItem {
    /**
     * 节点ID
     */
    private String id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 节点路径
     */
    private String path;
    /**
     * 父节点ID
     */
    private String pId;
    /**
     * 节点类型
     */
    private String type;
    /**
     * 是否父节点
     */
    private boolean isParent;
    /**
     * 图标类型
     */
    private String iconType;
    /**
     * 节点状态
     */
    private int checkStat;
    /**
     * 是否选中
     */
    private boolean isCheck;
    /**
     * 节点类型
     */
    private String nodeType;
    /**
     * 是否虚拟节点
     */
    private int isVirtual;
    /**
     * 是否有更多节点
     */
    private boolean hasMoreNode;
    /**
     * 设备大类
     */
    private int deviceCategory;
    /**
     * 设备类型
     */
    private int deviceType;
    /**
     * 设备型号
     */
    private String deviceModel;
    /**
     * 能力集
     */
    private String capability;
    /**
     * 通道类型
     */
    private String channelType;
    /**
     * 所属组织编码
     */
    private String ownerCode;
    /**
     * 摄像机类型
     */
    private String cameraType;
    /**
     * 单元类型
     */
    private int unitType;
    /**
     * 设备编码
     */
    private String deviceCode;
    /**
     * 是否在线
     */
    private int isOnline;
    /**
     * 通道序号
     */
    private int channelSeq;
    /**
     * 注册服务器编码
     */
    private String registServerCode;
    /**
     * 通道编码
     */
    private String channelCode;
    /**
     * 状态
     */
    private int stat;
    /*
     * 是否休眠 0:非休眠 1:休眠
     */
    private int sleepStat;
    /**
     * 加密狗是否到期0否，1是
     */
    private int licenseLimit;
}