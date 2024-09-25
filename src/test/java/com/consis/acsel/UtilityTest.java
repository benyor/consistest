/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.consis.acsel;


import com.consis.acsel.util.Utility;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author Orange
 */
@SpringBootTest
public class UtilityTest {
    
    @Test
    void testValuePositive() {
      Double valor =1D;
      valor=Utility.convertToPositive(valor);
      Assertions.assertThat(valor).isGreaterThanOrEqualTo(0);
    }
    
    @Test
    void testValueNegative() {
      Double valor =1D;
      valor=Utility.convertToNegative(valor);
      Assertions.assertThat(valor).isLessThanOrEqualTo(0);
    }
}
