package com.untitledev.monitoreofamiliar.activities;

import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.widget.TextView;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.monitoreofamiliar.adapters.PagerAdapter;
import com.untitledev.monitoreofamiliar.fragments.ContactFragment;
import com.untitledev.monitoreofamiliar.fragments.MonitoringFragment;
import com.untitledev.monitoreofamiliar.services.DeviceService;
import com.untitledev.untitledev_module.controllers.ContactController;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    Fragment fragment;
    private List<Contact> listContact;
    public static Context CONTEXT_MAIN = null;
    public static int ID = 0;
    public static String NAME = "";
    private ApplicationPreferences appPreferences;
    private Response mResponse;
    private String json;
    private JSONObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        CONTEXT_MAIN = HomeActivity.this;
        appPreferences = new ApplicationPreferences();
        mResponse = new Response();
        json = appPreferences.getPreferenceString(CONTEXT_MAIN, Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER);
        jsonObject = mResponse.parseJsonObject(json);
        try {
            ID = jsonObject.getInt("id");
            NAME = jsonObject.getString("name");
            Log.i("ID", ""+ID+" NAME:"+NAME);
            Bundle userData = new Bundle();
            userData.putInt("id", ID);
            userData.putString("name", NAME);

            AccountManager accountManager = AccountManager.get(this); //this is Activity
            Account account = new Account("MonitoreoFamiliarAccount","com.untitledev.monitoreofamiliar.MonitoreoFamiliarAccount");
            boolean success = accountManager.addAccountExplicitly(account,"password", userData);
            if(success){
                Log.i("MonitoreoFamiliarTag","Account created");
            }else{
                Log.i("MonitoreoFamiliarTag","Account creation failed. Look at previous logs to investigate");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
                        fragment = new MonitoringFragment();
                        viewPager.setCurrentItem(1);
                        fratmentTransaction = true;
                        break;
                }

                if(fratmentTransaction){
                    changeFragment(fragment, item);
                    drawerLayout.closeDrawers();
                }
                return true;
            }
        });
        //Inicia el servicio para la captura de la posición.
        Intent deviceService = new Intent(CONTEXT_MAIN, DeviceService.class);
        startService(deviceService);
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
