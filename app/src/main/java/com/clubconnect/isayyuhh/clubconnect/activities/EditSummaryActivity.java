package com.clubconnect.isayyuhh.clubconnect.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.text.TextWatcher;
import android.text.Editable;

import com.clubconnect.isayyuhh.clubconnect.R;

public class EditSummaryActivity extends BaseAirplaneActivity {
    private EditText editText;
    private TextView limitText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_summary);

        this.editText = (EditText) findViewById(R.id.edit_summary_text);
        this.limitText = (TextView) findViewById(R.id.edit_summary_limit);
        this.saveButton = (Button) findViewById(R.id.edit_summary_button);

        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                limitText.setText(count+" / 500");
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK,
                        new Intent().putExtra("SUMMARY", editText.getText().toString()));
                finish();
            }
        });
        System.out.println("in edit");
    }
}
