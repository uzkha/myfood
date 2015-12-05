package br.edu.imed.myfood;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import br.edu.imed.myfood.model.Receita;

/**
 * Created by diogo on 14/11/2015.
 */
public class ReceitaAdapter extends ArrayAdapter<Receita>{

    public ReceitaAdapter(Context context, List<Receita> lista) {
        super(context, R.layout.row_item_receita, lista);
    }

    @Override
    public View getView(int position, View row, ViewGroup parent){

        try {
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().
                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.row_item_receita, parent, false);
            }

            Receita receita = getItem(position);

            String path = receita.getPathImagem();

            if(path != null) {

                ImageView imageView = (ImageView) row.findViewById(R.id.imvReceitaItem);

                InputStream inputStreamBmp = new FileInputStream(path);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStreamBmp);

                int width = bitmap.getWidth();
                int height = bitmap.getHeight();

                int divisor = Auxiliar.calcularDivisao(width) * 4;

                bitmap = Bitmap.createScaledBitmap(bitmap, (width / divisor), (height / divisor), false);


                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageBitmap(bitmap);
            }


            ((TextView)row.findViewById(R.id.txReceitaItem)).setText("  " + receita.getNome().toString());
            ((TextView)row.findViewById(R.id.txId)).setText("  " + receita.getId().toString());

            ((TextView)row.findViewById(R.id.txId)).setVisibility(View.INVISIBLE);

            return row;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
