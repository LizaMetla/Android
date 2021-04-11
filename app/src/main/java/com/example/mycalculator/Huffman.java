package com.example.mycalculator;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman  implements Comparable<Huffman> {

    private Node root;

    public Huffman(Node root) {
        this.root = root;
    }

    private static class Node {
        private Integer frequency;
        private Character character;
        private Node leftChild;
        private Node rightChild;

        public Node(Integer frequency, Character character) {
            this.frequency = frequency;
            this.character = character;
        }

        public Node(Huffman left, Huffman right) {
            frequency = left.root.frequency + right.root.frequency;
            leftChild = left.root;
            rightChild = right.root;
        }

        @Override
        public String toString() {
            return "[id=" + frequency + ", data =" + character + "]";
        }
    }

    public static Map<Character, Integer> CharacterFrequency(String _text) {
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (int i = 0; i < _text.length(); i++) {
            Character c = _text.charAt(i);
            if (charFrequency.containsKey(c))
                charFrequency.put(c, charFrequency.get(c) + 1);
            else
                charFrequency.put(c, 1);
        }
        return charFrequency;
    }

    public static Huffman Create(Map<Character, Integer> _charFrequency) {
        PriorityQueue<Huffman> trees = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> pair : _charFrequency.entrySet()) {
            // Log.d("---DGDG---", pair.getValue() + " " + pair.getKey());
            trees.offer(new Huffman(new Node(pair.getValue(), pair.getKey())));
        }
        while (trees.size() > 1) {
            Huffman left = trees.poll();
            Huffman right = trees.poll();
            trees.offer(new Huffman(new Node(left, right)));
        }
        return trees.poll();
    }

    public Map<Character, String> CodeTable() {
        Map<Character, String> codeTable = new HashMap<>();
        CodeTable(root, new StringBuilder(), codeTable);
        return codeTable;
    }

    private void CodeTable(Node _node, StringBuilder _code, Map<Character, String> _table) {
        if (_node.character != null) {
            _table.put(_node.character, _code.toString());
            return;
        }
        CodeTable(_node.leftChild, _code.append('0'), _table);
        _code.deleteCharAt(_code.length() - 1);
        CodeTable(_node.rightChild, _code.append('1'), _table);
        _code.deleteCharAt(_code.length() - 1);
    }

    public BitSet Encode(String text) {
        BitSet bitSet = new BitSet();
        Map<Character, String> codeTable = CodeTable();
        StringBuilder result = new StringBuilder();
        int i = 0;
        for ( i = 0; i < text.length(); i++) {
            result.append(codeTable.get(text.charAt(i)));
        }
        String code = result.toString();
        for (i = 0; i < code.length(); i++) {
            if (code.charAt(i) == '0') {
                bitSet.set(i, false);
            } else {
                bitSet.set(i, true);
            }
        }
        bitSet.set(i, true); // dummy bit set to know the length
        return bitSet;
    }
    public String Decode(BitSet _bitSet){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < (_bitSet.length() - 1); ) {
            Node temp = root;
            while (temp.leftChild != null) {
                if (!_bitSet.get(i)) {
                    temp = temp.leftChild;
                } else {
                    temp = temp.rightChild;
                }
                i = i + 1;
            }
            stringBuilder.append(temp.character);
        }
        return stringBuilder.toString();
    }
    @Override
    public int compareTo(Huffman tree) {
        return root.frequency - tree.root.frequency;
    }
}
