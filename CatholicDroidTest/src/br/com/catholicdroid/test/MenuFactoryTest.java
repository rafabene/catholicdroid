package br.com.catholicdroid.test;

import java.util.List;

import junit.framework.Assert;

import br.com.catholicdroid.domain.factory.MenuOptionFactory;

import com.rafabene.android.lib.domain.MenuOption;

import android.test.AndroidTestCase;

public class MenuFactoryTest extends AndroidTestCase {

    /**
     * Make sure that all options have an activity to open
     */
    public void testGetMenuOptions() {
        List<MenuOption> options = MenuOptionFactory.getMenuOptions();
        for (MenuOption option : options) {
            Assert.assertNotNull(option.getActivity());
        }
    }

}
