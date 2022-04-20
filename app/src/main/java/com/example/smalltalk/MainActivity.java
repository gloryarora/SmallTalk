package com.example.smalltalk;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import android.support.v4.view.ViewPager;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentPagerAdapter;


public class MainActivity extends AppCompatActivity {
private Toolbar mtoolbar;
private ViewPager2 myViewPager;
private TabLayout mytabLayout;
private TabsAccessorAdaptor mytabsaccesoradaptor;
private FirebaseUser currentUser;
private FirebaseAuth mAuth;
//private FragmentPagerAdapter myfa;


    //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        currentUser= mAuth.getCurrentUser();
        mtoolbar=(Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Small Talk");

        myViewPager=(ViewPager2) findViewById(R.id.main_tabs_pager);
        mytabsaccesoradaptor=new TabsAccessorAdaptor(getSupportFragmentManager());
        myViewPager.setAdapter(mytabsaccesoradaptor);
        mytabLayout=(TabLayout) findViewById(R.id.main_tabs);
        //mytabLayout.setupWithViewPager(myViewPager);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mytabLayout, myViewPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.Tab tab, int position) {
                // position of the current tab and that tab
            }
        });
        tabLayoutMediator.attach();




    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null)
        {
            SendUserToLoginActivity();
        }
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.main_logout_option)
        {
            mAuth.signOut();
            SendUserToLoginActivity();
        }
        if(item.getItemId()==R.id.main_settings_option)
        {

        }
        if(item.getItemId()==R.id.main_find_friends_option)
        {

        }
        return true;
    }
}
//org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8