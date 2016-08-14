package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.context.MyAppCollection;
import com.tygas.tianyu.tianyu.context.PhonStateLisen;
import com.tygas.tianyu.tianyu.ui.model.PtCustomer;
import com.tygas.tianyu.tianyu.ui.model.User;
import com.tygas.tianyu.tianyu.ui.model.UserPtInfoModel;
import com.tygas.tianyu.tianyu.ui.view.activity.PtCustomersDetailActivity;
import com.tygas.tianyu.tianyu.ui.view.activity.ReturnVisitFail;
import com.tygas.tianyu.tianyu.utils.PhoneValidate;
import com.tygas.tianyu.tianyu.utils.RecordFile;

import java.util.List;

/**
 * Created by SJTY_YX on 2016/1/20.
 */
public class CustomersInfoFragment extends Fragment {


    private User user;
    private View root;
    private PtCustomer customer;

    private TextView name;
    private TextView phone;
    private TextView level;
    private TextView province;//省份
    private TextView city;
    private TextView area;
    private TextView channeName;//接触渠道
    private TextView carBrand;
    private TextView carSer;//意向车系
    private TextView carType;
    private TextView color;
    /*private TextView trunTo;//转介绍
    private TextView carNumber;//车牌*/
    private TextView use;//用途
    private TextView payType;//付款方式
    private TextView sorceChannelName;//信息渠道
    private TextView quotesituationTextView;//报价情况
    private TextView goucheyongtu;
    private TextView sparePhone;//备用电话
    private RelativeLayout rl_sparePhone;

    private GridView gridView;

    private PtCustomersDetailActivity activity;

    private static final String LOG_TAG = "CustomersInfoFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.activity = (PtCustomersDetailActivity) getActivity();
        root = inflater.inflate(R.layout.fragment_customersinfo, null);
        customer = (PtCustomer) getArguments().get("data");
        MyAppCollection context = (MyAppCollection) getActivity().getApplicationContext();
        user = context.getUser();
        List<UserPtInfoModel> list_focus = user.getList_Focus();
        if (list_focus == null) {
            return null;
        }

        String[] ms = customer.getFocusCarModel().split(",");

        gridView = (GridView) root.findViewById(R.id.frgment_customersinfo_gridview);
        gridView.setAdapter(new FoucseAdapter(list_focus, ms));
        initView();
        return root;
    }

    private void initView() {
        if (root != null && customer != null) {
            name = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_name);
            name.setText(customer.getCustomerName());
            phone = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_phone);
            phone.setText(customer.getCustomerPhone());
            level = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_level);
            level.setText(customer.getIntentLevel());
            province = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_liveProvince);
            province.setText(customer.getLiveProvince());
            city = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_liveCity);
            city.setText(customer.getLiveCity());
            area = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_liveArea);
            area.setText(customer.getLiveArea());
            channeName = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_channelName);
            channeName.setText(customer.getChannelName());
            carBrand = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_carIntentBrand);
            carBrand.setText(customer.getCarBrandName());//-------------------------
            carSer = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_carseriesName);
            carSer.setText(customer.getCarseriesName());
            carType = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_carModelName);
            carType.setText(customer.getCarModelName());
            color = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_colorName);
            color.setText(customer.getColorName());
            /*trunTo = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_turnToIntroduce);
            trunTo.setText(customer.getTurnToIntroduce());
            carNumber = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_oldCusCarNO);
            carNumber.setText(customer.getOldCusCarNO());*/

            use = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_useageName);
            use.setText(customer.getUseageName());
            payType = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_payType);
            payType.setText(customer.getPayType());


            //设置关注特征
            sorceChannelName = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_sourceChannelName);
            sorceChannelName.setText(customer.getSourceChannelName());

            quotesituationTextView = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_quotesituation);
            quotesituationTextView.setText(customer.getQuoteSituation());

            sparePhone = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_sparephone);
            rl_sparePhone = (RelativeLayout) root.findViewById(R.id.fragment_customersinfo_rl_sparephone);
            sparePhone.setText(customer.getSparePhone());

            goucheyongtu = (TextView) root.findViewById(R.id.fragment_customersinfo_tv_goucheleixin);
            goucheyongtu.setText(customer.getIsChange());
        }
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.frgment_customersinfo_bt_callout://电话播出
//                TelephonyManager manager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
//                if (manager.getCallState() == TelephonyManager.CALL_STATE_OFFHOOK) {
//                    Toast.makeText(getActivity(), "不能同时播出多个号码", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                if (!PhoneValidate.validateNumber(customer.getCustomerPhone())) {//电话又字符串则返回true
//                    Toast.makeText(activity, "电话号码有误,请修改后再回访", Toast.LENGTH_SHORT).show();
//                    /*Intent intent = new Intent();
//                    intent.putExtra("data", customer);
//                    intent.putExtra("path", "  ");
//                    intent.setClass(activity, ReturnVisitFail.class);
//                    ReturnVisitFail.ptCustomerActivity = activity;
//                    //intent.setClass(this,ReturnVisitOk.class);
//                    activity.startActivity(intent);*/
//                } else {
//
//                    if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
//                            ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
//
//                        Uri uri = Uri.parse("tel:" + customer.getCustomerPhone());
//                        Intent call = new Intent(Intent.ACTION_CALL, uri); //直接播出电话
//                        RecordFile recordFile = new RecordFile(activity, customer.getCustomerName() + customer.getCustomerPhone());
//                        PhonStateLisen.setParamsForPtCustomer(activity, customer,
//                                recordFile.getFilePath(), recordFile.getRecordFile());
//                        getActivity().startActivity(call);
//                    } else {
//                        Toast.makeText(activity, "该应用没有允许打电话权限或读取通话记录，请在设应用程序权限管理中将打开！", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                break;
//
//            case R.id.fragment_customersinfo_rl_sparephone://电话播出
//                Toast.makeText(activity, "备用电话播出啦！！！", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG,"[requestCode] "+requestCode);
    }*/

    class FoucseAdapter extends BaseAdapter {

        private List<UserPtInfoModel> models;
        private String[] ms;

        public FoucseAdapter(List<UserPtInfoModel> models, String[] ms) {
            this.models = models;
            this.ms = ms;
        }

        @Override

        public int getCount() {
            return models.size();
        }

        @Override
        public Object getItem(int position) {
            return models.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_customersinfo_fouces_item, null);
            TextView textView = (TextView) view.findViewById(R.id.fragment_customersinfo_fouces_item_tv);
            textView.setText(models.get(position).getName());
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.fragment_customersinfo_fouces_item_cb);
            for (int i = 0; i < ms.length; i++) {
                if (ms[i].equals(models.get(position).getName())) {
                    checkBox.setChecked(true);
                    textView.setTextColor(Color.BLACK);
                    break;
                } else {
                    checkBox.setChecked(false);
                    textView.setTextColor(Color.parseColor("#a8a9a9"));
                }
            }
            return view;
        }
    }

}
