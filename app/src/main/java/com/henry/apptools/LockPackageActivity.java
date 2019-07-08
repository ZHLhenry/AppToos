package com.henry.apptools;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.henry.apptools.adapter.CustomDialogAdapter;
import com.henry.apptools.adapter.LockPackageAdapter;
import com.henry.apptools.entity.SignModel;
import com.henry.apptools.taskutils.AppUtils;
import com.kongzue.dialog.v2.CustomDialog;
import com.kongzue.dialog.v2.DialogSettings;

import java.util.ArrayList;
import java.util.List;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_MATERIAL;

/**
 * Author:henry
 * Time:2019-06-04 10:51:51
 * ClassName:This is LockPackageActivity
 * Remark:不要冲动,杀人犯法
 * Description:app相关信息
 */
public class LockPackageActivity extends AppCompatActivity {

    private RecyclerView lock_package_rv;
    private TextView no_system_tv, system_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_package);
        DialogSettings.style = STYLE_MATERIAL;
        initView();
    }

    private void initView() {
        no_system_tv = findViewById(R.id.no_system_tv);
        system_tv = findViewById(R.id.system_tv);
        lock_package_rv = findViewById(R.id.lock_package_rv);
        lock_package_rv.setLayoutManager(new LinearLayoutManager(LockPackageActivity.this));

        no_system_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 非系统应用
                no_system_tv.setTextColor(Color.parseColor("#ff0000"));
                system_tv.setTextColor(Color.parseColor("#333333"));
                LockPackageAdapter lockPackageAdapter = new LockPackageAdapter(LockPackageActivity.this,
                        AppUtils.getInstalledApplication(LockPackageActivity.this, false));
                lockPackageAdapter.setOnItemClickListener(new LockPackageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos, List<ResolveInfo> resolveInfoList) {
                        showItemDialog(pos, resolveInfoList);
                    }
                });
                lock_package_rv.setAdapter(lockPackageAdapter);
            }
        });

        system_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 系统应用
                no_system_tv.setTextColor(Color.parseColor("#333333"));
                system_tv.setTextColor(Color.parseColor("#ff0000"));
                LockPackageAdapter lockPackageAdapter = new LockPackageAdapter(LockPackageActivity.this,
                        AppUtils.getInstalledApplication(LockPackageActivity.this, true));
                lockPackageAdapter.setOnItemClickListener(new LockPackageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos, List<ResolveInfo> resolveInfoList) {
                        showItemDialog(pos, resolveInfoList);
                    }
                });
                lock_package_rv.setAdapter(lockPackageAdapter);
            }
        });

        no_system_tv.performClick();
    }


    private List<SignModel> mSignModels;

    private void showItemDialog(int pos, List<ResolveInfo> resolveInfoList) {
        mSignModels = new ArrayList<>();
        //包名
        mSignModels.add(new SignModel("包名", resolveInfoList.get(pos).activityInfo.packageName));
        //MD5
        mSignModels.add(new SignModel(AppUtils.MD5, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.MD5, false).get(0)));
        mSignModels.add(new SignModel(AppUtils.MD5, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.MD5, true).get(0)));
        //SHA1
        mSignModels.add(new SignModel(AppUtils.SHA1, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.SHA1, false).get(0)));
        mSignModels.add(new SignModel(AppUtils.SHA1, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.SHA1, true).get(0)));
        //SHA256
        mSignModels.add(new SignModel(AppUtils.SHA256, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.SHA256, false).get(0)));
        mSignModels.add(new SignModel(AppUtils.SHA256, AppUtils.getSingInfo(LockPackageActivity.this,
                resolveInfoList.get(pos).activityInfo.packageName, AppUtils.SHA256, true).get(0)));

        CustomDialog.show(LockPackageActivity.this, R.layout.layout_custom, new CustomDialog.BindView() {
            @Override
            public void onBind(final CustomDialog dialog, View rootView) {
                RecyclerView custom_rv = rootView.findViewById(R.id.custom_rv);
                custom_rv.setLayoutManager(new LinearLayoutManager(LockPackageActivity.this));
                CustomDialogAdapter customDialogAdapter = new CustomDialogAdapter(LockPackageActivity.this, mSignModels);
                customDialogAdapter.setItemClicklistener(new CustomDialogAdapter.ItemClicklistener() {
                    @Override
                    public void onItemClicklistener(int i, View view) {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText(mSignModels.get(i).getName(), mSignModels.get(i).getValue());
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        Toast.makeText(LockPackageActivity.this, mSignModels.get(i).getName() + "已复制到粘贴板", Toast.LENGTH_SHORT).show();
                        dialog.doDismiss();
                    }
                });
                custom_rv.setAdapter(customDialogAdapter);
            }
        }).setCanCancel(true);
    }
}
