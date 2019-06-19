public class Main {

    public static void main(String[] args) {


        int data_arr[][]={{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};

        MyIterator iterator = new MyIterator(data_arr);

        for (MyIterator it = iterator; it.hasNext(); ) {
            int data = (Integer) it.next();

            System.out.print(data + " ");
        }
        System.out.println();

    }
}
