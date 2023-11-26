import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ExpenseTrackerGUI {
    private JFrame frame;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextArea expenseTextArea;
    private ExpenseTracker expenseTracker;
    private JButton addButton;
    private JButton saveButton;

    public ExpenseTrackerGUI() {
        frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));
        inputPanel.setBackground(Color.BLACK);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setForeground(Color.WHITE);
        descriptionField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField();

        addButton = new JButton("Add Expense");
        amountLabel.setForeground(Color.WHITE);
        addButton.setBackground(new Color(135, 206, 250));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });

        saveButton = new JButton("Save");
        saveButton.setBackground(new Color(135, 206, 250));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveExpensesToFile();
            }
        });

        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionField);
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);
        inputPanel.add(addButton);
        inputPanel.add(saveButton);

        expenseTextArea = new JTextArea();
        expenseTextArea.setEditable(false);
        expenseTextArea.setBackground(Color.BLACK);
        expenseTextArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(expenseTextArea);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(saveButton);
        buttonPanel.setBackground(Color.BLACK);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        expenseTracker = new ExpenseTracker();
    }

    private void addExpense() {
        try {
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            expenseTracker.addExpense(description, amount);
            updateExpenseTextArea();
            clearInputFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid amount.", "Invalid Amount", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateExpenseTextArea() {
        expenseTextArea.setText(expenseTracker.getExpenseReport());
    }

    private void clearInputFields() {
        descriptionField.setText("");
        amountField.setText("");
    }

    private void saveExpensesToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int userChoice = fileChooser.showSaveDialog(frame);

        if (userChoice == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

                for (Expense expense : expenseTracker.getExpenses()) {
                    writer.write(expense.toString());
                    writer.newLine();
                }

                writer.close();
                JOptionPane.showMessageDialog(frame, "Expenses saved to file successfully.", "Save Successful", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error saving expenses to file.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ExpenseTrackerGUI expenseTrackerGUI = new ExpenseTrackerGUI();
                expenseTrackerGUI.show();
            }
        });
    }
}