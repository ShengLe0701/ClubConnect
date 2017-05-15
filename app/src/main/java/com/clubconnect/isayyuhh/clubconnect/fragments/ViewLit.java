package com.clubconnect.isayyuhh.clubconnect.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.clubconnect.isayyuhh.clubconnect.R;
import com.clubconnect.isayyuhh.clubconnect.activities.DetailClub;
import com.clubconnect.isayyuhh.clubconnect.activities.DetailEvent;
import com.clubconnect.isayyuhh.clubconnect.objects.Event;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewLit extends BaseFragment {

    private FrameLayout frame;
    private ImageView imageIV;
    private ImageView backIV;
    private TextView titleTV;
    private TextView dateTV;
    private TextView timeTV;
    private TextView locationTV;

    private Event event;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lit_view, container, false);
        this.frame = (FrameLayout) v.findViewById(R.id.lit);
        this.imageIV = (ImageView) v.findViewById(R.id.popular_view_image_iv);
        this.backIV = (ImageView) v.findViewById(R.id.popular_view_background_iv);
        this.titleTV = (TextView) v.findViewById(R.id.popular_view_title);
        this.dateTV = (TextView) v.findViewById(R.id.popular_view_date);
        this.timeTV = (TextView) v.findViewById(R.id.popular_view_time);
        this.locationTV = (TextView) v.findViewById(R.id.popular_view_location);

        ParseQuery<Event> query = new ParseQuery<>("Event");
        query.whereEqualTo("objectId", getArguments().getString("EVENT"));
        query.findInBackground(new FindCallback<Event>() {
            @Override
            public void done(List<Event> objects, ParseException e) {
                if (e == null && objects.size() == 1) {
                    event = objects.get(0);
                    event.set();

                    titleTV.setText(event.title);
                    dateTV.setText(event.day + " : " + event.date);
                    timeTV.setText(event.startTime + " - " + event.endTime);
                    locationTV.setText(event.location);
                    Picasso.with(getContext())
                            .load(event.imageURL)
                            .resize(600, 240).centerCrop()
                            .into(imageIV, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    BitmapDrawable drawable = (BitmapDrawable) imageIV.getDrawable();
                                    if (drawable != null) {
                                        Bitmap bitmap = drawable.getBitmap();
                                        Bitmap blurred = blurRenderScript(bitmap, 25);

                                        Paint p = new Paint();
                                        ColorFilter filter = new LightingColorFilter(Color.GRAY, 0);
                                        p.setColorFilter(filter);

                                        Canvas canvas = new Canvas(blurred);
                                        canvas.drawBitmap(blurred, 0, 0, p);

                                        backIV.setImageBitmap(blurred);
                                    }
                                }

                                @Override
                                public void onError() {
                                }
                            });

                    frame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), DetailEvent.class);
                            intent.putExtra("ID", event.id);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {

        try {
            smallBitmap = RGB565toARGB888(smallBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Bitmap bitmap = Bitmap.createBitmap(
                smallBitmap.getWidth(), smallBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(getContext());

        Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(radius); // radius must be 0 < r <= 25
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];

        //Get JPEG pixels.  Each int is the color values for one pixel.
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());

        //Create a Bitmap of the appropriate format.
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);

        //Set RGB pixels.
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
    }
}
