package com.bizreport.consumer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bizreport.consumer.database.Company;
import com.bizreport.consumer.database.DatabaseHandler;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<Company> companies = DatabaseHandler.getInstance(this).getAllCompanies();
        companies.add(new Company("New Company Consumer Report"));
        final IDrawerItem[] items = buildDrawerItems(companies);
        new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(items)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(position == items.length-1){
                            new MaterialDialog.Builder(MainActivity.this)
                                    .title("New Company's Name")
                                    .positiveText("Yes")
                                    .negativeText("No")
                                    .show();
                        }
                        return true;
                    }
                })
                .build();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public IDrawerItem[] buildDrawerItems(ArrayList<Company> companies){
        ArrayList<IDrawerItem> items = new ArrayList<>();
        for(int i = 0; i < companies.size(); ++i){
            PrimaryDrawerItem item = i != companies.size()-1 ? new PrimaryDrawerItem().withName(companies.get(i).getName()) : new PrimaryDrawerItem().withName(companies.get(i).getName()).withIcon(R.drawable.plus);
            items.add(item);
        }
        return items.toArray(new IDrawerItem[items.size()]);
    }
}
