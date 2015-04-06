package com.josiebealle.asst2try3;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public static final int VIDEO_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        ArrayList<String> listItems = new ArrayList<String>();
        listItems.add("goat");
        listItems.add("kudu");
        listItems.add("pig");
        listItems.add("moose");
        listItems.add("parrot fish");
        listItems.add("frogfish");
        listItems.add("blue tang");
        listItems.add("scorpion fish");
        listItems.add("spotted eagle ray");

        final ListDemoAdapter adapter = new ListDemoAdapter(this, R.layout.list_cell, listItems);
        ListView listView = (ListView)this.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        //http://stackoverflow.com/questions/24648140/list-item-click-check-the-check-placed-in-listview-in-android
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> v, View arg1, int position,long id) {
                Object object = adapter.getItem(position);
                System.out.println("checcked item" + position);

                TextView textView = (TextView) v.findViewById(R.id.listCellTextView);
                System.out.println(position);
                if (v != null && textView != null) {
                    if (position == 0) {
                        // In this case, we want to get information back from the CowActivity
                        // (specifically, we want the cow's name as entered by the user).
                        // The startActivityForResult method helps us do that.
                        Intent intent = new Intent(MainActivity.this, VideoActivity.class);
                        startActivityForResult(intent, MainActivity.VIDEO_REQUEST_CODE);
                        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        boolean success = true;
        if (menu == null){
            success = false;
        }
        try{
            MenuInflater infl = getMenuInflater();
            if (infl == null){
                success = false;
            }
            infl.inflate(R.menu.menu_main, menu);

        }
        catch (Exception e){
            success = false;
        }
        return success;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        TextView textView = (TextView)v.findViewById(R.id.listCellTextView);
        System.out.println(position);
        if (v != null && textView != null) {
            if (position == 0) {
                // In this case, we want to get information back from the CowActivity
                // (specifically, we want the cow's name as entered by the user).
                // The startActivityForResult method helps us do that.
                Intent intent = new Intent(this, VideoActivity.class);
                startActivityForResult(intent, MainActivity.VIDEO_REQUEST_CODE);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
