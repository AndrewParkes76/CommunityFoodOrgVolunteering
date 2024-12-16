/**
 * Class FoodPantry
 * Author: Andrew Parkes
 */

import java.util.Arrays;

public class FoodPantry extends CommunityFoodOrg {

    private int[] dailyVolunteersNeeded;
    private int[] dailyVolunteerSignups;

    public FoodPantry(String id, String name, Location loc, TimeFrame[] dailyOH, int[] dailyVN, int[] dailyVS, boolean offersT) {
        super(id, name, loc, dailyOH);
        this.dailyVolunteersNeeded = validateDailyArray(dailyVN, 7);
        this.dailyVolunteerSignups = validateDailyArray(dailyVS, 7);
    }

    private int[] validateDailyArray(int[] array, int size) {
        if (array == null || array.length != size) {
            int[] defaultArray = new int[size];
            Arrays.fill(defaultArray, 0);
            return defaultArray;
        }
        for (int value : array) {
            if (value < 0) {
                Arrays.fill(array, 0);
                return array;
            }
        }
        return array;
    }

    public int[] getDailyVolunteersNeeded() {
        return dailyVolunteersNeeded;
    }

    public int[] getDailyVolunteerSignups() {
        return dailyVolunteerSignups;
    }

    public void setDailyVolunteersNeeded(int volunteers, String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0 && volunteers >= 0) this.dailyVolunteersNeeded[index] = volunteers;
    }

    public void setDailyVolunteersNeeded(int[] volunteers) {
        this.dailyVolunteersNeeded = validateDailyArray(volunteers, 7);
    }

    public void setDailyVolunteerSignups(int signups, String dayName) {
        int index = getDayIndex(dayName);
        if (index >= 0 && signups >= 0) this.dailyVolunteerSignups[index] = signups;
    }

    public void setDailyVolunteerSignups(int[] signups) {
        this.dailyVolunteerSignups = validateDailyArray(signups, 7);
    }

    @Override
    public boolean signUpVolunteer(Volunteer volunteer) {
        String dayName = volunteer.getDayAvailable();
        int dayIndex = getDayIndex(dayName);
        if (dayIndex < 0) return false;

        System.out.println("Checking the necessary information for signing up " + volunteer.getFullName() +
                " for helping " + getName() + " on " + dayName);

        if (dailyVolunteerSignups[dayIndex] < dailyVolunteersNeeded[dayIndex]) {
            dailyVolunteerSignups[dayIndex]++;
            return true;
        }
        return false;
    }

    @Override
    public void cancelVolunteerSignup(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex >= 0 && dailyVolunteerSignups[dayIndex] > 0) {
            dailyVolunteerSignups[dayIndex]--;
            System.out.println("Canceling volunteer signup for " + getName() + " on " + dayName);
        }
    }

    public int dailyVolunteerSpotsLeft(String dayName) {
        int dayIndex = getDayIndex(dayName);
        if (dayIndex < 0) return -1;
        return dailyVolunteersNeeded[dayIndex] - dailyVolunteerSignups[dayIndex];
    }
}