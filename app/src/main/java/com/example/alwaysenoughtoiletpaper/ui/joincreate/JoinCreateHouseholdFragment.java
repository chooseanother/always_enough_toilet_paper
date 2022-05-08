package com.example.alwaysenoughtoiletpaper.ui.joincreate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.databinding.FragmentJoinCreateHouseholdBinding;

public class JoinCreateHouseholdFragment extends Fragment {
    Button helpButton;

    private FragmentJoinCreateHouseholdBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJoinCreateHouseholdBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        helpButton = binding.buttonQuestionMark;
        helpButton.setOnClickListener(view -> {
//            Toast.makeText(getContext(), getContext().getText(R.string.household_code_help), Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getContext().getString(R.string.household_code_help_text)).setTitle(R.string.household_code_help_title);
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