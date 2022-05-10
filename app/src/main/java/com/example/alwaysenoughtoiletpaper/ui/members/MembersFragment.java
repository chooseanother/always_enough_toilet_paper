package com.example.alwaysenoughtoiletpaper.ui.members;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import com.example.alwaysenoughtoiletpaper.databinding.FragmentMembersBinding;
import com.example.alwaysenoughtoiletpaper.model.adapter.MemberAdapter;

public class MembersFragment extends Fragment {
    private MemberAdapter memberAdapter;
    private RecyclerView membersList;
    private FragmentMembersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MembersViewModel membersViewModel =
                new ViewModelProvider(this).get(MembersViewModel.class);

        binding = FragmentMembersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup recycler view
        membersList = binding.membersRV;
        membersList.hasFixedSize();
        membersList.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Setup adapter
        memberAdapter = new MemberAdapter(membersViewModel.getMembers().getValue());
        // TODO is this proper handling of onclick listener, should it call a method in the view model?
        memberAdapter.setOnClickListener((member, index, delete) -> {
            if (delete){
                Toast.makeText(root.getContext(), "Delete: " + member.getName(), Toast.LENGTH_SHORT).show();
            } else {
                ClipboardManager clipboardManager = (ClipboardManager) root.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Household code",member.getPhoneNumber());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(root.getContext(), R.string.members_copied_phone_number, Toast.LENGTH_SHORT).show();

            }
        });

        membersViewModel.getMembers().observe(getViewLifecycleOwner(), memberAdapter::setMembers);

        membersList.setAdapter(memberAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}