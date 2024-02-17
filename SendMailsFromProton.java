package com.test;

import java.io.*;
import java.util.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class SendMailsFromProton {
	

 public static void main(String[] args) throws Exception {
	 
	 String Body = "";
	 
	 
	 String emailExcelFile = "D:\\EMAIL_as_LIST.xlsx";
	 
	 FileInputStream fis = new FileInputStream(emailExcelFile);
	 XSSFWorkbook workbook =  new XSSFWorkbook(fis);
	 XSSFSheet sheet = workbook.getSheetAt(0);
	 
	 
	 List<String> list  = new ArrayList<String>(Arrays.asList());
	 
	 for(int i=3;i<sheet.getLastRowNum();i++) {
		 String email     = sheet.getRow(i).getCell(0).toString();
		 String nextEmail = sheet.getRow(i+1).getCell(0).toString();
		 list.add(email);
		 if(nextEmail.equals("")) {
			 break;
		 }
	 }
	 
	 
	 System.out.println("Total number of emails => "+list.size());
	 System.out.println("=========================================");

	 
	 
//	 ======================================================================	 
	 String Attachment = "D:\\Others\\imp\\PRASHANT BAURAI - QA Automation Tester ( Resume ).pdf";
	 String Subject    = "QA Test Automation | Hyderabad | 3-4 Yrs";
	 String email      = "";      
	 String password   = "";
//	 ======================================================================
	 
   	 System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
     WebDriver driver = new ChromeDriver();
	    
	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
	 driver.manage().window().maximize();
	 driver.manage().deleteAllCookies();
	 
	 driver.get("https://account.proton.me/login");
	 driver.findElement(By.id("username")).sendKeys(email);
	 driver.findElement(By.id("password")).sendKeys(password);
	 driver.findElement(By.xpath("//button[@type='submit']")).click();
	 
	 String title = "Inbox | iamprashant.008@protonmail.com | Proton Mail";
	 WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(50));
	 wait.until(ExpectedConditions.titleContains(title));
	 
	 System.out.println(driver.getTitle());
	 System.out.println(title);
	 System.out.println("============================");

	 
	 String xNewMessageButton = "//button[@class='button button-large button-solid-norm w-full hidden md:inline']";
	 String xTo               = "//input[@placeholder='Email address']";
	 String xSubject          = "//input[@placeholder='Subject']";
	 String xFrame            = "//iframe[@title='Email composer']";
	 String xMessage          = "//div/div[@style='font-family: Arial, sans-serif; font-size: 14px;'][1]";
	 String xAttachment       = "//input[@class='composer-attachments-button']";
	 String xSendButton       = "//button[@data-testid='composer:send-button']";
	 String xPopUpClose       = "//button[@data-testid='modal:close']";
	 String xComposeMailBox   = "//div[@class='composer rounded flex flex-nowrap flex-column outline-none left-custom']";
	 String xDeleteMail       = "//div[@class='flex']//button[1]";
	 String xDeleteDraft      = "//button[@class='button button-medium button-solid-danger']";
	 String xSendAnyway       = "//button[contains(text(),'Send anyway')]";
	 String xConfirmRecipient = "//h1[contains(text(),'Confirm recipient address')]";
	 String xProfile          = "//span[@class='block text-ellipsis color-weak text-sm m-0 lh-rg user-dropdown-email']";
	 String xLogout           = "//button[@data-testid='userdropdown:button:logout']";
	 String xCloseBanner      = "//button[@title='Close this banner']";
	 
	
	 int size = driver.findElements(By.xpath(xCloseBanner)).size();
	 
	 
	 
	 if(size>0) {
		 driver.findElement(By.xpath(xCloseBanner)).click();
	 }
	 
	 
	 List<WebElement> sendAnyway ;
	 int anywayCount =0;
	 int row=1;
	 int currentRow=3;
	 
	 for(int i=0;i<list.size();i++)
		 
	 {
		 
		 if(i==77) {
			 break; // due to daily limit of ProtonMail
		     }
		 
		 
		 
		 
	 Thread.sleep(500);
	 try {
	 driver.findElement(By.xpath(xNewMessageButton)).click();
	 }
	 
	 catch (Exception e) {
		 
		 sendAnyway = driver.findElements(By.xpath(xSendAnyway));
		 
		  anywayCount = sendAnyway.size();
		  
		    if(anywayCount>0) {
		    	row = (i+4-1);
			 System.out.println("sendAnyway button is displayed / "+" Excel Row="+row);
			 
	    	 driver.findElement(By.xpath(xSendAnyway)).click();
	    	 System.out.println("sendAnyway button is clicked - 'new message' catch");
	    	 anywayCount =0;
		    }
		    driver.findElement(By.xpath(xNewMessageButton)).click();
	 }
	 
	 driver.findElement(By.xpath(xTo)).sendKeys(list.get(i));
	 driver.findElement(By.xpath(xSubject)).sendKeys(Subject);
	 driver.switchTo().frame(driver.findElement(By.xpath(xFrame)));
	 driver.findElement(By.xpath(xMessage)).sendKeys(Body);
	 driver.switchTo().defaultContent();
	 driver.findElement(By.xpath(xAttachment)).sendKeys(Attachment);
	 
     try {
	 WebElement attachment = driver.findElement(By.xpath("//span[@class='mr-2 color-weak']/span[2]"));                                  
	 wait.until(ExpectedConditions.visibilityOf(attachment));
     }
     
     catch(Exception e) {
    	 System.out.println("Exception found in => " +list.get(i)+" i="+i);
     }
     
	 Thread.sleep(1000);
	 
	 try {
	 driver.findElement(By.xpath(xSendButton)).click();        // clicked on send button for sending email
	 Thread.sleep(1000);
	 
	 }
	 
	 catch(Exception e){
		 
		 System.out.println(" Exception came =>"+e.getMessage().substring(0,45));
		 
		 
		  sendAnyway = driver.findElements(By.xpath(xSendAnyway));
		 
		  anywayCount = sendAnyway.size();
		  
		    if(anywayCount>0) {
		    	
		     row = (i+4);
			 System.out.println("sendAnyway button is displayed / "+" Excel Row="+row);
	    	 driver.findElement(By.xpath(xSendAnyway)).click();
	    	 System.out.println("sendAnyway button is clicked - catch");
	    	 anywayCount =0;
		     }
		 
		 System.out.println(" Exception Handled =>"+e.getMessage().substring(0,45)+" i="+i);
	 }
	 
	 
	
	 
	 if((i+1)<10) {
	 System.out.println("0"+(i+1)+" out of "+list.size()+" has been sent => ("+list.get(i)+")");
	 
     }else {
    	 
    	 System.out.println((i+1)+" out of "+list.size()+" has been sent => ("+list.get(i)+")");
     }
	 
	 currentRow++;
	 
	 }
	 
	 System.out.println("All Mails have been sent successfully");
	 
	 try {
	 driver.findElement(By.xpath(xProfile)).click();
	 }
	 catch(Exception e) {
		 
		 sendAnyway = driver.findElements(By.xpath(xSendAnyway));
		 
		  anywayCount = sendAnyway.size();
		  
		    if(anywayCount>0) {
		     
			 System.out.println("sendAnyway button is displayed / "+" Excel Row="+currentRow);
	    	 driver.findElement(By.xpath(xSendAnyway)).click();
	    	 System.out.println("sendAnyway button is clicked - 'new message' catch");
	    	 anywayCount =0;
		    }
		    driver.findElement(By.xpath(xProfile)).click();
	 }
	 
	 driver.findElement(By.xpath(xLogout)).click();
	 
	 System.out.println("Email Logged Out successfully");
	 
	 driver.close();
	 
	 workbook.close();
	 fis.close();
	 
	 System.out.println("driver got closed successfully");
	 
	 
	 
  } 
 
}
