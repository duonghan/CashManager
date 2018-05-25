package lecture.com.cashmanager.menu;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import lecture.com.cashmanager.R;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView icShare;
    ImageView icFB;
    ImageView icTwitter;
    ImageView icBBM;
    ImageView icGgPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        icShare = findViewById(R.id.ic_about_share);
        icFB = findViewById(R.id.ic_about_facebook);
        icTwitter = findViewById(R.id.ic_about_twitter);
        icBBM = findViewById(R.id.ic_about_bbm);
        icGgPlus = findViewById(R.id.ic_about_ggplus);

        icShare.setOnClickListener(this);
        icFB.setOnClickListener(this);
        icTwitter.setOnClickListener(this);
        icBBM.setOnClickListener(this);
        icGgPlus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ic_about_share:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share subject");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Cash manager application");
                startActivity(Intent.createChooser(shareIntent, getString(R.string.txt_about_share_title)));
                break;
            case R.id.ic_about_facebook:
                Intent fb;
                try {
                    getPackageManager().getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
                    fb = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/0x0ff0")); //Trys to make intent with FB's URI
                } catch (Exception e) {
                    fb = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/0x0ff0")); //catches and opens a url to the desired page
                }
                startActivity(fb);
                break;
            case R.id.ic_about_twitter:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=minh_nhat314"));
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/minh_nhat314")));
                }
                break;
            case R.id.ic_about_bbm:
                Dialog dialog = new Dialog(this);
                dialog.setTitle(R.string.txt_title_header_bbm);
                dialog.setContentView(R.layout.layout_alert_bbm);
                dialog.show();
                break;
            case R.id.ic_about_ggplus:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setClassName("com.google.android.apps.plus",
                            "com.google.android.apps.plus.phone.UrlGatewayActivity");
                    intent.putExtra("customAppUri", "102540245952570533629");
                    startActivity(intent);
                } catch(ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/102540245952570533629/posts")));
                }
                break;
        }
    }
}
