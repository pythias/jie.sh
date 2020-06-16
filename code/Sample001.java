public class Sample001 {
    public static void main(String[] args) {
        for (int round = 0; round < 10; round++) {
            long begin = System.nanoTime();
            int count = 100_000;
            for (int i = 0; i < count; i++) {
                cat();
                //builder();
            }
            System.out.printf("Round %03d, finished %d in %2.3f ms\n", round, count, (System.nanoTime() - begin) / 1_000_000.0);
        }
    }

    private static long cat() {
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
        return a.length();
    }

    private static long builder() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        return sb.toString().length();
    }
}