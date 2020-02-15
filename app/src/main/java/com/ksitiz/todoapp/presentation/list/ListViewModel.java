package com.ksitiz.todoapp.presentation.list;

import androidx.lifecycle.MutableLiveData;

import com.ksitiz.todoapp.domain.usecases.GetCurrentUserUseCase;
import com.ksitiz.todoapp.presentation.common.BaseViewModel;

import static com.ksitiz.todoapp.presentation.list.ListViewState.*;

public class ListViewModel extends BaseViewModel {

    private GetCurrentUserUseCase getCurrentUserUseCase;
    public MutableLiveData<ListViewState> listLiveData = new MutableLiveData<>();

    public ListViewModel(GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }


    public void getCurrentUser() {
        listLiveData.setValue(new ListViewState(LOADING));
        addDisposable(getCurrentUserUseCase.getCurrentUser()
                .subscribe(
                        user -> {
                            ListViewState listViewState = new ListViewState(SUCCESS);
                            listLiveData.setValue(listViewState);
                        },
                        error -> {
                            ListViewState listViewState = new ListViewState(ERROR);
                            listViewState.throwable = error;
                            listLiveData.setValue(listViewState);
                        },
                        () -> listLiveData.setValue(new ListViewState(COMPLETED))
                )
        );
    }
}
