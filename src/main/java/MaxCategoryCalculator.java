import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MaxCategoryCalculator {

    public MaxCategory getMaxCategory (Map <String, Integer> categoriesToSums) {
        Map.Entry<String, Integer> entry = categoriesToSums.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get();
        MaxCategory maxCategory = new MaxCategory();
        maxCategory.setCategory(entry.getKey());
        maxCategory.setSum(entry.getValue());
        return maxCategory;
    }
    public void recalculateSums (String title, int sum, Map <String, Integer> categoriesToSums, Map <String, String> itemsToCategories) {
        String category = itemsToCategories.get(title);
        if (category == null) {
            if (categoriesToSums.containsKey("другое")) {
                Integer currentSum = categoriesToSums.get("другое");
                currentSum += sum;
                categoriesToSums.put("другое", currentSum);
            } else {
                categoriesToSums.put("другое", sum);
            }
        } else {
            Integer currentSum = categoriesToSums.get(category);
            currentSum +=sum;
            categoriesToSums.put(category,currentSum);

        }
    }
}
