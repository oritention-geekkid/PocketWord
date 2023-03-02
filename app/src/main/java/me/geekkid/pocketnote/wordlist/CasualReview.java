package me.geekkid.pocketnote.wordlist;

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
import java.util.Random;

import me.geekkid.pocketnote.R;
import me.geekkid.pocketnote.item.Word;

public class CasualReview extends AppCompatActivity implements View.OnClickListener {

    String[] data;
    TextView tv_word;
    TextView tv_translation;
    TextView tv_pos;
    int currentNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casual_review);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        ImageButton btnBack = findViewById(R.id.btn_review_back);
        btnBack.setOnClickListener(this);
        Button btn_switch = findViewById(R.id.btn_switchword);
        btn_switch.setOnClickListener(this);
        ImageButton btnSee = findViewById(R.id.btn_see_translation);
        btnSee.setOnClickListener(this);

        tv_word = findViewById(R.id.tv_review_word);
        tv_translation = findViewById(R.id.tv_review_translation);
        tv_pos = findViewById(R.id.tv_review_pos);

        if (wordList.size()!=0) {
            Random random = new Random();
            currentNum = random.nextInt(wordList.size());
            switchData(currentNum);
        } else {
            tv_word.setText("暂无数据");
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_review_back) {
            finish();
        }
        if (view.getId()==R.id.btn_switchword) {
            if (wordList.size()!=1&&wordList.size()!=0) {
                Random random = new Random();
                int num = random.nextInt(wordList.size());
                while (num == currentNum) {
                    num = random.nextInt(wordList.size());
                }
                for (HashMap<String, Object> h : wordList) {
                    if (h.containsValue(data[0]) && h.containsValue(data[1]) && h.containsValue(data[2])) {
                        h.replace("reviewTimes", Integer.parseInt(String.valueOf(Objects.requireNonNull(h.get("reviewTimes")))) + 1);
                    }
                }
                switchData(num);
                currentNum = num;
            }
        }
        if (view.getId()==R.id.btn_see_translation) {
            if (data!=null) {
                tv_translation.setText(data[1]);
            }
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

    }

    private void switchData(int r) {
        String srcData = wordList.get(r).toString();
        data = Word.StringToWord(srcData);

        tv_word.setText(data[0]);
        tv_pos.setText(data[2]);
        tv_translation.setText("-----");
    }
}