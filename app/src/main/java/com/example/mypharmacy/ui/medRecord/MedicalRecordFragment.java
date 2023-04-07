package com.example.mypharmacy.ui.medRecord;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.mypharmacy.R;
import com.google.android.material.tabs.TabLayout;

public class MedicalRecordFragment extends Fragment {

    private MyPagerAdapter mAdapter;
    private ViewPager mViewPager;
    public MedicalRecordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medical_records, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }

}
