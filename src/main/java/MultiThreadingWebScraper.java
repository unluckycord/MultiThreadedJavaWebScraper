package src.main.java;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.FileReader;
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

    public static void webScraper(String website) {
        try {

            Document document = Jsoup.connect(website).get();

            // Extract specific elements from the HTML
            Elements links = document.select("a[href]");
            Elements images = document.select("img[src]");

            // Print the extracted links
            System.out.println("Links:");
            for (Element link : links) {
                String url = link.attr("abs:href");
                System.out.println(url);
            }

            // Print the extracted images
            System.out.println("\nImages:");
            for (Element image : images) {
                String imageUrl = image.attr("abs:src");
                System.out.println(imageUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
