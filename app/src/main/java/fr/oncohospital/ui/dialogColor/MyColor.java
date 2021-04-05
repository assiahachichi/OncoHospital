package fr.oncohospital.ui.dialogColor;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by Assia HACHICHI on 27/03/2021
 */
public class MyColor {
    private static ArrayList<String> myColorNames;
    private static ArrayList<Integer> myColorValues;

    private static void getMyColors() {
        myColorNames = new ArrayList<String>();
        myColorValues = new ArrayList<Integer>();
        myColorNames.add("RED"); myColorValues.add(Color.RED);
        myColorNames.add("MAGENTA"); myColorValues.add(Color.MAGENTA);
        myColorNames.add("YELLOW"); myColorValues.add(Color.YELLOW);
        myColorNames.add("GREEN"); myColorValues.add(Color.GREEN);
        myColorNames.add("CYAN"); myColorValues.add(Color.CYAN);
        myColorNames.add("BLUE"); myColorValues.add(Color.BLUE);
    }

    public static ArrayList<String> getColorNames(){
        getMyColors();
        return myColorNames;
    }

    public static ArrayList<Integer> getColorValues(){
        getMyColors();
        return myColorValues;
    }

    public static String getNameColor(int iColor){
        getMyColors();
        int i = 0;
        for (int jColor : myColorValues ){
            if (jColor == iColor) return (String) myColorNames.get(i);
            i++;
        }
        return " ";
    }
    public static int getIndex(int iColor){
        getMyColors();

        int i = 0;
        for (int jColor : myColorValues ){
            if (jColor == iColor) {
                return i;
            }else {
                i++;
            }
        }
        return i-1;
    }

    public static int getIndex(String sColor){
        getMyColors();
        int i = 0;
        for (String s : myColorNames ){
            if (s.equals(sColor)) return i;
            i++;
        }
        return i-1;
    }

    public static int getColor(String sColor){
        getMyColors();
        int i = 0;
        for (String s : myColorNames ){
            if (s.equals(sColor)) return myColorValues.get(i);
            i++;
        }
        return myColorValues.get(0);
    }


    public static int getColor(int index){
        getMyColors();
        return myColorValues.get(index);
    }
}
