package org.onepf.openpush.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Different utils.
 *
 * @author Kirill Rozov
 * @since 29.09.14
 */
public final class Utils {

    public static final String ITEM_DIVIDER = ", ";

    /**
     * Convert intent to string.
     *
     * @param intent Intent from what get extras.
     * @return String representation of extras.
     */
    @Nullable
    public static String toString(@NonNull Intent intent) {
        StringBuilder b = new StringBuilder();
        b.append("Intent{");
        b.append("action=").append('"').append(intent.getAction()).append('"');
        b.append(ITEM_DIVIDER);
        b.append("data=").append('"').append(intent.getDataString()).append('"');
        b.append(ITEM_DIVIDER);
        b.append("component=").append('"').append(intent.getComponent()).append('"');
        b.append(ITEM_DIVIDER);
        Bundle extras = intent.getExtras();
        b.append("extras=").append(extras == null ? null : toString(extras));
        b.append('}');
        return b.toString();
    }

    @NonNull
    public static String toString(@NonNull Bundle bundle) {
        if (bundle.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (String key : bundle.keySet()) {
            builder.append('"').append(key).append('"');
            builder.append(':');
            builder.append('"').append(bundle.get(key)).append('"');
            builder.append(ITEM_DIVIDER);
        }
        builder.setLength(builder.length() - ITEM_DIVIDER.length());
        builder.append(']');
        return builder.toString();
    }

    private Utils() {
    }
}