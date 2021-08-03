package com.sdg.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class AppUtil {

    /**
     * 获取当前应用版本
     *
     * @param context
     * @return
     */
    public static long getVersionCode(Context context) {
        PackageManager packageManager = null;
        PackageInfo packageInfo = null;
        try {
            packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                return packageInfo.getLongVersionCode();
            } else {
                return packageInfo.versionCode;

            }
        }
        return -1;
    }

    /**
     * 安装应用
     *
     */
    public static void installApk(Activity activity, File apkFile) {
        //安装前校验包，一防止篡改
        if(!veritySignature(activity,apkFile.getAbsolutePath())){
            Toast.makeText(activity,"升级失败，安装包不一致",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(apkFile);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    public static String getFileMd5(File file) {
        MessageDigest digest = null;
        FileInputStream is = null;
        try {
            digest = MessageDigest.getInstance("MD5");
            is = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            byte[] result = digest.digest();
            //转为16进制
            return new BigInteger(result).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean veritySignature(Activity activity, String filePath) {
        try {
            //获取已安装应用签名
            Signature pkgSig = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), PackageManager.GET_SIGNATURES)
                    .signatures[0];

            //获取下载的apk签名
            Signature apkSig = Objects.requireNonNull(activity.getPackageManager()
                    .getPackageArchiveInfo(filePath, PackageManager.GET_SIGNATURES))
                    .signatures[0];

            //对比两个签名的md5是否一致
            String pkgSigMd5 = getMd5(pkgSig);
            String apkSigMd5 = getMd5(apkSig);
            if (pkgSigMd5.equals(apkSigMd5)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static String getMd5(Signature signature) {
        return encryptionMD5(signature.toByteArray());
    }

    public static String encryptionMD5(byte[] byteStr) {
        MessageDigest messageDigest = null;
        StringBuffer md5StrBuff = new StringBuffer();
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(byteStr);
            byte[] byteArray = messageDigest.digest();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5StrBuff.toString();
    }
}
