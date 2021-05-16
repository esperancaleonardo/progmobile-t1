package com.example.manager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.tabs.TabLayout;

public class CtxAlunoFragment extends Fragment {
    private ViewPager pagerTabLayoutAluno;
    private TabLayout tabLayoutAluno;
    private CtxAlunoPagerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ctx_aluno, container, false);
        pagerTabLayoutAluno = v.findViewById(R.id.pagerAluno);
        tabLayoutAluno = v.findViewById(R.id.tabAlunoLay);
        adapter = new CtxAlunoPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, tabLayoutAluno.getTabCount());
        adapter.notifyDataSetChanged();
        pagerTabLayoutAluno.setAdapter(adapter);
        pagerTabLayoutAluno.setCurrentItem(0);
        pagerTabLayoutAluno.setOffscreenPageLimit(2);

        tabLayoutAluno.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerTabLayoutAluno.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        pagerTabLayoutAluno.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutAluno));
        return v;
    }


}