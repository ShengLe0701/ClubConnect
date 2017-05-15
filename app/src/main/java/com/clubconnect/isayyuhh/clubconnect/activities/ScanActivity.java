package com.clubconnect.isayyuhh.clubconnect.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Vibrator;

import com.clubconnect.isayyuhh.clubconnect.objects.Club;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.Intents;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.clubconnect.isayyuhh.clubconnect.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import com.parse.FindCallback;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class ScanActivity extends BaseDetailActivity {
    private static final String TAG = ScanActivity.class.getSimpleName();
    private CompoundBarcodeView barcodeView;
    private Context context;
    private String objectId;
    private boolean eventReady;
    private boolean clubReady;
    private SweetAlertDialog scanning;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                pause(barcodeView);
                System.out.println("this is : "+this);

                Vibrator v = (Vibrator) ScanActivity.this.context.getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(500);

                ScanActivity.this.scanning = new SweetAlertDialog(ScanActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                scanning.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                scanning.setTitleText("Loading");
                scanning.setCancelable(false);
                scanning.show();

                ScanActivity.this.clubReady = false;
                ScanActivity.this.eventReady = false;

                System.out.println(result.getText());
                ScanActivity.this.objectId = result.getText();

                ScanActivity.this.isClub();
                //ScanActivity.this.isEvent();
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan);
        this.context = getApplicationContext();
        System.out.println(this.context);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
    }

    public void resume(View view) {
        barcodeView.resume();
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    public Context getAppContext() {
        return this.context;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
    private void isClub() {
        new ParseQuery<ParseObject>("Club").getInBackground(objectId, new GetCallback<ParseObject>() {
            public void done(ParseObject club, ParseException e) {
                ScanActivity.this.scanning.dismissWithAnimation();
                if (e != null) {
                    System.out.println("error : "+e);

                    new SweetAlertDialog(ScanActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("That doesn't seem to be a Connect Code.")
                            .setConfirmText("Back to Scanning")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    resume(barcodeView);
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                    resume(barcodeView);
                } else {
                    System.out.println("club : "+club);
                    if (ScanActivity.this.connectClub(club)) {
                        new SweetAlertDialog(ScanActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Success!")
                                .setContentText("You Connected to " + club.get("title"))
                                .setCancelText("Okay")
                                .setConfirmText("Show Me More!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        ScanActivity.this.goToClub();
                                    }
                                })
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        resume(barcodeView);
                                        sDialog.cancel();
                                    }
                                })
                                .show();
                    } else {
                        ScanActivity.this.scanning.dismissWithAnimation();
                        new SweetAlertDialog(ScanActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Wait a Second...")
                                .setContentText("You're already Connected to "+club.get("title"))
                                .setConfirmText("Back to Scanning")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        resume(barcodeView);
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }
    private void isEvent() {
        new ParseQuery<Event>("Event").getInBackground(objectId, new GetCallback<Event>() {
            public void done(Event event, ParseException e) {
                if (e != null) {
                    System.out.println("error : "+e);
                    ScanActivity.this.eventReady = true;
                    if (ScanActivity.this.eventReady && ScanActivity.this.clubReady) {
                        resume(barcodeView);
                    }
                } else {
                    System.out.println("event : "+event);
//                    new AlertDialog.Builder(ScanActivity.this)
//                            .setTitle("Success")
//                            .setMessage("You connected to the Event")
//                            .setPositiveButton("Show Me More", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // continue with delete
//                                    ScanActivity.this.goToEvent();
//                                }
//                            })
//                            .setNegativeButton("Okay", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//                                    resume(barcodeView);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
                }
            }
        });
    }
    private void goToClub() {
        Intent intent = new Intent(ScanActivity.this, DetailClub.class);
        intent.putExtra("ID", objectId);
        startActivity(intent);
        finish();
    }
    private boolean connectClub(final ParseObject club) {
        if (!find(club.getObjectId(), this.mUser.getRelation("savedClubs").getQuery())) {
            this.mUser.getRelation(this.str(R.string.parse_savedclubs)).add(club);
            this.mUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                        System.out.println("background : "+e);
                        return;
                    }

                    try {
                        System.out.println("Before : clubId : "+club.getObjectId());
                        Club pClub = (new ParseQuery<Club>(str(R.string.parse_class_club))).get(club.getObjectId());
                        System.out.println("After : clubId : "+club.getObjectId());
                        int connected = ((int) pClub.get(str(R.string.parse_connected)));
                        pClub.put(str(R.string.parse_connected), ++connected);
                        pClub.saveInBackground();
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                }
            });
            club.getRelation("Interested").add(this.mUser);
            club.saveInBackground();

            System.out.println("club : "+CLUB);
            System.out.println("title : "+club.get("title"));
            System.out.println("id : "+club.getObjectId());

            this.subscribe(CLUB, club.get("title").toString(), club.getObjectId());
            return true;
        }
        return false;
    }
    private void goToEvent() {
        Intent intent = new Intent(ScanActivity.this, DetailEvent.class);
        intent.putExtra("ID", objectId);
        startActivity(intent);
        finish();
    }
}