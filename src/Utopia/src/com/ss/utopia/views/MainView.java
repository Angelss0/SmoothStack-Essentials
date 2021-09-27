package com.ss.utopia.views;

import java.util.Arrays;
import java.util.Scanner;

import com.ss.utopia.util.AnsiFormat;
import com.ss.utopia.util.AnsiFormat.Colors;

public class MainView extends View {

    public MainView(Scanner scanner, View previousView) {
        super(scanner, previousView);
    }

    View agentView = new AgentView(scanner, this);
    View travelerView = new TravelerView(scanner, this);
    View adminView = new AdminView(scanner, this);

    @Override
    public IOption entryPoint() {
        return optionsMenu(
            "Welcome to the Utopia Airlines Management System. Which category of user are you?"
            , Arrays.asList(
                AnsiFormat.setColor("Employee/Agent (WIP)", Colors.BrightBlack),
                "Administrator",
                AnsiFormat.setColor("Traveler (WIP)", Colors.BrightBlack),
                QUIT_STR
            ), Arrays.asList(
                () -> agentView.entryPoint(),
                () -> adminView.entryPoint(),
                () -> travelerView.entryPoint(),
                null
            )
        );
    }
}
