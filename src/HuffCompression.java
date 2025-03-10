import java.io.*;
import java.util.*;

public class HuffCompression {
    private static StringBuilder sb = new StringBuilder();
    private static Map<Byte,String> huffmap= new HashMap<>();

    public static void compress(String src,String dst) {
        try{
            FileInputStream inStream = new FileInputStream(src);
            byte[] b = new byte[inStream.available()];
            inStream.read(b);
            byte[] huffmanBytes = createZip(b);
            OutputStream outStream = new FileOutputStream(dst);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeObject(huffmanBytes);
            objectOutStream.writeObject(huffmap);
            inStream.close();
            objectOutStream.close();
            outStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static byte[] createZip(byte[] bytes){
        PriorityQueue<ByteNode> nodes = getByteNodes(bytes);
        ByteNode root = createHuffmanTree(nodes);
        Map<Byte, String> huffmanCodes = getHuffCodes(root);
        byte[] huffmanCodeBytes = zipBytesWithCodes(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }
    private static PriorityQueue<ByteNode> getByteNodes(byte[] bytes){
        PriorityQueue<ByteNode> nodes = new PriorityQueue<>();
        Map<Byte,Integer> tempMap = new HashMap<>();
        for (byte b : bytes) {
            tempMap.put(b ,tempMap.getOrDefault(b ,0) + 1);
        }
        for(Map.Entry<Byte,Integer> entry :tempMap.entrySet()) {
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }
    private static ByteNode createHuffmanTree(PriorityQueue<ByteNode> nodes){
        while(nodes.size() > 1){
            ByteNode left = nodes.poll();
            ByteNode right = nodes.poll();
            ByteNode parent = new ByteNode(null, left.freq + right.freq);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
        }
        return nodes.poll();
    }
    private static Map<Byte, String> getHuffCodes(ByteNode root){
        if(root == null) return null;
        getHuffCodes(root.left ,"0",sb);
        getHuffCodes(root.right, "1",sb);
        return huffmap;
    }
    private static void getHuffCodes(ByteNode node,String code, StringBuilder sb1){
        StringBuilder sb2 = new StringBuilder(sb1);
        sb2.append(code);
        if(node != null){
            if(node.data == null){
                getHuffCodes(node.left ,"0" ,sb2);
                getHuffCodes(node.right , "1",sb2);
            }else{
                huffmap.put(node.data, sb2.toString());
            }
        }
    }
    private static byte[] zipBytesWithCodes(byte[] bytes, Map<Byte, String> huffCodes) {
        StringBuilder strB = new StringBuilder();
        for (byte b : bytes) {
            strB.append(huffCodes.get(b));
        }
        int length = (strB.length() + 7) / 8;
        byte[] huffCodeBytes = new byte[length];
        int idx = 0;

        // Process 8-bit chunks
        for (int i = 0; i < strB.length(); i += 8) {
            String strByte = strB.substring(i, Math.min(i + 8, strB.length()));
            while (strByte.length() < 8) {
                strByte += "0"; // Pad with trailing zeros
            }
            huffCodeBytes[idx++] = (byte) Integer.parseInt(strByte, 2);
        }
        return huffCodeBytes;
    }

    public static void decompress(String src, String dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            // Read Huffman bytes safely
            Object obj1 = objectInStream.readObject();
            byte[] huffmanBytes = (obj1 instanceof byte[]) ? (byte[]) obj1 : null;
            // Read Huffman codes safely
            Object obj2 = objectInStream.readObject();
            Map<?, ?> tempMap = (obj2 instanceof Map<?, ?>) ? (Map<?, ?>) obj2 : null;
            Map<Byte, String> huffmanCodes = new HashMap<>();
            if (tempMap != null) {
                for (Map.Entry<?, ?> entry : tempMap.entrySet()) {
                    if (entry.getKey() instanceof Byte && entry.getValue() instanceof String) {
                        huffmanCodes.put((Byte) entry.getKey(), (String) entry.getValue());
                    }
                }
            }
            if (huffmanBytes == null || huffmanCodes.isEmpty()) {
                throw new IOException("Corrupted or invalid Huffman data.");
            }
            byte[] bytes = decomp(huffmanCodes, huffmanBytes);
            OutputStream outStream = new FileOutputStream(dst);
            outStream.write(bytes);
            // Close resources
            inStream.close();
            objectInStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] decomp(Map<Byte, String> huffmanCodes , byte[] huffmanBytes){
        StringBuilder sb1 = new StringBuilder();
        for(int i = 0; i < huffmanBytes.length; i++){
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            sb1.append(convertbyteInBit(!flag ,b));
        }
        Map<String,Byte> decodemap = new HashMap<>();
        for(Map.Entry<Byte,String> entry: huffmanCodes.entrySet()){
            decodemap.put(entry.getValue(),entry.getKey());
        }
        List<Byte> list = new ArrayList<>();
        for(int i = 0;i < sb1.length(); ){
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while(flag){
                if (i + count > sb1.length()) break;
                String key = sb1.substring(i,i + count);
                b = decodemap.get(key);
                if (b == null) count++;
                else flag = false;
            }
            list.add(b);
            i += count;
        }
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            if (list.get(i) != null) b[i] = list.get(i);
        }
        return b;
    }
    private  static  String convertbyteInBit(boolean flag, byte b){
        int byte0 = b & 0xFF;
        if (flag) byte0 |= 256;
        String str0 = Integer.toBinaryString(byte0);
        if (flag || byte0 < 0)
            return str0.substring(str0.length() - 8);
        else return str0;
    }
}
