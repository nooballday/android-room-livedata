package com.naufal.googleroomexample;

import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText mUserInput;
    private RecyclerView mUserList;
    private Button mAddBtn;

    private UserAdapter adapter;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getAppDatabase(this);

        mUserInput = findViewById(R.id.user_input);
        mUserList = findViewById(R.id.user_list);
        mAddBtn = findViewById(R.id.btn_add);

        adapter = new UserAdapter(db.userDao().getAll(), this);
        mUserList.setLayoutManager(new LinearLayoutManager(this));
        mUserList.setAdapter(adapter);

        mAddBtn.setOnClickListener(v -> {
            User user = new User();

            String nama = mUserInput.getText().toString().isEmpty() ? "Anonymous" : mUserInput.getText().toString();
            user.setName(nama);

            db.userDao().insertData(user);

            mUserInput.setText("");
        });


        db.userDao().listenChanges().observe(this, users -> adapter.updateData(db));

    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}
