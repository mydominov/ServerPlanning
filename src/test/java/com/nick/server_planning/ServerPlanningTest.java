package com.nick.server_planning;

import com.nick.server_planning.model.CountingResource;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class ServerPlanningTest {

    static Stream<Arguments> calculateProvider() {
        return Stream.of(
                // 1. Empty test.
                arguments(new CountingResource(), new ArrayList<CountingResource>(), 0),
                // 2. Simple test.
                arguments(
                        new CountingResource(2, 2, 2),
                        new ArrayList<CountingResource>() { {
                                add(new CountingResource(1, 1, 1));
                        } },
                        1),
                // 3. Another simple test.
                arguments(
                        new CountingResource(2, 2, 2),
                        new ArrayList<CountingResource>() { {
                            add(new CountingResource(1, 1, 1));
                            add(new CountingResource(1, 1, 1));
                            add(new CountingResource(1, 1, 1));
                        } },
                        2),
                // 4. Test for small components.
                arguments(
                        new CountingResource(2, 2, 2),
                        new ArrayList<CountingResource>() { {
                            add(new CountingResource(96, 1, 1));
                            add(new CountingResource(1, 96, 1));
                            add(new CountingResource(1, 1, 96));
                            add(new CountingResource(96, 96, 96));
                        } },
                        0),
                // 5. Test from the task.
                arguments(
                        new CountingResource(2, 32, 100),
                        new ArrayList<CountingResource>() { {
                            add(new CountingResource(1, 16, 10));
                            add(new CountingResource(1, 16, 10));
                            add(new CountingResource(2, 32, 100));
                        } },
                        2),
                // 6. Test for tiny VMs.
                arguments(
                        new CountingResource(2, 2, 11),
                        new ArrayList<CountingResource>() { {
                            add(new CountingResource(0, 0, 0));
                            add(new CountingResource(0, 0, 0));
                        } },
                        0),
                // 6. Test for the negative server's spec.
                arguments(
                        new CountingResource(2, -2, 11),
                        new ArrayList<CountingResource>() { {
                            add(new CountingResource(0, 0, 0));
                            add(new CountingResource(0, 0, 0));
                        } },
                        -1)
        );
    }

    @ParameterizedTest
    @MethodSource("calculateProvider")
    void calculateTest(
            final CountingResource serverType,
            final List<CountingResource> virtualMachines,
            final int expected) {
        int actual = ServerPlanning.calculate(serverType, virtualMachines);

        assertThat(actual, is(expected));
    }

    static Stream<Arguments> hasValidVirtualMachineSizeProvider() {
        return Stream.of(
                // 1. Empty test.
                arguments(new CountingResource(), new CountingResource(), false),
                // 2. Test for same VM and server.
                arguments(new CountingResource(1, 2, 3),
                        new CountingResource(1, 2, 3),
                        true),
                // 3. Test for a small VM
                arguments(new CountingResource(8, 128, 1024),
                        new CountingResource(2, 4, 50),
                        true),
                // 4. Test for a big VM
                arguments(new CountingResource(1, 4, 32),
                        new CountingResource(6, 16, 256),
                        false)
        );
    }


    @ParameterizedTest
    @MethodSource("hasValidVirtualMachineSizeProvider")
    void hasValidVirtualMachineSizeTest(
            final CountingResource serverType,
            final CountingResource virtualMachine,
            final boolean expected) {
        boolean actual = ServerPlanning.hasValidVirtualMachineSize(serverType, virtualMachine);

        assertThat(actual, is(expected));
    }

    static Stream<Arguments> serverHasSpaceProvider() {
        return Stream.of(
                // 1. Empty test.
                arguments(new CountingResource(), new CountingResource(), new CountingResource(), true),
                // 2. Test for same VM, server and server type.
                arguments(new CountingResource(1, 2, 3),
                        new CountingResource(1, 2, 3),
                        new CountingResource(1, 2, 3),
                        false),
                // 3. Test for a small VM, usual server and usual server type
                arguments(new CountingResource(3, 64, 56),
                        new CountingResource(8, 128, 1024),
                        new CountingResource(2, 4, 50),
                        true),
                // 4. Test for a small VM, loaded server and usual server type
                arguments(new CountingResource(7, 126, 1020),
                        new CountingResource(8, 128, 1024),
                        new CountingResource(2, 4, 50),
                        false),
                // 5. Test for a big VM, usual server and usual server type
                arguments(new CountingResource(3, 64, 56),
                        new CountingResource(8, 128, 1024),
                        new CountingResource(24, 256, 4096),
                        false)
        );
    }

    @ParameterizedTest
    @MethodSource("serverHasSpaceProvider")
    void serverHasSpaceTest(
            final CountingResource server,
            final CountingResource serverType,
            final CountingResource virtualMachine,
            final boolean expected) {
        boolean actual = ServerPlanning.serverHasSpace(server, serverType, virtualMachine);

        assertThat(actual, is(expected));
    }

    static Stream<Arguments> loadVirtualMachineIntoServerProvider() {
        return Stream.of(
                // 1. Empty test.
                arguments(new CountingResource(), new CountingResource(), new CountingResource()),
                // 2. Simple test.
                arguments(new CountingResource(1, 1, 1),
                        new CountingResource(3, 3, 3),
                        new CountingResource(4, 4, 4)),
                // 3. Another simple test.
                arguments(new CountingResource(4, 64, 128),
                        new CountingResource(2, 8, 64),
                        new CountingResource(6, 72, 192))
        );
    }

    @ParameterizedTest
    @MethodSource("loadVirtualMachineIntoServerProvider")
    void loadVirtualMachineIntoServer(
            CountingResource server,
            final CountingResource virtualMachine,
            final CountingResource expected) {
        ServerPlanning.loadVirtualMachineIntoServer(server, virtualMachine);

        assertThat(server, is(expected));
    }
}