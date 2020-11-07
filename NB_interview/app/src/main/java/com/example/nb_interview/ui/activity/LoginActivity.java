package com.example.nb_interview.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.example.nb_interview.R;
import com.example.nb_interview.utils.AlertType;
import com.example.nb_interview.utils.Utils;
import com.example.nb_interview.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private LoginViewModel viewModel;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loadingDialog = Utils.getLoadingDialog(this);


        username = findViewById(R.id.txt_username);
        password = findViewById(R.id.txt_password);


        findViewById(R.id.ll_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateFields();
            }
        });


        viewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    loadingDialog.show();
                } else {
                    Utils.dismissDialog(loadingDialog);
                }
            }
        });


        viewModel.isSuccess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    finish();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                }
            }
        });


        viewModel.onError.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Utils.showAlert(LoginActivity.this, s, AlertType.ERROR);
            }
        });

    }


    private void validateFields() {
        if (!Utils.validateField(username, getString(R.string.empty_username))) {
            return;
        } else if (!Utils.validateField(password, getString(R.string.empty_password))) {
            return;
        } else {
            viewModel.login(username.getText().toString(), password.getText().toString());
        }
    }
}