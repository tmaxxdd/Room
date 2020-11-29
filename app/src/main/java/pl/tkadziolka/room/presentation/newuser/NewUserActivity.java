package pl.tkadziolka.room.presentation.newuser;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.tkadziolka.room.App;
import pl.tkadziolka.room.database.dao.ProfessionDao;
import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.dao.UserDao;
import pl.tkadziolka.room.database.model.Product;
import pl.tkadziolka.room.database.model.Profession;
import pl.tkadziolka.room.presentation.main.MainActivity;

public class NewUserActivity extends AppCompatActivity {
    private NewUserViewModel viewModel;

    private EditText name, age;
    private CheckBox isMan;
    private Spinner professionDropdown;
    private Button save;

    private boolean isInEditMode = false;
    private long userId = 0;

    private ProductAdapter productAdapter = new ProductAdapter();

    private final Bitmap bitmap = Bitmap.createBitmap(64, 64, Bitmap.Config.ALPHA_8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        bitmap.eraseColor(Color.BLACK);
        bindViews();

        userId = getIntent().getLongExtra("userId", 0);

        UserDao userDao = ((App) getApplication()).db.userDao();
        ProfessionDao professionDao = ((App) getApplication()).db.professionDao();
        viewModel = new NewUserViewModel(professionDao, userDao);

        observeViewModel();

        if (userId > 0) { isInEditMode = true; }

        if (isInEditMode)
            viewModel.getUserWithProducts(userId);

        viewModel.loadProfessions();
    }

    private void observeViewModel() {
        viewModel.professions.observe(this, professions ->
            updateProfessions(professions)
        );

        viewModel.addingState.observe(this, success -> {
            if (success) {
                finish();
            } else {
                Toast.makeText(
                    this,
                    "User couldn't have been added to database",
                    Toast.LENGTH_SHORT
                ).show();
            }
        });

        viewModel.loadedUser.observe(this, userWithProducts -> {
            name.setText(userWithProducts.user.name);
            age.setText(String.valueOf(userWithProducts.user.age));
            isMan.setChecked(userWithProducts.user.isMan);
            professionDropdown.setSelection((int) (userWithProducts.user.professionOwnerId - 1));
            productAdapter.update(mapBitmapToProducts(userWithProducts.products));
        });
    }

    private void addUser() {
        if (isInEditMode) {
            viewModel.updateUser(
                userId,
                name.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                isMan.isChecked(),
                professionDropdown.getSelectedItemPosition() + 1 // Professions start at 1
            );
            // TODO Missing 'else' word
        } else {
            viewModel.addUser(
                name.getText().toString(),
                Integer.parseInt(age.getText().toString()),
                isMan.isChecked(),
                professionDropdown.getSelectedItemPosition() + 1 // Professions start at 1
            );
        }

    }

    private void updateProfessions(List<Profession> professions) {
        String[] professionNames = new String[professions.size()];
        for (int i = 0; i < professions.size(); i++)
            professionNames[i] = professions.get(i).name;

        professionDropdown.setAdapter(
            new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, professionNames)
        );
    }

    private void bindViews() {
        name = findViewById(R.id.newUserName);
        age = findViewById(R.id.newUserAge);
        isMan = findViewById(R.id.newUserIsMan);
        professionDropdown = findViewById(R.id.professionDropdown);
        save = findViewById(R.id.saveAction);
        save.setOnClickListener(view -> addUser());
        RecyclerView list = findViewById(R.id.productList);
        list.setAdapter(productAdapter);
    }

    private List<Product> mapBitmapToProducts(List<Product> products) {
        for (Product product : products) {
            product.image = bitmap;
        }
        return products;
    }
}
