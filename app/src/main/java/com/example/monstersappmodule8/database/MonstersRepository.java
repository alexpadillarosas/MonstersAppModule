package com.example.monstersappmodule8.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.monstersappmodule8.login.User;
import com.example.monstersappmodule8.login.UserDAO;
import com.example.monstersappmodule8.monster.Monster;
import com.example.monstersappmodule8.monster.MonsterDAO;
import com.example.monstersappmodule8.monster.review.Review;
import com.example.monstersappmodule8.monster.review.ReviewDAO;
import com.example.monstersappmodule8.monster.review.ReviewStatistics;
import com.example.monstersappmodule8.monster.reviews.ShowReview;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created to provide a single source of data
 * The repository will handle calls to data sources such:
 *  Local Database
 *  Remote Database
 *  API's
 */
public class MonstersRepository {

    //This section is used for DAO declarations
    private MonsterDAO monsterDAO;
    private UserDAO userDAO;
    private ReviewDAO reviewDAO;

    //This section is used to declare all data structures we will request from the repository
    private User user;
    private Monster monster;
    private Review review;
    private LiveData<List<Monster>> allMonsters;
    private ReviewStatistics reviewStatistics;

    private List<ShowReview> monstersReviews;

    //Monster's Repository Constructor
    public MonstersRepository(Application application) {
        /*  We use the application passed as argument and get the database  */
        MonstersRoomDatabase db = MonstersRoomDatabase.getDatabase(application);
        /* Request a DAO to perform data operations with a User */
        userDAO = db.userDAO();
        /* Request a DAO to perform data operations with Monsters */
        monsterDAO = db.monsterDAO();
        /* Request a DAO to perform data operations with Reviews */
        reviewDAO = db.reviewDAO();
        /*  We retrieve all monsters registered in the database, but wrapped in a liveData object to listen for changes in realtime */
        allMonsters = monsterDAO.findAll();

    }
    /*************************************************************
    Methods the APP will use when dealing with Users
     *************************************************************/
     public Long insert(User user){
//        MonstersRoomDatabase.databaseWriteExecutor.execute(()->{
//            userDAO.insert(user);
//        });
         Long userId;
         Callable<Long> c = () -> {
             return userDAO.insert(user);
         };
        //Future represents the result of an asynchronous computation
         Future<Long> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
         try {
             //get() will block until the result is ready
             userId = future.get();
         } catch (ExecutionException | InterruptedException e) {
             throw new RuntimeException(e);
         }
         return userId;
    }

    public void update(User user){
        MonstersRoomDatabase.databaseWriteExecutor.execute(()-> {
            userDAO.update(user);
        });
    }

    public void delete(User user){
        MonstersRoomDatabase.databaseWriteExecutor.execute(()->{
            userDAO.delete(user);
        });
    }

    /**
     * Retrieve a User from the database
     * @param email  : the user's email we request
     * @return    : The object with the requested user's information
     */
    public User findUserByEmail(String email) {
        //Callable is a way to pass a block of code as parameter, returning a User as result
        Callable<User> c = () -> {
           return userDAO.findByEmail(email);
        };
        //Future represents the result of an asynchronous computation
        Future<User> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            //get() will block until the result is ready
            user = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public User findUserById(Integer id){
        Callable<User> c = () -> {
            return userDAO.findById(id);
        };

        Future<User> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            user = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    /*************************************************************
     Methods the APP will use when dealing with Monsters
     *************************************************************/

    public void insert(Monster monster){
        MonstersRoomDatabase.databaseWriteExecutor.execute( () -> {
            monsterDAO.insert(monster);
        });
    }

    public void update(Monster monster){
        MonstersRoomDatabase.databaseWriteExecutor.execute(()-> {
            monsterDAO.update(monster);
        });
    }

    public void delete(Monster monster){
        MonstersRoomDatabase.databaseWriteExecutor.execute(()->{
            monsterDAO.delete(monster);
        });
    }

    /**
     *  Retrieve a Monster from the database
     * @param id  : the monster's id we request
     * @return    : The object with the requested monster's information
     */
    public Monster findMonsterById(Integer id){
        Callable<Monster> c = () -> {
            return monsterDAO.findById(id);
        };

        Future<Monster> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            monster = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return monster;
    }

    /**
     * Retrieve LiveData of all monsters
     * @return   : The List of all monsters in the MONSTER table
     */
    public LiveData<List<Monster>> getMonsters(){
        return allMonsters;
    }


    /*************************************************************
     Methods the APP will use when dealing with Reviews
     *************************************************************/

    public Long insert(Review review){
//        MonstersRoomDatabase.databaseWriteExecutor.execute( () -> {
//            reviewDAO.insert(review);
//        });
        Long reviewId;
        Callable<Long> c = () -> {
            return reviewDAO.insert(review);
        };
        //Future represents the result of an asynchronous computation
        Future<Long> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try {
            //get() will block until the result is ready
            reviewId = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return reviewId;

    }


//    public LiveData<ReviewStatistics> findReviewStatisticsByMonsterId(Integer monsterId){
//        Callable<LiveData<ReviewStatistics>> c = () -> {
//            return reviewDAO.findReviewStatisticsByMonsterId(monsterId);
//        };
//
//        Future<LiveData<ReviewStatistics>> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
//        try{
//            reviewStatistics = future.get();
//        } catch (ExecutionException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return reviewStatistics;
//
//    }

    /**
     * Find the total votes and average stars for a monster
     * @param monsterId : The Monster ID
     * @return  ReviewStatistics object containing the data
     */
    public ReviewStatistics findReviewStatisticsByMonsterId(Integer monsterId){
        Callable<ReviewStatistics> c = () -> {
            return reviewDAO.findReviewStatisticsByMonsterId(monsterId);
        };

        Future<ReviewStatistics> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            reviewStatistics = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return reviewStatistics;

    }

    /**
     * Find the specific Review done by a User on a Monster
     * @param monsterId :   The Monster ID
     * @param userId    :   The User ID
     * @return  Review Object containing the review data
     */
    public Review findReviewByMonsterAndUserId(Integer monsterId, Integer userId){
        Callable<Review> c = () -> {
            return reviewDAO.findByMonsterAndUserId(monsterId, userId);
        };

        Future<Review> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            review = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return review;
    }

    public List<ShowReview> findReviewsByMonsterId(Integer monsterId){
        Callable<List<ShowReview>> c = () -> {
            return reviewDAO.findReviewsByMonsterId(monsterId);
        };

        Future<List<ShowReview>> future = MonstersRoomDatabase.databaseWriteExecutor.submit(c);
        try{
            monstersReviews = future.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return monstersReviews;
    }
}
