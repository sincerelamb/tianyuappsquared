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
import com.tygas.tianyu.tianyu.ui.model.TimingProtect;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TP_CustomerInfo_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TP_CustomerInfo_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private TimingProtect mParam1;

    private View inflate;

    public TP_CustomerInfo_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TP_CustomerInfo_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TP_CustomerInfo_Fragment newInstance(TimingProtect param1) {
        TP_CustomerInfo_Fragment fragment = new TP_CustomerInfo_Fragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getSerializable(ARG_PARAM1) != null) {
                mParam1 = (TimingProtect) (getArguments().getSerializable(ARG_PARAM1));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        inflate = inflater.inflate(R.layout.fragment_tp__customer_info_, container, false);
        initView();
        return inflate;
    }

    private void initView() {
        TextView tv_name = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_name);
        tv_name.setText(mParam1.getCustomerName());
        TextView tv_phone = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_phone);
        tv_phone.setText(mParam1.getCustomerPhone());
        TextView tv_carmodel = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_carmodel);
        tv_carmodel.setText(mParam1.getFrameNum());
        TextView tv_carnumber = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_carnumber);
        tv_carnumber.setText(mParam1.getCarNO());
        TextView tv_nearprotect = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_nearprotect);
        tv_nearprotect.setText(mParam1.getLatelyMaintainTime());
        TextView tv_nearbymile = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_nearbymile);
        tv_nearbymile.setText(mParam1.getLatelyMileage());


        TextView tv_milemaintaindate = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_milemaintaindata);
        tv_milemaintaindate.setText(mParam1.getLatelyMaintainDate());
        TextView tv_datemaintaindate = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_datemaintaindata);
        tv_datemaintaindate.setText(mParam1.getPredictDate());

        TextView tv_recentlyinvitedtime = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_recentlyinvitedtime);
        tv_recentlyinvitedtime.setText(mParam1.getLatelyInviteCallDate());
        TextView tv_shouldinvitationdate = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_shouldinvitationdate);
        tv_shouldinvitationdate.setText(mParam1.getShouldInviteCallDate());
        TextView tv_last_talk = (TextView) inflate.findViewById(R.id.fragment_tpcustomersinfo_tv_last_talk);
        tv_last_talk.setText(mParam1.getLatelyTalkProcess());
    }
}
