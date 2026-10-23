package com.myspring.demo.files;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;


@ConfigurationProperties("storage")
public class StorageProperties {

  private Environment env;

  public StorageProperties(Environment env) {
      this.env = env;
  }
   
  /**
   * Folder location for storing files
   */
  private String location = env.getProperty("location.pdf.uploads");

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}