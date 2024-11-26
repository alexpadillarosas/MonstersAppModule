package com.example.monstersappmodule8.login;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.monstersappmodule8.database.MonstersRepository;

public class LoginViewModel extends AndroidViewModel {

    //A reference to talk to the repository
    private final MonstersRepository monstersRepository;


    // Here we keep all properties to keep the UI state and some business logic
    // Its principal advantage is that it caches state and persists it through configuration changes.
    // This means that your UI doesn’t have to fetch data again when navigating between activities, or following configuration changes, such as when rotating the screen.
    private User loggedInUser;

    //Constructor
    public LoginViewModel(@NonNull Application application) {
        super(application);

        //create an instance of the Repository
        monstersRepository = new MonstersRepository(application);

    }

    // TODO: Implement the ViewModel
    public User getLoggedInUser() {
        return loggedInUser;
    }

//    public void setLoggedInUser(User loggedInUser) {
//        this.loggedInUser = loggedInUser;
//    }

    public Long insert(User user){
        return monstersRepository.insert(user);
    }

    public void update(User user){
        monstersRepository.update(user);
    }

    //This method can be used if a user would like to delete its account
    public void delete(User user){
        monstersRepository.delete(user);
    }

    public User findUserByEmail(String email){
        return monstersRepository.findUserByEmail(email);
    }

    public User findUserById(Integer id){
        return monstersRepository.findUserById(id);
    }

    /**
     *  Validate a
     * @param email     :   Email provided by the user
     * @param password  :   Password provided by the user
     * @return          :   True if the credentials match, else False
     */
    public void validateCredentials(String email, String password) throws UserNotFoundException, WrongPasswordException{

        //retrieve the user by using the provided email
        User userByEmail = findUserByEmail(email);
        if(userByEmail != null){
            if(userByEmail.getPassword().equals(password)){
                loggedInUser = userByEmail;
            }else{
                throw  new WrongPasswordException("Password does not match");
            }
        }else{
            throw new UserNotFoundException("User does not exist");
        }

    }

    public String fullName(User user){
        return user.getFirstname().concat(" ").concat(user.getLastname());
    }

    public Boolean userExists(User user) {
        Boolean exists = Boolean.FALSE;
        User existingUser = findUserByEmail(user.getEmail());
        if(existingUser != null){
            exists = Boolean.TRUE;
        }
        return exists;
    }

    public void registerUser(User user) {
        Long userId = insert(user);
        user.setId(userId.intValue());
        loggedInUser = user;
        Log.i("XYZ", loggedInUser.toString());
    }

}