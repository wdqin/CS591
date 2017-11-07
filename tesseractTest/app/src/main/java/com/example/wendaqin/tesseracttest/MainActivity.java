package com.example.wendaqin.tesseracttest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    public final static String SAVED_IMAGE_PATH1 = Environment.getExternalStorageDirectory().getAbsolutePath()+"/pic";
    public final static String SAVED_IMAGE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    private TessBaseAPI mTess; //Tess API reference
    String datapath = ""; //path to folder containing language data file
    String photoPath ="";
    String ocrRawData="";
    ArrayList<String> shoplist=new ArrayList<String>();
    private Button btnOCR;
    private ImageView imVImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOCR = (Button) findViewById(R.id.btnOCR);
        datapath = getFilesDir()+ "/tesseract/";
        checkFile(new File(datapath + "tessdata/"));
        String language = "eng";
        mTess = new TessBaseAPI();
        mTess.init(datapath, language);
        btnOCR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String state = Environment.getExternalStorageState();
                if(state.equals(Environment.MEDIA_MOUNTED))
                {
                    photoPath = SAVED_IMAGE_PATH+"/"+System.currentTimeMillis()+".png";
                    File imageDir = new File(photoPath);
                    if(!imageDir.exists())
                    {
                        try{
                            imageDir.createNewFile();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                takePhoto();
            }
        });

    }
    private void takePhoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = new File(photoPath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photoFile));
        startActivityForResult(intent,REQUEST_TAKE_PHOTO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if(resultCode != RESULT_CANCELED){
            if (requestCode == REQUEST_TAKE_PHOTO) {
                File photoFile=new File(photoPath);
                if(photoFile.exists())
                {
                    Bitmap bm = BitmapFactory.decodeFile(photoPath);
                    processImage(bm);
                }
            }
        }
    }


    private void copyFiles() {
        try {
            //location we want the file to be at
            String filepath = datapath + "/tessdata/eng.traineddata";

            //get access to AssetManager
            AssetManager assetManager = getAssets();
            //open byte streams for reading/writing
            //InputStream instream = assetManager.open("tessdata/eng.traineddata");
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    public void processImage(Bitmap image){
        mTess.setImage(image);
        ocrRawData = mTess.getUTF8Text();
        TextView OCRTextView = (TextView) findViewById(R.id.txVresult);
        OCRTextView.setText(ocrRawData);
    }
}
