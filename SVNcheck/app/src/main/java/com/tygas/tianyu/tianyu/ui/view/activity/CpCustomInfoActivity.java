package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.SharedPreferencesDate;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.CpCustomFocusAdapter;
import com.tygas.tianyu.tianyu.ui.model.CityModel;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.DistrictModel;
import com.tygas.tianyu.tianyu.ui.model.ProvinceModel;
import com.tygas.tianyu.tianyu.ui.model.SaveStatu;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XmlParser;
import com.tygas.tianyu.tianyu.utils.XmlParserHandler;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CpCustomInfoActivity extends Activity implements DatePickerDialog.OnDateSetListener {


    private RelativeLayout rl_references_carnumber;
    private LinearLayout ll_info_channel;

    private ScrollView scrollView;
    private EditText et_talkrecord;

    private EditText ed_name;
    private EditText ed_phone;
    private EditText ed_phone_sq;
    private EditText ed_weChat;
    private EditText ed_address_info;
    private EditText ed_remark;

    private TextView tv_sex;
    private TextView tv_province;
    private TextView tv_city;
    private TextView tv_area;
    private TextView tv_info_channel;
    private TextView tv_isdrive;
    private TextView tv_followeperson;
    private TextView tv_intention_garde;
    private RelativeLayout rl_intention_garde;
    private TextView tv_intention_chexi;
    private TextView tv_buyuse;
    private TextView tv_shop_channel;
    private TextView tv_customer_haver;

    private TextView tv_intent_pingpai;
    private TextView tv_date_nigou;
    private TextView tv_date_fangbian;
    private TextView tv_date_yujihuifang;

    private TextView tv_intention_chexin;
    private TextView tv_ispaytype;
    private EditText ed_QuoteSituation;


    private GridView gv_focus;
    private CpCustomFocusAdapter cpCustomFocusAdapter;
    private User user;

    private String references_carnumbe;
    private String info_channel;
    private CpCustomer cpData;

    private boolean isHavePhoneNumber;
    private String shop_channelId_wangluo = "0";
    private String shop_channelId_jiesaoren = "0";
    private boolean isLoading = false;

    private DatePickerDialog dpd;

    private boolean isOnclick = true;

    private RelativeLayout rl_name;
    private RelativeLayout rl_sex;
    private RelativeLayout rl_phone;
    private RelativeLayout rl_phone_sq;
    private RelativeLayout rl_wechat;
    private RelativeLayout rl_proince;
    private RelativeLayout rl_city;
    private RelativeLayout rl_country;
    private RelativeLayout rl_address;
    private RelativeLayout rl_shop_channl;
    private RelativeLayout rl_info_channel;
    private RelativeLayout rl_nigou;
    private RelativeLayout rl_fangbian;
    private RelativeLayout rl_qianke_hacer;
    private RelativeLayout rl_baojia;
    private RelativeLayout rl_remark;

    private boolean isSaveYanZhengHavePhone;
    private String  initContactEmpID;
    private String  initContactEmpName;

    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    private String province;
    private String city;
    private String district;


    private List<String> list_Provonce = new ArrayList<String>();
    private List<String> list_City = new ArrayList<String>();
    private List<String> list_District = new ArrayList<String>();


    private List<UserPtInfoModel> list_brand = new ArrayList<UserPtInfoModel>();
    private Map<String, List<UserPtInfoModel>> map_chexi = new HashMap<String, List<UserPtInfoModel>>();

    private List<UserPtInfoModel> list_chexi = new ArrayList<UserPtInfoModel>();
    private Map<String, List<UserPtInfoModel>> map_chexin = new HashMap<String, List<UserPtInfoModel>>();
    private List<UserPtInfoModel> list_leavel = new ArrayList<>();


    private String brand;
    private String chexi;
    private List<UserPtInfoModel> list_focus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_cp_custom_info);
        user = ((MyAppCollection) getApplicationContext()).getUser();

        initProvineceData();
        initCarModelList();

        initView();
        initCustomerLeavelList();
        initData();
        initProvince("", "", 1);
//        downLoadDeafualtData();
    }

    private void initProvineceData() {
//        XmlParser xmlParser = new XmlParser();
//        xmlParser.initProvinceDatas(this);
//        mProvinceDatas = xmlParser.getmProvinceDatas();
//        mCitisDatasMap = xmlParser.getmCitisDatasMap();
//        mDistrictDatasMap = xmlParser.getmDistrictDatasMap();
    }

    private void initCarModelList() {
        list_brand = user.getList_IntentBrand();
        if (null != list_brand && list_brand.size() > 0) {
            List<UserPtInfoModel> list_carSer = user.getList_CarSeries();
            if (null != list_carSer && list_carSer.size() > 0) {
                for (UserPtInfoModel brand : list_brand) {
                    String id = brand.getID();
                    ArrayList<UserPtInfoModel> userPtInfoModels = new ArrayList<>();
                    for (UserPtInfoModel chexi : list_carSer) {
                        if (id.equals(chexi.getPID())) {
                            userPtInfoModels.add(chexi);
                        }
                    }
                    map_chexi.put(id, userPtInfoModels);
                }
            }
        }
        Log.d("map_chexi", map_chexi.toString());


        list_chexi = user.getList_CarSeries();
        if (null != list_chexi && list_chexi.size() > 0) {
            List<UserPtInfoModel> list_carModels = user.getList_CarModels();
            if (null != list_carModels && list_carModels.size() > 0) {
                for (UserPtInfoModel chexi : list_chexi) {
                    String id = chexi.getID();
                    ArrayList<UserPtInfoModel> userPtInfoModels = new ArrayList<>();
                    for (UserPtInfoModel chexin : list_carModels) {
                        if (id.equals(chexin.getPID())) {
                            userPtInfoModels.add(chexin);
                        }
                    }
                    map_chexin.put(id, userPtInfoModels);
                }
            }
        }
        Log.d("map_chexin", map_chexin.toString());

    }

    private UserPtInfoModel levelModel;

    private void initCustomerLeavelList() {
        list_leavel = user.getList_LevelNotDefeat();
        if (list_leavel != null && list_leavel.size() > 0 && cpData != null) {
            for (UserPtInfoModel userPtInfoModel : list_leavel) {
                if (cpData.getIntentLevelID() != null && cpData.getIntentLevelID().equals(userPtInfoModel.getName())) {
                    levelModel = userPtInfoModel;
                }
            }
        }
        Calendar now = Calendar.getInstance();
        String nowStr = null;
        if (levelModel != null && levelModel.getPID() != null) {
            int i = Integer.parseInt(levelModel.getPID());
            now.add(Calendar.DAY_OF_YEAR, i);
            nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
            tv_intention_garde.setText(levelModel.getName());
        } else {
            nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        }
//        tv_date_yujihuifang.setText(nowStr);

    }

    private void initView() {
        ed_name = (EditText) findViewById(R.id.activity_cpinfo_ed_name);
        ed_phone = (EditText) findViewById(R.id.activity_cpinfo_et_phone);
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                checkPhone(s.toString());
                Log.d("chek", "chek___________");
            }


        });
        ed_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && isHavePhoneNumber) {
                    checkPhone(((EditText) v).getText().toString());
                }
                if (!hasFocus) {
                    if(isHavePhoneNumber){
                        if (TextUtils.isEmpty(((EditText) v).getText().toString())) {
                            isSaveYanZhengHavePhone=false;
                            tv_shop_channel.setHint("填写");
                            tv_shop_channel.setHintTextColor(Color.parseColor("#aaaaaa"));
                            tv_intention_garde.setHint("填写");
                            tv_intention_garde.setHintTextColor(Color.parseColor("#aaaaaa"));
                        } else {
                            isSaveYanZhengHavePhone=true;
                            tv_shop_channel.setHint("必填");
                            tv_shop_channel.setHintTextColor(getResources().getColor(R.color.appOrange));
                            tv_intention_garde.setHint("必填");
                            tv_intention_garde.setHintTextColor(getResources().getColor(R.color.appOrange));
                        }
                    }

                }
            }
        });


        ed_phone_sq = (EditText) findViewById(R.id.activity_cpinfo_et_phone_sq);
        ed_weChat = (EditText) findViewById(R.id.activity_cpinfo_et_wechat);
        ed_address_info = (EditText) findViewById(R.id.activity_cpinfo_et_infoaddress);
        ed_remark = (EditText) findViewById(R.id.activity_cpinfo_et_remark);
        //

        tv_sex = (TextView) findViewById(R.id.activity_cpinfo_tv_sex);
        tv_intent_pingpai = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_pingpai);
        tv_date_nigou = (TextView) findViewById(R.id.activity_cpinfo_tv_date_nigou);
        tv_date_fangbian = (TextView) findViewById(R.id.activity_cpinfo_tv_date_jieting);
        tv_date_yujihuifang = (TextView) findViewById(R.id.activity_cpinfo_tv_date_yujihuifang);
        rl_intention_garde = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_intention_garde);


        tv_info_channel = (TextView) findViewById(R.id.activity_cpinfo_tv_infochannel);
        rl_info_channel = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_infochannel);
        ll_info_channel = (LinearLayout) findViewById(R.id.activity_cpinfo_ll_infochannel);
        tv_intention_chexin = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_chexin);

