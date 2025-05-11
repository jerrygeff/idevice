package info.zhihui.idevice.core.sdk.dahua.icc.enums.brm;

import lombok.Getter;

/**
 * 厂商枚举
 *
 * @author jerryge
 */
@Getter
public enum ManufacturerEnum {
    DAHUA("1", "大华"),
    HIKVISION("2", "海康"),
    SPON("3", "世邦"),
    LABON("4", "来邦"),
    HONEYWELL("5", "霍尼"),
    NEWBEL("6", "纽贝尔"),
    LIZE("7", "丽泽"),
    AUPU("8", "奥普"),
    HID("9", "HID"),
    ZKTECO("10", "中控"),
    SEIKO("11", "精工"),
    XINGCHEN("12", "星辰"),
    MAYSUN("13", "迈斯"),
    RENYONG("14", "仁用"),
    HAOYUN("15", "浩云"),
    HENGTONG("16", "恒通"),
    DASHI("17", "达实"),
    UP("18", "UP"),
    BOKE("19", "博克"),
    HUANGZONG("20", "黄宗"),
    ZHENXUN("21", "振讯"),
    ANSEN("22", "安森"),
    KAISHIDA("23", "凯士达"),
    PIK("24", "披克"),
    CHRISDY("25", "克立司帝"),
    DDS("26", "DDS"),
    ZHONGZHENG("27", "中正"),
    QILIN("28", "麒麟"),
    AAC("29", "AAC平台"),
    KABA("30", "KABA"),
    KESONG("31", "科松"),
    YONGLIAN("32", "永联"),
    JINKAI("33", "金凯"),
    ANLANGE("34", "安朗杰"),
    RUIFAN("35", "瑞凡"),
    HAOBAO("36", "豪宝"),
    DONGWU("37", "东屋"),
    PORIS("38", "PORIS"),
    TIANYUE("39", "天悦"),
    ZHIYUAN("40", "致远"),
    LAIJI("41", "来吉"),
    ZHONGHONG("45", "中宏"),
    SHIKE("46", "时刻"),
    BOSCH("47", "博世（BOSCH）"),
    TIANYINLONG("48", "天盈隆"),
    YINGANTE("49", "英安特"),
    BLUE_STAR("50", "蓝色星际"),
    ANBIAO("51", "安标"),
    KELONG("52", "科隆"),
    FENGYE("53", "枫叶"),
    DSC("54", "泰科(DSC)"),
    PINKE("55", "品科"),
    YINGMA("56", "盈码"),
    MEIAN("57", "美安"),
    RUISIKE("58", "瑞思可"),
    SHANGHAI_NENGDAO("59", "上海能道"),
    CONGWEN("60", "丛文"),
    XISUO("61", "希索"),
    FUJI("62", "富士"),
    ZHONGDIAN_54("63", "中电54所"),
    LIFANG("64", "立方"),
    HENAN_YINXIN("65", "河南银鑫"),
    QINRUI("66", "秦瑞"),
    BEIKE_HUISHI("67", "北科惠识"),
    XIANFENG_JIXIE("68", "先锋机械"),
    DONGSHENG("69", "东升"),
    LINGHAI("70", "凌海"),
    DISHENG("71", "帝圣"),
    ZHONGLIAN_CHUANGXIN("72", "中联创新"),
    HAIKA("73", "海卡"),
    LAOKE("74", "劳恪"),
    KEDA("75", "科达"),
    INGERSOLL_RAND("76", "英格索兰"),
    JIANGXI_BAISHENG("77", "江西百胜智能"),
    XINKAIP("78", "新开普"),
    KEDASHI("79", "科达士"),
    XIRUI("80", "玺瑞"),
    GRANIY("81", "Graniy"),
    SANCHUANG("82", "三创"),
    CONTINENATAL("83", "外海Continenatal"),
    AILIAN("84", "艾礼安"),
    HAIWAN("85", "海湾"),
    HAOEN("86", "豪恩"),
    YIKUO("87", "诣阔"),
    YANGBANG("88", "仰邦"),
    LINGXIN("89", "灵信"),
    OTHER("90", "其他"),
    BEIJING_SHIJIZHIXING("91", "北京世纪之星"),
    LINGXINGYU("92", "灵星雨"),
    KUAIYU("93", "快鱼厂商"),
    SANXIONG_JIGUANG("94", "三雄极光"),
    HUAWEI("95", "华为"),
    UOV("96", "UOV"),
    TEST("99", "测试厂商"),
    TIANDIWEIYE("100", "天地伟业"),
    UNIVIEW("101", "宇视"),
    JIALIAN("102", "嘉联");

    private final String key;
    private final String value;

    ManufacturerEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public static String getValueByKey(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        
        for (ManufacturerEnum item : ManufacturerEnum.values()) {
            if (item.getKey().equals(key)) {
                return item.getValue();
            }
        }
        
        return "";
    }
} 