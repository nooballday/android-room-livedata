package com.naufal.googleroomexample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by Naufal on 16/03/2018.
 */

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user")
    LiveData<List<User>> listenChanges();

    @Insert
    void insertData(@NonNull User... users);


}
