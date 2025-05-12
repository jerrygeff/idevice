package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

/**
 * 通道信息
 *
 * @author jerryge
 */
public class Channel {
    private Long id;
    private String deviceCode;
    private Integer unitType;
    private Integer unitSeq;
    private Integer channelSeq;
    private String channelCode;
    private String channelSn;
    private String channelName;
    private String channelType;
    private String cameraType;
    private Integer sleepStat;
    private String ownerCode;
    private String gpsX;
    private String gpsY;
    private String gpsZ;
    private Integer mapId;
    private Integer domainId;
    private String memo;
    private Integer isOnline;
    private Integer stat;
    private String capability;
    private Integer access;
    private String chExt;
    private Integer isVirtual;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public Integer getUnitType() {
        return unitType;
    }

    public void setUnitType(Integer unitType) {
        this.unitType = unitType;
    }

    public Integer getUnitSeq() {
        return unitSeq;
    }

    public void setUnitSeq(Integer unitSeq) {
        this.unitSeq = unitSeq;
    }

    public Integer getChannelSeq() {
        return channelSeq;
    }

    public void setChannelSeq(Integer channelSeq) {
        this.channelSeq = channelSeq;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelSn() {
        return channelSn;
    }

    public void setChannelSn(String channelSn) {
        this.channelSn = channelSn;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getCameraType() {
        return cameraType;
    }

    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    public Integer getSleepStat() {
        return sleepStat;
    }

    public void setSleepStat(Integer sleepStat) {
        this.sleepStat = sleepStat;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    public String getGpsZ() {
        return gpsZ;
    }

    public void setGpsZ(String gpsZ) {
        this.gpsZ = gpsZ;
    }

    public Integer getMapId() {
        return mapId;
    }

    public void setMapId(Integer mapId) {
        this.mapId = mapId;
    }

    public Integer getDomainId() {
        return domainId;
    }

    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(String capability) {
        this.capability = capability;
    }

    public Integer getAccess() {
        return access;
    }

    public void setAccess(Integer access) {
        this.access = access;
    }

    public String getChExt() {
        return chExt;
    }

    public void setChExt(String chExt) {
        this.chExt = chExt;
    }

    public Integer getIsVirtual() {
        return isVirtual;
    }

    public void setIsVirtual(Integer isVirtual) {
        this.isVirtual = isVirtual;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", deviceCode='" + deviceCode + '\'' +
                ", unitType=" + unitType +
                ", unitSeq=" + unitSeq +
                ", channelSeq=" + channelSeq +
                ", channelCode='" + channelCode + '\'' +
                ", channelSn='" + channelSn + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelType='" + channelType + '\'' +
                ", cameraType='" + cameraType + '\'' +
                ", sleepStat=" + sleepStat +
                ", ownerCode='" + ownerCode + '\'' +
                ", gpsX='" + gpsX + '\'' +
                ", gpsY='" + gpsY + '\'' +
                ", gpsZ='" + gpsZ + '\'' +
                ", mapId=" + mapId +
                ", domainId=" + domainId +
                ", memo='" + memo + '\'' +
                ", isOnline=" + isOnline +
                ", stat=" + stat +
                ", capability='" + capability + '\'' +
                ", access=" + access +
                ", chExt='" + chExt + '\'' +
                ", isVirtual=" + isVirtual +
                '}';
    }
}