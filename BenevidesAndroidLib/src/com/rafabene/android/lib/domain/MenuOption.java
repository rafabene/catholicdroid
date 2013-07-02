package com.rafabene.android.lib.domain;

import android.app.Activity;

public class MenuOption {

    private int name;

    private int icon;

    private Class<? extends Activity> activity;

    public MenuOption(int name, int icon, Class<? extends Activity> activity) {
        this.name = name;
        this.icon = icon;
        this.activity = activity;
    }

    public int getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

}
