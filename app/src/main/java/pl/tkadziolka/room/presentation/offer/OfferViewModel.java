package pl.tkadziolka.room.presentation.offer;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import pl.tkadziolka.room.database.dao.OfferDao;
import pl.tkadziolka.room.database.model.Offer;

public class OfferViewModel extends ViewModel {
    private static final String TAG = "OfferViewModel";
    private final CompositeDisposable disposables = new CompositeDisposable();

    private MutableLiveData<List<Offer>> mutableOffers = new MutableLiveData<>();
    public LiveData<List<Offer>> offers = mutableOffers;

    public OfferViewModel(OfferDao offerDao) {
        disposables.add(offerDao.observe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                offers -> mutableOffers.setValue(offers),
                error -> Log.e(TAG, "Couldn't observe offers: " + error)
            )
        );
    }

    @Override
    protected void onCleared() {
        disposables.clear();
        super.onCleared();
    }
}
