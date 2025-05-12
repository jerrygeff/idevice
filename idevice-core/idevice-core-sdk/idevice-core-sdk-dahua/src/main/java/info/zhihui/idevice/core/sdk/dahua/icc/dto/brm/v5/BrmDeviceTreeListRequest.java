package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.ContentType;
import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;

import java.util.List;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.BRM_URL_DEVICE_TREE_LIST_POST;

/**
 * 设备树列表查询请求
 *
 * @author jerryge
 */
public class BrmDeviceTreeListRequest extends AbstractIccRequest<BrmDeviceTreeListResponse> {
    private static final String VERSION = "1.0.0";

    /** 上级节点编码(组织编码/设备编码) */
    private String id;

    /**
     * type参数说明 type 参数格式： "组织节点类型;设备节点类型;通道单元类型"
     *
     * <p>组织节点类型：为树结构中根组织节点编码 如： 001 表示该树根节点是001的基础组织（其他非001的树为逻辑组织树，暂不支持（预留））
     *
     * <p>设备类型： 1,2 表示展示设备分类为1和2的设备 00 标识不展示设备节点 00_1，00_2
     * 标识不展示设备节点，但设备分类为1和2的设备下的通道有权限展示，具体是否展示通道及那些种类的通道还需要根据通道单元类型判断
     *
     * <p>通道单元类型： 1 表示可以展示单元类型为1的通道，即编码通道 1,2 标识可以展示单元类型为1和2的通道，及编码通道和解码通道
     */
    private String type;

    /** 业务类型:1-基本组织，2-逻辑组织。默认值1，基础组织 ；逻辑组织编码是L打头 **/
    private Integer busiType;

    /** 查询逻辑组织根组织标识，默认true ，逻辑组织查询时使用 等价于基本组织组织编码传空的情况，其它情况请将将参数设置为false,逻辑组织时推荐false **/
    private Boolean checkLogicRootOrgNodeFlag;

    /**
     * 通道归属于组织还是归属于设备 1：归属于设备 0：归属于组织
     * 默认0(注意,该参数只在type值为"001;1;"这种类型(最后一个分号后面不指定通道类型)时生效,这种传参是组织、设备、通道都展示的，默认是将通道放在组织下面；
     * 若传参是"001;1;1"同样是展示组织、设备、通道，但是通道是归属于设备的，这个时候该参数不会生效)
     */
    private Integer channelBelong;

    /** 操作类型，没有默认为根据上级节点查询下级节点;如果为search,表示按节点名称模糊搜索 */
    private String act;

    /** 上级组织/设备节点是否有权限，查询下级节点时该值取上级节点中checkStat属性值；推荐送1 */
    private Integer checkStat;

    /** act=search时模糊搜索的关键字 */
    private String searchKey;

    /** 通道能力集,取值为: "单元类型1_通道能力集,单元类型2_通道能力集", 如: ["1_0000000001000000","2_0000000000000001110"] */
    private List<String> chCapability;

    /** 设备能力集,取值为: "设备大类1_通道能力集,设备大类2_通道能力集", 如: ["1_0000000001000000","2_0000000000000001110"] */
    private List<String> devCapability;

    /** 初始化加载树时需要勾选的节点id列表 */
    private List<String> checkNodes;

    /** 是否展示级联节点,默认是1 */
    private Integer showCascadeNode;

    /** 是否展示虚拟设备、通道,默认0 */
    private Integer showVirtualNode;

    /** 是否展示出没有通道或设备的组织节点,默认0 */
    private Integer showEmptyOrgNode;

    /** 设备通道状态 -1:查询全部 0:查询关闭 1:查询已经开启 默认:1 **/
    private Integer stat;

    public BrmDeviceTreeListRequest() {
        super(String.format(IccProfile.URL_SCHEME + BRM_URL_DEVICE_TREE_LIST_POST, VERSION), Method.POST);
        httpRequest.contentType(ContentType.JSON.toString());
    }

    @Override
    public Class<BrmDeviceTreeListResponse> getResponseClass() {
        return BrmDeviceTreeListResponse.class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.putBodyParameter("id", id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.putBodyParameter("type", type);
    }

    public Integer getBusiType() {
        return busiType;
    }

    public void setBusiType(Integer busiType) {
        this.busiType = busiType;
        this.putBodyParameter("busiType", busiType);
    }

    public Boolean getCheckLogicRootOrgNodeFlag() {
        return checkLogicRootOrgNodeFlag;
    }

    public void setCheckLogicRootOrgNodeFlag(Boolean checkLogicRootOrgNodeFlag) {
        this.checkLogicRootOrgNodeFlag = checkLogicRootOrgNodeFlag;
        this.putBodyParameter("checkLogicRootOrgNodeFlag", checkLogicRootOrgNodeFlag);
    }

    public Integer getChannelBelong() {
        return channelBelong;
    }

    public void setChannelBelong(Integer channelBelong) {
        this.channelBelong = channelBelong;
        this.putBodyParameter("channelBelong", channelBelong);
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
        this.putBodyParameter("act", act);
    }

    public Integer getCheckStat() {
        return checkStat;
    }

    public void setCheckStat(Integer checkStat) {
        this.checkStat = checkStat;
        this.putBodyParameter("checkStat", checkStat);
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
        this.putBodyParameter("searchKey", searchKey);
    }

    public List<String> getChCapability() {
        return chCapability;
    }

    public void setChCapability(List<String> chCapability) {
        this.chCapability = chCapability;
        this.putBodyParameter("chCapability", chCapability);
    }

    public List<String> getDevCapability() {
        return devCapability;
    }

    public void setDevCapability(List<String> devCapability) {
        this.devCapability = devCapability;
        this.putBodyParameter("devCapability", devCapability);
    }

    public List<String> getCheckNodes() {
        return checkNodes;
    }

    public void setCheckNodes(List<String> checkNodes) {
        this.checkNodes = checkNodes;
        this.putBodyParameter("checkNodes", checkNodes);
    }

    public Integer getShowCascadeNode() {
        return showCascadeNode;
    }

    public void setShowCascadeNode(Integer showCascadeNode) {
        this.showCascadeNode = showCascadeNode;
        this.putBodyParameter("showCascadeNode", showCascadeNode);
    }

    public Integer getShowVirtualNode() {
        return showVirtualNode;
    }

    public void setShowVirtualNode(Integer showVirtualNode) {
        this.showVirtualNode = showVirtualNode;
        this.putBodyParameter("showVirtualNode", showVirtualNode);
    }

    public Integer getShowEmptyOrgNode() {
        return showEmptyOrgNode;
    }

    public void setShowEmptyOrgNode(Integer showEmptyOrgNode) {
        this.showEmptyOrgNode = showEmptyOrgNode;
        this.putBodyParameter("showEmptyOrgNode", showEmptyOrgNode);
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
        this.putBodyParameter("stat", stat);
    }
}