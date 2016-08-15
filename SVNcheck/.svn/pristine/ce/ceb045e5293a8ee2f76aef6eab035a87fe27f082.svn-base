package com.tygas.tianyu.tianyu.ui.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.ui.adapter.CpCustomFocusAdapter;
import com.tygas.tianyu.tianyu.ui.model.County;
import com.tygas.tianyu.tianyu.ui.model.Permanent;
import com.tygas.tianyu.tianyu.ui.model.Province;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.utils.MyDialogHelper;
import com.tygas.tianyu.tianyu.utils.XmlParser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by SJTY_YX on 2016/2/29.
 * 新增外展的activity
 *
 *
 */
public class AddNewOutreach extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener{

    private ImageView backImageView;//返回按钮
    private ImageView saveImageView;//保存按钮

    private EditText nameEditText;//客户姓名s
    private EditText phoneEditText;//客服电话
    private TextView sourceTextView;//客户来源
    private TextView provinceTextView;//常住省
    private TextView permanentTextView;//常住市
    private TextView countyTextView;//常住县
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

    private ArrayList<Province> provinces;//省
    private int provincePosition = -1;//省的位置
    private int perPosition = -1;//市的位置

    private DatePickerDialog datePickerDialog;//日期选择
    private TimePickerDialog timePickerDialog;//时间选择

    private ArrayList<String> alongNumber = new ArrayList<>();

    private String storeChannel = "网络";
    private String infoChannel = "转介绍";
    private User user;

    private CpCustomFocusAdapter adapter;

    private UserPtInfoModel cars; // 当前的意向车系


