package main;

public class DeliveryService {

    public enum Dimension {
        SMALL, BIG
    }

    public enum LoadLevel {
        LOW, MEDIUM, HIGH, VERY_HIGH
    }

    public static int calculateDeliveryCost(int distanceKm, Dimension dimension, boolean isFragile, LoadLevel loadLevel) {
        if (isFragile && distanceKm > 30) {
            throw new IllegalArgumentException("Хрупкий груз нельзя доставлять на расстояние более 30 км");
        }

        int cost = 0;

        // Расчёт по расстоянию
        if (distanceKm > 30) {
            cost += 300;
        } else if (distanceKm > 10) {
            cost += 200;
        } else if (distanceKm > 2) {
            cost += 100;
        } else {
            cost += 50;
        }

        // Расчёт по габаритам
        if (dimension == Dimension.BIG) {
            cost += 200;
        } else {
            cost += 100;
        }

        // Расчёт по хрупкости
        if (isFragile) {
            cost += 300;
        }

        // Расчёт по загруженности
        double loadCoefficient;
        switch (loadLevel) {
            case VERY_HIGH:
                loadCoefficient = 1.6;
                break;
            case HIGH:
                loadCoefficient = 1.4;
                break;
            case MEDIUM:
                loadCoefficient = 1.2;
                break;
            default:
                loadCoefficient = 1.0;
                break;
        }

        int finalCost = (int) Math.round(cost * loadCoefficient);

        // Проверка минимальной стоимости
        return Math.max(finalCost, 400);
    }
}
