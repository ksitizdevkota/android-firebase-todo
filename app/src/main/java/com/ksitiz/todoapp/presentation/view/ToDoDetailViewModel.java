package com.ksitiz.todoapp.presentation.view;

import androidx.lifecycle.MutableLiveData;

import com.ksitiz.todoapp.domain.usecases.GetToDoUseCase;
import com.ksitiz.todoapp.presentation.common.BaseViewModel;

public class ToDoDetailViewModel extends BaseViewModel {

    private GetToDoUseCase getToDoUseCase;
    public MutableLiveData<GetToDoViewState> getTodoLiveData = new MutableLiveData<>();

    public ToDoDetailViewModel(GetToDoUseCase getToDoUseCase) {
        this.getToDoUseCase = getToDoUseCase;
    }

    public void getToDo(String todoKey) {
        getTodoLiveData.setValue(new GetToDoViewState(GetToDoViewState.LOADING));
        addDisposable(getToDoUseCase.getTodo(todoKey)
                .subscribe(
                        todo -> {
                            GetToDoViewState getToDoViewState = new GetToDoViewState(GetToDoViewState.SUCCESS);
                            getToDoViewState.todo = todo;
                            getTodoLiveData.setValue(getToDoViewState);
                        },
                        error -> {
                            GetToDoViewState getToDoViewState = new GetToDoViewState(GetToDoViewState.ERROR);
                            getToDoViewState.throwable = error;
                            getTodoLiveData.setValue(getToDoViewState);
                        },
                        () -> getTodoLiveData.setValue(new GetToDoViewState(GetToDoViewState.COMPLETED))
                )
        );
    }
}
