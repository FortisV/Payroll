package org.example;

import java.text.DecimalFormat;

public class App {
  static DecimalFormat monetaryFormat = new DecimalFormat("0.00");
  static DecimalFormat hourFormat     = new DecimalFormat("0.0");

  static double overtimeMultiplier = 1.5;
  static double overtimeLimit      = 40;

  static double healthInsuranceDependentLimit = 3;
  static double healthInsuranceLessDependents = 15.00;
  static double healthInsuranceMoreDependents = 35.00;

  static double noLifeInsurancePlanDeduction          =  0.00;
  static double singleLifeInsurancePlanDeduction      =  5.00;
  static double marriedLifeInsurancePlanDeduction     = 10.00;
  static double marriedKidsLifeInsurancePlanDeduction = 15.00;

  static double socialSecurityTax = 0.06;
  static double federalIncomeTax  = 0.14;
  static double stateIncomeTax    = 0.05;

  static double localUnionDeduction = 10.00;

  public static void main(String[] args) {
    System.out.println("Welcome to Payroll Application\n");


    String hourPrompt = "How many hours did you work this week: ";
    String hourError  = "Please a valid number of hours";
    double hours = Input.getDoubleMin(0, hourPrompt, hourError);
    System.out.println();


    String ratePrompt = "What is your hourly rate: ";
    String rateError  = "Please a valid hourly rate";
    double rate = Input.getDoubleMin(0, ratePrompt, rateError);
    System.out.println();


    String childrenPrompt = "How many children do you have: ";
    String childrenError  = "Please enter a valid number of children";
    int children = Input.getInteger(childrenPrompt, childrenError);
    System.out.println();

    double healthInsuranceDeduction = 0;
    if(children < 0) {
      children = 0;
    }
    if(children < healthInsuranceDependentLimit) {
      healthInsuranceDeduction = healthInsuranceLessDependents;
    } else {
      healthInsuranceDeduction = healthInsuranceMoreDependents;
    }


    String lifeInsurancePrompt = "(1) no plan\n" +
                                 "(2) single plan\n" +
                                 "(3) married plan\n" +
                                 "(4) married with children plan\n" +
                                 "\n" +
                                 "Selection: ";
    String lifeInsuranceError           = "Please enter a valid selection";
    String lifeInsuranceDependentsError = "This plan is only available to employees with dependents";
    int lifeInsuranceSelection = 0;
    double lifeInsuranceDeduction = 0;
    boolean repeat = true;
    while(repeat) {
      lifeInsuranceSelection = Input.getInteger(1, 4, lifeInsurancePrompt, lifeInsuranceError);
      if(lifeInsuranceSelection != 4 || children != 0) {
        repeat = false;
      } else {
        System.out.println(lifeInsuranceDependentsError + '\n');
      }
    }
    System.out.println();

    if(lifeInsuranceSelection == 1) {
      lifeInsuranceDeduction = noLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 2) {
      lifeInsuranceDeduction = singleLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 3) {
      lifeInsuranceDeduction = marriedLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 4) {
      lifeInsuranceDeduction = marriedKidsLifeInsurancePlanDeduction;
    }

    double grossPay;
    if(hours <= overtimeLimit) {
      grossPay = hours * rate;
    } else {
      grossPay = rate * (overtimeLimit + (hours - overtimeLimit) * overtimeMultiplier);
    }

    double socialSecurityDeduction = grossPay * socialSecurityTax;
    double federalIncomeDeduction = grossPay * federalIncomeTax;
    double stateIncomeDeduction = grossPay * stateIncomeTax;

    double netTax = 0;
    netTax += socialSecurityDeduction;
    netTax += federalIncomeDeduction;
    netTax += stateIncomeDeduction;

    double netFee = 0;
    netFee += localUnionDeduction;
    netFee += healthInsuranceDeduction;
    netFee += lifeInsuranceDeduction;

    boolean dues = true;
    double net = grossPay - netTax;
    if(net >= netFee) {
      net -= netFee;
      dues = false;
    }

    System.out.println("Payroll Stub:");
    System.out.println();
    System.out.println("            Hours: " + hourFormat.format(hours));
    System.out.println("             Rate: " + monetaryFormat.format(rate) + " $/hr");
    System.out.println("            Gross: $ " + monetaryFormat.format(grossPay));
    System.out.println();
    System.out.println("  Social Security: $ " + monetaryFormat.format(socialSecurityDeduction));
    System.out.println("   Federal Income: $ " + monetaryFormat.format(federalIncomeDeduction));
    System.out.println("     State Income: $ " + monetaryFormat.format(stateIncomeDeduction));
    if(dues) {
    System.out.println();
    System.out.println("              Net: $ " + monetaryFormat.format(net));
    System.out.println();
    System.out.println("The employee still owes:");
    System.out.println();
    }
    System.out.println(" Local Union Fees: $ " + monetaryFormat.format(localUnionDeduction));
    System.out.println(" Health Insurance: $ " + monetaryFormat.format(healthInsuranceDeduction));
    if(lifeInsuranceDeduction != 0) {
    System.out.println("   Life Insurance: $ " + monetaryFormat.format(lifeInsuranceDeduction));
    }
    System.out.println();
    if(!dues) {
    System.out.println("              Net: $ " + monetaryFormat.format(net));
    }
    System.out.println();
    System.out.println();
    System.out.println("Thank you for using payroll program");
  }
}