    private static final String LOG_TAG = "AddNewOutreach";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewtreach);
        MyAppCollection myAppCollection = (MyAppCollection) getApplication();
        user = myAppCollection.getUser();
       /* user.setEmpId("1");
        user.setEmpName("test");
        ArrayList<UserPtInfoModel> userPtInfoModels = new ArrayList<>();
        for(int i=0;i<10;i++){
            UserPtInfoModel model = new UserPtInfoModel();
            model.setName("来店渠道_"+i);
            userPtInfoModels.add(model);
        }
        user.setList_Channel(userPtInfoModels);
        user.getList_Channel();//来店渠道
        user.getList_SourceChannel();//信息渠道
        user.getList_CustomerLevel();//意向等级
        user.getList_CarSeries();//意向车系
        user.getList_CarModels();//意向车型
        user.getList_CheckColorList();//意向车色
        user.getList_Useage();//购车用途*/
        Log.i(LOG_TAG, "[意向车系]" + user.getList_CarSeries());  // id --> carmodels--> PID
        Log.i(LOG_TAG, "[意向车型]" + user.getList_CarModels());



        initView();
        initData();
    }//end onCreate

    private void initData() {
        receptionDateTextView.setText(getNowDate());
        for(int i=1;i<5;i++){
            alongNumber.add(i+"");
        }
        alongNumber.add("5人及5人以上");

        adapter = new CpCustomFocusAdapter(this,user.getList_Focus());

        featureGridView.setAdapter(adapter);
        provinces = new ArrayList<>();
        XmlParser xmlParser = new XmlParser();
        xmlParser.initProvinceDatas(this);

        for(int i=0;i<10;i++){
            Province province = new Province();
            province.setName("省_"+i);
            ArrayList<Permanent> permanents = new ArrayList<>();
            for(int j=0;j<10;j++){
                Permanent permanent = new Permanent();
                permanent.setName("市_"+i+"_"+j);
                ArrayList<County> counties = new ArrayList<>();
                for(int k=0;k<10;k++){
                    County county = new County();
                    county.setName("县_"+i+"_"+j+"_"+k);
                    counties.add(county);
                }
                permanent.setCounties(counties);
                permanents.add(permanent);
            }
            province.setPermanents(permanents);
            provinces.add(province);
        }//end for

    }

    private String getNowDate(){
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH)+1;
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        return y+"-"+m+"-"+d;
    }

    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_addnewtreach_iv_back://点击了返回按钮

                break;
            case R.id.activity_addnewtreach_iv_save://点击了保存按钮

                break;
            /*default:
                Object tag = v.getTag();
                if(tag != null){
                    String t = (String) tag;
                    Toast.makeText(this,"[点击事件  tag ]  "+t,Toast.LENGTH_SHORT).show();
                }
                break;*/
            case R.id.activity_addnewtreach_tv_province://点击了常住省
                new MyDialogHelper().showListDialog(this, "请选择", getProvincesName(), (TextView) v, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        provincePosition = position;
                        perPosition = -1;
                        permanentTextView.setText("请选择");
                        countyTextView.setText("请选择");
                    }
                });
                break;
            case R.id.activity_addnewtreach_tv_permanent://点击了常住市
                if(provincePosition >= 0)
                new MyDialogHelper().showListDialog(this,"请选择",getPermentsNameFromPosition(provincePosition), (TextView) v,new MyDialogHelper.DialogCallBack(){

                    @Override
                    public void callBack(int position) {
                        perPosition = position;
                        countyTextView.setText("请选择");
                    }
                });
                break;
            case R.id.activity_addnewtreach_tv_county://点击了常住县
                if(perPosition >= 0 ){
                    new MyDialogHelper().showListDialog(this,"请选择",getCountyFromPosition(provincePosition,perPosition), (TextView) v);
                }
                break;
            case R.id.activity_addnewtreach_tv_receptionDate://点击了外展接待日期
                datePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                break;
            case R.id.activity_addnewtreach_tv_receptionActv://点击了外展活动
                break;
            case R.id.activity_addnewtreach_tv_receptionStartTime://点击了接待开始时间
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        receptionStartTimeTextView.setText(hourOfDay+":"+minute);
                    }
                }, 12, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                timePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                break;
            case R.id.activity_addnewtreach_tv_receptionEndTime://点击了接待结束时间
                timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        receptionEndTimeTextView.setText(hourOfDay+":"+minute);
                    }
                }, 12, 12, 12, true);
                timePickerDialog.setAccentColor(Color.rgb(50, 126, 202));
                timePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                break;
            case R.id.activity_addnewtreach_tv_alongPersonNumber://点击了随行人数
                new MyDialogHelper().showListDialog(this,"请选择",alongNumber, (TextView) v);
                break;
            case R.id.activity_addnewtreach_tv_storeChannel://点击了来电渠道
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_Channel()), (TextView) v, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        storeChannel = user.getList_Channel().get(position).getName();
                    }
                });
                break;
            case R.id.activity_addnewtreach_tv_infoChannel://点击信息渠道
                if("网络".equals(storeChannel)){
                    //来店渠道为网络的时候才可以点击
                    new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_SourceChannel()), (TextView) v, new MyDialogHelper.DialogCallBack() {
                        @Override
                        public void callBack(int position) {
                            infoChannel = user.getList_SourceChannel().get(position).getName();
                        }
                    });
                }
                break;
            case R.id.activity_addnewtreach_tv_intentLevel://点击意向等级
                new MyDialogHelper().showListDialog(this,"请选择",getNamesFromUserPtInfoModel(user.getList_CustomerLevel()), (TextView) v);
                break;
            case R.id.activity_addnewtreach_tv_nextCallTime://下次回访时间
                datePickerDialog.show(getFragmentManager(), String.valueOf(v.getTag()));
                break;
            case R.id.activity_addnewtreach_tv_intentCars://意向车系
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_CarSeries()), (TextView) v, new MyDialogHelper.DialogCallBack() {
                    @Override
                    public void callBack(int position) {
                        cars = user.getList_CarSeries().get(position);
                        intentCarModleTextView.setText("请选择");
                    }
                });
                break;
            case R.id.activity_addnewtreach_tv_intentCarModle://意向车型
                if(cars != null)
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(getCarModleFromId(cars.getID())), (TextView) v);
                break;
            case R.id.activity_addnewtreach_tv_intentCarColor://意向车色
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_CheckColorList()), (TextView) v);
                break;
            case R.id.activity_addnewtreach_tv_carUse://购车用途
                new MyDialogHelper().showListDialog(this, "请选择", getNamesFromUserPtInfoModel(user.getList_Useage()), (TextView) v);
                break;
        }
    }  //end onclick


    /**
     * 通过id获取到 对应的车型
     *
     * @param id 车系的id
     * @return
     */
    private List<UserPtInfoModel> getCarModleFromId(String id){
        ArrayList<UserPtInfoModel> model = new ArrayList<>();
        for(int i=0;i<user.getList_CarModels().size();i++){
            if(user.getList_CarModels().get(i).getPID().equals(id)){
                model.add(user.getList_CarModels().get(i));
            }
        }
        return model;
    }

    private List<String> getNamesFromUserPtInfoModel(List<UserPtInfoModel> models){
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<models.size();i++){
            names.add(models.get(i).getName());
        }
        return names;
    }

    private List<String> getProvincesName() {
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<provinces.size();i++){
            names.add(provinces.get(i).getName());
        }
        return names;
    }

    /**
     * 通过位置获取到 市 的信息
     * @param position 省的位置
     * @return
     */
    private List<String> getPermentsNameFromPosition(int position){
        Province province = provinces.get(position);
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<province.getPermanents().size();i++){
            names.add(province.getPermanents().get(i).getName());
        }
        return names;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(view.getTag().equals("activity_addnewtreach_tv_receptionDate")){//外展接待日期
            receptionDateTextView.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }else if("activity_addnewtreach_tv_nextCallTime".equals(view.getTag())){
            nextCallTimeTextView.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
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
     *通过省  和市的位置 获取到  县的名字
     *
     * @param proPosition  省的位置
     * @param perposition  市的位置
     * @return
     */
    private List<String> getCountyFromPosition(int proPosition,int perposition){
        Province province = provinces.get(proPosition);
        Permanent permanent = province.getPermanents().get(perposition);
        ArrayList<String> names = new ArrayList<>();
        for(int i=0;i<permanent.getCounties().size();i++){
            names.add(permanent.getCounties().get(i).getName());
        }
        return names;
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
        provinceTextView.setOnClickListener(this);

        permanentTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_permanent);
        permanentTextView.setOnClickListener(this);

        countyTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_county);
        countyTextView.setOnClickListener(this);

        receptionDateTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionDate);
        receptionDateTextView.setOnClickListener(this);

        receptionActvTextViuew = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionActv);
        receptionActvTextViuew.setOnClickListener(this);

        receptionStartTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionStartTime);
        receptionStartTimeTextView.setOnClickListener(this);

        receptionEndTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_receptionEndTime);
        receptionEndTimeTextView.setOnClickListener(this);

        alongPersonNumberTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_alongPersonNumber);
        alongPersonNumberTextView.setOnClickListener(this);

        storeChannelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_storeChannel);
        storeChannelTextView.setOnClickListener(this);

        infoChannelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_infoChannel);
        infoChannelTextView.setOnClickListener(this);

        turnToEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_turnTo);

        intentLevelTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentLevel);
        intentLevelTextView.setOnClickListener(this);

        nextCallTimeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_nextCallTime);
        nextCallTimeTextView.setOnClickListener(this);

        intentCarsTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCars);
        intentCarsTextView.setOnClickListener(this);

        intentCarModleTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCarModle);
        intentCarModleTextView.setOnClickListener(this);

        intentCarColorTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_intentCarColor);
        intentCarColorTextView.setOnClickListener(this);

        carUseTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_carUse);
        carUseTextView.setOnClickListener(this);

        newChangeTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_newChange);
        newChangeTextView.setOnClickListener(this);

        mortgageTextView = (TextView) findViewById(R.id.activity_addnewtreach_tv_mortgage);
        mortgageTextView.setOnClickListener(this);
        featureGridView = (GridView) findViewById(R.id.activity_addnewtreach_gv_feature);
        talkProgressEditText = (EditText) findViewById(R.id.activity_addnewtreach_et_talkProgress);


        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = DatePickerDialog.newInstance(this,y,m,d);
        datePickerDialog.setAccentColor(Color.rgb(50, 126, 202));//327ECA
    }//end initView

}
