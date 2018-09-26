package com.fxpro.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for water collecting calculation and validation
 * Author: Oleksandr Malynskyi
 * Created: 9/24/2018
 */
public class WaterService {

    private static final String NOT_NUMBER_INPUT = "^$|[^\\d]+";
    private static final String DELIMS = ",|\\s+";

    static final int MAX_VALUE = 32000;

    static final String EMPTY = "<empty>";

    static final String INCORRECT_INPUT_MSG = "You haven't used numbers or proper delimiter for input: %s";
    static final String INCORRECT_INPUT_RANGE = "Height of position need to be between 0 and " + MAX_VALUE + " , what's failed for: %s";
    static final String INCORRECT_INPUT_SIZE = "Max number of positions is " + MAX_VALUE;

    /**
     * Validate input string:
     * - to have a set of integers with ',' and 'space' delimiters
     * - doesn't have other then numbers in elements
     * - every number should not exceed max value = 32000
     * - numbers amount should not exceed max value = 32000
     * @param line user input
     * @return list of error descriptions for all failed validations
     */
    public List<String> validateLandScapeInput(String line){
        String[] input = line.split(DELIMS);

        List<String> result = new ArrayList<>();
        String wrongInput = input.length == 0 ? EMPTY
                : Arrays.stream(input)
                    .filter(numb -> numb.matches(NOT_NUMBER_INPUT)).map(str -> str.length() == 0 ? EMPTY : str)
                    .collect(Collectors.joining(","));
        if((wrongInput != null && wrongInput.length() > 0) || input.length == 0)
            result.add(String.format(INCORRECT_INPUT_MSG, wrongInput));

        wrongInput = Arrays.stream(input)
                        .filter(numb -> numb.matches("\\d+")
                                && (numb.length() > String.valueOf(MAX_VALUE).length() ||
                                Integer.valueOf(numb) < 0 || Integer.valueOf(numb) > MAX_VALUE))
                        .collect(Collectors.joining(","));
        if(wrongInput != null && wrongInput.length() > 0)
            result.add(String.format(INCORRECT_INPUT_RANGE, wrongInput));

        if (input.length > MAX_VALUE)
            result.add(INCORRECT_INPUT_SIZE);

        return result;
    }

    /**
     * Parse input string to array of numbers
     * Expecting that validation of input is done outside of this method
     * @param input string input
     * @return  array of numbers
     * @throws NumberFormatException if inout has not integer numbers
     */
    public int[] parseInputIntoLandscape(String input) throws NumberFormatException{
        String[] numbers = input.split(DELIMS);
        return Arrays.stream(numbers).mapToInt(Integer::valueOf).toArray();
    }

    /**
     * Calculate water in a pits
     * @param landscape array of heights for landscape
     * @return collected amount of water
     */
    public long calculateWaterAmount(int[] landscape){
      		int water = 0;

      		int leftSide = 0, rightSide = landscape.length-1;
            int leftPeak = 0, rightPeak = 0;
      		while(rightSide >= leftSide){

      		    if(landscape[leftSide] > landscape[rightSide]){
      		        //check for moving up or down from the right side
                    if(landscape[rightSide] > rightPeak)
                        //moving up and find the highest peak for this hill
                        rightPeak = landscape[rightSide];
                	else
                	    //moving down - it's time to collect water
                        water += rightPeak - landscape[rightSide];
                	rightSide--;
                }else{
                    //check for moving up or down from the right side
                    if(landscape[leftSide] > leftPeak)
                        //moving up and find the highest peak for this hill
                    	leftPeak = landscape[leftSide];
                	else
                        //moving down - it's time to collect water
                    	water += leftPeak - landscape[leftSide];
                	leftSide++;
                }
      		}
      		return water;
    }
}
