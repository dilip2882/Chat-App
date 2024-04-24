package com.dilip.chatapp;


import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dilip.chatapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Set Toolbar
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Chat App");

        // Initialize ViewPager
//        viewPager = findViewById(R.id.view_pager);

        // Create Fragment List
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new UpdatesFragment());
        fragments.add(new CallsFragment());
//        fragments.add(CallsFragment.newInstance()); // Assuming CallsFragment has a no-argument constructor

        // Create FragmentAdapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        binding.viewPager.setAdapter(adapter);
//        view_pager.setAdapter(adapter);


        /*
         Set an onNavigationItemSelectedListener on BottomNavigationView.
         Inside this listener, based on the selected item, programmatically switch the ViewPager to the corresponding fragment using its setCurrentItem() method.
         */
        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {  // Handle BottomNavigationView clicks
                if (item.getItemId() == R.id.chats) {
                    binding.viewPager.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.updates) {
                    binding.viewPager.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.calls) {
                    binding.viewPager.setCurrentItem(2);
                    return true;
                } else {
                    return false;
                }
            }
        });


        // Implement swipe listener
        /*
        added an OnPageChangeListener to the ViewPager to detect swipe events and update the selected item in the BottomNavigationView accordingly.
         */
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Optional code to handle scroll events
            }

            @Override
            public void onPageSelected(int position) {
                // Update BottomNavigationView selection based on current viewpager position
                binding.navView.setSelectedItemId(getSelectedItemId(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // Optional code to handle scroll state changes
            }

            private int getSelectedItemId(int position) {
                switch (position) {
                    case 0:
                        return R.id.chats;
                    case 1:
                        return R.id.updates;
                    case 2:
                        return R.id.calls;
                }
                return -1; // Handle potential out of bounds errors
            }
        });
    }
}
