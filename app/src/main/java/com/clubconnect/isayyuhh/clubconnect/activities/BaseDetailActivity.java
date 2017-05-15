package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Arrays;
import java.util.List;

/**
 * Base fragment for a detail activity
 */
public abstract class BaseDetailActivity extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void setActionBar(Toolbar toolbar, FrameLayout statusBar, String title) {
        this.setSupportActionBar(toolbar);
        if (this.getSupportActionBar() != null) {
            this.getSupportActionBar().setTitle(title);
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT < 19) statusBar.getLayoutParams().height = 0;
    }

    protected void dial(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    protected void email(String emailAddress) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(Intent.createChooser(intent, "Email using:"));
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(mContext, "No email apps found", Toast.LENGTH_SHORT).show();
        }
    }

    protected void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(mContext, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    protected void setContactButton(final String phoneNumber, final String emailAddress,
                                    AlertDialog.Builder builder) {
        builder.setTitle(this.str(R.string.dialog_contacttitle));
        builder.setItems(this.arr(R.array.dialog_contactoptions),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dial(phoneNumber);
                                break;
                            case 1:
                                email(emailAddress);
                                break;
                            case 2:
                                copyToClipboard(str(R.string.tag_phone), phoneNumber);
                                break;
                            case 3:
                                copyToClipboard(str(R.string.tag_email), emailAddress);
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    protected void setShareButton(String title, String shareMsgOne, String shareMsgTwo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMsgOne + title + shareMsgTwo);
        sendIntent.setType(this.str(R.string.intent_type_textplain));
        this.startActivity(Intent.createChooser(sendIntent, getResources().getText(
                R.string.share_via)));
    }

    protected boolean find(String objectId, ParseQuery query) {
        try {
            query.whereEqualTo(this.str(R.string.parse_objectid), objectId);
            return query.count() > 0;
        } catch (ParseException pe) {
            return false;
        }
    }
}
