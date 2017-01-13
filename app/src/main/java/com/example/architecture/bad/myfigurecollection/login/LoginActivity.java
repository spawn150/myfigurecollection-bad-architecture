package com.example.architecture.bad.myfigurecollection.login;

import android.app.Activity;
import android.os.Bundle;

import com.example.architecture.bad.myfigurecollection.MainActivity;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class LoginActivity extends Activity implements LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onSignin() {
        ActivityUtils.startActivityWithNewTask(this, MainActivity.class);
    }
}
