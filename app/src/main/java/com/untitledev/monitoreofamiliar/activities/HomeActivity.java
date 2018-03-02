package com.untitledev.monitoreofamiliar.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.monitoreofamiliar.adapters.PagerAdapter;
import com.untitledev.monitoreofamiliar.fragments.ContactFragment;
import com.untitledev.monitoreofamiliar.fragments.MonitoringFragment;
import com.untitledev.untitledev_module.controllers.ContactController;
import com.untitledev.untitledev_module.entities.Contact;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Fragment fragment;
    private List<Contact> listContact;
    public static Context CONTEXT_MAIN = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CONTEXT_MAIN = HomeActivity.this;
        //Mandar a llamar el toolbar una vez generado en el activity_main de la actividad
        setToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navView);


        //Cargamos al frame por default
        if(savedInstanceState == null) {
            //setFragmentDefault();
        }

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_contact));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_monitoring));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                if(position == 0){
                    changeDrawerMenu(position);
                }else {
                    changeDrawerMenu(position);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean fratmentTransaction = false;
                fragment = null;
                switch (item.getItemId()){
                    case R.id.menu_contact:
                        fragment = new ContactFragment();
                        viewPager.setCurrentItem(0);
                        fratmentTransaction = true;
                        break;
                    case R.id.menu_monitoring:
                        //fragment = new MonitoringFragment();
                        //viewPager.setCurrentItem(1);
                        //fratmentTransaction = true;
                        break;
                }

                if(fratmentTransaction){
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });



    }

    private void setFragmentDefault(){
        changeFragment(new ContactFragment(), navigationView.getMenu().getItem(0));
    }

    private void changeFragment(Fragment fragment, MenuItem item){
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }
    private void changeDrawerMenu(int position){
        MenuItem item = navigationView.getMenu().getItem(position);
        item.setChecked(true);
        getSupportActionBar().setTitle(item.getTitle());
    }

    private void setToolbar(){
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_dark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //Abrir el menu lateral
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.menu_add_contact:
                Intent intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
