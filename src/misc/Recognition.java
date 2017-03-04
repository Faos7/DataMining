package misc;

import java.util.List;

/**
 * Created by faos7 on 3/4/17.
 */
public final class Recognition {
    public static int Distance(String str1, String str2)
    {
        int distance = 0;
        for(int i=0;i<str1.length();i++)
        {
            if (str1.charAt(i) != str2.charAt(i))
                distance++;
        }
        return distance;
    }

    public static int Recognition(List<Numbers> etalons, List<Numbers> info)
    {
        int mistakesCounter = 0;
        for (int i = 0; i < info.size(); i++)
        {
            double distance = 1000;
            Numbers temp = new Numbers();
            for (int k = 0; k < etalons.size(); k++)
            {
                if (Distance(info.get(i).getCode_str(), etalons.get(k).getCode_str()) < distance)
                {
                    distance = Distance(info.get(i).getCode_str(), etalons.get(k).getCode_str());
                    temp = etalons.get(k);
                }
            }
            if (temp.getNumb() != info.get(i).getNumb())
            {
                mistakesCounter++;
            }
        }
        return mistakesCounter;
    }

}
