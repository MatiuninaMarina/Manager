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
}
