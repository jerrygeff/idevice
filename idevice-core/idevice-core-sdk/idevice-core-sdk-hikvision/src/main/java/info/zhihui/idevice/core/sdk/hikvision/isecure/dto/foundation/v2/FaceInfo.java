package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;

import java.util.List;

@Data
public class FaceInfo {

    /**
     * 是否清除人员下所有人脸
     */
    private Boolean deleteAllFace;

    /**
     * 卡号：支持8-20位数字+大写字母的卡号；指纹绑定卡片（以卡为中心设备必填），请根据设备实际能力填写
     */
    private String cardNo;

    /**
     * asw图片服务器存储节点编号，
     * 将人脸图片数据存储在asw时，可填写图片服务器的存储节点编号，
     * 并在人脸数据data的value中填写asw返回的url（无ip端口）
     */
    private String storageNodeCode;

    private List<Face> faceList;
}