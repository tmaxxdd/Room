package pl.tkadziolka.room.presentation.offer;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.model.Offer;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferItem> {

    private List<Offer> list = new ArrayList<>();

    public OfferAdapter() {}

    public void update(List<Offer> offers) {
        list = offers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OfferItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.view_offer_item, parent, false);
        return new OfferItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferItem holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class OfferItem extends RecyclerView.ViewHolder {
        private final TextView name, userName, date;

        public OfferItem(View view) {
            super(view);
            name = view.findViewById(R.id.offerName);
            userName = view.findViewById(R.id.offerUser);
            date = view.findViewById(R.id.offerDate);
        }

        void bind(Offer offer) {
            name.setText(offer.product.name);
            CharSequence timeSpan =
                DateUtils.getRelativeTimeSpanString(offer.product.timestamp.getTime());
            date.setText(timeSpan.toString());
            if (!offer.users.isEmpty()) {
                userName.setText(offer.users.get(0).name);
            } else {
                userName.setText("No user");
            }
        }
    }
}
