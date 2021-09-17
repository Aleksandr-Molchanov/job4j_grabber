package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final SqlRuDateTimeParser sqlRuDateTimeParser;

    public SqlRuParse(SqlRuDateTimeParser sqlRuDateTimeParser) {
        this.sqlRuDateTimeParser = sqlRuDateTimeParser;
    }

    public static void main(String[] args) {
        SqlRuParse s = new SqlRuParse(new SqlRuDateTimeParser());
        System.out.println(s.list("https://www.sql.ru/forum/job-offers"));
    }

    @Override
    public List<Post> list(String link) {
        List<Post> rsl = new ArrayList<>();
        try {
            for (int i = 1; i < 6; i++) {
                String s = link + "/" + i;
                Document doc = Jsoup.connect(s).get();
                Elements row = doc.select(".postslisttopic");
                for (Element td : row) {
                    Element href = td.child(0);
                    rsl.add(detail(href.attr("href")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post detail(String link) {
        Post rsl = new Post();
        try {
            Document doc = Jsoup.connect(link).get();
            rsl.setTitle(doc.select(".messageHeader").first().text().split(" \\[")[0]);
            rsl.setLink(link);
            rsl.setDescription(doc.select(".msgBody").get(1).text());
            String cr = doc.select(".msgFooter").first().text().split(" \\[")[0];
            LocalDateTime created =
                    sqlRuDateTimeParser.parse(cr);
            rsl.setCreated(created);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}