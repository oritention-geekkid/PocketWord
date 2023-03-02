package me.geekkid.pocketnote.item;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Word extends Item{
    private String word;
    private String translation;
    private String pos;
    private Integer reviewTimes;

    public Word(String word, String translation, String pos) {
        this.word = word;
        this.translation = translation;
        this.pos = pos;
        this.reviewTimes = 0;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Integer getReviewTimes() {
        return reviewTimes;
    }

    public void setReviewTimes(Integer reviewTimes) {
        this.reviewTimes = reviewTimes;
    }

    public void reviewTimesPlus() {
        this.reviewTimes++;
    }

    @NonNull
    @Override
    public String toString() {
        return "{word="+this.getName()+",pos="+this.getPos()+",translation="+this.getTranslation()+",reviewTimes="+this.getReviewTimes()+"}";
    }

    public static String[] StringToWord(String s) {
        String[] ret = new String[4];
        ret[0] = getStringByRegex(s,"(?<=(word\\=)).+?(?=,|\\})");
        ret[1] = getStringByRegex(s,"(?<=(translation\\=)).+?(?=,|\\})");
        ret[2] = getStringByRegex(s,"(?<=(pos\\=)).+?(?=,|\\})");
        ret[3] = getStringByRegex(s,"(?<=(reviewTimes\\=)).+?(?=,|\\})");

        return ret;
    }

    private static String getStringByRegex(String s, String regex) {
        Matcher m = Pattern.compile(regex).matcher(s);
        if (m.find()) {
            return m.group();
        } else return "";
    }
}
