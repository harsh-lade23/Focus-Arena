# 🎯 Focus Arena

> A competitive productivity platform where users don't just track work — they **compete, win, and improve together**.

---

## About the Project
**Focus Arena** is a next-generation productivity app built around **competition-driven focus**.
Instead of traditional task tracking, users participate in **challenges**, complete study sessions, and compete using **points, streaks, and leaderboards**.

---
## 🎯 Problem
Most productivity apps track tasks, but lack motivation and competition.

## 💡 Solution
Focus Arena introduces a challenge-based system where users compete using real study sessions.

---

## 🚀 Core Idea
* ⚔️ 1v1 and Group Challenges
* ⏱ Track real study sessions (not fake inputs)
* 🏆 Compete using points & rankings
* 🔥 Gamification with streaks and rewards
* 🎯 Focus on **consistent performance**, not just activity

---

## 🧠 System Design

- StudySession → tracks actual work (truth layer)
- SessionParticipation → maps session to challenges
- Participants → stores leaderboard stats

### 🔗 Relationship Model
User ↔ Challenge (Many-to-Many)
→ A user can participate in multiple challenges simultaneously.


---

## 🔄 Data Flow

1. User creates study session
2. Session is mapped to selected challenges
3. Stats updated atomically
4. Leaderboard updates
---

## ⚙️ Current Features
* 🔐 User Authentication (Email & Password)
* ➕ Create / Join Challenges
* 📊 Challenge Dashboard:
    * Score comparison
    * Timer-based task tracking
    * My Tasks / Opponent Tasks / Overdue Tasks
* ⏳ Study Session Tracking
* 📅 Study Activity Heatmap (Last 30 days)
* 🔔 Nudge Opponent Feature

---

## 🚧 Planned Features
* 🔁 Multiple Challenges per User
* 👥 Group Challenges (multi-user competition)
* 🏆 Leaderboards & Ranking System
* 🎮 Advanced Gamification (streaks, rewards, points system)
* 📂 Challenge Lifecycle (templates, restart, archive)
* ⚡ Improved Firebase Sync & Data Consistency

---


## 🏗️ Tech Stack
* 🟣 Kotlin
* 🧱 Jetpack Compose
* 🔥 Firebase Firestore
* 💉 Hilt (Dependency Injection)
* 🧠 MVVM + Clean Architecture

---

## 📸 Screenshots

(coming soon)

---

## 🚀 Getting Started
```bash
# Clone the repository
git clone https://github.com/harsh-lade23/Focus-Arena.git

# Open in Android Studio
# Add your Firebase configuration (google-services.json)

# Run the app 🚀
```

---

## 🚧 Status

Currently rebuilding core system with scalable architecture.

---

## 🎯 Future Vision
Focus Arena is evolving into a **full-scale competitive productivity platform**:
* 🌍 Multi-challenge ecosystem
* 🧠 AI-based productivity insights
* 📈 Advanced analytics & performance tracking
* 💰 Monetization & premium features

---

## 👨‍💻 Author
**Harsh Lade**
* B.Tech CSE (Data Science)
* Android Developer (Kotlin + Jetpack Compose)
* Building scalable, real-world systems

