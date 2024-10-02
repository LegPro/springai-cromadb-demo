
# SpringAI ChromaDB RAG Demo

This project demonstrates a **Retrieval-Augmented Generation (RAG)** system using **Chroma DB** for storing and retrieving vectorized documents. The demo is built with **Spring Boot** and uses vector embeddings to facilitate similarity searches on text data stored in Chroma DB.

## Overview

In this example, the application reads data from a text file (`input.txt`), splits the content into smaller documents, vectorizes them, and stores the vectors in **Chroma DB**. It also provides an API to search for similar documents based on a query. The similarity search returns the most relevant documents using vectorized embeddings.

## Prerequisites

- **Java 21** installed on your system.
- **Maven** installed to build and run the project.
- **Chroma DB** properly configured for vectorized storage.
- **OpenAI API key** for vector generation.

## OpenAI API Key Setup

The application requires an **OpenAI API key** to interact with the OpenAI API for vectorization. You can set the API key as an environment variable or directly in the `application.properties` file.

### Option 1: Set the API Key as an Environment Variable

On **Linux/macOS**:

```bash
export SPRING_AI_OPENAI_API_KEY=your-openai-api-key
```

On **Windows**:

```bash
set SPRING_AI_OPENAI_API_KEY=your-openai-api-key
```

### Option 2: Add the API Key to `application.properties`

You can add the API key directly to the `src/main/resources/application.properties` file:

```properties
spring.ai.openai.api-key=your-openai-api-key
```

Replace `your-openai-api-key` with your actual OpenAI API key.

## Setup and Installation

### Step 1: Clone the Repository

```bash
git clone https://github.com/LegPro/springai-cromadb-demo.git
cd springai-cromadb-demo
```

### Step 2: Configure Database Connection

Make sure your Chroma DB is set up and accessible by your Spring Boot application. Any necessary configurations related to the database should be added to the `application.properties` file located in `src/main/resources`.

### Step 3: Add Input Data

Place your text data in the `src/main/resources/input.txt` file. This file will be processed to generate vectorized documents for storing in Chroma DB.

### Step 4: Build and Run the Application

Use Maven to build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

The application will start and run on `http://localhost:8080`.

## API Endpoints

### 1. `/load`

- **Method**: `GET`
- **Description**: Reads the text from `input.txt`, splits it into smaller documents, vectorizes the content, and stores it in Chroma DB.
- **Example**:
    ```bash
    curl "http://localhost:8080/load"
    ```

- **Response**: Returns the filename (`input.txt`) and logs the status of the documents being added to the vector store.

### 2. `/search`

- **Method**: `GET`
- **Description**: Searches for documents similar to the provided query based on vector embeddings stored in Chroma DB.
- **Query Parameter**:
    - `message`: The query string used to search for similar documents (default: `"Big Little Lies by Liane Moriarty"`).
- **Example**:
    ```bash
    curl "http://localhost:8080/search?message=Big%20Little%20Lies%20by%20Liane%20Moriarty"
    ```

- **Response**: Returns a comma-separated list of the most similar documents based on the vector search.

Example Response:
```
"Document 1 content, Document 2 content"
```

## How It Works

1. **Loading Data**: The `/load` endpoint processes the text from `input.txt`, splits it into smaller chunks using the `TokenTextSplitter`, and stores the vectorized documents in Chroma DB.
2. **Searching for Similar Documents**: The `/search` endpoint performs a similarity search on the vectorized data. The top K similar documents (default: 2) are retrieved based on their vector embeddings.

## Project Structure

```bash
springai-cromadb-demo/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── chromadbrag
│   │   │               └── controller
│   │   │                   └── AIController.java
│   └── resources
│       └── input.txt
│       └── application.properties
├── pom.xml
└── README.md
```

- **AIController.java**: Contains the REST API for loading and retrieving vectorized documents using Chroma DB.
- **input.txt**: The text file containing the data to be vectorized and stored in Chroma DB.
- **application.properties**: Configuration file for application settings, including database connection details and the OpenAI API key.

## How to Customize

- You can modify the **Top-K** parameter in the `/search` endpoint to retrieve more or fewer results by adjusting the `withTopK(2)` method call.
- The **`TokenTextSplitter`** used to split the documents can be customized to handle different tokenization strategies depending on your text data.
