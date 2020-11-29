package pl.tkadziolka.room.presentation.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pl.tkadziolka.room.database.model.UserWithProfession;
import pl.tkadziolka.room.presentation.interfaces.OnItemClickListener;
import pl.tkadziolka.room.R;
import pl.tkadziolka.room.database.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private final OnItemClickListener<UserWithProfession> deleteListener;
    private final OnItemClickListener<UserWithProfession> clickListener;
    private List<UserWithProfession> users = new ArrayList();

    public UserAdapter(
        OnItemClickListener<UserWithProfession> deleteListener,
        OnItemClickListener<UserWithProfession> clickListener) {
        this.deleteListener = deleteListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.view_user_item, parent, false);
        return new UserHolder(view, deleteListener, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    void update(List<UserWithProfession> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView age;
        private final TextView profession;
        private final ImageView delete;
        private OnItemClickListener<UserWithProfession> deleteListener;
        private OnItemClickListener<UserWithProfession> clickListener;

        public UserHolder(
            @NonNull View itemView,
            OnItemClickListener<UserWithProfession> deleteListener,
            OnItemClickListener<UserWithProfession> clickListener
        ) {
            super(itemView);
            name = itemView.findViewById(R.id.userName);
            age = itemView.findViewById(R.id.userAge);
            delete = itemView.findViewById(R.id.userDelete);
            profession = itemView.findViewById(R.id.userProfession);
            this.deleteListener = deleteListener;
            this.clickListener = clickListener;
        }

        void bind(UserWithProfession userWithProfession) {
            name.setText(userWithProfession.user.name);
            age.setText("Age: " + userWithProfession.user.age);
            delete.setOnClickListener(view -> deleteListener.onClick(userWithProfession));
            profession.setText(userWithProfession.professions.get(0).name);
            itemView.setOnClickListener(view -> clickListener.onClick(userWithProfession));
        }
    }
}
