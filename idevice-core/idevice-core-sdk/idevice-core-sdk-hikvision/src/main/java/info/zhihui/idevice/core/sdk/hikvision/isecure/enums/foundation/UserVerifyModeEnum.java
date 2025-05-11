package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

public enum UserVerifyModeEnum {
    // 刷卡+密码
    CARD_AND_PW("cardAndPw"),

    // 刷卡
    CARD("card"),

    // 刷卡或密码
    CARD_OR_PW("cardOrPw"),

    // 指纹
    FP("fp"),

    // 指纹+密码
    FP_AND_PW("fpAndPw"),

    // 指纹或刷卡
    FP_OR_CARD("fpOrCard"),

    // 指纹+刷卡
    FP_AND_CARD("fpAndCard"),

    // 指纹+刷卡+密码
    FP_AND_CARD_AND_PW("fpAndCardAndPw"),

    // 人脸或指纹或刷卡或密码
    FACE_OR_FP_OR_CARD_OR_PW("faceOrFpOrCardOrPw"),

    // 人脸+指纹
    FACE_AND_FP("faceAndFp"),

    // 人脸+密码
    FACE_AND_PW("faceAndPw"),

    // 人脸+刷卡
    FACE_AND_CARD("faceAndCard"),

    // 人脸
    FACE("face"),

    // 指纹或密码
    FP_OR_PW("fpOrPw"),

    // 人脸+指纹+刷卡
    FACE_AND_FP_AND_CARD("faceAndFpAndCard"),

    // 人脸+密码+指纹
    FACE_AND_PW_AND_FP("faceAndPwAndFp"),

    // 人脸或人脸+刷卡
    FACE_OR_FACE_AND_CARD("faceOrfaceAndCard"),

    // 指纹或人脸
    FP_OR_FACE("fpOrface"),

    // 刷卡或人脸或密码
    CARD_OR_FACE_OR_PW("cardOrfaceOrPw"),

    // 刷卡或人脸
    CARD_OR_FACE("cardOrFace"),

    // 刷卡或人脸或指纹（默认设备本身的认证方式，仅支持以人为中心设备）
    CARD_OR_FACE_OR_FP("cardOrFaceOrFp");

    private final String value;

    UserVerifyModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}