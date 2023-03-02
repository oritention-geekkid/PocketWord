package me.geekkid.pocketnote.wordlist;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import me.geekkid.pocketnote.R;

public class AddWord extends AppCompatActivity implements View.OnClickListener{

    private String word_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_add);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button btnToSubmit = findViewById(R.id.btn_addword_submit);
        btnToSubmit.setOnClickListener(this);
        ImageButton btnBack = findViewById(R.id.btn_addword_back);
        btnBack.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.addword_pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = findViewById(R.id.addword_show_pos);
                word_pos = adapterView.getItemAtPosition(i).toString();
                tv.setText(word_pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_addword_submit) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();

            EditText word = findViewById(R.id.addword_word);
            EditText translation = findViewById(R.id.addword_translation);
            if (!word.getText().toString().equals("") && !translation.getText().toString().equals("")) {
                bundle.putString("response_word", word.getText().toString());
                bundle.putString("response_translation", translation.getText().toString());
                bundle.putString("response_pos", word_pos);

                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED,intent);
            }
            finish();
        }
        if (view.getId() == R.id.btn_addword_back) {
            finish();
        }
    }
}