//        cb_focus_brand = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_brand);
//        cb_focus_price = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_price);
//        cb_focus_power = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_power);
//        cb_focus_safe = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_safe);
//        cb_focus_fuelconsumption = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_fuelconsumption);
//        cb_focus_appearance = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_appearance);
//        cb_focus_confortable = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_confortable);
//        cb_focus_configuration = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_configuration);
//        cb_focus_other = (CheckBox) findViewById(R.id.activity_cpinfo_cb_focus_other);
        et_talkrecord = (EditText) findViewById(R.id.activity_cpinfo_et_talkrecord);
        tv_isdrive = (TextView) findViewById(R.id.activity_cpinfo_tv_isdrive);
        tv_followeperson = (TextView) findViewById(R.id.activity_cpinfo_tv_followeperson);
        tv_intention_garde = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_garde);
        tv_intention_chexi = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_chexi);
        tv_buyuse = (TextView) findViewById(R.id.activity_cpinfo_tv_buyuse);
        tv_shop_channel = (TextView) findViewById(R.id.activity_cpinfo_tv_shopchannel);
        tv_province = (TextView) findViewById(R.id.activity_cpinfo_tv_province);
        tv_city = (TextView) findViewById(R.id.activity_cpinfo_tv_city);
        tv_area = (TextView) findViewById(R.id.activity_cpinfo_tv_area);
        gv_focus = (GridView) findViewById(R.id.activity_cpinfo_gv_focus);
        tv_ispaytype = (TextView) findViewById(R.id.activity_cpinfo_tv_paytype);
        ed_QuoteSituation = (EditText) findViewById(R.id.activity_cpinfo_tv_QuoteSituation);
        tv_customer_haver = (TextView) findViewById(R.id.activity_cpinfo_tv_customerhaver);


        rl_name = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_name);
        rl_sex = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_sex);
        rl_phone = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_phone);
        rl_phone_sq = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_phone_sq);
        rl_wechat = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_wechat);
        rl_proince = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_province);
        rl_city = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_city);
        rl_country = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_area);
        rl_address = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_infoaddress);
        rl_shop_channl = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_shopchannel);
        rl_info_channel = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_infochannel);
        rl_nigou = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_date_nigou);
        rl_fangbian = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_date_jieting);
        rl_qianke_hacer = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_customerhaver);
        rl_baojia = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_QuoteSituation);
        rl_remark = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_remark);


        list_focus = user.getList_Focus();
        cpCustomFocusAdapter = new CpCustomFocusAdapter(this, list_focus);

        gv_focus.setAdapter(cpCustomFocusAdapter);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("CpData");
