package com.henry.apptools;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.henry.apptools.taskutils.AccessibilityUtil;
import com.henry.apptools.taskutils.AppUtils;
import com.henry.apptools.taskutils.TrackerService;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        checkOverlayPermission();
        findViewById(R.id.lock_task_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessibilityUtil.checkAccessibility(MainActivity.this)) {
                    startService(
                            new Intent(MainActivity.this, TrackerService.class)
                                    .putExtra(TrackerService.COMMAND, TrackerService.COMMAND_OPEN)
                    );
                    finish();
                }
            }
        });

        findViewById(R.id.lock_package_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LockPackageActivity.class));
            }
        });

        findViewById(R.id.open_developer_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.startDevelopmentActivity(MainActivity.this);
            }
        });
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        REQUEST_CODE
                );
            }
        }
    }

}
