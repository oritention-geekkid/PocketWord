package me.geekkid.pocketnote.wordlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import me.geekkid.pocketnote.R;
import me.geekkid.pocketnote.item.Word;

public class WordActivity extends AppCompatActivity implements View.OnClickListener {

    public static List<HashMap<String, Object>> wordList;
    public static SimpleAdapter simpleAdapter;
    ActivityResultLauncher<Intent> register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        Button btnToAddWord = findViewById(R.id.btn_addword);
        btnToAddWord.setOnClickListener(this);
        ImageButton btnBack = findViewById(R.id.btn_word_back);
        btnBack.setOnClickListener(this);

        ListView listView = findViewById(R.id.list);
        TextView emptyList = findViewById(R.id.empty);
        listView.setEmptyView(emptyList);

        String[] from = new String[]{"word", "pos", "reviewTimes"};
        int[] to = new int[]{R.id.tv_word, R.id.tv_pos, R.id.tv_reviewTimes};
        simpleAdapter = new SimpleAdapter(this, wordList, R.layout.word, from, to);
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent();
            intent.setClass(WordActivity.this, WordView.class);
            String data = listView.getItemAtPosition(i).toString();
            Bundle bundle = new Bundle();
            bundle.putString("data", data);
            bundle.putString("id", String.valueOf(i));
            intent.putExtras(bundle);

            startActivity(intent);
        });

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result != null) {
                Intent intent = result.getData();
                if (intent != null && result.getResultCode() == RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    Word tmp = new Word(bundle.getString("response_word"), bundle.getString("response_translation"), bundle.getString("response_pos"));
                    HashMap<String, Object> wordData = new HashMap<>();
                    wordData.put("word", tmp.getWord());
                    wordData.put("pos", tmp.getPos());
                    wordData.put("reviewTimes", tmp.getReviewTimes());
                    wordData.put("translation", tmp.getTranslation());
                    wordList.add(wordData);
                    simpleAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_addword) {
            Intent intent = new Intent();
            intent.setClass(WordActivity.this, AddWord.class);
            register.launch(intent);
        }
        if (view.getId() == R.id.btn_word_back) {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        simpleAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            saveData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveData() throws IOException {
        File f = new File(this.getExternalFilesDir(null).getPath() + "/WordData.txt");
        if(!f.exists()) Log.i("I", String.valueOf(f.createNewFile()));
        try (
                FileWriter fw = new FileWriter(this.getExternalFilesDir(null).getPath() + "/WordData.txt");
                BufferedWriter bfw = new BufferedWriter(fw);
        ) {
            for (HashMap<String, Object> h : wordList) {
                bfw.write(h.toString());
                bfw.newLine();
            }
        }
    }
}