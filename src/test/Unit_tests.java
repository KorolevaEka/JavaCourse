package test;

import main.DeliveryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryServiceTest {

    @Test
    @DisplayName("Тест на выброс исключения при хрупком грузе на расстоянии более 30 км")
    @Tag("negative")
    void testFragileGoodsOver30Km() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                DeliveryService.calculateDeliveryCost(31, DeliveryService.Dimension.SMALL, true, DeliveryService.LoadLevel.LOW)
        );
        assertEquals("Хрупкий груз нельзя доставлять на расстояние более 30 км", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "1, SMALL, false, LOW, 400",
            "5, SMALL, false, LOW, 400",
            "15, SMALL, false, LOW, 400",
            "35, SMALL, false, LOW, 400"
    })
    @DisplayName("Проверка минимальной стоимости доставки")
    @Tag("positive")
    void testMinimumDeliveryCost(int distance, DeliveryService.Dimension dimension, boolean isFragile, DeliveryService.LoadLevel loadLevel, int expected) {
        int actual = DeliveryService.calculateDeliveryCost(distance, dimension, isFragile, loadLevel);
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({
            "2, SMALL, false, LOW, 400",
            "2, BIG, false, LOW, 400",
            "10, BIG, true, MEDIUM, 720",
            "25, SMALL, true, HIGH, 840",
            "5, BIG, false, VERY_HIGH, 720",
            "12, SMALL, false, HIGH, 420",
            "31, BIG, false, VERY_HIGH, 800"
    })
    @DisplayName("Проверка расчёта стоимости доставки в различных сценариях")
    @Tag("positive")
    void testVariousScenarios(int distance, DeliveryService.Dimension dimension, boolean isFragile, DeliveryService.LoadLevel loadLevel, int expected) {
        int actual = DeliveryService.calculateDeliveryCost(distance, dimension, isFragile, loadLevel);
        assertEquals(expected, actual);
    }
}
