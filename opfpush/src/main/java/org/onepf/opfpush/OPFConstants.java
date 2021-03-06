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

/**
 * @author Kirill Rozov
 * @author Roman Savin
 * @since 02.10.14.
 */
public final class OPFConstants {

    private OPFConstants() {
        throw new UnsupportedOperationException();
    }

    static final String ACTION_REGISTER = BuildConfig.APPLICATION_ID + "intent.REGISTER";

    public static final int MESSAGES_COUNT_UNKNOWN = Integer.MIN_VALUE;

    public static final String EXTRA_PROVIDER_NAME = "org.onepf.opfpush.intent.EXTRA_PROVIDER_NAME";
    public static final String EXTRA_ERROR = "org.onepf.opfpush.intent.EXTRA_ERROR";
    public static final String EXTRA_REGISTRATION_ID = "org.onepf.opfpush.intent.EXTRA_REGISTRATION_ID";
    public static final String EXTRA_MESSAGE_TYPE = "org.onepf.opfpush.intent.EXTRA_MESSAGE_TYPE";
    public static final String EXTRA_MESSAGE_COUNT = "org.onepf.opfpush.intent.EXTRA_MESSAGE_COUNT";

    public static final String ACTION_NO_AVAILABLE_PROVIDER = "org.onepf.opfpush.intent.NO_AVAILABLE_PROVIDER";
    public static final String ACTION_RECEIVE = "org.onepf.opfpush.intent.RECEIVE";
    public static final String ACTION_REGISTRATION = "org.onepf.opfpush.intent.REGISTRATION";
    public static final String ACTION_UNREGISTRATION = "org.onepf.opfpush.intent.UNREGISTRATION";
}
