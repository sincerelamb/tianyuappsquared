package com.tygas.tianyu.tianyu.ui.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XmlParser;
import com.tygas.tianyu.tianyu.utils.XmlParserHandler;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CpCustomInfoActivity extends Activity {


    private EditText et_references_carnumber;
    private RelativeLayout rl_references_carnumber;
    private RelativeLayout rl_info_channel;
    private RelativeLayout rl_info_more_channel;
    private LinearLayout ll_references_carnumber;
    private LinearLayout ll_info_channel;

    private ScrollView scrollView;
    private EditText et_talkrecord;

    private EditText ed_name;
    private TextView tv_phone;
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

    private TextView tv_intention_chexin;
    private TextView tv_intention_chese;
    private TextView tv_buycarjinyan;
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


    private List<UserPtInfoModel> list_chexi = new ArrayList<UserPtInfoModel>();
    private Map<String, List<UserPtInfoModel>> map_chexin = new HashMap<String, List<UserPtInfoModel>>();

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
        initData();
        downLoadDeafualtData();
    }

    private void initProvineceData() {
//        XmlParser xmlParser = new XmlParser();
//        xmlParser.initProvinceDatas(this);
//        mProvinceDatas = xmlParser.getmProvinceDatas();
//        mCitisDatasMap = xmlParser.getmCitisDatasMap();
//        mDistrictDatasMap = xmlParser.getmDistrictDatasMap();
    }

    private void initCarModelList() {
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
    }

    private void initView() {
        ed_name = (EditText) findViewById(R.id.activity_cpinfo_ed_name);
        ed_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cpData.setCustomerName(s.toString());
            }
        });
        tv_phone = (TextView) findViewById(R.id.activity_cpinfo_tv_phone);
        rl_intention_garde = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_intention_garde);
        et_references_carnumber = (EditText) findViewById(R.id.activity_cpinfo_et_references_carnumber);
        et_references_carnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cpData.setOldCusCarNO(s.toString());
            }
        });

        rl_references_carnumber = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_references_carnumber);
        ll_references_carnumber = (LinearLayout) findViewById(R.id.activity_cpinfo_ll_references_carnumber);

        tv_info_channel = (TextView) findViewById(R.id.activity_cpinfo_tv_infochannel);
        rl_info_channel = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_infochannel);
        ll_info_channel = (LinearLayout) findViewById(R.id.activity_cpinfo_ll_infochannel);
        rl_info_more_channel = (RelativeLayout) findViewById(R.id.activity_cpinfo_rl_more_channel);

        tv_intention_chexin = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_chexin);
        tv_intention_chese = (TextView) findViewById(R.id.activity_cpinfo_tv_intention_chese);
        tv_buycarjinyan = (TextView) findViewById(R.id.activity_cpinfo_tv_buycarjinyan);


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

        et_talkrecord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                cpData.setTalkProcess(s.toString());
            }
        });

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
        ed_QuoteSituation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                cpData.setQuoteSituation(s.toString());
            }
        });

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
        cpData.setChannelId("0");
        cpData.setSourceChannelId("0");
        cpData.setUseageID("0");
        cpData.setLiveProvince("");
        cpData.setLiveCity("");
        cpData.setLiveArea("");

        Log.d("cpDate", cpData.toString());
        if (!TextUtils.isEmpty(cpData.getCustomerName())) {
            ed_name.setText(cpData.getCustomerName());
        }
        if (TextUtils.isEmpty(cpData.getCustomerPhone())) {
            isHavePhoneNumber = false;
            rl_intention_garde.setClickable(false);
            tv_intention_garde.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv_phone.setText(cpData.getCustomerPhone());
            isHavePhoneNumber = true;
            tv_province.setHint("必填");
            tv_province.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_city.setHint("必填");
            tv_city.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_area.setHint("必填");
            tv_area.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_shop_channel.setHint("必填");
            tv_shop_channel.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_info_channel.setHint("必填");
            tv_info_channel.setHintTextColor(getResources().getColor(R.color.appOrange));
            et_references_carnumber.setHint("必填");
            et_references_carnumber.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_isdrive.setHint("必填");
            tv_isdrive.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_followeperson.setHint("必填");
            tv_followeperson.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_intention_garde.setHint("必填");
            tv_intention_garde.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_intention_chexin.setHint("必填");
            tv_intention_chexin.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_buyuse.setHint("必填");
            tv_buyuse.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_buycarjinyan.setHint("必填");
            tv_buycarjinyan.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_ispaytype.setHint("必填");
            tv_ispaytype.setHintTextColor(getResources().getColor(R.color.appOrange));
            ed_QuoteSituation.setHint("必填");
            ed_QuoteSituation.setHintTextColor(getResources().getColor(R.color.appOrange));
            tv_intention_chese.setHint("必填");
            tv_intention_chese.setHintTextColor(getResources().getColor(R.color.appOrange));

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
        if (isHavePhoneNumber) {
            if (checkSaveDate()) {
                upLoadData(view);
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
                    buffer.append("&" + list_focus.get(i).getID());
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
                            cpData.setIsDrive("true");
                        } else {
                            cpData.setIsDrive("false");
                        }
                    }
                });
                break;

            case R.id.activity_cpinfo_rl_paytype:
                MyDialogHelper myDialogHelper_ispaytype = new MyDialogHelper();
                final List<String> list_ispaytype = new ArrayList<String>();
                list_ispaytype.add("是");
                list_ispaytype.add("否");
                myDialogHelper_ispaytype.showListDialog(this, "是否按揭", list_ispaytype, (TextView) view.findViewById(R.id.activity_cpinfo_tv_paytype), new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
//                        if ("是".equals(list_ispaytype.get(position))) {
//                            cpData.setPaytype("true");
//                        } else {
//                            cpData.setPaytype("false");
//                        }
                        cpData.setPaytype(list_ispaytype.get(position));
                    }
                });
                break;


            case R.id.activity_cpinfo_rl_followeperson:
                MyDialogHelper myDialogHelper_flperson = new MyDialogHelper();
                final List<String> list_flperson = new ArrayList<String>();
                list_flperson.add("0");
                list_flperson.add("1");
                list_flperson.add("2");
                list_flperson.add("3");
                list_flperson.add("4");
                list_flperson.add("5");
                list_flperson.add("5人及以上");
                myDialogHelper_flperson.showListDialog(this, "随行人数", list_flperson, (TextView) view.findViewById(R.id.activity_cpinfo_tv_followeperson), new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        cpData.setFollowPeo(list_flperson.get(position));
                    }
                });
                break;
            case R.id.activity_cpinfo_rl_shopchannel:
                MyDialogHelper myDialogHelper_shopchannel = new MyDialogHelper();
                final List<UserPtInfoModel> list_channel = user.getList_Channel();
                if (list_channel != null && list_channel.size() > 0) {
                    List<String> list_shopchannel = userPtInfoModeltoStringList(list_channel);
                    myDialogHelper_shopchannel.showListDialog(this, "来店渠道", list_shopchannel, (TextView) view.findViewById(R.id.activity_cpinfo_tv_shopchannel), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            Log.d("ss", list_channel.get(position).toString());
                            cpData.setChannelId(list_channel.get(position).getID());
                            if ("网络".equals(list_channel.get(position).getName())) {
                                shop_channelId_wangluo = list_channel.get(position).getID();
                                shop_channelId_jiesaoren = "";
                                cpData.setOldCusCarNO("");
                                et_references_carnumber.setText("");
                                rl_info_more_channel.setVisibility(View.VISIBLE);
                                ll_references_carnumber.setVisibility(View.GONE);
                                ll_info_channel.setVisibility(View.VISIBLE);
                            } else if ("转介绍".equals(list_channel.get(position).getName())) {
                                shop_channelId_jiesaoren = list_channel.get(position).getID();
                                shop_channelId_wangluo = "";
                                cpData.setSourceChannelId("0");
                                tv_info_channel.setText("");
                                rl_info_more_channel.setVisibility(View.VISIBLE);
                                ll_references_carnumber.setVisibility(View.VISIBLE);
                                ll_info_channel.setVisibility(View.GONE);
                            } else {
                                cpData.setOldCusCarNO("");
                                cpData.setSourceChannelId("0");
                                et_references_carnumber.setText("");
                                tv_info_channel.setText("");
                                rl_info_more_channel.setVisibility(View.GONE);
                            }

                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_cpinfo_rl_intention_garde:
                MyDialogHelper myDialogHelper_garde = new MyDialogHelper();
                List<UserPtInfoModel> list_garde = user.getList_LevelNotDefeat();
                if (list_garde != null && list_garde.size() > 0) {
                    final List<String> listss_garde = userPtInfoModeltoStringList(list_garde);
                    myDialogHelper_garde.showListDialog(this, "意向等级", listss_garde, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_garde), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            cpData.setIntentLevel(listss_garde.get(position));
                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.activity_cpinfo_rl_intention_chexi:
                MyDialogHelper myDialogHelper_intention_chexi = new MyDialogHelper();
                if (list_chexi != null && list_chexi.size() > 0) {
                    final List<String> lists_chexi = userPtInfoModeltoStringList(list_chexi);
                    myDialogHelper_intention_chexi.showListDialog(this, "意向车系", lists_chexi, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chexi), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            chexi = list_chexi.get(position).getID();
                            cpData.setCarSeriesName(lists_chexi.get(position));
                            cpData.setCarModelName("");
                            tv_intention_chexin.setText("");
                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
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

                break;
            case R.id.activity_cpinfo_rl_city:
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

                break;
            case R.id.activity_cpinfo_rl_area:
                if (!TextUtils.isEmpty(city)) {
                    MyDialogHelper myDialogHelper_area = new MyDialogHelper();
//                    final List<String> list_area = Arrays.asList(mDistrictDatasMap.get(city));
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
                break;
            case R.id.activity_cpinfo_rl_intention_chexin:
                MyDialogHelper myDialogHelper_intention_chexin = new MyDialogHelper();
                List<UserPtInfoModel> userPtInfoModels = map_chexin.get(chexi);
                if (user.getList_CarModels() != null && user.getList_CarModels().size() > 0) {
                    if (userPtInfoModels != null && userPtInfoModels.size() > 0) {
                        final List<String> list_chexin = userPtInfoModeltoStringList(userPtInfoModels);
                        myDialogHelper_intention_chexin.showListDialog(this, "意向车型", list_chexin, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chexin), new MyDialogHelper.DialogCallBack() {
                            @Override
                            public void callBack(int position) {
                                cpData.setCarModelName(list_chexin.get(position));
                            }
                        });
                    } else {
                        Toast.makeText(CpCustomInfoActivity.this, "请选择车系", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.activity_cpinfo_rl_intention_chese:
                MyDialogHelper myDialogHelper_intention_chese = new MyDialogHelper();
                List<UserPtInfoModel> list_intention_chese = user.getList_CheckColorList();
                if (list_intention_chese != null && list_intention_chese.size() > 0) {
                    final List<String> list_chese = userPtInfoModeltoStringList(list_intention_chese);
                    myDialogHelper_intention_chese.showListDialog(this, "意向车色", list_chese, (TextView) view.findViewById(R.id.activity_cpinfo_tv_intention_chese), new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            cpData.setCarColor(list_chese.get(position));
                        }
                    });
                } else {
                    Toast.makeText(CpCustomInfoActivity.this, "没有数据", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.activity_cpinfo_rl_buycarjinyan:
                MyDialogHelper myDialogHelper_jinyan = new MyDialogHelper();
                final List<String> list_st_jinyan = new ArrayList<String>();
                list_st_jinyan.add("置换");
                list_st_jinyan.add("新购");
                myDialogHelper_jinyan.showListDialog(this, "购车经验", list_st_jinyan, (TextView) view.findViewById(R.id.activity_cpinfo_tv_buycarjinyan), new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        cpData.setIsChange(list_st_jinyan.get(position));
                        Log.d("ischange", position + 1 + "");
                    }
                });
                break;

            case R.id.activity_cpinfo_rl_infochannel:
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
                            tv_phone.setText(cpCustomer.getCustomerPhone());

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
                            et_references_carnumber.setText(cpCustomer.getOldCusCarNO());

                            if (!TextUtils.isEmpty(cpCustomer.getIsChangeID())) {
                                switch (cpCustomer.getIsChangeID()) {
                                    case "1":
                                        cpData.setIsChange("置换");
                                        tv_buycarjinyan.setText("置换");
                                        break;
                                    case "2":
                                        cpData.setIsChange("新购");
                                        tv_buycarjinyan.setText("新购");
                                        break;
                                }
                            }

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
                                if (user.getList_CheckColorList() != null && user.getList_CheckColorList().size() > 0) {
                                    for (UserPtInfoModel userPtInfoModel : user.getList_CheckColorList()) {
                                        if (userPtInfoModel.getID() != null && userPtInfoModel.getID().equals(cpCustomer.getCarColorID())) {
                                            cpData.setCarColor(userPtInfoModel.getName());
                                            tv_intention_chese.setText(userPtInfoModel.getName());
                                        }
                                    }
                                }
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
                                                        rl_info_more_channel.setVisibility(View.VISIBLE);
                                                        ll_references_carnumber.setVisibility(View.VISIBLE);
                                                        ll_info_channel.setVisibility(View.GONE);
                                                        break;
                                                    case "网络":
                                                        rl_info_more_channel.setVisibility(View.VISIBLE);
                                                        ll_references_carnumber.setVisibility(View.GONE);
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
            if (shop_channelId_wangluo.equals(cpData.getChannelId())) {
                if (TextUtils.isEmpty(cpData.getSourceChannelId()) || "0".equals(cpData.getSourceChannelId())) {
                    flag = false;
                    Toast.makeText(CpCustomInfoActivity.this, "未选择信息渠道", Toast.LENGTH_SHORT).show();
                    return flag;
                }
            } else if (shop_channelId_jiesaoren.equals(cpData.getChannelId())) {
                if (TextUtils.isEmpty(cpData.getOldCusCarNO())) {
                    flag = false;
                    Toast.makeText(CpCustomInfoActivity.this, "未填写转介绍人", Toast.LENGTH_SHORT).show();
                    return flag;
                }
            }
        } else {
            Toast.makeText(CpCustomInfoActivity.this, "未选择来店渠道", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }

        boolean flag_check = false;
        for (Boolean flges : cpCustomFocusAdapter.getList_checkinfo()) {
            if (flges) {
                flag_check = true;
            }
        }
        if (!flag_check) {
            Toast.makeText(CpCustomInfoActivity.this, "未勾选关注特征", Toast.LENGTH_SHORT).show();
            flag = false;
            return flag;
        }


        if (TextUtils.isEmpty(cpData.getIsDrive())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择是否试驾", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getFollowPeo())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择随行人数", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getIntentLevel())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择意向等级", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getCarSeriesName())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择意向车系", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getCarModelName())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择意向车型", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getUseageID()) || "0".equals(cpData.getUseageID())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择购买用途", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getIsChange())) {
            Toast.makeText(CpCustomInfoActivity.this, "置换/新购不能为空", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getTalkProcess())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写洽谈记录", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getLiveProvince())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写常住省", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getLiveCity())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写常住市", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getLiveArea())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写常住区", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getPaytype())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写是否按揭", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getQuoteSituation())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写报价情况", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getCarColor())) {
            Toast.makeText(CpCustomInfoActivity.this, "未填写意向车色", Toast.LENGTH_SHORT).show();
            flag = false;
        }


        return flag;
    }

    private boolean checkSaveDataNoPhone() {
        boolean flag = true;
        if (TextUtils.isEmpty(cpData.getCarSeriesName())) {
            Toast.makeText(CpCustomInfoActivity.this, "未选择意向车系", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (TextUtils.isEmpty(cpData.getTalkProcess())) {
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


}
