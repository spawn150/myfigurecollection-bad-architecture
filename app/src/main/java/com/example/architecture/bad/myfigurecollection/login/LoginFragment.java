package com.example.architecture.bad.myfigurecollection.login;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.ant_robot.mfc.api.pojo.UserProfile;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;

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
    private TextInputEditText editTextUsername;
    private TextInputEditText editTextPassword;

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

        editTextUsername = (TextInputEditText) view.findViewById(R.id.edit_text_username);
        editTextPassword = (TextInputEditText) view.findViewById(R.id.edit_text_password);

        Button buttonSignin = (Button) view.findViewById(R.id.button_signin);
        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(editTextUsername.getText().toString(), editTextPassword.getText().toString());
            }
        });
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

        MFCRequest.getInstance().connect(username, password, getActivity(), new MFCRequest.MFCCallback<Boolean>() {
            @Override
            public void success(Boolean aBoolean) {
                if (aBoolean) {
                    loadUserProfile();
                } else {
                    if (isAdded() && getActivity() != null) {
                        Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void error(Throwable throwable) {
                Log.e(TAG, "Error in connect()", throwable);
                if (isAdded() && getActivity() != null) {
                    Toast.makeText(getActivity(), "Connection error: " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUserProfile() {
        //TODO Remove hardcode user
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
