package pl.tkadziolka.room.presentation.newoffer;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import pl.tkadziolka.room.App;
import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.dao.ProductDao;
import pl.tkadziolka.room.presentation.main.MainActivity;

public class NewOfferActivity extends AppCompatActivity {
    private NewOfferViewModel viewModel;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_offer);

        userId = getIntent().getLongExtra(MainActivity.EXTRA_USER_ID, 0);

        ProductDao productDao = ((App) getApplication()).db.productDao();
        viewModel = new NewOfferViewModel(productDao);

        findViewById(R.id.addAction).setOnClickListener(view -> addOffer());

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.addingState.observe(this, success -> {
            if (success) {
                finish();
            }
        });
    }

    private void addOffer() {
        String title = ((EditText) findViewById(R.id.offerTitle)).getText().toString();
        Double price = Double.parseDouble(((EditText) findViewById(R.id.offerPrice)).getText().toString());

        viewModel.add(title, price, userId);
    }
}