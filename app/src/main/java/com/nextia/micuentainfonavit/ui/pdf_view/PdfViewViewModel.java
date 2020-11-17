package com.nextia.micuentainfonavit.ui.pdf_view;
/**
 * view model of the pdf view that share the pdf file to teh view from another fragments
 */

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.io.File;

public class PdfViewViewModel extends ViewModel {
    private MutableLiveData<File> _file= new MutableLiveData<>();

    //get and set methods of the file
    public LiveData<File> getFile(){return _file;}
    public void setFile(File file){_file.setValue(file);}

}