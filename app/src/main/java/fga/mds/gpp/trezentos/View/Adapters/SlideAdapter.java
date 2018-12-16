package fga.mds.gpp.trezentos.View.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import fga.mds.gpp.trezentos.R;

public class SlideAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public int[] sildeImages = {
            R.drawable.tutorial_classes,
            R.drawable.tutorial_class,
            R.drawable.tutorial_tests,
            R.drawable.tutorial_explore
    };

    public String[] slideDesc = {
            "Na tela das salas você poderá pesquisar suas salas, assim como suas salas favoritas, além de ver todas as salas as quais está cadastrado",
            "Ao entrar na sua sala, você terá acesso a suas provas e as informações da sua sala",
            "Ao selecionar alguma prova você terá acesso aos estudantes da sua sala que fizeram a prova. Além de gerar grupos, salvar notas, encaminhar avaliações e ver os grupos que foram formados",
            "Na aba de explorar você pode ver todas as turmas, além de pesquisar as turmas e entrar nas mesmas"
    };


    @Override
    public int getCount() {
        return sildeImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_about,container,false);

        TextView tvInstruction = view.findViewById(R.id.tv_desc_instruction);
        ImageView imgInstruction = view.findViewById(R.id.iv_img_instruction);

        imgInstruction.setImageResource(sildeImages[position]);
        tvInstruction.setText(slideDesc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
