package com.ksitiz.todoapp.presentation.common;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.ksitiz.todoapp.presentation.firebase.FirebaseUIUtil;
import com.ksitiz.todoapp.R;
import com.ksitiz.todoapp.presentation.list.ToDoListActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_sign_out) {
            showSignoutDialog(getString(R.string.sign_out_title), getString(R.string.sign_out_question));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void showSignoutDialog(String title, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_button),
                        (dialog, id) -> FirebaseUIUtil
                                .signOut(BaseActivity.this, new Intent (BaseActivity.this, ToDoListActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)))
                .setNegativeButton(getString(R.string.cancel_button),
                        (dialog, id) -> dialog.cancel());

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void showErrorDialog(Throwable throwable){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder
                .setTitle(throwable.getMessage())
                .setMessage(throwable.toString())
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok_button),
                        (dialog, id) -> dialog.dismiss());


        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
