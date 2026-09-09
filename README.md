# AI Listing Assistant

This is a small web app that helps a seller write a better listing. It uses **Gemini** for text generation, **React** for the UI, and **Kotlin** for the backend.

The AI agent takes a brief description provided by the user and sends back:
- A short title.
- 3-5 search tags.
- A suggested price range.

---

## Tech Stack

|Technology | Purpose |
|---|---|
| React | UI framework |
| Kotlin | Business logic & HTTP Request |
| Gemini | AI agent for text generation |

---

## Architecture

The backend follows a simple **layered architecture**, (Controller → Service)with DTOs used to define the request and response data structures. React receives the response from the backend and displays it to the user.

For this project, React uses **useState** to manage the component state and re-render the UI based on the response received from the backend.

---

## Project Structure

```
ai-listing-assistant/
├── backend/
│   └── src
│       ├── main/
│       │   └── kotlin/
│       │       └── com/
│       │           └── bricfern/
│       │               ├── config/         # CORS, Gemini SDK setup
│       │               ├── controller/     # HTTP request handling
│       │               ├── service/        # Business logic
│       │               ├── dto/            # Request/response DTOs
│       │               └── exceptions/     # Custom exceptions
│       └── resources/
│       │   └── mocks/
└── src/
    ├── services/          # API client layer (HTTP calls to the backend)
    └── types/             # Custom types to match DTOs from backend
```

---

## Getting Started

### Prerequisites
- JDK 21+
- Kotlin 2.4.20
- Node.js and npm
- A Gemini API key

### Frontend setup
```bash
# Install dependencies
npm install

# Configure your environment variables
cp .env.example .env

# Run the development server
npm run dev
```

### Backend setup
```bash
# Set your Gemini API key as an environment variable:
GEMINI_API_KEY=your_api_key

# Run the API
.\gradlew run # Windows
./gradlew run # Linux/macOS
```

---

## Mock Mode

By default, the backend runs in **mock mode** — it returns saved example
responses instead of calling Gemini. This lets you clone the repo and see
the app working immediately, with no API key required.

### Switching to the real AI

Set the following environment variable to `false` before running the backend:

```bash
$env:MOCK_MODE="false"    # Windows PowerShell
export MOCK_MODE=false    # Linux/macOS
```

A valid API key is required when mock mode is disabled (see Prerequisites).

### Simulating different AI behaviors

While in mock mode, you can control which kind of example response is
returned with the `MOCK_RESPONSE` variable:

| Value | Behavior |
|---|---|
| `success` *(default)* | Returns a well-formed example response |
| `invalid` | Returns a structurally valid response with unrealistic/edge-case data (empty tags, empty title, inverted price range) |
| `malformed` | Returns a response with an invalid data type (triggers the app's error handling) |

```bash
$env:MOCK_RESPONSE="invalid"     # Windows PowerShell
export MOCK_RESPONSE=invalid     # Linux/macOS
```

Example responses are stored in `backend/src/main/resources/mock/`.

---

## Author

**Briceida Fernandez**
[LinkedIn](https://www.linkedin.com/in/briceidafernandez) · [GitHub](https://github.com/BricFer)
