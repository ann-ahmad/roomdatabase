package com.example.roomdatabase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UsersRepository {

    private UsersDao usersDao;
    private Database database;
    private LiveData<List<Users>> userList;

    public UsersRepository(Application application) {
        database = Database.getDatabase(application);
        usersDao = database.userDao();
        userList = usersDao.getAllUsers();
    }

    public LiveData<List<Users>> getAllUsers() {
        return usersDao.getAllUsers();
    }

    public void insertUsers(final Users users){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids){
                database.userDao().insertUsers(users);
                return null;
            }
        }.execute();
    }

}
