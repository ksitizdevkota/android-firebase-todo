package com.ksitiz.todoapp.presentation.edit;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.ksitiz.todoapp.presentation.common.BaseActivity;
import com.ksitiz.todoapp.R;

import static com.ksitiz.todoapp.presentation.view.ToDoDetailFragment.ARG_TODO_KEY;

/**
 * An activity representing a single Edit Item detail screen.
 */
public class EditToDoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String todoKey = getIntent().getStringExtra(ARG_TODO_KEY);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {

            EditToDoDetailFragment fragment = new EditToDoDetailFragment();
            if(todoKey != null){
                Bundle arguments = new Bundle();
                arguments.putString(ARG_TODO_KEY, todoKey);
                fragment.setArguments(arguments);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.edit_todo_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
