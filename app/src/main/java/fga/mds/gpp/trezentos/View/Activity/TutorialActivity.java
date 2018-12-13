package fga.mds.gpp.trezentos.View.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.SlideAdapter;

public class TutorialActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private SlideAdapter slideAdapter;
    private TextView[] mDots;
    private ViewPager viewPager;

    private int currentPage;
    private Button btnNext;
    private Button btnPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        linearLayout = findViewById(R.id.ll_dots);
        viewPager = findViewById(R.id.vp_tutorial);
        btnPrevious = findViewById(R.id.btn_back);
        btnNext = findViewById(R.id.btn_next);
        slideAdapter = new SlideAdapter(this);

        viewPager.setAdapter(slideAdapter);
        addDotsIndicator(0);

        viewPager.addOnPageChangeListener(viewListener);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage == mDots.length - 1) {
                    Intent intent = new Intent(TutorialActivity.this,SignInActivity.class);
                    startActivity(intent);
                }
                else {
                    viewPager.setCurrentItem(currentPage + 1);
                }
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(currentPage -1);
            }
        });
    }

    private void addDotsIndicator(int posistion){
        mDots = new TextView[4];
        linearLayout.removeAllViews();
        for (int i = 0; i < mDots.length; i++){

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.whiteTransparent));

            linearLayout.addView(mDots[i]);

        }
        if (mDots.length > 0){
            mDots[posistion].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            currentPage = position;

            if (position == 0){
                btnNext.setEnabled(true);
                btnPrevious.setEnabled(false);
                btnPrevious.setVisibility(View.INVISIBLE);
                btnNext.setText("Próximo");
                btnPrevious.setText("");
            }
            else if (position == mDots.length -1) {
                btnPrevious.setEnabled(true);
                btnNext.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setText("Terminar");
                btnPrevious.setText("Voltar");
            }
            else {
                btnPrevious.setEnabled(true);
                btnNext.setEnabled(true);
                btnPrevious.setVisibility(View.VISIBLE);
                btnNext.setText("Próximo");
                btnPrevious.setText("Voltar");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
