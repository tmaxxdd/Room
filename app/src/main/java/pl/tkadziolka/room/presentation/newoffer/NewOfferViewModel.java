package pl.tkadziolka.room.presentation.newoffer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Date;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.tkadziolka.room.database.dao.ProductDao;
import pl.tkadziolka.room.database.model.Product;
import pl.tkadziolka.room.database.model.ProductUserCrossRef;

public class NewOfferViewModel extends ViewModel {
    private static final String TAG = "NewOfferViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();

    private ProductDao productDao;

    private MutableLiveData<Boolean> mutableAddingState = new MutableLiveData<>();
    public LiveData<Boolean> addingState = mutableAddingState;

    public NewOfferViewModel(ProductDao productDao) {
        this.productDao = productDao;
    }

    void add(String title, Double price, long userId) {
        disposables.add(productDao.add(title, price)
            .flatMapCompletable(productId -> addProductWithUser(userId, productId))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                () -> mutableAddingState.setValue(true),
                error -> {
                    Log.e(TAG, "Couldn't add product: " + error);
                    mutableAddingState.setValue(false);
                }
            )
        );
    }

    private Completable addProductWithUser(long userId, long productId) {
        return productDao.addWithUser(new ProductUserCrossRef(userId, productId));
    }

    @Override
    protected void onCleared() {
        disposables.dispose();
        super.onCleared();
    }
}
