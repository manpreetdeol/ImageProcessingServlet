package com.patternrecognition.preprocessing;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;

public class test {	
//	public static void main(String[] args) {
//	 System.out.println(test.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//	 System.out.println(test.class.getResourceAsStream("test.jar"));
//	}
    public static String extractText(String filename)
//    public static void main(String[] args)
    {
    	String result = null;
    	String path = "C:/Users/NAPSTER/ADT/ImagePreprocessing/";
//     System.setProperty("jna.library.path", "32".equals(System.getProperty("sun.arch.data.model")) ? "lib/win32-x86" : "lib/win32-x86-64");
        System.out.println("Next Read File");
        File imageFile = new File(path + filename);
        System.out.println("File Read");
        Tesseract instance = Tesseract.getInstance();  // JNA Interface Mapping
//         Tesseract1 instance = new Tesseract1(); // JNA Direct Mapping

        try {
        	System.out.println("Doing OCR");
            result = instance.doOCR(imageFile);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
