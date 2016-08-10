package com.example.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.widget.HorizontalScrollView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.adapters.MyFragmentPageAdapter;
import com.example.suat.financialasistant.R;

import java.util.List;
import java.util.Vector;

public class LineFragment  extends Fragment implements OnTabChangeListener,OnPageChangeListener {

    MyFragmentPageAdapter myFragmentPageAdapter;
    TabHost graph_tabhost;
    ViewPager graphPager;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        v=inflater.inflate(R.layout.graphs_viewpager_tab_layout,container,false);
        this.initGraphTabHost();
        this.initGraphViewPager();

        return v;
    }

    private void initGraphViewPager(){
        List<Fragment> graphFrags=new Vector<>();
        List<LineGraph> graphs=new Vector<>();

        for (int j=0;j<12;j++){
            graphs.add(new LineGraph());
            graphs.get(j).setMoneyId(j+1);
        }

        for(int i=0;i<12;i++){
            graphFrags.add(graphs.get(i));
        }


        this.myFragmentPageAdapter = new MyFragmentPageAdapter(getChildFragmentManager(),graphFrags);
        this.graphPager=(ViewPager) v.findViewById(R.id.graph_pager);
        this.graphPager.setAdapter(this.myFragmentPageAdapter);
        this.graphPager.addOnPageChangeListener(this);
    }

    private void initGraphTabHost(){

        graph_tabhost=(TabHost) v.findViewById(android.R.id.tabhost);
        graph_tabhost.setup();
        String[] graphNames={"USD","AUD","DKK","EUR","GBP",
                "CHF","SEK","CAD","KWD","NOK","SAR","JPY"};
        TextView tx;
        for(int i=0;i<graphNames.length;i++){
            TabHost.TabSpec tabSpec;
            tabSpec= graph_tabhost.newTabSpec(graphNames[i]);
            tabSpec.setIndicator(graphNames[i]);
            tabSpec.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    View view = new View(getActivity());
                    view.setMinimumHeight(0);
                    view.setMinimumWidth(0);
                    return view;
                }
            });
            graph_tabhost.addTab(tabSpec);
        }

        for (int j=0;j<graphNames.length;j++){
            tx=(TextView) graph_tabhost.getTabWidget().getChildAt(j).findViewById(android.R.id.title);
            tx.setTextSize(10);
            graph_tabhost.getTabWidget().getChildAt(j).getLayoutParams().height=(int) (30 * this.getResources().getDisplayMetrics().density);
        }


        graph_tabhost.setOnTabChangedListener(this);


    }

    @Override
    public void onTabChanged(String tabId) {
        int pos=this.graph_tabhost.getCurrentTab();
        this.graphPager.setCurrentItem(pos);


        HorizontalScrollView hScrollView = (HorizontalScrollView) v
                .findViewById(R.id.h_scroll_view_graph);
        View graphView=graph_tabhost.getCurrentTabView();

        int scrollpos=graphView.getLeft()- (hScrollView.getWidth()-graphView.getWidth()) /2;
        hScrollView.smoothScrollTo(scrollpos,0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.graph_tabhost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



}
