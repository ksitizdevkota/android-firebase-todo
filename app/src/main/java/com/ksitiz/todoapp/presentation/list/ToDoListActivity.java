package com.ksitiz.todoapp.presentation.list;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.ksitiz.todoapp.TodoApplication;
import com.ksitiz.todoapp.presentation.common.BaseActivity;
import com.ksitiz.todoapp.presentation.edit.EditToDoDetailActivity;
import com.ksitiz.todoapp.presentation.firebase.FirebaseUIUtil;
import com.ksitiz.todoapp.R;
import com.ksitiz.todoapp.presentation.view.ToDoDetailActivity;

import javax.inject.Inject;
import javax.inject.Provider;

;

/**
 * An activity representing a list of ToDos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ToDoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ToDoListActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 101;

    @Inject
    ListVMFactory listVMFactory;

    ListViewModel listViewModel;

    @Inject
    Provider<SimpleItemRecyclerViewAdapter> adapterProvider;
    private SimpleItemRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        ((TodoApplication) getApplication()).applicationComponent.inject(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            overridePendingTransition(0, 0);
            Intent intent = new Intent(ToDoListActivity.this, EditToDoDetailActivity.class);
            startActivity(intent);
        });


        listViewModel = ViewModelProviders.of(this, listVMFactory).get(ListViewModel.class);
        listViewModel.listLiveData.observe(this, this::handleViewState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listViewModel.getCurrentUser();
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                listViewModel.getCurrentUser();
            } else {
                finish();
            }
        }
    }


    private void handleViewState(ListViewState listViewState) {
        int state = listViewState.state;
        switch (state) {
            case ListViewState.LOADING:
                break;
            case ListViewState.SUCCESS:
                setContent();
                break;
            case ListViewState.ERROR:
                startActivityForResult(FirebaseUIUtil.getAuthUIIntent(), RC_SIGN_IN);
                break;
            case ListViewState.COMPLETED:
                break;
        }
    }

    protected void setContent() {
        RecyclerView recyclerView = findViewById(R.id.todo_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerView);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = adapterProvider.get();
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}
