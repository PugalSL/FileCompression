public class ByteNode implements Comparable<ByteNode>{
    Byte data;
    int freq;
    ByteNode left;
    ByteNode right;
    ByteNode(Byte data,int freq){
        this.data = data;
        this.freq = freq;
    }
    @Override
    public int compareTo(ByteNode o) {
        return Integer.compare(this.freq, o.freq);
    }
}
