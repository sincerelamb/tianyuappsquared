package com.tygas.tianyu.tianyu.utils;

import android.os.Bundle;
import android.util.Log;

import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;
import com.tygas.tianyu.tianyu.ui.model.MaintenanceHistory;
import com.tygas.tianyu.tianyu.ui.model.PID;
import com.tygas.tianyu.tianyu.ui.model.SaveStatu;
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/21.
 */
public class JsonParser {
    private static String SUCCESS = "Success";
    private static String FAIL = "Fail";
    private static String UserPWIncorrent = "UserPWIncorrent";
    private static final String LOG_TAG = "JsonParser";

    public static Bundle loginParser(String string) {
        Log.i(LOG_TAG, "[登录返回的数据]" + string);
        Bundle bundle = new Bundle();
        User user = new User();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");

            String message = jsonObject.getString("message");
            user.setLoginInformation(message);

            if (SUCCESS.equals(status)) {
                String data_str = jsonObject.getString("data");
                Log.i(LOG_TAG, "[登录返回的data]" + data_str);
                JSONObject data = new JSONObject(data_str);
                user.setIsLoginSuccess(true);
                user.setEmpId(data.getString("EmpID"));
                user.setEmpName(data.getString("EmpName"));
                JSONObject dataList = data.getJSONObject("SelectDataList");

                if (!dataList.isNull("PID")) {
                    JSONArray PIDList = dataList.getJSONArray("PID");
                    List<PID> list_pid = new ArrayList<PID>();
                    if (null != PIDList && PIDList.length() > 0) {
                        for (int i = 0; i < PIDList.length(); i++) {
                            PID pid = new PID();
                            JSONObject jsonObject_i = PIDList.getJSONObject(i);
                            pid.setFormID(jsonObject_i.getString("FormID"));
                            list_pid.add(pid);
                        }
                    }
                    user.setList_PID(list_pid);
                }

                if (!dataList.isNull("ChannelList")) {
                    JSONArray channelList = dataList.getJSONArray("ChannelList");
                    List<UserPtInfoModel> userPtInfoModels_channel = new ArrayList<>();
                    if (null != channelList && channelList.length() > 0) {
                        for (int i = 0; i < channelList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = channelList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("ChannelList");
                            userPtInfoModels_channel.add(userPtInfoModel);
                        }
                    }
                    user.setList_Channel(userPtInfoModels_channel);
                }

                if (!dataList.isNull("UseageList")) {
                    JSONArray useageList = dataList.getJSONArray("UseageList");
                    List<UserPtInfoModel> userPtInfoModels_useage = new ArrayList<>();
                    if (null != useageList && useageList.length() > 0) {
                        for (int i = 0; i < useageList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = useageList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("UseageList");
                            userPtInfoModels_useage.add(userPtInfoModel);
                        }
                    }
                    user.setList_Useage(userPtInfoModels_useage);
                }

                if (!dataList.isNull("OtherBrandList")) {
                    JSONArray brandList = dataList.getJSONArray("OtherBrandList");
                    List<UserPtInfoModel> userPtInfoModels_brand = new ArrayList<>();
                    if (null != brandList && brandList.length() > 0) {
                        for (int i = 0; i < brandList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = brandList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("OtherBrandList");
                            userPtInfoModels_brand.add(userPtInfoModel);
                        }
                    }
                    user.setList_Brand(userPtInfoModels_brand);
                }

                if (!dataList.isNull("CarBrand")) {
                    JSONArray intent_brandList = dataList.getJSONArray("CarBrand");
                    List<UserPtInfoModel> userPtInfoModels_intent_brand = new ArrayList<>();
                    if (null != intent_brandList && intent_brandList.length() > 0) {
                        for (int i = 0; i < intent_brandList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = intent_brandList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("CarBrand");
                            userPtInfoModels_intent_brand.add(userPtInfoModel);
                        }
                    }
                    user.setList_IntentBrand(userPtInfoModels_intent_brand);
                }


                if (!dataList.isNull("CarSeries")) {
                    JSONArray carSeriesList = dataList.getJSONArray("CarSeries");
                    List<UserPtInfoModel> userPtInfoModels_carSeries = new ArrayList<>();
                    if (null != carSeriesList && carSeriesList.length() > 0) {
                        for (int i = 0; i < carSeriesList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = carSeriesList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("CarSeries");
                            userPtInfoModels_carSeries.add(userPtInfoModel);
                        }
                    }
                    user.setList_CarSeries(userPtInfoModels_carSeries);
                }

                if (!dataList.isNull("FocusCarmodelList")) {
                    JSONArray focusList = dataList.getJSONArray("FocusCarmodelList");
                    Log.i(LOG_TAG, "[focuscarmodelist]" + focusList);
                    List<UserPtInfoModel> userPtInfoModels_focus = new ArrayList<>();
                    if (null != focusList && focusList.length() > 0) {
                        for (int i = 0; i < focusList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = focusList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("FocusCarmodelList");
                            userPtInfoModels_focus.add(userPtInfoModel);
                        }
                    }
                    user.setList_Focus(userPtInfoModels_focus);
                }

                if (!dataList.isNull("CheckColorList")) {
                    JSONArray checkColorList = dataList.getJSONArray("CheckColorList");
                    List<UserPtInfoModel> userPtInfoModels_checkColor = new ArrayList<>();
                    if (null != checkColorList && checkColorList.length() > 0) {
                        for (int i = 0; i < checkColorList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = checkColorList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("CheckColorList");
                            userPtInfoModels_checkColor.add(userPtInfoModel);
                        }
                    }
                    user.setList_CheckColorList(userPtInfoModels_checkColor);
                }

                if (!dataList.isNull("FailType")) {
                    JSONArray failTypeist = dataList.getJSONArray("FailType");
                    List<UserPtInfoModel> userPtInfoModels_failType = new ArrayList<>();
                    if (null != failTypeist && failTypeist.length() > 0) {
                        for (int i = 0; i < failTypeist.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = failTypeist.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("FailType");
                            userPtInfoModels_failType.add(userPtInfoModel);
                        }
                    }
                    user.setList_FailType(userPtInfoModels_failType);
                }


                if (!dataList.isNull("SourceChannel")) {
                    JSONArray listSourceChannel = dataList.getJSONArray("SourceChannel");
                    List<UserPtInfoModel> userPtInfoModels_SourceChannel = new ArrayList<>();
                    if (null != listSourceChannel && listSourceChannel.length() > 0) {
                        for (int i = 0; i < listSourceChannel.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listSourceChannel.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("SourceChannel");
                            userPtInfoModels_SourceChannel.add(userPtInfoModel);
                        }
                    }
                    user.setList_SourceChannel(userPtInfoModels_SourceChannel);
                }

                if (!dataList.isNull("CustomerLevel")) {
                    JSONArray listCustomerLevel = dataList.getJSONArray("CustomerLevel");
                    List<UserPtInfoModel> userPtInfoModels_CustomerLevel = new ArrayList<>();
                    if (null != listCustomerLevel && listCustomerLevel.length() > 0) {
                        for (int i = 0; i < listCustomerLevel.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listCustomerLevel.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("CustomerLevel");
                            userPtInfoModels_CustomerLevel.add(userPtInfoModel);
                        }
                    }
                    user.setList_CustomerLevel(userPtInfoModels_CustomerLevel);
                }


                if (!dataList.isNull("PayType")) {
                    JSONArray listPayType = dataList.getJSONArray("PayType");
                    List<UserPtInfoModel> userPtInfoModels_PayType = new ArrayList<>();
                    if (null != listPayType && listPayType.length() > 0) {
                        for (int i = 0; i < listPayType.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listPayType.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("PayType");
                            userPtInfoModels_PayType.add(userPtInfoModel);
                        }
                    }
                    user.setList_PayType(userPtInfoModels_PayType);
                }

                if (!dataList.isNull("WishBuyDate")) {
                    JSONArray listWishBuyDate = dataList.getJSONArray("WishBuyDate");
                    List<UserPtInfoModel> userPtInfoModels_WishBuyDate = new ArrayList<>();
                    if (null != listWishBuyDate && listWishBuyDate.length() > 0) {
                        for (int i = 0; i < listWishBuyDate.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listWishBuyDate.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("WishBuyDate");
                            userPtInfoModels_WishBuyDate.add(userPtInfoModel);
                        }
                    }
                    user.setList_WishBuyDate(userPtInfoModels_WishBuyDate);
                }


                if (!dataList.isNull("TimeFrame")) {
                    JSONArray listTimeFrame = dataList.getJSONArray("TimeFrame");
                    List<UserPtInfoModel> userPtInfoModels_TimeFrame = new ArrayList<>();
                    if (null != listTimeFrame && listTimeFrame.length() > 0) {
                        for (int i = 0; i < listTimeFrame.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listTimeFrame.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("TimeFrame");
                            userPtInfoModels_TimeFrame.add(userPtInfoModel);
                        }
                    }
                    user.setList_TimeFrame(userPtInfoModels_TimeFrame);
                }


                if (!dataList.isNull("CarModel")) {
                    JSONArray listCarModels = dataList.getJSONArray("CarModel");
                    List<UserPtInfoModel> userPtInfoModels_CarModels = new ArrayList<>();
                    if (null != listCarModels && listCarModels.length() > 0) {
                        for (int i = 0; i < listCarModels.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = listCarModels.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("CarModels");
                            userPtInfoModels_CarModels.add(userPtInfoModel);
                        }
                    }
                    user.setList_CarModels(userPtInfoModels_CarModels);
                }

                if (!dataList.isNull("OtherSeriesList")) {
                    JSONArray otherSeriesList = dataList.getJSONArray("OtherSeriesList");
                    List<UserPtInfoModel> userPtInfoModels_otherSeries = new ArrayList<>();
                    if (null != otherSeriesList && otherSeriesList.length() > 0) {
                        for (int i = 0; i < otherSeriesList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = otherSeriesList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("OtherSeries");
                            userPtInfoModels_otherSeries.add(userPtInfoModel);
                        }
                    }
                    user.setList_OtherCarSeries(userPtInfoModels_otherSeries);
                }

                if (!dataList.isNull("UserNum")) {
                    JSONArray UserNumList = dataList.getJSONArray("UserNum");
                    List<UserPtInfoModel> userPtInfoModels_UserNum = new ArrayList<>();
                    if (null != UserNumList && UserNumList.length() > 0) {
                        for (int i = 0; i < UserNumList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = UserNumList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("UserNum");
                            userPtInfoModels_UserNum.add(userPtInfoModel);
                        }
                    }
                    user.setList_UserNum(userPtInfoModels_UserNum);
                }

                if (!dataList.isNull("LevelNotDefeat")) {
                    JSONArray LevelNotDefeatList = dataList.getJSONArray("LevelNotDefeat");
                    List<UserPtInfoModel> userPtInfoModels_LevelNotDefeat = new ArrayList<>();
                    if (null != LevelNotDefeatList && LevelNotDefeatList.length() > 0) {
                        for (int i = 0; i < LevelNotDefeatList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = LevelNotDefeatList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("LevelNotDefeat");
                            userPtInfoModels_LevelNotDefeat.add(userPtInfoModel);
                        }
                    }
                    user.setList_LevelNotDefeat(userPtInfoModels_LevelNotDefeat);
                }

                if (!dataList.isNull("LevelNotClinch")) {
                    JSONArray LevelNotClinchList = dataList.getJSONArray("LevelNotClinch");
                    List<UserPtInfoModel> userPtInfoModels_LevelNotClinch = new ArrayList<>();
                    if (null != LevelNotClinchList && LevelNotClinchList.length() > 0) {
                        for (int i = 0; i < LevelNotClinchList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = LevelNotClinchList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("LevelNotClinch");
                            userPtInfoModels_LevelNotClinch.add(userPtInfoModel);
                        }
                    }
                    user.setList_LevelNotClinch(userPtInfoModels_LevelNotClinch);
                }
                if (!dataList.isNull("LevelAll")) {
                    JSONArray LevelAllList = dataList.getJSONArray("LevelAll");
                    List<UserPtInfoModel> userPtInfoModels_LevelAll = new ArrayList<>();
                    if (null != LevelAllList && LevelAllList.length() > 0) {
                        for (int i = 0; i < LevelAllList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = LevelAllList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("LevelAll");
                            userPtInfoModels_LevelAll.add(userPtInfoModel);
                        }
                    }
                    user.setList_LevelAll(userPtInfoModels_LevelAll);
                }

                if (!dataList.isNull("SubscribeType")) {
                    JSONArray SubscribeTypeList = dataList.getJSONArray("SubscribeType");
                    List<UserPtInfoModel> userPtInfoModels_SubscribeType = new ArrayList<>();
                    if (null != SubscribeTypeList && SubscribeTypeList.length() > 0) {
                        for (int i = 0; i < SubscribeTypeList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = SubscribeTypeList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("SubscribeType");
                            userPtInfoModels_SubscribeType.add(userPtInfoModel);
                        }
                    }
                    user.setList_SubscribeType(userPtInfoModels_SubscribeType);
                }


                if (!dataList.isNull("LostResult")) {
                    JSONArray LostResultList = dataList.getJSONArray("LostResult");
                    List<UserPtInfoModel> userPtInfoModels_LostResult = new ArrayList<>();
                    if (null != LostResultList && LostResultList.length() > 0) {
                        for (int i = 0; i < LostResultList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = LostResultList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("LostResult");
                            userPtInfoModels_LostResult.add(userPtInfoModel);
                        }
                    }
                    user.setList_LostResult(userPtInfoModels_LostResult);
                }

                if (!dataList.isNull("AllEmpID")) {
                    JSONArray AllEmpIDList = dataList.getJSONArray("AllEmpID");
                    List<UserPtInfoModel> userPtInfoModels_AllEmpID = new ArrayList<>();
                    if (null != AllEmpIDList && AllEmpIDList.length() > 0) {
                        for (int i = 0; i < AllEmpIDList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = AllEmpIDList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("AllEmpID");
                            userPtInfoModels_AllEmpID.add(userPtInfoModel);
                        }
                    }
                    user.setList_AllEmpID(userPtInfoModels_AllEmpID);
                }


                if (!dataList.isNull("ProjectCode")) {
                    String projectCode = dataList.getString("ProjectCode");
                    bundle.putString("ProjectCode", projectCode);
                }


                Log.d("userdata", user.toString());
            } else {
                user.setIsLoginSuccess(false);
            }


        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(LOG_TAG, "[json解析出错]" + e);
        }
        bundle.putSerializable("user", user);
        return bundle;
    }

    public static Bundle cpCustomersParser(String string) {
        Bundle bundle = new Bundle();
        String count = "0";
        ArrayList<CpCustomer> cpCustomers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray reciveComeList = data.getJSONArray("ReciveComeList");
                for (int i = 0; i < reciveComeList.length(); i++) {
                    JSONObject jsonObject1 = reciveComeList.getJSONObject(i);
                    CpCustomer cpCustomer = new CpCustomer();
                    if (!jsonObject1.isNull("ContactLogID")) {
                        cpCustomer.setReciveComeId(jsonObject1.getString("ContactLogID"));
                    } else {
                        cpCustomer.setReciveComeId("");
                    }

                    if (!jsonObject1.isNull("SuppleState")) {
                        cpCustomer.setIsSuppleState(jsonObject1.getString("SuppleState"));
                    } else {
                        cpCustomer.setIsSuppleState("");
                    }

                    if (!jsonObject1.isNull("ComeBeginTime")) {
                        cpCustomer.setComeTime(jsonObject1.getString("ComeBeginTime"));
                    } else {
                        cpCustomer.setComeTime("");
                    }

                    if (!jsonObject1.isNull("ComeEndTime")) {
                        cpCustomer.setLeaveTime(jsonObject1.getString("ComeEndTime"));
                    } else {
                        cpCustomer.setLeaveTime("");
                    }

                    if (!jsonObject1.isNull("IsOtherCome")) {
                        cpCustomer.setIsOtherCome(jsonObject1.getString("IsOtherCome"));
                    } else {
                        cpCustomer.setIsOtherCome("");
                    }

                    if (!jsonObject1.isNull("ContactEmpID")) {
                        cpCustomer.setCusOwnerID(jsonObject1.getString("ContactEmpID"));
                    } else {
                        cpCustomer.setCusOwnerID("");
                    }

                    if (!jsonObject1.isNull("BuyCarUsage")) {
                        cpCustomer.setBuyCarUsage(jsonObject1.getString("BuyCarUsage"));
                    } else {
                        cpCustomer.setBuyCarUsage("");
                    }

                    if (!jsonObject1.isNull("ReciveComeEmpName")) {
                        cpCustomer.setReciveComeEmpName(jsonObject1.getString("ReciveComeEmpName"));
                    } else {
                        cpCustomer.setReciveComeEmpName("");
                    }

                    if (!jsonObject1.isNull("PayType")) {
                        cpCustomer.setPaytype(jsonObject1.getString("PayType"));
                    } else {
                        cpCustomer.setPaytype("");
                    }

                    if (!jsonObject1.isNull("FocusCarModelID")) {
                        cpCustomer.setFocusCarmodelID(jsonObject1.getString("FocusCarModelID"));
                    } else {
                        cpCustomer.setFocusCarmodelID("");
                    }

                    if (!jsonObject1.isNull("CusName")) {
                        cpCustomer.setCustomerName(jsonObject1.getString("CusName"));
                    } else {
                        cpCustomer.setCustomerName("");
                    }

                    if (!jsonObject1.isNull("CusPhone")) {
                        cpCustomer.setCustomerPhone(jsonObject1.getString("CusPhone"));
                    } else {
                        cpCustomer.setCustomerPhone("");
                    }

                    if (!jsonObject1.isNull("SparePhone")) {
                        cpCustomer.setCustomerSparePhone(jsonObject1.getString("SparePhone"));
                    } else {
                        cpCustomer.setCustomerSparePhone("");
                    }

                    if (!jsonObject1.isNull("WeChatNum")) {
                        cpCustomer.setWeChatNum(jsonObject1.getString("WeChatNum"));
                    } else {
                        cpCustomer.setWeChatNum("");
                    }

                    if (!jsonObject1.isNull("CusSex")) {
                        cpCustomer.setCusSex(jsonObject1.getString("CusSex"));
                    } else {
                        cpCustomer.setCusSex("");
                    }

                    if (!jsonObject1.isNull("CarBrandName")) {
                        cpCustomer.setCarBrandName(jsonObject1.getString("CarBrandName"));
                    } else {
                        cpCustomer.setCarBrandName("");
                    }

                    if (!jsonObject1.isNull("CarSeriesName")) {
                        cpCustomer.setCarseriesName(jsonObject1.getString("CarSeriesName"));
                    } else {
                        cpCustomer.setCarseriesName("");
                    }

                    if (!jsonObject1.isNull("CarModelName")) {
                        cpCustomer.setCarModelName(jsonObject1.getString("CarModelName"));
                    } else {
                        cpCustomer.setCarModelName("");
                    }

                    if (!jsonObject1.isNull("CustomerLevel")) {
                        cpCustomer.setIntentLevel(jsonObject1.getString("CustomerLevel"));
                    } else {
                        cpCustomer.setIntentLevel("");
                    }

                    if (!jsonObject1.isNull("NextCallTime")) {
                        cpCustomer.setNextCallTime(jsonObject1.getString("NextCallTime"));
                    } else {
                        cpCustomer.setNextCallTime("");
                    }

                    if (!jsonObject1.isNull("IsDue")) {
                        cpCustomer.setIsDue(jsonObject1.getString("IsDue"));
                    } else {
                        cpCustomer.setIsDue("");
                    }

                    if (!jsonObject1.isNull("TalkProcess")) {
                        cpCustomer.setTalkProcess(jsonObject1.getString("TalkProcess"));
                    } else {
                        cpCustomer.setTalkProcess("");
                    }

                    if (!jsonObject1.isNull("Remark")) {
                        cpCustomer.setRemark(jsonObject1.getString("Remark"));
                    } else {
                        cpCustomer.setRemark("");
                    }


                    if (!jsonObject1.isNull("CusLiveProvince")) {
                        cpCustomer.setLiveProvince(jsonObject1.getString("CusLiveProvince"));
                    } else {
                        cpCustomer.setLiveProvince("");
                    }
                    if (!jsonObject1.isNull("CusLiveCity")) {
                        cpCustomer.setLiveCity(jsonObject1.getString("CusLiveCity"));
                    } else {
                        cpCustomer.setLiveCity("");
                    }
                    if (!jsonObject1.isNull("CusLiveArea")) {
                        cpCustomer.setLiveArea(jsonObject1.getString("CusLiveArea"));
                    } else {
                        cpCustomer.setLiveArea("");
                    }
                    if (!jsonObject1.isNull("CusLiveAddress")) {
                        cpCustomer.setAddressInfo(jsonObject1.getString("CusLiveAddress"));
                    } else {
                        cpCustomer.setAddressInfo("");
                    }

                    if (!jsonObject1.isNull("EmpName")) {
                        cpCustomer.setEmpName(jsonObject1.getString("EmpName"));
                    } else {
                        cpCustomer.setEmpName("");
                    }

                    if (!jsonObject1.isNull("FollowPeople")) {
                        cpCustomer.setFollowPeo(jsonObject1.getString("FollowPeople"));
                    } else {
                        cpCustomer.setFollowPeo("");
                    }

                    cpCustomers.add(cpCustomer);
                }
                count = data.getString("totalrows");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            bundle.putString("totalrows", count);
            bundle.putSerializable("list", (Serializable) cpCustomers);
        }

        return bundle;
    }


    public static CpCustomer cpPhoneCustomersParser(String string) {

        CpCustomer cpCustomer = null;

        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {

                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray reciveComeList = data.getJSONArray("IsSuReciveComeList");

                if (reciveComeList != null && reciveComeList.length() > 0) {
                    JSONObject jsonObject1 = reciveComeList.getJSONObject(0);
                    cpCustomer = new CpCustomer();
                    if (!jsonObject1.isNull("CustomerStateStr")) {
                        cpCustomer.setCustomerStateStr(jsonObject1.getString("CustomerStateStr"));
                    } else {
                        cpCustomer.setCustomerStateStr("");
                    }

                    if (!jsonObject1.isNull("CusOwnerName")) {
                        cpCustomer.setCusOwnerName(jsonObject1.getString("CusOwnerName"));
                    } else {
                        cpCustomer.setCusOwnerName("");
                    }

                    if (!jsonObject1.isNull("CusName")) {
                        cpCustomer.setCustomerName(jsonObject1.getString("CusName"));
                    } else {
                        cpCustomer.setCustomerName("");
                    }

                    if (!jsonObject1.isNull("CusPhone")) {
                        cpCustomer.setCustomerPhone(jsonObject1.getString("CusPhone"));
                    } else {
                        cpCustomer.setCustomerPhone("");
                    }
                    if (!jsonObject1.isNull("SparePhone")) {
                        cpCustomer.setCustomerSparePhone(jsonObject1.getString("SparePhone"));
                    } else {
                        cpCustomer.setCustomerSparePhone("");
                    }
                    if (!jsonObject1.isNull("CarBrandID")) {
                        cpCustomer.setCarBrandName(jsonObject1.getString("CarBrandID"));
                    } else {
                        cpCustomer.setCarBrandName("");
                    }

                    if (!jsonObject1.isNull("CarSeriesID")) {
                        cpCustomer.setCarseriesName(jsonObject1.getString("CarSeriesID"));
                    } else {
                        cpCustomer.setCarseriesName("");
                    }

                    if (!jsonObject1.isNull("CarModelID")) {
                        cpCustomer.setCarModelName(jsonObject1.getString("CarModelID"));
                    } else {
                        cpCustomer.setCarModelName("");
                    }


                    if (!jsonObject1.isNull("ComeChannelID")) {
                        cpCustomer.setChannelId(jsonObject1.getString("ComeChannelID"));
                    } else {
                        cpCustomer.setChannelId("");
                    }
                    if (!jsonObject1.isNull("SourceChannelID")) {
                        cpCustomer.setSourceChannelId(jsonObject1.getString("SourceChannelID"));
                    } else {
                        cpCustomer.setSourceChannelId("");
                    }

                    if (!jsonObject1.isNull("CusSex")) {
                        cpCustomer.setCusSex(jsonObject1.getString("CusSex"));
                    } else {
                        cpCustomer.setCusSex("");
                    }
                    if (!jsonObject1.isNull("CusOwnerID")) {
                        cpCustomer.setCusOwnerID(jsonObject1.getString("CusOwnerID"));
                    } else {
                        cpCustomer.setCusOwnerID("");
                    }
                    if (!jsonObject1.isNull("CarColorID")) {
                        cpCustomer.setCarColorID(jsonObject1.getString("CarColorID"));
                    } else {
                        cpCustomer.setCusOwnerID("");
                    }
                    if (!jsonObject1.isNull("CustomerLevelID")) {
                        cpCustomer.setIntentLevel(jsonObject1.getString("CustomerLevelID"));
                    } else {
                        cpCustomer.setIntentLevel("");
                    }


                    if (!jsonObject1.isNull("WishBuyDate")) {
                        cpCustomer.setWishBuyDate(jsonObject1.getString("WishBuyDate"));
                    } else {
                        cpCustomer.setWishBuyDate("");
                    }


                    if (!jsonObject1.isNull("NextCallTime")) {
                        cpCustomer.setNextCallTime(jsonObject1.getString("NextCallTime"));
                    } else {
                        cpCustomer.setNextCallTime("");
                    }
                    if (!jsonObject1.isNull("EmpName")) {
                        cpCustomer.setEmpName(jsonObject1.getString("EmpName"));
                    } else {
                        cpCustomer.setEmpName("");
                    }
                    if (!jsonObject1.isNull("IsDrive")) {
                        cpCustomer.setIsDrive(jsonObject1.getString("IsDrive"));
                    } else {
                        cpCustomer.setIsDrive("");
                    }
                    if (!jsonObject1.isNull("QuoteSituation")) {
                        cpCustomer.setQuoteSituation(jsonObject1.getString("QuoteSituation"));
                    } else {
                        cpCustomer.setQuoteSituation("");
                    }
                    if (!jsonObject1.isNull("WeChatNum")) {
                        cpCustomer.setWeChatNum(jsonObject1.getString("WeChatNum"));
                    } else {
                        cpCustomer.setWeChatNum("");
                    }
                    if (!jsonObject1.isNull("Remark")) {
                        cpCustomer.setRemark(jsonObject1.getString("Remark"));
                    } else {
                        cpCustomer.setRemark("");
                    }
                    if (!jsonObject1.isNull("CusLiveProvince")) {
                        cpCustomer.setLiveProvince(jsonObject1.getString("CusLiveProvince"));
                    } else {
                        cpCustomer.setLiveProvince("");
                    }
                    if (!jsonObject1.isNull("CusLiveCity")) {
                        cpCustomer.setLiveCity(jsonObject1.getString("CusLiveCity"));
                    } else {
                        cpCustomer.setLiveCity("");
                    }
                    if (!jsonObject1.isNull("CusLiveArea")) {
                        cpCustomer.setLiveArea(jsonObject1.getString("CusLiveArea"));
                    } else {
                        cpCustomer.setLiveArea("");
                    }
                    if (!jsonObject1.isNull("CusLiveAddress")) {
                        cpCustomer.setAddressInfo(jsonObject1.getString("CusLiveAddress"));
                    } else {
                        cpCustomer.setAddressInfo("");
                    }
                    if (!jsonObject1.isNull("IsDisable")) {
                        cpCustomer.setIsDisable(jsonObject1.getString("IsDisable"));
                    } else {
                        cpCustomer.setIsDisable("");
                    }

                }


//
//                if(!jsonObject1.isNull("ContactLogID")){
//                    cpCustomer.setReciveComeId(jsonObject1.getString("ContactLogID"));
//                }else {
//                    cpCustomer.setReciveComeId("");
//                }
//
//                if(!jsonObject1.isNull("SuppleState")){
//                    cpCustomer.setIsSuppleState(jsonObject1.getString("SuppleState"));
//                }else {
//                    cpCustomer.setIsSuppleState("");
//                }
//
//                if(!jsonObject1.isNull("ComeBeginTime")){
//                    cpCustomer.setComeTime(jsonObject1.getString("ComeBeginTime"));
//                }else {
//                    cpCustomer.setComeTime("");
//                }
//
//
//                if(!jsonObject1.isNull("ComeEndTime")){
//                    cpCustomer.setLeaveTime(jsonObject1.getString("ComeEndTime"));
//                }else {
//                    cpCustomer.setLeaveTime("");
//                }
//
//                if(!jsonObject1.isNull("IsOtherCome")){
//                    cpCustomer.setIsOtherCome(jsonObject1.getString("IsOtherCome"));
//                }else {
//                    cpCustomer.setIsOtherCome("");
//                }
//
//
//
//                if(!jsonObject1.isNull("BuyCarUsage")){
//                    cpCustomer.setBuyCarUsage(jsonObject1.getString("BuyCarUsage"));
//                }else {
//                    cpCustomer.setBuyCarUsage("");
//                }
//
//                if(!jsonObject1.isNull("ReciveComeEmpName")){
//                    cpCustomer.setReciveComeEmpName(jsonObject1.getString("ReciveComeEmpName"));
//                }else {
//                    cpCustomer.setReciveComeEmpName("");
//                }
//
//                if(!jsonObject1.isNull("PayType")){
//                    cpCustomer.setPaytype(jsonObject1.getString("PayType"));
//                }else {
//                    cpCustomer.setPaytype("");
//                }
//
//                if(!jsonObject1.isNull("FocusCarModelID")){
//                    cpCustomer.setFocusCarmodelID(jsonObject1.getString("FocusCarModelID"));
//                }else {
//                    cpCustomer.setFocusCarmodelID("");
//                }
//
//
//                if(!jsonObject1.isNull("IsDue")){
//                    cpCustomer.setIsDue(jsonObject1.getString("IsDue"));
//                }else {
//                    cpCustomer.setIsDue("");
//                }
//
//                if(!jsonObject1.isNull("TalkProcess")){
//                    cpCustomer.setTalkProcess(jsonObject1.getString("TalkProcess"));
//                }else {
//                    cpCustomer.setTalkProcess("");
//                }
//
//
//
//
//
//
//
//                if(!jsonObject1.isNull("FollowPeople")){
//                    cpCustomer.setFollowPeo(jsonObject1.getString("FollowPeople"));
//                }else {
//                    cpCustomer.setFollowPeo("");
//                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cpCustomer;
    }


    public static Bundle fpCustomersParser(String string) {
        Bundle bundle = new Bundle();
        ArrayList<FirstProtect> fpCustomers = new ArrayList<FirstProtect>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray fpComeList = data.getJSONArray("ShouInviteCallList");
                for (int i = 0; i < fpComeList.length(); i++) {
                    JSONObject jsonObject1 = fpComeList.getJSONObject(i);
                    FirstProtect fpCustomer = new FirstProtect();
                    if (!jsonObject1.isNull("CarOwnerName")) {
                        fpCustomer.setCarOwnerName(jsonObject1.getString("CarOwnerName"));
                    }
                    if (!jsonObject1.isNull("CarOwnerPhone")) {
                        fpCustomer.setCarOwnerPhone(jsonObject1.getString("CarOwnerPhone"));
                    }
                    if (!jsonObject1.isNull("FrameNum")) {
                        fpCustomer.setFrameNum(jsonObject1.getString("FrameNum"));
                    }
                    if (!jsonObject1.isNull("CarNO")) {
                        fpCustomer.setCarNO(jsonObject1.getString("CarNO"));
                    }
                    if (!jsonObject1.isNull("CarSalesTime")) {
                        fpCustomer.setCarSalesTime(jsonObject1.getString("CarSalesTime"));
                    }
                    if (!jsonObject1.isNull("EnterMileage")) {
                        fpCustomer.setEnterMileage(jsonObject1.getString("EnterMileage"));
                    }
                    if (!jsonObject1.isNull("PredictDate")) {
                        fpCustomer.setPredictDate(jsonObject1.getString("PredictDate"));
                    }
                    if (!jsonObject1.isNull("PredictFitDate")) {
                        fpCustomer.setPredictFitDate(jsonObject1.getString("PredictFitDate"));
                    }

                    if (!jsonObject1.isNull("LatelyMaintainDate")) {
                        fpCustomer.setLatelyMaintainDate(jsonObject1.getString("LatelyMaintainDate"));
                    }
                    if (!jsonObject1.isNull("InviteCallDate")) {
                        fpCustomer.setInviteCallDate(jsonObject1.getString("InviteCallDate"));
                    }
                    if (!jsonObject1.isNull("ShouldInviteCallDate")) {
                        fpCustomer.setShouldInviteCallDate(jsonObject1.getString("ShouldInviteCallDate"));
                    }
                    if (!jsonObject1.isNull("TalkProcess")) {
                        fpCustomer.setTalkProcess(jsonObject1.getString("TalkProcess"));
                    }
                    fpCustomers.add(fpCustomer);
                }
                bundle.putString("totalrows", data.getString("totalrows"));
            }
            bundle.putSerializable("list", (Serializable) fpCustomers);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bundle;
    }


    public static Bundle tpCustomersParser(String string) {
        Bundle bundle = new Bundle();
        ArrayList<TimingProtect> tpCustomers = new ArrayList<TimingProtect>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray tpComeList = data.getJSONArray("DingInviteCallList");
                for (int i = 0; i < tpComeList.length(); i++) {
                    JSONObject jsonObject1 = tpComeList.getJSONObject(i);
                    TimingProtect tpCustomer = new TimingProtect();
                    if (!jsonObject1.isNull("CustomerName")) {
                        tpCustomer.setCustomerName(jsonObject1.getString("CustomerName"));
                    }
                    if (!jsonObject1.isNull("CustomerPhone")) {
                        tpCustomer.setCustomerPhone(jsonObject1.getString("CustomerPhone"));
                    }
                    if (!jsonObject1.isNull("FrameNum")) {
                        tpCustomer.setFrameNum(jsonObject1.getString("FrameNum"));
                    }
                    if (!jsonObject1.isNull("CarNO")) {
                        tpCustomer.setCarNO(jsonObject1.getString("CarNO"));
                    }
                    if (!jsonObject1.isNull("LatelyMaintainTime")) {
                        tpCustomer.setLatelyMaintainTime(jsonObject1.getString("LatelyMaintainTime"));
                    }
                    if (!jsonObject1.isNull("LatelyMileage")) {
                        tpCustomer.setLatelyMileage(jsonObject1.getString("LatelyMileage"));
                    }
                    if (!jsonObject1.isNull("LatelyInviteCallDate")) {
                        tpCustomer.setLatelyInviteCallDate(jsonObject1.getString("LatelyInviteCallDate"));
                    }
                    if (!jsonObject1.isNull("PredictFitDate")) {
                        tpCustomer.setPredictFitDate(jsonObject1.getString("PredictFitDate"));
                    }

                    if (!jsonObject1.isNull("LatelyMaintainDate")) {
                        tpCustomer.setLatelyMaintainDate(jsonObject1.getString("LatelyMaintainDate"));
                    }

                    if (!jsonObject1.isNull("PredictDate")) {
                        tpCustomer.setPredictDate(jsonObject1.getString("PredictDate"));
                    }
                    if (!jsonObject1.isNull("ShouldInviteCallDate")) {
                        tpCustomer.setShouldInviteCallDate(jsonObject1.getString("ShouldInviteCallDate"));
                    }
                    if (!jsonObject1.isNull("LatelyTalkProcess")) {
                        tpCustomer.setLatelyTalkProcess(jsonObject1.getString("LatelyTalkProcess"));
                    }
                    tpCustomers.add(tpCustomer);
                }
                bundle.putString("totalrows", data.getString("totalrows"));
            }
            bundle.putSerializable("list", (Serializable) tpCustomers);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bundle;
    }


    public static List<CpCustomer> cpDeafultCustomersParser(String string) {
        List<CpCustomer> cpCustomers = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray deafultList = data.getJSONArray("IsSuReciveComeList");
                for (int i = 0; i < deafultList.length(); i++) {
                    JSONObject jsonObject1 = deafultList.getJSONObject(i);
                    CpCustomer cpCustomer = new CpCustomer();

                    if (!jsonObject1.isNull("CustomerName")) {
                        cpCustomer.setCustomerName(jsonObject1.getString("CustomerName"));
                    } else {
                        cpCustomer.setCustomerName("");
                    }

                    if (!jsonObject1.isNull("CustomerPhone")) {
                        cpCustomer.setCustomerPhone(jsonObject1.getString("CustomerPhone"));
                    } else {
                        cpCustomer.setCustomerPhone("");
                    }

                    if (!jsonObject1.isNull("LiveProvince")) {
                        cpCustomer.setLiveProvince(jsonObject1.getString("LiveProvince"));
                    } else {
                        cpCustomer.setLiveProvince("");
                    }

                    if (!jsonObject1.isNull("LiveCity")) {
                        cpCustomer.setLiveCity(jsonObject1.getString("LiveCity"));
                    } else {
                        cpCustomer.setLiveCity("");
                    }

                    if (!jsonObject1.isNull("LiveArea")) {
                        cpCustomer.setLiveArea(jsonObject1.getString("LiveArea"));
                    } else {
                        cpCustomer.setLiveArea("");
                    }

                    if (!jsonObject1.isNull("Introducer")) {
                        cpCustomer.setOldCusCarNO(jsonObject1.getString("Introducer"));
                    } else {
                        cpCustomer.setOldCusCarNO("");
                    }

                    if (!jsonObject1.isNull("ChannelId")) {
                        cpCustomer.setChannelId(jsonObject1.getString("ChannelId"));
                    } else {
                        cpCustomer.setChannelId("");
                    }

                    if (!jsonObject1.isNull("SourceChannelID")) {
                        cpCustomer.setSourceChannelId(jsonObject1.getString("SourceChannelID"));
                    } else {
                        cpCustomer.setSourceChannelId("");
                    }

                    if (!jsonObject1.isNull("IntentLevel")) {
                        cpCustomer.setIntentLevelID(jsonObject1.getString("IntentLevel"));
                    } else {
                        cpCustomer.setIntentLevelID("");
                    }

                    if (!jsonObject1.isNull("UseageID")) {
                        cpCustomer.setUseageID(jsonObject1.getString("UseageID"));
                    } else {
                        cpCustomer.setUseageID("");
                    }

                    if (!jsonObject1.isNull("IsChange")) {
                        cpCustomer.setIsChangeID(jsonObject1.getString("IsChange"));
                    } else {
                        cpCustomer.setIsChangeID("");
                    }

                    if (!jsonObject1.isNull("CarColorID")) {
                        cpCustomer.setCarColorID(jsonObject1.getString("CarColorID"));
                    } else {
                        cpCustomer.setCarColorID("");
                    }

                    if (!jsonObject1.isNull("PayType")) {
                        cpCustomer.setPaytype(jsonObject1.getString("PayType"));
                    } else {
                        cpCustomer.setPaytype("");
                    }

                    if (!jsonObject1.isNull("FollowPeo")) {
                        cpCustomer.setFollowPeo(jsonObject1.getString("FollowPeo"));
                    } else {
                        cpCustomer.setFollowPeo("");
                    }

                    if (!jsonObject1.isNull("CarSeriesID")) {
                        cpCustomer.setCarSeriesID(jsonObject1.getString("CarSeriesID"));
                    } else {
                        cpCustomer.setCarSeriesID("");
                    }

                    if (!jsonObject1.isNull("CarModelID")) {
                        cpCustomer.setCarModelID(jsonObject1.getString("CarModelID"));
                    } else {
                        cpCustomer.setCarModelID("");
                    }

                    if (!jsonObject1.isNull("FocusCarmodelID")) {
                        cpCustomer.setFocusCarmodelID(jsonObject1.getString("FocusCarmodelID"));
                    } else {
                        cpCustomer.setFocusCarmodelID("");
                    }

                    cpCustomers.add(cpCustomer);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cpCustomers;
    }


    public static List<MaintenanceHistory> fpCustomersHistoryMachineParser(String string) {
        List<MaintenanceHistory> maintenanceHistoryArrayList = new ArrayList<MaintenanceHistory>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                JSONArray aftInsDeailList = data.getJSONArray("AftInsDeailList");
                for (int i = 0; i < aftInsDeailList.length(); i++) {
                    JSONObject jsonObject1 = aftInsDeailList.getJSONObject(i);
                    MaintenanceHistory maintenanceHistory = new MaintenanceHistory();
                    if (!jsonObject1.isNull("ReciveComeDate")) {
                        maintenanceHistory.setReciveComeDate(jsonObject1.getString("ReciveComeDate"));
                    }
                    if (!jsonObject1.isNull("ServiceEmpName")) {
                        maintenanceHistory.setServiceEmpName(jsonObject1.getString("ServiceEmpName"));
                    }
                    if (!jsonObject1.isNull("ReciveTypeName")) {
                        maintenanceHistory.setReciveTypeName(jsonObject1.getString("ReciveTypeName"));
                    }
                    if (!jsonObject1.isNull("ReciveComeID")) {
                        maintenanceHistory.setReciveComeID(jsonObject1.getString("ReciveComeID"));
                    }
                    maintenanceHistoryArrayList.add(maintenanceHistory);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return maintenanceHistoryArrayList;
    }


    public static SaveStatu saveCustomersParser(String string) {
        SaveStatu saveStatu = new SaveStatu();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            saveStatu.setMessage(message);
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                String reciveComeID = data.getString("ContactLogID");
                saveStatu.setReciveComeID(reciveComeID);
                saveStatu.setIsSaveDate(true);
            } else {
                saveStatu.setIsSaveDate(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return saveStatu;
    }


    public static boolean savePhoneModelProblem(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                return true;
            } else {
                return false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static Bundle versionCodeParser(String string) {
        Bundle bundle = new Bundle();
        try {
            JSONObject jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            String message = jsonObject.getString("message");
            bundle.putString("message", message);
            if (SUCCESS.equals(status)) {
                String data_string = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_string);
                String APPVersion = data.getString("APPVersion");
                bundle.putString("APPVersion", APPVersion);
                String APPDownLoadPath = data.getString("APPDownLoadPath");
                bundle.putString("APPDownLoadPath", APPDownLoadPath);
                bundle.putBoolean("isSucces", true);
            } else {
                bundle.putBoolean("isSucces", false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public static List<UserPtInfoModel> updataUItotal(String string) {
        JSONObject jsonObject = null;
        List<UserPtInfoModel> userPtInfoModels_UserNum = new ArrayList<>();
        try {
            jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_str = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_str);
                JSONObject dataList = data.getJSONObject("SelectDataList");
                if (!dataList.isNull("UserNum")) {
                    JSONArray UserNumList = dataList.getJSONArray("UserNum");
                    if (null != UserNumList && UserNumList.length() > 0) {
                        for (int i = 0; i < UserNumList.length(); i++) {
                            UserPtInfoModel userPtInfoModel = new UserPtInfoModel();
                            JSONObject jsonObject_i = UserNumList.getJSONObject(i);
                            userPtInfoModel.setID(jsonObject_i.getString("ID"));
                            userPtInfoModel.setPID(jsonObject_i.getString("PID"));
                            userPtInfoModel.setName(jsonObject_i.getString("Name"));
                            userPtInfoModel.setType("UserNum");
                            userPtInfoModels_UserNum.add(userPtInfoModel);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userPtInfoModels_UserNum;
    }


    public static List<String> loadPro_City_Country(String string, int type) {
        JSONObject jsonObject = null;
        List<String> stringList = new ArrayList<>();
        try {
            jsonObject = new JSONObject(string);
            String status = jsonObject.getString("status");
            if (SUCCESS.equals(status)) {
                String data_str = jsonObject.getString("data");
                JSONObject data = new JSONObject(data_str);
                JSONArray list = data.getJSONArray("BaseProvinceList");
                switch (type) {
                    case 1:
                        if (null != list && list.length() > 0) {
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObject_i = list.getJSONObject(i);
                                stringList.add(jsonObject_i.getString("Province"));
                            }
                        }

                        break;
                    case 2:
                        if (null != list && list.length() > 0) {
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObject_i = list.getJSONObject(i);
                                stringList.add(jsonObject_i.getString("City"));
                            }
                        }
                        break;
                    case 3:
                        if (null != list && list.length() > 0) {
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObject_i = list.getJSONObject(i);
                                stringList.add(jsonObject_i.getString("Area"));
                            }
                        }
                        break;

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return stringList;
    }


}
