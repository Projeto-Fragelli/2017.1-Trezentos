package fga.mds.gpp.trezentos.View.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.About;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.AboutAdapter;
import fga.mds.gpp.trezentos.View.ViewHolder.AboutViewHolder;

public class AboutActivity extends AppCompatActivity {
    private ArrayList<About> about;
    private static AboutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final RecyclerView recyclerView =  findViewById(R.id.rv_about);

        aboutItem();
        RecyclerView.LayoutManager layout = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
    }

    public void aboutItem(){
        about = new ArrayList<>();
        try {
            about.add(new About(getResources().getString(R.string.about_site),getResources().getString(R.string.about_metodo_trezentos)));
            about.add(new About(getResources().getString(R.string.about_trezentos), getResources().getString(R.string.about_tedx)));
            about.add(new About(getResources().getString(R.string.about_metodologia),getResources().getString(R.string.about_entenda_metodo)));
            about.add(new About(getResources().getString(R.string.about_para_professores),getResources().getString(R.string.about_detalhes)));
            about.add(new About(getResources().getString(R.string.about_artigo), getResources().getString(R.string.about_pdf_download)));
            about.add(new About(getResources().getString(R.string.about_record),getResources().getString(R.string.about_reportagem)));
            about.add(new About(getResources().getString(R.string.about_unbtv),getResources().getString(R.string.about_reportagem)));
        }
        catch (UserException e){
            e.printStackTrace();
        }
        adapter = new AboutAdapter(about,this);
        adapter.setOnItemClickListener(callJoinClass());
    }

    public void openUrl(int position){
        Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
        String link = "";
        switch (position) {
            case 0:
                link = "http://metodo300.com";
                break;
            case 1:
                link = "https://youtu.be/gay6TYwVwf4";
                break;
            case 2:
                link = "https://youtu.be/s0g1AgGFP5k";
                break;
            case 3:
                link = "https://youtu.be/QLJtwsX8NqU";
                break;
            case 4:
                link = "http://www.scielo.br/pdf/er/n63/1984-0411-er-63-00253.pdf";
                break;
            case 5:
                link = "https://youtu.be/zQsaUjWw330";
                break;
            default:
                link = "https://youtu.be/7cfNcn-zge0";
        }
        setDataLink(link, myWebLink);
    }

    private void setDataLink(String link, Intent myWebLink){
        myWebLink.setData(Uri.parse(link));
        startActivity(myWebLink);
    }
    private AboutViewHolder.OnItemClickListener callJoinClass() {
        return new AboutViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                openUrl(position);
            }
        };
    }
}
