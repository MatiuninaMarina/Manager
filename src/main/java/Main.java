import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static Map<String, Integer> categoriesToSums = new HashMap<>();
    private static Map<String, String> itemsToCategories = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ClassLoader classLoader = Main.class.getClassLoader();
        File file = new File(classLoader.getResource("categories.tsv").getFile());
        loadCategoriesFromFile(file);
        MaxCategoryCalculator maxCategoryCalculator = new MaxCategoryCalculator();
        ObjectMapper objectMapper = new ObjectMapper();
        try (ServerSocket serverSocket = new ServerSocket(8989)) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                ) {
                    String line;
                    String requestString = "";
                    Request request = null;
                    while ((line = in.readLine()) != null) {
                        requestString += line;
                        try {
                            request = objectMapper.readValue(requestString, Request.class);
                            break;
                        } catch (Exception e) {
                        }
                    }
                    maxCategoryCalculator.recalculateSums(request.getTitle(), request.getSum(), categoriesToSums, itemsToCategories);
                    MaxCategory maxCategory = maxCategoryCalculator.getMaxCategory(categoriesToSums);
                    Response response = new Response();
                    response.setMaxCategory(maxCategory);
                    String responseAsString = objectMapper.writeValueAsString(response);
                    out.println(responseAsString);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    public static void loadCategoriesFromFile(File textFile) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {

            String itemToCategoryString;
            while ((itemToCategoryString = bufferedReader.readLine()) != null) {
                String[] itemToCategory = itemToCategoryString.split("\t");
                if (!categoriesToSums.containsKey(itemToCategory[1])) {
                    categoriesToSums.put(itemToCategory[1], 0);
                }
                itemsToCategories.put(itemToCategory[0], itemToCategory[1]);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            categoriesToSums.put("другое", 0);
        }
    }
}
