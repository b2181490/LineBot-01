package com.example.linebot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.sql.*;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PreExamDAO {

    private static final String URL = "jdbc:h2:file:~/h2db/linebot-DB;Mode=PostgreSQL;AUTO_SERVER=TRUE";
    private static final String USER_NAME = "b2181490";
    private static final String USER_PASS = "b2181490";


    //　DBのテーブルを接続する。
    @EventMapping
    public List<PreExam> selectPreExams(String week) throws Exception{
        List<PreExam> returning = new ArrayList<PreExam>();
        String sql = "select * from SCHEDULE_TABLE where WEEK in(?)";

        try(Connection conn = DriverManager.getConnection(URL,USER_NAME,USER_PASS);
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,week);
            ResultSet results = stmt.executeQuery();
            while (results.next()){
                var col0 = results.getInt("WEEKKEY");
                var col1 = results.getString("WEEK");
                var col2 = results.getInt("TIME");
                var col3 = results.getString("SUBJECT");
                var preExam = new PreExam(col0,col1,col2,col3);
                returning.add(preExam);
            }
        }
        return returning;
    }
}
