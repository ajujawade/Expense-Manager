package in.sp.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/profitLoss")
public class ProfitLossServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/expensemanager";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Latari1234!";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        double totalIncome = 0;
        double totalExpense = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {

                // Sum Income
                String incomeQuery = "SELECT SUM(amount) AS total_income FROM transactions WHERE type = 'Income'";
                try (PreparedStatement ps = con.prepareStatement(incomeQuery);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalIncome = rs.getDouble("total_income");
                }

                // Sum Expense
                String expenseQuery = "SELECT SUM(amount) AS total_expense FROM transactions WHERE type = 'Expense'";
                try (PreparedStatement ps = con.prepareStatement(expenseQuery);
                     ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) totalExpense = rs.getDouble("total_expense");
                }
            }

            double profitLoss = totalIncome - totalExpense;
            req.setAttribute("totalIncome", totalIncome);
            req.setAttribute("totalExpense", totalExpense);
            req.setAttribute("profitLoss", profitLoss);

            req.getRequestDispatcher("profitLoss.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to compute Profit & Loss");
        }
    }
}
