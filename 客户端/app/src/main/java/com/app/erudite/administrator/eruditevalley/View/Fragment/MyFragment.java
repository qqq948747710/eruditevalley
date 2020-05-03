package com.app.erudite.administrator.eruditevalley.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.erudite.administrator.eruditevalley.Model.UserInfo;
import com.app.erudite.administrator.eruditevalley.R;
import com.app.erudite.administrator.eruditevalley.View.LoginActivity;

public class MyFragment extends Fragment implements View.OnClickListener {
    private ImageView myHistoryIco;
    private ImageView mySettingIco;
    private TextView myLoginname;
    private static MyFragment myfragment;
    private UserInfo userInfo;
    public MyFragment(){
        super();
    }
    public static MyFragment get(){
        if(myfragment!=null){
            return myfragment;
        }else {
            myfragment=new MyFragment();
        }
        return myfragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_my,container,false);
        myHistoryIco = (ImageView) view.findViewById(R.id.my_history_ico);
        mySettingIco = (ImageView) view.findViewById(R.id.my_setting_ico);
        myLoginname = (TextView) view.findViewById(R.id.my_loginname);
        myLoginname.setOnClickListener(this);
        initModul();
        this.update(userInfo.getName());
        return view;
    }

    private void initModul(){
        userInfo=userInfo.get(this);
    }
    public void update(String loginname){
        myLoginname.setText(loginname.isEmpty()?"点击登录":loginname);
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfo.getinfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_loginname:
                if(userInfo.getUsername().isEmpty()){
                    Intent intent =new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
        }
    }
}
