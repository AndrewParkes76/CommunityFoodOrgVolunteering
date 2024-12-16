/**
 * Class FoodBank
 * Author: Andrew Parkes
 */

import java.util.Arrays;

public class FoodBank extends CommunityFoodOrg {

    private double maxCapacity;
    private double[] dailyDonationsNeeded;

    public FoodBank(String id, String name, Location loc, TimeFrame[] dailyOH, double maxCap, double[] dailyDN) {
        super(id, name, loc, dailyOH);
        this.maxCapacity = maxCap > 0 ? maxCap : 500;
        this.dailyDonationsNeeded = validateDailyArray(dailyDN, 7, this.maxCapacity);
    }

    private double[] validateDailyArray(double[] array, int size, double maxCapacity) {
        if (array == null || array.length != size) {
            double[] defaultArray = new double[size];
            Arrays.fill(defaultArray, maxCapacity);
            return defaultArray;
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] < 0 || array[i] > maxCapacity) {
                array[i] = maxCapacity;
            }
        }
        return array;
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(double maxCapacity) {
        if (maxCapacity > 0) {
            this.maxCapacity = maxCapacity;
            Arrays.fill(this.dailyDonationsNeeded, maxCapacity);
        }
    }

    public double[] getDailyDonationsNeeded() {
        return dailyDonationsNeeded;
    }

    public void setDailyDonationsNeeded(double donations, String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex >= 0 && donations >= 0 && donations <= maxCapacity) {
            this.dailyDonationsNeeded[dayIndex] = donations;
        }
    }

    public double dailyDonationsNeeded(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex >= 0) {
            return this.dailyDonationsNeeded[dayIndex];
        }
        return -1; // Invalid day
    }

    @Override
    public boolean signUpVolunteer(Volunteer volunteer) {
        String dayName = volunteer.getDayAvailable();
        int dayIndex = getDayIndex(dayName);
        if (dayIndex < 0) return false;

        double donation = volunteer.getDistanceAvailable(); // Simulating donations as volunteer distance available
        System.out.println("Checking the necessary information for signing up " + volunteer.getFullName() +
                " for helping " + getName() + " on " + dayName);

        if (donation > dailyDonationsNeeded[dayIndex]) {
            System.out.println("Not enough space left for donations on " + dayName);
            return false;
        }

        dailyDonationsNeeded[dayIndex] -= donation;
        return true;
    }

    public void cancelVolunteerSignup(String dayName, double donation) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex >= 0) {
            dailyDonationsNeeded[dayIndex] += donation;
            if (dailyDonationsNeeded[dayIndex] > maxCapacity) {
                dailyDonationsNeeded[dayIndex] = maxCapacity;
            }
            System.out.println("Canceling volunteer signup for " + getName() + " on " + dayName);
        }
    }

    @Override
    public void cancelVolunteerSignup(String dayName) {
        System.out.println("Canceling volunteer signup for " + getName() + " on " + dayName);
    }
}
