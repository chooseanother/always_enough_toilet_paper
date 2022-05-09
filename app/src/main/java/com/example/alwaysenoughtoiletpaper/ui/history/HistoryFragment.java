package com.example.alwaysenoughtoiletpaper.ui.history;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentHistoryBinding;
import com.example.alwaysenoughtoiletpaper.model.adapter.HistoryAdapter;
import com.example.alwaysenoughtoiletpaper.model.adapter.MemberAdapter;

public class HistoryFragment extends Fragment {

    private View root;
    private FragmentHistoryBinding binding;
    private HistoryViewModel viewModel;
    private HistoryAdapter historyAdapter;
    RecyclerView historyList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        // Setup recycler view
        historyList = binding.historyRV;
        historyList.hasFixedSize();
        historyList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Setup adapter
        historyAdapter = new HistoryAdapter(viewModel.getHistory().getValue());

        viewModel.getHistory().observe(getViewLifecycleOwner(), historyAdapter::setHistoryItems);
        historyList.setAdapter(historyAdapter);

        binding.historyFab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.history_delete_all_text));
            builder.setPositiveButton(R.string.history_delete_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getContext().getString(R.string.history_delete_sure));
                    builder.setPositiveButton(R.string.history_delete_yes_again, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(getContext().getString(R.string.history_delete_really_sure));
                            builder.setPositiveButton(R.string.history_delete_yes_goddamn, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    viewModel.deleteHistory();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
