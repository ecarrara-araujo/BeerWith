package br.eng.ecarrara.beerwith;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.eng.ecarrara.beerwith.ui.DrinkWithRegistrationActivity;

public class BeerWithMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_with_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_beer_with);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, DrinkWithRegistrationActivity.class);
        startActivity(intent);
    }
}
