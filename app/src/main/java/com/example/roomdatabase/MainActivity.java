package com.example.roomdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewModel userViewModel;

    Usersadapter usersadapter;

    RecyclerView recyclerView;

    Button btnNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = new ViewModelProvider(this).get(ViewModel.class);
        usersadapter = new Usersadapter();
        recyclerView = findViewById(R.id.recyclerView);
        btnNewUser = findViewById(R.id.btnNewUser);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel.getAllUsers().observe(this, new Observer<List<Users>>() {
            @Override
            public void onChanged(List<Users> users) {

                if(users.size() > 0){
                    usersadapter.setData(users);
                    recyclerView.setAdapter(usersadapter);
                }

            }
        });
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUsers(MainActivity.this);
            }
        });
    }

    public void addUsers(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view1 = getLayoutInflater().inflate(R.layout.row_add_users, null);

        Button addUser = view1.findViewById(R.id.btnAddUser);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edUsers = view1.findViewById(R.id.edUsers);
                Users users = new Users();
                users.setUsername(edUsers.getText().toString());

                userViewModel.insertUsers(users);


            }
        });

        builder.setView(view1);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}