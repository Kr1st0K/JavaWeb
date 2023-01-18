package com.example.views;

import com.example.sorters.*;

import java.util.Scanner;

public class FacultySorterMenu {
    private static void getInterface() {
        System.out.println("1. By Name(a-z)");
        System.out.println("2. By Name(z-a)");
        System.out.println("3. By all places count (decrease)");
        System.out.println("4. By all places count (increase)");
        System.out.println("5. By budget places count (decrease)");
        System.out.println("6. By budget places count (increase)");
        System.out.println("7. Exit");
        System.out.print("Enter option: ");
    }

    public static FacultySorter getSorter() {
        Scanner scanner = new Scanner(System.in);
        String answer;
        do {
            FacultySorterMenu.getInterface();
            answer = scanner.nextLine();
            switch(answer){
                case "1":
                    return new StraightNameFacultySorter();
                case "2":
                    return new ReverseNameFacultySorter();
                case "3":
                    return new ReverseAllPlacesFacultySorter();
                case "4":
                    return new StraightAllPlacesFacultySorter();
                case "5":
                    return new ReverseBudgetPlacesFacultySorter();
                case "6":
                    return new StraightBudgetPlacesFacultySorter();
                case "7":
                    return null;
                default:
                    System.out.println("Enter correct option");
            }
        } while (true);
    }
}
