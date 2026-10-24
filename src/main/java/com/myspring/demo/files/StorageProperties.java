package com.myspring.demo.files;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("file")
public class StorageProperties {

  private String Location;

  /**
   * Folder location for storing files
   */
  public String getLocation() {
    return Location;
  }

  public void setLocation(String Location) {
    this.Location = Location;
  }

}