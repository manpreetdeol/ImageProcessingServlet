package com.patternrecognition.preprocessing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.Core;

//import com.sun.java.util.jar.pack.Package.Class/*??*/

/**
 * Servlet implementation class Preprocessing
 */
@WebServlet("/PreprocessingPath")
@MultipartConfig
public class Preprocessing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public Preprocessing() {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	   /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	String method = request.getMethod();
    	
        doGet(request, response);
    }

    public static long copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[4096];

        long count = 0L;
        int n = 0;

        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
     
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	// get current date time with Date()
    	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");			    
	    Date date = new Date();
	    String timestamp = dateFormat.format(date);
	    
	   
	    // set the filename
		String filename = "IMAGE_" + timestamp+".png";

       // InputStream in = request.getInputStream();
        InputStream in = request.getPart("uploaded_file").getInputStream();
        OutputStream out = new FileOutputStream("C:/Users/NAPSTER/ADT/ImagePreprocessing/"+filename);
        copy(in, out); 
        System.out.println("File Saved");
        out.flush();
        out.close();
        
        String preprocessedFile =  PreProcessingTest.startPreprocessing(filename);
       
//        System.out.println(request.getServletContext().getRealPath("/"));
//        System.out.println(request.getServletContext().getResourceAsStream("/WEB-INF/lib/test"));
//        List<String> classNames=new ArrayList<String>();
        
        readJar(request.getServletContext().getRealPath("/")+"\\WEB-INF\\lib\\");

       String result = test.extractText(preprocessedFile);    
       
       PrintWriter writer = response.getWriter();
       writer.print(result);
        
    }
    
    public static void addPath(String s) throws Exception {
        File f = new File(s);
        URL u = f.toURL();
        URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Class urlClass = URLClassLoader.class;
        java.lang.reflect.Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(urlClassLoader, new Object[]{u});
    }
    static{
       /* System.loadLibrary("liblept168");
        System.loadLibrary("libtesseract302");*/
    }
    
    private static void process(InputStream input,  String path, String dllName) throws IOException {

    	int size = 2048;

    	BufferedInputStream reader = new BufferedInputStream(input);

    	FileOutputStream fos = new FileOutputStream(new File(path+dllName));

    	BufferedOutputStream bs = new BufferedOutputStream(fos,2048);

    	byte readContent[] = new byte[size];

    	int count = 0;

    	while ((count = input.read(readContent, 0, size) )!= -1) {

    	bs.write(readContent,0,count);

    	}

    	bs.flush();

    	bs.close();

    	reader.close();
    	
    	System.load(path+dllName);

    	}

    	public static void readJar(String path) throws IOException {
    	System.out.println(path);
    	JarFile jarFile = new JarFile(path+"useless.jar");

    	final Enumeration<JarEntry> entries = jarFile.entries();

    	while (entries.hasMoreElements()) {

    	JarEntry entry = entries.nextElement();

    	if (entry.getName().contains(".dll")) {
    		System.out.println(System.getProperty("os.arch"));
    	System.out.println("File : " + entry.getName());

    	InputStream input = jarFile.getInputStream(entry);

    	process(input,path, entry.getName());

    	}

    	}

    	}
}


