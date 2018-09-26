package com.fxpro;

import com.fxpro.service.WaterService;

import java.util.List;
import java.util.Scanner;

public class ReservoirApp{

    private static final String EXIT = "EXIT";

    public static void main( String[] args )
    {

        WaterService waterService = new WaterService();
        System.out.println( "Please enter landscape with delimiters: ',' or ' '" );
        System.out.println( "Or type EXIT to finish" );

        Scanner in = new Scanner(System.in);
        String line = in.nextLine();

        while(!line.toUpperCase().equals(EXIT)){
            try {
                List<String> wrongMessages = waterService.validateLandScapeInput(line);
                if(wrongMessages.size() > 0){
                    wrongMessages.forEach(System.out::println);
                }else{
                    long waterAmount = waterService.calculateWaterAmount(waterService.parseInputIntoLandscape(line));
                    System.out.println("Collected " + waterAmount + " squares of water.");
                }
            } catch (NumberFormatException e) {
                System.out.println("There is exception thrown: " + e.getMessage());
                e.printStackTrace();
            }

            line = in.nextLine();
        }

        System.exit(0);
    }
}
