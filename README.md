# 🌤️ Weather API — Spring Boot

A RESTful Weather API built with Spring Boot that fetches real-time weather data and 5-day forecasts using the OpenWeatherMap API.

---

## 📦 Tech Stack

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Web** (RestTemplate)
- **Lombok**
- **OpenWeatherMap API** (free tier)

---

## 🚀 Features

- 🌍 Get current weather by city name
- 🌡️ Temperature, humidity, wind speed
- 🖼️ Weather icons via OpenWeatherMap
- 📅 5-day forecast (grouped by date)
- 📍 Weather by coordinates (lat/lon)
- ⚠️ Clean error responses for invalid cities or API issues

---

## 📁 Project Structure

```
src/main/java/com/weatherAPI/backend/
├── controller/
│   └── WeatherController.java      # REST endpoints
├── service/
│   └── WeatherService.java         # Business logic + API calls
├── model/
│   ├── WeatherResponse.java        # Current weather DTO
│   └── ForecastDay.java            # Forecast DTO
├── config/
│   └── AppConfig.java              # RestTemplate bean
└── WeatherApplication.java
```

---

## ⚙️ Setup & Installation

### 1. Clone the repository
```bash
git clone https://github.com/your-username/weather-api.git
cd weather-api
```

### 2. Get a free API key
Sign up at [openweathermap.org](https://openweathermap.org/api) and copy your API key.
> ⏳ Note: New keys take up to 2 hours to activate.

### 3. Configure `application.properties`
```properties
weather.api.key=YOUR_API_KEY_HERE
weather.api.url=https://api.openweathermap.org/data/2.5/weather
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```
App starts at `http://localhost:8080`

---

## 📡 API Endpoints

### Get Current Weather
```
GET /weather?city={cityName}
```
**Example:**
```bash
curl "http://localhost:8080/weather?city=Mumbai"
```
**Response:**
```json
{
  "city": "Mumbai",
  "temperature": 31.5,
  "description": "broken clouds",
  "humidity": 78,
  "windSpeed": 4.2,
  "iconUrl": "https://openweathermap.org/img/wn/04d@2x.png"
}
```

---

### Get 5-Day Forecast
```
GET /weather/forecast?city={cityName}
```
**Example:**
```bash
curl "http://localhost:8080/weather/forecast?city=Delhi"
```
**Response:**
```json
[
  {
    "date": "2026-05-21",
    "maxTemp": 43.1,
    "minTemp": 38.2,
    "description": "haze",
    "iconUrl": "https://openweathermap.org/img/wn/50d@2x.png"
  },
  {
    "date": "2026-05-22",
    "maxTemp": 41.5,
    "minTemp": 37.0,
    "description": "clear sky",
    "iconUrl": "https://openweathermap.org/img/wn/01d@2x.png"
  }
]
```

---

### Get Weather by Coordinates
```
GET /weather/coordinates?lat={latitude}&lon={longitude}
```
**Example:**
```bash
curl "http://localhost:8080/weather/coordinates?lat=26.12&lon=85.36"
```

---

## ⚠️ Error Handling

| HTTP Status | Reason |
|---|---|
| `404` | City not found |
| `401` | Invalid or inactive API key |
| `500` | Unexpected server error |

**Example error response:**
```json
{
  "error": "City not found: Muzaffarpur123"
}
```

---

## 🔑 Key Annotations Used

| Annotation | Purpose |
|---|---|
| `@RestController` | Marks class as REST API controller |
| `@Service` | Marks class as business logic layer |
| `@Configuration` | Defines Spring beans manually |
| `@Value` | Injects values from `application.properties` |
| `@GetMapping` | Maps HTTP GET requests to methods |
| `@RequestParam` | Reads query parameters from URL |
| `@ExceptionHandler` | Handles exceptions with clean responses |
| `@Data` / `@Builder` | Lombok: generates boilerplate code |

---

## 🛣️ Roadmap

- [x] Current weather by city
- [x] 5-day forecast
- [x] Wind speed
- [x] Weather icons
- [x] Coordinates endpoint
- [ ] Compare two cities
- [ ] Caching with `@Cacheable`
- [ ] Switch to WebClient (reactive)
- [ ] Frontend UI

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

---

## 🙋‍♂️ Author

Built while learning Spring Boot — contributions and feedback welcome!
