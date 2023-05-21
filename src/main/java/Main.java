package src.main.java;
public class Main{
    public static void main(String[] args){
        //in order to use this, you must spectify the ammount to threads to run and a website text file
        MultiThreadingWebScraper.CreatesThreads(100, "websites.txt");
    }
}