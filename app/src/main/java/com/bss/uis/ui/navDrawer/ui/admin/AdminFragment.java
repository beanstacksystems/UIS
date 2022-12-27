package com.bss.uis.ui.navDrawer.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bss.uis.R;

import java.util.Arrays;
import java.util.List;

public class AdminFragment extends Fragment {

    private List<String> entityList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_fragment, container, false);
        entityList = Arrays.asList("Counseller", "Admin","Doctor","SuperAdmin","guest", "chairman","Member", "Patient");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_user_list);
        UsernameListAdapter adapter = new UsernameListAdapter(entityList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return  view;
    }



}