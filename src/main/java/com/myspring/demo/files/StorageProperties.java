package com.myspring.demo.files;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

  private String pdfUloads;
  private String uploadsLocation;

  /**
   * Folder location for storing files
   */
  public String getPdfLocation() {
    return pdfUloads;
  }

  public void setPdfLocation(String pdfUloadsLocation) {
    this.pdfUloads = pdfUloadsLocation;
  }

   public String getLocation() {
    return uploadsLocation;
  }

  public void setLocation(String uploadsLocation) {
    this.uploadsLocation = uploadsLocation;
  }

}