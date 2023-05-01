package it_school.sumdu.edu.db2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class MainActivity extends AppCompatActivity {
    private WordViewModel mWordViewModel;
    private EditText mRoomEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoomEditText = findViewById(R.id.roomEditText);

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final WordListAdapter adapter = new WordListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWordViewModel.getAllWords().observe(this, words -> adapter.setWords(words));

        if (savedInstanceState!= null){
        }
    }

    public void onSaveClick(View view) {
        String roomText = mRoomEditText.getText().toString();
        Word word = new Word(roomText);
        mWordViewModel.insert(word);

        mRoomEditText.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}