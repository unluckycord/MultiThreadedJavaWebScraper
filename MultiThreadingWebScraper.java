
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MultiThreadingWebScraper {

    public static ArrayList<String> websiteList = new ArrayList<String>();
    public static int websiteIndex = 0;

    public static String AssignWebsite(){

        String website = websiteList.get(websiteIndex);
        websiteIndex++;
        return "https://"+website;
    }

    public static void storeData(String website, ArrayList<String> links, ArrayList<String> images, 
    ArrayList<String> metaDataTagName, ArrayList<String> metaDataName, ArrayList<String> metaDataContent ){

        int startOfIndex = 8;
        int endOfIndex = website.indexOf(".");
        String fileName = website.substring(startOfIndex, endOfIndex);
        try{
            //File outputTextFile = new File( "WebsiteData/" + fileName + ".txt");
            BufferedWriter writeToTextFile = new BufferedWriter(new FileWriter("WebsiteData/"+fileName +".txt", true));
            //if(outputTextFile.createNewFile()){
                for(int i = 0; i< links.size(); i++){
                    writeToTextFile.write(links.get(i)+ "\n");
                }
                for(int i = 0; i< images.size(); i++){
                    writeToTextFile.write(images.get(i)+ "\n");
                }
                for(int i = 0; i< metaDataTagName.size(); i++){
                    writeToTextFile.write(metaDataTagName.get(i)+ "\n");
                }
                for(int i = 0; i< metaDataName.size(); i++){
                    writeToTextFile.write(metaDataName.get(i)+ "\n");
                }
                for(int i = 0; i< metaDataContent.size(); i++){
                    writeToTextFile.write(metaDataContent.get(i)+ "\n");
                }
                writeToTextFile.close();
            //}else{
            //    System.out.println("A file already exists with that name");
            //}
        }catch(IOException e){
        
        }
    }

    public static void webScraper(String website) {
        ArrayList<String> linkStorageArrayList = new ArrayList<String>();
        ArrayList<String> imageArrayList = new ArrayList<String>();
        ArrayList<String> metaDataTagNameArrayList = new ArrayList<String>();
        ArrayList<String> metaDataNameArrayList = new ArrayList<String>();
        ArrayList<String> metaDataContentArrayList = new ArrayList<String>();
        linkStorageArrayList.add("Links: \n");
        imageArrayList.add("Images: \n");
        metaDataTagNameArrayList.add("Tag name: \n");
        metaDataNameArrayList.add("Name Attribute: \n");
        metaDataContentArrayList.add("Content Attribute: \n");

        try {

            Document document = Jsoup.connect(website).get();

            // Extract specific elements from the HTML
            Elements links = document.select("a[href]");
            Elements images = document.select("img[src]");
            Elements metaTags = document.select("meta");

            // Finds all links on a website
            for (Element link : links) {
                String url = link.attr("abs:href");
                linkStorageArrayList.add(url);
            }

            // Finds all images on a website
            for (Element image : images) {
                String imageUrl = image.attr("abs:src");
                imageArrayList.add(imageUrl);
            }
            // Finds all meta data
            for (Element metaTag : metaTags){
                String tagname = metaTag.tagName();
                String name = metaTag.attr("name");
                String content = metaTag.attr("content");
                metaDataTagNameArrayList.add(tagname);
                metaDataNameArrayList.add(name);
                metaDataContentArrayList.add(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        storeData(website, linkStorageArrayList, imageArrayList, metaDataTagNameArrayList, 
        metaDataNameArrayList, metaDataContentArrayList);
    }

    public static void CreatesThreads(int AmountOfThreads, String website) {
        try (BufferedReader br = new BufferedReader(
            new FileReader("websites.txt"))){
                String line = br.readLine();
                while(line != null){
                    websiteList.add(line);
                    line = br.readLine();
                }
            }
            catch (Exception e){
                
            }
        // Create an array to hold the threads
        Thread[] threads = new Thread[AmountOfThreads];

        // Create and start the threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new MyRunnable("Thread " + (i + 1)));
            threads[i].start();
        }

        // Wait for all the threads to finish
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All threads have finished execution.");
    }

    static class MyRunnable implements Runnable {
        private final String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Thread " + name + " started.");
            while(websiteIndex < websiteList.size())
                // lauches the webscraper for each thread
                webScraper(AssignWebsite());

            System.out.println( name + " finished.");
        }
    }
}
