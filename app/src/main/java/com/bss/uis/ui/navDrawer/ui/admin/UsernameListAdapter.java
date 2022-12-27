package com.bss.uis.ui.navDrawer.ui.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bss.uis.R;

import java.util.List;

public class UsernameListAdapter extends RecyclerView.Adapter<UsernameListAdapter.ViewHolder> {
    private List<String> entityList;

    public UsernameListAdapter(List<String> entityListFrag) {
        this.entityList = entityListFrag;
    }

    @NonNull
    @Override
    public UsernameListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.user_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsernameListAdapter.ViewHolder holder, int position) {
        holder.tvEntityType.setText(entityList.get(position));
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEntityType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEntityType = itemView.findViewById(R.id.tv_entity_type);
        }
    }
}
