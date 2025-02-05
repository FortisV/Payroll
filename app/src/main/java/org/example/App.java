package org.example;

import javax.xml.stream.events.StartDocument;
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

  public static int getLifeInsuranceSelection(int children, String prompt, String error, String errorDependent) {
    while(true) {
      int lifeInsuranceSelection = Input.getInteger(1, 4, prompt, error);
      if(lifeInsuranceSelection != 4 || children != 0) {
        return lifeInsuranceSelection;
      } else {
        System.out.println(errorDependent + '\n');
      }
    }
  }

  public static int getChildren(String prompt, String error) {
    int children = Input.getInteger(prompt, error);
    if(children < 0) {
      return 0;
    }
    return children;
  }

  public static double getGrossPay(double hours, double rate) {
    if(hours <= overtimeLimit) {
      return hours * rate;
    } else {
      return rate * (overtimeLimit + (hours - overtimeLimit) * overtimeMultiplier);
    }
  }

  public static double getHealthInsuranceDeduction(int children) {

    if(children < healthInsuranceDependentLimit) {
      return healthInsuranceLessDependents;
    } else {
      return healthInsuranceMoreDependents;
    }
  }

  public static double getLifeInsurancePlanDeduction(int lifeInsuranceSelection) {
    if(lifeInsuranceSelection == 1) {
      return noLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 2) {
      return singleLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 3) {
      return marriedLifeInsurancePlanDeduction;
    } else if(lifeInsuranceSelection == 4) {
      return marriedKidsLifeInsurancePlanDeduction;
    }
    return -1;
  }

  public static double getNetTax(double socialSecurityDeduction, double federalIncomeDeduction, double stateIncomeDeduction) {
    return socialSecurityDeduction + federalIncomeDeduction + stateIncomeDeduction;
  }

  public static double getNetFee(double localUnionDeduction, double healthInsuranceDeduction, double lifeInsuranceDeduction) {
    return localUnionDeduction + healthInsuranceDeduction + lifeInsuranceDeduction;
  }

  public static void main(String[] args) {
    System.out.println("Welcome to Payroll Application\n");


    String hourPrompt = "How many hours did you work this week: ";
    String hourError  = "Please enter a valid number of hours";
    double hours = Input.getDoubleMin(0, hourPrompt, hourError);
    System.out.println();

    String ratePrompt = "What is your hourly rate: ";
    String rateError  = "Please enter a valid hourly rate";
    double rate = Input.getDoubleMin(0, ratePrompt, rateError);
    System.out.println();
    double grossPay = getGrossPay(hours, rate);

    String childrenPrompt = "How many children do you have: ";
    String childrenError  = "Please enter a valid number of children";
    int children = getChildren(childrenPrompt, childrenError);
    System.out.println();
    double healthInsuranceDeduction = getHealthInsuranceDeduction(children);

    String lifeInsurancePrompt          = "(1) no plan\n(2) single plan\n(3) married plan\n(4) married with children plan\n\nSelection: ";
    String lifeInsuranceError           = "Please enter a valid selection";
    String lifeInsuranceDependentsError = "This plan is only available to employees with dependents";
    int lifeInsuranceSelection = getLifeInsuranceSelection(children, lifeInsurancePrompt, lifeInsuranceError, lifeInsuranceDependentsError);
    System.out.println();
    double lifeInsuranceDeduction = getLifeInsurancePlanDeduction(lifeInsuranceSelection);


    double socialSecurityDeduction = grossPay * socialSecurityTax;
    double federalIncomeDeduction = grossPay * federalIncomeTax;
    double stateIncomeDeduction = grossPay * stateIncomeTax;

    double netTax = getNetTax(socialSecurityDeduction, federalIncomeDeduction, stateIncomeDeduction);
    double netFee = getNetFee(localUnionDeduction, healthInsuranceDeduction, lifeInsuranceDeduction);

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
