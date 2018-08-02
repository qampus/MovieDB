package me.papercom.moviedb.activity;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Loader;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import me.papercom.moviedb.R;

import java.util.ArrayList;

import me.papercom.moviedb.adapter.SearchAdapter;
import me.papercom.moviedb.async.SearchTask;
import me.papercom.moviedb.model.MovieItem;
import me.papercom.moviedb.async.NowPlayingTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<ArrayList<MovieItem>> {
    ListView listView;
    SearchAdapter adapter;
    static final String EXTRAS_FILM = "EXTRAS_FILM";
    ProgressDialog mProgress;

    private ArrayList<MovieItem> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new SearchAdapter(this);
        adapter.notifyDataSetChanged();
        listView = (ListView) findViewById(R.id.list_view);
        mProgress = new ProgressDialog(this);
        listView.setAdapter(adapter);

        String query = "";
        mProgress.setTitle("Loading...");
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        mProgress.show();
        //mProgress.setCancelable(false);
        Log.e("submit", "" + query);
        String judul = query;
        query = query.replaceAll(" ", "%20");
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_FILM, query);
        getLoaderManager().restartLoader(0, bundle, MainActivity.this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setQueryHint("Search Movie");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.e("change", "" + newText);
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mProgress.setTitle("Loading");
                mProgress.setMessage("Looking " + query + " . . .");
                mProgress.setCancelable(false);
                mProgress.show();
                //mProgress.setCancelable(false);
                Log.e("submit", "" + query);
                String judul = query;
                query = query.replaceAll(" ", "%20");
                Bundle bundle = new Bundle();
                bundle.putString(EXTRAS_FILM, query);
                getLoaderManager().restartLoader(0, bundle, MainActivity.this);
                return false;
            }


        });

        return true;
    }


    @Override
    public Loader<ArrayList<MovieItem>> onCreateLoader(int i, Bundle bundle) {
        String ListMovies = "";
        if (bundle != null) {
            ListMovies = bundle.getString(EXTRAS_FILM);
        }
        return new NowPlayingTask(this, ListMovies);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItem>> loader, ArrayList<MovieItem> filmItems) {
        adapter.setData(filmItems);
        mProgress.dismiss();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItem>> loader) {
        adapter.setData(null);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
