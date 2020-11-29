package pl.tkadziolka.room.presentation.newuser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductItem> {

    private List<Product> list = new ArrayList<>();

    public ProductAdapter() {}

    public void update(List<Product> products) {
        list = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.view_product_item, parent, false);
        return new ProductItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductItem holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ProductItem extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView name;

        private ProductItem(View view) {
            super(view);
            image = view.findViewById(R.id.productImage);
            name = view.findViewById(R.id.productName);
        }

        void bind(Product product) {
            name.setText(product.name);
            if (product.image != null) image.setImageBitmap(product.image);

            image.setImageBitmap(product.image);
        }
    }
}