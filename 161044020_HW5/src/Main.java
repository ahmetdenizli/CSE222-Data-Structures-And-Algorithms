import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

public class Main {

    private static BufferedImage originalImage;

    public static void main(String[] args) {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter File path : ");
        String filename = null;
        try {
            filename = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            originalImage = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Color[][] result = new Color[width][height];

        MyPriorityQueue<Color> PQLEX = new MyPriorityQueue<Color>(200, new LEX());
        MyPriorityQueue<Color> PQEUC = new MyPriorityQueue<Color>(200, new EUC());
        MyPriorityQueue<Color> PQBMX = new MyPriorityQueue<Color>(200, new BMX());

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (count != width*height){
                    if (!PQLEX.isEmpty()) {
                        Color ret = PQLEX.poll();
                        System.out.println("Thread2-PQLEX: [" + ret.getRed() + ", " + ret.getGreen() + ", " + ret.getBlue() + "]");
                        count++;
                    }
                }

            }
        });

        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (count != width*height){
                    if (!PQEUC.isEmpty()) {
                        Color ret = PQEUC.poll();
                        System.out.println("Thread3-PQEUC: [" + ret.getRed() + ", " + ret.getGreen() + ", " + ret.getBlue() + "]");
                        count++;
                    }
                }
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                int count=0;
                while (count != width*height){
                    if (!PQBMX.isEmpty()) {
                        Color ret = PQBMX.poll();
                        System.out.println("Thread4-PQBMX: [" + ret.getRed() + ", " + ret.getGreen() + ", " + ret.getBlue() + "]");
                        count++;
                    }
                }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isStarted = false;
                Color temp;
                for(int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        result[i][j] = new Color(originalImage.getRGB(i, j));
                        PQLEX.offer(result[i][j]);
                        PQEUC.offer(result[i][j]);
                        PQBMX.offer(result[i][j]);
                        int tem = (i+1)*(j+1)+j+1;
                        System.out.println("Thread 1: [" + result[i][j].getRed() + ", " + result[i][j].getGreen() + ", " + result[i][j].getBlue() + "]" + tem );

                        if (!isStarted && i+j > 99){
                            thread2.start();
                            thread3.start();
                            thread4.start();
                            isStarted = true;
                        }
                    }
                }
            }
        });
        thread1.start();

    }

    static class LEX implements Comparator<Color>{

        @Override
        public int compare(Color o1, Color o2) {
            if (o1 == null || o2 == null)
                throw new NullPointerException();

            if (o1.getRed() > o2.getRed())
                return 1;
            else if (o1.getRed() == o2.getRed()){
                if (o1.getGreen() > o2.getGreen())
                    return 1;
                else if (o1.getGreen() == o2.getGreen()){
                    if (o1.getBlue() > o2.getBlue())
                        return 1;
                    else
                        return 0;
                }
                else
                    return 0;
            }
            else
                return 0;
        }
    }

    static class EUC implements Comparator<Color>{

        @Override
        public int compare(Color o1, Color o2) {
            if (o1 == null || o2 == null)
                throw new NullPointerException();

            int L2norm_1st = (o1.getRed()*o1.getRed() + o1.getGreen()*o1.getGreen() + o1.getBlue()*o1.getBlue());
            int L2norm_2nd = (o2.getRed()*o2.getRed() + o2.getGreen()*o2.getGreen() + o2.getBlue()*o2.getBlue());

            if (L2norm_1st > L2norm_2nd)
                return 1;
            else if (L2norm_1st < L2norm_2nd)
                return -1;
            else
                return 0;
        }
    }

    static class BMX implements Comparator<Color>{

        /**
         * Compares its two arguments for order.  Returns a negative integer,
         * zero, or a positive integer as the first argument is less than, equal
         * to, or greater than the second.<p>
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return a negative integer, zero, or a positive integer as the
         * first argument is less than, equal to, or greater than the
         * second.
         * @throws NullPointerException if an argument is null and this
         *                              comparator does not permit null arguments
         * @throws ClassCastException   if the arguments' types prevent them from
         *                              being compared by this comparator.
         */
        @Override
        public int compare(Color o1, Color o2) {
            if (o1 == null || o2 == null)
                throw new NullPointerException();

            for (int i = 0; i < 8; i++) {
                if( (o1.getRed() >> (7-i) & 0b1) > (o2.getRed() >> (7-i) & 0b1) )
                    return 1;
                else if( (o1.getRed() >> (7-i) & 0b1) < (o2.getRed() >> (7-i) & 0b1) )
                    return 0;
                else if ( (o1.getGreen() >> (7-i) & 0b1) > (o2.getGreen() >> (7-i) & 0b1) )
                    return 1;
                else if ( (o1.getGreen() >> (7-i) & 0b1) < (o2.getGreen() >> (7-i) & 0b1) )
                    return 0;
                else if ( (o1.getBlue() >> (7-i) & 0b1) > (o2.getBlue() >> (7-i) & 0b1) )
                    return 1;
                else if ( (o1.getBlue() >> (7-i) & 0b1) < (o2.getBlue() >> (7-i) & 0b1) )
                    return 0;
            }

            return 0;
        }

    }
}