//        cpData=new CpCustomer();
//        cpData.setReciveComeId();
        cpData = (CpCustomer) (bundle.getSerializable("CpData"));
        if (cpData != null) {
            initContactEmpID=cpData.getCusOwnerID();
            initContactEmpName=cpData.getCusOwnerName();

            cpData.setChannelId("0");//
            cpData.setSourceChannelId("0");
            cpData.setUseageID("0");//?
            cpData.setWishBuyDate("");
            cpData.setCusHandyTimeFrame("");

            cpData.setCarSeriesID("");
            cpData.setCarModelID("");
            cpData.setCarBrandID("");
            cpData.setCustomerStateStr("");

            Calendar now = Calendar.getInstance();
            dpd = DatePickerDialog.newInstance(
                    this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA

            Log.d("cpDate", cpData.toString());
            ed_name.setText(cpData.getCustomerName());
            tv_sex.setText(cpData.getCusSex());
            ed_phone.setText(cpData.getCustomerPhone());
            ed_phone_sq.setText(cpData.getCustomerSparePhone());
            ed_weChat.setText(cpData.getWeChatNum());
            ed_remark.setText(cpData.getRemark());
            if (!TextUtils.isEmpty(cpData.getLiveProvince())) {
                isdismissdialog = false;
                cpData.setLiveProvince(cpData.getLiveProvince());
                tv_province.setText(cpData.getLiveProvince());
                province = cpData.getLiveProvince();
                initProvince(province, "", 2);
                if (!TextUtils.isEmpty(cpData.getLiveCity())) {
                    cpData.setLiveCity(cpData.getLiveCity());
                    tv_city.setText(cpData.getLiveCity());
                    city = cpData.getLiveCity();
                    initProvince(province, city, 3);
                    if (!TextUtils.isEmpty(cpData.getLiveArea())) {
                        cpData.setLiveArea(cpData.getLiveArea());
                        tv_area.setText(cpData.getLiveArea());
                    }
                }
            }
            ed_address_info.setText(cpData.getAddressInfo());
            tv_followeperson.setText(cpData.getFollowPeo());


//            tv_intention_chexi.setText(cpData.getCarSeriesName());
//            if(!TextUtils.isEmpty(cpData.getCarSeriesName())){
//                if (user.getList_CarSeries() != null && user.getList_CarSeries().size() > 0) {
//                    for (UserPtInfoModel userPtInfoModel : user.getList_CarSeries()) {
//                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpData.getCarSeriesName())) {
//                            cpData.setCarSeriesID(userPtInfoModel.getID());
//                            chexi = userPtInfoModel.getID();
//                        }
//                    }
//                }else {
//                    cpData.setCarSeriesID("");
//                }
//            }else {
//                cpData.setCarSeriesID("");
//            }


//            if (map_chexin != null && map_chexin.get(cpData.getCarSeriesID()) != null && map_chexin.get(cpData.getCarSeriesID()).size() > 0) {
//                for (UserPtInfoModel userPtInfoModel : map_chexin.get(cpData.getCarSeriesID())) {
//                    if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpData.getCarModelID())) {
//                        cpData.setCarModelName(userPtInfoModel.getName());
//                        tv_intention_chexin.setText(userPtInfoModel.getName());
//                    }
//                }
//            }

            tv_buyuse.setText(cpData.getBuyCarUsage());
            if (user.getList_Useage() != null && user.getList_Useage().size() > 0) {
                for (UserPtInfoModel userPtInfoModel : user.getList_Useage()) {
                    if (userPtInfoModel.getName() != null && userPtInfoModel.getName().equals(cpData.getBuyCarUsage())) {
                        cpData.setUseageID(cpData.getUseageID());
                    }
                }
            }

//            if (user.getList_AllEmpID() != null && user.getList_AllEmpID().size() > 0) {
//                for (UserPtInfoModel userPtInfoModel : user.getList_AllEmpID()) {
//                    if ((userPtInfoModel.getId() + "").equals(cpData.getCusOwnerID())) {
//                        tv_customer_haver.setText(userPtInfoModel.getName());
//                    }
//                }
//            }


            String focusCarmodelID = cpData.getFocusCarmodelID();
            if (!TextUtils.isEmpty(focusCarmodelID) && !"null".equals(focusCarmodelID) && focusCarmodelID.length() % 2 == 1) {
                for (int i = 0; i < focusCarmodelID.length() / 2 + 1; i++) {
                    String substring = focusCarmodelID.substring(2 * i, 2 * i + 1);
                    Log.d("submit", substring);
                    if (user.getList_Focus() != null && user.getList_Focus().size() > 0) {
                        for (int j = 0; j < user.getList_Focus().size(); j++) {
                            UserPtInfoModel userPtInfoModel = user.getList_Focus().get(j);
                            if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(substring)) {
                                List<Boolean> list_checkinfo = cpCustomFocusAdapter.getList_checkinfo();
                                list_checkinfo.set(j, true);
                                cpCustomFocusAdapter.setList_checkinfo(list_checkinfo);
                                ((CheckBox) gv_focus.getChildAt(j).findViewById(R.id.customersinfo_fouces_item_cb)).setChecked(true);
                            }
                        }
                    }
                }
            }


            if (TextUtils.isEmpty(cpData.getIsOtherCome()) || "是".equals(cpData.getIsOtherCome())) {
                isHavePhoneNumber = false;
//                rl_intention_garde.setClickable(false);
//                tv_intention_garde.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                et_talkrecord.setHint("必填");
                et_talkrecord.setHintTextColor(getResources().getColor(R.color.appOrange));

            } else {
                //

                isHavePhoneNumber = true;
//                tv_shop_channel.setHint("必填");
//                tv_shop_channel.setHintTextColor(getResources().getColor(R.color.appOrange));
//                tv_intention_garde.setHint("必填");
//                tv_intention_garde.setHintTextColor(getResources().getColor(R.color.appOrange));

                tv_isdrive.setHint("必填");
                tv_isdrive.setHintTextColor(getResources().getColor(R.color.appOrange));

                et_talkrecord.setHint("必填");
                et_talkrecord.setHintTextColor(getResources().getColor(R.color.appOrange));

            }
        }

//        if (!TextUtils.isEmpty(cpData.getLiveProvince())) {
//            Log.d("provicen", cpData.getLiveProvince() + "------");
//            tv_province.setText(cpData.getLiveProvince());
//            province = cpData.getLiveProvince();
//        }
//        if (!TextUtils.isEmpty(cpData.getLiveCity())) {
//            tv_city.setText(cpData.getLiveCity());
//            city = cpData.getLiveCity();
//        }
//        if (!TextUtils.isEmpty(cpData.getLiveArea())) {
//            tv_area.setText(cpData.getLiveArea());
//        }

