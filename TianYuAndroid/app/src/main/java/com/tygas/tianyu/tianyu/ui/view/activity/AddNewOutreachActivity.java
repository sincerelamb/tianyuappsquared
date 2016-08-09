package com.tygas.tianyu.tianyu.ui.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.data.UrlData;
import com.tygas.tianyu.tianyu.ui.adapter.CpCustomFocusAdapter;
import com.tygas.tianyu.tianyu.ui.model.CpCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.model.WaiActivity;
import com.tygas.tianyu.tianyu.utils.HttpUtilsHelper;
import com.tygas.tianyu.tianyu.utils.JsonParser;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.ProgressDialogHelper;
import com.tygas.tianyu.tianyu.utils.SystemBarUtils;
import com.tygas.tianyu.tianyu.utils.XmlParser;
import com.tygas.tianyu.tianyu.utils.XutilsRequest;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SJTY_YX on 2016/2/29.
 * 新增外展的activity
 */
public class AddNewOutreachActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private ImageView backImageView;//返回按钮
    private ImageView saveImageView;//保存按钮

    private EditText nameEditText;//客户姓名s
    private EditText phoneEditText;//客服电话
    private TextView sourceTextView;//客户来源
    private TextView provinceTextView;//常住省
    private TextView permanentTextView;//常住市
    private TextView countyTextView;  //常住县
    private TextView receptionDateTextView;//接待日期
    private TextView receptionActvTextViuew;//外展活动
    private TextView receptionStartTimeTextView;//接待开始时间
    private TextView receptionEndTimeTextView;//接待结束时间
    private TextView alongPersonNumberTextView;//随行人数
    private TextView storeChannelTextView;//来店渠道
    private TextView infoChannelTextView;//信息渠道
    private EditText turnToEditText;//转介绍人
    private TextView intentLevelTextView;//意向等级
    private TextView nextCallTimeTextView;//下次回访时间
    private TextView intentCarsTextView;//意向车系
    private TextView intentCarModleTextView;//意向车型
    private TextView intentCarColorTextView;//意向车色
    private TextView carUseTextView;//购车用途
    private TextView newChangeTextView;//购置新换
    private TextView mortgageTextView;//是否按揭
    private GridView featureGridView;//车辆特征
    private EditText talkProgressEditText;//洽谈记录

    private LinearLayout ll_provinceTextView;
    private LinearLayout ll_permanentTextView;
    private LinearLayout ll_countyTextView;
    private LinearLayout ll_receptionDateTextView;
    private LinearLayout ll_receptionActvTextViuew;
    private LinearLayout ll_receptionStartTimeTextView;
    private LinearLayout ll_receptionEndTimeTextView;
    private LinearLayout ll_alongPersonNumberTextView;
    private LinearLayout ll_storeChannelTextView;
    private LinearLayout ll_infoChannelTextView;
    private LinearLayout ll_intentLevelTextView;
    private LinearLayout ll_nextCallTimeTextView;
    private LinearLayout ll_intentCarsTextView;
    private LinearLayout ll_intentCarModleTextView;
    private LinearLayout ll_intentCarColorTextView;
    private LinearLayout ll_carUseTextView;
    private LinearLayout ll_newChangeTextView;
    private LinearLayout ll_mortgageTextView;


    private ProgressBar waiActivityProgressBar;//加载外展活动的时候 的progressbar


    private List<String> provinces;//省
    private Map<String, String[]> permanent;//市
    private Map<String, String[]> county;//县
    private String provinceName = "";
    private String perName = "";
    private String initprovinceName = "";
    private String initperName = "";
    private String initAre = "";


    private List<String> list_Provonce = new ArrayList<String>();
    private List<String> list_City = new ArrayList<String>();
    private List<String> list_District = new ArrayList<String>();
    private boolean isLoading;

    private DatePickerDialog datePickerDialog;//日期选择
    private TimePickerDialog timePickerDialog;//时间选择

    private ArrayList<String> alongNumber = new ArrayList<>();

    private String storeChannel = "";
    private String infoChannel = "";
    private User user;

    private CpCustomFocusAdapter adapter;

    private UserPtInfoModel cars; // 当前的意向车系
    private XmlParser xmlParser;

    private String[] USE = {"置换", "新购"};
    private String[] ANJIE = {"是", "否"};


    private ArrayList<WaiActivity> waiActivities = new ArrayList<>();//外展活动

    private static final String LOG_TAG = "AddNewOutreachActivity";
    private static final int DONTUSE_COLOR = Color.rgb(221, 221, 221);//不能选择的颜色
    private static final int CANUSE_COLOR = Color.WHITE;//可以选择的颜色

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SystemBarUtils.setSystemBarColor(this, "#327ECA");
        setContentView(R.layout.activity_addnewtreach);
        MyAppCollection myAppCollection = (MyAppCollection) getApplication();
        user = myAppCollection.getUser();
        initView();
        initData();
        loadDate();
    }//end onCreate

    private void initData() {
        receptionDateTextView.setText(getNowDate());
        nextCallTimeTextView.setText(getSpecifiedDayAfter(getNowDate()));
        jiedaiDate = getSpecifiedDayAfter(getNowDate());
        for (int i = 0; i < 5; i++) {
            alongNumber.add(i + "");
        }
        alongNumber.add("5人及以上");
        adapter = new CpCustomFocusAdapter(this, user.getList_Focus());
        featureGridView.setAdapter(adapter);

//        provinces = new ArrayList<>();
//        xmlParser = new XmlParser();
//        xmlParser.initProvinceDatas(this);
//        provinces = Arrays.asList(xmlParser.getmProvinceDatas());
//        permanent = xmlParser.getmCitisDatasMap();
//        county = xmlParser.getmDistrictDatasMap();
        loadWaiActivity();
    }

    private String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH) + 1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        String result = y + "-";
        result += m > 9 ? m + "-" : "0" + m + "-";
        result += d > 9 ? d : "0" + d;
        return result;
    }

    private void loadWaiActivity() {
        waiActivityProgressBar.setVisibility(View.VISIBLE);
        String time = (String) receptionDateTextView.getText();
        RequestParams requestParams = XutilsRequest.getWaiActivityParams(time);
        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.WAIACTIVITY_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                waiActivityProgressBar.setVisibility(View.GONE);
                Log.i(LOG_TAG, "[请求成功]" + responseInfo.result);
                if (responseInfo.result != null && responseInfo.result.length() > 0) {
                    try {
                        parseWaiActivity(responseInfo.result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//end if
            }

            @Override
            public void onFailure(HttpException e, String s) {
                waiActivityProgressBar.setVisibility(View.GONE);
            }
        });
    }//end loadWaiActivity

    private void parseWaiActivity(String result) throws JSONException {
        //{"status":"Success","message":"","data":"{\"CampaignList\":[{\"CampaignID\":1,\"CampaignName\":\"测试外展活动\"},{\"CampaignID\":2,\"CampaignName\":\"测试外展活动2\"},{\"CampaignID\":3,\"CampaignName\":\"测试外展活动3\"},{\"CampaignID\":4,\"CampaignName\":\"测试外展活动4\"}],\"totalrows\":0}"}
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            String data = jsonObject.getString("data");
            JSONObject dataobj = new JSONObject(data);
            if (!dataobj.isNull("CampaignList")) {
                JSONArray jsonArray = dataobj.getJSONArray("CampaignList");
                waiActivities.clear();
                if (jsonArray != null && jsonArray.length() > 0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        waiActivities.add(parseWaiModel(jsonArray.getJSONObject(i)));
                    }
                }
            }
        }
    }//end parseWaiActivity

    private WaiActivity parseWaiModel(JSONObject o) throws JSONException {
        WaiActivity waiActivity = new WaiActivity();
        waiActivity.setCampaignID(o.getInt("CampaignID"));
        waiActivity.setCampaignName(o.getString("CampaignName"));
        return waiActivity;
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addnewtreach_iv_back://点击了返回按钮
                //获取今日的活动
                finish();
                break;
            case R.id.activity_addnewtreach_iv_save://点击了保存按钮
                Log.i(LOG_TAG, "[购置新换]" + newChangeTextView.getText() + "[CheckBox的选择]" + getCheckBoxPar());

                saveInfoToNet();
                break;
            case R.id.activity_addnewtreach_ll_province://点击了常住省
                if (list_Provonce.size() > 0 && !isLoading) {
                    new MyDialogHelper().showListDialog(this, "请选择", list_Provonce, provinceTextView, new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            provinceName = list_Provonce.get(position);
                            permanentTextView.setText("请选择");
                            perName = "";
                            //((ViewGroup)permanentTextView.getParent()).setBackgroundColor(CANUSE_COLOR);
                            setParentColor(permanentTextView, CANUSE_COLOR);
                            countyTextView.setText("请选择");
                            setParentColor(countyTextView, DONTUSE_COLOR);
                            list_City.clear();
                            list_District.clear();
                            initProvince(provinceName, "", 2);
                        }
                    });
                } else {
                    Toast.makeText(AddNewOutreachActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.activity_addnewtreach_ll_permanent://点击了常住市
                if (provinceName != null && provinceName.length() > 1 && list_City != null && list_City.size() > 0 && !isLoading) {
                    new MyDialogHelper().showListDialog(this, "请选择", list_City, permanentTextView, new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            perName = list_City.get(position);
                            countyTextView.setText("请选择");
                            setParentColor(countyTextView, CANUSE_COLOR);
                            list_District.clear();
                            initProvince(provinceName, perName, 3);
                        }
                    });
                } else {
                    Toast.makeText(AddNewOutreachActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.activity_addnewtreach_ll_county://点击了常住县
                if (perName != null && perName.length() > 1 && null != list_District && list_District.size() > 0 && !isLoading) {
                    new MyDialogHelper().showListDialog(this, "请选择", list_District, countyTextView);
                } else {
                    Toast.makeText(AddNewOutreachActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_addnewtreach_ll_receptionDate://点击了外展接待日期
                if (!datePickerDialog.isAdded()) {
                    datePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                }
                break;
            case R.id.activity_addnewtreach_ll_receptionActv://点击了外展活动
                new MyDialogHelper().showListDialog(this, "请选择", getWaiStringsFromList(), receptionActvTextViuew);
                break;
            case R.id.activity_addnewtreach_ll_receptionStartTime://点击了接待开始时间
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String endtime = receptionEndTimeTextView.getText().toString();
                        if (endtime == null || "".equals(endtime) || endtime.contains("选择")) {
                            receptionStartTimeTextView.setText(hourOfDay + ":" + minute);
                        } else {
                            String time[] = endtime.split(":");
                            int h = Integer.valueOf(time[0]);
                            int m = Integer.valueOf(time[1]);
                            if (hourOfDay < h) {
                                receptionStartTimeTextView.setText(hourOfDay + ":" + minute);
                            } else if (hourOfDay == h && minute < m) {
                                receptionStartTimeTextView.setText(hourOfDay + ":" + minute);
                            } else {
                                receptionStartTimeTextView.setText("");
                                Toast.makeText(AddNewOutreachActivity.this, "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, 12, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                if (!timePickerDialog.isAdded()) {
                    timePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                }

                break;
            case R.id.activity_addnewtreach_ll_receptionEndTime://点击了接待结束时间
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

                        String startTime = receptionStartTimeTextView.getText().toString();
                        if (startTime == null || "".equals(startTime) || startTime.contains("选择")) {
                            receptionEndTimeTextView.setText(hourOfDay + ":" + minute);
                        } else {
                            String time[] = startTime.split(":");
                            int h = Integer.valueOf(time[0]);
                            int m = Integer.valueOf(time[1]);
                            Log.i(LOG_TAG, "[hourOfDay]" + hourOfDay + " [minute]" + minute + " [h]" + h + " [m]" + m);
                            if (hourOfDay > h) {
                                receptionEndTimeTextView.setText(hourOfDay + ":" + minute);
                            } else if (hourOfDay == h && minute > m) {
                                receptionEndTimeTextView.setText(hourOfDay + ":" + minute);
                            } else {
                                receptionEndTimeTextView.setText("");
                                Toast.makeText(AddNewOutreachActivity.this, "结束时间不能小于开始时间", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, 12, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                if (!timePickerDialog.isAdded()) {
                    timePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                }

                break;
            case R.id.activity_addnewtreach_ll_alongPersonNumber://点击了随行人数
                new MyDialogHelper().showListDialog(this, "请选择", alongNumber, alongPersonNumberTextView);
                break;
            case R.id.activity_addnewtreach_ll_storeChannel://点击了来电渠道
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_Channel()), storeChannelTextView, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        storeChannel = user.getList_Channel().get(position).getName();
                        infoChannelTextView.setText("");
                        turnToEditText.setText("");
                        receptionActvTextViuew.setFocusable(false);
                        if ("转介绍".equals(storeChannel)) {
                            Log.i(LOG_TAG, "[设置转介绍人 true]");
                            turnToEditText.setFocusable(true);
                            turnToEditText.setFocusableInTouchMode(true);
                            setParentColor(turnToEditText, CANUSE_COLOR);
                            turnToEditText.setHint("必填");
                            turnToEditText.setHintTextColor(getResources().getColor(R.color.appOrange));
                        } else {
                            Log.i(LOG_TAG, "[设置转介绍人 false]");
                            turnToEditText.setFocusable(false);
                            turnToEditText.setFocusableInTouchMode(false);
                            turnToEditText.setHintTextColor(DONTUSE_COLOR);
                            setParentColor(turnToEditText, DONTUSE_COLOR);
                            turnToEditText.setHint("");
                        }
                        if ("网络".equals(storeChannel)) {
                            setParentColor(infoChannelTextView, CANUSE_COLOR);
                            infoChannelTextView.setHint("必填");

                        } else {
                            setParentColor(infoChannelTextView, DONTUSE_COLOR);
                            infoChannelTextView.setHint("");
                        }
                    }
                });
                break;
            case R.id.activity_addnewtreach_ll_infoChannel://点击信息渠道
                if ("网络".equals(storeChannel)) {
                    //来店渠道为网络的时候才可以点击
                    new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_SourceChannel()), infoChannelTextView, new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            infoChannel = user.getList_SourceChannel().get(position).getName();

                        }
                    });
                }
                break;
            case R.id.activity_addnewtreach_ll_intentLevel://点击意向等级
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_LevelNotDefeat()), intentLevelTextView, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        intentLeavel = intentLevelTextView.getText().toString();
                        if ("o".equalsIgnoreCase(intentLevelTextView.getText().toString())) {
                            nextCallTimeTextView.setText("");
                        } else {
                            nextCallTimeTextView.setText(jiedaiDate);
                        }
                    }
                });
                break;
            case R.id.activity_addnewtreach_ll_nextCallTime://下次回访时间
                //datePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                break;
            case R.id.activity_addnewtreach_ll_intentCars://意向车系
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_CarSeries()), intentCarsTextView, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        cars = user.getList_CarSeries().get(position);
                        intentCarModleTextView.setText("请选择");
                    }
                });
                break;
            case R.id.activity_addnewtreach_ll_intentCarModle://意向车型
                if (cars != null)
                    new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(getCarModleFromId(cars.getID())), intentCarModleTextView);
                break;
            case R.id.activity_addnewtreach_ll_intentCarColor://意向车色
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_CheckColorList()), intentCarColorTextView);
                break;
            case R.id.activity_addnewtreach_ll_carUse://购车用途
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_Useage()), carUseTextView);
                break;
            case R.id.activity_addnewtreach_ll_newChange://购置新换
                new MyDialogHelper().showListDialog(this, "请选择", Arrays.asList(USE), newChangeTextView);
                break;
            case R.id.activity_addnewtreach_ll_mortgage://是否按揭
                new MyDialogHelper().showListDialog(this, "请选择", Arrays.asList(ANJIE), mortgageTextView);
                break;
        }
    }  //end onclick


    private void setParentColor(View v, int color) {
        ((View) v.getParent()).setBackgroundColor(color);
    }

    private void saveInfoToNet() {
        String name = nameEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String sorce = "外展";
        String province = getStrFromTextView(provinceTextView);//省
        String permanent = getStrFromTextView(permanentTextView);//市
        String county = getStrFromTextView(countyTextView);//县
        String receptionDate = getStrFromTextView(receptionDateTextView);//接待时间
        int receptionActv = getWaiActivtiyIdByName(getStrFromTextView(receptionActvTextViuew));//外展活动
        String receptionStart = getStrFromTextView(receptionStartTimeTextView);//接待开始时间
        String receptionEnd = getStrFromTextView(receptionEndTimeTextView);//接待结束时间
        String alongPersonNumber = getStrFromTextView(alongPersonNumberTextView);//随行人数
        String storeChannel = getIdFromName(getStrFromTextView(storeChannelTextView), user.getList_Channel());//来店渠道
        String infoChannel = getIdFromName(getStrFromTextView(infoChannelTextView), user.getList_SourceChannel());//信息渠道
        String turnto = turnToEditText.getText().toString();//转介绍人
        String intentLevel = getIdFromName(getStrFromTextView(intentLevelTextView), user.getList_LevelNotDefeat());//意向等级
        String nextCallTime = getStrFromTextView(nextCallTimeTextView);//下次回访时间
        String intentCars = getIdFromName(getStrFromTextView(intentCarsTextView), user.getList_CarSeries());//意向车系
        String intentCarModle = getIdFromName(getStrFromTextView(intentCarModleTextView), user.getList_CarModels());// 意向车型
        String intentCarColor = getIdFromName(getStrFromTextView(intentCarColorTextView), user.getList_CheckColorList());//意向车色
        String carUse = getIdFromName(getStrFromTextView(carUseTextView), user.getList_Useage());//购车用途
        String newChange = "置换".equals(getStrFromTextView(newChangeTextView)) ? "1" : "".equals(getStrFromTextView(newChangeTextView)) ? "" : "0";//购置新换
        String mortgage = getStrFromTextView(mortgageTextView);//是否按揭
        String feat = getCheckBoxPar();//车辆特征
        String talkPro = talkProgressEditText.getText().toString();//洽谈记录
        /*Log.i(LOG_TAG,"[填写的结果] name "+name+" phone "+phone+"   sorce "+sorce+"  province "+province);
        Log.i(LOG_TAG,"[填写的结果] permanent "+permanent+"  county "+county+" receptionDate "+receptionDate+" receptionActv "+receptionActv);
        Log.i(LOG_TAG,"[填写的结果] receptionStart "+receptionStart+"  receptionEnd "+receptionEnd+"  alongPersonNumber "+alongPersonNumber);
        Log.i(LOG_TAG,"[填写的结果] storeChannel "+storeChannel+"  infoChannel "+infoChannel+"  turnto "+turnto+"  intentLevel "+intentLevel);
        Log.i(LOG_TAG,"[填写的结果] nextCallTime "+nextCallTime+"  intentCars "+intentCars+"  intentCarModle "+intentCarModle+"  intentCarColor "+intentCarColor);
        Log.i(LOG_TAG,"[填写的结果] carUse "+carUse+"  newChange "+newChange+"  mortgage "+mortgage+"  feat "+feat+"  talkPro "+talkPro);*/
        if (validateNotEmpty(name)) {
            showToast("客户姓名不能为空");
            return;
        }
        if (validateNotEmpty(phone)) {
            showToast("客户电话不能为空");
            return;
        }
        if (validateNotEmpty(receptionDate)) {
            showToast("接待日期不能为空");
            return;
        }
        if (validateNotEmpty(getStrFromTextView(receptionActvTextViuew))) {
            showToast("外展活动不能为空");
            return;
        }
        if (validateNotEmpty(receptionStart)) {
            showToast("接待开始时间不能为空");
            return;
        }
        if (validateNotEmpty(receptionEnd)) {
            showToast("接待结束时间不能为空");
            return;
        }
        if (validateNotEmpty(alongPersonNumber)) {
            showToast("随行人数不能为空");
            return;
        }
        if (validateNotEmpty(storeChannel)) {
            showToast("来店渠道不能为空");
            return;
        }
        if (getStrFromTextView(storeChannelTextView).equals("网络") && validateNotEmpty(infoChannel)) {
            showToast("信息渠道不能为空");
            return;
        }
        if (getStrFromTextView(storeChannelTextView).equals("转介绍") && validateNotEmpty(turnto)) {
            showToast("请输入转介绍人");
            return;
        }
        if (validateNotEmpty(intentLevel)) {
            showToast("意向等级不能为空");
            return;
        }
        if (validateNotEmpty(intentCars)) {
            showToast("意向车系不能为空");
            return;
        }
        if (validateNotEmpty(carUse)) {
            showToast("购车用途不能为空");
            return;
        }
        if (validateNotEmpty(newChange)) {
            showToast("购置/新换不能为空");
            return;
        }
        if (validateNotEmpty(mortgage)) {
            showToast("是否按揭不能为空");
            return;
        }
        if (validateNotEmpty(feat)) {
            showToast("关注特征不能为空");
            return;
        }
        if (validateNotEmpty(talkPro)) {
            showToast("洽谈记录不能为空");
            return;
        }
        //提交数据
        RequestParams requestParams = XutilsRequest.getWaiUpdateParams(name, phone, sorce,
                province, permanent, county,
                receptionDate, String.valueOf(receptionActv), receptionStart, receptionEnd,
                alongPersonNumber, storeChannel, infoChannel, turnto,
                intentLevel, nextCallTime,
                intentCars, intentCarModle, intentCarColor,
                carUse, newChange, mortgage,
                feat, talkPro, user.getEmpId());

        HttpUtils httpUtils = HttpUtilsHelper.getInstance();
        ProgressDialogHelper.showProgressDialog(this, "数据提交中..");
        httpUtils.send(HttpRequest.HttpMethod.POST, UrlData.WAIACTIVITY_SAVE_URL, requestParams, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i(LOG_TAG, "[访问成功]" + responseInfo.result);
                if (responseInfo.result != null && responseInfo.result.length() > 0) {
                    try {
                        parseResult(responseInfo.result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(AddNewOutreachActivity.this, "请求错误" + s, Toast.LENGTH_SHORT).show();
                ProgressDialogHelper.dismissProgressDialog();
            }
        });

    }//end  saveInfoToNet

    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    private void parseResult(String result) throws JSONException {
        //{"status":"Success","message":"","data":"{\"Status\":\"Success\",\"Message\":\"回访保存成功!\",\"AddCusInfo\":\"电话10086为admin正在跟踪的意向客户,本次外展记录会记录在该客户的跟踪历史中\"}"}
        JSONObject jsonObject = new JSONObject(result);
        String status = jsonObject.getString("status");
        if ("Success".equals(status)) {
            JSONObject obj = new JSONObject(jsonObject.getString("data"));
            status = obj.getString("Status");
            if ("Success".equals(status)) {
                Toast.makeText(this, " " + obj.getString("Message") + obj.getString("AddCusInfo"), Toast.LENGTH_SHORT).show();
                //finish();
                isdismissdialog = false;
                clearData();
            } else {
                Toast.makeText(this, " " + obj.getString("Message"), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, " " + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
        }
        if(isdismissdialog){
            ProgressDialogHelper.dismissProgressDialog();
        }
    }

    /**
     * 提交数据后不退出 清除数据
     */
    private void clearData() {
        nameEditText.setText("");
        phoneEditText.setText("");

        provinceTextView.setText(initprovinceName);
        provinceName = initprovinceName;
        initProvince(initprovinceName, "", 2);

        permanentTextView.setText(initperName);
        perName = initperName;
        initProvince(initprovinceName,initperName,3);
//        setParentColor(permanentTextView, DONTUSE_COLOR);
        countyTextView.setText("");
//        setParentColor(countyTextView, DONTUSE_COLOR);

        receptionActvTextViuew.setText("");
        receptionStartTimeTextView.setText("");
        receptionEndTimeTextView.setText("");
        alongPersonNumberTextView.setText("");
        storeChannelTextView.setText("");
        infoChannelTextView.setText("");
        setParentColor(infoChannelTextView, DONTUSE_COLOR);
        turnToEditText.setText("");
        turnToEditText.setFocusable(false);
        turnToEditText.setHintTextColor(DONTUSE_COLOR);
        intentLevelTextView.setText("");
        //nextCallTimeTextView.setText("请选择");
        intentCarsTextView.setText("");
        intentCarModleTextView.setText("");
        intentCarColorTextView.setText("");
        carUseTextView.setText("");
        newChangeTextView.setText("");
        mortgageTextView.setText("");
        for (int i = 0; i < featureGridView.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) ((ViewGroup) featureGridView.getChildAt(i)).getChildAt(0);
            checkBox.setChecked(false);
        }
        talkProgressEditText.setText("");
    }

    private int getWaiActivtiyIdByName(String name) {
        for (int i = 0; i < waiActivities.size(); i++) {
            if (waiActivities.get(i).getCampaignName().equals(name)) {
                return waiActivities.get(i).getCampaignID();
            }
        }
        return 0;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 验证文本是否为空
     *
     * @param str
     * @return
     */
    private boolean validateNotEmpty(String str) {
        if (str == null || str.equals("") || str.length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过 level字符串得到 intentLevel的id
     *
     * @param name
     * @return
     */
    private String getIdFromName(String name, List<UserPtInfoModel> list) {
        //List<UserPtInfoModel> levels = user.getList_CustomerLevel();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i).getID();
            }
        }
        return "";
    }

    private String getStrFromTextView(TextView v) {
        String re = (String) v.getText();
        if (re == null || "请选择".equals(re) || "必填".equals(re)) {
            return "";
        } else {
            return re;
        }
    }

    private List<String> getWaiStringsFromList() {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < waiActivities.size(); i++) {
            strings.add(waiActivities.get(i).getCampaignName());
        }
        return strings;
    }

    /**
     * 得到 GridView的选择 的项目
     *
     * @return
     */
    private String getCheckBoxPar() {
        String result = "";
        List<UserPtInfoModel> temp = user.getList_Focus();
        for (int i = 0; i < featureGridView.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) featureGridView.getChildAt(i);
            TextView tv = (TextView) viewGroup.getChildAt(1);
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(0);
            if (checkBox.isChecked()) {
                String str = (String) tv.getText();
                for (int j = 0; j < temp.size(); j++) {
                    if (temp.get(j).getName().equals(str)) {
                        result += temp.get(j).getID() + "&";
                    }
                }
            }
        }
        return result.endsWith("&") ? result.substring(0, result.length() - 1) : result;
    }

    /**
     * 通过id获取到 对应的车型
     *
     * @param id 车系的id
     * @return
     */
    private List<UserPtInfoModel> getCarModleFromId(String id) {
        ArrayList<UserPtInfoModel> model = new ArrayList<>();
        for (int i = 0; i < user.getList_CarModels().size(); i++) {
            if (user.getList_CarModels().get(i).getPID().equals(id)) {
                model.add(user.getList_CarModels().get(i));
            }
        }
        return model;
    }

    private List<String> getNamesFromUserPtInfoModel(List<UserPtInfoModel> models) {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            names.add(models.get(i).getName());
        }
        return names;
    }


    /**
     * 通过位置获取到 市 的信息
     *
     * @param provinceName 省的位置
     * @return
     */
    private List<String> getPermentsNameFromPosition(String provinceName) {
        return Arrays.asList(permanent.get(provinceName));
    }

    private String jiedaiDate;
    private String intentLeavel = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if ("activity_addnewtreach_tv_receptionDate".equals(view.getTag())) {//外展接待日期

            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            jiedaiDate = getSpecifiedDayAfter(date);
            receptionDateTextView.setText(date);
            receptionActvTextViuew.setText("");
            waiActivities.clear();
            if ("O".equals(intentLeavel)) {
                nextCallTimeTextView.setText("");
            } else {
                nextCallTimeTextView.setText(getSpecifiedDayAfter(date));
            }
            loadWaiActivity();
        } else if ("activity_addnewtreach_tv_nextCallTime".equals(view.getTag())) {
            nextCallTimeTextView.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }
    }


   /* @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Log.i(LOG_TAG, "[view.getContentDescription() ]"+view.getContentDescription());
        if(view.getTag().equals("activity_addnewtreach_tv_receptionStartTime")){//接待开始时间
            receptionStartTimeTextView.setText(hourOfDay+":"+minute);
        }else if(view.getTag().equals("activity_addnewtreach_tv_receptionEndTime")){//接待结束时间
            receptionEndTimeTextView.setText(hourOfDay+":"+minute);
        }
    }*/


    /**
     * 通过省  和市的位置 获取到  县的名字
     *
     * @param provinceName 省的位置
     * @param perName      市的位置
     * @return
     */
    private List<String> getCountyFromPosition(String provinceName, String perName) {
        if (county.get(perName) != null) {
            return Arrays.asList(county.get(perName));
        } else {
            return new ArrayList<>();
        }

    }


    private boolean isdismissdialog = true;

    private void loadDate() {
        ProgressDialogHelper.showProgressDialog(this, "正在加载默认数据...");
        Log.d("IP", UrlData.PCINFOLIST_URL);
        HttpUtilsHelper.getInstance().send(HttpRequest.HttpMethod.POST, UrlData.PCINFOLIST_URL, XutilsRequest.getCpCustomerList("1",
                        "", "", "", "", 1, 10, "", ""),
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> objectResponseInfo) {

                        Log.d("cpresultsssssss", objectResponseInfo.result);
                        Bundle bundle = JsonParser.cpCustomersParser(objectResponseInfo.result);
                        List<CpCustomer> cpCustomerList = (List<CpCustomer>) bundle.get("list");
                        Log.d("cpresultlist", cpCustomerList.toString());
                        if (null != cpCustomerList && cpCustomerList.size() > 0) {
                            CpCustomer cpCustomer = cpCustomerList.get(0);
                            Log.d("cpresult", cpCustomer.toString());
                            //      if (!TextUtils.isEmpty(cpCustomer.getLiveProvince()) && permanent.get(cpCustomer.getLiveProvince()) != null) {
                            if (!TextUtils.isEmpty(cpCustomer.getLiveProvince())) {
                                initprovinceName = cpCustomer.getLiveProvince();
                                Log.d("cpresult", cpCustomer.toString());
                                isdismissdialog = false;
                                provinceTextView.setText(cpCustomer.getLiveProvince());//省
                                provinceName = cpCustomer.getLiveProvince();
                                setParentColor(permanentTextView, CANUSE_COLOR);
                                initProvince(provinceName, "", 2);
                                if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
                                    initperName = cpCustomer.getLiveCity();
                                    permanentTextView.setText(cpCustomer.getLiveCity());
                                    perName = cpCustomer.getLiveCity();
                                    setParentColor(countyTextView, CANUSE_COLOR);
                                    initProvince(provinceName, perName, 3);
//                                    if(!TextUtils.isEmpty(cpCustomer.getLiveArea())){
////                                        countyTextView.setText(cpCustomer.getLiveArea());
//                                    }
                                }

                            }


                            if (isdismissdialog) {
                                ProgressDialogHelper.dismissProgressDialog();
                            }
                        }


//                            if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
//                                String[] strings = permanent.get(cpCustomer.getLiveProvince());
//                                if (strings != null) {
//                                    for (int i = 0; i < strings.length; i++) {
//                                        if (cpCustomer.getLiveCity().equals(strings[i])) {
//                                            permanentTextView.setText(cpCustomer.getLiveCity());
//                                            perName = cpCustomer.getLiveCity();
//                                            setParentColor(countyTextView, CANUSE_COLOR);
//
//                                            if (!TextUtils.isEmpty(cpCustomer.getLiveArea())) {
//                                                String[] strings1 = county.get(cpCustomer.getLiveArea());
//                                                if (strings1 != null) {
//                                                    for (int j = 0; j < strings1.length; j++) {
//                                                        if (cpCustomer.getLiveArea().equals(strings1[j])) {
//                                                            countyTextView.setText(cpCustomer.getLiveArea());
//                                                        } else {
//                                                            continue;
//                                                        }
//                                                    }
//                                                }
//                                            }
//
//                                        } else {
//                                            continue;
//                                        }
//
//                                    }
//                                }
//                            }
                        //        }


//                        if (!TextUtils.isEmpty(cpCustomer.getLiveProvince())) {
//                            provinceTextView.setText(cpCustomer.getLiveProvince());//省
//                            provinceName = cpCustomer.getLiveProvince();
//                            setParentColor(permanentTextView, CANUSE_COLOR);
//                            if (!TextUtils.isEmpty(cpCustomer.getLiveCity())) {
//                                permanentTextView.setText(cpCustomer.getLiveCity());
//                                perName = cpCustomer.getLiveCity();
//                                setParentColor(countyTextView, CANUSE_COLOR);
//                            }
//                        }

                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        if (isdismissdialog) {
                            ProgressDialogHelper.dismissProgressDialog();
                        }
                        Toast.makeText(AddNewOutreachActivity.this, "网络请求出错，请检查网络", Toast.LENGTH_SHORT).show();

                    }
                });
        initProvince("", "", 1);
    }


    /**
     * 初始化控件
     */
    private void initView() {
        backImageView = (ImageView) findViewById(R.id.activity_addnewtreach_iv_back);
        backImageView.setOnClickListener(this);
        saveImageView = (ImageView) findViewById(R.id.activity_addnewtreach_iv_save);
        saveImageView.setOnClickListener(this);

        nameEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_name);
        phoneEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_phone);
        sourceTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_source);
        provinceTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_province);
        //   provinceTextView.setOnClickListener(this);

        permanentTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_permanent);
        //   permanentTextView.setOnClickListener(this);

        countyTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_county);
        //    countyTextView.setOnClickListener(this);

        receptionDateTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionDate);
        //    receptionDateTextView.setOnClickListener(this);

        receptionActvTextViuew = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionActv);
        //   receptionActvTextViuew.setOnClickListener(this);

        receptionStartTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionStartTime);
        //    receptionStartTimeTextView.setOnClickListener(this);

        receptionEndTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionEndTime);
        //    receptionEndTimeTextView.setOnClickListener(this);

        alongPersonNumberTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_alongPersonNumber);
        //   alongPersonNumberTextView.setOnClickListener(this);

        storeChannelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_storeChannel);
        //   storeChannelTextView.setOnClickListener(this);

        infoChannelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_infoChannel);
        //   infoChannelTextView.setOnClickListener(this);

        turnToEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_turnTo);
        turnToEditText.setFocusable(false);
        turnToEditText.setFocusableInTouchMode(false);

        intentLevelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentLevel);
        //   intentLevelTextView.setOnClickListener(this);

        nextCallTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_nextCallTime);
        //   nextCallTimeTextView.setOnClickListener(this);

        intentCarsTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCars);
        //   intentCarsTextView.setOnClickListener(this);

        intentCarModleTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCarModle);
        //   intentCarModleTextView.setOnClickListener(this);

        intentCarColorTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCarColor);
        //  intentCarColorTextView.setOnClickListener(this);

        carUseTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_carUse);
