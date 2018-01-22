package id.dgd.travelkita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import id.dgd.travelkita.adapter.GridAdapter;
import id.dgd.travelkita.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    // VARIABLES
    private String feature;
    private int dotsCount;
    private ImageView[] dot;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    // GRID VIEW IMAGES
    private int[] images = {
            R.drawable.ic_pesawat,
            R.drawable.ic_kereta,
            R.drawable.ic_hotel,
            R.drawable.ic_umroh,
            R.drawable.ic_bus,
            R.drawable.ic_transport,
            R.drawable.ic_ppob,
            R.drawable.ic_pulsa,
            R.drawable.ic_tur,
            R.drawable.ic_agen,
            R.drawable.ic_kontak,
            R.drawable.ic_keluar
    };

    // GRID VIEW TITLES
    private String[] titles = {
            "Pesawat",
            "Kereta",
            "Hotel",
            "Umroh",
            "Bus",
            "Transport",
            "PPOB",
            "Pulsa",
            "Tur",
            "Agen",
            "Kontak",
            "Keluar"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SET CUSTOM TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar().getTitle() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // SET VIEW PAGER
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        // SET VIEW PAGER INDICATOR
        LinearLayout viewPagerIndicator = (LinearLayout) findViewById(R.id.viewpager_indicator);
        dotsCount = viewPagerAdapter.getCount();
        dot = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dot[i] = new ImageView(this);
            dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            viewPagerIndicator.addView(dot[i], params);
        }

        dot[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // DO NOTHING
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dot[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_inactive));
                }
                dot[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dot_active));

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                // DO NOTHING
            }
        });

        // SET GRID VIEW
        GridView gridView = (GridView) findViewById(R.id.gridview);
        GridAdapter gridAdapter = new GridAdapter(this, titles, images);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    feature = "pesawat";
                    loadWeb(feature);
                } else if (position == 1) {
                    feature = "kereta";
                    loadWeb(feature);
                } else if (position == 2) {
                    feature = "hotel";
                    loadWeb(feature);
                } else if (position == 3) {
                    feature = "umroh";
                    loadWeb(feature);
                } else if (position == 4) {
                    feature = "bus";
                    loadWeb(feature);
                } else if (position == 5) {
                    feature = "transport";
                    loadWeb(feature);
                } else if (position == 6) {
                    feature = "ppob";
                    loadWeb(feature);
                } else if (position == 7) {
                    feature = "pulsa";
                    loadWeb(feature);
                } else if (position == 8) {
                    feature = "tur";
                    loadWeb(feature);
                } else if (position == 9) {
                    feature = "agen";
                    loadWeb(feature);
                } else if (position == 10) {
                    feature = "kontak";
                    loadWeb(feature);
                } else if (position == 11) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    // ADD OVERFLOW TO TOOLBAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_overflow, menu);
        return true;
    }

    // OVERFLOW CONTENT
    Toast toast;
    String message = "icons made by Freepik from\nwww.flaticon.com";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.credits) {
            if (toast != null) {
                toast.cancel();
            }
            toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    // PASS FEATURE STRING VALUE AND START THE ACTIVITY
    public void loadWeb(String extraString) {
        Intent intent = new Intent(MainActivity.this, WebPagesActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, extraString);

        // CHECK IF TARGET DEVICE HAS APP TO OPEN THE WEB VIEW
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // SET THAT USER MUST PRESS BACK BUTTON TWICE TO EXIT THE APP
    @Override
    public void onBackPressed() {

        if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {

            moveTaskToBack(true);
        } else {
            Toast.makeText(MainActivity.this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }
}
