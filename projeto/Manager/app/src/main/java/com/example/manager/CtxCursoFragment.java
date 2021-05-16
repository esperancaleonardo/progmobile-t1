package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;

public class CtxCursoFragment extends Fragment {
    private ViewPager pagerTabLayoutCurso;
    private TabLayout tabLayoutCurso;
    private CtxCursoPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ctx_curso, container, false);
        pagerTabLayoutCurso = v.findViewById(R.id.pagerCurso);
        tabLayoutCurso = v.findViewById(R.id.tabCursoLay);
        adapter = new CtxCursoPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutCurso.getTabCount());
        adapter.notifyDataSetChanged();
        pagerTabLayoutCurso.setAdapter(adapter);
        pagerTabLayoutCurso.setCurrentItem(0);
        pagerTabLayoutCurso.setOffscreenPageLimit(2);

        tabLayoutCurso.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerTabLayoutCurso.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        pagerTabLayoutCurso.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutCurso));
        return v;
    }
}