package com.example.Finanzas.Inteligentes.utils.dto;

public class Base64Request {
    private String base64File;
    private String folder;
    public Base64Request(){
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
