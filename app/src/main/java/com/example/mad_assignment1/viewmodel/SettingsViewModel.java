// Class: SettingsViewModel
// Description: Holds data from the SettingsFragment

package com.example.mad_assignment1.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> numRows;
    private final MutableLiveData<Integer> numColumns;

    public SettingsViewModel() {
        numRows = new MutableLiveData<>(6);
        numColumns = new MutableLiveData<>(7);
    }

    public LiveData<Integer> getNumRows() {
        return numRows;
    }

    public LiveData<Integer> getNumColumns() {
        return numColumns;
    }

    public void setGridSize(int rows, int columns) {
        numRows.setValue(rows);
        numColumns.setValue(columns);
    }
}
