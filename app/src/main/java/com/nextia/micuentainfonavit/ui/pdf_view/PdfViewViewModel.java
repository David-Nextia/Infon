package com.nextia.micuentainfonavit.ui.pdf_view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nextia.domain.models.credit_year_info.CreditYearInfoResponse;

import java.io.File;

public class PdfViewViewModel extends ViewModel {
    private MutableLiveData<File> _file= new MutableLiveData<>();
    public LiveData<File> getFile(){return _file;}
    public void setFile(File file){_file.setValue(file);}

}