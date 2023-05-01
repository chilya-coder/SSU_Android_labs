package it_school.sumdu.edu.db2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    Word(@NonNull String word) {
        this.mWord = word;
    }

    String getWord(){
        return this.mWord;
    }
}