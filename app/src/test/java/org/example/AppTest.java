package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
  @Test
  void grossPayTest() {
    assertEquals(192, App.getGrossPay(12, 16));
  }

  @Test
  void grossPayOTTest() {
    assertEquals(880, App.getGrossPay(50, 16));
  }

  @Test
  void healthInsuranceTestNegativeDependents() {
    assertEquals(15, App.getHealthInsuranceDeduction(-1));
  }

  @Test
  void healthInsuranceTestNoDependents() {
    assertEquals(15, App.getHealthInsuranceDeduction(0));
  }

  @Test
  void healthInsuranceTest4Dependents() {
    assertEquals(35, App.getHealthInsuranceDeduction(4));
  }

  @Test
  void lifeInsuranceTest1() {
    assertEquals(0, App.getLifeInsurancePlanDeduction(1));
  }

  @Test
  void lifeInsuranceTest2() {
    assertEquals(5, App.getLifeInsurancePlanDeduction(2));
  }

  @Test
  void lifeInsuranceTest3() {
    assertEquals(10, App.getLifeInsurancePlanDeduction(3));
  }

  @Test
  void lifeInsuranceTest4() {
    assertEquals(15, App.getLifeInsurancePlanDeduction(4));
  }

  @Test
  void netTaxTest() {
    assertEquals(15, App.getNetTax(5, 5, 5));
  }

  @Test
  void netFeeTest() {
    assertEquals(15, App.getNetFee(5, 5, 5));
  }
}
