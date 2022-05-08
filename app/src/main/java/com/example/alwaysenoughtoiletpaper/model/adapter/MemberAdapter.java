package com.example.alwaysenoughtoiletpaper.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alwaysenoughtoiletpaper.R;
import com.example.alwaysenoughtoiletpaper.model.Member;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    private List<Member> members;
    private OnClickListener listener;

    public MemberAdapter(List<Member> members) {
        this.members = members;
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        Member member = members.get(position);
        holder.memberName.setText(member.getName());
        holder.memberPhone.setText(member.getPhoneNumber());
        // TODO only show owner icon next to household owner
        // TODO only show delete icon next to member when household owner views the list
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public void setOnClickListener(OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView memberName;
        private final TextView memberPhone;
        private final ConstraintLayout member;
        private final ImageView owner;
        private final ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            memberName = itemView.findViewById(R.id.member_list_name);
            memberPhone = itemView.findViewById(R.id.member_list_phone);
            member = itemView.findViewById(R.id.member_list_member);
            owner = itemView.findViewById(R.id.member_list_owner);
            delete = itemView.findViewById(R.id.member_list_delete);
            // TODO set onclick listener for member, should copy phone number
            member.setOnClickListener(view -> {
                listener.OnClick(members.get(getBindingAdapterPosition()),getBindingAdapterPosition(),false);
            });
            // TODO set onclick listener for delete
            delete.setOnClickListener(view -> {
                listener.OnClick(members.get(getBindingAdapterPosition()),getBindingAdapterPosition(),true);
            });
        }
    }

    public interface OnClickListener {
        void OnClick(Member member, int index, boolean delete);
    }
}
