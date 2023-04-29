import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    @Test
    public void maxCategory() {
        MaxCategoryCalculator maxCategoryCalculator = new MaxCategoryCalculator();
        Map<String, Integer> map = new HashMap<>();
        map.put("еда", 500);
        map.put("одежда", 2000);
        map.put("быт", 300);
        MaxCategory maxCategory = maxCategoryCalculator.getMaxCategory(map);
        MaxCategory clothes = new MaxCategory();
        clothes.setCategory("одежда");
        clothes.setSum(2000);
        assertThat(clothes.getSum()).isEqualTo(maxCategory.getSum());
        assertThat(clothes.getCategory()).isEqualTo(maxCategory.getCategory());
    }

    @Test
    public void recalculateSums() {
        MaxCategoryCalculator maxCategoryCalculator = new MaxCategoryCalculator();
        Map<String, Integer> map = new HashMap<>();
        map.put("еда", 500);
        map.put("одежда", 2000);
        map.put("быт", 300);
        Map<String, String> categories = new HashMap<>();
        categories.put("булка", "еда");
        categories.put("шапка", "одежда");
        categories.put("мыло", "быт");

        maxCategoryCalculator.recalculateSums("булка", 1600, map, categories);

        assertThat(map.get("еда")).isEqualTo(2100);
        assertThat(map.get("одежда")).isEqualTo(2000);
        assertThat(map.get("быт")).isEqualTo(300);
        assertThat(map.size()).isEqualTo(3);

        maxCategoryCalculator.recalculateSums("брюки", 1600, map, categories);

        assertThat(map.get("еда")).isEqualTo(2100);
        assertThat(map.get("одежда")).isEqualTo(2000);
        assertThat(map.get("быт")).isEqualTo(300);
        assertThat(map.get("другое")).isEqualTo(1600);
        assertThat(map.size()).isEqualTo(4);
    }
}
