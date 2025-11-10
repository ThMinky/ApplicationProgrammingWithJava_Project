package transport_company.util;

import transport_company.entities.Company;
import transport_company.entities.Employee;
import transport_company.entities.Transport;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompanyReportUtil {

    public static void printCompanyReport(Company company) {
        if (company == null) {
            System.out.println("Company is null");
            return;
        }

        Set<Transport> transports = company.getTransports();
        if (transports == null || transports.isEmpty()) {
            System.out.println("No transports found for company: " + company.getName());
            return;
        }

        double totalRevenue = 0;
        int paidCount = 0;

        Map<Long, DriverStats> driverStatsMap = new HashMap<>();

        for (Transport t : transports) {
            if (t == null || t.getDriver() == null) continue;

            if (!Boolean.TRUE.equals(t.getPaidStatus())) continue;

            Employee driver = t.getDriver();
            double price = t.getPrice();

            totalRevenue += price;
            paidCount++;

            driverStatsMap.compute(driver.getId(), (id, stats) -> {
                if (stats == null) stats = new DriverStats(driver.getName());
                stats.tripCount++;
                stats.totalEarned += price;
                return stats;
            });
        }

        System.out.println("//////////////////////////////////////////////////");
        System.out.println("Company ID: " + company.getId());
        System.out.println("Company Name: " + company.getName());
        System.out.println("Paid (Completed) Transports: " + paidCount);
        System.out.println("Total Revenue: " + totalRevenue);
        System.out.println("--------------------------------------------------");
        System.out.println("Driver Statistics:");
        System.out.println("--------------------------------------------------");

        driverStatsMap.forEach((id, stats) -> {
            System.out.printf("Driver ID: %d | Name: %s | Paid Trips: %d | Earned: %.2f%n",
                    id, stats.name, stats.tripCount, stats.totalEarned);
        });

        System.out.println("//////////////////////////////////////////////////");
    }

    private static class DriverStats {
        String name;
        int tripCount = 0;
        double totalEarned = 0;

        DriverStats(String name) {
            this.name = name;
        }
    }
}