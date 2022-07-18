package com.example.thucphamxanh.Fragment.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.thucphamxanh.Model.User;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<User> user;

    public ProfileViewModel() {
        user = new MutableLiveData<>();
    }
    public void setUser(User user) {
        this.user.setValue(user);
    }
    public LiveData<User> getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ProfileViewModel{" +
                "user=" + user.toString() +
                '}';
    }
}
