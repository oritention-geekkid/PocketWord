package me.geekkid.pocketnote;

import static me.geekkid.pocketnote.wordlist.WordActivity.wordList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import me.geekkid.pocketnote.item.Word;
import me.geekkid.pocketnote.wordlist.CasualReview;
import me.geekkid.pocketnote.wordlist.WordActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        wordList = new ArrayList<>();
        try {
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Button btnToWord = findViewById(R.id.btn_word);
        btnToWord.setOnClickListener(this);
        Button btnToReview = findViewById(R.id.btn_review);
        btnToReview.setOnClickListener(this);
        Button btnAbout = findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_word) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, WordActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_review) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CasualReview.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_about) {
            display();
        }

    }

    private void loadData() throws IOException {
        File f = new File(this.getExternalFilesDir(null).getPath() + "/WordData.txt");
        if (!f.exists()) Log.i("I", String.valueOf(f.createNewFile()));
        try (
                FileReader fr = new FileReader(this.getExternalFilesDir(null).getPath() + "/WordData.txt");
                BufferedReader bfr = new BufferedReader(fr);
        ) {
            String line;
            wordList.clear();
            while (true) {
                line = bfr.readLine();
                if (line == null) {
                    break;
                } else {
                    String[] data = Word.StringToWord(line);
                    HashMap<String, Object> wordData = new HashMap<>();
                    wordData.put("word", data[0]);
                    wordData.put("pos", data[2]);
                    wordData.put("reviewTimes", Integer.parseInt(data[3]));
                    wordData.put("translation", data[1]);
                    wordList.add(wordData);
                }
            }
        }
    }
    public void display() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("About PocketWord")
                .setMessage("Version: demo\nDeveloper: GeekKid\n\nJust for English words reciting.")
                .create()
                .show();
    }
}