package com.cartorgon;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@org.springframework.boot.autoconfigure.SpringBootApplication
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class SpringBootApplication {
  
  private static final char CHAR_1 = '1';

  /**
   * <p>
   * Main entry point of the application
   * </p>
   */
	public static final void main(final String[] args) {
		SpringApplication.run(SpringBootApplication.class, args);
	}
	
	/**
	 * <p>
	 * Returns the IP of the current machine
	 * </p>
	 * @return the ip of the current machine
	 */
	@GetMapping("/getip")
	@ResponseBody
	public final String getIp() {
	  log.info("Obtaining current IP...");
	  try {
        return InetAddress.getLocalHost().getHostAddress();
    } catch (final UnknownHostException uhE) {
        log.error("", uhE);
        return null;
    }
	}
	
	/**
	 * <p>
	 * Returns the binary gap of the given positive number
	 * </p>
	 * @param number positive number whose binary gap needs to be returned
	 * @return binary gap <i>(0 (zero) by default)</i>
	 */
	@GetMapping("/getbinarygap")
	@ResponseBody
	public final Integer getBinarygGap(@RequestParam(name = "n", required =  true) final Integer number) {
	  log.info("Calculating binary gap...");
	  return this.getBinaryGap(number);
	}
	
	private int getBinaryGap(int n) {
    if(n <= 0) {
      log.error("Input number must be positive", new IllegalArgumentException("Input number must be positive"));
      return 0;      
    }
    
    final String binaryString = Integer.toBinaryString(n);
    
    if(binaryString.contains("1") && binaryString.contains("0")) {
      int resGap = 0;
      final char[] charArray = binaryString.toCharArray();
      for(int i=0; i<charArray.length; i++) {        
        if(charArray[i] == CHAR_1) {
          int partialCount = 0;
          int j;
          for(j=i+1; j<charArray.length; j++) {
            if(charArray[j] != CHAR_1) {
              partialCount++;
            } else {              
              break;
            }                        
          }
          if(j<charArray.length && resGap < partialCount) {
            resGap = partialCount;
          }
        }
      }      
      return resGap;
    }    
    return 0;
  }
}