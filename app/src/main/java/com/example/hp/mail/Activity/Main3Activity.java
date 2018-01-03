package com.example.hp.mail.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.mail.R;
import com.example.hp.mail.adapterclass.DT;
import com.github.zagum.speechrecognitionview.RecognitionProgressView;
import com.github.zagum.speechrecognitionview.adapters.RecognitionListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class Main3Activity extends AppCompatActivity implements TextToSpeech.OnInitListener,TextToSpeech.OnUtteranceCompletedListener {
        TextToSpeech tvvs;
    TextView textView5;
    final Handler handler = new Handler();

    private static final String TAG = "MainActivity";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION_CODE = 1;

    private SpeechRecognizer speechRecognizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        int[] colors = {
                ContextCompat.getColor(this, R.color.color1),
                ContextCompat.getColor(this, R.color.color2),
                ContextCompat.getColor(this, R.color.color3),
                ContextCompat.getColor(this, R.color.color4),
                ContextCompat.getColor(this, R.color.color5)
        };

        int[] heights = {60, 76, 58, 80, 55};

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        textView5=(TextView)findViewById(R.id.textView5);
        tvvs=new TextToSpeech(Main3Activity.this,Main3Activity.this);
        Toast.makeText(this,"CLICK THE TEXTVIEW", Toast.LENGTH_SHORT).show();
            textView5.setText(DT.getEname());

            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!tvvs.isSpeaking()){
                        HashMap<String,String> params=new HashMap<String, String>();
                        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"sampleText");
                        tvvs.speak(DT.getEname()+"is going to happen on"+DT.endD+"Do you like to attend this event! Please say yes to confirm",TextToSpeech.QUEUE_ADD,params);                }
                    else{
                        tvvs.stop();
                    }



                }
            });

         final RecognitionProgressView recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
        recognitionProgressView.setSpeechRecognizer(speechRecognizer);
        recognitionProgressView.setRecognitionListener(new RecognitionListenerAdapter() {
            @Override
            public void onResults(Bundle results) {
                showResults(results);
            }
        });
        recognitionProgressView.setColors(colors);
        recognitionProgressView.setBarMaxHeightsInDp(heights);
        recognitionProgressView.play();

//        Button listen = (Button) findViewById(R.id.listen);
//        Button reset = (Button) findViewById(R.id.reset);

//        listen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RecognitionProgressView recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
//                if (ContextCompat.checkSelfPermission(Main3Activity.this,
//                        Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestPermission();
//                } else {
//                    startRecognition();
//                    recognitionProgressView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            startRecognition();
//                        }
//                    }, 50);
//                }
//            }
//        });

//        reset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                recognitionProgressView.stop();
//                recognitionProgressView.play();
//            }
//        });



    }
    @Override
    protected void onDestroy() {
        if(tvvs!=null){tvvs.stop();
            tvvs.shutdown();

            tvvs=null;
        }
        if (speechRecognizer != null) {
            speechRecognizer.destroy();

        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        tvvs.setOnUtteranceCompletedListener(this);

    }

    @Override
    public void onUtteranceCompleted(String utteranceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Main3Activity.this, "COMPLETED", Toast.LENGTH_SHORT).show();
                requestPermission();
                startRecognition();
//                if (ContextCompat.checkSelfPermission(Main3Activity.this,
//                        Manifest.permission.RECORD_AUDIO)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestPermission();
//                } else {
//                    RecognitionProgressView recognitionProgressView = (RecognitionProgressView) findViewById(R.id.recognition_view);
//                    startRecognition();
//                    recognitionProgressView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            startRecognition();
//                        }
//                    }, 50);
//                }
            }
        });

    }
    private void startRecognition() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en");
        speechRecognizer.startListening(intent);
    }

    private void showResults(Bundle results)
    {
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Toast.makeText(this, matches.get(0), Toast.LENGTH_LONG).show();
        speechRecognizer.stopListening();
        if(matches.get(0).equals("yes")){
            if(!tvvs.isSpeaking()){

                HashMap<String,String> params=new HashMap<String, String>();
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"sampleText");
                tvvs.speak("Your response is noted",TextToSpeech.QUEUE_ADD,params);                }
            else{
                tvvs.stop();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    finish();
                }
            }, 1000);



        }
        else if(matches.get(0).equals("no")) {
            if (!tvvs.isSpeaking()) {

                HashMap<String, String> params = new HashMap<String, String>();
                params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "sampleText");
                tvvs.speak("Your response is noted", TextToSpeech.QUEUE_ADD, params);
            } else {
                tvvs.stop();

            }
            this.finish();
        }
        else{
           this.finish();

        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)) {
            Toast.makeText(this, "Requires RECORD_AUDIO permission", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    REQUEST_RECORD_AUDIO_PERMISSION_CODE);
        }
    }

}
