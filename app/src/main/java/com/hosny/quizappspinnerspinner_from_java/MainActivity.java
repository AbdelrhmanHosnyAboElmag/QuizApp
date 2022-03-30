package com.hosny.quizappspinnerspinner_from_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {
    TextView answer_show;
    Spinner spnanswer;
    Button btanswer,btstart,btskip,btmax;
    String[] country={"egypt","usa","france","uk"};
    String[] cities={"cairo","ws","paris","london"};
    ArrayList<String>items=new ArrayList<>();
    ArrayList<Byte>max=new ArrayList<>();
    List<Byte>freq=new ArrayList<>();
    byte score=0,i=0,wronfc=0,skip=0;
    //creat obj
    MediaPlayer media1,media2;
    //create obj
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answer_show=findViewById(R.id.answer_show);
        spnanswer=findViewById(R.id.spnanswer);
        btanswer=findViewById(R.id.btanswer);
        btstart=findViewById(R.id.btstart);
        btskip=findViewById(R.id.btskip);
        btmax=findViewById(R.id.btmax);
        media1=MediaPlayer.create(this,R.raw.fail);
        media2=MediaPlayer.create(this,R.raw.sussed);
        //make this data apear hear only
        pref=getPreferences(MODE_PRIVATE);
//        Collections.addAll(items, "please select item",
//        "cairo",
//        "ws",
//        "paris",
//        "london",
//        "tokyo",
//        "bussan");
//        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1);
//        spnanswer.setAdapter(adapter);
        //problem if press start will not creat anther one<line51>
    }

    @Override
    public void onBackPressed() {
        media2.stop();
        media1.stop();
        super.onBackPressed();
    }

    /*
        public  void sound (){
            if(score<2){
                media=MediaPlayer.create(this,R.raw.fail);
                media.start();
            }
            else {
                media=MediaPlayer.create(this,R.raw.sussed);
                media.start();
            }
        }
        close because evry time press button created obj take time
         */
    public void answer(View view) {
        //work_around_if_refuse_work_in_oncreated
        if(media2==null) {
            media2 = MediaPlayer.create(this, R.raw.sussed);
        }
        media2.start();
        String answer=spnanswer.getSelectedItem().toString();
        if(answer.equalsIgnoreCase(cities[i])&&i<cities.length) {
            score++;
            items.remove(answer);
            wronfc = 0;
            i++;
            if (i < country.length) {
                spnanswer.setSelection(0);
                answer_show.setText("what citiy of " + country[i]);
            }
        }
        else if(!(answer.equalsIgnoreCase(cities[i]))&&i<cities.length) {
            spnanswer.setSelection(0);
            wronfc++;
            Toast.makeText(this, "WRONG ANSWER ", Toast.LENGTH_SHORT).show();
            if(wronfc==2){
                score++;
                wronfc=0;
                i++;
                if (i <country.length) {
                    spnanswer.setSelection(0);
                    answer_show.setText("what citiy of " + country[i]);
                }
            }
            if(!(wronfc==1)&&score>0) {
                score--;
            }
        }
        if (i == country.length) {
            Toast.makeText(this, "score=" + score, Toast.LENGTH_SHORT).show();
            if(score<2){
                    media1.start();
            }
            else {
                media2.start();
            }
            //take
            SharedPreferences.Editor editor=pref.edit();
            editor.putInt("score",score);
            editor.commit();
            //put
            if(pref.contains("score")){
                freq.add((byte) pref.getInt("score",-1));
                int c=Collections.frequency(freq,score);
                Toast.makeText(this, "you get this "+score+">>"+c, Toast.LENGTH_SHORT).show();
            }
            btstart.setEnabled(true);
            btanswer.setEnabled(false);
            btskip.setEnabled(false);
            max.add(score);
            btmax.setEnabled(true);
        }
        //solution1>>o(n)
        //Collections.shuffle(items);
        //items.remove("please select item");
        //items.add(0,"please select");
        //solution2>>o(1);
        Collections.shuffle(items);
        Collections.swap(items,0,items.indexOf("please select item"));//o(1)
    }

    public void start(View view) {
            media1.stop();
            media2.stop();
        spnanswer.setSelection(0);
        spnanswer.setEnabled(true);
        btanswer.setEnabled(true);
        btskip.setEnabled(true);
        i=0;
        score=0;
        answer_show.setText("what citiy of " + country[0]);
        btstart.setEnabled(false);
        items.clear();
        Collections.addAll(items,
                "please select item","cairo",
        "ws",
        "paris",
        "london",
        "tokyo",
        "bussan");
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_activated_1,items);
        spnanswer.setAdapter(adapter);
    }

    public void skip (View view) {
        score++;
        if(i==country.length-1){
            Toast.makeText(this, "score=" + score, Toast.LENGTH_SHORT).show();
            if(score<2){
                media1.start();
            }
            else {
                media2.start();
            }
            btstart.setEnabled(true);
            btanswer.setEnabled(false);
            btskip.setEnabled(false);
        }
        i++;
        if (i <country.length) {
            answer_show.setText("what citiy of " + country[i]);
        }
        skip++;
        if(skip==1){
            btskip.setEnabled(false);
        }
    }

    public void max(View view) {
        Intent go=new Intent(this,maxscore.class);
        go.putExtra("max",Collections.max(max));
        startActivity(go);
    }
}