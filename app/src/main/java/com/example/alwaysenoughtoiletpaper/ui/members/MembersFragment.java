package com.example.alwaysenoughtoiletpaper.ui.members;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.databinding.FragmentMembersBinding;

import java.util.ArrayList;

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
                Toast.makeText(root.getContext(), "Phone: " + member.getPhoneNumber(), Toast.LENGTH_SHORT).show();
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