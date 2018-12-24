/*
 * Tencent is pleased to support the open source community by making Tinker available.
 *
 * Copyright (C) 2016 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the BSD 3-Clause License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * https://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ltz.mymvp.tinker.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ltz.mymvp.MApplication;
import com.ltz.mymvp.tinker.app.BuildInfo;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by zhangshaowen on 16/4/7.
 */
public class Utils {
    private static final String TAG = "Tinker.Utils";

    /**
     * the error code define by myself
     * should after {@code ShareConstants.ERROR_PATCH_INSERVICE
     */
    public static final int ERROR_PATCH_GOOGLEPLAY_CHANNEL      = -20;
    public static final int ERROR_PATCH_ROM_SPACE               = -21;
    public static final int ERROR_PATCH_MEMORY_LIMIT            = -22;
    public static final int ERROR_PATCH_CRASH_LIMIT             = -23;
    public static final int ERROR_PATCH_CONDITION_NOT_SATISFIED = -24;

    public static final String PLATFORM = "platform";

    public static final int MIN_MEMORY_HEAP_SIZE = 45;

    private static boolean background = false;

    public static boolean isGooglePlay() {
        return false;
    }

    public static boolean isBackground() {
        return background;
    }

    public static void setBackground(boolean back) {
        background = back;
    }

    public static int checkForPatchRecover(long roomSize, int maxMemory) {
        if (Utils.isGooglePlay()) {
            return Utils.ERROR_PATCH_GOOGLEPLAY_CHANNEL;
        }
        if (maxMemory < MIN_MEMORY_HEAP_SIZE) {
            return Utils.ERROR_PATCH_MEMORY_LIMIT;
        }
        //or you can mention user to clean their rom space!
        if (!checkRomSpaceEnough(roomSize)) {
            return Utils.ERROR_PATCH_ROM_SPACE;
        }

        return ShareConstants.ERROR_PATCH_OK;
    }

    public static boolean isXposedExists(Throwable thr) {
        StackTraceElement[] stackTraces = thr.getStackTrace();
        for (StackTraceElement stackTrace : stackTraces) {
            final String clazzName = stackTrace.getClassName();
            if (clazzName != null && clazzName.contains("de.robv.android.xposed.XposedBridge")) {
                return true;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean checkRomSpaceEnough(long limitSize) {
        long allSize;
        long availableSize = 0;
        try {
            File data = Environment.getDataDirectory();
            StatFs sf = new StatFs(data.getPath());
            availableSize = (long) sf.getAvailableBlocks() * (long) sf.getBlockSize();
            allSize = (long) sf.getBlockCount() * (long) sf.getBlockSize();
        } catch (Exception e) {
            allSize = 0;
        }

        if (allSize != 0 && availableSize > limitSize) {
            return true;
        }
        return false;
    }

    public static String getExceptionCauseString(final Throwable ex) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(bos);

        try {
            // print directly
            Throwable t = ex;
            while (t.getCause() != null) {
                t = t.getCause();
            }
            t.printStackTrace(ps);
            return toVisualString(bos.toString());
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String toVisualString(String src) {
        boolean cutFlg = false;

        if (null == src) {
            return null;
        }

        char[] chr = src.toCharArray();
        if (null == chr) {
            return null;
        }

        int i = 0;
        for (; i < chr.length; i++) {
            if (chr[i] > 127) {
                chr[i] = 0;
                cutFlg = true;
                break;
            }
        }

        if (cutFlg) {
            return new String(chr, 0, i);
        } else {
            return src;
        }
    }

    public static class ScreenState {
        public interface IOnScreenOff {
            void onScreenOff();
        }

        public ScreenState(final Context context, final IOnScreenOff onScreenOffInterface) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(Intent.ACTION_SCREEN_OFF);

            context.registerReceiver(new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent in) {
                    String action = in == null ? "" : in.getAction();
                    TinkerLog.i(TAG, "ScreenReceiver action [%s] ", action);
                    if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                        if (onScreenOffInterface != null) {
                            onScreenOffInterface.onScreenOff();
                        }
                    }
                    context.unregisterReceiver(this);
                }
            }, filter);
        }
    }



    public static void showTinkerInfo(Context context) {
        // add more Build Info
        final StringBuilder sb = new StringBuilder();
        Tinker tinker = Tinker.with(MApplication.getContext());
        if (tinker.isTinkerLoaded()) {
            sb.append(String.format("[patch is loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BuildInfo.TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName(ShareConstants.TINKER_ID)));
            sb.append(String.format("[packageConfig patchMessage] %s \n", tinker.getTinkerLoadResultIfPresent().getPackageConfigByName("patchMessage")));
            sb.append(String.format("[TINKER_ID Rom Space] %d k \n", tinker.getTinkerRomSpace()));

        } else {
            sb.append(String.format("[patch is not loaded] \n"));
            sb.append(String.format("[buildConfig TINKER_ID] %s \n", BuildInfo.TINKER_ID));
            sb.append(String.format("[buildConfig BASE_TINKER_ID] %s \n", BuildInfo.TINKER_ID));

            sb.append(String.format("[buildConfig MESSSAGE] %s \n", BuildInfo.MESSAGE));
            sb.append(String.format("[TINKER_ID] %s \n", ShareTinkerInternals.getManifestTinkerID(MApplication.getContext())));
        }
        sb.append(String.format("[BaseBuildInfo Message] %s \n", BuildInfo.TINKER_ID));

        final TextView v = new TextView(context);
        v.setText(sb);
        v.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        v.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        v.setTextColor(0xFF000000);
        v.setTypeface(Typeface.MONOSPACE);
        final int padding = 16;
        v.setPadding(padding, padding, padding, padding);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(v);
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
