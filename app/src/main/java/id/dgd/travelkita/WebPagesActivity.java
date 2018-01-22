package id.dgd.travelkita;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPagesActivity extends AppCompatActivity {

    // VARIABLES
    private WebView webView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String extraString;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // SET CUSTOM TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar().getTitle() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // SET SWIPE REFRESH
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWeb();
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {
            extraString = intent.getStringExtra(Intent.EXTRA_TEXT);
        }

        // LOAD WEB FUNCTION
        loadWeb();
    }

    public void loadWeb() {

        // SET WEB VIEW
        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        // SET DIFFERENT ZOOM SCALE TO DIFFERENT CONTENT
        if (extraString.equals("pesawat") | extraString.equals("kereta") |
                extraString.equals("hotel") | extraString.equals("transport") |
                extraString.equals("agen")) {

            webView.setInitialScale(150);
        } else if (extraString.equals("ppob") | extraString.equals("pulsa")) {

            webView.setInitialScale(120);

        } else {

            webView.setInitialScale(1);
        }

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        swipeRefreshLayout.setRefreshing(true);

        // ENABLE BUILT-IN ZOOM FUNCTION
        webView.getSettings().setBuiltInZoomControls(true);

        // TODO (1) CHANGE CLIENT'S URLS HERE!
        // DETERMINE WHICH URL TO CALL BASED ON INTENT'S EXTRA VALUE
        switch (extraString) {
            case "pesawat":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=travelkita.biz&d=hotel-kereta-antarjemput-registeragen-umroh-bustravel";
                break;
            case "kereta":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=travelkita.biz&d=pesawat-hotel-antarjemput-registeragen-umroh-bustravel";
                break;
            case "hotel":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=travelkita.biz&d=pesawat-kereta-antarjemput-registeragen-umroh-bustravel";
                break;
            case "umroh":
                url = "http://travelkita.biz/umroh";
                break;
            case "bus":
                url = "http://transaksi.klikmbc.co.id/bustravel/index.php?s=travelkita.biz";
                break;
            case "transport":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=travelkita.biz&d=pesawat-hotel-kereta-registeragen-umroh-bustravel";
                break;
            case "ppob":
                url = "http://transaksi.klikmbc.co.id/ppob";
                break;
            case "pulsa":
                url = "https://transaksi.klikmbc.co.id/pulsa";
                break;
            case "tur":
                url = "http://tour.klikmbc.co.id/?s=tour.klikmbc.co.id?s=travelkita.biz";
                break;
            case "agen":
                url = "http://booking.klikmbc.co.id/booking/flights/page/formagen.php?s=travelkita.biz&d=pesawat-kereta-antarjemput-umroh-bustravel-hotel";
                break;
            case "kontak":
                url = "http://travelkita.biz/contact-us";
                break;
        }

        webView.loadUrl(url);

        // SET ERROR PAGE WHEN FAILED TO LOAD THE WEB
        webView.setWebViewClient(new WebViewClient() {

            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                webView.loadUrl("file:///android_asset/error.html");
            }

            public void onPageFinished(WebView view, String url) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    // SET BACK BUTTON TO ACT LIKE BROWSER BACK FUNCTION WHEN IN WEB VIEW
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
