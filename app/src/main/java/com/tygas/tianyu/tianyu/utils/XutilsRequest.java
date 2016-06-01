package com.tygas.tianyu.tianyu.utils;

import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.tygas.tianyu.tianyu.data.Config;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class XutilsRequest {
    public static String PC = "";
    //  public static String PC = "BQ2015";

    public static final String KEY = "zAt3B&#i&AcdWnHg";
    private static final String LOG_TAG = "XutilsRequest";


    /**
     * 获取登录的个人用户的个人工作主页的 kpi
     *
     * @param Type  需要查询的项目
     * @param EmpID 用户的id
     * @return
     */
    public static RequestParams getUserKpiRequestParams(String Type, String EmpID) {
        HashMap<String, String> data = new HashMap<>();
        data.put("Type", Type);
        data.put("EmpID", EmpID);

        String ts = String.valueOf(System.currentTimeMillis());
        String dataStr = getJsonFromMap(data);

        String sign = XutilsRequest.getSign("userkpi", XutilsRequest.PC, ts, dataStr, XutilsRequest.KEY);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", dataStr);
        Log.i(LOG_TAG, "[请求的参数] data " + dataStr + "   pc " + PC + "  sign " + sign + "   ts " + ts);
        return requestParams;
    }

    /**
     * 获取销售顾问的请求参数
     *
     * @return
     */
    public static RequestParams getSalesConsltantRequestParams(String EmpName) {
        HashMap<String, String> data = new HashMap<>();
        data.put("EmpName", EmpName);
        String ts = String.valueOf(System.currentTimeMillis());
        String dataStr = getJsonFromMap(data);
        String sign = XutilsRequest.getSign("likename", XutilsRequest.PC, ts, dataStr, XutilsRequest.KEY);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", dataStr);
        Log.i(LOG_TAG, "[请求的参数] data " + dataStr + "   pc " + PC + "  sign " + sign + "   ts " + ts);
        return requestParams;
    }


    /**
     * 首保电话拨出后需要请求的参数
     *
     * @param frameNum           车架号
     * @param dailResult         拨出结果
     * @param talkProcess        邀约情况
     * @param isReInviteCall     再次邀约
     * @param isEnd              终止本次保养提醒
     * @param nowMaintainMileage 本次已保养时间
     * @param nowMaintainDate    本次保养里程
     * @param isTurnOverCus      客户流失
     * @param isSuccess          成功邀约
     * @param predictFitDate     预计保养日期
     * @param lostResultId       流失原因
     * @param reInviteCallDate   再次邀约时间
     * @param subscribeInTime    预计进厂时间
     * @param subscribeLastTime  最迟进厂时间
     * @param serviceEmpId       服务顾问
     * @param empId              销售顾问
     * @param ShouOrDing         "首保" 或者 "定保"
     * @return
     */
    public static RequestParams getFirstProjectCallSuccess(String frameNum, String dailResult, String talkProcess,
                                                           String isReInviteCall, String reInviteCallDate,
                                                           String isEnd, String nowMaintainDate, String nowMaintainMileage,
                                                           String isTurnOverCus, String lostResultId,
                                                           String isSuccess, String subscribeInTime, String subscribeLastTime, String serviceEmpId, String subscribeTypeID,
                                                           String empId, String ShouOrDing, String predictFitDate, String MileageNow) {
        HashMap<String, String> data = new HashMap<>();
        data.put("FrameNum", frameNum);
        data.put("DailResult", dailResult);
        data.put("TalkProcess", talkProcess);
        data.put("IsReInviteCall", isReInviteCall);
        data.put("IsEnd", isEnd);
        data.put("NowMaintainDate", nowMaintainDate);
        data.put("NowMaintainMileage", nowMaintainMileage);
        data.put("IsTurnOverCus", isTurnOverCus);
        data.put("IsSuccess", isSuccess);
        data.put("PredictFitDate", predictFitDate);
        data.put("LostResultID", lostResultId);
        data.put("ReInviteCallDate", reInviteCallDate);
        data.put("SubscribeInTime", subscribeInTime);
        data.put("SubscribeLastTime", subscribeLastTime);
        data.put("ServiceEmpID", serviceEmpId);
        data.put("EmpID", empId);
        data.put("ShouOrDing", ShouOrDing);
        data.put("SubscribeTypeID", subscribeTypeID);
        data.put("MileageNow", MileageNow);


        RequestParams requestParams = new RequestParams();
        String ts = String.valueOf(System.currentTimeMillis());
        String dataStr = getJsonFromMap(data);
        String sign = getSign("IsShouResult", PC, ts, dataStr, KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", dataStr);
        Log.i(LOG_TAG, "[提交的数据]" + dataStr);
        return requestParams;
    }


    /**
     * 配件明细
     *
     * @return
     */
    public static RequestParams getAftPartDetail(String reciveComeId) {
        RequestParams requestParams = new RequestParams();
        HashMap<String, String> map = new HashMap<>();
        map.put("ReciveComeID", reciveComeId);
        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("aftPartDetail", PC, ts, data, KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }


    /**
     * 维修项目明细的请求
     *
     * @return
     */
    public static RequestParams getAftItemsDetail(String reciveComeId) {
        RequestParams requestParams = new RequestParams();
        HashMap<String, String> map = new HashMap<>();
        map.put("ReciveComeID", reciveComeId);
        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("aftItemsDetail", PC, ts, data, KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        Log.i(LOG_TAG, "[data]" + data + "  [pc]" + PC);
        return requestParams;
    }


    /**
     * 获取新增外展的时候需要提交的数据
     *
     * @return
     */
    public static RequestParams getWaiUpdateParams(String name, String phone, String sorce,
                                                   String province, String permanent, String county,
                                                   String receptionDate, String receptionActv, String receptionStart, String receptionEnd,
                                                   String alongPersonNumber, String storeChannel, String infoChannel, String turnto,
                                                   String intentLevel, String nextCallTime,
                                                   String intentCars, String intentCarModle, String intentCarColor,
                                                   String carUse, String newChange, String mortgage,
                                                   String feat, String talkPro, String empID
    ) {

        HashMap<String, String> map = new HashMap<>();
        map.put("CustomerName", name);
        map.put("Phone", phone);
        map.put("CustomerSource", sorce);
        map.put("IntentLevel", intentLevel);
        map.put("CarSeriesID", intentCars);
        map.put("CarModelID", intentCarModle);
        map.put("CarColorID", intentCarColor);
        map.put("FocusCarmodelID", feat);
        map.put("FollowPeo", alongPersonNumber);
        map.put("QuoteSituation", "0");
        map.put("ChannelId", storeChannel);
        map.put("IsDrive", "0");
        map.put("UseageID", carUse);
        map.put("PayType", mortgage);
        map.put("TalkProcess", talkPro);
        map.put("Comedate", receptionDate);
        map.put("CampaignName", receptionActv);
        map.put("ComeTime", receptionStart);
        map.put("LeaveTime", receptionEnd);
        map.put("SourceChannelID", infoChannel);
        map.put("LiveProvince", province);
        map.put("LiveCity", permanent);
        map.put("LiveArea", county);
        map.put("IsChange", newChange);
        map.put("NextTime", nextCallTime);
        map.put("CarNo", turnto);
        map.put("EmpID", empID);
        RequestParams requestParams = new RequestParams();
        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("waicussave", PC, ts, data, KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        Log.i(LOG_TAG, "[data]" + data);
        return requestParams;
    }

    //获取到当前时间的车展活动
    public static RequestParams getWaiActivityParams(String time) {
        RequestParams requestParams = new RequestParams();
        String ts = String.valueOf(System.currentTimeMillis());
        String data = "{\"Comedate\":\"" + time + "\"}";
        String sign = getSign("waiactivity", PC, ts, data, KEY);
        Log.i(LOG_TAG, "[data]" + data);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }


    //获取到登录的参数
    public static RequestParams getLoginParams(String username, String password) {
        RequestParams requestParams = new RequestParams();
        String ts = String.valueOf(System.currentTimeMillis());
        String data = "{\"user\":\"" + username + "\",\"pw\":\"" + sha1Code(password) + "\"}";
        String sign = getSign("login", PC, ts, data, KEY);
        Log.d("data", data);
        Log.d("sign", sign);
        Log.d("ts", ts);

        //Log.i(LOG_TAG,"[登录的参数] [ts] "+ts+"  [data] "+data+"  [sign] "+sign+"  [key]"+ KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }

    //获取到意向客户的参数
    public static RequestParams getPvcList(String empid, String customername, String customerphone,
                                           String customerlevel, String begintime, String endtime,
                                           String pageindex, String pagesize, String cusempname, String isExpiry) {
        HashMap<String, String> map = new HashMap<>();
        map.put("empid", empid);
        map.put("customername", customername);
        map.put("customerphone", customerphone);
        map.put("customerlevel", customerlevel);
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        map.put("pageindex", pageindex);
        map.put("pagesize", pagesize);
        map.put("cusempname", cusempname); // 这个字段是什么 ？？
        map.put("CallStatus", isExpiry);
        String data = getJsonFromMap(map);
        String ts = String.valueOf(System.currentTimeMillis());
        String sign = getSign("pcvlist", PC, ts, data, KEY);
        if (Config.isDebug)
            Log.i(LOG_TAG, "[请求数据]   [sign] " + sign + "   [ts] " + ts + "   [data] " + data);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }


    //意向客户跟进过程请求
    public static RequestParams getPc(String customerID) {
        RequestParams requestParams = new RequestParams();
        String ts = String.valueOf(System.currentTimeMillis());
        String data = "{\"CustomerID\":\"" + customerID + "\"}";
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        requestParams.addBodyParameter("sign", getSign("pc", PC, ts, data, KEY));
        return requestParams;
    }

    //回访请求
    public static RequestParams getVisit(String customerID, String empID, String talkProcess,
                                         String intentLevel, String isSuccess, String failBackType,
                                         String bakDateTime, String failType, String otherBrandName,
                                         String otherSeriesName, String isSubscribe, String quoteSituation,
                                         String subscribeDate, String remark) {
        HashMap<String, String> data = new HashMap<>();
        data.put("CustomerID", customerID);
        data.put("EmpID", empID);
        data.put("TalkProcess", talkProcess);
        data.put("IntentLevel", intentLevel);
        data.put("IsSuccess", isSuccess);
        data.put("FailBackType", failBackType);
        data.put("BackDateTime", bakDateTime);
        data.put("FailType", failType);
        data.put("OtherBrandName", otherBrandName);
        data.put("OtherSeriesName", otherSeriesName);
        data.put("IsSubscribe", isSubscribe);
        data.put("QuoteSituation", quoteSituation);
        data.put("SubscribeDate", subscribeDate);
        data.put("Remark", remark);

        String dataStr = getJsonFromMap(data);
        String ts = String.valueOf(System.currentTimeMillis());
        String sign = getSign("visit", PC, ts, dataStr, KEY);//这里和文档不一样
        if (Config.isDebug)
            Log.i(LOG_TAG, "[提交的数据] [data]" + dataStr + "  [pc]" + PC + "   [ts]" + ts + "   [sign]" + sign + "  [key]" + KEY);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", dataStr);
        requestParams.addBodyParameter("sign", sign);
        return requestParams;
    }


    //文件上传请求    -->文件上传有问题  没有data部分  MD5([projectCode] + [timestamp] + [key]).ToUpper
    public static RequestParams getUploadFile(String callFollowID, String timeInt, File file) {
        RequestParams requestParams = new RequestParams();

        String ts = String.valueOf(System.currentTimeMillis());
        String sign = getSign("uploadfile", PC, ts, "", KEY);

        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("timeInt", timeInt);
        requestParams.addBodyParameter("CallFollowID", callFollowID);
        requestParams.addBodyParameter("recodeing", file);
        return requestParams;
    }


    //线索列表接口
    public static RequestParams getClueList(String empId, String clueName, String cluePhone,
                                            String isExpiry, String begintime, String endtime,
                                            String pageindex, String pagesize, String cusempname, String customerlevel) {
        HashMap<String, String> map = new HashMap<>();
        map.put("empid", empId);
        map.put("clueName", clueName);
        map.put("cluePhone", cluePhone);
        map.put("isExpiry", isExpiry);
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        map.put("pageindex", pageindex);
        map.put("pagesize", pagesize);
        map.put("clueEmpName", cusempname);
        map.put("customerlevel", customerlevel);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("cluelist", PC, ts, data, KEY);
        /**
         * requestParams.addBodyParameter("pc", PC);
         requestParams.addBodyParameter("sign", sign);
         requestParams.addBodyParameter("ts", ts);
         requestParams.addBodyParameter("data", data);
         */
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("data", data);
        if (Config.isDebug)
            Log.i(LOG_TAG, "[请求的数据] [data]" + data + "  [sign]" + sign + "   [ts]" + ts + "  [pc]" + PC + "  [key]" + KEY);
        return requestParams;
    }

    //线索明细
    public static RequestParams getClue(String clueID) {
        RequestParams requestParams = new RequestParams();

        String ts = String.valueOf(System.currentTimeMillis());
        String data = "{\"clueID\":\"" + clueID + "\"}";
        String sign = getSign("clue", PC, ts, data, KEY);
        if (Config.isDebug)
            Log.i(LOG_TAG, "[请求的数据] [data]" + data + "  [sign]" + sign + "   [ts]" + ts + "  [pc]" + PC + "  [key]" + KEY);
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }


    //线索跟踪接口
    public static RequestParams getCvisit(String clueID, String empID, String talkProcess, String intentLevel,
                                          String isSuccess, String failBackType, String backDateTime, String failType,
                                          String otherBrandName, String otherSeriesName, String isSubscribe, String SubscribeDate,
                                          String Remark) {
        HashMap<String, String> map = new HashMap<>();
        map.put("clueID", clueID);
        map.put("empID", empID);
        map.put("talkProcess", talkProcess);
        map.put("intentLevel", intentLevel);
        map.put("isSuccess", isSuccess);
        map.put("failBackType", failBackType);
        map.put("backDateTime", backDateTime);
        map.put("failType", failType);
        map.put("otherBrandName", otherBrandName);
        map.put("otherSeriesName", otherSeriesName);
        map.put("isSubscribe", isSubscribe);
        map.put("SubscribeDate", SubscribeDate);
        map.put("Remark", Remark);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("cvisit", PC, ts, data, KEY);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);
        if (Config.isDebug)
            Log.i(LOG_TAG, "[提交的数据]  [data] " + data + "   [ts]" + ts + "   [sign]" + sign);
        return requestParams;
    }


    //补全客户
    public static RequestParams getCpCustomerList(String empId, String clueName, String cluePhone,
                                                  String begintime, String endtime, int pageindex, int pagesize, String isSuppleState, String Recempname) {

        HashMap<String, String> map = new HashMap<>();
        map.put("empid", empId);
        map.put("customerName", clueName);
        map.put("customerPhone", cluePhone);
//        if (TextUtils.isEmpty(begintime)) {
//            map.put("begintime", null);
//        } else {
        map.put("begintime", begintime);
//        }

//        if (TextUtils.isEmpty(endtime)) {
//            map.put("endtime", null);
//        } else {
        map.put("endtime", endtime);
//        }

        map.put("pageindex", pageindex + "");
        map.put("pagesize", pagesize + "");
        map.put("Recempname", Recempname);
        map.put("IsSuppleState", isSuppleState);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("pcinfolist", PC, ts, data, KEY);

        Log.d("data", data);
        Log.d("sign", sign);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }


    public static RequestParams getFpCustomerList(String empId, String CarOwnerName, String CarOwnerPhone,
                                                  String begintime, String endtime, int pageindex, int pagesize, String DueState, String ServiceEmpName, String CarInfo) {

        HashMap<String, String> map = new HashMap<>();

        map.put("empid", empId);
        map.put("CarOwnerName", CarOwnerName);
        map.put("CarOwnerPhone", CarOwnerPhone);
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        map.put("pageindex", pageindex + "");
        map.put("pagesize", pagesize + "");
        map.put("ServiceEmpName", ServiceEmpName);
        map.put("DueState", DueState);
        map.put("CarInfo", CarInfo);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        //String data = "{\"pageindex\":1,\"CarOwnerName\":\"\",\"ServiceEmpName\":\"\",\"CarInfo\":\"\",\"begintime\":\"2016-4-21\",\"DueState\":\"\",\"CarOwnerPhone\":\"\",\"pagesize\":10,\"endtime\":\"2016-4-21\",\"empid\":1}";
        String sign = getSign("aftinslist", PC, ts, data, KEY);

        Log.d("data", data);
        Log.d("sign", sign);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }


    public static RequestParams getTpCustomerList(String empId, String CarOwnerName, String CarOwnerPhone,
                                                  String begintime, String endtime, int pageindex, int pagesize, String DueState, String ServiceEmpName, String CarInfo) {

        HashMap<String, String> map = new HashMap<>();
        map.put("empid", empId);
        map.put("CarOwnerName", CarOwnerName);
        map.put("CarOwnerPhone", CarOwnerPhone);
        map.put("begintime", begintime);
        map.put("endtime", endtime);
        map.put("pageindex", pageindex + "");
        map.put("pagesize", pagesize + "");
        map.put("ServiceEmpName", ServiceEmpName);
        map.put("DueState", DueState);
        map.put("CarInfo", CarInfo);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("aftDinglist", PC, ts, data, KEY);

        Log.d("data", data);
        Log.d("sign", sign);
        Log.d("ts", ts);
        Log.d("pc", PC);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }


    public static RequestParams getFpCustomerHistory(String FrameNum) {

        HashMap<String, String> map = new HashMap<>();
        map.put("FrameNum", FrameNum);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("aftinsDeail", PC, ts, data, KEY);

        Log.d("data", data);
        Log.d("sign", sign);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }


    public static RequestParams getPhoneModel(String phoneModel, String phoneSysModel, String phoneSDKNum, String YesOrNo) {

        HashMap<String, String> map = new HashMap<>();
        map.put("PhoneModel", phoneModel);
        map.put("PhoneSysModel", phoneSysModel);
        map.put("PhoneSysSDKNo", phoneSDKNum);
        map.put("YesOrNo", YesOrNo);

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("iphonemodel", PC, ts, data, KEY);

        Log.d("data", data);
        Log.d("sign", sign);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }


    //补全客户
    public static RequestParams saveCpCustomer(String empId, CpCustomer cpCustomer
    ) {
        Log.d("cpcustomer", cpCustomer.toString());
        HashMap<String, String> map = new HashMap<>();
        map.put("empID", empId);
        map.put("reciveComeID", cpCustomer.getReciveComeId());
        map.put("customerName", cpCustomer.getCustomerName());
        map.put("customerPhone", cpCustomer.getCustomerPhone());
        map.put("liveProvince", cpCustomer.getLiveProvince());
        map.put("liveCity", cpCustomer.getLiveCity());
        map.put("liveArea", cpCustomer.getLiveArea());
        map.put("channelId", cpCustomer.getChannelId());
        map.put("sourceChannelId", cpCustomer.getSourceChannelId());
        map.put("oldCusCarNO", cpCustomer.getOldCusCarNO());
        map.put("isDrive", cpCustomer.getIsDrive());
        map.put("followPeo", cpCustomer.getFollowPeo());
        map.put("intentLevel", cpCustomer.getIntentLevel());
        map.put("carSeriesName", cpCustomer.getCarSeriesName());
        map.put("carModelName", cpCustomer.getCarModelName());
        map.put("carColor", cpCustomer.getCarColor());
        map.put("UseageID", cpCustomer.getUseageID());
        map.put("IsChange", cpCustomer.getIsChange());
        map.put("FocusCarmodelID", cpCustomer.getFocusCarmodelID());
        map.put("TalkProcess", cpCustomer.getTalkProcess());
        map.put("paytype", cpCustomer.getPaytype());
        map.put("QuoteSituation", cpCustomer.getQuoteSituation());


//        map.put("empID", "1");
//        map.put("reciveComeID", "3232");
//        map.put("customerName", "xu");
//        map.put("customerPhone", "");
//        map.put("liveProvince", "");
//        map.put("liveCity", "");
//        map.put("liveArea", "");
//        map.put("channelId", "66");
//        map.put("sourceChannelId", "0");
//        map.put("oldCusCarNO", "");
//        map.put("isDrive", "");
//        map.put("followPeo", "");
//        map.put("intentLevel", "");
//        map.put("carSeriesName", "D50");
//        map.put("carModelName", "");
//        map.put("carColor", "");
//        map.put("UseageID", "");
//        map.put("IsChange", "");
//        map.put("FocusCarmodelID", "");
//        map.put("TalkProcess", "ssssss");

        String ts = String.valueOf(System.currentTimeMillis());
        String data = getJsonFromMap(map);
        String sign = getSign("pcinfosave", PC, ts, data, KEY);
        Log.d("sign", sign);
        Log.d("data", data);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        return requestParams;
    }

    public static RequestParams getDeafultCPinfo(String ReciveComeID) {
        String ts = String.valueOf(System.currentTimeMillis());
        HashMap<String, String> map = new HashMap<>();
        map.put("ReciveComeID", ReciveComeID);
        String data = getJsonFromMap(map);
        String sign = getSign("IsSuinfolist", PC, ts, data, KEY);

        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);

        Log.d("sign", sign);
        Log.d("pc", PC);
        Log.d("ts", ts);
        Log.d("data", data);

        return requestParams;
    }


    public static RequestParams getVersonCode() {
        String ts = String.valueOf(System.currentTimeMillis());
        String sign = getSign("ver", PC, ts, KEY);
        RequestParams requestParams = new RequestParams();
        Log.d("sss", "sign");
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        return requestParams;
    }

    public static RequestParams getPr(String province, String city) {
        String ts = String.valueOf(System.currentTimeMillis());

        HashMap<String, String> map = new HashMap<>();
        map.put("Province", province);
        map.put("City", city);
        String data = getJsonFromMap(map);
        String sign = getSign("Province", PC, ts, data, KEY);
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("pc", PC);
        requestParams.addBodyParameter("ts", ts);
        requestParams.addBodyParameter("sign", sign);
        requestParams.addBodyParameter("data", data);
        Log.d("sign", sign);
        Log.d("ts", ts);

        return requestParams;
    }


    private static String getJsonFromMap(Map<String, String> data) {
        String result = "{";
        Set<Map.Entry<String, String>> entries = data.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result += "\"" + entry.getKey() + "\":" + "\"" + entry.getValue() + "\",";
        }
        result = result.endsWith(",") ? result.substring(0, result.length() - 1) : result;
        return result + "}";
    }

    private static String getJsonFromMap(Map<String, String> data, boolean flag) {
        String result = "{";
        Set<Map.Entry<String, String>> entries = data.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            result += "'" + entry.getKey() + "':" + "'" + entry.getValue() + "',";
        }
        result = result.endsWith(",") ? result.substring(0, result.length() - 1) : result;
        return result + "}";
    }

    public static String getSign(String type, String pc, String time, String data, String key) {
        StringBuffer sb = new StringBuffer();
        sb.append(type);
        sb.append(pc);
        sb.append(time);
        sb.append(data);
        sb.append(key);
        Log.d("sign", sb.toString());
        return md5Code(sb.toString()).toUpperCase();
    }

    public static String getSign(String type, String pc, String time, String key) {
        StringBuffer sb = new StringBuffer();
        sb.append(type);
        sb.append(pc);
        sb.append(time);
        sb.append(key);
        Log.d("sign", sb.toString());
        return md5Code(sb.toString()).toUpperCase();
    }

    private static String md5Code(String before) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(before.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String sha1Code(String before) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hash = digest.digest(before.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}
