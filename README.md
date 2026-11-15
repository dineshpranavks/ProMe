# ProMe – Personal Knowledge Intelligence System 🧠

**ProMe** is an AI-powered personal knowledge management system designed to help individuals capture, organize, connect, and retrieve their knowledge from a centralized workspace.

The goal is to go beyond traditional note-taking applications by combining knowledge management, semantic search, and AI-powered information retrieval into a single platform.

> 🚧 **Project Status: Under Development**
>
> ProMe is currently being developed. Features, architecture, and integrations are being implemented incrementally.

---

## 🎯 Project Vision

People collect information from multiple sources, including notes, documents, articles, research materials, and learning resources. Over time, this information becomes scattered and difficult to retrieve when needed.

ProMe aims to address this problem by creating a centralized knowledge workspace where users can:

- Capture and organize personal knowledge.
- Store notes and documents in one place.
- Retrieve information using semantic search rather than relying only on keywords.
- Interact with their personal knowledge through AI-powered conversations.
- Discover meaningful connections between stored information.

The long-term vision is to transform scattered information into an intelligent, searchable personal knowledge system.

## ✨ Planned Features

The following features represent the intended product scope. They are not all implemented yet.

| Feature | Description | Status |
|---|---|---|
| Knowledge Management | Create, edit, organize, and manage personal notes | Planned / In Progress |
| Document Management | Upload and manage knowledge documents | Planned |
| AI Summarization | Generate concise summaries of stored content | Planned |
| Semantic Search | Find relevant information using vector similarity | Planned |
| AI Knowledge Chat | Ask questions based on personal knowledge | Planned |
| RAG Pipeline | Retrieve relevant knowledge before generating AI responses | Planned |
| Automatic Tagging | Generate relevant tags and categories | Planned |
| Local AI Support | Connect locally hosted AI models | Planned |
| External AI Integration | Integrate external AI providers such as NVIDIA NIM | Planned |

## 🏗️ Architecture

ProMe follows a **Modular Monolith Architecture** using Spring Boot.

The application is designed to maintain clear boundaries between business modules while keeping deployment and infrastructure relatively simple.

### High-Level Architecture

```text
                  React Frontend
                        |
                        |
                 REST API Layer
                        |
                  Spring Boot
                        |
        +---------------+----------------+
        |               |                |
   Knowledge         Document           AI
    Module            Module           Module
        |               |                |
        +---------------+----------------+
                        |
                Infrastructure Layer
                        |
          +-------------+--------------+
          |             |              |
      PostgreSQL     pgvector       AI Providers
                                    /          \
                              NVIDIA NIM    Local Models
```

### Architectural Principles

- Modular and maintainable code organization.
- Separation of business logic and infrastructure.
- Provider-independent AI integration.
- Clear module boundaries.
- Scalable knowledge retrieval architecture.

## 🛠️ Technology Stack

The following technologies are selected for the project.

### Backend

- Java
- Spring Boot
- Spring Data JPA
- Spring AI
- Maven
- REST APIs

### Frontend

- React.js
- JavaScript
- HTML
- CSS

### Database and Vector Search

- PostgreSQL
- pgvector

### AI Integration

- NVIDIA NIM – External AI inference provider
- Ollama – Local model integration
- Embedding Models – Semantic representation of knowledge
- Retrieval-Augmented Generation (RAG)

### Development Tools

- IntelliJ IDEA
- Visual Studio Code
- Git
- GitHub
- Postman

## 🤖 AI Integration Strategy

ProMe is designed to support both external AI services and locally hosted models.

An AI provider abstraction layer will help separate application business logic from individual AI providers.

Planned AI capabilities include:

- Text generation and knowledge-based conversations.
- Document summarization.
- Embedding generation.
- Semantic retrieval.
- Context-aware question answering.

### Planned AI Architecture

```text
             AI Service Layer
                    |
              Model Router
                    |
             AI Provider Layer
                /       \
               /         \
        External AI     Local AI
           APIs          Models
             |              |
        NVIDIA NIM       Ollama
```

This architecture is intended to make it possible to switch AI providers without changing the core knowledge management logic.

## 🔍 Semantic Search and RAG

A key objective of ProMe is to enable semantic retrieval of personal knowledge.

The planned processing pipeline is:

```text
       User Notes / Documents
                 |
                 v
          Text Extraction
                 |
                 v
           Text Chunking
                 |
                 v
         Embedding Generation
                 |
                 v
          PostgreSQL + pgvector
                 |
                 v
           Similarity Search
                 |
                 v
          Relevant Knowledge
                 |
                 v
            AI / LLM
                 |
                 v
          Contextual Answer
```

pgvector will be used to store embeddings and perform similarity searches directly within PostgreSQL.

## 📁 Project Structure

The backend is intended to follow a modular structure similar to:

```text
ProMe/
│
├── prome-backend/
│   ├── src/main/java/com/prome/
│   │   ├── knowledge/
│   │   ├── document/
│   │   ├── ai/
│   │   ├── search/
│   │   ├── user/
│   │   ├── common/
│   │   └── ProMeApplication.java
│   │
│   ├── src/main/resources/
│   │   └── application.yml
│   │
│   └── pom.xml
│
├── prome-frontend/
│   └── (React application)
│
└── README.md
```

*The structure is a proposed organizational layout and may evolve during development.*

## 🚀 Development Roadmap

ProMe is being developed incrementally.

- [ ] Project initialization and configuration
- [ ] PostgreSQL database integration
- [ ] Modular backend foundation
- [ ] Knowledge management module
- [ ] Document management module
- [ ] pgvector integration
- [ ] Embedding generation
- [ ] Semantic search implementation
- [ ] NVIDIA NIM integration
- [ ] Local model integration using Ollama
- [ ] RAG-based AI knowledge chat
- [ ] React frontend integration
- [ ] Testing and optimization
- [ ] Deployment

## 💡 Long-Term Goals

Beyond the initial MVP, ProMe aims to explore:

- Intelligent knowledge connections.
- Automatic knowledge categorization.
- Context-aware personal AI assistants.
- Multi-format knowledge ingestion.
- Improved retrieval through hybrid search.
- Personalized knowledge discovery.

## 👨‍💻 Development

ProMe is an individual development project focused on exploring practical applications of:

- Backend engineering with Java and Spring Boot.
- Modular monolith architecture.
- Relational and vector database integration.
- AI provider abstraction.
- Embedding models and semantic search.
- Retrieval-Augmented Generation.

The project is actively evolving, and implementation details may change as development progresses.

---

**ProMe – Your Knowledge. Connected. Intelligent. Accessible.**
