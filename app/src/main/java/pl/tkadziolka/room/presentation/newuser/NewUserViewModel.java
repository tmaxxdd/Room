package pl.tkadziolka.room.presentation.newuser;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pl.tkadziolka.room.database.dao.ProfessionDao;
import pl.tkadziolka.room.database.dao.UserDao;
import pl.tkadziolka.room.database.model.Profession;
import pl.tkadziolka.room.database.model.User;
import pl.tkadziolka.room.database.model.UserWithProducts;

public class NewUserViewModel extends ViewModel {
    private final static String TAG = "NewUser";

    private CompositeDisposable disposables = new CompositeDisposable();

    private ProfessionDao professionDao;
    private UserDao userDao;

    private Disposable addUserDisposable;
    private Disposable updateUserDisposable;

    public NewUserViewModel(ProfessionDao professionDao, UserDao userDao) {
        this.professionDao = professionDao;
        this.userDao = userDao;
    }

    private MutableLiveData<List<Profession>> mutableProfessions = new MutableLiveData<>();
    public LiveData<List<Profession>> professions = mutableProfessions;

    private MutableLiveData<Boolean> mutableAddingState = new MutableLiveData<>();
    public LiveData<Boolean> addingState = mutableAddingState;

    private MutableLiveData<UserWithProducts> mutableLoadedUser = new MutableLiveData<>();
    public LiveData<UserWithProducts> loadedUser = mutableLoadedUser;

    void loadProfessions() {
        disposables.add(
            professionDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    professions -> mutableProfessions.setValue(professions),
                    error -> Log.e(TAG, "Couldn't load professions " + error)
                )
        );
    }

    void getUserWithProducts(long userId) {
        disposables.add(
            userDao.getUserWithProducts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    user -> mutableLoadedUser.setValue(user),
                    error -> Log.e(TAG, "Couldn't load user with products " + error)
                )
        );
    }

    void addUser(String name, int age, Boolean isMan, long professionId) {
        if (addUserDisposable != null && !addUserDisposable.isDisposed())
            return;

        User newUser = new User(0, name, isMan, age, professionId);

        addUserDisposable = userDao.add(newUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                () -> mutableAddingState.setValue(true),
                error -> {
                    Log.e(TAG, "Couldn't add user " + error);
                    mutableAddingState.setValue(false);
                }
            );

        disposables.add(addUserDisposable);
    }

    void updateUser(long userId, String name, int age, Boolean isMan, long professionId) {
        if (updateUserDisposable != null && !updateUserDisposable.isDisposed())
            return;

        User newUser = new User(userId, name, isMan, age, professionId);

        updateUserDisposable = userDao.update(newUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                () -> {
                    Log.e(TAG, "On update user id = " + userId);
                    mutableAddingState.setValue(true);
                },
                error -> {
                    Log.e(TAG, "Couldn't update user " + error);
                    mutableAddingState.setValue(false);
                }
            );
        disposables.add(updateUserDisposable);
    }
}
