package br.com.catholicdroid.domain.factory;

import java.util.ArrayList;
import java.util.List;

import br.com.catholicdroid.R;
import br.com.catholicdroid.activity.SettingsActivity;
import br.com.catholicdroid.activity.TwittersActivity;

import com.rafabene.android.lib.domain.MenuOption;

public class MenuOptionFactory {

    private MenuOptionFactory(){
        
    }
    
    public static List<MenuOption> getMenuOptions() {
        List<MenuOption> options = new ArrayList<MenuOption>();
        options.add(new MenuOption(R.string.pope_twitter, R.drawable.ic_twitter, TwittersActivity.class));
        options.add(new MenuOption(R.string.title_activity_settings, R.drawable.ic_launcher, SettingsActivity.class));
        return options;
    }
}
