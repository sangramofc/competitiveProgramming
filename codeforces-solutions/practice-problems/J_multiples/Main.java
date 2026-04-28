import java.io.*;
import java.util.*;
import java.math.*;

public class Main {
    InputStream is;
    PrintWriter out;
    String INPUT = "";

    final int inf     = (int)  1e9 + 9;
    final long biginf = (long) 1e18 + 7;
    final long mod    = (long) 1e9  + 7;

    // int[] dx = {0,0,1,-1};
    // int[] dy = {1,-1,0,0};
    // int[] ddx = {0,0,1,-1,1,-1,1,-1};
    // int[] ddy = {1,-1,0,0,1,-1,-1,1};

    void solve() throws Exception {
        int a = ni();
        int b = ni();

        out.println((a%b==0 || b %a == 0 )?"Multiples":"No Multiples");
    }

    // ---------- math helpers ----------
    long pow(long a, long b) {
        long result = 1;
        while (b > 0) {
            if (b % 2 == 1) result = (result * a) % mod;
            b /= 2; a = (a * a) % mod;
        }
        return result;
    }
    long gcd(long a, long b) { return b == 0 ? a : gcd(b, a % b); }
    long lcm(long a, long b) { return a / gcd(a, b) * b; }

    // ---------- pair ----------
    static class pair implements Comparable<pair> {
        long x, y;
        pair(long i, long j) { x = i; y = j; }
        public int compareTo(pair p) {
            return x != p.x ? Long.compare(x, p.x) : Long.compare(y, p.y);
        }
        public int     hashCode()       { return (x + " " + y).hashCode(); }
        public String  toString()        { return x + " " + y; }
        public boolean equals(Object o) { pair q=(pair)o; return x==q.x && y==q.y; }
    }

    // ---------- graph helpers ----------
    // undirected: ud=true  |  directed: ud=false
    private int[][] ng(int n, int e, int[] f, int[] t, boolean ud) {
        int[][] g = new int[n+1][]; int[] c = new int[n+1];
        for (int i=0;i<e;i++) { c[f[i]]++; if(ud) c[t[i]]++; }
        for (int i=0;i<=n;i++) g[i] = new int[c[i]];
        for (int i=0;i<e;i++) { g[f[i]][--c[f[i]]]=t[i]; if(ud) g[t[i]][--c[t[i]]]=f[i]; }
        return g;
    }
    // weighted graph: each entry is {neighbor, edgeIndex, direction}
    private int[][][] nwg(int n, int e, int[] f, int[] t, boolean ud) {
        int[][][] g = new int[n+1][][]; int[] c = new int[n+1];
        for (int i=0;i<e;i++) { c[f[i]]++; if(ud) c[t[i]]++; }
        for (int i=0;i<=n;i++) g[i] = new int[c[i]][];
        for (int i=0;i<e;i++) {
            g[f[i]][--c[f[i]]] = new int[]{t[i], i, 0};
            if(ud) g[t[i]][--c[t[i]]] = new int[]{f[i], i, 1};
        }
        return g;
    }

    // ---------- output ----------
    void pn(Object... o)  { for(int i=0;i<o.length;i++) out.print(o[i]+(i+1<o.length?" ":"\n")); }
    void pni(Object... o) { for(Object oo:o) out.print(oo+" "); out.println(); out.flush(); }
    private void dbg(Object... o) { System.err.println(Arrays.deepToString(o)); }

    // ---------- boilerplate ----------
    void run() throws Exception {
        is = INPUT.isEmpty() ? System.in : new ByteArrayInputStream(INPUT.getBytes());
        out = new PrintWriter(System.out);
        long s = System.currentTimeMillis();
        solve();
        out.flush();
        System.err.println(System.currentTimeMillis()-s+"ms");
    }
    public static void main(String[] args) throws Exception {
        boolean memory = false;
        if (memory) new Thread(null, new Runnable() {
            public void run() { try { new Main().run(); } catch(Exception e) { e.printStackTrace(); System.exit(1); } }
        }, "1", 1<<28).start();
        else new Main().run();
    }

    // ---------- fast I/O ----------
    private byte[] inbuf = new byte[1<<16];
    public int lenbuf = 0, ptrbuf = 0;
    private int readByte() {
        if (lenbuf == -1) throw new InputMismatchException();
        if (ptrbuf >= lenbuf) {
            ptrbuf = 0;
            try { lenbuf = is.read(inbuf); } catch (IOException e) { throw new InputMismatchException(); }
            if (lenbuf <= 0) return -1;
        }
        return inbuf[ptrbuf++];
    }
    private boolean isSpaceChar(int c) { return !(c >= 33 && c <= 126); }
    private int skip() { int b; while((b=readByte())!=-1 && isSpaceChar(b)); return b; }

    private int ni() {
        int num=0, b; boolean m=false;
        while((b=readByte())!=-1 && !((b>='0'&&b<='9') || b=='-'));
        if(b=='-'){m=true; b=readByte();}
        while(true){if(b>='0'&&b<='9') num=num*10+(b-'0'); else return m?-num:num; b=readByte();}
    }
    private long nl() {
        long num=0; int b; boolean m=false;
        while((b=readByte())!=-1 && !((b>='0'&&b<='9') || b=='-'));
        if(b=='-'){m=true; b=readByte();}
        while(true){if(b>='0'&&b<='9') num=num*10+(b-'0'); else return m?-num:num; b=readByte();}
    }
    private double nd() { return Double.parseDouble(ns()); }
    private char   nc() { return (char) skip(); }

    // reads next token as String
    private String ns() {
        int b = skip();
        StringBuilder sb = new StringBuilder();
        while (!isSpaceChar(b)) { sb.appendCodePoint(b); b = readByte(); }
        return sb.toString();
    }
    // reads exactly n chars as char[]  <-- THIS was missing, caused your error
    private char[] ns(int n) {
        char[] buf = new char[n];
        int b = skip(), p = 0;
        while (p < n && !isSpaceChar(b)) { buf[p++] = (char)b; b = readByte(); }
        return n == p ? buf : Arrays.copyOf(buf, p);
    }
    private int[]   na(int n)         { int[] a=new int[n];    for(int i=0;i<n;i++) a[i]=ni(); return a; }
    private long[]  nal(int n)         { long[] a=new long[n];  for(int i=0;i<n;i++) a[i]=nl(); return a; }
    // reads n x m char grid
    private char[][] nm(int n, int m)  { char[][] map=new char[n][]; for(int i=0;i<n;i++) map[i]=ns(m); return map; }
}