/**
 * Created by Vimal on August-2021.
 */
package com.vimal.recyclerviewpaginationloader.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.vimal.recyclerviewpaginationloader.Api.model.Source;
import com.vimal.recyclerviewpaginationloader.Api.model.SourceMain;
import com.vimal.recyclerviewpaginationloader.Api.viewmodel.SourceViewModel;
import com.vimal.recyclerviewpaginationloader.MyApp;
import com.vimal.recyclerviewpaginationloader.R;
import com.vimal.recyclerviewpaginationloader.adapter.CategoryDataPagination;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFiles;
    ImageView norecord;
    CategoryDataPagination filesAdapter;
    int pageno = 1;
    int Categoryid = 3;
    List<Source> sourceList = new ArrayList<>();
    boolean isLoading = false;
    ShimmerFrameLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();

        Log.e("vml", "onCreateView" + isLoading);

        recyclerViewFiles.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                Log.e("vml", "onScrollStateChanged");

                Log.e("vml", "addOnScrollListener" + isLoading);

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == sourceList.size() - 1) {
                        //bottom of list!
                        isLoading = true;
                        pageno = pageno + 1;
                        LoadMore(pageno);

                        Toast.makeText(MainActivity.this, "Bottom of page " + pageno + " reached", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.e("vml", "onScrollStateChanged" + isLoading);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    public void findViewById() {
        norecord = findViewById(R.id.norecord);
        shimmerLayout = findViewById(R.id.shimmerLayout);
        recyclerViewFiles = findViewById(R.id.recyclerViewFiles);
        recyclerViewFiles.setHasFixedSize(true);
        recyclerViewFiles.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        if (isConnected()) {

            SourceViewModel sourceViewModel = ViewModelProviders.of(this).get(SourceViewModel.class);
            sourceViewModel.getSource(Categoryid, 1).observe(MainActivity.this, new Observer<SourceMain>() {
                @Override
                public void onChanged(@Nullable SourceMain heroList) {
                    if (heroList != null && heroList.getSucess()) {
                        shimmerLayout.setVisibility(View.GONE);
                        norecord.setVisibility(View.GONE);
                        recyclerViewFiles.setVisibility(View.VISIBLE);

                        sourceList = heroList.getData();

                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        realm.delete(Source.class);
                        realm.copyToRealmOrUpdate(sourceList);
                        realm.commitTransaction();

                        filesAdapter = new CategoryDataPagination(MainActivity.this, sourceList);
                        recyclerViewFiles.setAdapter(filesAdapter);
//                        recyclerViewFiles.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_left_to_right));
                        isLoading = false;

                    } else {
                        shimmerLayout.setVisibility(View.GONE);
                        Glide.with(MainActivity.this).asGif().load(R.raw.nodatafound).into(norecord);
                        norecord.setVisibility(View.VISIBLE);
                        recyclerViewFiles.setVisibility(View.GONE);
                    }

                }
            });
        }else {
            RealmResults<Source> tempList = MyApp.realm.where(Source.class).findAll();
            sourceList = tempList;
            shimmerLayout.setVisibility(View.GONE);
            filesAdapter = new CategoryDataPagination(MainActivity.this, sourceList);
            recyclerViewFiles.setAdapter(filesAdapter);
//            recyclerViewFiles.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_left_to_right));

        }
    }

    public void LoadMore(int pageno) {


        Source source = new Source();
        source.setStatus("load");
        sourceList.add(source);
        filesAdapter.notifyItemInserted(sourceList.size() - 1);
        SourceViewModel sourceViewModel = ViewModelProviders.of(this).get(SourceViewModel.class);
        sourceViewModel.getSource(Categoryid, pageno).observe(MainActivity.this, new Observer<SourceMain>() {
            @Override
            public void onChanged(@Nullable SourceMain heroList) {
                if (heroList != null && heroList.getSucess()) {
                    isLoading = false;
                    sourceList.remove(sourceList.size() - 1);
                    sourceList.addAll(heroList.getData());
                    filesAdapter.notifyDataChanged();
                } else {
                    sourceList.remove(sourceList.size() - 1);
                    filesAdapter.notifyDataChanged();
                    isLoading = true;
                    Log.e("vml", "0 size");
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        isLoading = false;
        pageno = 1;
    }
}