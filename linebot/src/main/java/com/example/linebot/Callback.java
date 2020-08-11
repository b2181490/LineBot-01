package com.example.linebot;

import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@LineMessageHandler
public class Callback {
    private static final Logger log = LoggerFactory.getLogger(Callback.class);



    //改行コード
    String kaigyou_code_1 = System.lineSeparator();

    // フォローイベントに対応する
    @EventMapping
    public TextMessage handleFollow(FollowEvent event) throws Exception {
        // 実際の開発ではユーザIDを返信せず、フォロワーのユーザIDをデータベースに格納しておくなど
        String userId = event.getSource().getUserId();
        return reply("あなたのユーザIDは " + userId);
    }


    // 返答メッセージを作る
    private TextMessage reply(String text) throws Exception {
        PreExamDAO dao = new PreExamDAO();
        List<PreExam> returning = dao.selectPreExams(text);
        if(returning.get(0).getWeekKey()==1 || returning.get(0).getWeekKey()==3){
            return new TextMessage(returning.get(0).getWeek() +"；"+ returning.get(0).getTime() + "講義" +"："+ returning.get(0).getSubject()+kaigyou_code_1
                                    + returning.get(1).getWeek() +"："+ returning.get(1).getTime() + "講義" +"："+ returning.get(1).getSubject()+kaigyou_code_1
                                    + returning.get(2).getWeek() +"："+ returning.get(2).getTime() + "講義" +"："+ returning.get(2).getSubject());
        }else {
            return new TextMessage(returning.get(0).getWeek() +"："+ returning.get(0).getTime() + "講義" +"："+ returning.get(0).getSubject()+kaigyou_code_1
                                    + returning.get(1).getWeek() +"："+ returning.get(1).getTime() + "講義" +"："+ returning.get(1).getSubject());
        }

        /*　for文の中でreturnを書いてはならない
        for (PreExam returns : returning) {

            return new TextMessage(returns.getWeek() + kaigyou_code_1
                    + returns.getTime() + "講義" + kaigyou_code_1
                    + returns.getSubject());

        }*/

        /*　配列番号を指定し、Listに情報が入っているかの確認for文
        for (int i=0 ; i<=3 ; i++){
            return new TextMessage(returning.get(2).getWeek() + kaigyou_code_1
                    + returning.get(2).getTime() +"講義" + kaigyou_code_1
                    + returning.get(2).getSubject());
        }*/
    }



    // 文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        return reply(text);
    }


}




