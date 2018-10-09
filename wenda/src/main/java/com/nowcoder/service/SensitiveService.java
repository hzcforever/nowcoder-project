package com.nowcoder.service;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        InputStream is = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String lineTxt;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                addWord(lineTxt.trim());
            }

        } catch (Exception e) {
            logger.error("读取敏感词文件失败" + e.getMessage());
        } finally {
            bufferedReader.close();
            reader.close();
            is.close();
        }
    }

    //增加敏感词
    private void addWord(String lineTxt) {
        TireNode tempNode = rootNode;
        for (int i = 0;i < lineTxt.length();i++) {
            Character c = lineTxt.charAt(i);
            TireNode node = tempNode.getSubNode(c);
            if (node == null) {
                node = new TireNode();
                tempNode.addSubNode(c, node);
            }
            tempNode = node;
            if (i == lineTxt.length() - 1) {
                tempNode.setkeywordEnd(true);
            }
        }
    }

    private class TireNode {
        private boolean end = false; //是不是关键词的结尾

        private Map<Character, TireNode> subNodes = new HashMap<>(); //当前节点下所有的子节点

        public void addSubNode(Character key, TireNode node) {
            subNodes.put(key, node);
        }

        public TireNode getSubNode(Character key) {
            return subNodes.get(key);
        }

        public boolean isKeywordEnd() {
            return end;
        }

        public void setkeywordEnd(boolean end) {
            this.end = end;
        }
    }

    private TireNode rootNode = new TireNode();

    private boolean isSymbol(char c) {
        int ic = (int) c;
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        String replacement = "***";
        TireNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        while (position < text.length()) {
            char c = text.charAt(position);
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                result.append(text.charAt(begin));
                position = begin + 1;
                begin = position;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd()) {
                //发现敏感词
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                position++;
            }
        }
        result.append(text.substring(begin));
        return result.toString();
    }

    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("赌博");
        s.addWord("色狼");
        System.out.println(s.filter("i色情，hello，去赌博吗？"));
    }
}
