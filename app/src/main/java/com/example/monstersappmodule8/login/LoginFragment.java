package com.example.monstersappmodule8.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.monstersappmodule8.R;
import com.example.monstersappmodule8.databinding.LoginFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;
    private LoginFragmentBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.login_fragment, container, false);
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
//        // TODO: Use the ViewModel
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Notice here: We need to user requireActivity() instead of this, as we are going to share
        //this viewModel with other fragments.
        //usually we need 1 view Model per Fragment, but here we share this view model as it contains the user information.
        //The fragment that will share the view model will act in the same way.
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);


        binding.loginLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmailTIL.getEditText().getText().toString();
                String password = binding.loginPasswordTIL.getEditText().getText().toString();
                if (email.isBlank()) {
                    Snackbar.make(view, "Email is mandatory", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (password.isBlank()) {
                    Snackbar.make(view, "Password cannot be empty or contain only empty spaces", Snackbar.LENGTH_LONG).show();
                    return;
                }
                try {
                    mViewModel.validateCredentials(email, password);
                } catch (UserNotFoundException e) {
                    Snackbar.make(view, "User does not exist", Snackbar.LENGTH_LONG).show();
                    return;
                } catch (WrongPasswordException e) {
                    Snackbar.make(view, "Wrong password", Snackbar.LENGTH_LONG).show();
                    return;
                }
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_showMonstersFragment);
            }
        });

        binding.loginSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_loginFragment_to_signUpFragment);
            }
        });

    }
}