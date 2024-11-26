package com.example.monstersappmodule8.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.monstersappmodule8.login.User;
import com.example.monstersappmodule8.login.UserDAO;
import com.example.monstersappmodule8.monster.Monster;
import com.example.monstersappmodule8.monster.MonsterDAO;
import com.example.monstersappmodule8.monster.review.Review;
import com.example.monstersappmodule8.monster.review.ReviewDAO;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Monster.class, User.class, Review.class},
        version = 1,
        exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MonstersRoomDatabase extends RoomDatabase{


    //DAO's sections
    public abstract MonsterDAO monsterDAO();
    public abstract UserDAO userDAO();
    public abstract ReviewDAO reviewDAO();

    //The volatile keyword ensures that updates to a variable are propagated predictably to other threads.
    private static volatile MonstersRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //This is a fixed thread pool we will use to run database operations asynchronously on a background thread
    //operations such as Insert, Delete, Update
    public static  final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //This method is what is called Singleton, this avoid creating more than 1 instance of the Database
    public static MonstersRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (MonstersRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), MonstersRoomDatabase.class, "monster_database")
                            .addCallback(roomCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                    //This statement is to force the database creation
                    INSTANCE.query("SELECT 1",null);
                }
            }
        }
        return INSTANCE;
    }

    public static Callback roomCallback = new Callback(){
        //onCreate will be call only the first time the database is created.
        //This is a great place to set up initial data in the database.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            populateInitialData(INSTANCE);
            Log.i("XYZ", "onCreate Called");
        }

        //onOpen will be called every time the database is opened
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.i("XYZ", "onOpen Called");
        }
    };

    private static void populateInitialData(MonstersRoomDatabase instance) {

        MonstersRoomDatabase.databaseWriteExecutor.execute( ()-> {
            UserDAO userDAO = instance.userDAO();
            //Create a new user
            userDAO.insert(new User("admin@ait.edu.au", "12345678", "admin", "admin", new Date() ));
            //Get the registered user
            User user1 = userDAO.findByEmail("admin@ait.edu.au");

            //Create another user
            userDAO.insert(new User("support@ait.edu.au", "12345678", "it", "it", new Date() ));
            //Get the registered user
            User user2 = userDAO.findByEmail("support@ait.edu.au");

            MonsterDAO monsterDao = instance.monsterDAO();
            //insert a monster
            Monster monster1 = new Monster("Damian", "I look terrific when I wake up", "monster_1", 5, 2, 2.5F, new Date());
            Long monsterId1 = monsterDao.insert(monster1);
            //insert a monster
            Monster monster2 = new Monster("Randall", "Under your bed, I am always there", "monster_2", 4, 1, 2.0F, new Date());
            Long monsterId2 = monsterDao.insert(monster2);

            //after the previous inserts, monster_1 and monster_2 will have the generated Id

            ReviewDAO reviewDAO = instance.reviewDAO();

            reviewDAO.insert(new Review(3, "It could be better", "It has a nice colour", new Date(), monsterId1.intValue(), user1.getId()));
            reviewDAO.insert(new Review(2, "Not scary at all", "I don't like it", new Date(), monsterId1.intValue(), user2.getId()));
            reviewDAO.insert(new Review(2, "Nothing to say...",  "It could be better", new Date(), monsterId2.intValue(), user1.getId()));

        });

    }
}


