package com.fxpro.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static com.fxpro.service.WaterService.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Testing WaterService
 * Author: Oleksandr Malynskyi
 * Created: 9/24/2018
 */
public class WaterServiceTest {

    private static WaterService waterService;

    @BeforeClass
    public static void setUp() throws Exception {
        waterService = new WaterService();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        waterService = null;
    }

    @Test
    public void parseInputIntoLandscape() throws Exception {
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(
                () -> waterService.parseInputIntoLandscape("1,2,FFFDR")).withMessageContaining("FFFDR");
        assertThatExceptionOfType(NumberFormatException.class).isThrownBy(
                () -> waterService.parseInputIntoLandscape("1,2,10000000000000000000000000000000000"))
                .withMessageContaining("10000000000000000000000000000000000");

        assertThat(waterService.parseInputIntoLandscape("1,2, ,")).containsExactly(1,2);

        assertThat(waterService.parseInputIntoLandscape("1,2,3,4")).containsExactly(1,2,3,4);
    }

    @Test
    public void validateLandScapeInput() throws Exception {

        Random random = new Random();
        StringBuilder wideInput = new StringBuilder(String.valueOf(random.nextInt(MAX_VALUE)));
        for(int i=1; i<=MAX_VALUE+1; i++){
            wideInput.append(",").append(random.nextInt(MAX_VALUE));
        }

        assertThat(waterService.validateLandScapeInput(",1"))
                .containsExactly(String.format(INCORRECT_INPUT_MSG, EMPTY));
        assertThat(waterService.validateLandScapeInput(","))
                .containsExactly(String.format(INCORRECT_INPUT_MSG, EMPTY));
        assertThat(waterService.validateLandScapeInput(""))
                .containsExactly(String.format(INCORRECT_INPUT_MSG, EMPTY));
        assertThat(waterService.validateLandScapeInput("2,1,de,34"))
                .containsExactly(String.format(INCORRECT_INPUT_MSG, "de"));
        assertThat(waterService.validateLandScapeInput("34,1,@#.,"))
                .containsExactly(String.format(INCORRECT_INPUT_MSG, "@#."));

        assertThat(waterService.validateLandScapeInput("1,2,1000000000000000"))
                .containsExactly(String.format(INCORRECT_INPUT_RANGE, "1000000000000000"));
        assertThat(waterService.validateLandScapeInput("2,1,-5,6"))
                .containsExactly(String.format(INCORRECT_INPUT_RANGE, "-5"));

        assertThat(waterService.validateLandScapeInput(wideInput.toString()))
                .containsExactly(INCORRECT_INPUT_SIZE);

        wideInput.append(",string").append(",9999999999999");
        assertThat(waterService.validateLandScapeInput(wideInput.toString()))
                .containsExactlyInAnyOrder(
                        INCORRECT_INPUT_SIZE,
                        String.format(INCORRECT_INPUT_MSG, "string"),
                        String.format(INCORRECT_INPUT_RANGE, "9999999999999")
                        );
    }

    @Test
    public void calculateWaterAmount() throws Exception {
        assertThat(waterService.calculateWaterAmount(new int[] {})).isEqualTo(0);
        assertThat(waterService.calculateWaterAmount(new int[] {0,0,0,0})).isEqualTo(0);
        assertThat(waterService.calculateWaterAmount(new int[] {5,6,7,4,2})).isEqualTo(0);
        assertThat(waterService.calculateWaterAmount(new int[] {5,5,5,5})).isEqualTo(0);
        assertThat(waterService.calculateWaterAmount(new int[] {7, 4, 5, 3})).isEqualTo(1);
        assertThat(waterService.calculateWaterAmount(new int[] {2,0,5,7,7,7,1,1,4,4,9})).isEqualTo(20);
        assertThat(waterService.calculateWaterAmount(new int[] {6,4,2,1,3,4,5,2,5,0,6})).isEqualTo(28);
        assertThat(waterService.calculateWaterAmount(new int[] {0,6,1,6,2,2,2,6,3})).isEqualTo(17);
        assertThat(waterService.calculateWaterAmount(new int[] {6,0,4,0,7,0,8,0,0,6})).isEqualTo(33);
        assertThat(waterService.calculateWaterAmount(new int[] {5,5,2,3,1})).isEqualTo(1);
        assertThat(waterService.calculateWaterAmount(new int[] {8,4,2,1,1,4,5,2,5,0,5})).isEqualTo(21);
        assertThat(waterService.calculateWaterAmount(new int[] {3,3,1,3,1,4})).isEqualTo(4);
    }

}