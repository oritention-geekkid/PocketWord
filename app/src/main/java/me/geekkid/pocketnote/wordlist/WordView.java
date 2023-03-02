package me.geekkid.pocketnote.wordlist;

import static me.geekkid.pocketnote.wordlist.WordActivity.simpleAdapter;
import static me.geekkid.pocketnote.wordlist.WordActivity.wordList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Objects;

import me.geekkid.pocketnote.R;
import me.geekkid.pocketnote.item.Word;

public class WordView extends AppCompatActivity implements View.OnClickListener {

    String[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_view);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ImageButton btnBack = findViewById(R.id.btn_wordview_back);
        btnBack.setOnClickListener(this);
        Button btn_delete = findViewById(R.id.btn_wordview_delete);
        btn_delete.setOnClickListener(this);

        TextView tv_word = findViewById(R.id.tv_word_word);
        TextView tv_translation = findViewById(R.id.tv_word_translation);
        TextView tv_pos = findViewById(R.id.tv_word_pos);

        Bundle bundle = getIntent().getExtras();
        data = Word.StringToWord(bundle.getString("data"));
        tv_word.setText(data[0]);
        tv_translation.setText(data[1]);
        tv_pos.setText(data[2]);

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_wordview_back) {
            finish();
        }
        if (view.getId()==R.id.btn_wordview_delete) {
            wordList.removeIf(h -> h.containsValue(data[0]) && h.containsValue(data[1]) && h.containsValue(data[2]));
            simpleAdapter.notifyDataSetChanged();
            finish();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        for(HashMap<String,Object> h : wordList) {
            if (h.containsValue(data[0])&&h.containsValue(data[1])&&h.containsValue(data[2])) {
                h.replace("reviewTimes",Integer.parseInt(String.valueOf(Objects.requireNonNull(h.get("reviewTimes"))))+1);
            }
        }
        simpleAdapter.notifyDataSetChanged();
    }
}