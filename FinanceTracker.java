import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FinanceTracker extends JFrame {
    private DefaultTableModel tableModel;
    private JLabel lblBalance, lblIncome, lblExpense;
    private double totalBalance = 0, totalIncome = 0, totalExpense = 0;

    public FinanceTracker() {
        // 1. Frame Setup
        setTitle("Personal Finance Tracker");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 2. Summary Panel (North)
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Account Summary"));

        lblBalance = new JLabel("Balance: $0.00", SwingConstants.CENTER);
        lblIncome = new JLabel("Income: $0.00", SwingConstants.CENTER);
        lblExpense = new JLabel("Expenses: $0.00", SwingConstants.CENTER);

        // Style labels
        lblBalance.setFont(new Font("Arial", Font.BOLD, 16));
        lblIncome.setForeground(new Color(0, 128, 0)); // Green
        lblExpense.setForeground(Color.RED);

        summaryPanel.add(lblIncome);
        summaryPanel.add(lblExpense);
        summaryPanel.add(lblBalance);
        add(summaryPanel, BorderLayout.NORTH);

        // 3. Table Setup (Center)
        String[] columns = {"Type", "Description", "Amount"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // 4. Input Panel (South)
        JPanel inputPanel = new JPanel(new FlowLayout());

        JTextField txtDesc = new JTextField(10);
        JTextField txtAmount = new JTextField(7);
        JComboBox<String> comboType = new JComboBox<>(new String[]{"Income", "Expense"});
        JButton btnAdd = new JButton("Add Transaction");

        inputPanel.add(new JLabel("Desc:"));
        inputPanel.add(txtDesc);
        inputPanel.add(new JLabel("Amt:"));
        inputPanel.add(txtAmount);
        inputPanel.add(comboType);
        inputPanel.add(btnAdd);

        add(inputPanel, BorderLayout.SOUTH);

        // 5. Button Logic
        btnAdd.addActionListener((ActionEvent e) -> {
            try {
                String desc = txtDesc.getText();
                double amt = Double.parseDouble(txtAmount.getText());
                String type = (String) comboType.getSelectedItem();

                updateTotals(amt, type);
                tableModel.addRow(new Object[]{type, desc, String.format("$%.2f", amt)});

                // Clear inputs
                txtDesc.setText("");
                txtAmount.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number for Amount.");
            }
        });
    }

    private void updateTotals(double amt, String type) {
        if (type.equals("Income")) {
            totalIncome += amt;
            totalBalance += amt;
        } else {
            totalExpense += amt;
            totalBalance -= amt;
        }

        lblIncome.setText(String.format("Income: $%.2f", totalIncome));
        lblExpense.setText(String.format("Expenses: $%.2f", totalExpense));
        lblBalance.setText(String.format("Balance: $%.2f", totalBalance));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FinanceTracker().setVisible(true);
        });
    }
}