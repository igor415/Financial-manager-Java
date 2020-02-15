package com.varivoda.igor.financijskimanager;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.varivoda.igor.financijskimanager.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    public static Typeface typeface;
    private ActivityMainBinding binding;
    public static final String INTENT_VALUE = "com.varivoda.igor.financijskimanager.INTENT_VALUE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(R.string.naslovnica);
        }
        changeToolbarFont(toolbar,this);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/baloo.ttf");
        setListeners();
    }
    public void setListeners(){

        for(int i=0; i<binding.gridLayout.getChildCount(); i++){
            int x = i+1;
            binding.gridLayout.getChildAt(i).setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this,OptionsActivity.class);
                intent.putExtra(INTENT_VALUE, x);
                startActivity(intent);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            });
        }

    }

    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/baloo.ttf"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.bug){
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:igor.varivodaa@gmail.com.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Naslov");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Molimo Vas opišite problem na koji ste naišli..");
            try {
                startActivity(Intent.createChooser(emailIntent, "Pošalji e-mail"));

            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "Pojavio se problem sa otvaranjem e-maila", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else if(item.getItemId()==R.id.podijeli){
            Intent in = new Intent(Intent.ACTION_SEND);
            in.setType("text/plain");
            in.putExtra(Intent.EXTRA_SUBJECT, "Financijski manager 2020");
            String message = "\nhttps://play.google.com/store/apps/details?id=com.varivoda.igor.financijskimanager\n\n";
            in.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(in, "Izaberite gdje želite podijeliti:"));
        }
        return super.onOptionsItemSelected(item);
    }

}
