package com.nick.server_planning;

import com.nick.server_planning.model.CountingResource;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class of the project.
 *
 * @author Nikita Dominov
 */
public class Main {

    /**
     * Sample use of the @ServerPlanning class.
     *
     * @param args arguments for the console
     */
    public static void main(String[] args) {
        // Initialization
        CountingResource serverType = new CountingResource(2, 32, 100);
        List<CountingResource> virtualMachines = new ArrayList<CountingResource>() {{
           add(new CountingResource(1, 16, 10));
           add(new CountingResource(1, 16, 10));
           add(new CountingResource(2, 32, 100));
        }};

        // Printing the given data.
        System.out.println("Server specs:");
        serverType.print();
        System.out.println();

        System.out.println("Specs of the virtual machines:");
        virtualMachines.forEach(CountingResource::print);
        System.out.println();

        // Printing the result.
        System.out.print("The amount of servers that is required for the virtual machines: ");
        System.out.println(ServerPlanning.calculate(serverType, virtualMachines));
    }
}