//        if (!TextUtils.isEmpty(cpData.getFollowPeo())) {
//            tv_followeperson.setText(cpData.getFollowPeo());
//        }
//
//        if (!TextUtils.isEmpty(cpData.getCarSeriesName())) {
//            tv_intention_chexi.setText(cpData.getCarSeriesName());
//        }

    }

    public void saveData(View view) {
        Log.d("boolean", cpCustomFocusAdapter.getList_checkinfo().toString());
        cpData.setCustomerName(ed_name.getText().toString());
        cpData.setCustomerPhone(ed_phone.getText().toString());
        cpData.setCustomerSparePhone(ed_phone_sq.getText().toString());
        cpData.setTalkProcess(et_talkrecord.getText().toString());
        cpData.setQuoteSituation(ed_QuoteSituation.getText().toString());
        cpData.setAddressInfo(ed_address_info.getText().toString());
//        cpData.setChannelRemark("");
        cpData.setWeChatNum(ed_weChat.getText().toString());
        cpData.setRemark(ed_remark.getText().toString());
        cpData.setNextCallTime(tv_date_yujihuifang.getText().toString());

        if (isHavePhoneNumber) {
            if(isSaveYanZhengHavePhone){
                if (checkSaveDate()) {
                    upLoadData(view);
                }
            }else {
                if (checkSaveDate_weitianxiephone()) {
                    upLoadData(view);
                }
            }

        } else {
            if (checkSaveDataNoPhone()) {
                upLoadData(view);
            }
        }

    }

    private void upLoadData(View view) {
        StringBuffer buffer = new StringBuffer();
        List<Boolean> list_checkinfo = cpCustomFocusAdapter.getList_checkinfo();
        boolean flagss = true;
        for (int i = 0; i < list_checkinfo.size(); i++) {
            if (list_checkinfo.get(i)) {
                if (flagss) {
                    buffer.append(list_focus.get(i).getID() + "");
                } else {
                    buffer.append("," + list_focus.get(i).getID());
                }
                flagss = false;
            }
        }
        cpData.setFocusCarmodelID(buffer.toString());
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        downLoadData();
    }

    public void viewOnclick(View view) {
        switch (view.getId()) {
            case R.id.activity_cpinfo_rl_isdrive:
                MyDialogHelper myDialogHelper_isdriver = new MyDialogHelper();
                final List<String> list_isdriver = new ArrayList<String>();
                list_isdriver.add("是");
                list_isdriver.add("否");
                myDialogHelper_isdriver.showListDialog(this, "是否试驾", list_isdriver, (TextView) view.findViewById(R.id.activity_cpinfo_tv_isdrive), new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        if ("是".equals(list_isdriver.get(position))) {
                            cpData.setIsDrive("是");
                        } else {
                            cpData.setIsDrive("否");
                        }
                    }
                });
                break;

            case R.id.activity_cpinfo_rl_paytype:
                MyDialogHelper myDialogHelper_ispaytype = new MyDialogHelper();
                final List<UserPtInfoModel> list_paytype = user.getList_PayType();
                if (list_paytype != null && list_paytype.size() > 0) {
                    final List<String> stringList_paytype = userPtInfoModeltoStringList(list_paytype);
                    myDialogHelper_ispaytype.showListDialog(this, "付款方式", stringList_paytype, (TextView) view.findViewById(R.id.activity_cpinfo_tv_paytype), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            cpData.setPaytype(stringList_paytype.get(position));
                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.activity_cpinfo_rl_customerhaver:
//                MyDialogHelper myDialogHelper_custerhaver = new MyDialogHelper();
//                final List<UserPtInfoModel> list_custerhaver = user.getList_AllEmpID();
//                if (list_custerhaver != null && list_custerhaver.size() > 0) {
//                    final List<String> stringList_custerhaver = userPtInfoModeltoStringList(list_custerhaver);
//                    myDialogHelper_custerhaver.showListDialog(this, "潜客拥有者", stringList_custerhaver, (TextView) view.findViewById(R.id.activity_cpinfo_tv_customerhaver), new MyDialogHelper.DialogCallBack() {
//                        @Override
//                        public void callBack(int position) {
//                            cpData.setCusOwnerID(list_custerhaver.get(position).getID());
//                        }
//                    });
//                } else {
//                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                }

                break;


//            case R.id.activity_cpinfo_rl_followeperson:
//                MyDialogHelper myDialogHelper_flperson = new MyDialogHelper();
//                final List<String> list_flperson = new ArrayList<String>();
//                list_flperson.add("0");
//                list_flperson.add("1");
//                list_flperson.add("2");
//                list_flperson.add("3");
//                list_flperson.add("4");
//                list_flperson.add("5");
//                list_flperson.add("5人及以上");
//                myDialogHelper_flperson.showListDialog(this, "随行人数", list_flperson, (TextView) view.findViewById(R.id.activity_cpinfo_tv_followeperson), new MyDialogHelper.DialogCallBack() {
//                    @Override

//                    public void callBack(int position) {
//                        cpData.setFollowPeo(list_flperson.get(position));
//                    }
//                });
//                break;
            case R.id.activity_cpinfo_rl_shopchannel:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_shopchannel = new MyDialogHelper();
                    final List<UserPtInfoModel> list_channel = user.getList_Channel();
                    if (list_channel != null && list_channel.size() > 0) {
                        List<String> list_shopchannel = userPtInfoModeltoStringList(list_channel);
                        myDialogHelper_shopchannel.showListDialog(this, "接触渠道", list_shopchannel, (TextView) view.findViewById(R.id.activity_cpinfo_tv_shopchannel), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                Log.d("ss", list_channel.get(position).toString());
                                cpData.setChannelId(list_channel.get(position).getID());

                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.activity_cpinfo_rl_intention_garde:
                MyDialogHelper myDialogHelper_garde = new MyDialogHelper();
                final List<UserPtInfoModel> list_garde = user.getList_LevelNotDefeat();
                if (list_garde != null && list_garde.size() > 0) {
                    final List<String> listss_garde = userPtInfoModeltoStringList(list_garde);
                    myDialogHelper_garde.showListDialog(this, "意向等级", listss_garde, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_garde), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            cpData.setIntentLevel(list_garde.get(position).getID());
                            levelModel = list_garde.get(position);

                            Calendar now_1 = Calendar.getInstance();
                            String nowStr_1 = null;
                            if (levelModel != null && levelModel.getPID() != null) {
                                int i = Integer.parseInt(levelModel.getPID());
                                now_1.add(Calendar.DAY_OF_YEAR, i);
                            } else {
                                now_1.add(Calendar.DAY_OF_YEAR, 1);
                            }
                            nowStr_1 = now_1.get(Calendar.YEAR) + "-" + (now_1.get(Calendar.MONTH) + 1) + "-" + now_1.get(Calendar.DAY_OF_MONTH);
                            dpd = DatePickerDialog.newInstance(CpCustomInfoActivity.this, now_1.get(Calendar.YEAR), now_1.get(Calendar.MONTH), now_1.get(Calendar.DAY_OF_MONTH));
                            dpd.setAccentColor(Color.rgb(50, 126, 202));//327ECA

                            if ("O".equals(listss_garde.get(position))) {
                                tv_date_yujihuifang.setText("");
                            } else {
                                tv_date_yujihuifang.setText(nowStr_1);
                            }

                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.activity_cpinfo_rl_intention_chexi:
                MyDialogHelper myDialogHelper_intention_chexi = new MyDialogHelper();
                final List<UserPtInfoModel> userPtInfoModels_chexi = map_chexi.get(brand);
                if (user.getList_CarSeries() != null && user.getList_CarSeries().size() > 0) {
                    if (userPtInfoModels_chexi != null && userPtInfoModels_chexi.size() > 0) {
                        final List<String> lists_chexi = userPtInfoModeltoStringList(userPtInfoModels_chexi);
                        myDialogHelper_intention_chexi.showListDialog(this, "意向车系", lists_chexi, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chexi), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                chexi = userPtInfoModels_chexi.get(position).getID();
                                cpData.setCarSeriesName(lists_chexi.get(position));
                                cpData.setCarSeriesID(userPtInfoModels_chexi.get(position).getID());

                                cpData.setCarModelName("");
                                cpData.setCarModelID("");
                                tv_intention_chexin.setText("");
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }


//                MyDialogHelper myDialogHelper_intention_chexin = new MyDialogHelper();
//                final List<UserPtInfoModel> userPtInfoModels = map_chexin.get(chexi);
//                if (user.getList_CarModels() != null && user.getList_CarModels().size() > 0) {
//                    if (userPtInfoModels != null && userPtInfoModels.size() > 0) {
//                        final List<String> list_chexin = userPtInfoModeltoStringList(userPtInfoModels);
//                        myDialogHelper_intention_chexin.showListDialog(this, "意向车型", list_chexin, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chexin), new MyDialogHelper.DialogCallBack() {
//                            @Override
//                            public void callBack(int position) {
//                                cpData.setCarModelName(list_chexin.get(position));
//                                cpData.setCarModelID(userPtInfoModels.get(position).getID());
//                            }
//                        });
//                    } else {
//                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
//                }


                break;
            case R.id.activity_cpinfo_rl_intention_use:

                MyDialogHelper myDialogHelper_use = new MyDialogHelper();
                final List<UserPtInfoModel> list_ptuse = user.getList_Useage();
                if (list_ptuse != null && list_ptuse.size() > 0) {
                    final List<String> list_use = userPtInfoModeltoStringList(list_ptuse);
                    myDialogHelper_use.showListDialog(this, "购车用途", list_use, (TextView) view.findViewById(R.id.activity_cpinfo_tv_buyuse), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            cpData.setUseageID(list_ptuse.get(position).getID());
                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.activity_cpinfo_rl_province:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_province = new MyDialogHelper();
//                List<String> list_province = Arrays.asList(mProvinceDatas);

                    if (list_Provonce.size() > 0 && !isLoading) {
                        myDialogHelper_province.showListDialog(this, "常住省", list_Provonce, (TextView) view.findViewById(R.id.activity_cpinfo_tv_province), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                province = list_Provonce.get(position);
                                cpData.setLiveProvince(province);
                                cpData.setLiveCity("");
                                tv_city.setText("");
                                cpData.setLiveArea("");
                                tv_area.setText("");
                                city = "";
                                list_City.clear();
                                list_District.clear();
                                initProvince(province, "", 2);

                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            case R.id.activity_cpinfo_rl_city:
                if (isOnclick) {
                    if (!TextUtils.isEmpty(province)) {
                        MyDialogHelper myDialogHelper_city = new MyDialogHelper();
//                    final List<String> list_city = Arrays.asList(mCitisDatasMap.get(province));
                        if (list_City != null && list_City.size() > 0 && !isLoading) {
                            myDialogHelper_city.showListDialog(this, "常住市", list_City, (TextView) view.findViewById(R.id.activity_cpinfo_tv_city), new MyDialogHelper.DialogCallBack() {
                                @Override
                                public void callBack(int position) {
                                    city = list_City.get(position);
                                    cpData.setLiveCity(city);
                                    cpData.setLiveArea("");
                                    tv_area.setText("");
                                    list_District.clear();
                                    initProvince(province, city, 3);

                                }
                            });
                        } else {
                            Toast.makeText(CpCustomInfoActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "请选择省", Toast.LENGTH_SHORT).show();
                    }
                }


                break;
            case R.id.activity_cpinfo_rl_area:
                if (isOnclick) {
                    if (!TextUtils.isEmpty(city)) {
                        MyDialogHelper myDialogHelper_area = new MyDialogHelper();
                        if (null != list_District && list_District.size() > 0 && !isLoading) {
                            myDialogHelper_area.showListDialog(this, "常住区", list_District, (TextView) view.findViewById(R.id.activity_cpinfo_tv_area), new MyDialogHelper.DialogCallBack() {
                                @Override
                                public void callBack(int position) {
                                    cpData.setLiveArea(list_District.get(position));
                                }
                            });
                        } else {
                            Toast.makeText(CpCustomInfoActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "请选择市", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.activity_cpinfo_rl_intention_chexin:
                MyDialogHelper myDialogHelper_intention_chexin = new MyDialogHelper();
                final List<UserPtInfoModel> userPtInfoModels = map_chexin.get(chexi);
                if (user.getList_CarModels() != null && user.getList_CarModels().size() > 0) {
                    if (userPtInfoModels != null && userPtInfoModels.size() > 0) {
                        final List<String> list_chexin = userPtInfoModeltoStringList(userPtInfoModels);
                        myDialogHelper_intention_chexin.showListDialog(this, "意向车型", list_chexin, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chexin), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                cpData.setCarModelName(list_chexin.get(position));
                                cpData.setCarModelID(userPtInfoModels.get(position).getID());
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;


            case R.id.activity_cpinfo_rl_infochannel:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_infochannel = new MyDialogHelper();
                    final List<UserPtInfoModel> list_infochannel = user.getList_SourceChannel();
                    if (list_infochannel != null && list_infochannel.size() > 0) {
                        final List<String> list_info = userPtInfoModeltoStringList(list_infochannel);
                        myDialogHelper_infochannel.showListDialog(this, "信息渠道", list_info, (TextView) view.findViewById(R.id.activity_cpinfo_tv_infochannel), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                cpData.setSourceChannelId(list_infochannel.get(position).getID());
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.activity_cpinfo_rl_sex:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_sex = new MyDialogHelper();
                    final List<String> list_sex = new ArrayList<String>();
                    list_sex.add("男");
                    list_sex.add("女");

                    myDialogHelper_sex.showListDialog(this, "性别", list_sex, (TextView) view.findViewById(R.id.activity_cpinfo_tv_sex), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            //
                            if ("男".equals(list_sex.get(position))) {
                                cpData.setCusSex("1");
                            } else {
                                cpData.setCusSex("0");
                            }

                        }
                    });
                }

                break;
            case R.id.activity_cpinfo_rl_intention_pingpai:
                MyDialogHelper myDialogHelper_pingpai = new MyDialogHelper();
                final List<UserPtInfoModel> list_pingpai = user.getList_IntentBrand();
                if (list_pingpai != null && list_pingpai.size() > 0) {
                    final List<String> list_ping = userPtInfoModeltoStringList(list_pingpai);
                    myDialogHelper_pingpai.showListDialog(this, "意向品牌", list_ping, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_pingpai), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            //
                            brand = list_pingpai.get(position).getID();
                            Log.d("brand", brand);
                            cpData.setCarBrandID(list_pingpai.get(position).getID());

                            chexi = "0";
                            tv_intention_chexi.setText("");
                            cpData.setCarSeriesID("");
                            cpData.setCarSeriesName("");

                            cpData.setCarModelName("");
                            cpData.setCarModelID("");
                            tv_intention_chexin.setText("");

                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.activity_cpinfo_rl_date_nigou:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_nigou = new MyDialogHelper();
                    final List<UserPtInfoModel> list_nigou = user.getList_WishBuyDate();
                    if (list_nigou != null && list_nigou.size() > 0) {
                        List<String> stringList_nigou = userPtInfoModeltoStringList(list_nigou);
                        myDialogHelper_nigou.showListDialog(this, "拟购时间", stringList_nigou, (TextView) view.findViewById(R.id.activity_cpinfo_tv_date_nigou), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                cpData.setWishBuyDate(list_nigou.get(position).getName());
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.activity_cpinfo_rl_date_jieting:
                if (isOnclick) {
                    MyDialogHelper myDialogHelper_jieting = new MyDialogHelper();
                    final List<UserPtInfoModel> list_jieting = user.getList_TimeFrame();
                    if (list_jieting != null && list_jieting.size() > 0) {
                        List<String> stringList_jieting = userPtInfoModeltoStringList(list_jieting);
                        myDialogHelper_jieting.showListDialog(this, "方便接听时段", stringList_jieting, (TextView) view.findViewById(R.id.activity_cpinfo_tv_date_jieting), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                cpData.setCusHandyTimeFrame(list_jieting.get(position).getName());
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.activity_cpinfo_rl_date_yujihuifang:
                if (!dpd.isAdded()) {
                    dpd.show(getFragmentManager(), "yuedingtime");
                }
                break;


            case R.id.activity_cpcustominfo_back:
                finish();
                break;
        }
    }

    private void downLoadData() {
        ProgressDialogHelper.showProgressDialog(this, "正在上传数据...");
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PCINFOSAVE_URL, XutilsRequest.saveCpCustomer(user.getEmpId(), cpData),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                        Log.d("cpresult", objectResponseInfo.result);
                        SaveStatu saveStatu = JsonParser.saveCustomersParser(objectResponseInfo.result);
                        if (saveStatu.isSaveDate()) {
                            HttpUtilsHelper.downLoadUpdataUI(CpCustomInfoActivity.this, true, RESULT_OK, true, true, true);
                        } else {
                            Toast.makeText(CpCustomInfoActivity.this, saveStatu.getMessage(), Toast.LENGTH_SHORT).show();
                            ProgressDialogHelper.dismissProgressDialog();
                        }


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(CpCustomInfoActivity.this, "网络请求出错，请检查网络", Toast.LENGTH_SHORT).show();
                        ProgressDialogHelper.dismissProgressDialog();
                    }
                });
    }

    private void checkPhone(String phone) {
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PCINFOSAVE_PHONEINFO_URL, XutilsRequest.cpPhoneCustomer(user.getEmpId(), phone, cpData.getReciveComeId()),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {
                        Log.d("cpresult_sasasdasdas", objectResponseInfo.result);
                        CpCustomer cpCustomer = JsonParser.cpPhoneCustomersParser(objectResponseInfo.result);

                        if (cpCustomer != null) {

                            Log.d("cpresult_sasasdasdas", cpCustomer.toString());
                            cpData.setCustomerStateStr(cpCustomer.getCustomerStateStr());
                            cpData.setCustomerName(cpCustomer.getCustomerName());
                            ed_name.setText(cpCustomer.getCustomerName());

                            cpData.setCustomerPhone(cpCustomer.getCustomerPhone());
                            ed_phone.setText(cpCustomer.getCustomerPhone());

                            cpData.setCustomerSparePhone(cpCustomer.getCustomerSparePhone());
                            ed_phone_sq.setText(cpCustomer.getCustomerSparePhone());

                            String carBrandID = cpCustomer.getCarBrandID();
                            if (carBrandID != null) {
                                cpData.setCarBrandID(carBrandID);
                                brand = carBrandID;
                                List<UserPtInfoModel> list_pingpai = user.getList_IntentBrand();
                                if (list_pingpai != null && list_pingpai.size() > 0) {
                                    for (int i = 0; i < list_pingpai.size(); i++) {
                                        if (carBrandID.equals(list_pingpai.get(i).getID())) {
                                            tv_intent_pingpai.setText(list_pingpai.get(i).getName());
                                        }
                                    }
                                }
                                String carSeriesID = cpCustomer.getCarSeriesID();
                                if (carSeriesID != null) {
                                    cpData.setCarSeriesID(carSeriesID);
                                    chexi = carBrandID;
                                    List<UserPtInfoModel> list_chexi = map_chexi.get(brand);
                                    if (list_chexi != null && list_chexi.size() > 0) {
                                        for (int i = 0; i < list_chexi.size(); i++) {
                                            if (carSeriesID.equals(list_chexi.get(i).getID())) {
                                                tv_intention_chexi.setText(list_chexi.get(i).getName());
                                            }
                                        }
                                    }
                                    String carModelID = cpCustomer.getCarModelID();
                                    if (carModelID != null) {
                                        cpData.setCarModelID(carModelID);
                                        List<UserPtInfoModel> list_chexin = map_chexin.get(chexi);
                                        if (list_chexin != null && list_chexin.size() > 0) {
                                            for (int i = 0; i < list_chexin.size(); i++) {
                                                if (carModelID.equals(list_chexin.get(i).getID())) {
                                                    tv_intention_chexin.setText(list_chexin.get(i).getName());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            cpData.setChannelId(cpCustomer.getChannelId());
                            List<UserPtInfoModel> list_channel = user.getList_Channel();
                            if (list_channel != null && list_channel.size() > 0) {
                                for (int i = 0; i < list_channel.size(); i++) {
                                    if (cpCustomer.getChannelId().equals(list_channel.get(i).getID())) {
                                        tv_shop_channel.setText(list_channel.get(i).getName());
                                    }
                                }
                            }

                            cpData.setSourceChannelId(cpCustomer.getSourceChannelId());
                            List<UserPtInfoModel> list_sourceChannel = user.getList_SourceChannel();
                            if (list_sourceChannel != null && list_sourceChannel.size() > 0) {
                                for (int i = 0; i < list_sourceChannel.size(); i++) {
                                    if (cpCustomer.getSourceChannelId().equals(list_sourceChannel.get(i).getID())) {
                                        tv_info_channel.setText(list_sourceChannel.get(i).getName());
                                    }
                                }
                            }

                            cpData.setCusSex(cpCustomer.getCusSex());
                            tv_sex.setText(cpCustomer.getCusSex());

                            cpData.setCusOwnerID(cpCustomer.getCusOwnerID());
                            cpData.setCusOwnerName(cpCustomer.getCusOwnerName());
                            tv_customer_haver.setText(cpCustomer.getCusOwnerName());

                            cpData.setWishBuyDate(cpCustomer.getWishBuyDate());
                            tv_date_nigou.setText(cpCustomer.getWishBuyDate());

                            cpData.setIntentLevel(cpCustomer.getIntentLevel());
                            List<UserPtInfoModel> list_levelNotDefeat = user.getList_LevelNotDefeat();
                            if (list_levelNotDefeat != null && list_levelNotDefeat.size() > 0) {
                                for (int i = 0; i < list_levelNotDefeat.size(); i++) {
                                    if (cpCustomer.getIntentLevel().equals(list_levelNotDefeat.get(i).getID())) {
                                        tv_intention_garde.setText(list_levelNotDefeat.get(i).getName());
                                    }
                                }
                            }

                            cpData.setNextCallTime(cpCustomer.getNextCallTime());
                            tv_date_yujihuifang.setText(cpCustomer.getNextCallTime());

                            cpData.setEmpName(cpCustomer.getEmpName());

                            cpData.setIsDrive(cpCustomer.getIsDrive());
                            tv_isdrive.setText(cpCustomer.getIsDrive());

                            cpData.setQuoteSituation(cpCustomer.getQuoteSituation());
                            ed_QuoteSituation.setText(cpCustomer.getQuoteSituation());

                            cpData.setWeChatNum(cpCustomer.getWeChatNum());
                            ed_weChat.setText(cpCustomer.getWeChatNum());

                            cpData.setRemark(cpCustomer.getRemark());
                            ed_remark.setText(cpCustomer.getRemark());


                            if (!TextUtils.isEmpty(cpCustomer.getLiveProvince())) {
                                cpData.setLiveProvince(cpCustomer.getLiveProvince());
                                tv_province.setText(cpCustomer.getLiveProvince());
                                province = cpCustomer.getLiveProvince();
                                initProvince(province, "", 2);
                                if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
                                    cpData.setLiveCity(cpCustomer.getLiveCity());
                                    tv_city.setText(cpCustomer.getLiveCity());
                                    city = cpCustomer.getLiveCity();
                                    initProvince(province, city, 3);
                                    if (!TextUtils.isEmpty(cpCustomer.getLiveArea())) {
                                        cpData.setLiveArea(cpCustomer.getLiveArea());
                                        tv_area.setText(cpCustomer.getLiveArea());
                                    }
                                }
                            }

                            ed_address_info.setText(cpCustomer.getAddressInfo());
                            cpData.setAddressInfo(cpCustomer.getAddressInfo());
                            if ("是".equals(cpCustomer.getIsDisable())) {
                                isOnclick = false;
                                setEditStatu(false, "#aaaaaa");
                            } else {
                                isOnclick = true;
                                setEditStatu(true, "#ffffff");
                            }

                        } else {
                            isOnclick = true;
                            setEditStatu(true, "#ffffff");
                            cpData.setCustomerStateStr("");
                            cpData.setCustomerName("");
                            ed_name.setText("");

//                            cpData.setCustomerPhone("");
//                            ed_phone.setText("");

                            cpData.setCustomerSparePhone("");
                            ed_phone_sq.setText("");

                            cpData.setCusOwnerID(initContactEmpID);
                            cpData.setCusOwnerName(initContactEmpName);
                            tv_customer_haver.setText("");

                            cpData.setCarBrandID("");
                            tv_intent_pingpai.setText("");
                            cpData.setCarSeriesID("");
                            tv_intention_chexi.setText("");
                            cpData.setCarModelID("");
                            tv_intention_chexin.setText("");

                            cpData.setChannelId("0");
                            tv_shop_channel.setText("");

                            cpData.setSourceChannelId("0");
                            tv_info_channel.setText("");

                            cpData.setCusSex("");
                            tv_sex.setText("");

                            cpData.setWishBuyDate("");
                            tv_date_nigou.setText("");

                            cpData.setIntentLevel("");
                            tv_intention_garde.setText("");
                            cpData.setNextCallTime("");
                            tv_date_yujihuifang.setText("");

                            cpData.setEmpName("");

                            cpData.setIsDrive("");
                            tv_isdrive.setText("");

                            cpData.setQuoteSituation("");
                            ed_QuoteSituation.setText("");

                            cpData.setWeChatNum("");
                            ed_weChat.setText("");

                            cpData.setRemark("");
                            ed_remark.setText("");

                            cpData.setLiveProvince("");
                            tv_province.setText("");

                            cpData.setLiveCity("");
                            tv_city.setText("");

                            cpData.setLiveArea("");
                            tv_area.setText("");

                            ed_address_info.setText("");
                            cpData.setAddressInfo("");
                        }


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Log.d("cpresult", "faai");
                    }
                });
    }


    private void setEditStatu(boolean isEdit, String color) {
        ed_name.setFocusable(isEdit);
        ed_name.setFocusableInTouchMode(isEdit);
//        ed_name.requestFocus();
        ed_phone_sq.setFocusable(isEdit);
        ed_phone_sq.setFocusableInTouchMode(isEdit);
        ed_weChat.setFocusable(isEdit);
        ed_weChat.setFocusableInTouchMode(isEdit);
        ed_address_info.setFocusable(isEdit);
        ed_address_info.setFocusableInTouchMode(isEdit);
        ed_QuoteSituation.setFocusable(isEdit);
        ed_QuoteSituation.setFocusableInTouchMode(isEdit);
        ed_remark.setFocusable(isEdit);
        ed_remark.setFocusableInTouchMode(isEdit);

        rl_name.setBackgroundColor(Color.parseColor(color));
        rl_sex.setBackgroundColor(Color.parseColor(color));
//        rl_phone.setBackgroundColor(Color.parseColor(color));
        rl_phone_sq.setBackgroundColor(Color.parseColor(color));
        rl_wechat.setBackgroundColor(Color.parseColor(color));
        rl_proince.setBackgroundColor(Color.parseColor(color));
        rl_city.setBackgroundColor(Color.parseColor(color));
        rl_country.setBackgroundColor(Color.parseColor(color));
        rl_address.setBackgroundColor(Color.parseColor(color));
        rl_shop_channl.setBackgroundColor(Color.parseColor(color));
        rl_info_channel.setBackgroundColor(Color.parseColor(color));
        rl_nigou.setBackgroundColor(Color.parseColor(color));
        rl_fangbian.setBackgroundColor(Color.parseColor(color));
        rl_qianke_hacer.setBackgroundColor(Color.parseColor(color));
        rl_baojia.setBackgroundColor(Color.parseColor(color));
        rl_remark.setBackgroundColor(Color.parseColor(color));
    }


    private boolean isnosuchLoading = false;

    private void initProvince(String province, String city, final int type) {
        isLoading = true;
        Log.d("prossssssssssssssssssss", UrlData.PROVINCE_URL);
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PROVINCE_URL, XutilsRequest.getPr(province, city), new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.d("prossssssssssssssssssss", responseInfo.result);
                switch (type) {
                    case 1:
                        list_Provonce = JsonParser.loadPro_City_Country(responseInfo.result, type);
                        Log.d("stringList", list_Provonce.toString());
                        break;
                    case 2:
                        list_City = JsonParser.loadPro_City_Country(responseInfo.result, type);
                        Log.d("stringList", list_City.toString());
                        break;
                    case 3:
                        list_District = JsonParser.loadPro_City_Country(responseInfo.result, type);
                        Log.d("stringList", list_District.toString());
                        break;
                }
                isLoading = false;
                if (!isdismissdialog) {
                    ProgressDialogHelper.dismissProgressDialog();
                    isdismissdialog = true;
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.d("prossssssssssssssssssss", "nodate");
                isLoading = false;
                if (isdismissdialog) {
                    ProgressDialogHelper.dismissProgressDialog();
                    isdismissdialog = true;
                }
            }
        });
    }


    private boolean isdismissdialog = true;

    private void downLoadDeafualtData() {
        ProgressDialogHelper.showProgressDialog(this, "正在加载默认数据...");
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PCINFODAFULT_URL, XutilsRequest.getDeafultCPinfo(cpData.getReciveComeId()),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                        Log.d("cpredefaultsult", objectResponseInfo.result);
                        List<CpCustomer> cpCustomerList = JsonParser.cpDeafultCustomersParser(objectResponseInfo.result);
                        Log.d("cpredefaultsult", cpCustomerList.toString());
                        if (null != cpCustomerList && cpCustomerList.size() > 0) {
                            CpCustomer cpCustomer = cpCustomerList.get(0);
                            cpData.setCustomerName(cpCustomer.getCustomerName());
                            ed_name.setText(cpCustomer.getCustomerName());

                            cpData.setCustomerPhone(cpCustomer.getCustomerPhone());
                            ed_phone.setText(cpCustomer.getCustomerPhone());

                            if (!TextUtils.isEmpty(cpCustomer.getLiveProvince())) {
                                isdismissdialog = false;
                                cpData.setLiveProvince(cpCustomer.getLiveProvince());
                                tv_province.setText(cpCustomer.getLiveProvince());
                                province = cpCustomer.getLiveProvince();
                                initProvince(province, "", 2);
                                if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
                                    cpData.setLiveCity(cpCustomer.getLiveCity());
                                    tv_city.setText(cpCustomer.getLiveCity());
                                    city = cpCustomer.getLiveCity();
                                    initProvince(province, city, 3);
                                    if (!TextUtils.isEmpty(cpCustomer.getLiveArea())) {
                                        cpData.setLiveArea(cpCustomer.getLiveArea());
                                        tv_area.setText(cpCustomer.getLiveArea());
                                    }
                                }
                            }


//                            if (!TextUtils.isEmpty(cpCustomer.getLiveProvince()) && mCitisDatasMap.get(cpCustomer.getLiveProvince()) != null) {
//                                cpData.setLiveProvince(cpCustomer.getLiveProvince());
//                                tv_province.setText(cpCustomer.getLiveProvince());
//                                province = cpCustomer.getLiveProvince();
//
//                                if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
//                                    String[] strings = mCitisDatasMap.get(cpCustomer.getLiveProvince());
//                                    if (strings != null) {
//                                        for (int i = 0; i < strings.length; i++) {
//                                            if (cpCustomer.getLiveCity().equals(strings[i])) {
//                                                cpData.setLiveCity(cpCustomer.getLiveCity());
//                                                tv_city.setText(cpCustomer.getLiveCity());
//                                                city = cpCustomer.getLiveCity();
//
//                                                if (!TextUtils.isEmpty(cpCustomer.getLiveArea())) {
//                                                    String[] strings1 = mDistrictDatasMap.get(cpCustomer.getLiveArea());
//                                                    if (strings1 != null) {
//                                                        for (int j = 0; j < strings1.length; j++) {
//                                                            if (cpCustomer.getLiveArea().equals(strings1[j])) {
//                                                                cpData.setLiveArea(cpCustomer.getLiveArea());
//                                                                tv_area.setText(cpCustomer.getLiveArea());
//                                                            } else {
//                                                                continue;
//                                                            }
//                                                        }
//                                                    }
//                                                }
//
//                                            } else {
//                                                continue;
//                                            }
//
//                                        }
//                                    }
//                                }
//                            }


                            cpData.setOldCusCarNO(cpCustomer.getOldCusCarNO());

//                            if (!TextUtils.isEmpty(cpCustomer.getIsChangeID())) {
//                                switch (cpCustomer.getIsChangeID()) {
//                                    case "1":
//                                        cpData.setIsChange("置换");
//                                        tv_buycarjinyan.setText("置换");
//                                        break;
//                                    case "2":
//                                        cpData.setIsChange("新购");
//                                        tv_buycarjinyan.setText("新购");
//                                        break;
//                                }
//                            }

                            cpData.setPaytype(cpCustomer.getPaytype());
                            tv_ispaytype.setText(cpCustomer.getPaytype());


                            cpData.setFollowPeo(cpCustomer.getFollowPeo());
                            tv_followeperson.setText(cpCustomer.getFollowPeo());


                            if (user != null) {
                                if (user.getList_LevelNotDefeat() != null && user.getList_LevelNotDefeat().size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : user.getList_LevelNotDefeat()) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getIntentLevelID())) {
                                            cpData.setIntentLevel(userPtInfoModel.getName());
                                            tv_intention_garde.setText(userPtInfoModel.getName());
                                        }
                                    }
                                }
//                                if (user.getList_CheckColorList() != null && user.getList_CheckColorList().size() > 0) {
//                                    for (UserPtInfoModel userPtInfoModel : user.getList_CheckColorList()) {
//                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getCarColorID())) {
//                                            cpData.setCarColor(userPtInfoModel.getName());
//                                            tv_intention_chese.setText(userPtInfoModel.getName());
//                                        }
//                                    }
//                                }
                                if (user.getList_CarSeries() != null && user.getList_CarSeries().size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : user.getList_CarSeries()) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getCarSeriesID())) {
                                            cpData.setCarSeriesName(userPtInfoModel.getName());
                                            tv_intention_chexi.setText(userPtInfoModel.getName());
                                            chexi = userPtInfoModel.getID();
                                        }
                                    }
                                }

                                if (map_chexin != null && map_chexin.get(cpCustomer.getCarSeriesID()) != null && map_chexin.get(cpCustomer.getCarSeriesID()).size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : map_chexin.get(cpCustomer.getCarSeriesID())) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getCarModelID())) {
                                            cpData.setCarModelName(userPtInfoModel.getName());
                                            tv_intention_chexin.setText(userPtInfoModel.getName());
                                        }
                                    }
                                }

                                cpData.setChannelId(cpCustomer.getChannelId());
                                if (user.getList_Channel() != null && user.getList_Channel().size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : user.getList_Channel()) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getChannelId())) {
                                            tv_shop_channel.setText(userPtInfoModel.getName());
                                            if (userPtInfoModel.getName() != null) {
                                                switch (userPtInfoModel.getName()) {
                                                    case "转介绍":
                                                        ll_info_channel.setVisibility(View.GONE);
                                                        break;
                                                    case "网络":
                                                        ll_info_channel.setVisibility(View.VISIBLE);
                                                        break;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (TextUtils.isEmpty(cpCustomer.getSourceChannelId()) || "null".equals(cpCustomer.getSourceChannelId())) {
                                    cpData.setSourceChannelId("0");

                                } else {
                                    cpData.setSourceChannelId(cpCustomer.getSourceChannelId());
                                    if (user.getList_SourceChannel() != null && user.getList_SourceChannel().size() > 0) {
                                        for (UserPtInfoModel userPtInfoModel : user.getList_SourceChannel()) {
                                            if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getSourceChannelId())) {
                                                tv_info_channel.setText(userPtInfoModel.getName());
                                            }
                                        }
                                    }
                                }


                                cpData.setUseageID(cpCustomer.getUseageID());
                                if (user.getList_Useage() != null && user.getList_Useage().size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : user.getList_Useage()) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getUseageID())) {
                                            tv_buyuse.setText(userPtInfoModel.getName());
                                        }
                                    }
                                }

                                String focusCarmodelID = cpCustomer.getFocusCarmodelID();
                                cpData.setFocusCarmodelID(focusCarmodelID);

                                if (!TextUtils.isEmpty(focusCarmodelID) && !"null".equals(focusCarmodelID)) {
                                    for (int i = 0; i < focusCarmodelID.length() / 2 + 1; i++) {
                                        String substring = focusCarmodelID.substring(2 * i, 2 * i + 1);
                                        Log.d("submit", substring);
                                        if (user.getList_Focus() != null && user.getList_Focus().size() > 0) {
                                            for (int j = 0; j < user.getList_Focus().size(); j++) {
                                                UserPtInfoModel userPtInfoModel = user.getList_Focus().get(j);
                                                if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(substring)) {
                                                    List<Boolean> list_checkinfo = cpCustomFocusAdapter.getList_checkinfo();
                                                    list_checkinfo.set(j, true);
                                                    cpCustomFocusAdapter.setList_checkinfo(list_checkinfo);
                                                    ((CheckBox) gv_focus.getChildAt(j).findViewById(R.id.customersinfo_fouces_item_cb)).setChecked(true);
                                                }
                                            }
                                        }
                                    }
                                }

                            }


                        }
                        if (isdismissdialog) {
                            ProgressDialogHelper.dismissProgressDialog();
                        }


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Toast.makeText(CpCustomInfoActivity.this, "网络请求出错，请检查网络", Toast.LENGTH_SHORT).show();
                        if (isdismissdialog) {
                            ProgressDialogHelper.dismissProgressDialog();
                        }
                    }
                });

        initProvince("", "", 1);
    }


    private boolean checkSaveDate() {
        boolean flag = true;
        if (!TextUtils.isEmpty(cpData.getChannelId()) && !"0".equals(cpData.getChannelId())) {

        } else {
            Toast.makeText(CpCustomInfoActivity.this, "未选择接触渠道", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        if (TextUtils.isEmpty(cpData.getIsDrive())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择是否试驾", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getIntentLevel())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择意向等级", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getTalkProcess())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写洽谈记录", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        return flag;
    }


    private boolean checkSaveDate_weitianxiephone() {
        boolean flag = true;

        if (TextUtils.isEmpty(cpData.getIsDrive())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择是否试驾", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getTalkProcess())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写洽谈记录", Toast.LENGTH_SHORT).show();
            flag = false;
        }


        return flag;
    }


    private boolean checkSaveDataNoPhone() {
        boolean flag = true;
        if (TextUtils.isEmpty(cpData.getTalkProcess())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写洽谈记录", Toast.LENGTH_SHORT).show();
            flag = false;
        }
        return flag;
    }

    private List<String> userPtInfoModeltoStringList(List<UserPtInfoModel> list) {
        List<String> strings = new ArrayList<String>();
        for (UserPtInfoModel userPtInfoModel : list) {
            String name = userPtInfoModel.getName();
            strings.add(name);
        }
        return strings;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String other_yueding = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String nowstr_yueding = getNowDate();
        if (!PhoneValidate.compareData(other_yueding, nowstr_yueding)) {
            Toast.makeText(this, "下次回访时间必须大于等于今天", Toast.LENGTH_SHORT).show();
        } else {
            tv_date_yujihuifang.setText(other_yueding);
        }
    }

    private String getNowDate() {
        Calendar now = Calendar.getInstance();
        String nowStr = now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DAY_OF_MONTH);
        return nowStr;
    }
}
