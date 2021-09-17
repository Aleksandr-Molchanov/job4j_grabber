package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;

public class SqlRuParse {

    private static final SqlRuDateTimeParser PARSER = new SqlRuDateTimeParser();

    public static void main(String[] args) {
        /*String https = "https://www.sql.ru/forum/job-offers";
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
        }*/
        System.out.println(load("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t"));
    }

    public static Post load(String url) {
        Post rsl = new Post();
        try {
            Document doc = Jsoup.connect(url).get();
            rsl.setTitle(doc.select(".messageHeader").first().text().split(" \\[")[0]);
            rsl.setLink(url);
            rsl.setDescription(doc.select(".msgBody").get(1).text());
            String cr = doc.select(".msgFooter").first().text().split(" \\[")[0];
            LocalDateTime created =
                    PARSER.parse(cr);
            rsl.setCreated(created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}