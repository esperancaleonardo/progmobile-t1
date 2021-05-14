package com.example.manager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CtxAlunoPagerAdapter extends FragmentStatePagerAdapter {

    private int tabsCount;

    public CtxAlunoPagerAdapter(@NonNull FragmentManager fm, int behavior, int tabs) {
        super(fm, behavior);
        this.tabsCount = tabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CadAluno();
            case 1:
                return new EdtAluno();
            case 2:
                return new DropAluno();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}
