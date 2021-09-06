package com.fju;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Tester {
    private static HttpURLConnection connection;
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.println("Welcome to FJU DVD rental store.");
        System.out.println("Enter a key word to find your movie or series.(like:harry/superman/spider)");
        //api connection
        while(true) {
            try {
                String keyword = sc.nextLine();
                String search = "https://www.omdbapi.com/?apikey=4c75f77b" + "&s=" + keyword;
                URL url = new URL(search);
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = reader.readLine();
                    StringBuffer responseContent = new StringBuffer();
                    while (line != null) {
                        responseContent.append(line);
                        line = reader.readLine();
                    }
                    reader.close();
                    parse(responseContent.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            System.out.println("Did you find it ?");
            System.out.println("Enter 'Y' to rent a movie or enter 'N' to search again.");
            String dd ;
            dd= sc.nextLine();
            if (dd.equals("Y")) {
                break;
            }
            if (dd.equals("N")) {
                System.out.println("Please enter again to find your movie.");
            }
        }
        System.out.println("Insert the DVD name you want to rent.");
        String dvd;
        dvd= sc.nextLine();
        System.out.println("How many days do you want to rent ?");
        System.out.println("Please insert a number :");
        //Calendar
        Calendar todaycal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int exdate=sc.nextInt();
        System.out.println("This is your order ! ");
        System.out.println("DVD name : "+dvd);
        //cost
        Cost[] costs={new ShortCost(),new MediumCost(),new LongCost()};
        for (Cost cost:costs) {
            if (cost.money(exdate)){
                System.out.println(cost.name+" "+(cost.price*exdate));
                break;
            }else if(exdate>=365){
                System.out.println("You rent over 1 year !"+"\n"+"Over one year = "+exdate*6);
                break;
            }
        }
        //day count
        System.out.println("Start From : "+sdf.format(todaycal.getTime()));
        todaycal.add(Calendar.DAY_OF_MONTH,+exdate);
        System.out.println("Expiry Date : "+sdf.format(todaycal.getTime()));
    }
    //api parse
    public static String parse(String responseBody){
        JSONObject albums=new JSONObject(responseBody);
        JSONArray arr=albums.getJSONArray("Search");
        for (int i = 0; i < arr.length(); i++) {
            String title= arr.getJSONObject(i).getString("Title");
            String year= arr.getJSONObject(i).getString("Year");
            String type= arr.getJSONObject(i).getString("Type");
            String poster= arr.getJSONObject(i).getString("Poster");
            System.out.println(type+" "+title+" "+year+" "+poster);
        }
        System.out.println("\n");
        return null;
    }

}
