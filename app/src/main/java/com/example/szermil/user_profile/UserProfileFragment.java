package com.example.szermil.user_profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.restaurant_search.RestaurantSearchActivity;
import com.example.szermil.user.mark.UserMarksActivity;

public class UserProfileFragment extends Fragment {

    public View view;

    public UserProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        setButtonListeners();

        return view;
    }
    private void setButtonListeners() {
        BootstrapLabel registerButton = view.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });
        BootstrapLabel yourRatingButton = view.findViewById(R.id.userRatingButton);
        yourRatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), UserMarksActivity.class);
            startActivity(intent);
        });
    }

}
