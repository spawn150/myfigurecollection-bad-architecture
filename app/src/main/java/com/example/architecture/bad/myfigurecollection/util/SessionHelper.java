package com.example.architecture.bad.myfigurecollection.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ant_robot.mfc.api.pojo.User;
import com.example.architecture.bad.myfigurecollection.data.user.SessionUser;

/**
 * Helper to manage session info into SharedPreferences
 */
public class SessionHelper {

    private final static String APP_ACCOUNT_PREFS_NAME = SessionHelper.class.getPackage() + ".account.prefs";
    private final static String USER_ID_PREFS = SessionHelper.class.getPackage().getName() + ".user.id";
    private final static String USER_NAME_PREFS = SessionHelper.class.getPackage().getName() + ".user.name";
    private final static String USER_PICTURE_PREFS = SessionHelper.class.getPackage().getName() + ".user.picture";
    private final static String USER_HOMEPAGE_PREFS = SessionHelper.class.getPackage().getName() + ".user.homepage";

    /**
     * Returns if the user is authenticated.
     *
     * @param context context
     * @return true if id and name are not empty.
     */
    public static boolean isAuthenticated(Context context) {
        SessionUser user = getUserData(context);
        return !TextUtils.isEmpty(user.getId()) && !TextUtils.isEmpty(user.getName());
    }

    /**
     * Gets name associated to User.
     *
     * @param context context
     * @return user's name
     */
    public static String getUserName(Context context) {
        SessionUser user = getUserData(context);
        return user.getName();
    }

    /**
     * Gets User data in session
     *
     * @param context context
     * @return user's data
     */
    public static SessionUser getUserData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ACCOUNT_PREFS_NAME, Context.MODE_PRIVATE);
        //TODO Remove hardcoded values when implemented the correct behaviour
        return new SessionUser(
                prefs.getString(USER_ID_PREFS, ""),
                prefs.getString(USER_NAME_PREFS, ""), /*"spawn150""STARlock""climbatize"*/
                prefs.getString(USER_PICTURE_PREFS, ""),
                prefs.getString(USER_HOMEPAGE_PREFS, "")
        );
    }

    @SuppressLint("CommitPrefEdits")
    public static void createSession(Context context, User user) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ACCOUNT_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString(USER_ID_PREFS, user.getId());
        edit.putString(USER_NAME_PREFS, user.getName());
        edit.putString(USER_PICTURE_PREFS, user.getPicture());
        edit.putString(USER_HOMEPAGE_PREFS, user.getHomepage());

        edit.commit();
    }

    public static void removeSession(Context context) {

        SharedPreferences prefs = context.getSharedPreferences(APP_ACCOUNT_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();

    }

}
