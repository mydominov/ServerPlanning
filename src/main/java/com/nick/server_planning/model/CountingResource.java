package com.nick.server_planning.model;

import java.util.Objects;

/**
 * Model of the counting source.
 *
 * @author Nikita Dominov
 */
public class CountingResource {

    private int cpu;
    private int ram;
    private int hdd;

    public CountingResource() {
    }

    public CountingResource(final int cpu, final int ram, final int hdd) {
        this.cpu = cpu;
        this.ram = ram;
        this.hdd = hdd;
    }

    public int getCpu() {
        return cpu;
    }

    public void incrementCpu(final int cpu) {
        this.cpu += cpu;
    }

    public int getRam() {
        return ram;
    }

    public void incrementRam(final int ram) {
        this.ram += ram;
    }

    public int getHdd() {
        return hdd;
    }

    public void incrementHdd(final int hdd) {
        this.hdd += hdd;
    }

    public void print() {
        System.out.println("CPU: " + cpu + " RAM: " + ram + " HDD: " + hdd);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountingResource that = (CountingResource) o;
        return cpu == that.cpu &&
                ram == that.ram &&
                hdd == that.hdd;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpu, ram, hdd);
    }
}
