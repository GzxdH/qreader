package com.example.administrator.qreader;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.NavigationPopup;
import org.geometerplus.android.fbreader.NavigationWindow;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.fbreader.book.IBookCollection;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BookCollectionShadow bs;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // /storage/emulated/0/11111.mobi
        //Environment.getExternalStorageDirectory().getAbsolutePath() + "/33333.epub";
        path = "/storage/emulated/0/11111.mobi";
        if (bs == null) {
            bs = new BookCollectionShadow();
            bs.bindToService(this, new downloadbook());
        }
        Log.i("info:::", path);


    }

    public void openFile(String filePath) {
        Book book = bs.getBookByFile(filePath);
//        if (type.equalsIgnoreCase("mobi") || type.equalsIgnoreCase("epub")) {
            FBReader.openBookActivity(this, book, null);
//        } else if (type.equalsIgnoreCase("pdf")) {
//            Uri uri = Uri.parse(filePath);
//            Intent intent = new Intent(this,MuPDFActivity.class);
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.setData(uri);
//            startActivity(intent);

//        }
    }

    class downloadbook implements Runnable {
        @Override
        public void run() {
                    openFile(path);
        }
    }

}
