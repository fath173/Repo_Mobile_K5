package com.fathor.pondokkopi.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.fathor.pondokkopi.Activity.Auth.AccountFragment;
import com.fathor.pondokkopi.Activity.Auth.LoginActivity;
import com.fathor.pondokkopi.Activity.Auth.Preferences;
import com.fathor.pondokkopi.Activity.Cart.CartFragment;
import com.fathor.pondokkopi.Activity.Orders.OrdersFragment;
import com.fathor.pondokkopi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public boolean statusLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusLogin = Preferences.getLogInStatus(getBaseContext());

        loadFragment (new HomeFragment());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.menu_home:
                fragment = new HomeFragment();
                break;
            case R.id.menu_testi:
                fragment = new TestiFragment();
                break;
            case R.id.menu_cart:
                if(statusLogin){
                    fragment = new CartFragment();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.menu_orders:
                if(statusLogin){
                    fragment = new OrdersFragment();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.menu_account:
                if(statusLogin){
                    fragment = new AccountFragment();
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
        }
        return loadFragment(fragment);
    }
}