package pl.tkadziolka.room.presentation.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import pl.tkadziolka.room.App;
import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.dao.OfferDao;
import pl.tkadziolka.room.database.dao.UserDao;
import pl.tkadziolka.room.database.model.User;
import pl.tkadziolka.room.presentation.newoffer.NewOfferActivity;
import pl.tkadziolka.room.presentation.offer.OfferAdapter;
import pl.tkadziolka.room.presentation.offer.OfferViewModel;
import pl.tkadziolka.room.presentation.user.UserViewModel;
import pl.tkadziolka.room.presentation.newuser.NewUserActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_USER_ID = "MainActivity.userId";
    private UserViewModel userViewModel;
    private OfferViewModel offerViewModel;

    private final UserAdapter userAdapter = new UserAdapter(
        userToDelete -> userViewModel.deleteUser(userToDelete),
        userClicked -> showUserOptions(userClicked.user)
    );

    private final OfferAdapter offerAdapter = new OfferAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.addUserAction).setOnClickListener(view -> goToNewUser());

        UserDao userDao = ((App) getApplication()).db.userDao();
        OfferDao offerDao = ((App) getApplication()).db.offerDao();
        userViewModel = new UserViewModel(userDao);
        offerViewModel = new OfferViewModel(offerDao);

        setupList();

        observeViewModel();
    }

    private void setupList() {
        RecyclerView userList = findViewById(R.id.userList);
        RecyclerView offerList = findViewById(R.id.offerList);
        userList.setAdapter(userAdapter);
        offerList.setAdapter(offerAdapter);
    }

    void observeViewModel() {
        userViewModel.users.observe(this, userAdapter::update);
        offerViewModel.offers.observe(this, offerAdapter::update);
    }

    private void showUserOptions(User user) {
        String[] options = {"Edit user", "Add product"};
        new AlertDialog.Builder(this)
            .setTitle("Select action")
            .setItems(options, (dialog, which) -> {
                switch (which) {
                    case 0: goToUser(user); break;
                    case 1: goToNewOffer(user); break;
                    default: break;
                }
            }).show();
    }

    private void goToNewUser() {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }

    private void goToUser(User user) {
        Intent intent = new Intent(this, NewUserActivity.class);
        // TODO Show this was changed to ' "userId" '
        intent.putExtra("userId", user.userId);
        startActivity(intent);
    }

    private void goToNewOffer(User user) {
        Intent intent = new Intent(this, NewOfferActivity.class);
        intent.putExtra(EXTRA_USER_ID, user.userId);
        startActivity(intent);
    }
}
