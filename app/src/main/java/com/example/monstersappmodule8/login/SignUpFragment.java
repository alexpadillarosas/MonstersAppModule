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
import com.example.monstersappmodule8.databinding.SignUpFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;


public class SignUpFragment extends Fragment {

    private SignUpFragmentBinding binding;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.sign_up_fragment, container, false);
        binding = SignUpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);


        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = binding.firstNameTIL.getEditText().getText().toString();
                String lastname = binding.lastNameTIL.getEditText().getText().toString();
                String email = binding.emailTIL.getEditText().getText().toString();
                String password = binding.passwordTIL.getEditText().getText().toString();
                String passwordConfirmation = binding.confirmedPasswordTIL.getEditText().getText().toString();
                if(firstname.isBlank()){
                    Snackbar.make(view, "Firstname is mandatory", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(lastname.isBlank()){
                    Snackbar.make(view, "Lastname is mandatory", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(email.isBlank()){
                    Snackbar.make(view, "Email is mandatory", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.isBlank()){
                    Snackbar.make(view, "Password cannot be empty or contain only empty spaces", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(passwordConfirmation.isBlank()){
                    Snackbar.make(view, "Confirmed Password cannot be empty or contain only empty spaces", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(!password.equals(passwordConfirmation)){
                    Snackbar.make(view, "Password and Confirmed password have different values", Snackbar.LENGTH_LONG).show();
                    return;
                }

                User user = new User(email, password, firstname, lastname, new Date());

                if(loginViewModel.userExists(user)){
                    Snackbar.make(view, "User Already exists", Snackbar.LENGTH_LONG).show();
                    return;
                }
                loginViewModel.registerUser(user);

                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_signUpFragment_to_showMonstersFragment);
            }
        });

        binding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}