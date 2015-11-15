package br.edu.imed.myfood;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by diogo on 07/11/2015.
 */
public class AbstractActivity extends AppCompatActivity {

    final String TAG = this.getClass().getSimpleName();

    void showMessage(String s, int tempo){
        Toast.makeText(this, s, tempo).show();
    }
}
