package misc;

import java.io.*;

/**
 * Created by faos7 on 3/3/17.
 */
public class FileWorker {

    public static void write(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            //проверяем, что если файл не существует то создаем его
            if(!file.exists()){
                file.createNewFile();
            }

            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                //Записываем текст у файл
                out.print(text);
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void exists(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        if (!file.exists()){
            throw new FileNotFoundException(file.getName());
        }
    }

    public static String[] read(String fileName) throws FileNotFoundException {



        exists(fileName);

        File file = new File(fileName);

        int lineNo = 0;

        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;

                while ((s = in.readLine()) != null) {
                    lineNo++;
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        String[] res = new String[lineNo];
        try {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
            try {
                //В цикле построчно считываем файл
                String s;
                int i = 0;
                while ((s = in.readLine()) != null) {
                    res[i] = s;
                    i++;
                }
            } finally {
                //Также не забываем закрыть файл
                in.close();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        //Возвращаем полученный текст с файла
        return res;
    }
}
