public class WeatherApp {
    public static void main() {
        WeatherDataModel model = new WeatherDataModel();
        model.setCity("Sierre");
        model.setLanguage("en");
        WeatherDataView view = new WeatherDataView(model);
        view.setSize(458,802);
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        WeatherDataController controller = new WeatherDataController(model, view);
    }
}
