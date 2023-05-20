import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Webscraper{

    public static Document webScraperWebsitePicker(String website) throws IOException{
        Document document = Jsoup.connect(website).get();
        return document;
    }

    public static void webScraper(String string) {
        try {

            Document document = Jsoup.connect("https://apple.com").get();

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
}