package org.firstinspires.ftc.teamcode.test;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class BiquadLowPass {
    private final double sampleRateHz;
    private final double target;
    // Coefficients
    public static final class BiquadCoeffs {
        public final double b0, b1, b2, a1, a2;

        public BiquadCoeffs(
                double b0, double b1, double b2,
                double a1, double a2
        ) {
            this.b0 = b0;
            this.b1 = b1;
            this.b2 = b2;
            this.a1 = a1;
            this.a2 = a2;
        }
    }

    // Filter state (past inputs and outputs)
    private double x1 = 0.0, x2 = 0.0, x3 = 0.0;
    private double y1 = 0.0, y2 = 0.0;


    double percStartCutoff = 0.9;
    double percEndCutoff = 0.95;
    BiquadCoeffs startingC;
    BiquadCoeffs nearTargetC;

    Telemetry telemetry;

    /**
     * Constructor for a Butterworth low-pass biquad
     * @param sampleRateHz Sample rate in Hz (e.g., 50 for 20ms timestep)
     * @param cutoffHz1   Cutoff frequency in Hz that we use for the ramp (should be higher e.g., 5 Hz)
     * @param cutoffHz2   Cutoff frequency in Hz that we use after ramp when the velocity has settled (should be lower e.g., 0.5 Hz)
     */
    public BiquadLowPass(LinearOpMode opModeParam, double sampleRateHz, double cutoffHz1, double cutoffHz2, double target) {
        this.sampleRateHz = sampleRateHz;
        this.target = target;
        telemetry = opModeParam.telemetry;
        startingC = calcCoeffs(cutoffHz1);
        nearTargetC = calcCoeffs(cutoffHz2);
    }

    public BiquadCoeffs calcCoeffs(double cutoffHz) {
        double omega = 2.0 * Math.PI * cutoffHz / this.sampleRateHz;
        double cos = Math.cos(omega);
        double sin = Math.sin(omega);
        double Q = 1.0 / Math.sqrt(2.0); // Butterworth
        double alpha = sin / (2.0 * Q);

        double a0 = 1.0 + alpha;

        double b0 = ((1 - cos) / 2.0) / a0;
        double b1 = (1 - cos) / a0;
        double b2 = ((1 - cos) / 2.0) / a0;
        double a1 = -2.0 * cos / a0;
        double a2 = (1.0 - alpha) / a0;

        return new BiquadCoeffs(b0, b1, b2, a1, a2);
    }

    /**
     * Process one sample of input and return the filtered output
     * @param x Current raw input sample (e.g., velocity)
     * @return Smoothed output sample
     */
    public double filter(double x) {
        double ratio = Math.abs(x / this.target);

        // Start blending at startCutoff, finish at EndCutoff: this is if using linear interpolation to smoothly transition from one set of coeffs to another (it doesn't work properly)
//        double t = (ratio - percStartCutoff) / (percEndCutoff - percStartCutoff);
//        t = Math.max(0.0, Math.min(1.0, t));

        // switch from cutoffHz1 coeffs to cutoffHz2 coeffs at when raw velocity reaches a certain percentage of the target
        double t;
        if (ratio > percEndCutoff) {
            t = 1;
        } else {
            t = 0;
        }

        // Compute coeffs
        double b0 = lerp(startingC.b0, nearTargetC.b0, t);
        double b1 = lerp(startingC.b1, nearTargetC.b1, t);
        double b2 = lerp(startingC.b2, nearTargetC.b2, t);
        double a1 = lerp(startingC.a1, nearTargetC.a1, t);
        double a2 = lerp(startingC.a2, nearTargetC.a2, t);

        // debugging telemetry
//        telemetry.addData("start b0", startingC.b0);
//        telemetry.addData("current b0", b0);
//        telemetry.addData("end b0", nearTargetC.b0);
//        telemetry.addData("ratio", ratio);
//        telemetry.update();

        // Compute current output
        double y = b0 * x + b1 * x1 + b2 * x2 - a1 * y1 - a2 * y2;

        // Shift input history
        x2 = x1;
        x1 = x;

        // Shift output history
        y2 = y1;
        y1 = y;


        return y;
    }

    public void reset() {
        x1 = 0.0;
        x2 = 0.0;
        x3 = 0.0;
        y1 = 0.0;
        y2 = 0.0;
    }

    private static double lerp(double a, double b, double t) { // used to be linear interpolation but that caused weird spikes so it's now a step function
        if (t >= 1) {
            return b;
        } else {
            return a;
        }
//        return a + t * (b - a);
    }

}
