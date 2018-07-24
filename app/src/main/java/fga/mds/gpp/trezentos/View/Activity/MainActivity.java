package fga.mds.gpp.trezentos.View.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import fga.mds.gpp.trezentos.Controller.UserAccountControl;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Fragment.AboutFragment;
import fga.mds.gpp.trezentos.View.Fragment.ClassFragment;
import fga.mds.gpp.trezentos.View.Fragment.ExploreFragment;
import fga.mds.gpp.trezentos.View.Fragment.UserFragment;


public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private Fragment selectedFragment;
    private Toolbar toolbar = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                if(toolbar.getTitle().equals("Salas")){
                    ClassFragment classFragment = ClassFragment.getInstance();
                    classFragment.filterClassList(newText);

                } else if(toolbar.getTitle().equals("Explorar")){
                    ExploreFragment exploreFragment = ExploreFragment.getInstance();
                    exploreFragment.filterClassList(newText);
                }
                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                return false;
            }

        };

        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        int id = item.getItemId();
//        // Noinspection SimplifiableIfStatement
//        if(id == R.id.search_classes){
//            goClassScreen();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserAccountControl userAccountControl = UserAccountControl.getInstance(getApplicationContext());

        if(!userAccountControl.isLoggedUser()){
            userAccountControl.logOutUser();
            goSignInScreen();
        }

        selectedFragment = null;
        initToolbar();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                        .OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.salas_item:
                        toolbar.setTitle("Salas");
                        selectedFragment = ClassFragment.getInstance();
                        break;

                    case R.id.usuario_item:
                        toolbar.setTitle("Explorar");
                        selectedFragment = ExploreFragment.getInstance();
                        break;

                    case R.id.about_item:
                        toolbar.setTitle("Perfil");
                        selectedFragment = UserFragment.getInstance();
                        break;

                    case R.id.avaliacao_item:
                        toolbar.setTitle("Sobre");
                        selectedFragment = AboutFragment.getInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, selectedFragment);
                transaction.commit();
                return true;
            }

        });

        showClassFragment();

    }

    public void showClassFragment(){
        toolbar.setTitle("Salas");
        bottomNavigationView.setSelectedItemId(R.id.salas_item);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, ClassFragment.getInstance());
        transaction.commit();
    }


    private void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Salas");
        setSupportActionBar(toolbar);
    }

    private void goSignInScreen() {
        Intent intent = new Intent(MainActivity.this, SignInActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
