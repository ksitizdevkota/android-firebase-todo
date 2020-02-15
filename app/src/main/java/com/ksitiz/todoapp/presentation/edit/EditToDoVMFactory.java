package com.ksitiz.todoapp.presentation.edit;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.ksitiz.todoapp.domain.usecases.EditToDoUseCase;
import com.ksitiz.todoapp.domain.usecases.GetToDoUseCase;

@SuppressWarnings("unchecked")
public class EditToDoVMFactory implements ViewModelProvider.Factory {

    private EditToDoUseCase editToDoUseCase;
    private GetToDoUseCase getToDoUseCase;

    public EditToDoVMFactory(GetToDoUseCase getToDoUseCase, EditToDoUseCase editToDoUseCase) {
        this.editToDoUseCase = editToDoUseCase;
        this.getToDoUseCase = getToDoUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditToDoViewModel(getToDoUseCase, editToDoUseCase);
    }
}
