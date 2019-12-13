package com.nick.server_planning;

import com.nick.server_planning.model.CountingResource;

import java.util.List;

/**
 * The class that allows to count how many servers are required for the VMs.
 *
 * @author Nick Dominov
 */
public class ServerPlanning {

    /**
     * Counts how many servers are required for the VMs.
     *
     * @param serverType specs of the server
     * @param virtualMachines list of the VMs
     * @return number of servers
     */
    public static int calculate(final CountingResource serverType, final List<CountingResource> virtualMachines) {
        if (serverType == null ||
                virtualMachines == null ||
                serverType.getCpu() < 0 ||
                serverType.getRam() < 0 ||
                serverType.getHdd() < 0) {
            return -1;
        }

        if (virtualMachines.size() == 0) {
            return 0;
        }

        boolean found = false;
        int result = 1;
        CountingResource currentServerLoad = new CountingResource();

        for (final CountingResource virtualMachine : virtualMachines) {
            // Checking that the VM has the size that is comparable to the server.
            if (hasValidVirtualMachineSize(serverType, virtualMachine)) {
                // Checking that the server still have some space left
                if (serverHasSpace(currentServerLoad, serverType, virtualMachine)) {
                    found = true;
                    loadVirtualMachineIntoServer(currentServerLoad, virtualMachine);
                } else {
                    ++result;
                    currentServerLoad = new CountingResource();
                }
            }
        }

        return (found) ? result : 0;
    }

    /**
     * Checks if the VM fits into the server.
     *
     * @param serverType spec of the server
     * @param virtualMachine VM
     * @return if the VM fits into the server or not
     */
    static boolean hasValidVirtualMachineSize(
            final CountingResource serverType,
            final CountingResource virtualMachine) {
        return (
                (virtualMachine.getCpu() > 0) &&
                (virtualMachine.getRam() > 0) &&
                (virtualMachine.getHdd() > 0) &&
                (serverType.getCpu() >= virtualMachine.getCpu()) &&
                (serverType.getRam() >= virtualMachine.getRam()) &&
                (serverType.getHdd() >= virtualMachine.getHdd())

        );
    }

    /**
     * Checks if there's some space left for the VM.
     *
     * @param server current load of the server
     * @param serverType spec of the server
     * @param virtualMachine VM
     * @return if there's some space left for the VM or not
     */
    static boolean serverHasSpace(
            final CountingResource server,
            final CountingResource serverType,
            final CountingResource virtualMachine) {
        return (
                (server.getCpu() + virtualMachine.getCpu() <= serverType.getCpu()) &&
                (server.getRam() + virtualMachine.getRam() <= serverType.getRam()) &&
                (server.getHdd() + virtualMachine.getHdd() <= serverType.getHdd())
               );
    }

    /**
     * Emulates the process of loading the VM into the server.
     *
     * @param server current server
     * @param virtualMachine VM
     */
    static void loadVirtualMachineIntoServer(
            final CountingResource server,
            final CountingResource virtualMachine) {
        server.incrementCpu(virtualMachine.getCpu());
        server.incrementRam(virtualMachine.getRam());
        server.incrementHdd(virtualMachine.getHdd());
    }
}
