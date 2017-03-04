package lab3;

import misc.FileWorker;
import misc.Numbers;
import misc.Recognition;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by faos7 on 3/4/17.
 */
public class Main {

    static double ArithmeticalMean(String[] numbers)
    {
        double answer = 0;
        for (int i = 0; i < numbers.length; i++)
            answer = answer + Double.parseDouble(numbers[i]);
        return answer / numbers.length;
    }

    //медиана
    static double Median(String[] numbers)
    {
        double answer = 0;
        double[] num = new double[numbers.length];
        for (int i = 0; i < num.length; i++)
            num[i] = Double.parseDouble(numbers[i]);
        Arrays.sort(num);
        if(num.length % 2 == 0)
        {
            answer = num[num.length / 2] + num[num.length / 2 - 1];
            answer = answer / 2;
            return answer;  //полусумма средних чисел
        }
        else
        {
            answer = num[(num.length - 1) / 2]; //число стоящее посередине
            return answer;
        }
    }

    //разброс
    static double Scatter(String[] numbers)
    {
        double answer = 0;
        double[] num = new double[numbers.length];
        for (int i = 0; i < num.length; i++)
            num[i] = Double.parseDouble(numbers[i]);
        Arrays.sort(num);

        answer = num[num.length - 1] - num[0]; //максимальный - минимальный
        return answer;
    }

    //среднеквадратичное отклонение - квадратный корень из среднего арифметического
    //всех квадратов разностей между данными величинами и их средним арифметическим
    static double StandardDeviation(String[] numbers) {
        double answer = 0;
        double arithMean = ArithmeticalMean(numbers);

        double[] num = new double[numbers.length];
        for (int i = 0; i < num.length; i++)
            num[i] = Double.parseDouble(numbers[i]);

        for (int i = 0; i < num.length; i++) //квадраты разностей между данными величинами и их средним арифметическим
        {
            double temp = 0;
            temp = num[i] - arithMean;
            temp = temp * temp;
            answer = answer + temp;
        }

        answer = answer / numbers.length;
        answer = Math.sqrt(answer);

        return answer;
    }




    public static void main(String[] args)throws Exception{

        int parts = 10;

        String[] lines = FileWorker.read("numbers.txt");
        String[][] numbers = new String[parts][lines.length/ parts];

        //разделяем на части учебную выборку и записываем ее в массив numbers
        int counter = 0;
        for(int i=0;i< parts;i++)
        {
            for(int j=0;j< lines.length / parts;j++)
            {
                numbers[i][j] = lines[counter];
                counter++;
            }
        }

        //формируем новый файл numbers.txt проводим обучение и распознавание
        //записываем полученный результат в файл results.txt
        //формируя массив процентов неправильно распознанных объектов
        for (int i=0;i< parts;i++)
        {
            List<String> numbersInFile = new ArrayList<>();
            for(int j=0;j< parts;j++)
            {
                if(j != i)
                {
                    for (int k = 0; k < lines.length / parts; k++)
                    {
                        numbersInFile.add(numbers[j][k]);
                    }
                }
            }
            File numbersFile = new File("numbers.txt");
            PrintWriter out = new PrintWriter(numbersFile.getAbsoluteFile());
            try {
                for (String str : numbersInFile) {
                    out.println(str);
                }
            }
            finally {
                out.close();
            }

            File file = new File("info.txt");
            PrintWriter fileOut = new PrintWriter(numbersFile.getAbsoluteFile());
            try {
                for (int n = 0; n < lines.length/parts; n++) {
                    out.println(numbers[i][n]);
                }
            }
            finally {
                out.close();
            }
            lab2.Main.main(args);
            Thread.sleep(2500);
        }

        String[] answers = FileWorker.read("results.txt");

        System.out.println("Среднее арифметическое: " + ArithmeticalMean(answers));
        System.out.println("Медиана: " + Median(answers));
        System.out.println("Разброс: " + Scatter(answers));
        System.out.println("Среднеквадратическое отклонение: " + StandardDeviation(answers));
    }

}
