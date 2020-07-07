package com.example.recyclerviewhw.recycler;

import java.util.ArrayList;
import java.util.List;

public class TestDataSet {

    public static List<MessageData> getData() {
        List<MessageData> result = new ArrayList();
        result.add(new MessageData("张东升","一起爬山吗", "20:20pm", 1));
        result.add(new MessageData("张朝阳","这个暑假我没时间学习", "20:20pm", 0));
        result.add(new MessageData("老番茄","好兄弟~", "20:20pm", 0));
        result.add(new MessageData("小刘","吃饭了吗", "20:20pm", 0));
        result.add(new MessageData("老王","吃饭了吗", "20:20pm", 5));
        result.add(new MessageData("阿俊","吃饭了吗", "20:20pm", 1));
        result.add(new MessageData("阿基","吃饭了吗", "20:20pm", 3));
        result.add(new MessageData("老陈","吃饭了吗", "20:20pm", 1));
        return result;
    }

}
