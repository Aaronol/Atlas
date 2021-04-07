package com.example.atlas.util;

import com.alibaba.druid.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.List;

public class ParseHtml {
    private static String extractText(Node node) {
        /* TextNode直接返回结果 */
        if (node instanceof TextNode) {
            return ((TextNode) node).text();
        }
        /* 非TextNode的Node，遍历其孩子Node */
        List<Node> children = node.childNodes();
        StringBuffer buffer = new StringBuffer();
        for (Node child : children) {
            buffer.append(extractText(child));
        }
        return buffer.toString();
    }

    private static String cycle(Node node, StringBuffer ssb) {
        if (node instanceof Element && StringUtils.equals(((Element) node).tagName(), "body")) {
            ssb.append(extractText(node));
        } else {
            List<Node> nodes = node.childNodes();
            nodes.forEach(x -> ssb.append(cycle(x, ssb)));
        }
        return ssb.toString();
    }

    /* 使用jsoup解析html并转化为提取字符串*/
    public static String html2Str(String html) {
        Document doc = Jsoup.parse(html);
        StringBuffer nsb = new StringBuffer();
        return cycle(doc, nsb);
    }
}
