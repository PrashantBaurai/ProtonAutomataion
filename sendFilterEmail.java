package com.test;
 
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class sendFilterEmail { 
        
	public static void main(String[] args) throws Exception {
        
		String PDFPath = "D:\\jobs.pdf"; 
		
        File file = new File(PDFPath);
        PDDocument document = PDDocument.load(file);

        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text2 = pdfStripper.getText(document);
        
        String[] sections = text2.split("================");
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(emailRegex);
           
        int x =0;
         for (String section : sections) {
        	 
//        	 System.out.println(section);
//        	 System.out.println("===========================");
        	 
        	//  Any below Keywords must be present in the section 
            if (
            	section.toLowerCase().contains("automation") ||
            	section.toLowerCase().contains("selenium") ||
                section.toLowerCase().contains("qa")||
                section.toLowerCase().contains("java")
                ){
            		
            	// any below Keyword must be present in the section 	
             if(section.toLowerCase().contains("4 to")||
                section.toLowerCase().contains("3 to")||
                section.toLowerCase().contains("2 to")||
                section.toLowerCase().contains("1 to")||
            	section.toLowerCase().contains("4-")||
                section.toLowerCase().contains("3-")||
                section.toLowerCase().contains("2-")||
                section.toLowerCase().contains("1-")||
                section.toLowerCase().contains("4+")||
                section.toLowerCase().contains("3+")||
                section.toLowerCase().contains("2+")||
                section.toLowerCase().contains("1+")||
                section.toLowerCase().contains("4 years")||
                section.toLowerCase().contains("3 years")||
                section.toLowerCase().contains("2 years")||
                section.toLowerCase().contains("1 years")||
                section.toLowerCase().contains("4 yrs")||
                section.toLowerCase().contains("3 yrs")||
                section.toLowerCase().contains("2 yrs")||
                section.toLowerCase().contains("1 yrs"))
                
                {
            	 // these Keywords should not be present in the section  
            	 if(!section.toLowerCase().contains("rpa")&&
            	    !section.toLowerCase().contains("tosca")&&
            	    !section.toLowerCase().contains("android")&&
            	    !section.toLowerCase().contains("appium")&&
            	    !section.toLowerCase().contains("embedded")
            			 )
            	 {		
                Matcher matcher = pattern.matcher(section);
                
                while (matcher.find()) {
                	if(!matcher.group().contains("info@jobcurator.in")){
                    System.out.println( matcher.group());
                    x++;
                  }
                }
            }
          }
       }
     }
         System.out.println("==============================================");
         System.out.println("total emails = "+ x);
         System.out.println("==============================================");  

    }
    
	}
 



