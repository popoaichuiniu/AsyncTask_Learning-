package com.zms.aysnc_task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button= findViewById(R.id.btn);
        final DownloadTask downloadTask = new DownloadTask(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTask.execute("www.baidu.com");
            }
        });
        Button button2=findViewById(R.id.btn_cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTask.cancel(true);
            }
        });

    }
}

class DownloadTask extends AsyncTask<String, Integer, Boolean> {

    private Context context = null;

    private ProgressDialog progressDialog = null;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
       // progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        progressDialog.dismiss();
        if (aBoolean) {
            Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);

    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        Toast.makeText(context, "onCancelled(Boolean aBoolean)", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        Log.i("zms",strings[0]);
        int progress = 0;
        while (true) {
            progress++;
            publishProgress(progress);
            if (progress >= 100) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Boolean.TRUE;

    }
}