//
        newChangeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_newChange);
        //    newChangeTextView.setOnClickListener(this);

        mortgageTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_mortgage);
        //     mortgageTextView.setOnClickListener(this);
        featureGridView = (GridView) findViewById(R.id.activity_addnewtreach_gv_feature);
        talkProgressEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_talkProgress);


        ll_provinceTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_province);
        ll_provinceTextView.setOnClickListener(this);
        ll_permanentTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_permanent);
        ll_permanentTextView.setOnClickListener(this);
        ll_countyTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_county);
        ll_countyTextView.setOnClickListener(this);
        ll_receptionDateTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_receptionDate);
        ll_receptionDateTextView.setOnClickListener(this);
        ll_receptionActvTextViuew = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_receptionActv);
        ll_receptionActvTextViuew.setOnClickListener(this);
        ll_receptionStartTimeTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_receptionStartTime);
        ll_receptionStartTimeTextView.setOnClickListener(this);
        ll_receptionEndTimeTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_receptionEndTime);
        ll_receptionEndTimeTextView.setOnClickListener(this);
        ll_alongPersonNumberTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_alongPersonNumber);
        ll_alongPersonNumberTextView.setOnClickListener(this);
        ll_storeChannelTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_storeChannel);
        ll_storeChannelTextView.setOnClickListener(this);
        ll_infoChannelTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_infoChannel);
        ll_infoChannelTextView.setOnClickListener(this);
        ll_intentLevelTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_intentLevel);
        ll_intentLevelTextView.setOnClickListener(this);
        ll_nextCallTimeTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_nextCallTime);
        ll_nextCallTimeTextView.setOnClickListener(this);
        ll_intentCarsTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_intentCars);
        ll_intentCarsTextView.setOnClickListener(this);
        ll_intentCarModleTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_intentCarModle);
        ll_intentCarModleTextView.setOnClickListener(this);
        ll_intentCarColorTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_intentCarColor);
        ll_intentCarColorTextView.setOnClickListener(this);
        ll_carUseTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_carUse);
        ll_carUseTextView.setOnClickListener(this);
        ll_newChangeTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_newChange);
        ll_newChangeTextView.setOnClickListener(this);
        ll_mortgageTextView = (LinearLayout) findViewById(R.id.activity_addnewtreach_ll_mortgage);
        ll_mortgageTextView.setOnClickListener(this);


        waiActivityProgressBar = (ProgressBar) findViewById(R.id.activity_addnewtreach_pb_waiactivity);
        waiActivityProgressBar.setVisibility(View.GONE);
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = DatePickerDialog.newInstance(this, y, m, d);
        datePickerDialog.setAccentColor(Color.rgb(50, 126, 202));//327ECA
    }//end initView


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
                if (!isdismissdialog) {
                    ProgressDialogHelper.dismissProgressDialog();
                    isdismissdialog = true;
                }
            }
        });
    }
}
