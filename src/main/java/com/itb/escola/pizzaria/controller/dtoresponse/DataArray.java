package com.itb.escola.pizzaria.controller.dtoresponse;

import java.util.List;

public class DataArray<T> {

    private int status;
    private List<T> data;

    // Construtores
    public DataArray() {
    }

    public DataArray(int status, List<T> data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
