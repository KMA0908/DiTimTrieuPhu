package com.example.ditimtrieuphu.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ditimtrieuphu.entity.Question;
import com.example.ditimtrieuphu.roomdatabse.AppDatabase;
import com.example.ditimtrieuphu.roomdatabse.Repository;

import java.util.List;

public class QuestionViewModel extends ViewModel {
    public static List<Question> allQuestions;

    public static Repository repository;

    public QuestionViewModel(Context context) {
         repository= new Repository(context);
         allQuestions = repository.getAllQuestions();
    }
}
