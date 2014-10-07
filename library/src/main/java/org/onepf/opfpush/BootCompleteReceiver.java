/*
 * Copyright 2012-2014 One Platform Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onepf.opfpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import static org.onepf.opfpush.OPFPushLog.LOGD;
import static org.onepf.opfpush.OPFPushLog.LOGI;

/**
 * @author Kirill Rozov
 * @since 09.09.14.
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(@NonNull Context context, Intent intent) {
        LOGD("OpenPush receive boot complete.");
        final OPFPushHelper helper = OPFPushHelper.getInstance(context);
        if (helper.isRegistered()) {
            if (isAndroidIDChanged(context)) {
                LOGI("Android ID changed.");
                helper.onNeedRetryRegister();
            }
        } else if (helper.isRegistering()) {
            LOGI("Retry register after reboot.");
            helper.restartRegisterOnBoot();
        }
    }

    public static boolean isAndroidIDChanged(@NonNull Context context) {
        return !android.provider.Settings.Secure.ANDROID_ID.equals(new Settings(context).getLastAndroidId());
    }
}
