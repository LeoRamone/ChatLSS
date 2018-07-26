package com.leonardo_soares_soares.batepapolss.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.leonardo_soares_soares.batepapolss.fragment.ContatosFragment;
import com.leonardo_soares_soares.batepapolss.fragment.ConversasFragment;
/**
 * Created by Leonardo Soares on 17/07/18.
 * leonardo_soares_santos@outlook.com
 */
public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas ={"CONVERSAS","CONTATOS"};
    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= null;


        switch (position){

            case 0 :
                fragment= new ConversasFragment();
                break;
            case  1 :
                fragment= new ContatosFragment();
                break;

        }


        return fragment;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position){

        return tituloAbas[position];

    }
}
