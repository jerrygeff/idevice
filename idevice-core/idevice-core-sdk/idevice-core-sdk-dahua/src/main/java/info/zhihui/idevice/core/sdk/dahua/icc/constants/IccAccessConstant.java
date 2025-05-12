package info.zhihui.idevice.core.sdk.dahua.icc.constants;

/**
 * 门禁相关参数
 *
 * @author jerryge
 */
public class IccAccessConstant {

    /**
     * 门禁批量授权
     */
    public static final String ASSESS_CONTROL_URL_AUTH_PERSON_BATCH_ADD_POST =
            "/evo-apigw/evo-accesscontrol/%s/card/accessControl/personAuthority/batchAuthority";

    /**
     * 门禁批量删除权限
     */
    public static final String ASSESS_CONTROL_URL_AUTH_PERSON_BATCH_DELETE_POST =
            "/evo-apigw/evo-accesscontrol/%s/card/accessControl/personAuthority/batchDeleteAuthority";

    /**
     * 门禁单个删除权限
     */
    public static final String ASSESS_CONTROL_URL_AUTH_PERSON_DELETE_SINGLE_POST =
            "/evo-apigw/evo-accesscontrol/%s/card/accessControl/personAuthority/deleteSinglePrivilege";

}
