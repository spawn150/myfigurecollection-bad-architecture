package com.example.architecture.bad.myfigurecollection.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.ant_robot.mfc.api.pojo.UserProfile;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = LoginFragment.class.getName();
    private OnFragmentInteractionListener mListener;
    private ViewFlipper viewFlipper;
    private TextInputLayout inputLayoutUsername;
    private TextInputLayout inputLayoutPassword;
    private EditText editTextUsername;
    private EditText editTextPassword;

    private static final int EDITING = 0;
    private static final int LOADING = 1;

    @IntDef({LOADING, EDITING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputLayoutUsername = (TextInputLayout) view.findViewById(R.id.input_layout_username);
        inputLayoutPassword = (TextInputLayout) view.findViewById(R.id.input_layout_password);
        editTextUsername = (EditText) view.findViewById(R.id.edit_text_username);
        editTextPassword = (EditText) view.findViewById(R.id.edit_text_password);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper_login);

        Button buttonSignin = (Button) view.findViewById(R.id.button_signin);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButtonClicked();
            }
        });

        Button buttonSignup = (Button) view.findViewById(R.id.button_signup);
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupButtonClicked();
            }
        });

        Button buttonForgotPassword = (Button) view.findViewById(R.id.button_forgot_password);
        buttonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPasswordButtonClicked();
            }
        });

    }

    private void loginButtonClicked() {

        // First, clear errors
        inputLayoutUsername.setError(null);
        inputLayoutPassword.setError(null);

        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Set errors, if they exist
        boolean error = false;
        if (TextUtils.isEmpty(username)) {
            inputLayoutUsername.setError("This field is required");
            error = true;
        }
        if (TextUtils.isEmpty(password)) {
            inputLayoutPassword.setError("This field is required");
            error = true;
        }

        if (!error) {
            login(username, password);
        }
    }

    private void signupButtonClicked() {
        openMFCSite(getString(R.string.signup_url));
    }

    private void forgotPasswordButtonClicked() {
        openMFCSite(getString(R.string.forgotten_password_url));
    }

    private void openMFCSite(String url) {

        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);

    }

    public void signinSuccess() {
        if (mListener != null) {
            mListener.onSignin();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void login(String username, String password) {
        Log.d(TAG, "Username: " + username + " and Pwd: " + password);

        setLoginState(LOADING);

        MFCRequest.getInstance().connect(username, password, getActivity(), new MFCRequest.MFCCallback<Boolean>() {
            @Override
            public void success(Boolean aBoolean) {
                if (aBoolean) {
                    loadUserProfile();
                } else {
                    if (isAdded() && getActivity() != null) {
                        setLoginState(EDITING);
                        inputLayoutPassword.setError("Username or Password is incorrect");
                    }
                }
            }

            @Override
            public void error(Throwable throwable) {
                Log.e(TAG, "Error in connect()", throwable);
                if (isAdded() && getActivity() != null) {
                    setLoginState(EDITING);
                    Toast.makeText(getActivity(), "Connection error: " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUserProfile() {
        Call<UserProfile> call = MFCRequest.getInstance().getUserService().getUser(editTextUsername.getText().toString());
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (isAdded() && getActivity() != null) {
                    UserProfile userProfile = response.body();
                    Log.d(TAG, "Login Data [Name]: " + userProfile.getUser().getName());
                    Log.d(TAG, "Login Data [Pic ]: " + userProfile.getUser().getPicture());

                    SessionHelper.createSession(getActivity(), userProfile.getUser());
                    signinSuccess();
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e(TAG, "Error in getUser()", t);
                signinSuccess(); //TODO Reviews error management
            }
        });
    }

    private void setLoginState(@ViewState int viewState) {
        viewFlipper.setDisplayedChild(viewState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onSignin();
    }
}
