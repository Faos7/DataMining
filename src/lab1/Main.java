package lab1;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by faos7 on 3/3/17.
 */
public class Main {

    public static void main(String[] args) throws IOException{
        List<String> noise = new ArrayList<>(1000);


        int size = 36;
        String  line = "";
        int len = 0;
        File file = new File("start.txt");

        System.out.println("Set copies of 1: ");
        int k1 = getInt();
        System.out.println("Set copies of 2: ");
        int k2 = getInt();
        System.out.println("Set copies of 3: ");
        int k3 = getInt();
        System.out.println("Input epsilon(0<epsilon<1): ");
        double eps = getDouble();
        int count = 0;


        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;

                while ((s = in.readLine()) != null) {
                    line = s;
                    switch (count){
                        case 0:
                            for (int i = 0; i < k1; i++){
                                noise.add(MakeNoise(size, line, eps, " 1"));
                            }
                            break;
                        case 1:
                            for (int i =0; i < k2; i++){
                                noise.add(MakeNoise(size, line, eps," 2"));
                            }break;
                        case 2:
                            for (int i = 0; i < k3; i++){
                                noise.add(MakeNoise(size, line, eps, " 3"));
                            }break;
                        default:
                            break;
                    }
                    line += "\n";
                    count++;
                }
            } finally {
                in.close();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        Collections.shuffle(noise);
        File file1 = new File("numbers.txt");

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file1.exists()){
                file1.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file1.getAbsoluteFile());

            try {
                //Записываем текст у файл
                for (String item : noise) {
                    out.println(item);
                }

            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static String MakeNoise(int size, String line,double eps, String symbol)
    {
        //Random rd = new Random(10);
        Random rd = new Random(System.currentTimeMillis());


        double[] resmas = new double[size];

        char[] lines = line.toCharArray();


        int con = 0;
        for (int i = 0; i < resmas.length; i++)
        {
            resmas[i] = rd.nextDouble();
            if (resmas[i] < eps)
            {
                switch (lines[i])
                {
                    case '0':
                        lines[i] = '1';
                        break;
                    case '1':
                        lines[i] = '0';
                        break;

                }
            }
            con++;
            System.out.print(lines[i]);
            if (con%6==0)
            {
                System.out.println();
            }
            if (con==36)
            {
                System.out.println();
            }
        }

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < line.length(); i++)
        {
            sb2.append(lines[i]);
        }
        sb2.append(symbol);
        String ln = sb2.toString().trim();
        ln = ln.replace("\r\n", "");


        return ln;
    }

    private static int getInt() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return Integer.parseInt(reader.readLine());
    }

    private static double getDouble() throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        return Double.parseDouble(reader.readLine());
    }

    public static int rnd(int max)
    {
        return (int) (Math.random() * ++max);
    }
}
