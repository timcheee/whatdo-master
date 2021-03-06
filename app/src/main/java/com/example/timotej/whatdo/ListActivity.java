package com.example.timotej.whatdo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;
    MyApplication app;
    MyActionList actionList;
    boolean smartActivities = false;
    Button smartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        smartActivities = getIntent().getBooleanExtra("smart",false);
        smartButton = (Button) findViewById(R.id.smartActivitiesButton);

        if(smartActivities == false)
            smartButton.setText("Smart activites");
        else
            smartButton.setText("All activities");

        smartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //smartActivities = !smartActivities;
                Intent intent = getIntent();
                finish();
                intent.putExtra("smart",!smartActivities);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, AddLocationActivity.class));
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        app = (MyApplication) getApplication();
        if(!smartActivities)
            mAdapter = new MyAdapter(this,app.sez);
        else
            mAdapter = new MyAdapter(this,app.pametniSez);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
