package br.com.catholicdroid.activity;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import br.com.catholicdroid.R;
import br.com.catholicdroid.domain.factory.MenuOptionFactory;

import com.actionbarsherlock.view.MenuItem;
import com.rafabene.android.lib.activity.BaseActivity;
import com.rafabene.android.lib.adapter.MenuOptionAdapter;
import com.rafabene.android.lib.domain.MenuOption;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridView gridview = (GridView) findViewById(R.id.main_gridview);
        final List<MenuOption> options = MenuOptionFactory.getMenuOptions();
        gridview.setAdapter(new MenuOptionAdapter(this, options));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MenuOption option = options.get(position);
                startActivity(new Intent(MainActivity.this, option.getActivity()));
            }
        });
    }
    
    /**
     * Avoid killing the main screen
     */
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return true;
    }

}
