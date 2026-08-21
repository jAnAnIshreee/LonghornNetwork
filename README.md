# LonghornNetwork

A simulated social network for university students — matching roommates, mapping social connections, and finding internship referral paths through a graph of shared relationships.

## Overview

LonghornNetwork models a campus community as a weighted graph, where each student is a node and the strength of their relationship to another student (shared major, age, internships, roommate status) forms the weight of the edge between them. On top of this graph, the project layers three classic algorithms to solve real social-network problems:

- **Stable roommate matching** using the Gale-Shapley algorithm
- **Internship referral path-finding** using Dijkstra's algorithm
- **Real-time simulated interactions** (friend requests, chats) using multithreading

The result is a small but complete system spanning data parsing, graph theory, concurrency, and a Swing-based visual interface.

## Features

### Connection Strength Modeling
Every pair of students is scored on a weighted connection strength based on:
- Being roommates (+4)
- Each shared internship (+3 each)
- Sharing a major (+2)
- Being the same age (+1)

Students with no shared attributes are left unconnected — the graph is sparse by design, and disconnected components are expected and handled explicitly.

### Roommate Matching (Gale-Shapley)
Students submit ranked roommate preferences. The system computes a stable matching where no two students would both prefer each other over their assigned roommates. Students with empty, partial, or cyclic preferences are handled gracefully, and unmatched students remain unpaired rather than forced into a bad fit.

### Referral Path Finding (Dijkstra's Algorithm)
Given a target company, the system finds the strongest chain of connections leading to a student who interned there. Because Dijkstra's algorithm finds *shortest* paths and the project wants the *strongest* connections, edge weights are inverted (`10 - weight`) specifically for this search — connection strength everywhere else in the project is used as-is.

### Concurrent Social Activity
Friend requests and chat messages are simulated as concurrent operations using `ExecutorService` and thread-safe data structures, mimicking how a real social platform would need to handle simultaneous user actions without corrupting shared state (like chat histories or friend lists).

### Graph Visualization (Swing UI)
A graphical interface built with Java Swing lets you:
- Load and inspect test data on startup
- Visualize the full student graph, with names and connection weights on edges
- View roommate pairings
- Trace and display referral paths to a target company
- Inspect each student's friend request and chat history

## Architecture

| Component | Responsibility |
|---|---|
| `DataParser.java` | Reads raw student data from file and constructs `UniversityStudent` objects |
| `StudentGraph.java` | Adjacency-list graph of students, supporting edge insertion, neighbor lookup, and traversal |
| `GaleShapley.java` | Stable roommate matching |
| `ReferralPathFinder.java` | Dijkstra-based path search for internship referrals |
| `UniversityStudent.java` | Student model, including the connection-strength calculation |
| `FriendRequestThread.java` / `ChatThread.java` | Concurrent simulation of social interactions |
| Swing UI | Front-end visualization for graph, roommates, referral paths, and chat/friend history |

The graph itself is built as an adjacency list, where each student maps to a list of `(neighbor, weight)` pairs, e.g.:

```
Alice   -> [(Bob, 7), (Charlie, 5)]
Bob     -> [(Alice, 7)]
Charlie -> [(Alice, 5)]
```

## Tech Stack

- **Java** — core language
- **Graph algorithms** — Gale-Shapley (stable matching), Dijkstra's (shortest/strongest path)
- **Concurrency** — `ExecutorService`, synchronized/concurrent collections
- **Swing** — desktop UI for visualization

## Project Structure

```
├── src/            # Core implementation
├── testing/        # Sample input/output for validation (input_sample.txt, output_sample.txt)
└── README.md
```

## Notes

This project was originally built as a course assignment (ECE 422C) and has been adapted here as a standalone showcase of graph algorithms, concurrent programming, and UI visualization in Java.
