package com.example.algostudy.service;

import com.example.algostudy.domain.entity.Member;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

@Service
public class CrawlingService {

    public Document crawler(Member member) throws IOException, ParseException {
        String bojId = member.getBojId();
        String url = "https://www.acmicpc.net/status?problem_id=&user_id=" + bojId + "&language_id=-1&result_id=-1";
        Connection conn = Jsoup.connect(url);
        Document html = conn.get();
        return conn.get();
    }

    public boolean checkRecent(Document html) {
        //LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate yesterday = LocalDate.now();
        ArrayList<Object> objects = new ArrayList<>();

        Element table = html.getElementById("status-table");
        Element tbody = table.child(1);
        for (Element tr : tbody.children()) {
            Element dateTd = tr.child(tr.childrenSize() - 1);
            Element resultTd = tr.child(3);

            Element child = dateTd.child(0);
            String dateTimeString = child.attributes().get("title");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate localDate = localDateTime.toLocalDate();

            if (localDate.equals(yesterday)) {
                String successYn = tr.child(3).child(0).childNode(0).attr("#text");
                if (successYn.equals("맞았습니다!!")) {
                    return true;
                }
            }
            else{
                if (localDate.isBefore(yesterday)) {
                    return false;
                }
            }
        }
        return false;
    }



    public void getRecentDate(Document html) {
        Element table = html.getElementById("status-table");
        Element tbody = table.child(1);
        for (Element tr : tbody.children()) {
            Element td = tr.child(tr.childrenSize() - 1);
            Element child = td.child(0);
            String dateTimeString = child.attributes().get("title");
            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDate localDate = localDateTime.toLocalDate();
            LocalTime localTime = localDateTime.toLocalTime();
        }
    }
}
