package fga.mds.gpp.trezentos.View.Activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.SlideAdapter;

public class TutorialActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        linearLayout = findViewById(R.id.ll_items);
        viewPager = findViewById(R.id.vp_tutorial);

        slideAdapter = new SlideAdapter(getApplication());

        viewPager.setAdapter(slideAdapter);
        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);
    }

    private void addDotsIndicator(int posistion){
        mDots = new TextView[2];
        for (int i = 0; i < mDots.length; i++){
            mDots[i] = new TextView(getApplication());
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(0xcccccc);
        }
        if (mDots.length > 0 ){
            mDots[posistion].setTextColor(0xFFFFFF);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
