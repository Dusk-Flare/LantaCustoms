package lanta.math;

public class LinearEquations {
    public static double[] gaussSeidel(double[][] matrix, double[] results, double[] initialValues, double precision) {
        if (matrix.length != matrix[0].length) throw new IllegalArgumentException("Matrix must be square");
        if (matrix.length != initialValues.length) throw new IllegalArgumentException("Initial values size must match matrix size");
        if (matrix.length != results.length) throw new IllegalArgumentException("Results size must match matrix size");
        int size = matrix.length;
        double[] values = initialValues.clone();
        while (true) {
            double[] nextValues = updateValues(matrix, results, values, size);
            boolean isFinished = true;
            for (int i = 0; i < size; i++) {
                if (Math.abs(values[i] - nextValues[i]) > precision) {
                    isFinished = false;
                    break;
                }
            } if (isFinished) return nextValues;
            values = nextValues;
        }
    }

    private static double[] updateValues(double[][] matrix, double[] results, double[] values, int size) {
        double[] newValues = values.clone();
        for (int i = 0; i < size; i++) {
            double sum = results[i];
            for (int j = 0; j < size; j++) {
                if (i != j) sum -= matrix[i][j] * newValues[j];
            }
            newValues[i] = sum / matrix[i][i];
        }
        return newValues;
    }
}