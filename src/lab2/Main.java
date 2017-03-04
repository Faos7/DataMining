package lab2;

import misc.Numbers;
import misc.FileWorker;
import misc.Recognition;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by faos7 on 3/3/17.
 */
public class Main {
    //коэффициент для отсечения выброса
    static double EmissionFactor(double minSame, double minOther)
    {
        return minSame / minOther;
    }

    public static void main(String[] args){

        double emissionFactor = 0.6;
        int mistakesCounter = 0;
        try {
            String[] all_lines = FileWorker.read("numbers.txt");
            String[] infoLines = FileWorker.read("info.txt");




            List<Numbers> all_numbers = new ArrayList<Numbers>();
            List<Numbers> info_numbers = new ArrayList<Numbers>();
            List<Numbers> study_select = new ArrayList<Numbers>();
            List<Numbers> etalons = new ArrayList<Numbers>();

            for (String line : all_lines) {

                boolean isSameCode = false;
                String[] jn = line.split(" ");
                for (int i = 0; i < all_numbers.size(); i++){ //do not add the same lines
                    if (jn[0] == all_numbers.get(i).getCode_str()){
                        isSameCode = true;
                        break;
                    }
                }
                if (!isSameCode)all_numbers.add(new Numbers(jn[0], Integer.parseInt(jn[1])));
                //считаем минимумы расстояния и сравниваем с коеффициентом выброса, если  коеф. EmissionFactor больше,
                //чем заданный нами коеф. выброса  - не включаем в обучающую выборку
                for (int i =0; i < all_numbers.size(); i++){
                    for (int k = 0; k < all_numbers.size(); k++){
                        if (all_numbers.get(i) != all_numbers.get(k)){
                            int minDistance;
                            boolean isSame;
                            if (all_numbers.get(i).getNumb() == all_numbers.get(k).getNumb())
                                isSame = true;
                            else isSame = false;
                            minDistance = Recognition.Distance(all_numbers.get(i).getCode_str(), all_numbers.get(k).getCode_str());
                            if (isSame){
                                if (minDistance < all_numbers.get(i).getMinToSame())
                                    all_numbers.get(i).setMinToSame(minDistance);
                            }else {
                                if (minDistance < all_numbers.get(i).getMinToOther())
                                    all_numbers.get(i).setMinToOther(minDistance);
                            }
                        }
                    }
                    if (emissionFactor > EmissionFactor(all_numbers.get(i).getMinToSame(),
                            all_numbers.get(i).getMinToOther()))
                        study_select.add(all_numbers.get(i));
                }
            }

            //выбираем первые эталоны (с минимальным значением коеф. EmissionFactor)
            for (int i = 0; i < 10; i++){
                double minEmissionFactor = 1000;
                Numbers temp = new Numbers();
                for (int k = 0; k < study_select.size(); k++){
                    if (study_select.get(k).getNumb() == i){
                        if (minEmissionFactor > EmissionFactor(study_select.get(k).getMinToSame(),
                                study_select.get(k).getMinToOther())){
                            minEmissionFactor = EmissionFactor(study_select.get(k).getMinToSame(),
                                    study_select.get(k).getMinToOther());
                            temp = study_select.get(k);
                        }
                    }
                }
                if (temp.getCode_str() != null)
                    etalons.add(temp);
            }

            //проводим распознавание и добавляем новые эталоны, пока кол-во ошибок не станет равно 0
            mistakesCounter = 1;
            while(mistakesCounter != 0){
                List<Numbers> wrong = new ArrayList<>();
                mistakesCounter = 0;
                for (int i = 0; i < study_select.size(); i++){
                    double distance = 1000;
                    Numbers temp = new Numbers();
                    for (int k = 0; k < etalons.size(); k++){
                        if (Recognition.Distance(study_select.get(i).getCode_str(), etalons.get(k).getCode_str()) < distance){
                            distance = Recognition.Distance(study_select.get(i).getCode_str(), etalons.get(k).getCode_str());
                            temp = etalons.get(k);
                        }
                    }
                    if (temp.getNumb() != study_select.get(i).getNumb()){
                        mistakesCounter++;
                        wrong.add(study_select.get(i));
                    }
                }
                for (int i = 0; i < 10; i++){
                    double coef = -1;
                    Numbers temp = new Numbers();
                    for (int k =0; k< wrong.size(); k++){
                        if (wrong.get(k).getNumb() == i){
                            if (coef < EmissionFactor(wrong.get(k).getMinToSame(), wrong.get(k).getMinToOther())){
                                coef = EmissionFactor(wrong.get(k).getMinToSame(), wrong.get(k).getMinToOther());
                                temp = wrong.get(k);
                            }
                        }
                    }
                    if (temp.getCode_str() != null)
                        etalons.add(temp);
                }
            }

            for (String str: infoLines) {
                String[] jn = str.split(" ");
                info_numbers.add(new Numbers(jn[0], Integer.parseInt(jn[1])));
            }

            int amount = Recognition.Recognition(etalons, info_numbers);

            double persent = (double) amount/info_numbers.size();

            System.out.println(persent);

            String[] results = FileWorker.read("results.txt");

            File file = new File("results.txt");

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            try {
                for (int i = 0; i < results.length; i++) {
                    if (results[i] != "") {
                        out.println(results[i]);
                    }
                }
                out.println(persent * 100);
            }
            finally {
                out.close();
            }
        }catch (Exception e){
            System.err.println("Cant find file.");

        }
    }

}
