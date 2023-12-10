package com.example.moneymanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SpendingAdapter extends RecyclerView.Adapter<SpendingAdapter.ViewHolder> {
    public static final int Request = 1002;

    private LayoutInflater inflater;
    private List<Note> notes;
    private DBManager dbManager;

    SpendingAdapter(Context context, List<Note> persons) {
        this.notes = persons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = inflater.inflate(R.layout.edit_note, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Note note = notes.get(position);
        if (note.getCategory().equals(Categories.PRODUCTS.getCategoryName())) {
            viewHolder.categories = Categories.PRODUCTS;

        } else if (note.getCategory().equals(Categories.CAFE.getCategoryName())) {
            viewHolder.categories = Categories.CAFE;
        } else if (note.getCategory().equals(Categories.FAMILY.getCategoryName())) {
            viewHolder.categories = Categories.FAMILY;

        } else if (note.getCategory().equals(Categories.GIFTS.getCategoryName())) {
            viewHolder.categories = Categories.GIFTS;

        } else if (note.getCategory().equals(Categories.HEALTH.getCategoryName())) {
            viewHolder.categories = Categories.HEALTH;

        } else if (note.getCategory().equals(Categories.LEISURE.getCategoryName())) {
            viewHolder.categories = Categories.LEISURE;

        } else if (note.getCategory().equals(Categories.TRANSPORT.getCategoryName())) {
            viewHolder.categories = Categories.TRANSPORT;

        } else if (note.getCategory().equals(Categories.PURCHASES.getCategoryName())) {
            viewHolder.categories = Categories.PURCHASES;
        }
        updateNameImage(viewHolder);
        viewHolder.spendingDate.setText(note.getDate());
        viewHolder.inputPrice.setText(Double.toString(note.getPrice()));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Storage.dbManager.deleteNote(note);
                ManagerActivity managerActivity = (ManagerActivity) v.getContext();
                Storage.notes.remove(note);
                Storage.currentUserNotes.remove(note);
                Storage.resulOfFindNotes.remove(note);
                managerActivity.updateAll();
            }
        });
        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!viewHolder.inputPrice.getText().toString().equals("")) {
                    note.setPrice(Double.parseDouble(viewHolder.inputPrice.getText().toString()));
                    Storage.dbManager.updateNote(note);
                    ManagerActivity managerActivity = (ManagerActivity) v.getContext();
                    managerActivity.updateSpending();
                }
            }
        });

        viewHolder.left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewHolder.categories) {
                    case CAFE: {
                        viewHolder.categories = Categories.GIFTS;
                        break;
                    }
                    case GIFTS: {
                        viewHolder.categories = Categories.FAMILY;
                        break;
                    }
                    case FAMILY: {
                        viewHolder.categories = Categories.HEALTH;
                        break;
                    }
                    case HEALTH: {
                        viewHolder.categories = Categories.LEISURE;
                        break;
                    }
                    case LEISURE: {
                        viewHolder.categories = Categories.PRODUCTS;
                        break;
                    }
                    case PRODUCTS: {
                        viewHolder.categories = Categories.PURCHASES;
                        break;
                    }
                    case PURCHASES: {
                        viewHolder.categories = Categories.TRANSPORT;
                        break;
                    }
                    case TRANSPORT: {
                        viewHolder.categories = Categories.CAFE;
                        break;
                    }

                }
                note.setCategory(viewHolder.categories.getCategoryName());
                Storage.dbManager.updateNote(note);
                updateNameImage(viewHolder);
            }
        });
        viewHolder.right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (viewHolder.categories) {
                    case CAFE: {
                        viewHolder.categories = Categories.TRANSPORT;
                        break;
                    }
                    case GIFTS: {
                        viewHolder.categories = Categories.CAFE;
                        break;
                    }
                    case FAMILY: {
                        viewHolder.categories = Categories.GIFTS;
                        break;
                    }
                    case HEALTH: {
                        viewHolder.categories = Categories.FAMILY;
                        break;
                    }
                    case LEISURE: {
                        viewHolder.categories = Categories.HEALTH;
                        break;
                    }
                    case PRODUCTS: {
                        viewHolder.categories = Categories.LEISURE;
                        break;
                    }
                    case PURCHASES: {
                        viewHolder.categories = Categories.PRODUCTS;
                        break;
                    }
                    case TRANSPORT: {
                        viewHolder.categories = Categories.PURCHASES;
                        break;
                    }

                }
                note.setCategory(viewHolder.categories.getCategoryName());
                Storage.dbManager.updateNote(note);
                updateNameImage(viewHolder);
            }
        });
    }
    public void updateNameImage(ViewHolder viewHolder) {
        switch (viewHolder.categories) {
            case CAFE: {
                viewHolder.categoryName.setText("Кафе");
                viewHolder.categoryImage.setImageResource(R.drawable.cafe);
                break;
            }
            case GIFTS: {
                viewHolder.categoryName.setText("Подарки");
                viewHolder.categoryImage.setImageResource(R.drawable.podarki);
                break;
            }
            case FAMILY: {
                viewHolder.categoryName.setText("Семья");
                viewHolder.categoryImage.setImageResource(R.drawable.semya);
                break;
            }
            case HEALTH: {
                viewHolder.categoryName.setText("Здоровье");
                viewHolder.categoryImage.setImageResource(R.drawable.zdorovie);
                break;
            }
            case LEISURE: {
                viewHolder.categoryName.setText("Досуг");
                viewHolder.categoryImage.setImageResource(R.drawable.dosug);
                break;
            }
            case PRODUCTS: {
                viewHolder.categoryName.setText("Продукты");
                viewHolder.categoryImage.setImageResource(R.drawable.eda);
                break;
            }
            case PURCHASES: {
                viewHolder.categoryName.setText("Покупки");
                viewHolder.categoryImage.setImageResource(R.drawable.pokupki);
                break;
            }
            case TRANSPORT: {
                viewHolder.categoryName.setText("Транспорт");
                viewHolder.categoryImage.setImageResource(R.drawable.transport);
                break;
            }

        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName, spendingDate;
        ImageView categoryImage, left, right;
        EditText inputPrice;
        Button save, delete;
        Categories categories;

        ViewHolder(View view) {
            super(view);
            categoryImage = view.findViewById(R.id.categoryImage);
            categoryName = view.findViewById(R.id.categoryName);
            inputPrice = view.findViewById(R.id.inputPrice);
            spendingDate = view.findViewById(R.id.date);
            save = view.findViewById(R.id.save);
            delete = view.findViewById(R.id.delete);
            left = view.findViewById(R.id.left);
            right = view.findViewById(R.id.right);
        }
    }
}
