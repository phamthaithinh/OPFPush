/*******************************************************************************
 * Copyright 2014 One Platform Foundation
 *
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 ******************************************************************************/

package org.onepf.openpush.nokia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nokia.push.PushBaseIntentService;
import com.nokia.push.PushConstants;

import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.onepf.openpush.OpenPushConstants;
import org.onepf.openpush.OpenPushHelper;
import org.onepf.openpush.ProviderRegistrationResult;

/**
 * @author Anastasia Karimova
 * @since 08.07.2014
 */
public class NokiaPushService extends PushBaseIntentService {

    public NokiaPushService() {
        super("Nokia Push Client"); //Passed name will use as Thread name;
    }

    /**
     * Called when a Push Notification message has been received.
     *
     * @param appContext Application's context.
     * @param intent     Intent containing the message payload as extras.
     */
    @Override
    protected void onMessage(@NotNull Context appContext, Intent intent) {
        OpenPushHelper.sendMessage(this, NokiaPushProvider.NAME, intent.getExtras());
    }

    /**
     * Called on registration or unregistration error.
     *
     * @param appContext   Application's context.
     * @param errorMessage Error id returned by the Push Notifications service.
     */
    @Override
    protected void onError(@NotNull Context appContext, String errorMessage) {
    }

    /**
     * Called when the Push Notifications server tells pending messages
     * have been deleted because the device was idle.
     *
     * @param appContext Application's context.
     * @param total      Total number of collapsed messages.
     */
    @Override
    protected void onDeletedMessages(@NotNull Context appContext, int total) {
        Bundle extras = new Bundle(1);
        extras.putInt(OpenPushConstants.EXTRA_MESSAGES_COUNT, total);
        OpenPushHelper.sendMessageDeleted(this, NokiaPushProvider.NAME, extras);
    }

    /**
     * Called on a registration error that could be retried.
     * By default, it does nothing and returns true,
     * but could be overridden to change that behavior and/or display the error.
     *
     * @param appContext Application's context.
     * @param errorId    Error id returned by the Push Notifications service.
     * @return If true, failed operation will be retried (using exponential backoff).
     */
    @Override
    protected boolean onRecoverableError(@NotNull Context appContext,
                                         @NotNull
                                         @MagicConstant(stringValues = {
                                                 PushConstants.ERROR_INVALID_PARAMETERS,
                                                 PushConstants.ERROR_INVALID_SENDER,
                                                 PushConstants.ERROR_SERVICE_NOT_AVAILABLE
                                         })
                                         String errorId) {
        int error;
        if (PushConstants.ERROR_INVALID_PARAMETERS.equals(errorId)) {
            error = OpenPushConstants.ERROR_INVALID_PARAMETERS;
        } else if (PushConstants.ERROR_SERVICE_NOT_AVAILABLE.equals(errorId)) {
            error = OpenPushConstants.ERROR_INVALID_PARAMETERS;
        } else if (PushConstants.ERROR_SERVICE_NOT_AVAILABLE.equals(errorId)) {
            error = OpenPushConstants.ERROR_SERVICE_NOT_AVAILABLE;
        } else {
            error = OpenPushConstants.ERROR_UNKNOWN;
        }
        OpenPushHelper.notifyRegistrationEnd(
                new ProviderRegistrationResult(NokiaPushProvider.NAME, error));
        return false;
    }

    /**
     * Called after a device has been registered.
     *
     * @param appContext        Application's context.
     * @param registrationToken The registration id returned by the Push Notifications service.
     */
    @Override
    protected void onRegistered(@NotNull Context appContext,
                                @NotNull String registrationToken) {
        OpenPushHelper.notifyRegistrationEnd(
                new ProviderRegistrationResult(NokiaPushProvider.NAME, registrationToken));
        OpenPushHelper.sendRegistered(this, NokiaPushProvider.NAME, registrationToken);
    }

    /**
     * Called after a device has been unregistered.
     *
     * @param appContext           Application's context.
     * @param oldRegistrationToken the registration id that was previously registered.
     */
    @Override
    protected void onUnregistered(@NotNull Context appContext,
                                  @NotNull String oldRegistrationToken) {
        OpenPushHelper.sendUnregistered(this, NokiaPushProvider.NAME, oldRegistrationToken);
    }
}