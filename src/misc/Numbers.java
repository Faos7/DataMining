package misc;

import java.util.List;

/**
 * Created by faos7 on 3/3/17.
 */
public class Numbers {

    private String code_str;
    private int numb;
    private double minToSame;
    private double minToOther;

    public Numbers() { }

    public Numbers(String str, int num)
    {
        code_str = str;
        numb = num;
        minToSame = 1000;
        minToOther = 1000;
    }

    public int getNumb() {
        return numb;
    }

    public void setNumb(int numb) {
        if (numb <0 || numb > 9){
            System.err.println("You should write a right number.");
        }else
        this.numb = numb;
    }

    public double getMinToSame() {
        return minToSame;
    }

    public void setMinToSame(double minToSame) {
        if (minToSame < 0){
            System.err.println("You should write a right minimal distance to the Same class.");
        }
        this.minToSame = minToSame;
    }

    public double getMinToOther() {
        return minToOther;
    }

    public void setMinToOther(double minToOther) {
        if (minToOther < 0){
            System.err.println("You should write a right minimal distance to the Other class.");
        }else
        this.minToOther = minToOther;
    }

    public String getCode_str() {
        return code_str;
    }

    public void setCode_str(String code_str) {
        if (code_str.length() > 35){
            System.err.println("You shoud write a right code.");
            this.code_str = "";

        }else
        this.code_str = code_str;
    }

    public void Draw()
    {
        char[] char_str = code_str.toCharArray();
        int counter = 0;
        for (int i = 0; i < 35; i++)
        {
            if (counter != 5)
            {
                System.out.print(char_str[i]);
                counter++;
            }
            else
            {
                System.out.println();
                System.out.print(char_str[i]);
                counter = 1;
            }
        }
    }



}
