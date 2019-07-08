package com.henry.apptools.taskutils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.provider.Settings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:henry
 * Time:2019-06-04 15:22:22
 * ClassName:This is AppUtils
 * Remark:不要冲动,杀人犯法
 * Description:description
 */
public class AppUtils {

    /**
     * 获取安装的所有app
     *
     * @param context
     * @param needSysAPP
     * @return
     */
    public static List<ResolveInfo> getInstalledApplication(Context context, boolean needSysAPP) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> responseInfos = new ArrayList<>();
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        //排除系统应用
        if (!needSysAPP) {
            for (int i = 0; i < resolveInfos.size(); i++) {
                ResolveInfo resolveInfo = resolveInfos.get(i);
                try {
                    if (!isSysApp(context, resolveInfo.activityInfo.packageName)) {
                        responseInfos.add(resolveInfo);
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            responseInfos = resolveInfos;
        }
        return responseInfos;
    }

    /**
     * 判断是否系统应用
     *
     * @param context
     * @param packageName
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static boolean isSysApp(Context context, String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return false;
        } else {
            return true;
        }
    }

    public final static String MD5 = "md5";
    public final static String SHA1 = "sha1";
    public final static String SHA256 = "sha256";

    /**
     * 返回一个签名的对应类型的字符串
     *
     * @param context
     * @param packageName
     * @param type
     * @return
     */
    public static ArrayList<String> getSingInfo(Context context, String packageName, String type, boolean showColon) {
        ArrayList<String> result = new ArrayList<String>();
        try {
            Signature[] signs = getSignatures(context, packageName);
            for (Signature sig : signs) {
                String tmp = "error!";
                if (MD5.equals(type)) {
                    tmp = getSignatureString(sig, MD5, showColon);
                } else if (SHA1.equals(type)) {
                    tmp = getSignatureString(sig, SHA1, showColon);
                } else if (SHA256.equals(type)) {
                    tmp = getSignatureString(sig, SHA256, showColon);
                }
                result.add(tmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 创建日期：2017/11/8 on 下午2:24
     * 描述:  返回对应包的签名信息
     */
    public static Signature[] getSignatures(Context context, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return packageInfo.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建日期：2017/11/8 on 下午2:24
     * 描述:  获取相应的类型的字符串（把签名的byte[]信息转换成16进制）
     */
    public static String getSignatureString(Signature sig, String type, boolean showColon) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();
                for (byte digestByte : digestBytes) {
                    if (showColon) {
                        sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3) + ":");
                    } else {
                        sb.append((Integer.toHexString((digestByte & 0xFF) | 0x100)).substring(1, 3));
                    }
                }
                if (showColon) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return fingerprint;
    }


    /**
     * 判断版本是否是8.0
     * @return
     */
    public static boolean isSDKPie() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return true;
        }
        return false;
    }


    /**
     * 打开开发者选项界面
     * @param context
     */
    public static void startDevelopmentActivity(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.DevelopmentSettings");
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.setAction("android.intent.action.View");
                context.startActivity(intent);
            } catch (Exception e1) {
                try {
                    //部分小米手机采用这种方式跳转
                    Intent intent = new Intent("com.android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    context.startActivity(intent);
                } catch (Exception e2) {

                }

            }
        }
    }
}
