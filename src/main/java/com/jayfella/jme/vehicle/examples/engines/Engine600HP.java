package com.jayfella.jme.vehicle.examples.engines;

import com.jayfella.jme.vehicle.Vehicle;
import com.jayfella.jme.vehicle.engine.Engine;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;

public class Engine600HP extends Engine {

    private final Vehicle vehicle;
    private final Spline powerGraph;

    public Engine600HP(Vehicle vehicle) {
        super("Basic 600", 6000, 9000, 20f);
        this.vehicle = vehicle;

        Vector3f[] points = new Vector3f[] {

                new Vector3f(0, 0.25f, 0),
                new Vector3f(1000, 0.35f, 0),
                new Vector3f(2000, 0.4f, 0),
                new Vector3f(3000, 0.5f, 0),
                new Vector3f(4000, 0.7f, 0),
                new Vector3f(5000, 0.95f, 0),
                new Vector3f(6000, 0.99f, 0),
                new Vector3f(7000, 0.85f, 0),
                new Vector3f(9000, 0.75f, 0),
        };

        powerGraph = new Spline(Spline.SplineType.Linear, points, 0.1f, false);
    }

    @Override
    public float getTorqueAtSpeed() {
        return getTorqueAtSpeed(vehicle);
    }

    public float evaluateSpline(float range) {
        return evaluateSpline(powerGraph, range);
    }

}
