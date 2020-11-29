package pl.tkadziolka.room.presentation.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.tkadziolka.room.database.dao.UserDao;
import pl.tkadziolka.room.database.model.User;
import pl.tkadziolka.room.database.model.UserWithProfession;

public class UserViewModel extends ViewModel {
    private final static String TAG = "UserViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final UserDao userDao;

    private MutableLiveData<List<UserWithProfession>> mutableUsers = new MutableLiveData<>();
    public LiveData<List<UserWithProfession>> users = mutableUsers;

    public UserViewModel(UserDao userDao) {
        this.userDao = userDao;

        disposables.add(
            userDao.observeUserWithProfession()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    users -> mutableUsers.setValue(users),
                    error -> Log.e(TAG, "Couldn't load users = " + error.toString())
                )
        );
    }

    public void deleteUser(UserWithProfession userWithProfession) {
        disposables.add(
            userDao.delete(userWithProfession.user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    () -> {},
                    error -> Log.e(TAG, "Couldn't delete users = " + error.toString())
                )
        );
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
