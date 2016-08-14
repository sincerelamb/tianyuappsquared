package com.tygas.tianyu.tianyu.data;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class UrlData {

    //app根路径
    public static String VIRTUALADDRESS = "";
    public static String APP_IP =
            "118.123.249.59:1999" +
                    "/" + VIRTUALADDRESS;
    public static String APP_ROOT_PATH = "http://" + APP_IP + "/OpenService/AppTest.ashx";
    //登录的url

    public static String LOGIN_URL = APP_ROOT_PATH + "?type=login";
    //意向客户
    public static String PCVLIST_URL = APP_ROOT_PATH + "?type=pcvlist";
    //意向客户跟进过程请求
    public static String PC_URL = APP_ROOT_PATH + "?type=pc";
    //回访请求
    public static String VISIT_URL = APP_ROOT_PATH + "?type=visit";
    //文件上传
    public static String UPLOADFILE_URL = APP_ROOT_PATH + "?type=uploadfile";
    //线索列表
    public static String CLUELIST_URL = APP_ROOT_PATH + "?type=cluelist";

    //线索跟进过程请求（明细）接口
    public static String CLUE_URL = APP_ROOT_PATH + "?type=clue";

    //线索跟踪接口
    public static String CVISIT_URL = APP_ROOT_PATH + "?type=cvisit";

    //线索录音文件
    public static String UPLOADCLUE_URL = APP_ROOT_PATH + "?type=uploadclue";

    //补全客户到店 列表 接口
    public static String PCINFOLIST_URL = APP_ROOT_PATH + "?type=pcinfolist";

    //补全客户信息
    public static String PCINFOSAVE_URL = APP_ROOT_PATH + "?type=pcinfosave";

    //补全客户电话联想
    public static String PCINFOSAVE_PHONEINFO_URL = APP_ROOT_PATH + "?type=IsSuinfolist";

    //补全客户到店 列表 接口
    public static String PCINFODAFULT_URL = APP_ROOT_PATH + "?type=IsSuinfolist";

    //版本接口号
    public static String VERSON_URL = APP_ROOT_PATH + "?type=ver";

    //外展活动
    public static String WAIACTIVITY_URL = APP_ROOT_PATH + "?type=waiactivity";

    //新增外展保存
    public static String WAIACTIVITY_SAVE_URL = APP_ROOT_PATH + "?type=waicussave";

    //维修项目明细
    public static String AFTITEMSDETAIL = APP_ROOT_PATH + "?type=aftItemsDetail";

    //配件明细
    public static String AFTPARTDETAIL = APP_ROOT_PATH + "?type=aftPartDetail";

    //客户首保
    public static String FIRST_PROTECT_URL = APP_ROOT_PATH + "?type=aftinslist";

    //客户定保
    public static String TIMING_PROTECT_URL = APP_ROOT_PATH + "?type=aftDinglist";

    //首保客户维修
    public static String FIRST_PROTECT_HISTORY_URL = APP_ROOT_PATH + "?type=aftinsDeail";

    //首保邀约拨出
    public static String FIRST_ISSHOURESULT_URL = APP_ROOT_PATH + "?type=IsShouResult";

    //上传首保的录音文件
    public static String UPLOADSHOUINV_URL = APP_ROOT_PATH + "?type=UploadShouInv";

    public static String SALESCONSLTANT_LIKENAME_URL = APP_ROOT_PATH + "?type=likename";

    //用户的个人工作主页的数据
    public static String USERKPI_URL = APP_ROOT_PATH + "?type=userkpi";

    public static String PROVINCE_URL = APP_ROOT_PATH + "?type=Province";


    public static String PHONE_MODEL = "http://118.123.249.59:1999/OS/OpenService/AppTest.ashx?type=iphonemodel";


    public static void reloadURL() {
        APP_ROOT_PATH = "http://" + APP_IP + "/OpenService/AppTest.ashx";
        LOGIN_URL = APP_ROOT_PATH + "?type=login";
        PCVLIST_URL = APP_ROOT_PATH + "?type=pcvlist";
        PC_URL = APP_ROOT_PATH + "?type=pc";
        VISIT_URL = APP_ROOT_PATH + "?type=visit";
        UPLOADFILE_URL = APP_ROOT_PATH + "?type=uploadfile";
        CLUE_URL = APP_ROOT_PATH + "?type=clue";
        CLUELIST_URL = APP_ROOT_PATH + "?type=cluelist";
        CVISIT_URL = APP_ROOT_PATH + "?type=cvisit";
        UPLOADCLUE_URL = APP_ROOT_PATH + "?type=uploadclue";
        PCINFOLIST_URL = APP_ROOT_PATH + "?type=pcinfolist";
        PCINFOSAVE_URL = APP_ROOT_PATH + "?type=pcinfosave";
        PCINFODAFULT_URL = APP_ROOT_PATH + "?type=IsSuinfolist";
        VERSON_URL = APP_ROOT_PATH + "?type=ver";
        WAIACTIVITY_URL = APP_ROOT_PATH + "?type=waiactivity";
        WAIACTIVITY_SAVE_URL = APP_ROOT_PATH + "?type=waicussave";
        FIRST_PROTECT_URL = APP_ROOT_PATH + "?type=aftinslist";
        TIMING_PROTECT_URL = APP_ROOT_PATH + "?type=aftDinglist";
        FIRST_PROTECT_HISTORY_URL = APP_ROOT_PATH + "?type=aftinsDeail";
        FIRST_ISSHOURESULT_URL = APP_ROOT_PATH + "?type=IsShouResult";
        AFTITEMSDETAIL = APP_ROOT_PATH + "?type=aftItemsDetail";
        AFTPARTDETAIL = APP_ROOT_PATH + "?type=aftPartDetail";
        UPLOADSHOUINV_URL = APP_ROOT_PATH + "?type=UploadShouInv";
        SALESCONSLTANT_LIKENAME_URL = APP_ROOT_PATH + "?type=likename";
        USERKPI_URL = APP_ROOT_PATH + "?type=userkpi";
        PROVINCE_URL = APP_ROOT_PATH + "?type=Province";
        PCINFOSAVE_PHONEINFO_URL = APP_ROOT_PATH + "?type=IsSuinfolist";
    }

}
