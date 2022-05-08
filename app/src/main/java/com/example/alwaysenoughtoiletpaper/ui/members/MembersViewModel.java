package com.example.alwaysenoughtoiletpaper.ui.members;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.alwaysenoughtoiletpaper.model.Member;

import java.util.ArrayList;
import java.util.List;

public class MembersViewModel extends ViewModel {
    // TODO Implement repository that gets data
    private MutableLiveData<List<Member>> members;

    public MembersViewModel() {
        PopulateMembers();
    }

    public LiveData<List<Member>> getMembers() {
        return members;
    }

    private void PopulateMembers(){
        members = new MutableLiveData<>();
        List<Member> testMembers = new ArrayList<>();
        testMembers.add(new Member("Kim Tranberg long long long long long long name","+4512345678"));
        testMembers.add(new Member("Markéta Lapčíková","+4587654321"));
        testMembers.add(new Member("Mongo Bongo","+4512341234"));
        testMembers.add(new Member("Steen Steensen Blicher","+4556785678"));
        testMembers.add(new Member("Allan Henriksen","+4587878787"));
        testMembers.add(new Member("Test Tester Testsen","+45918273645"));
        testMembers.add(new Member("Lorem Ipsum","+4545362718"));
        testMembers.add(new Member("Saepe Aut Dolor","+4543215678"));
        testMembers.add(new Member("Aut Repudiandae Velit","+4512211221"));
        testMembers.add(new Member("Molestias Ullam","+4534434334"));
        testMembers.add(new Member("Deleniti Facere","+4556655656"));

        members.setValue(testMembers);
    }
}