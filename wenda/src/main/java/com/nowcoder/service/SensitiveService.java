package com.nowcoder.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService {

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
}
