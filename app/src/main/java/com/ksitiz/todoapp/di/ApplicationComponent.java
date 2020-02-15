package com.ksitiz.todoapp.di;


import com.ksitiz.todoapp.presentation.edit.EditToDoDetailFragment;
import com.ksitiz.todoapp.presentation.list.ToDoListActivity;
import com.ksitiz.todoapp.presentation.view.ToDoDetailFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(EditToDoDetailFragment editToDoDetailFragment);

    void inject(ToDoDetailFragment toDoDetailFragment);

    void inject(ToDoListActivity toDoListActivity);
}
