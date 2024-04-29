package com.dilip.chatapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dilip.chatapp.Adapters.TopStatusAdapter;
import com.dilip.chatapp.Adapters.UsersAdapter;
import com.dilip.chatapp.Models.User;
import com.dilip.chatapp.Models.UserStatus;
import com.dilip.chatapp.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    FragmentChatBinding binding; // View binding for the fragment
    FirebaseDatabase database; // Firebase database instance
    ArrayList<User> users; // List to store user data
    UsersAdapter usersAdapter; // Adapter for user data
    TopStatusAdapter statusAdapter;
    ArrayList<UserStatus> userStatuses;

    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(getLayoutInflater());
        View rootView = binding.getRoot(); // Get the root view from the binding

        // Initialize the database
        database = FirebaseDatabase.getInstance();

        users = new ArrayList<>(); // Initialize the list to store user data

        usersAdapter = new UsersAdapter(requireContext(), users); // Create the adapter
/*        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));   // already mentioned in layout*/
        binding.recyclerView.setAdapter(usersAdapter); // Set the adapter to the RecyclerView

        binding.recyclerView.showShimmerAdapter();


        // Listen for changes in the "users" node in the database
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear(); // Clear the existing user data
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    User user = snapshot1.getValue(User.class); // Deserialize user data
                    if (!user.getUid().equals(FirebaseAuth.getInstance().getUid())) {
                        users.add(user); // Add user to the list
                    }
                }
                binding.recyclerView.hideShimmerAdapter();
                usersAdapter.notifyDataSetChanged(); // Notify adapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });

        return rootView; // Return the inflated view
    }
}
