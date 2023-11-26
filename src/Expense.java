import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

class Expense {
    private String description;
    private double amount;

    public Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String toString() {
        return description + "," + amount;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses;
    private double totalExpenses;

    public ExpenseTracker() {
        this.expenses = new ArrayList<>();
        this.totalExpenses = 0.0;
    }

    public void addExpense(String description, double amount) {
        Expense expense = new Expense(description, amount);
        expenses.add(expense);
        totalExpenses += amount;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public String getExpenseReport() {
        StringBuilder report = new StringBuilder("Expense List:\n");
        for (Expense expense : expenses) {
            report.append("Description: ").append(expense.getDescription()).append("\tAmount: $").append(expense.getAmount()).append("\n");
        }
        report.append("Total Expenses: $").append(totalExpenses);
        return report.toString();
    }
}