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

package org.onepf.opfpush.pushsample.listener;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.onepf.opfpush.OPFPushLog;
import org.onepf.opfpush.listener.EventListener;
import org.onepf.opfpush.model.OPFError;
import org.onepf.opfpush.pushsample.R;
import org.onepf.opfpush.pushsample.model.MessageEvent;
import org.onepf.opfpush.pushsample.model.RegisteredEvent;
import org.onepf.opfpush.pushsample.model.RegistrationErrorEvent;
import org.onepf.opfpush.pushsample.model.UnregisteredEvent;
import org.onepf.opfpush.pushsample.model.UnregistrationErrorEvent;
import org.onepf.opfpush.pushsample.util.NotificationUtils;
import org.onepf.opfpush.util.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import de.greenrobot.event.EventBus;

import static org.onepf.opfpush.pushsample.util.Constants.MESSAGE_EXTRA_KEY;
import static org.onepf.opfpush.pushsample.util.Constants.PAYLOAD_EXTRA_KEY;

/**
 * @author Roman Savin
 * @since 09.12.14
 */
public class DemoEventListener implements EventListener {

    @NonNull
    private Context appContext;

    public DemoEventListener(@NonNull final Context context) {
        this.appContext = context.getApplicationContext();
    }

    @Override
    public void onMessage(@NonNull final String providerName, @Nullable final Bundle extras) {
        OPFPushLog.methodD(DemoEventListener.class, "onMessage", providerName, Utils.toString(extras));
        if (extras == null) {
            return;
        }

        String message = null;
        if (extras.containsKey(MESSAGE_EXTRA_KEY)) {
            message = extras.getString(MESSAGE_EXTRA_KEY);
        } else if (extras.containsKey(PAYLOAD_EXTRA_KEY)) {
            message = extras.getString(PAYLOAD_EXTRA_KEY);
        }

        if (message != null) {
            try {
                NotificationUtils.showNotification(
                        appContext,
                        appContext.getString(R.string.message_notification_title),
                        message
                );
                EventBus.getDefault().postSticky(new MessageEvent(URLDecoder.decode(message, "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                OPFPushLog.e(e.getCause().toString());
            }
        }
    }

    @Override
    public void onDeletedMessages(@NonNull final String providerName, final int messagesCount) {
        OPFPushLog.methodD(DemoEventListener.class, "onDeletedMessages", providerName, messagesCount);
    }

    @Override
    public void onRegistered(@NonNull final String providerName,
                             @NonNull final String registrationId) {
        OPFPushLog.methodD(DemoEventListener.class, "onRegistered", providerName, registrationId);
        EventBus.getDefault().postSticky(new RegisteredEvent(registrationId));
    }

    @Override
    public void onUnregistered(@NonNull final String providerName,
                               @NonNull final String registrationId) {
        OPFPushLog.methodD(DemoEventListener.class, "onUnregistered", providerName, registrationId);
        EventBus.getDefault().postSticky(new UnregisteredEvent(registrationId));
    }

    @Override
    public void onRegistrationError(@NonNull final String providerName,
                                    @NonNull final OPFError error) {
        OPFPushLog.methodD(DemoEventListener.class, "onRegistrationError", providerName, error);
        EventBus.getDefault().postSticky(new RegistrationErrorEvent(error));
    }

    @Override
    public void onUnregistrationError(@NonNull final String providerName,
                                      @NonNull final OPFError error) {
        OPFPushLog.methodD(DemoEventListener.class, "onUnregistrationError", providerName, error);
        EventBus.getDefault().postSticky(new UnregistrationErrorEvent(error));
    }

    @Override
    public void onNoAvailableProvider() {
        OPFPushLog.methodD(DemoEventListener.class, "onNoAvailableProvider()");
    }
}
