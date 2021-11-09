package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.HomeViewBinding;

public class HomeView extends Fragment {

    private static String TAG = "Home View Activity";

    private HomeViewBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = HomeViewBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"first button clicked");
//                NavHostFragment.findNavController(HomeView.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                // how can the data saved on a good way, backup- >write data in Json File (matrix which maps data on day)
                // for each week one Json file?
                // @todo: open pop up: "Do you want to answer some questions regarding your current emotion?"
                // @todo: if yes: open new page with questions
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}