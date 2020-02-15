package com.ksitiz.todoapp.domain.usecases;

import com.ksitiz.todoapp.domain.Repository;
import com.ksitiz.todoapp.domain.common.Transformer;
import com.ksitiz.todoapp.domain.entities.ToDo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;

public class GetToDoUseCase  extends UseCase<ToDo> {

    private static final String PARAM_TODO_KEY = ":todo_key";

    private Repository repository;

    public GetToDoUseCase(Repository repository, Transformer transformer) {
        super(transformer);
        this.repository = repository;
    }

    public Observable<ToDo> getTodo(String todoKey){
        Map<String, Object> data = new HashMap<>();
        data.put(PARAM_TODO_KEY, todoKey);
        return observable(data);
    }


    @Override
    Observable<ToDo> createObservable(Map<String, Object> data) {
        return repository.getTodo((String)data.get(PARAM_TODO_KEY));
    }
}
