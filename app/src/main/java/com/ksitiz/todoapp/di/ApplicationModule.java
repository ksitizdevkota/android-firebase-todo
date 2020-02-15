package com.ksitiz.todoapp.di;

import android.content.Context;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ksitiz.todoapp.presentation.view.ToDoDetailVMFactory;
import com.ksitiz.todoapp.data.RepositoryImpl;
import com.ksitiz.todoapp.domain.Repository;
import com.ksitiz.todoapp.domain.common.AsyncTransformer;
import com.ksitiz.todoapp.domain.entities.ToDo;
import com.ksitiz.todoapp.domain.usecases.EditToDoUseCase;
import com.ksitiz.todoapp.domain.usecases.GetCurrentUserUseCase;
import com.ksitiz.todoapp.domain.usecases.GetToDoUseCase;
import com.ksitiz.todoapp.presentation.edit.EditToDoVMFactory;
import com.ksitiz.todoapp.presentation.list.ListVMFactory;
import com.ksitiz.todoapp.presentation.list.SimpleItemRecyclerViewAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        return firebaseDatabase;
    }

    @Provides
    @Singleton
    FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    @Singleton
    Repository provideRepository(FirebaseDatabase firebaseDatabase, FirebaseAuth firebaseAuth) {
        return new RepositoryImpl(firebaseAuth);
    }


    @Provides
    SimpleItemRecyclerViewAdapter provideAdapter(Repository repository) {
        Query todosQuery = repository.getQueryForUserTodos();

        FirebaseRecyclerOptions<ToDo> options = new FirebaseRecyclerOptions.Builder<ToDo>()
                .setQuery(todosQuery, ToDo.class)
                .build();

        return new SimpleItemRecyclerViewAdapter(options, repository);
    }

    @Provides
    @Singleton
    EditToDoUseCase provideEditTodoUseCase(Repository repository) {
        return new EditToDoUseCase(repository, new AsyncTransformer());
    }

    @Provides
    @Singleton
    GetToDoUseCase provideGetTodoUseCase(Repository repository) {
        return new GetToDoUseCase(repository, new AsyncTransformer());
    }


    @Provides
    @Singleton
    GetCurrentUserUseCase provideGetCurrentUserUseCase(Repository repository) {
        return new GetCurrentUserUseCase(repository, new AsyncTransformer());
    }

    @Provides
    @Singleton
    ListVMFactory provideListViewModelFactory(GetCurrentUserUseCase getCurrentUserUseCase) {
        return new ListVMFactory(getCurrentUserUseCase);
    }

    @Provides
    @Singleton
    EditToDoVMFactory provideEditTodoViewModelFactory(GetToDoUseCase getToDoUseCase, EditToDoUseCase editToDoUseCase) {
        return new EditToDoVMFactory(getToDoUseCase, editToDoUseCase);
    }

    @Provides
    @Singleton
    ToDoDetailVMFactory provideTodoDetailViewModelFactory(GetToDoUseCase getToDoUseCase) {
        return new ToDoDetailVMFactory(getToDoUseCase);
    }
}
