package com.tygas.tianyu.tianyu.ui.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tygas.tianyu.tianyu.R;
import com.tygas.tianyu.tianyu.ui.model.FirstProtect;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FP_CustomerInfo_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FP_CustomerInfo_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private FirstProtect firstProtect;
    private View inflate;

    public FP_CustomerInfo_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FP_CustomerInfo_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FP_CustomerInfo_Fragment newInstance(FirstProtect firstProtect) {
        FP_CustomerInfo_Fragment fragment = new FP_CustomerInfo_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, firstProtect);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) != null) {
                firstProtect = (FirstProtect) (getArguments().getSerializable(ARG_PARAM1));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_fp__customer_info_, container, false);
        initView();
        return inflate;
    }

    private void initView() {
        TextView tv_name = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_name);
        tv_name.setText(firstProtect.getCarOwnerName());
        TextView tv_phone = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_phone);
        tv_phone.setText(firstProtect.getCarOwnerPhone());
        TextView tv_carmodel = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_carmodel);
        tv_carmodel.setText(firstProtect.getFrameNum());
        TextView tv_carnumber = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_carnumber);
        tv_carnumber.setText(firstProtect.getCarNO());
        TextView tv_takecardate = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_takecardate);
        tv_takecardate.setText(firstProtect.getCarSalesTime());
        TextView tv_nearbymile = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_nearbymile);
        tv_nearbymile.setText(firstProtect.getEnterMileage());

        
        TextView tv_datemaintaindate = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_datemaintaindata);
        tv_datemaintaindate.setText(firstProtect.getPredictFitDate());


        TextView tv_recentlyinvitedtime = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_recentlyinvitedtime);
        tv_recentlyinvitedtime.setText(firstProtect.getInviteCallDate());
        TextView tv_shouldinvitationdate = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_shouldinvitationdate);
        tv_shouldinvitationdate.setText(firstProtect.getShouldInviteCallDate());
        TextView tv_last_talk = (TextView) inflate.findViewById(R.id.fragment_fpcustomersinfo_tv_last_talk);
        tv_last_talk.setText(firstProtect.getTalkProcess());
    }

}
