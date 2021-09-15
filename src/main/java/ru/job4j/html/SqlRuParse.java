package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String https = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i < 6; i++) {
            String s = https + "/" + i;
            Document doc = Jsoup.connect(s).get();
            Elements row = doc.select(".postslisttopic");
            Elements date = doc.select(".altCol");
            for (Element td : row) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(td.parent().child(5).text());
            }
        }
    }
}