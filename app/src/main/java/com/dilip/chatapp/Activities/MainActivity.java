package com.dilip.chatapp.Activities;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dilip.chatapp.Fragments.CallsFragment;
import com.dilip.chatapp.Fragments.ChatFragment;
import com.dilip.chatapp.Adapters.FragmentAdapter;
import com.dilip.chatapp.R;
import com.dilip.chatapp.Fragments.UpdatesFragment;
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
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ChatFragment());
        fragments.add(new UpdatesFragment());
        fragments.add(new CallsFragment());

        // Create FragmentAdapter
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        binding.viewPager.setAdapter(adapter);

        // Set an onNavigationItemSelectedListener on BottomNavigationView
        binding.navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                    default:
                        return -1; // Handle potential out of bounds errors
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Toast.makeText(this, "Search clicked.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.group) {
            Toast.makeText(this, "Groups clicked.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.invite) {
            Toast.makeText(this, "Invite clicked.", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.setting) {
            Toast.makeText(this, "Settings clicked.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
