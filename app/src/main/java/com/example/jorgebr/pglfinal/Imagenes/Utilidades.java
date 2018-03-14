package com.example.jorgebr.pglfinal.Imagenes;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jorge on 16/11/2017.
 */

public class Utilidades {
    /*Cargar imagen desde el almacenamiento interno*/
    static public void loadImageFromStorage(Context contexto, String imagenFichero, ImageView img) throws FileNotFoundException {
        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);
    }

    /*Guardar imagen en almacenamiento interno*/
    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {
        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG, 100, fos);  //comprimimos la imagen
        fos.close();
    }
}
