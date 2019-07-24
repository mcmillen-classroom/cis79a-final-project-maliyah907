package com.maliyahhoward.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlaceEditActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mSubmitButton;
    private EditText mNameEditText;
    private EditText mDescriptionEditText;

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_create);
        setTitle(R.string.place_edit_title);

        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mNameEditText = (EditText) findViewById(R.id.place_name_edit_text);
        mDescriptionEditText = (EditText) findViewById(R.id.place_description_edit_text);

        mSubmitButton.setOnClickListener(this);

        Intent data = getIntent();
        mNameEditText.setText(data.getStringExtra("place_name"));
        mDescriptionEditText.setText(data.getStringExtra("place_description"));
        mPosition = data.getIntExtra("place_position",-1);

        if(mPosition == -1)
        {
            //TODO: throw exception
        }
    }

    @Override
    public void onClick(View view)
    {
        String name = mNameEditText.getText().toString();
        String description = mDescriptionEditText.getText().toString();

        Intent resultData  = new Intent();
        resultData.putExtra("place_position",mPosition);
        resultData.putExtra("place_name",name);
        resultData.putExtra("place_description", description);

        setResult(RESULT_OK,resultData);
        finish();
    }
}
